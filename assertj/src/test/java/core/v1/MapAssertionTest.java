package core.v1;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * MapをEntry単位に有無を検証できる。
 *
 * @author irof
 * @version 1.2.0
 */
public class MapAssertionTest {

    @Test
    public void test() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("hoge", "HHH");
        map.put("fuga", "FFF");

        assertThat(map)
                .containsEntry("hoge", "HHH")
                .doesNotContainEntry("fuga", "fff")
                // containsOnly/containsExactlyは 1.5.0 で追加
                .containsOnly(entry("fuga", "FFF"), entry("hoge", "HHH"))
                .containsExactly(entry("hoge", "HHH"), entry("fuga", "FFF"))
        ;

        // key,valueそれぞれがequalsで比較される。
        // 柔軟な検証は対応してない感じだけれど、そういうのはmatchesとかを使えばよい。
    }
}
