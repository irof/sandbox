package hogedriven.matchers;

import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

/**
 * @author irof
 */
public class ReceiveMessageResultMatchers {

    public static TypeSafeMatcher<ReceiveMessageResult> messageCount(int i) {
        return new TypeSafeMatcher<ReceiveMessageResult>() {
            @Override
            protected boolean matchesSafely(ReceiveMessageResult receiveMessageResult) {
                return receiveMessageResult.getMessages().size() == i;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("messages.size is " + i);
            }
        };
    }

    public static TypeSafeMatcher<ReceiveMessageResult> hasMessage(String message) {
        return new TypeSafeMatcher<ReceiveMessageResult>() {
            @Override
            protected boolean matchesSafely(ReceiveMessageResult receiveMessageResult) {
                return receiveMessageResult.getMessages().stream()
                        .filter(res -> Objects.equals(res.getBody(), message))
                        .findAny()
                        .isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("contains message '" + message + "'");
            }
        };
    }
}
