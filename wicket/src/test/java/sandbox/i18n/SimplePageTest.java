package sandbox.i18n;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class SimplePageTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
        tester.startPage(SimplePage.class);
    }

    @Test
    public void simplePropertyMessage() {
        tester.assertContains("hello simple property");
    }

    @Test
    public void simpleTagMessage() {
        tester.assertContains("hello tag attribute property");
    }

    @Test
    public void resourceModelMessageStatic() {
        tester.assertLabel("resource.static", "Resource Model message");
    }

    @Test
    public void resourceModelMessageDynamic() {
        tester.assertLabel("resource.dynamic", "Dynamic Resource Model message");
    }

    @Test
    public void helloWorld() {
        tester.assertLabel("hello", "hello irof.");
    }
}