package gettingstarted;

/**
 * @author irof
 */
public interface CreditCardProcessor {
    void settlement(CreditCard creditCard, PizzaOrder order);
}
