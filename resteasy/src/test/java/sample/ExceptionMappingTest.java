package sample;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExceptionMappingTest {

    @Test
    public void とにかく例外マッピングしてみる() throws Exception {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry()
                .addSingletonResource(new Misc());
        dispatcher.getProviderFactory()
                .registerProvider(MyExceptionMapper.class);

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(
                MockHttpRequest.get("misc/exception"),
                response);

        assertThat(response.getStatus(), is(200));
    }
}
