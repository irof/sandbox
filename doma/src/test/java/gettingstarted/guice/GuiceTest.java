package gettingstarted.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import gettingstarted.dao.AppDao;
import gettingstarted.dao.AppDaoImpl;
import gettingstarted.dao.EmployeeDao;
import gettingstarted.dao.EmployeeDaoImpl;
import gettingstarted.entity.Employee;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.inject.Inject;
import javax.inject.Named;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(GuiceTestRunner.class)
public class GuiceTest {

    @GuiceTestRunner.Module
    public static Module envModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(Dialect.class).to(H2Dialect.class);
                bind(LocalTransactionDataSource.class).toInstance(
                        new LocalTransactionDataSource("jdbc:h2:mem:tutorial;DB_CLOSE_DELAY=-1", "sa", null)
                );
                bind(Config.class).annotatedWith(Names.named("config")).to(GuiceAppConfig.class);
            }
        };
    }

    @GuiceTestRunner.Module
    public static Module daoModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(AppDao.class).to(AppDaoImpl.class);
                bind(EmployeeDao.class).to(EmployeeDaoImpl.class);
            }
        };
    }

    @Inject
    @Named("config")
    Config config;

    @Inject
    AppDao appDao;

    @Inject
    EmployeeDao dao;

    @Rule
    public ExternalResource dbResource = new ExternalResource() {

        @Override
        protected void before() throws Throwable {
            TransactionManager tm = config.getTransactionManager();
            tm.required(appDao::create);
        }

        @Override
        protected void after() {
            config.getTransactionManager().required(appDao::drop);
        }
    };

    @Test
    public void testSelectById() throws Exception {

        config.getTransactionManager().required(() -> {
            Employee employee = dao.selectById(1);
            assertThat(employee.name, is("ALLEN"));
        });
    }
}
