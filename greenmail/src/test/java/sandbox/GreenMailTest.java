package sandbox;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class GreenMailTest {

    @Rule
    public final GreenMailRule greenMailRule = new GreenMailRule(ServerSetupTest.SMTP_IMAP);

    @Test
    public void testSending() throws Exception {
        GreenMailUtil.sendTextEmailTest("to@example.com", "from@example.com", "some test subject", "some test body");

        String body = GreenMailUtil.getBody(greenMailRule.getReceivedMessages()[0]);
        assertThat(body).isEqualTo("some test body");
    }

    @Test
    public void testRetrieving() throws Exception {
        GreenMailUser user = greenMailRule.setUser("to@example.com", "login-test-id", "login-test-password");
        user.deliver(createMessage());

        GreenMailUtil.sendTextEmailTest("to@example.com", "from@example.com", "some test subject", "some test body");

        assertThat(greenMailRule.getReceivedMessages()).hasSize(2);
    }

    private MimeMessage createMessage() {
        ServerSetup serverSetup = ServerSetupTest.IMAP;
        MimeMessage message = GreenMailUtil.createTextEmail("to@example.com", "from@example.com", "some test subject", "some test body", serverSetup);
        return message;
    }

    @Test
    public void testingUsingPlainJavaMail() throws Exception {
        String host = greenMailRule.getSmtp().getBindTo();
        int port = greenMailRule.getSmtp().getPort();

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", Integer.toString(port));
        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("foo@example.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("bar@example.com"));
        message.setSubject("ほげ");
        message.setText("ふが");
        Transport.send(message);

        MimeMessage[] receivedMessages = greenMailRule.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);

        MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getContent()).isEqualTo("ふが");

        String encodedBody = GreenMailUtil.getBody(greenMailRule.getReceivedMessages()[0]);
        byte[] decode = Base64.getMimeDecoder().decode(encodedBody);
        String body = new String(decode);
        assertThat(body).isEqualTo("ふが");
    }
}
