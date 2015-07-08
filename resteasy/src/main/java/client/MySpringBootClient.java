package client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * @author irof
 */
public class MySpringBootClient {

    public static void main(String[] args) {
        Response response = ClientBuilder.newClient()
                .target("http://localhost:9000")
                .path("hello")
                .request()
                .get();

        System.out.println(response);
        System.out.println(response.getStatusInfo());
        System.out.println(response.readEntity(String.class));
    }
}
