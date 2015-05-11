package forms

import geb.spock.GebSpec

class IndexSpec extends GebSpec {

    def setup() {
        go this.class.getResource("index.html").toString()
    }

    def "適当にフォーム埋めてsubmitして戻ってくる"() {
        given:
        def form = $("form")
        form.textForm = "TEXT-FIELD"
        form.$(type: "password").value("PASSWORD-PASSWORD")
        form.find(name: "selectForm").value "ccc"
        form.radioForm = "y"

        when:
        form.$("input", type: "submit").click()
        then:
        title == "さぶ"

        when:
        $("a").click()
        then:
        title == "ひょうし"
    }

    def "非表示を表示する、表示されるまで待つ"() {
        when:
        $("#saikaButton").click()
        $("#saika").$("button").click()
        then:
        $("#message").text() == "雑賀"

        when:
        $("#fukiageButton").click()
        waitFor { $("#fukiage").displayed }
        $("#fukiage").$("button").click()
        then:
        $("#message").text() == "吹上"
    }
}
