package local

import geb.spock.GebSpec

/**
 * @author irof
 */
class LocalHtmlSpec extends GebSpec {

    def openLocalHtml() {
        go this.class.getResource("index.html").toString()
        expect:
        title =~ "local.+index"
    }
}
