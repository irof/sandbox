package fuga.spring.security;

import fuga.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Spring4.1以降でUserDetailsServiceをBean定義していたら自動的に使われる。
 *
 * @author irof
 * @see org.springframework.security.config.annotation.authentication.configuration.InitializeUserDetailsBeanManagerConfigurer
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // どこかしらから取得してくる
        String encodedPassword = encoder.encode("password");
        Account account = Account.builder()
                .username(username)
                .password(encodedPassword)
                .build();

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new MyUserDetails(account, authorities);
    }
}
