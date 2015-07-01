package sandbox.structure;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author irof
 */
public class FragmentSamplePageTest {

    private WicketTester tester;

    @Before
    public void setup() throws Exception {
        tester = new WicketTester();
    }

    @Test
    public void myPage() throws Exception {
        tester.startPage(FragmentSamplePage.class);
    }

    @Test
    public void myFragment() throws Exception {
        tester.startPage(FragmentSamplePage.class);
        tester.assertLabel("target1:message", "hoge");
        tester.assertLabel("target2:message", "fuga");
    }
}
