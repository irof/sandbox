import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.rules.ExternalResource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringJoiner;

/**
 * @author irof
 */
public class Migrator extends ExternalResource {

    private JdbcConnectionPool dataSource;
    private final String[] locations;

    public Migrator(String... locations) {
        this.locations = locations;
    }

    @Override
    public void before() throws Exception {
        String url = new StringJoiner(";")
                .add("jdbc:h2:mem:hoge")
                .add("DB_CLOSE_DELAY=-1")
                .toString();
        dataSource = JdbcConnectionPool.create(url, "sa", "sa");

        Flyway flyway = new Flyway();
        flyway.setLocations(locations);
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    @Override
    protected void after() {
        if (dataSource == null) {
            dataSource.dispose();
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
