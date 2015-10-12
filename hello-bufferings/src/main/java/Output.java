import java.util.Collection;

/**
 * @author irof
 */
public class Output {
    private Collection<Integer> coins;
    private Product product;

    public void setCoins(Collection<Integer> coins) {
        this.coins = coins;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Collection<Integer> getCoins() {
        return coins;
    }

    public Product getProduct() {
        return product;
    }
}
