import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author irof
 */
@ApplicationPath("")
public class Application extends ResourceConfig {

    public Application() {
        packages(true, "sample");
    }
}
