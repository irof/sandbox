package core;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author irof
 * @version assertj-core:3.5.0
 */
public class NavigateAndPerformAssertions {

    @Test
    public void test() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "hoge");
        map.put("b", "fuga");
        map.put("c", "piyo");

        Assertions.assertThat(map)
                .hasSize(3)
                .size().isEqualTo(3)
                .returnToMap()
                .containsValue("fuga");
    }
}
