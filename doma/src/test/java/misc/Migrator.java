package misc;

import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;
import org.seasar.doma.jdbc.Config;
import trial.AppConfig;

import java.util.function.Supplier;

/**
 * @author irof
 */
public class Migrator extends ExternalResource {

    private Supplier<Config> config;

    public Migrator(Supplier<Config> config) {
        this.config = config;
    }

    @Override
    protected void before() throws Throwable {
        config.get().getTransactionManager().requiresNew(() -> {
            Flyway flyway = new Flyway();
            flyway.setDataSource(config.get().getDataSource());
            flyway.setLocations("META-INF/trial/migration");
            flyway.migrate();
        });
    }
}
