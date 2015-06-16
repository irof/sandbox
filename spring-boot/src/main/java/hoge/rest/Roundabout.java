package hoge.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author irof
 */
@RestController
@RequestMapping("/roundabout")
public class Roundabout {

    @Value("${server.port}")
    String port;

    @RequestMapping("hello")
    public String hello() {
        return new RestTemplate()
                .getForObject("http://localhost:" + port + "/hello", String.class);
    }
}
