package hoge.client;

import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate を使用した REST API の呼び出し。
 *
 * @author irof
 */
public class RESTClient {

    public static void main(String[] args) {
        String result = new RestTemplate()
                .getForObject("http://localhost:9000/hello", String.class);

        System.out.println(result);
    }
}
