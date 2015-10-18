package chirp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * @author irof
 */
public class Debug {
    public static void main(String[] args) {
        String message = "にほんごor test";
        String body = "{\"message\":\"" + message + "\"}";

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("X-Chirp-User-Id", "id-hoge");

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:9000/status", entity);
    }
}
