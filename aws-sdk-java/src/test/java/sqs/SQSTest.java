package sqs;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
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
}
