package sample;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class KitakyuTest extends JerseyTest {

    @Test
    public void test() throws Exception {
        String response = target("kitakyu")
                .path("stations")
                .request()
                .get(String.class);
        assertThat(response, containsString("桃山台"));
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(Kitakyu.class);
    }
}