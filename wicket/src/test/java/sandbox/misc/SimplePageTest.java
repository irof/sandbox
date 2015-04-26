package sandbox.misc;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

public class SimplePageTest {

    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester(new WicketApplication());
        tester.startPage(SimplePage.class);
    }

    @Test
    public void hasButtonPanel() {
        tester.assertComponent("panel.button", ButtonPanel.class);
    }

    @Test
    public void hasAjaxButtonPanel() {
        tester.assertComponent("panel.button.ajax", AjaxButtonPanel.class);
    }
}