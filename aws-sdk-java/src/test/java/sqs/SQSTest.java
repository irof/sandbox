package sqs;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import org.junit.Before;
import org.junit.Test;

import static hogedriven.matchers.ReceiveMessageResultMatchers.hasMessage;
import static hogedriven.matchers.ReceiveMessageResultMatchers.messageCount;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * 使うのは5つ(CreateQueue、SendMessage、ReceiveMessage、ChangeMessageVisibility、および DeleteMessage)
 *
 * @author irof
 */
public class SQSTest {

    private AmazonSQS sqs;

    @Before
    public void setup() throws Exception {
        sqs = new AmazonSQSClient(new ProfileCredentialsProvider());
    }

    @Test
    public void キューの作成と削除() throws Exception {
        // キュー作るよ(既にあっても別に文句言われない)
        CreateQueueResult myQueue = sqs.createQueue("myTestQueue1");
        assertThat(myQueue.getQueueUrl(), is(endsWith("/myTestQueue1")));

        // キューの一覧を見るよ
        ListQueuesResult list = sqs.listQueues();
        assertThat(list.getQueueUrls(), hasItem(endsWith("/myTestQueue1")));

        // キューを消しちゃうよ
        sqs.deleteQueue(myQueue.getQueueUrl());
    }

    @Test
    public void メッセージの送受信() throws Exception {
        ListQueuesResult list = sqs.listQueues("myTestQueue2");
        if (!list.getQueueUrls().stream().anyMatch(url -> url.endsWith("/myTestQueue2")))
            sqs.createQueue("myTestQueue2");

        GetQueueUrlResult queue = sqs.getQueueUrl("myTestQueue2");
        String url = queue.getQueueUrl();

        // メッセージを詰める
        sqs.sendMessage(url, "test message.");

        // メッセージを取り出す
        ReceiveMessageResult receiveMessage = sqs.receiveMessage(url);
        assertThat(receiveMessage, messageCount(1));
        assertThat(receiveMessage, hasMessage("test message."));

        // メッセージを消す
        sqs.deleteMessage(url, receiveMessage.getMessages().get(0).getReceiptHandle());
    }

