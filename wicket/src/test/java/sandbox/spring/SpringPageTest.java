package sandbox.spring;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author irof
 */
public class SpringPageTest {

    private WicketTester tester;

    @Before
    public void setup() throws Exception {
        tester = new WicketTester();
    }

    @Test
    public void myPage() throws Exception {
        tester.startPage(SpringPage.class);
    }
}
