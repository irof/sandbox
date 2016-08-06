package core.v3_5;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.StringAssert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集合の個々の要素に対する検証をチェーンできるようにするもの。
 *
 * Iterableの各要素へのナビゲートと、IterableおよびMapのサイズへのナビゲートと戻しが追加された。
 * この機能を使わないとAssertを1メソッドで完結させられない。そこにモチベーションがあればって感じ。
 *
 * @author irof
 * @version 2.5.0 / 3.5.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-2.5.0-navigational-assertions-element">Navigate and perform assertions on an Iterable's elements</a>
 */
public class NavigateAndPerformAssertions {

    @Test
    public void Iterableの要素に対する検証への切り替えができる() throws Exception {
        List<String> list = Arrays.asList("Hoge", "Fuga", "Piyo");

        Assertions.assertThat(list, StringAssert.class)
                .first()
                .startsWith("Ho");

        // 第二引数に取り出した要素に使用したいAssertを指定しないとObjectAssertになり、最低限の検証しか行えない。
        Assertions.assertThat(list)
                .last()
                .isEqualTo("Piyo");

        // 出番があるとしたら、リストに対する何かしらの検証を行ったあと、
        // 続けて要素のうち代表に対する検証を行いたい場合かな。
        Assertions.assertThat(list, StringAssert.class)
                .contains("Hoge")
                .element(1)
                .endsWith("uga");
    }

    @Test
    public void Mapのサイズに対する検証への切り替えができる() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("a", "hoge");
        map.put("b", "fuga");
        map.put("c", "piyo");

        Assertions.assertThat(map)
                // hasSizeだと一致の検証しかできない。
                .hasSize(3)
                // size()でMapSizeAssertに切り替えれば、サイズの詳しい検証が行える。
                // MapSizeAssertはIntegerAssertを継承しているので、数値に対する一通りの検証が可能。
                // MapSizeAssertで追加されているのはMapAssertに戻るためのreturnToMapメソッドのみ。
                .size()
                .isEqualTo(3)
                .isGreaterThan(0)
                .isLessThan(10)
                // 検証が終わったらMapに戻って
                .returnToMap()
                // 他の検証を続けたければ繋げる。
                .containsValue("fuga");

        // first/last/elementのようなものは追加されていない。
        // entrySetに対して行えば可能だが、Map.Entryをアサーションするのも面倒な感じがする。
        // と思いつつ書いたら、Map.EntryのAssertがないのでObjectAssertになって超微妙。
        Assertions.assertThat(map.entrySet())
                .last()
                .matches(entry -> entry.getValue().length() == 4);
    }
}
