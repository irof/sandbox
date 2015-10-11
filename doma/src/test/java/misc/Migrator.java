package misc;

import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;
import java.util.function.Supplier;

/**
 * JUnitを使用する場合にFlywayを使用してマイグレーションするRule
 * 直接DataSourceをコンストラクタ引数にうけても、DataSourceがテストインスタンスに
 * インジェクションされてないためnullになるんで、Supplierかましてみてる。
 *
 * @author irof
 */
public class Migrator extends ExternalResource {

    private Supplier<DataSource> dataSource;

    public Migrator(Supplier<DataSource> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void before() throws Throwable {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource.get());
        flyway.setLocations("META-INF/trial/migration");
        flyway.migrate();
    }
}
