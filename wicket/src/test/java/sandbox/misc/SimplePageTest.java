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
    public void ButtonPanelが表示できてる() {
        tester.assertComponent("panel.button", ButtonPanel.class);
    }

    @Test
    public void AjaxButtonPanelが表示できてる() {
        tester.assertComponent("panel.button.ajax", AjaxButtonPanel.class);
    }
}