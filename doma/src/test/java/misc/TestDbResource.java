package misc;

import org.junit.rules.ExternalResource;
import org.seasar.doma.jdbc.tx.TransactionManager;

import java.util.function.Supplier;

public class TestDbResource extends ExternalResource {

    private final Runnable create;
    private final Runnable drop;
    private final Supplier<TransactionManager> tm;

    public TestDbResource(Runnable create, Runnable drop, Supplier<TransactionManager> tm) {
        this.create = create;
        this.drop = drop;
        this.tm = tm;
    }

    @Override
    protected void before() throws Throwable {
        tm.get().required(create);
    }

    @Override
    protected void after() {
        tm.get().required(drop);
    }
}
