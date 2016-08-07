package core.v3_5;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * IterableAssertに追加された謎機能flatExtraction。
 *
 * @author irof
 * @version 3.5.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.5.0-flat-extraction"></a>
 */
public class FlatExtractionTest {

    @Test
    public void flatExtraction() throws Exception {
        List<Bean> list = Arrays.asList(new Bean("HOGE", BigDecimal.ONE), new Bean("FUGA", BigDecimal.TEN));

        assertThat(list)
                .flatExtracting(Bean::getStr, Bean::getDecimal)
                .containsExactly(
                        "HOGE", BigDecimal.ONE,
                        "FUGA", BigDecimal.TEN
                );

        // 参考: 通常の extracting だと結果はTupleになる。
        // flatのほうが読み易い・・・・か？
        assertThat(list)
                .extracting(Bean::getStr, Bean::getDecimal)
                .containsExactly(
                        Tuple.tuple("HOGE", BigDecimal.ONE),
                        Tuple.tuple("FUGA", BigDecimal.TEN)
                );
    }

    private static class Bean {
        String str;
        BigDecimal decimal;

        Bean(String str, BigDecimal decimal) {
            this.str = str;
            this.decimal = decimal;
        }

        String getStr() {
            return str;
        }

        BigDecimal getDecimal() {
            return decimal;
        }
    }
}
