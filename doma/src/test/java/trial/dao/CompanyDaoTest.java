package trial.dao;

import misc.Migrator;
import org.junit.Rule;
import org.junit.Test;
import trial.AppConfig;
import trial.entity.Company;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class CompanyDaoTest {

    @Rule
    public Migrator migrator = new Migrator();

    private CompanyDao dao = new CompanyDaoImpl();

    @Test
    public void testSelectAll() throws Exception {

        AppConfig.singleton().getTransactionManager().required(() -> {
            List<Company> companies = dao.selectAll();
            assertThat(companies, hasSize(2));
        });
    }
}