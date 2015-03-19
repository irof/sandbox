package net.hogedriven.i18n;

import net.hogedriven.HomePage;
import net.hogedriven.WicketApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class SimplePageTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void linkHomePageToSimplePage() {
        tester.startPage(HomePage.class);
        tester.clickLink(tester.getComponentFromLastRenderedPage("simplePage"));

        tester.assertRenderedPage(SimplePage.class);
    }

    @Test
    public void simplePropertyMessage() {
        tester.startPage(SimplePage.class);

        tester.assertContains("hello simple property");
    }

    @Test
    public void simpleTagMessage() {
        tester.startPage(SimplePage.class);

        tester.assertContains("hello tag attribute property");
    }
}
