import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

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
        public void 投入金額が足りなかった() throws Exception {
            Collection<Integer> coins = Collections.singletonList(50);
            Output output = hello.execute(new Input(new ProductNumber(1), new Coins(coins)));

            assertThat(output.getProduct(), is(nullValue()));
            assertThat(output.getCoins(), is(Collections.singletonList(50)));
        }

        @Test
        public void ぴったりもの買い物をした() throws Exception {
            Collection<Integer> coins = Collections.singletonList(100);
            Output output = hello.execute(new Input(new ProductNumber(1), new Coins(coins)));

            assertThat(output.getProduct(), is(Dao.百円のアレ));
            assertThat(output.getCoins(), is(empty()));
        }

        @Test
        public void お釣りが出る買い物をした() throws Exception {
            Collection<Integer> coins = Arrays.asList(500, 50, 100);
            Output output = hello.execute(new Input(new ProductNumber(10), new Coins(coins)));

            assertThat(output.getProduct(), is(Dao.二百十円のアレ));
            assertThat(output.getCoins(), is(containsInAnyOrder(100, 100, 100, 100, 10, 10, 10, 10)));
        }
    }

    public static class 商品番号の境界値 {

        @Test(expected = IllegalArgumentException.class)
        public void 番号0はNG() throws Exception {
            new ProductNumber(0);
        }

        @Test
        public void 番号1はOK() throws Exception {
            new ProductNumber(1);
        }

        @Test
        public void 番号10はOK() throws Exception {
            new ProductNumber(10);
        }

        @Test(expected = IllegalArgumentException.class)
        public void 番号11はNG() throws Exception {
            new ProductNumber(11);
        }
    }
}
