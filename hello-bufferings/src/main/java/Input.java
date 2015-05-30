import java.util.Collection;

/**
 * @author irof
 */
public class Input {
    private final Integer selected;
    private final Collection<Integer> coins;

    public Input(Integer selected, Collection<Integer> coins) {
        this.selected = selected;
        this.coins = coins;
    }

    public Integer getSelected() {
        return selected;
    }

    public Collection<Integer> getCoins() {
        return coins;
    }
}
