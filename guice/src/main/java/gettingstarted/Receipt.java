package gettingstarted;

/**
 * @author irof
 */
public class Receipt {
    private final PizzaOrder order;
    private final CreditCard creditCard;

    public Receipt(PizzaOrder order, CreditCard creditCard) {
        this.order = order;
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Receipt{" + "order=" + order + ", creditCard=" + creditCard + '}';
    }
}
