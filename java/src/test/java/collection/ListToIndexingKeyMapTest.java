package collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * オブジェクトのリストをインデックスつきの項目名でMapに詰める
 *
 * @author irof
 */
public class ListToIndexingKeyMapTest {

    List<TargetObject> objects = Arrays.asList(
            new TargetObject("a1", "a2"),
            new TargetObject("b1", "b2"));

    @Test
    public void flatMapしてentryをcollectしてみる() throws Exception {
        Map<String, String> result = objects.stream()
                .flatMap(t -> {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("key." + objects.indexOf(t) + ".hoge", t.hoge);
                    map.put("key." + objects.indexOf(t) + ".fuga", t.fuga);
                    return map.entrySet().stream();
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        assertThat(result).containsOnly(
                entry("key.0.hoge", "a1"),
                entry("key.0.fuga", "a2"),
                entry("key.1.hoge", "b1"),
                entry("key.1.fuga", "b2"));
    }

    @Test
    public void いきなりcollectでいい気がした() throws Exception {
        HashMap<String, String> result = objects.stream()
                .collect(HashMap::new, (map, t) -> {
                    map.put("key." + objects.indexOf(t) + ".hoge", t.hoge);
                    map.put("key." + objects.indexOf(t) + ".fuga", t.fuga);
                }, HashMap::putAll);

        assertThat(result).containsOnly(
                entry("key.0.hoge", "a1"),
                entry("key.0.fuga", "a2"),
                entry("key.1.hoge", "b1"),
                entry("key.1.fuga", "b2"));
    }


    /**
     * 対象のオブジェクト
     */
    static class TargetObject {
        TargetObject(String hoge, String fuga) {
            this.hoge = hoge;
            this.fuga = fuga;
        }

        String hoge;
        String fuga;
    }
}
