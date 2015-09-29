import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

public class HogeTest {

    @Rule
    public Migrator migrator = new Migrator();

    @Test
    public void コミュニティテーブルに一件以上入っている() throws Exception {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:hoge", "sa", null);
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("select * from community")) {
            assertTrue(rs.next());
        }
    }
}
