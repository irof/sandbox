package core.v3_4;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * containsExactlyInAnyOrder による順序無視の検証。
 *
 * 順序が不要で、要素の重複がある場合に今までできなかった検証ができるようになった。
 *
 * @author irof
 * @version 3.4.0
 */
public class ContainsExactlyInAnyOrderTest {

    @Test
    public void test() throws Exception {
        List<String> list = Arrays.asList("a", "b", "a", "c");

        assertThat(list)
                // 名前の通り、順番は関係ない
                .containsExactlyInAnyOrder("a", "a", "b", "c")
                .containsExactlyInAnyOrder("c", "b", "a", "a")
                // containsOnly だと重複した要素が考慮できないので、"a"が一つでもパスする
                .containsOnly("b", "c", "a")
                // contains, containsOnlyOnce, containsAll などは含まれていない要素は検証されない
                .containsAll(Arrays.asList("b", "c"));
    }
}
