package gettingstarted.dao;

import gettingstarted.AppConfig;
import org.junit.rules.ExternalResource;
import org.seasar.doma.jdbc.tx.TransactionManager;

class TestDbResource extends ExternalResource {

    AppDao dao = new AppDaoImpl();

    @Override
    protected void before() throws Throwable {
        TransactionManager tm = AppConfig.singleton().getTransactionManager();
        tm.required(dao::create);
    }

    @Override
    protected void after() {
        AppConfig.singleton().getTransactionManager().required(dao::drop);
    }
}
