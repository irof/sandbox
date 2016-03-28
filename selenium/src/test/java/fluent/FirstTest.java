package fluent;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
public class FirstTest extends FluentTest {

    @Test
    public void test() throws Exception {
        String url = Paths.get("html/index.html").toUri().toURL().toString();
        goTo(url);

        click("form input[type='submit']");
        assertThat(title()).isEqualTo("target page");
    }
}
