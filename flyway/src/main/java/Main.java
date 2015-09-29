import org.flywaydb.core.Flyway;

public class Main {

    public static void main(String[] args) {
        Flyway flyway = new Flyway();

        flyway.setDataSource("jdbc:h2:mem:", "sa", null);

        flyway.migrate();
    }
}
