package example;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author irof
 */
@WebServlet("/*")
public class MyServlet extends HttpServlet {

    @Resource(name = "java:app/jdbc/sample")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServletOutputStream outputStream = resp.getOutputStream();
             PrintWriter pw = new PrintWriter(outputStream);
             Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from person")) {
            while (resultSet.next()) {
                pw.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
