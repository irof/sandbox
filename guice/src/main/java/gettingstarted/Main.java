package gettingstarted;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * https://github.com/google/guice/wiki/GettingStarted を雑にやってみたもの。
 *
 * @author irof
 */
public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BillingModule());

        BillingService instance = injector.getInstance(BillingService.class);

        Receipt receipt = instance.chargeOrder(new PizzaOrder(), new CreditCard());
        System.out.println(receipt);
    }

    public static class BillingModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(TransactionLog.class).to(DummyTransactionLog.class);
            bind(CreditCardProcessor.class).to(DummyCreditCardProcessor.class);
        }
    }

    public static class DummyCreditCardProcessor implements CreditCardProcessor {
        @Override
        public void settlement(CreditCard creditCard, PizzaOrder order) {
            System.out.printf("settlement[order: %s, card: %s]%n", order, creditCard);
        }
    }

    public static class DummyTransactionLog implements TransactionLog {

        @Override
        public void of(PizzaOrder order, CreditCard creditCard) {
            System.out.printf("TransactionLog[order: %s, card: %s]%n", order, creditCard);
        }
    }
}
