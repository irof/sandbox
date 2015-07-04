package gettingstarted;

import javax.inject.Inject;

/**
 * @author irof
 */
public class BillingService {
    private final CreditCardProcessor processor;
    private final TransactionLog transactionLog;

    @Inject
    BillingService(CreditCardProcessor processor, TransactionLog transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }

    Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
        transactionLog.of(order, creditCard);
        processor.settlement(creditCard, order);
        return new Receipt(order, creditCard);
    }
}
