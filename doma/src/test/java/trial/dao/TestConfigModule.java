package trial.dao;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.inject.Inject;
import javax.sql.DataSource;

public class TestConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dialect.class).to(H2Dialect.class);
        bind(LocalTransactionDataSource.class).toInstance(
                new LocalTransactionDataSource("jdbc:h2:mem:tutorial;DB_CLOSE_DELAY=-1", "sa", null)
        );
        bind(Config.class).annotatedWith(Names.named("config")).to(GuiceAppConfig.class);
    }

    static class GuiceAppConfig implements Config {

        private final LocalTransactionManager transactionManager;
        private final LocalTransactionDataSource dataSource;
        private final Dialect dialect;

        @Inject
        GuiceAppConfig(LocalTransactionDataSource dataSource, Dialect dialect) {
            this.dataSource = dataSource;
            this.dialect = dialect;
            transactionManager = new LocalTransactionManager(dataSource.getLocalTransaction(getJdbcLogger()));
        }

        @Override
        public DataSource getDataSource() {
            return dataSource;
        }

        @Override
        public Dialect getDialect() {
            return dialect;
        }

        @Override
        public TransactionManager getTransactionManager() {
            return transactionManager;
        }
    }
}
