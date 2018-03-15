package sandbox;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.mail.host=localhost", "spring.mail.port=9999"})
public class SpringMailTest {

    @Rule
    public GreenMailRule greenMailRule = new GreenMailRule(new ServerSetup(9999, null, "smtp"));

    @Autowired
    MailSender sender;

    @Test
    public void test() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("from-test@example.com");
        message.setTo("to-1@example.com", "to-2@example.com");
        message.setCc("cc-1@example.com");
        message.setSubject("テストメッセージサブジェクト");
        message.setText("テストメッセージテキスト");

        sender.send(message);

        MimeMessage[] receivedMessages = greenMailRule.getReceivedMessages();
        // 宛先が3つなので
        assertThat(receivedMessages).hasSize(3);

        MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getSubject()).isEqualTo("テストメッセージサブジェクト");
        assertThat(receivedMessage.getContent()).isEqualTo("テストメッセージテキスト");
    }
}
