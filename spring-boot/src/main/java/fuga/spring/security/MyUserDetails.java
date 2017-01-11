package fuga.spring.security;

import fuga.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author irof
 */
public class MyUserDetails extends User {

    MyUserDetails(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getName().getValue(), account.getPassword().getValue(), authorities);
    }
}
