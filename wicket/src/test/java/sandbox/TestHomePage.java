package sandbox;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.i18n.ResourcePage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void homepageRendersSuccessfully() {
        //start and render the test page
        tester.startPage(HomePage.class);

        //assert rendered page class
        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    public void click_i18n_link() throws Exception {
        tester.startPage(HomePage.class);
        tester.clickLink("i18n.link");
        tester.assertRenderedPage(ResourcePage.class);
    }

    @Test
    public void click_components_link() throws Exception {
        tester.startPage(HomePage.class);
        tester.clickLink("misc.link");
        tester.assertRenderedPage(sandbox.misc.SimplePage.class);
    }
}
