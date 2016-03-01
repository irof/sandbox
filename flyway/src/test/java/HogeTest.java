import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HogeTest {

    @Rule
    public Migrator migrator = new Migrator("db/migration", "db/migration-test");

    @Test
    public void PERSONに3件入ってる() throws Exception {
        // src/main/resources/db/migration が読まれていれば通る
        try (Connection conn = migrator.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("select count(*) cnt from PERSON")) {
            rs.next();
            assertEquals(3, rs.getInt(1));
        }
    }

    @Test
    public void コミュニティテーブルに一件以上入っている() throws Exception {
        // src/test/resources/db/migration-test が読まれていれば通る
        try (Connection conn = migrator.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("select * from community")) {
            assertTrue(rs.next());
        }
    }
}
