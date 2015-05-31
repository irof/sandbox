import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author irof
 */
public class Hello {

    private static final Log LOG = LogFactory.getLog(Hello.class);
    private Dao dao = new Dao();

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

            List<Integer> coins3 = new ArrayList<>();
            coins3.add(500);
            coins3.add(100);
            coins3.add(50);
            coins3.add(10);
            for (Integer coin : coins3) {
                while (amount >= coin) {
                    payBack.add(coin);
                    amount -= coin;
                }
            }
            result.setCoins(payBack);
            return result;
        } catch (Exception e) {
            LOG.error(e);
            return cancel(in);
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
            if (coin == 1) {
                sum += 0;
                coins2.add(coin);
            } else if (coin == 5) {
                sum += 0;
                coins2.add(coin);
            } else if (coin == 10) {
                sum += 10;
            } else if (coin == 50) {
                sum += 50;
            } else if (coin == 100) {
                sum += 100;
            } else if (coin == 500) {
                sum += 500;
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