    @Test
    public void 複数メッセージの送受信() throws Exception {
        ListQueuesResult list = sqs.listQueues("myTestQueue2");
        if (!list.getQueueUrls().stream().anyMatch(url -> url.endsWith("/myTestQueue2")))
            sqs.createQueue("myTestQueue2");

        GetQueueUrlResult queue = sqs.getQueueUrl("myTestQueue2");
        String url = queue.getQueueUrl();

        // 2件登録する
        sqs.sendMessage(new SendMessageRequest()
                .withQueueUrl(url)
                .withMessageBody("hello message 1."));
        sqs.sendMessage(new SendMessageRequest()
                .withQueueUrl(url)
                .withMessageBody("hello message 2."));

        // 複数件入っている状態で受信する
        ReceiveMessageResult receiveMessage1 = sqs.receiveMessage(
                new ReceiveMessageRequest(url));
        // 特に何もしなければ1件取得される
        assertThat(receiveMessage1, messageCount(1));

        // visibilityTimeoutを0にして受信する（デフォルトは30秒）
        // 指定した秒間は他のリクエストでは取得できなくなる
        ReceiveMessageResult receiveMessage2 = sqs.receiveMessage(
                new ReceiveMessageRequest(url).withVisibilityTimeout(0));
        // 何も指定せずもう一度受信する
        ReceiveMessageResult receiveMessage3 = sqs.receiveMessage(
                new ReceiveMessageRequest(url));
        // 2回目のリクエストのvisibilityTimeoutが0なので即タイムアウトして同じメッセージが取得できる
        // messageIdが同じだけどreceiptHandleは異なっている
        Message message2 = receiveMessage2.getMessages().get(0);
        Message message3 = receiveMessage3.getMessages().get(0);
        assertThat(message2.getMessageId(), is(message3.getMessageId()));
        assertThat(message2.getReceiptHandle(), is(not(message3.getReceiptHandle())));

        // 2件ともreceiveされた状態で取得を試みる
        ReceiveMessageResult receiveMessage4 = sqs.receiveMessage(
                new ReceiveMessageRequest(url));
        // 全部不可視となっているため何も取得できない
        assertThat(receiveMessage4, messageCount(0));

        // メッセージのおおよその件数を取得する　
        GetQueueAttributesResult queueAttributes = sqs.getQueueAttributes(new GetQueueAttributesRequest(url)
                .withAttributeNames(QueueAttributeName.ApproximateNumberOfMessages)
                .withAttributeNames(QueueAttributeName.ApproximateNumberOfMessagesNotVisible));
        // 不可視2件
        assertThat(queueAttributes.getAttributes(),
                hasEntry(QueueAttributeName.ApproximateNumberOfMessages.toString(), "0"));
        assertThat(queueAttributes.getAttributes(),
                hasEntry(QueueAttributeName.ApproximateNumberOfMessagesNotVisible.toString(), "2"));

        // バッチで削除する
        // バッチに指定するIDはリクエウトとレスポンスの紐付け用でリクエスト内でユニークならなんでも良い
        // 削除にはreceiptHandler必要
        // message2のようなタイムアウトしているreceiptHandleでも削除できる
        DeleteMessageBatchResult batchResult = sqs.deleteMessageBatch(new DeleteMessageBatchRequest()
                .withQueueUrl(url)
                .withEntries(new DeleteMessageBatchRequestEntry()
                        .withId("ID1")
                        .withReceiptHandle(receiveMessage1.getMessages().get(0).getReceiptHandle()))
                .withEntries(new DeleteMessageBatchRequestEntry("ID2", message2.getReceiptHandle())));
        // deleteMessageは結果返ってこず失敗したら例外ぽいけど、deleteMessageBatchは返ってくる
        assertThat(batchResult.getSuccessful(), hasSize(2));
        assertThat(batchResult.getFailed(), hasSize(0));

        // メッセージのおおよその件数を取得する　
        GetQueueAttributesResult queueAttributes2 = sqs.getQueueAttributes(new GetQueueAttributesRequest(url)
                .withAttributeNames(QueueAttributeName.ApproximateNumberOfMessages)
                .withAttributeNames(QueueAttributeName.ApproximateNumberOfMessagesNotVisible));
        // メッセージなし
        assertThat(queueAttributes2.getAttributes(),
                hasEntry(QueueAttributeName.ApproximateNumberOfMessages.toString(), "0"));
        assertThat(queueAttributes2.getAttributes(),
                hasEntry(QueueAttributeName.ApproximateNumberOfMessagesNotVisible.toString(), "0"));
    }

    @Test
    public void DelayQueueとLongPolling() throws Exception {
        ListQueuesResult list = sqs.listQueues("myTestQueue3");
        if (!list.getQueueUrls().stream().anyMatch(url -> url.endsWith("/myTestQueue3")))
            sqs.createQueue("myTestQueue3");

        String url = sqs.getQueueUrl("myTestQueue3").getQueueUrl();

        // Delay Queue
        // 5秒後に遅延配信されるメッセージ
        sqs.sendMessage(new SendMessageRequest(url, "test delay message.").withDelaySeconds(5));
        // すぐ配信されるメッセージ
        sqs.sendMessage(url, "test message.");

        // Long Polling
        // メッセージがなかったら10秒待つよ
        ReceiveMessageResult receiveMessage = sqs.receiveMessage(new ReceiveMessageRequest(url)
                .withMaxNumberOfMessages(5)
                .withWaitTimeSeconds(10));
        // 1件はすぐ取れるので待たずに返ってくる
        assertThat(receiveMessage, messageCount(1));
        assertThat(receiveMessage, hasMessage("test message."));

        // もう一度取得しに行く
        ReceiveMessageResult receiveMessage2 = sqs.receiveMessage(new ReceiveMessageRequest(url)
                .withMaxNumberOfMessages(5)
                .withWaitTimeSeconds(10));
        // 5秒後に遅延配信されるのをとってくる
        assertThat(receiveMessage2, messageCount(1));
        assertThat(receiveMessage2, hasMessage("test delay message."));

        // 消しとく
        receiveMessage.getMessages().stream()
                .map(Message::getReceiptHandle)
                .forEach(handle -> sqs.deleteMessage(url, handle));
        receiveMessage2.getMessages().stream()
                .map(Message::getReceiptHandle)
                .forEach(handle -> sqs.deleteMessage(url, handle));
    }
}
