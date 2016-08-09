package core.v1;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 特定フィールドだけを使用したコレクションの検証。
 *
 * ElementComparator自体は初期の頃からある。
 * 1.6.0で特定フィールドを使用する usingElementComparatorOnFields が追加された。
 *
 * @author irof
 * @version 1.6.0
 */
public class ElementComparatorTest {

    @Test
    public void test() throws Exception {
        List<Hoge> list = Arrays.asList(new Hoge("a", "b"), new Hoge("a", "c"), new Hoge("a", "d"));

        assertThat(list)
                // barフィールドだけを使用して検証する
                .usingElementComparatorOnFields("bar")
                .contains(new Hoge("x", "b"))

                // 他のElementComparatorたち
                .usingElementComparatorIgnoringFields("bar")
                .containsOnly(new Hoge("a", "x"))
                .usingFieldByFieldElementComparator()
                .contains(new Hoge("a", "b"));
    }

    private static class Hoge {
        String foo;
        String bar;

        Hoge(String foo, String bar) {
            this.foo = foo;
            this.bar = bar;
        }
    }
}
