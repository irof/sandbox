package jdbc;

import org.flywaydb.core.Flyway;

/**
 * @author irof
 */
public class HSQLDBMigrator {

    public static void main(String[] args) {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:hsqldb:hsql://localhost/", "SA", null);
        flyway.setLocations("jdbc/hsqldb");
        flyway.migrate();
    }
}
