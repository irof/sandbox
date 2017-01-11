package fuga.spring.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author irof
 */
@RequestMapping("/secret")
@Controller
public class SecretController {

    @RequestMapping("/user")
    public String user(Model model) {
        return "secret/user";
    }
}
