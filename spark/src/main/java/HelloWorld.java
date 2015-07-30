import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.get;

/**
 * @author irof
 */
public class HelloWorld {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {

        get("/hello", (req, res) -> "Hello World");

        get("/sample/status/:code", (req, res) -> {
            res.status(Integer.valueOf(req.params(":code")));
            return "HTTP " + req.params(":code");
        });

        get("/loggingRequest", (req, res) -> {
            LOG.info("request : {}", req);

            class Entry {
                private final String key;
                private final Object value;

                Entry(String key, Object value) {
                    this.key = key;
                    this.value = value == null ? "null" : value;
                }

                String getKey() {
                    return key;
                }

                Object getValue() {
                    return value.toString();
                }
            }

            Map<String, Object> collect = Arrays.stream(req.getClass().getDeclaredMethods())
                    .filter(method -> method.getParameterCount() == 0)
                    .map(method -> {
                                try {
                                    method.setAccessible(true);
                                    LOG.info("request.{}() : {}", method.getName(), method.invoke(req));
                                    return new Entry(method.getName(), method.invoke(req));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    )
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

            Gson gson = new Gson();
            return gson.toJson(collect);
        });
    }
}
