
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author irof
 */
@DataSourceDefinition(name = "java:global/MyApp/myDataSource",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "sa",
        password = "sa"
)
@WebServlet("/*")
public class MyServlet extends HttpServlet {

    @Resource(lookup = "java:global/MyApp/myDataSource")
    DataSource myDB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = myDB.getConnection();
             Statement st = conn.createStatement()) {
            st.execute("create table hoge(name varchar(100))");

            st.executeUpdate("insert into hoge values('duke')");

            try (ResultSet resultSet = st.executeQuery("select * from hoge")) {
                resultSet.next();
                String name = resultSet.getString(1);
                System.out.println(name);
            }

            st.execute("drop table hoge");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
