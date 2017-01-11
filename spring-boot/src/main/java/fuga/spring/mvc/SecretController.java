package fuga.spring.mvc;

import fuga.domain.Account;
import fuga.spring.security.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String user(Model model, @AuthenticationPrincipal MyUserDetails details) {

        Account account = Account.builder()
                .username(details.getUsername())
                .mailAddress(details.getAccount().getMailAddress())
                .createdDate(details.getAccount().getCreatedDate())
                .build();
        model.addAttribute(account);

        return "secret/user";
    }
}
