import com.github.mjeanroy.junit.servers.jetty.EmbeddedJetty
import com.github.mjeanroy.junit.servers.rules.JettyServerRule
import geb.spock.GebSpec
import org.junit.ClassRule
import pages.AdminPage
import pages.IndexPage
import spock.lang.Shared

class JettyServerSpec extends GebSpec {

    @ClassRule @Shared
    JettyServerRule server = new JettyServerRule(new EmbeddedJetty());

    def "indexを表示する"() {
        go "http://localhost:${server.port}"

        expect:
        at IndexPage

        when:
        管理メニューリンクをクリック()
        then:
        at AdminPage
    }
}
