package clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class RestTemplateTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("url");

        RestTemplate restTemplate = new RestTemplate();

        String text = new StringJoiner("&")
                .add("test1=test")
                .add("test2=" + URLEncoder.encode("テストお", StandardCharsets.UTF_8.toString()))
                .toString();

        System.out.println(restTemplate.getForEntity(resourceBundle.getString("url"), String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<>(text, headers);

                restTemplate
                .put(
                //.postForEntity(
                        resourceBundle.getString("url"),
                        text,
                        // httpEntity,
                        String.class);
    }
}
