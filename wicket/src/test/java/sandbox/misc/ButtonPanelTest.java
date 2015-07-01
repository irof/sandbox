package sandbox.misc;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

public class ButtonPanelTest {

    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester(new WicketApplication());
        tester.startComponentInPage(ButtonPanel.class);
    }

    @Test
    public void propertyField() {
        tester.assertLabel("form:label", "FIELD");
    }

    @Test
    public void propertyField2() {
        tester.newFormTester("form").submit("button1");
        tester.assertLabel("form:label", "field");
    }

    @Test
    public void buttonLabel() {
        tester.assertLabel("form:button1:label", "FIELD");

        FormTester form = tester.newFormTester("form");
        form.submit("button1");

        tester.assertLabel("form:button1:label", "field");
    }

    @Test
    public void inputButtonSubmit() {
        tester.assertModelValue("form:button2", "FIELD");

        FormTester form = tester.newFormTester("form");
        form.submit("button2");

        tester.assertModelValue("form:button2", "field");
    }
    @Test
    public void ajaxInputButtonSubmit() {
        tester.executeAjaxEvent("ajaxForm:button1", "click");

        tester.assertComponentOnAjaxResponse("ajaxForm:label1");
        tester.assertLabel("ajaxForm:label1", "fuga");
    }

    @Test
    public void ajaxInputButtonSubmit2() {
        tester.executeAjaxEvent("ajaxForm:button2", "click");

        tester.assertComponentOnAjaxResponse("ajaxForm:label2");
    }
}