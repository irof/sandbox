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

    public Result execute(Input in) {
        try {
            validate(in);
            List<Integer> payBack = new ArrayList<>();
            int amount = calculateAvailableAmount(in, payBack);

            Product product = dao.findById(in.getSelected());
            if (product == null || product.getPrice() > amount) {
                return cancel(in);
            }
            Result result = new Result();
            result.setProduct(product);
            amount -= product.getPrice();

            calculatePayBack(payBack, amount);
            result.setCoins(payBack);
            return result;
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

    private Result cancel(Input in) {
        Result result = new Result();
        result.setCoins(in.getCoins());
        result.setProduct(null);
        return result;
    }

    private int calculateAvailableAmount(Input in, List<Integer> coins2) {
        int sum = 0;
        for (Integer coin : in.getCoins()) {
            if (coin == 1 || coin == 5) {
                coins2.add(coin);
            } else if (availableCoins.contains(coin)) {
                sum += coin;
            }
        }
        return sum;
    }

    private void validate(Input in) {
        if (in.getSelected() == null || in.getSelected() <= 0 || in.getSelected() >= 11) {
            throw new IllegalArgumentException();
        } else if (in.getCoins() == null || in.getCoins().size() <= 0 || in.getCoins().size() >= 101) {
            throw new IllegalArgumentException();
        }
    }
}
