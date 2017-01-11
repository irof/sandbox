package fuga.spring.security;

import fuga.spring.data.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring4.1以降でUserDetailsServiceをBean定義していたら自動的に使われる。
 *
 * @author irof
 * @see org.springframework.security.config.annotation.authentication.configuration.InitializeUserDetailsBeanManagerConfigurer
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new MyUserDetails(
                repository.findByName(username),
                AuthorityUtils.createAuthorityList("ROLE_SAMPLE"));
    }
}
