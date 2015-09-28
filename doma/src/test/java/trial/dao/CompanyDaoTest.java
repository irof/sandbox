package trial.dao;

import misc.TestDbResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import trial.AppConfig;
import trial.entity.Company;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class CompanyDaoTest {

    private AppDao appDao = new AppDaoImpl();

    @Rule
    public ExternalResource dbResource = new TestDbResource(appDao::create, appDao::drop, AppConfig.singleton());

    private CompanyDao dao = new CompanyDaoImpl();

    @Test
    public void testSelectAll() throws Exception {

        AppConfig.singleton().getTransactionManager().required(() -> {
            List<Company> companies = dao.selectAll();
            assertThat(companies, hasSize(2));
        });
    }
}