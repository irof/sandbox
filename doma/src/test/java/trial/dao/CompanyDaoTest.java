package trial.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import misc.GuiceTestRunner;
import misc.Migrator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.doma.jdbc.Config;
import trial.entity.Company;
import trial.entity.CompanyName;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * Guice+Domaをやってみたもの。
 * <p>
 * Guiceの初期化とかは冗長になるのでGuiceTestRunnerにやらせて、
 * テーブル定義などはFlyWayでmigrationする。
 */
@RunWith(GuiceTestRunner.class)
@GuiceTestRunner.Module(TestConfigModule.class)
public class CompanyDaoTest {

    @GuiceTestRunner.Module
    public static Module module() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(CompanyDao.class).to(CompanyDaoImpl.class);
            }
        };
    }

    @Inject
    CompanyDao dao;

    @Inject
    @Named("config")
    Config config;

    @Rule
    public Migrator migrator = new Migrator(() -> config.getDataSource());

    @Test
    public void testSelectAll() throws Exception {
        List<Company> companies = dao.selectAll();
        assertThat(companies, hasSize(2));
    }

    @Test
    public void 名前で検索する() throws Exception {
        Optional<Company> company = dao.findByName(new CompanyName("HOGE"));
        assertTrue(company.isPresent());
        assertThat(company.get().phoneNumber.getValue(), is("012012345678"));

        Optional<Company> notFound = dao.findByName(new CompanyName("ABCD"));
        assertFalse(notFound.isPresent());
    }
}