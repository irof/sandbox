import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
@RunWith(Enclosed.class)
public class HelloTest {

    public static class Normal {
        private Hello hello = new Hello();

        @Test(expected = NullPointerException.class)
        public void 入力がnullのときはぬるぽ() throws Exception {
            hello.execute(null);
        }

        @Test
        public void ぴったりもの買い物をした() throws Exception {
            Collection<Integer> coins = Collections.singletonList(100);
            Result result = hello.execute(new Input(1, coins));

            assertThat(result.getProduct(), is(Dao.百円のアレ));
            assertThat(result.getCoins(), is(empty()));
        }

        @Test
        public void お釣りが出る買い物をした() throws Exception {
            Collection<Integer> coins = Arrays.asList(500, 50, 100);
            Result result = hello.execute(new Input(10, coins));

            assertThat(result.getProduct(), is(Dao.二百十円のアレ));
            assertThat(result.getCoins(), is(containsInAnyOrder(100, 100, 100, 100, 10, 10, 10, 10)));
        }
    }

    @RunWith(Parameterized.class)
    public static class 商品選択NGパターン {
        private Hello hello = new Hello();
        private static final Collection<Integer> COINS = Collections.singletonList(500);

        @Parameterized.Parameters
        public static Iterable<Input> param() {
            return Arrays.asList(
                    new Input(null, COINS),
                    // 範囲外の選択 1-10 らしい
                    new Input(0, COINS),
                    new Input(11, COINS)
            );
        }

        @Parameterized.Parameter
        public Input in;

        @Test
        public void test() throws Exception {
            Result result = hello.execute(in);

            assertThat(result.getProduct(), is(nullValue()));
            assertThat(result.getCoins(), is(COINS));
        }
    }
}
