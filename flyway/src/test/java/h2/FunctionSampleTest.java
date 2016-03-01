package h2;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author irof
 */
public class FunctionSampleTest {

    @Test
    public void date_format() throws Exception {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa");
             Statement statement = conn.createStatement();
        ) {
            statement.execute("create alias date_format for \"h2.Functions.dateFormat\"");
            statement.execute("create table DUMMY (col VARCHAR(1))");
            statement.execute("insert into DUMMY (col) values ('a')");

            try (ResultSet rs = statement.executeQuery("select DATE_FORMAT(NOW(),'%b %d %Y %h:%i %p') x from dummy")) {
                rs.next();

                String hoge = rs.getString("x");
                System.out.println(hoge);
            }
        }
    }
}
