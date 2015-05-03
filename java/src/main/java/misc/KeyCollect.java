package misc;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author irof
 */
public class KeyCollect {

    Collection<Output> collectByCode(List<Input> data) {
        Map<String, List<Input>> collected = data.stream()
                .collect(Collectors.groupingBy(Input::getKey));
        return collected.entrySet().stream()
                .map((entry) -> new Output(
                        entry.getKey(),
                        entry.getValue().stream()
                                .mapToInt(Input::getValue).sum()))
                .collect(Collectors.toList());
    }

    static class Input {
        String key;
        String name;
        int value;

        Input(String key, String name, int value) {
            this.key = key;
            this.name = name;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }
    }

    static class Output {
        String key;
        int value;

        Output(String key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Output)) {
                return false;
            }
            Output target = (Output) obj;
            return Objects.equals(key, target.key) && value == target.value;
        }
    }
}
