package core.v3_0;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Optionalの検証。
 *
 * 3.2.0:
 * containsのエイリアスのhasValueが追加された。
 * インスタンスの一致を見るcontainsSameが追加された。
 *
 * 3.3.0:
 * lambdaならではとも言える hasValueSatisfying が追加された。
 *
 * 3.4.0:
 * isEmptyのエイリアスのisNotPresentが追加された。
 * isPresentのエイリアスのisNotEmptyが追加された。
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
        // lambdaの引数はvalueじゃなく assertThat(value) の結果でよいのでは、という気もする。
    }

    @Test
    public void containsSame() throws Exception {
        // 3.2で追加されたcontainsSameを使ってみたー
        Optional<String> opt = Optional.of("hoge");

        // 文字列リテラル同士は同じインスタンスなので通る
        assertThat(opt).containsSame("hoge");

        // 同じ文字列の異なるインスタンスをつくる。
        char[] chars = {'h', 'o', 'g', 'e'};
        String hoge = new String(chars);

        // containsは同値性なので通る
        assertThat(opt).contains(hoge);

        // containsSameは同一性なのでエラーになる
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() ->
                assertThat(opt).containsSame(hoge)
        );
    }
}
