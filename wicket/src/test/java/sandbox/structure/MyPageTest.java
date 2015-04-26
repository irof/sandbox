package sandbox.structure;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author irof
 */
public class MyPageTest {

    private WicketTester tester;

    @Before
    public void setup() throws Exception {
        tester = new WicketTester();
    }

    @Test
    public void myPage() throws Exception {
        tester.startPage(MyPage.class);
        tester.assertContains("my panel is rendered.");
    }

    @Test
    public void myPanel() throws Exception {
        tester.startComponentInPage(MyPanel.class);
        tester.assertContains("inside");
        tester.assertContainsNot("outside");
    }

    @Test
    public void myFragment() throws Exception {
        tester.startPage(MyPage.class);
        tester.assertLabel("contentArea1:message", "hoge");
        tester.assertLabel("contentArea2:message", "fuga");
    }
}
