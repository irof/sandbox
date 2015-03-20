package net.hogedriven.components;

import net.hogedriven.WicketApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class SimplePageTest {

    @Test
    public void propertyField() {
        WicketTester tester = new WicketTester(new WicketApplication());
        tester.startPage(SimplePage.class);

        tester.assertLabel("property:field", "FIELD");
    }
}