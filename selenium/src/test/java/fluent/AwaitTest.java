package fluent;

import com.google.common.base.Predicate;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;


/**
 * @author irof
 */
public class AwaitTest extends FluentTest {

    @Before
    public void setup() throws Exception {
        // idとかの検索で見つからない時にとりあえず待つ時間
        withDefaultSearchWait(5, TimeUnit.SECONDS);

        String url = Paths.get("html", "await", "await.html").toUri().toURL().toString();
        goTo(url);
    }

    @Test
    public void 普通の表示() throws Exception {
        click("#showHoge");
        assertThat(findFirst("#hoge")).isDisplayed();
    }

    @Test
    public void 少し経ってから表示() throws Exception {
        click("#showFuga");
        await().atMost(3, TimeUnit.SECONDS).until("#fuga").areDisplayed();

        assertThat(findFirst("#fuga")).isDisplayed();
    }

    @Test
    public void 少し経ってから要素が追加されて少し経ってから要素が消えて() throws Exception {
        click("#addPiyo");

        // ここはwithDefaultSearchWaitの設定に待つ感じ
        assertThat(findFirst("#piyo > #foo")).hasText("ふー");

        click("#removePiyo");
        await().atMost(3, TimeUnit.SECONDS).until(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent input) {
                try {
                    FluentWebElement element = input.findFirst("#piyo > #foo");
                    return false;
                } catch (NoSuchElementException ex) {
                    return true;
                }
            }
        });
    }
}
