package bog

import geb.spock.GebSpec
import spock.lang.Unroll

/**
 * Finding & Filtering
 */
class BoG0402 extends GebSpec {

    def setup() {
        go this.class.getResource("index.html").toString()
    }

    @Unroll
    def "select p.b"() {
        expect:
        nav.call().text() == "geb"

        where:
        // そのまま書くとその場で評価結果が返されるのでClosureに突っ込んでおく
        nav << [
                { $("p.b") },
                { $(".a .b") },
                { $(".a").$(".b") },
                { $("div").find(".b") },
                { $("div").$(".b") },
                { $("div .b") },
                { $(".b").not("div") },
        ]
    }

    @Unroll
    def "select div.b"() {
        expect:
        nav.call().tag() == "div"
        nav.call().attr("class") == "b"

        where:
        nav << [
                { $("div.b") },
                { $("div").filter(".b") },
                { $(".b").not("p") },
                { $("div.b") },
                { $("div").has("p", text: "fuga") },
                { $("div").has("input", type: "text") },
        ]

    }
}
