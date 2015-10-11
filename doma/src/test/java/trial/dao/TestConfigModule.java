package trial.dao;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.SimpleDataSource;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;

import javax.inject.Inject;
import javax.sql.DataSource;

public class TestConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dialect.class).to(H2Dialect.class);
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setUrl("jdbc:h2:mem:tutorial;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        bind(DataSource.class).toInstance(dataSource);
        bind(Config.class).annotatedWith(Names.named("config")).to(GuiceAppConfig.class);
    }

    static class GuiceAppConfig implements Config {

        private final DataSource dataSource;
        private final Dialect dialect;

        @Inject
        GuiceAppConfig(DataSource dataSource, Dialect dialect) {
            this.dataSource = dataSource;
            this.dialect = dialect;
        }

        @Override
        public DataSource getDataSource() {
            return dataSource;
        }

        @Override
        public Dialect getDialect() {
            return dialect;
        }
    }
}
