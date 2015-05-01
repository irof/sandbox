package sample;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
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
        // jersey-test-framework-provider-xxx を複数いれて選択する場合
        // 1つならそれが使用され、複数あれば GrizzlyTestContainerFactory が優先される
        // 通常は指定する必要はなさそう
        System.setProperty(TestProperties.CONTAINER_FACTORY,
                InMemoryTestContainerFactory.class.getName());

        return new ResourceConfig(Kitakyu.class);
    }
}