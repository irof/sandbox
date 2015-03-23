package sandbox.misc;

import org.apache.wicket.util.tester.FormTester;
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
        FormTester form = tester.newFormTester("form");
        form.submit("button1");

        tester.assertLabel("label1", "fuga");
        tester.assertLabel("label2", "hoge");
    }
}