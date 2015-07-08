package client;

import javax.ws.rs.client.Client;
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

        // 動くけどなんか違う気がする
        KeyStore trustStore = KeyStore.getInstance("JKS", "SUN");
        try (InputStream inputStream = Files.newInputStream(Paths.get("hoge.keystore"))) {
            trustStore.load(inputStream, "hogehoge".toCharArray());
        }
        Client client = ClientBuilder.newBuilder()
                .trustStore(trustStore)
                // hostnameの検証を無効にするー
                .hostnameVerifier((hostname, session) -> true)
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
