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
    public void showPage() throws Exception {
        tester.startPage(MyPage.class);
    }

    @Test
    public void myPanel() throws Exception {
        tester.startComponentInPage(MyPanel.class);
        tester.assertContains("inside");
        tester.assertContainsNot("outside");
    }
}
