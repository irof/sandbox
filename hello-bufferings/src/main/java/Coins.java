import java.util.Collection;

/**
 * @author irof
 */
public class Coins {
    private final Collection<Integer> coins;

    public Coins(Collection<Integer> coins) {
        if (coins == null || coins.size() <= 0 || coins.size() >= 101) {
            throw new IllegalArgumentException();
        }
        this.coins = coins;
    }

    public Collection<Integer> getCoins() {
        return coins;
    }
}
