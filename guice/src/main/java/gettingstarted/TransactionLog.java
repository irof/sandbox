package gettingstarted;

/**
 * @author irof
 */
public interface TransactionLog {
    void of(PizzaOrder order, CreditCard creditCard);
}
