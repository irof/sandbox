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
        Result result = new Result();
        List<Integer> coins2 = new ArrayList<>();
        int sum = 0;
        try {
            if (in.getSelected() != null && in.getSelected() > 0 && in.getSelected() < 11) {
                if (in.getCoins() != null && in.getCoins().size() > 0 && in.getCoins().size() < 101) {
                    sum = 0;
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

                    Product product = dao.findById(in.getSelected());
                    if (product == null) {
                        result = new Result();
                        result.setCoins(in.getCoins());
                        result.setProduct(null);
                    } else if (product.getPrice() < sum) {
                        result.setProduct(product);
                        sum -= product.getPrice();

                        List<Integer> coins3 = new ArrayList<>();
                        coins3.add(500);
                        coins3.add(100);
                        coins3.add(50);
                        coins3.add(10);
                        for (Integer coin : coins3) {
                            while (sum > coin) {
                                coins2.add(coin);
                                sum -= coin;
                            }
                        }
                        result.setCoins(coins2);
                    } else {
                        result = new Result();
                        result.setCoins(in.getCoins());
                        result.setProduct(null);
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            LOG.error(e);
            result = new Result();
            result.setCoins(in.getCoins());
            result.setProduct(null);
        }

        return result;
    }
}
