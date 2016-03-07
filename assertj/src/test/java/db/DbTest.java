package db;

import org.assertj.db.api.Assertions;
import org.assertj.db.type.Table;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @author irof
 */
public class DbTest {

    private DataSource ds;

    @Before
    public void setup() {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:hoge", "sa", "sa");

        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();
    }

    @Test
    public void TABLEを使用した検証() throws Exception {
        // Tableは全データに対してかかる
        // ソート順は org.assertj.db.util.RowComparator に依存し、変更できない模様。
        // まずはPKの昇順、次に全ての値の配列の昇順となる。
        Table table = new Table(ds, "EXAMPLE");
        Assertions.assertThat(table)
                .column("COL1")
                // value() を呼び出すたびに次の行の検証になる
                .value().isEqualTo("AAA")
                .value().isEqualTo("BBB")
                // 3行までいってても column() を呼び出したら1行目から再開
                .column("COL2")
                .value().isEqualTo("aaa")
                // 別のcolumnを呼び出した後に戻ってきても続きになる
                .column("COL1")
                .value().isEqualTo("CCC")
        ;

        // assertThatから始めればリセットされる
        Assertions.assertThat(table)
                .column("COL1").value().isEqualTo("AAA")
                .column("COL1").value().isEqualTo("BBB")
                // column().hasValues() は列をまるごと検証
                // 前に column() を呼び出してても関係ない（こんな使い方しないだろうけど）
                .column("COL1").hasValues("AAA", "BBB", "CCC", "DDD")
                // row().hasValues() は行を丸ごと検証
                .row().hasValues("AAA", "aaa", 1)
                // 3行目（0オリジン）
                .row(3).hasValues("DDD", "ddd", 4)
        ;
    }
}
