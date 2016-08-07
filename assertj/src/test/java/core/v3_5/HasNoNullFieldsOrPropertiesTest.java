package core.v3_5;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * フィールドがnullでないことの一括検証。
 *
 * 具体的な利用イメージは湧かないけれど、知っておくと可能性が拡がるものの気はする。
 * ただし hasNoNullFieldsOrProperties と言う名前に反して、プロパティのnullには対応していない。
 *
 * @author irof
 * @version 2.5.0 / 3.5.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-2.5.0-hasNoNullFieldsOrProperties">Add hasNoNullFieldsOrProperties/Except to Object assertions</a>
 */
public class HasNoNullFieldsOrPropertiesTest {

    @Test
    public void nullのフィールドがなければ正常() throws Exception {
        Hoge hoge = new Hoge();
        hoge.field1 = "";
        hoge.field2 = "";

        assertThat(hoge).hasNoNullFieldsOrProperties();
    }

    @Test(expected = AssertionError.class)
    public void nullのフィールドが一つでもあると検証エラーになる() throws Exception {
        Hoge hoge = new Hoge();
        hoge.field2 = "";

        assertThat(hoge).hasNoNullFieldsOrProperties();
    }

    @Test
    public void 一部を除外して判定できる() throws Exception {
        Hoge hoge = new Hoge();
        hoge.field2 = "";

        assertThat(hoge).hasNoNullFieldsOrPropertiesExcept("field1");
    }

    @Test
    public void プロパティには使えない() throws Exception {
        Fuga fuga = new Fuga();

        assertThat(fuga)
                .hasFieldOrProperty("prop")
                // これが通ってしまう
                .hasNoNullFieldsOrProperties();

        // バグじゃないかな？
    }

    private static class Hoge {
        String field1;
        String field2;
    }

    private static class Fuga {
        public String getProp() {
            return null;
        }
    }
}
