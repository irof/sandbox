package misc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author irof
 */
public interface KeyCollect {

    Collection<Output> collectByKey(List<Input> data);

    /**
     * @return Streamを使った実装
     */
    @FactoryMethod
    static KeyCollect withStream() {
        return data -> {
            Map<String, List<Input>> collected = data.stream()
                    .collect(Collectors.groupingBy(Input::getKey));
            return collected.entrySet().stream()
                    .map((entry) -> new Output(
                            entry.getKey(),
                            entry.getValue().stream()
                                    .mapToInt(Input::getValue).sum()))
                    .collect(Collectors.toList());
        };
    }

    /**
     * @return 昔やった実装
     */
    @FactoryMethod
    static KeyCollect legacy() {
        return data -> {
            Map<String, Output> map = new LinkedHashMap<>();
            for (Input in : data) {
                if (!map.containsKey(in.key)) {
                    map.put(in.key, new Output(in.key, 0));
                }
                map.get(in.key).value += in.value;
            }
            return new ArrayList<>(map.values());
        };
    }

    class Input {
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

    class Output {
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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface FactoryMethod {}
}
