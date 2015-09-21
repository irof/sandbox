package gettingstarted;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;

import javax.sql.DataSource;

public class AppConfig implements Config {

    @Override
    public DataSource getDataSource() {
        return null;
    }

    @Override
    public Dialect getDialect() {
        return null;
    }
}
