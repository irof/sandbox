package core.v3_5;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 集合の個々の要素に対する検証をチェーンできるようにするもの。
 *
 * Iterableの各要素と、IterableおよびMapのサイズへのナビゲートが追加された。
 *
 * @author irof
 * @version assertj-core:3.5.0
 */
public class NavigateAndPerformAssertions {

    @Test
    public void MapSizeAssertの素振り() throws Exception {
        Map<String, Object> map = new HashMap<>();
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
    }
}
