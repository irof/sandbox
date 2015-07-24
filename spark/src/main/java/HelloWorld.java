import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

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

            Arrays.stream(req.getClass().getDeclaredMethods())
                    .filter(method -> method.getParameterCount() == 0)
                    .forEach(method -> {
                        try {
                            method.setAccessible(true);
                            LOG.info("request.{}() : {}", method.getName(), method.invoke(req));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });

            return req.toString();
        });
    }
}
