package gettingstarted.dao;

import gettingstarted.AppConfig;
import gettingstarted.entity.Employee;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.seasar.doma.jdbc.tx.TransactionManager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EmployeeDaoTest {

    @Rule
    public ExternalResource dbResource = new ExternalResource() {

        AppDao dao  = new AppDaoImpl();

        @Override
        protected void before() throws Throwable {
            TransactionManager tm = AppConfig.singleton().getTransactionManager();
            tm.required(dao::create);
        }

        @Override
        protected void after() {
            AppConfig.singleton().getTransactionManager().required(dao::drop);
        }
    };

    private EmployeeDao dao = new EmployeeDaoImpl();

    @Test
    public void testSelectById() throws Exception {
        AppConfig.singleton().getTransactionManager().required(() -> {
            Employee employee = dao.selectById(1);
            assertThat(employee.name, is("ALLEN"));
        });
    }
}
