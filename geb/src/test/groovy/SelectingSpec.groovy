import geb.spock.GebSpec

class SelectingSpec extends GebSpec {

    def setup() {
        go ClassLoader.getSystemResource("selecting.html").toString()
    }

    /**
     * HtmlUnitだとタグの中にテキスト全部入っているから取れてしまう。
     */
    def "テキストの内容で選り分ける_HtmlUnitOnly"() {
        expect:
        $("section.selectByText div", text: ~/.*ふが.*/).$("p").text() == "取りたいやつ"
    }

    def "テキストの内容で選り分ける"() {
        expect:
        $("section.selectByText div").has("span", text: ~/.*ふが.*/).$("p").text() == "取りたいやつ"
    }

    /**
     * HtmlUnitだとjavascriptが有効になっていないととれない。
     * {@link org.openqa.selenium.htmlunit.HtmlUnitWebElement#isDisplayed()}が
     * {@link org.openqa.selenium.htmlunit.HtmlUnitDriver#isJavascriptEnabled()}を見て、
     * <code>false</code>だと常に<code>true</code>を返しよる。
     */
    def "表示されているかで選り分ける"() {
        expect:
        $("section.selectDisplayed div").findAll { it.displayed }.$("p").text() == "取りたいやつ"
    }
}
