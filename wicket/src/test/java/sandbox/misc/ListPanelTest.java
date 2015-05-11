package sandbox.misc;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;
import sandbox.misc.ListPanel;

import java.util.Collections;

/**
 * Simple test using the WicketTester
 */
public class ListPanelTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
        tester.startComponentInPage(ListPanel.class);
    }

    @Test
    public void emptyList() throws Exception {
        tester.assertListView("list", Collections.emptyList());
    }

    @Test
    public void singletonList() {
        tester.newFormTester("form").submit("singleton");
        tester.assertListView("list", Collections.singletonList("hoge"));
    }

    @Test
    public void repeatingView() {
        tester.assertContains("repeating1");
        tester.assertContains("repeating2");
    }
}
