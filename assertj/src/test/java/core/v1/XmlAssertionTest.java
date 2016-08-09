package core.v1;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * XMLとしての検証。
 *
 * XPATHとかで見るような豪華なものではなく、単にタグ間の空白を無視する程度。
 * 内部では、単純にDOMで読んでStringに書き戻して比較しています。
 *
 * @author irof
 * @version 1.4.0
 * @see org.assertj.core.util.xml.XmlStringPrettyFormatter
 */
public class XmlAssertionTest {

    @Test
    public void test() throws Exception {
        String actual = "<TAG1> <TAG2>value</TAG2>   </TAG1>";

        assertThat(actual)
                .isXmlEqualTo("<TAG1><TAG2>value</TAG2></TAG1>");

        // XMLとしては完全一致するパターンを検証できます。
    }

    @Test(expected = AssertionError.class)
    public void xml宣言も含めて検証される() throws Exception {
        // ので、片方にのみ含まれているとエラー
        String actual = "<TAG1>value</TAG1>";

        assertThat(actual)
                .isXmlEqualTo("<?xml version='1.0' ?><TAG1>value</TAG1>");
    }
}
