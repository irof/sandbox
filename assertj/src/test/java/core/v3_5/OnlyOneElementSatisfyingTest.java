package core.v3_5;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * IterableやArrayの検証に追加された hasOnlyOneElementSatisfying メソッド。
 * 一体何に使うのかわからない。
 *
 * @author irof
 * @version assertj-core:3.5.0
 */
public class OnlyOneElementSatisfyingTest {

    @Test
    public void test() throws Exception {
        List<String> list = Collections.singletonList("hoge");

        assertThat(list).hasOnlyOneElementSatisfying(element ->
                assertThat(element)
                        .hasSize(4)
                        .startsWith("hog")
        );
    }

    @Test(expected = AssertionError.class)
    public void リストが2件だとエラー() throws Exception {
        List<String> list = Arrays.asList("hoge", "hoge");

        assertThat(list).hasOnlyOneElementSatisfying(element -> {
            // 何もしなくても
        });
    }
}
