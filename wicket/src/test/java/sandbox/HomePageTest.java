package sandbox;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.i18n.ResourcePage;

public class HomePageTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
        tester.startPage(HomePage.class);
    }

    @Test
    public void 初期状態の確認() {
        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    public void リンク確認_Resouce() throws Exception {
        tester.clickLink("i18n.link");
        tester.assertRenderedPage(ResourcePage.class);
    }

    @Test
    public void リンク確認_misc() throws Exception {
        tester.clickLink("misc.link");
        tester.assertRenderedPage(sandbox.misc.SimplePage.class);
    }
}
