package core.v3_5;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link java.util.function.Predicate} の検証。
 *
 * Predicateはfilterなどの条件として使用され、主にlambdaで記述される。
 * そのlambdaを検証したい機会もあるかもしれない。
 *
 * @author iro
 * @version assertj-core:3.5.0
 * @see <a href="httphttp://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.5.0-predicate">Add Predicate assertions</a>
 */
public class PredicateAssertionTest {

    @Test
    public void test() throws Exception {
        Predicate<String> length4 = value -> value.length() == 4;

        // Predicate の結果がtrue/falseのどちらになるかをaccept/rejectで検証する。
        // パラメーターライズなテストができると思えば良いが、一つでも検証エラーがあればそこで検証は止まる。
        // つまり複数エラーがあっても、検証結果には一つしか通知されない。
        assertThat(length4)
                .accepts("hoge", "fuga", "piyo")
                .rejects("foo", "bar", "buz")
                // xxxAllは引数にIterableを受けるだけ。あまり出番はなさそう。
                .acceptsAll(Arrays.asList("hoge", "fuga"))
                .rejectsAll(Arrays.asList("foo", "bar"));
    }

    @Test
    public void 例外を送出するPredicateの検証() throws Exception {
        // 例外に対する特別なメソッドはなさそう。
        // 現在はこうするしかないかも。

        Predicate<String> throwsException = value -> {
            throw new RuntimeException("test exception");
        };

        assertThatThrownBy(() -> {
            throwsException.test("hoge");
        }).hasMessage("test exception");
    }
}
