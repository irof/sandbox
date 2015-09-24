package gettingstarted;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.seasar.doma.SingletonConfig;
import org.seasar.doma.jdbc.AbstractJdbcLogger;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.function.Supplier;

@SingletonConfig
public class AppConfig implements Config {

    private static final AppConfig CONFIG = new AppConfig();
    private final H2Dialect dialect;
    private final LocalTransactionDataSource dataSource;
    private final LocalTransactionManager transactionManager;

    private AppConfig() {
        dialect = new H2Dialect();
        dataSource = new LocalTransactionDataSource("jdbc:h2:mem:tutorial;DB_CLOSE_DELAY=-1", "sa", null);
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
    public LocalTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static AppConfig singleton() {
        return CONFIG;
    }

    @Override
    public JdbcLogger getJdbcLogger() {
        return new AbstractJdbcLogger<Level>(Level.INFO) {
            @Override
            protected void log(Level level, String callerClassName, String callerMethodName,
                               Throwable throwable, Supplier<String> messageSupplier) {
                LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
                Logger logger = loggerContext.getLogger(callerClassName);
                int levelInt = Level.toLocationAwareLoggerInteger(level);
                logger.log(null, callerClassName, levelInt, messageSupplier.get(), null, null);
            }
        };
    }
}
