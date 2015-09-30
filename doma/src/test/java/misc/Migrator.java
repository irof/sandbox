package misc;

import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;
import trial.AppConfig;

/**
 * @author irof
 */
public class Migrator extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        AppConfig.singleton().getTransactionManager().requiresNew(() -> {
            Flyway flyway = new Flyway();
            flyway.setDataSource(AppConfig.singleton().getDataSource());
            flyway.setLocations("META-INF/trial/migration");
            flyway.migrate();
        });
    }
}
