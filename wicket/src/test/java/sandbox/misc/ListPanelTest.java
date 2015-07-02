package sandbox.misc;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

import java.util.Arrays;
import java.util.Collections;

public class ListPanelTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
        tester.startComponentInPage(ListPanel.class);
    }

    @Test
    public void ボタンを押してリストを増やす() {
        tester.newFormTester("form").submit("add");
        tester.assertListView("list", Collections.singletonList("hoge0"));

        tester.newFormTester("form").submit("add");
        tester.assertListView("list", Arrays.asList("hoge0", "hoge1"));
    }

    @Test
    public void RepeatingViewが出てる確認() {
        tester.assertContains("repeating1");
        tester.assertContains("repeating2");
    }
}
