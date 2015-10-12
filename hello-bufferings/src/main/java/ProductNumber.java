/**
 * @author irof
 */
public class ProductNumber {

    private final int number;

    public ProductNumber(int number) {
        if (number <= 0 || number >= 11) {
            throw new IllegalArgumentException("存在しない商品番号です");
        }
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
