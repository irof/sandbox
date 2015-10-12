import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * @author irof
 */
public class Coins {
    private final List<Integer> availableCoins = Arrays.asList(500, 100, 50, 10);

    private final Collection<Integer> coins;

    public Coins(Collection<Integer> coins) {
        if (coins == null || coins.size() <= 0 || coins.size() >= 101) {
            throw new IllegalArgumentException();
        }
        this.coins = coins;
    }

    public Coins(int amount) {
        List<Integer> coins = new ArrayList<>();
        for (Integer coin : availableCoins) {
            while (amount >= coin) {
                coins.add(coin);
                amount -= coin;
            }
        }
        this.coins = coins;
    }

    public Collection<Integer> getCoins() {
        return coins;
    }

    public Collection<Integer> availables() {
        return coins.stream()
                .filter(availableCoins::contains)
                .collect(toList());
    }

    public int amount() {
        return availables().stream().mapToInt(c -> c).sum();
    }

    public Collection<Integer> unavailables() {
        Predicate<Integer> contains = availableCoins::contains;
        return coins.stream()
                .filter(contains.negate())
                .collect(toList());
    }
}
