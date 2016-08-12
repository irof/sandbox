package core.v3_3;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 対象のオブジェクトから値を取り出して配列として検証するもの。
 *
 * extractingの引数はv2.3ではフィールドやプロパティ名のStringだが、
 * v3.3ではFunctionを受け取る。
 * これはメソッドリファレンスによるgetterの指定などを想定しているが、
 * 任意の関数が渡せるので結構ヤンチャなことができてしまう。
 *
 * ところでextractingはAbstractObjectAssertに実装されているのだが、
 * Stringで使用されるAbstractCharSequenceAssertはAbstractObjectAssertを継承していないため、
 * Stringの検証でextractingは使用できない。
 * （このコードを書くときに困った）
 *
 * @author irof
 * @version 3.3.0
 */
public class ExtractingTest {

    @Test
    public void 普通の使い方() throws Exception {
        BigDecimal hoge = BigDecimal.ONE;

        assertThat(hoge)
                .extracting(BigDecimal::scale, BigDecimal::intValue)
                .containsExactly(0, 1);
    }

    @Test
    public void ヤンチャな使い方() throws Exception {
        BigDecimal hoge = BigDecimal.ONE;

        assertThat(hoge)
                .extracting(
                        value -> value.multiply(BigDecimal.TEN).intValue(),
                        value -> "全く関係ない処理")
                .containsExactly(10, "全く関係ない処理");
        // できなくはないけど、やればやるほどテストが意味不明なものになる。
        // 自由度が高いってことだけ知っておけばいいんじゃないかな。
    }
}
