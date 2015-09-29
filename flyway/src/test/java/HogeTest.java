import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

public class HogeTest {

    @Before
    public void setup() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:mem:hoge;DB_CLOSE_DELAY=-1", "sa", null);
        flyway.migrate();
    }

    @Test
    public void コミュニティテーブルに一件以上入っている() throws Exception {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:hoge", "sa", null);
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("select * from community")) {
            assertTrue(rs.next());
        }
    }
}
