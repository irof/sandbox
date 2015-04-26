import com.github.mjeanroy.junit.servers.jetty.EmbeddedJetty
import com.github.mjeanroy.junit.servers.rules.JettyServerRule
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test

class JettyServerSpec {

    @ClassRule
    public static JettyServerRule server = new JettyServerRule(new EmbeddedJetty());

    @Before
    void setup() {
        go "http://localhost:${server.port}"
    }

    @Test
    def "indexを表示する"() {
        expect:
        title == "JettyServerRuleIndexPage"
    }

    @Test
    def "テキストの内容で選り分ける_HtmlUnit"() {
        expect:
        $("section.classA div", text: ~/.*ふが.*/).$("p").text() == "取りたいやつ"
    }

    @Test
    def "テキストの内容で選り分ける"() {
        expect:
        $("section.classA div").has("span", text: ~/.*ふが.*/).$("p").text() == "取りたいやつ"
    }

    @Test
    def "表示されているかで選り分ける_HtmlUnit"() {
        expect:
        $("section.classB div").findAll { !it.attr("style").contains("display:none") }.$("p").text() == "取りたいやつ"
    }

    @Test
    def "表示されているかで選り分ける_FireFox"() {
        expect:
        $("section.classB div").findAll { it.displayed }.$("p").text() == "取りたいやつ"
    }
}
