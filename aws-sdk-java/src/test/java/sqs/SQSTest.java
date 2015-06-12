package sqs;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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

        // メッセージを詰める
        SendMessageResult sendMessage = sqs.sendMessage(queue.getQueueUrl(), "test message.");
        assertThat(sendMessage.getMessageId(), is(notNullValue()));

        // メッセージを取り出す
        ReceiveMessageResult receiveMessage = sqs.receiveMessage(queue.getQueueUrl());
        List<Message> messages = receiveMessage.getMessages();
        assertThat(messages, hasSize(1));
        Message message = messages.get(0);
        assertThat(message.getBody(), is("test message."));

        // メッセージを消す
        sqs.deleteMessage(queue.getQueueUrl(), message.getReceiptHandle());
    }
}
