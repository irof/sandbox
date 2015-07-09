package client;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

/**
 * @author irof
 */
public class MySpringBootClient {

    public static void main(String[] args) throws Exception {

        // RESTEasyのBuilderでやっちゃえば #disableTrustManager で手っ取り早い感ある。
        // javadoc に "this is a security hole" って書いてるけど。
        Client client = new ResteasyClientBuilder()
                .disableTrustManager()
                .build();

        Response response = client
                .target("https://localhost:9000")
                .path("hello")
                .request()
                .get();

        System.out.println(response);
        System.out.println(response.getStatusInfo());
        System.out.println(response.readEntity(String.class));
    }
}
