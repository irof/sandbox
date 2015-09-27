package gettingstarted.dao;

import gettingstarted.AppConfig;
import gettingstarted.entity.Employee;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EmployeeDaoTest {

    @Rule
    public ExternalResource dbResource = new ExternalResource() {

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
    };

    private EmployeeDao dao = new EmployeeDaoImpl();

    @Test
    public void testSelectById() throws Exception {
        AppConfig.singleton().getTransactionManager().required(() -> {
            Employee employee = dao.selectById(1);
            assertThat(employee.name, is("ALLEN"));
        });
    }

    @Test
    public void 年齢での検索() throws Exception {
        AppConfig.singleton().getTransactionManager().required(() -> {
            List<Employee> employees = dao.selectByAge(32);
            assertThat(employees, hasSize(1));
        });
    }

    @Test
    public void 登録更新削除() throws Exception {
        LocalTransactionManager tm = AppConfig.singleton().getTransactionManager();

        Employee hoge = new Employee();
        hoge.age = 32;
        hoge.name = "HOGE";

        assertThat(hoge.id, is(nullValue()));

        // INSERT
        tm.required(() -> {
            dao.insert(hoge);
            assertThat(hoge.id, is(notNullValue()));
        });
        tm.required(() -> {
            Employee employee = dao.selectById(hoge.id);
            assertThat(employee.name, is("HOGE"));
        });

        // UPDATE
        tm.required(() -> {
            Employee employee = dao.selectById(hoge.id);
            assertThat(employee.age, is(32));

            employee.age = 33;
            int update = dao.update(employee);
            assertThat(update, is(1));
        });
        tm.required(() -> {
            Employee employee = dao.selectById(hoge.id);
            assertThat(employee.age, is(33));
        });

        // DELETE
        tm.required(() -> {
            Employee employee = dao.selectById(hoge.id);
            int delete = dao.delete(employee);
            assertThat(delete, is(1));
        });
        tm.required(() -> {
            Employee employee = dao.selectById(hoge.id);
            assertThat(employee, is(nullValue()));
        });
    }
}