package sandbox.i18n;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Simple test using the WicketTester
 */
public class ResourcePageTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
        tester.startPage(ResourcePage.class);
    }

    @Test
    public void Javaファイルを介しない_wicketMessageタグ() {
        assertThat(tester.getTagById("simple1").getValue(), is("シンプルなメッセージ"));
    }

    @Test
    public void Javaファイルを介しない_inputタグ() {
        // <input id="simple2" value="シンプルなメッセージ"/>
        String value = tester.getTagById("simple2").getAttribute("value");
        assertThat(value, is("シンプルなメッセージ"));
    }

    @Test
    public void ResourceModel() {
        tester.assertLabel("resource.static", "シンプルなメッセージ");
    }

    @Test
    public void StringResourceModel1() {
        tester.assertLabel("resource.dynamic1", "動的なメッセージ1[Dynamic, Args, {2}]");
    }

    @Test
    public void StringResourceModel2() {
        tester.assertLabel("resource.dynamic2", "動的なメッセージ2[Argument1, ${arg2}, Argument3]");
    }
}
