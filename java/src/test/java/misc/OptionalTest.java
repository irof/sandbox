package misc;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Optional;

import static matchers.IsOptional.isEmpty;
import static matchers.IsOptional.isValue;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
@RunWith(Enclosed.class)
public class OptionalTest {

    public static class FactoryMethod {

        @Test
        public void of() throws Exception {
            Optional<String> sut = Optional.of("hoge");
            assertThat(sut, isValue("hoge"));
        }

        @Test(expected = NullPointerException.class)
        public void of_nullを渡すと例外() throws Exception {
            // nullでない値をOptionalにしたい場合がどれだけあるか？
            // 例えば、インタフェースを合わせるためならわからなくはない。
            // nullでないことがわかっているときに ofNullable を使ってしまうと
            // nullに気づくのが遅れるので、それよりはいいのかも。
            Optional.of(null);
        }

        @Test
        public void empty() throws Exception {
            Optional<?> sut = Optional.empty();
            assertThat(sut, isEmpty());
        }

        @Test
        public void ofNullable() throws Exception {
            // nullでない限りはofと同じ挙動
            Optional<String> sut = Optional.ofNullable("hoge");
            assertThat(sut, isValue("hoge"));
        }

        @Test
        public void ofNullable_nullを渡すとempty() throws Exception {
            // nullのときはemptyと同じ挙動
            Optional<Object> sut = Optional.ofNullable(null);
            assertThat(sut, isEmpty());

            // nullを渡すとemptyと同じインスタンスが使われる
            // ……のは実装の詳細で、たまたま。
            // だから、こんなコードを書いてはいけない。
            // assertTrue(sut == Optional.empty());
        }
    }
}
