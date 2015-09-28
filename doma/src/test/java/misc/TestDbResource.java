package misc;

import gettingstarted.AppConfig;
import gettingstarted.dao.AppDao;
import gettingstarted.dao.AppDaoImpl;
import org.junit.rules.ExternalResource;
import org.seasar.doma.jdbc.tx.TransactionManager;

public class TestDbResource extends ExternalResource {

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
