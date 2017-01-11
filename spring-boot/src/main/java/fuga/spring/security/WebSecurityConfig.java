package fuga.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author irof
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/secret/*").authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                // ログアウトしたらトップページに戻る
                // ログアウトメッセージを出すためにパラメタ付き。
                .logout().logoutSuccessUrl("/?logout");
    }

    /**
     * PasswordEncoderをBean定義したら自動的に使われる。
     * 定義しなかったら PlaintextPasswordEncoder になる模様。
     *
     * @return encoder
     * @see org.springframework.security.authentication.encoding.PlaintextPasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}