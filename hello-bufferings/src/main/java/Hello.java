import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author irof
 */
public class Hello {

    private static final Log LOG = LogFactory.getLog(Hello.class);
    private Dao dao = new Dao();

    private final List<Integer> availableCoins = Arrays.asList(500, 100, 50, 10);

    public Output execute(Input in) {
        try {
            validate(in);
            List<Integer> payBack = new ArrayList<>();
            int amount = calculateAvailableAmount(in, payBack);

            Product product = dao.findById(in.getSelected());
            if (product == null || product.getPrice() > amount) {
                return cancel(in);
            }
            Output output = new Output();
            output.setProduct(product);
            amount -= product.getPrice();

            calculatePayBack(payBack, amount);
            output.setCoins(payBack);
            return output;
        } catch (Exception e) {
            LOG.error(e);
            return cancel(in);
        }
    }

    private void calculatePayBack(List<Integer> payBack, int amount) {
        for (Integer coin : availableCoins) {
            while (amount >= coin) {
                payBack.add(coin);
                amount -= coin;
            }
        }
    }

    private Output cancel(Input in) {
        Output output = new Output();
        output.setCoins(in.getCoins());
        output.setProduct(null);
        return output;
    }

    private int calculateAvailableAmount(Input in, List<Integer> coins2) {
        in.getCoins().stream()
                .filter(coin -> coin == 1 || coin == 5)
                .forEach(coins2::add);
        return in.getCoins().stream()
                .filter(availableCoins::contains)
                .mapToInt(coins -> coins)
                .sum();
    }

    private void validate(Input in) {
        if (in.getSelected() == null || in.getSelected() <= 0 || in.getSelected() >= 11) {
            throw new IllegalArgumentException();
        } else if (in.getCoins() == null || in.getCoins().size() <= 0 || in.getCoins().size() >= 101) {
            throw new IllegalArgumentException();
        }
    }
}
