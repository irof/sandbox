import static spark.Spark.get;

/**
 * @author irof
 */
public class HelloWorld {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
