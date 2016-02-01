package example;

import org.flywaydb.core.Flyway;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 * DataSourceを使用してマイグレーションするListener
 */
@WebListener
public class MyContextListener implements ServletContextListener {

    @Resource(name = "java:app/jdbc/sample")
    DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
