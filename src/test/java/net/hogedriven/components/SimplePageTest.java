package net.hogedriven.components;

import net.hogedriven.WicketApplication;
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
    public void propertyField() {
        tester.assertLabel("property:field", "FIELD");
    }
}