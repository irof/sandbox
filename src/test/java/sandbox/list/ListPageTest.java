package sandbox.list;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

import java.util.Collections;

/**
 * Simple test using the WicketTester
 */
public class ListPageTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void emptyList() throws Exception {
        tester.startPage(ListPage.class);
        tester.assertListView("list", Collections.emptyList());
    }
}
