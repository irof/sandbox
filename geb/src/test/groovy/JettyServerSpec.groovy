import com.github.mjeanroy.junit.servers.jetty.EmbeddedJetty
import com.github.mjeanroy.junit.servers.rules.JettyServerRule
import geb.spock.GebSpec
import org.junit.ClassRule
import spock.lang.Shared

class JettyServerSpec extends GebSpec {

    @ClassRule @Shared
    JettyServerRule server = new JettyServerRule(new EmbeddedJetty());

    def "indexを表示する"() {
        go "http://localhost:${server.port}"

        expect:
        title == "JettyServerRuleIndexPage"
    }
}
