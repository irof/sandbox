import java.util.Arrays;
import java.util.List;

/**
 * @author irof
 */
public class Dao {
    public static Product 百円のアレ = new Product(100);
    public static Product 百円のソレ = new Product(100);
    public static Product 百円のコレ = new Product(100);
    public static Product 百二十円のアレ = new Product(120);
    public static Product 百三十円のアレ = new Product(130);
    public static Product 百五十円のアレ = new Product(150);
    public static Product 百五十円のソレ = new Product(150);
    public static Product 百五十円のコレ = new Product(150);
    public static Product 百六十円のアレ = new Product(160);
    public static Product 二百十円のアレ = new Product(210);

    List<Product> list = Arrays.asList(
            百円のアレ,
            百円のソレ,
            百円のコレ,
            百二十円のアレ,
            百三十円のアレ,
            百五十円のアレ,
            百五十円のソレ,
            百五十円のコレ,
            百六十円のアレ,
            二百十円のアレ
    );

    public Product findById(ProductNumber selected) {
        return list.get(selected.getNumber() - 1);
    }
}
