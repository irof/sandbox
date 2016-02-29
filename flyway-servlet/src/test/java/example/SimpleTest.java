package example;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringJoiner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class SimpleTest {

    String url = new StringJoiner(";")
            .add("jdbc:h2:mem:test")
            .add("DB_CLOSE_DELAY=-1")
            .add("MODE=MySQL")
            .add("INIT=create schema if not exists \"public\"")
            .toString();

    JdbcConnectionPool dataSource = JdbcConnectionPool.create(url, "sa", "sa");

    @After
    public void tearDown() {
        dataSource.dispose();
    }

    @Test
    public void testMigrate() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();

        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("select * from person")) {
            // 1件以上あればOK
            assertThat(resultSet.next(), is(true));
        }
    }
}
