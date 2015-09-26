package gettingstarted.guice;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.sql.DataSource;

class GuiceAppConfig implements Config {

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