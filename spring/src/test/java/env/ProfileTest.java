package env;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Profileのサンプル
 *
 * @author irof
 * @see <a href="http://docs.spring.io/spring/docs/4.1.6.RELEASE/spring-framework-reference/html/beans.html">5.13.1 Bean definition profiles</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ActiveProfiles("hoge")
public class ProfileTest {

    @Autowired
    String str;

    @Autowired
    String foo;

    @Autowired
    String bar;

    @Autowired
    String baz;

    @Test
    public void ごく普通のBean() throws Exception {
        assertThat(str, is("STR"));
    }

    @Test
    public void 普通にアクティブなProfileのBean() throws Exception {
        assertThat(foo, is("FOO"));
    }

    @Test
    public void 否定条件でアクティブになったProfileのBean() throws Exception {
        assertThat(bar, is("BAR"));
    }

    @Test
    public void 組み合わせ() throws Exception {
        assertThat(baz, is("BAZ"));
    }

    @Configuration
    static class Config {

        @Bean
        public String str() {
            // @Profile つけてないのはいつでも登録される
            // @Profile("default") とは違うのだよ
            return "STR";
        }

        @Bean
        @Profile("hoge")
        public String foo() {
            // 指定したprofileそのものズバリ
            return "FOO";
        }

        @Bean
        @Profile("!fuga")
        public String bar() {
            // 特定profileを指定しなかったという条件
            return "BAR";
        }

        @Bean
        @Profile({"hoge", "fuga"})
        public String baz() {
            // どちらかが合致すれば登録される
            // 両方合致すればではない……否定と何かの組み合わせって意味あるの？
            return "BAZ";
        }

        @Bean
        @Profile("default")
        public String def() {
            // どのprofileもアクティブでないときはdefaultになるけど、
            // 何か指定していた場合はdefaultのBeanは登録されない。
            throw new AssertionError("もしBeanが登録されたら落ちる");
        }

        @Bean
        @Profile("fuga")
        public String fuga() {
            throw new AssertionError("もしBeanが登録されたら落ちる");
        }

        @Bean
        @Profile({"fuga", "piyo"})
        public String fugaPiyo() {
            throw new AssertionError("もしBeanが登録されたら落ちる");
        }
    }
}
