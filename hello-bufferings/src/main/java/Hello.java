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

    public Output execute(Input in) {
        try {
            int amount = in.getCoins().amount();

            Product product = dao.findById(in.getSelected());
            if (product == null || product.getPrice() > amount) {
                return cancel(in);
            }
            Output output = new Output();
            output.setProduct(product);
            amount -= product.getPrice();

            List<Integer> payBack = new ArrayList<>();
            payBack.addAll(in.getCoins().unavailables());
            payBack.addAll(new Coins(amount).getCoins());
            output.setCoins(payBack);
            return output;
        } catch (Exception e) {
            LOG.error(e);
            return cancel(in);
        }
    }

    private Output cancel(Input in) {
        Output output = new Output();
        output.setCoins(in.getCoins().getCoins());
        output.setProduct(null);
        return output;
    }
}
