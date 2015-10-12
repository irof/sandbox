import java.util.Collection;

/**
 * @author irof
 */
public class Input {
    private final ProductNumber selected;
    private final Coins coins;

    public Input(ProductNumber selected, Coins coins) {
        this.selected = selected;
        this.coins = coins;
    }

    public ProductNumber getSelected() {
        return selected;
    }

    public Collection<Integer> getCoins() {
        return coins.getCoins();
    }
}
