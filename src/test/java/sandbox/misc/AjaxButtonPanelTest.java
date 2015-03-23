package sandbox.misc;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

public class AjaxButtonPanelTest {

    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester(new WicketApplication());
        tester.startComponentInPage(AjaxButtonPanel.class);
    }

    @Test
    public void inputButtonSubmit() {
        tester.executeAjaxEvent("form:button1", "click");

        tester.assertComponentOnAjaxResponse("label1");
        tester.assertLabel("label1", "fuga");
    }

    @Test
    public void inputButtonSubmit2() {
        tester.executeAjaxEvent("form:button2", "click");

        tester.assertComponentOnAjaxResponse("label2");
    }
}