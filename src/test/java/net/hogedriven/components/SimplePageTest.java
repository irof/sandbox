package net.hogedriven.components;

import net.hogedriven.HomePage;
import net.hogedriven.WicketApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

public class SimplePageTest {

    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester(new WicketApplication());
        tester.startPage(SimplePage.class);
    }

    @Test
    public void click() {
        tester.startPage(HomePage.class);
        tester.clickLink("components.link");
        tester.assertRenderedPage(SimplePage.class);
    }

    @Test
    public void propertyField() {
        tester.assertLabel("property:field", "FIELD");
    }

    @Test
    public void propertyField2() {
        tester.newFormTester("property").submit("button1");
        tester.assertLabel("property:field", "field");
    }

    @Test
    public void buttonLabel() {
        tester.assertLabel("property:button1:field", "FIELD");

        FormTester form = tester.newFormTester("property");
        form.submit("button1");

        tester.assertLabel("property:button1:field", "field");
    }

    @Test
    public void inputButtonSubmit() {
        tester.assertModelValue("property:button2", "FIELD");

        FormTester form = tester.newFormTester("property");
        form.submit("button2");

        tester.assertModelValue("property:button2", "field");
    }
}