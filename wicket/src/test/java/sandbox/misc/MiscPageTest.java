package sandbox.misc;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

public class MiscPageTest {

    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester(new WicketApplication());
        tester.startPage(MiscPage.class);
    }

    @Test
    public void ButtonPanelが表示できてる() {
        tester.assertComponent("panel.button", ButtonPanel.class);
    }

    @Test
    public void ListPanelが表示できてる() {
        tester.assertComponent("panel.list", ListPanel.class);
    }
}