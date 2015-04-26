package hoge.rest;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author irof
 */
@org.springframework.web.bind.annotation.RestController
public class Greeter {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, World.";
    }
}
