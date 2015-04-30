import geb.spock.GebSpec
import pages.AdminPage
import pages.IndexPage

class SimpleSpec extends GebSpec {

    def setup() {
        go ClassLoader.getSystemResource("index.html").toString()
    }

    def "indexを表示する"() {
        expect:
        at IndexPage

        when:
        管理メニューリンクをクリック()
        then:
        at AdminPage
    }
}
