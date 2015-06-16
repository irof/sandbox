package sample;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class SakaisujiLineTest {

    @Test
    public void test() throws Exception {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry()
                .addResourceFactory(new POJOResourceFactory(SakaisujiLine.class));

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(
                MockHttpRequest.get("sakaisuji/stations/K16"),
                response);

        assertThat(response.getContentAsString(), containsString("長堀橋駅"));
    }
}