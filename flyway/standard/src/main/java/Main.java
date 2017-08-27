import org.flywaydb.core.Flyway;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        Flyway flyway = new Flyway();

        flyway.setDataSource("jdbc:h2:mem:hoge;DB_CLOSE_DELAY=-1", "sa", null);

        flyway.migrate();

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:hoge", "sa", null);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from person")) {
            while (resultSet.next())
                System.out.println(resultSet.getString("name"));
        }
    }
}
