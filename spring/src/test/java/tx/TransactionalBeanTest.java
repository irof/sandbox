package tx;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author irof
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionalBeanTest {

    @Autowired
    TransactionalBean bean;
    @Autowired
    DataSource ds;

    @Before
    public void setup() throws Exception {
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE hoge(id CHAR)");
        }
    }

    @Test
    public void test() throws Exception {
        assertThatThrownBy(() ->
        )
        bean.method();

        int count = count();
        assertThat(count).isEqualTo(0);
    }

    private int count() throws Exception {
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM hoge");
        ) {
            resultSet.next();
            return resultSet.getInt(0);
        }
    }

    @Configuration
    @EnableTransactionManagement
    static class Config {

        @Bean
        public PlatformTransactionManager manager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        public DataSource dataSource() {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:mem:hoge;DB_CLOSE_DELAY=-1");
            dataSource.setUser("su");
            dataSource.setPassword("secret");
            return dataSource;
        }

        @Bean
        public TransactionalBean bean() {
            return new TransactionalBean();
        }
    }
}