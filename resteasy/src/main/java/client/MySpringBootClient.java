package client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

/**
 * @author irof
 */
public class MySpringBootClient {

    public static void main(String[] args) throws Exception {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();

        // 動くけどなんか違う気がする
        KeyStore trustStore = KeyStore.getInstance("JKS", "SUN");
        try (InputStream inputStream = Files.newInputStream(Paths.get("hoge.keystore"))) {
            trustStore.load(inputStream, "hogehoge".toCharArray());
        }
        clientBuilder.trustStore(trustStore);

        Response response = clientBuilder.build()
                .target("https://localhost:9000")
                .path("hello")
                .request()
                .get();

        System.out.println(response);
        System.out.println(response.getStatusInfo());
        System.out.println(response.readEntity(String.class));
    }
}
