package db;

import org.assertj.db.api.Assertions;
import org.assertj.db.type.Table;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @author irof
 */
public class DbTest {

    @Test
    public void select() throws Exception {
        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:hoge", "sa", "sa");

        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();

        Table table = new Table(ds, "EXAMPLE");
        Assertions.assertThat(table)
                .hasNumberOfRows(2)
                .column("COL1").value().isEqualTo("HOGE")
                .column("COL1").value().isEqualTo("fuga")
        ;
    }
}
