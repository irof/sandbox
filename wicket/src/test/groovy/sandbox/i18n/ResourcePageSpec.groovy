package sandbox.i18n

import org.apache.wicket.util.tester.WicketTester
import sandbox.WicketApplication
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author irof
 */
class ResourcePageSpec extends Specification {

    WicketTester tester

    def setup() {
        tester = new WicketTester(new WicketApplication())
        tester.startPage(ResourcePage.class)
    }

    @Unroll
    def "出力確認 id=#id"() {
        expect:
        tester.getTagById(id).value == value

        where:
        id             | value
        // wicket:message のデフォルトはエスケープされない
        "m.p1"         | "<b>BOLD</b>"
        "m.p2"         | "<b>BOLD</b>"
        "m.p3"         | "&lt;b&gt;BOLD&lt;/b&gt;"

        // wicket:message でescapeを指定
        "m.p1.esc"     | "&lt;b&gt;BOLD&lt;/b&gt;"
        "m.p2.esc"     | "&lt;b&gt;BOLD&lt;/b&gt;"
        "m.p3.esc"     | "&amp;lt;b&amp;gt;BOLD&amp;lt;/b&amp;gt;"

        // Label のデフォルトはエスケープされる
        "m.l.p1"       | "&lt;b&gt;BOLD&lt;/b&gt;"
        "m.l.p2"       | "&lt;b&gt;BOLD&lt;/b&gt;"
        "m.l.p3"       | "&amp;lt;b&amp;gt;BOLD&amp;lt;/b&amp;gt;"

        // Label でエスケープfalseを指定
        "m.l.p1.unesc" | "<b>BOLD</b>"
        "m.l.p2.unesc" | "<b>BOLD</b>"
        "m.l.p3.unesc" | "&lt;b&gt;BOLD&lt;/b&gt;"
    }

}
