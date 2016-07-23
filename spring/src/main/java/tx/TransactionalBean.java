package tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author irof
 */
public class TransactionalBean {

    @Autowired
    DataSource ds;

    @Transactional
    public void method() throws Exception {
        try (
                Connection connection = ds.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.execute("INSERT INTO hoge VALUES('AAA')");
        }

        throw new Exception();
    }
}
