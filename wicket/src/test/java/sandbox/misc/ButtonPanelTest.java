package sandbox.misc;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import sandbox.WicketApplication;

public class ButtonPanelTest {

    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester(new WicketApplication());
        tester.startComponentInPage(ButtonPanel.class);
    }

    @Test
    public void ボタンの確認() {
        // 大文字開始
        tester.assertLabel("form:label", "FIELD");
        tester.assertModelValue("form:upperButton", "FIELD");
        tester.assertLabel("form:lowerButton:label", "FIELD");

        // ボタンを押すよ
        FormTester form1 = tester.newFormTester("form");
        form1.submit("lowerButton");

        tester.assertLabel("form:label", "field");
        tester.assertModelValue("form:upperButton", "field");
        tester.assertLabel("form:lowerButton:label", "field");

        // ボタンを押すよ
        FormTester form2 = tester.newFormTester("form");
        form2.submit("upperButton");

        tester.assertLabel("form:label", "FIELD");
        tester.assertModelValue("form:upperButton", "FIELD");
        tester.assertLabel("form:lowerButton:label", "FIELD");
    }

    @Test
    public void AJAXボタンの確認() {
        // ボタンを押すよ
        FormTester form2 = tester.newFormTester("ajaxForm");
        form2.submit("rightButton");
        tester.assertComponentOnAjaxResponse("ajaxForm:p2");
        tester.assertLabel("ajaxForm:p2:rightLabel", "fugaa");

        // clickイベントを直接叩いてもOK
        tester.executeAjaxEvent("ajaxForm:leftButton", "click");
        tester.assertComponentOnAjaxResponse("ajaxForm:p1");
        tester.assertLabel("ajaxForm:p1:leftLabel", "hogee");

        // ajaxのtarget外でも WicketTester#assertLabel で見ると変わるので、
        // 「ajaxで内部は変わってるけど裏は変わらない」とかの検証はしづらい。
        // する必要もないだろうけどなー。
    }
}