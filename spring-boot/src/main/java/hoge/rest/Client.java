package hoge.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author irof
 */
@RestController
public class Client {

    /**
     * ../jaxrs/resteasy を呼び出す謎のメソッド.
     * もちろん相手が起動していないと動かない。
     */
    @RequestMapping(value = "/jaxrs/resteasy", produces = "application/xml")
    public String jaxrsRestEasy() {
        RestTemplate template = new RestTemplate();
        String response = template.getForObject("http://localhost:8080/resteasy/customers", String.class);
        return response;
    }
}
