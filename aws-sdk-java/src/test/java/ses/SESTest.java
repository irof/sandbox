package ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author irof
 */
public class SESTest {

    private static final Logger logger = LoggerFactory.getLogger(SESTest.class);

    @Test
    public void 適当に送信する() throws Exception {
        AmazonSimpleEmailService client = new AmazonSimpleEmailServiceClient();

        SendEmailResult result = client.sendEmail(
                new SendEmailRequest()
                        .withSource("irof@hogedriven.net")
                        .withDestination(new Destination()
                                .withToAddresses("irof@hogedriven.net"))
                        .withMessage(new Message()
                                .withSubject(new Content("HOGEDRIVEN"))
                                .withBody(new Body().withText(new Content("hello, SES!"))))
        );

        logger.info(result.toString());
    }
}
