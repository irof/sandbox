import com.github.mjeanroy.junit.servers.jetty.EmbeddedJetty
import com.github.mjeanroy.junit.servers.rules.JettyServerRule
import geb.spock.GebSpec
import org.junit.ClassRule
import spock.lang.Shared

class JettyServerSpec extends GebSpec {

    @Shared @ClassRule
    JettyServerRule server = new JettyServerRule(new EmbeddedJetty());

    def setup() {
        go "http://localhost:${server.port}"
    }

    def "indexを表示する"() {
        expect:
        title == "JettyServerRuleIndexPage"
    }

    def "テキストの内容で選り分ける_HtmlUnit"() {
        expect:
        $("section.classA div", text: ~/.*ふが.*/).$("p").text() == "取りたいやつ"
    }

    def "テキストの内容で選り分ける"() {
        expect:
        $("section.classA div").has("span", text: ~/.*ふが.*/).$("p").text() == "取りたいやつ"
    }

    def "表示されているかで選り分ける_HtmlUnit"() {
        expect:
        $("section.classB div").findAll { !it.attr("style").contains("display:none") }.$("p").text() == "取りたいやつ"
    }

    def "表示されているかで選り分ける_FireFox"() {
        expect:
        $("section.classB div").findAll { it.displayed }.$("p").text() == "取りたいやつ"
    }
}
