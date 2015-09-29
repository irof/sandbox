import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.rules.ExternalResource;

/**
 * @author irof
 */
public class Migrator extends ExternalResource {

    @Override
    public void before() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setLocations("db/migration", "db/migration/test");
        flyway.setDataSource("jdbc:h2:mem:hoge;DB_CLOSE_DELAY=-1", "sa", null);
        flyway.migrate();
    }
}
