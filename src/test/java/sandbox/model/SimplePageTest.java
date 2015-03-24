package sandbox.model;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class SimplePageTest {

    WicketTester tester = new WicketTester();

    @Test
    public void instantiation() {
        tester.startPage(SimplePage.class);
    }
}
