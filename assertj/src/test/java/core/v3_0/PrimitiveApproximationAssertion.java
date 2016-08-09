package core.v3_0;

import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * int,long,short,byteに対する範囲検証が追加された。
 *
 * `Assertions.within`メソッドのstatic importで記述する形式になっているが、
 * AssertJを使うならstatic importはあまり使いたくないので、ちょい微妙。
 *
 * doubleやfloat、プリミティブではないBigDecimalに対する範囲検証は 1.7.0 で追加されたもの。
 * （JUnitでdeltaを指定しないdoubleのassertEqualsが必ず失敗するのはよく知られた話）
 * それの他のプリミティブへの適用って感じだけど、実際の利用用途は違いそう。
 *
 * というか範囲での検証ってしたいと思ったことがほとんどない。
 *
 * @author irof
 * @version 3.0.0
 * @see <a href="http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-3.0.0-primitive-approximation-assertions">Extends primitive approximation assertions to int, long, short and byte</a>
 */
public class PrimitiveApproximationAssertion {

    @Test
    public void intに対する範囲内の検証() throws Exception {
        int i = 45;

        assertThat(i)
                // 具体値で範囲を指定する
                .isCloseTo(40, Offset.offset(5))
                .isCloseTo(50, within(5))
                // パーセンテージで範囲を指定する
                .isCloseTo(40, Percentage.withPercentage(12.5))
                .isCloseTo(50, withinPercentage(10));
    }
}
