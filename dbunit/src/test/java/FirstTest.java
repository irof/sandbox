import org.dbunit.*;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author irof
 */
public class FirstTest {

    JdbcDataSource dataSource;

    @Before
    public void setup() throws Exception {
        // H2DatabaseのDataSourceを用意
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:temp;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");

        // Flywayでテーブル作成
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    @Test
    public void test() throws Exception {
        // 伝統的なテストの親クラスに指定する子
        // PrepAndExpectedTestCaseに渡すためにここでインスタンス化
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setSetUpOperation(DatabaseOperation.INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE);

        // データの形式を設定するやつ
        // 標準で提供されているのは CSV、XML二種類、Excel
        DataFileLoader dataFileLoader = new FlatXmlDataFileLoader();

        // 2.4.8で入った親クラスを使わずにテストする仕組み
        // pre-postを自分で呼ぶか、runTestに任せるかのどちらか
        PrepAndExpectedTestCase tc = new DefaultPrepAndExpectedTestCase(dataFileLoader, databaseTester);

        // 初期投入するデータを設定
        String[] prepDataFiles = {"/input.xml"};
        // 期待値のデータを設定
        String[] expectedDataFiles = {"/expected.xml"};
        // 検証するテーブルとカラムを設定
        VerifyTableDefinition[] verifyTables = {
                new VerifyTableDefinition("event", new String[]{})
        };

        // runTestは処理をlambdaで渡す
        // lambdaの戻り値がそのままrunTestの戻り値となる
        // runTestの前後でpreTest/postTestが呼ばれる
        Object count = tc.runTest(verifyTables, prepDataFiles, expectedDataFiles, () -> {
            try (Connection conn = dataSource.getConnection();
                 Statement st = conn.createStatement()) {
                return st.executeUpdate("update event set capacity=15 where id = 123");
            }
        });

        // おまけ
        assertThat(count).isEqualTo(1);
    }
}
