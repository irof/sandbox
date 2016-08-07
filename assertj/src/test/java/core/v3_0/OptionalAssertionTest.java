package core.v3_0;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Optionalの検証。
 *
 * `isNotPresent`は`isEmpty`のエイリアス、`isNotEmpty`は`isPresent`のエイリアスとしてv3.4.0で追加された。
 * lambdaならではの `hasValueSatisfying` は、v3.4.0の`satisfies`メソッドに受け継がれていそう。
 *
 * isNotPresent/isNotEmpty は 3.4.0 で追加された（面倒なのでここで書いておく）。
 *
 * @author irof
 * @version 3.0.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.0.0-optional-assertions">Optinal assersions</a>
 */
public class OptionalAssertionTest {

    @Test
    public void 何も値が入っていない() throws Exception {
        Optional<Object> empty = Optional.empty();

        assertThat(empty)
                // isEmpty = isNotPresent
                .isNotPresent()
                .isEmpty();
    }

    @Test
    public void 何か値が入っている() throws Exception {
        Optional<String> opt = Optional.of("hoge");

        assertThat(opt)
                // isPresent = isNotEmpty
                .isPresent()
                .isNotEmpty()
                // contains = hasValue
                .contains("hoge")
                .hasValue("hoge");

        // 値に対する一致以外の検証は `hasValueSatisfying` を使用する。
        // ネストになるので少々読みづらい。
        assertThat(opt).hasValueSatisfying(value ->
                assertThat(value)
                        .startsWith("hog")
                        .endsWith("oge")
        );
    }
}
