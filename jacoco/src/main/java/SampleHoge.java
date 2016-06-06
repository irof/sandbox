import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author irof
 */
public class SampleHoge {

    public String invoke(int i) {
        if (i > 0) {
            return "A";
        }
        return "B";
    }

    public void lambda() {
        invokeLambda(s -> {
            if (Objects.equals(s, "FUGA")) {
                return "fuga";
            }
            return "hoge";
        });
    }

    public void invokeLambda(Function<String, String> func) {
        func.apply("HOGE");
    }
}
