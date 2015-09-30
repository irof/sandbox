package trial;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.inject.Inject;
import javax.sql.DataSource;

public class AppConfig implements Config {

    private final H2Dialect dialect;
    private final DataSource dataSource;
    private final TransactionManager transactionManager;

    @Inject
    private AppConfig(DataSource dataSource, TransactionManager transactionManager) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
        dialect = new H2Dialect();
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
