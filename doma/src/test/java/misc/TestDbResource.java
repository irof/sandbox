package misc;

import org.junit.rules.ExternalResource;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.tx.TransactionManager;

public class TestDbResource extends ExternalResource {

    private final Runnable create;
    private final Runnable drop;
    private Config appConfig;

    public TestDbResource(Runnable create, Runnable drop, Config appConfig) {
        this.create = create;
        this.drop = drop;
        this.appConfig = appConfig;
    }

    @Override
    protected void before() throws Throwable {
        TransactionManager tm = appConfig.getTransactionManager();
        tm.required(create);
    }

    @Override
    protected void after() {
        appConfig.getTransactionManager().required(drop);
    }
}
