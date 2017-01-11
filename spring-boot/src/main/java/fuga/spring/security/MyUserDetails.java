package fuga.spring.security;

import fuga.spring.data.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author irof
 */
public class MyUserDetails extends User {

    private final AccountEntity account;

    MyUserDetails(AccountEntity account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getName(), account.getPassword(), authorities);
        this.account = account;
    }

    public AccountEntity getAccount() {
        return account;
    }
}
