package sandbox.spring;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author irof
 */
public class SpringPage extends WebPage {

    @SpringBean(name = "hoge")
    private String a;

    @SpringBean(name = "fuga")
    private String b;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("profile", "profile"));
        add(new Label("text1", a));
        add(new Label("text2", b));
    }

    @Configuration
    public static class Config {

        @Profile("active1")
        @Bean(name = "hoge")
        public String str1() {
            return "active1が有効な時のhoge";
        }

        @Profile("!active1")
        @Bean(name = "hoge")
        public String str2() {
            return "active1が無効な時のhoge";
        }

        @Bean(name = "fuga")
        public String str3() {
            return "profileに関係ないfuga";
        }
    }
}
