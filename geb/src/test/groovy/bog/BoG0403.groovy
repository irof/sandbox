package bog

import geb.spock.GebSpec

/**
 * 4.3 Traversing
 */
class BoG0403 extends GebSpec {

    def setup() {
        go this.class.getResource("index.html").toString()
    }

    def "select content around p.d"() {
        expect:
        with($("#id0403")) {
            $("p.d").previous().text() == "foo"
            $("p.e").prevAll()*.text() == ["foo", "bar"]

            $("p.d").next().text() == "baz"
            $(".b").next().text() == "piyo"
            $("p.c").nextAll()*.text() == ["bar", "baz", "qux"]

            $("p.c").parent().text().startsWith("fuga")

            $("p.d").siblings()*.text() == ["foo", "baz", "qux"]
            $(".a .b").children().filter("p")*.text() == ["foo", "bar", "baz", "qux", "foobaz"]
        }
    }

    def "引数付きのTraversing"() {
        expect:
        $("#id0403 div").next(class: 'y').text() == "fugafuga"

        $("#id0403 p").parent(".b").$("span").text() == "fooqux"

        // pを含む
        with($("p", id:"id0403z").closest(".a")) {
            tag() == "div"
            attr("class") == "a"
            attr("id") == "id0403x"
        }
    }

    def "nextUntil"() {
        expect:
        $("#id0403 .a").first().nextUntil(".a")*.text() == ['hogehoge', 'fugafuga', 'piyopiyo']

        and: "見つからなかったらsiblingsの最後まで取れる"
        $("#id0403 .a .b .d").nextUntil("#id0403x")*.text() == ['baz', 'qux']
        $("#id0403 .a .b .d").nextUntil("xxxx")*.text() == ['baz', 'qux']
    }
}
