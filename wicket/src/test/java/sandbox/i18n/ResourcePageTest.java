package sandbox.i18n;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
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

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void wicketのmessageタグは通常エスケープされない() throws Exception {
        collector.checkThat(tester.getTagById("m.p1").getValue(), is("<b>BOLD</b>"));
        collector.checkThat(tester.getTagById("m.p2").getValue(), is("<b>BOLD</b>"));
        collector.checkThat(tester.getTagById("m.p3").getValue(), is("&lt;b&gt;BOLD&lt;/b&gt;"));
    }

    @Test
    public void wicketのmessageタグのエスケープを有効にした() throws Exception {
        collector.checkThat(tester.getTagById("m.p1.esc").getValue(), is("&lt;b&gt;BOLD&lt;/b&gt;"));
        collector.checkThat(tester.getTagById("m.p2.esc").getValue(), is("&lt;b&gt;BOLD&lt;/b&gt;"));
        collector.checkThat(tester.getTagById("m.p3.esc").getValue(), is("&amp;lt;b&amp;gt;BOLD&amp;lt;/b&amp;gt;"));
    }

    @Test
    public void Labelクラスは通常エスケープされる() throws Exception {
        collector.checkThat(tester.getTagById("m.l.p1").getValue(), is("&lt;b&gt;BOLD&lt;/b&gt;"));
        collector.checkThat(tester.getTagById("m.l.p2").getValue(), is("&lt;b&gt;BOLD&lt;/b&gt;"));
        collector.checkThat(tester.getTagById("m.l.p3").getValue(), is("&amp;lt;b&amp;gt;BOLD&amp;lt;/b&amp;gt;"));
    }

    @Test
    public void Labelクラスのエスケープを無効にした() throws Exception {
        collector.checkThat(tester.getTagById("m.l.p1.unesc").getValue(), is("<b>BOLD</b>"));
        collector.checkThat(tester.getTagById("m.l.p2.unesc").getValue(), is("<b>BOLD</b>"));
        collector.checkThat(tester.getTagById("m.l.p3.unesc").getValue(), is("&lt;b&gt;BOLD&lt;/b&gt;"));
    }
}
