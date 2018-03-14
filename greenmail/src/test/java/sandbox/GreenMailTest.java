package sandbox;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.internet.MimeMessage;

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
}
