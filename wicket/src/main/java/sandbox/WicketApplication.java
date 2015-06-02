package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WicketApplication extends WebApplication {
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();

        // WicketからSpringを使う程度ならこのタイミングで作るのがシンプルっぽい。設定もここだけでいいしね。
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.getEnvironment().addActiveProfile("def");
        ctx.register(Config.class);
        ctx.refresh();

        // Wicketのコンポーネント生成時に @SpringBean にプロキシをインジェクションするリスナー。
        // ApplicationContext の生成を WebApplication#init で行わないならなら、第二引数は基本的に不要。
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));

        // SpringWebApplicationFactory を WicketFilter に設定するのは WebApplication をSpring管理下にしたいとき。
        // その際の ApplicationContext の生成は外でやる。
        // Wicketの外でSpring使う時は ContextLoaderListener とか AbstractContextLoaderInitializer を実装する。
    }

    @Configuration
    public static class Config {

        @Profile("abc")
        @Bean(name = "hoge")
        public String str1() {
            return "HOGE";
        }

        @Profile("!abc")
        @Bean(name = "hoge")
        public String str2() {
            return "hogehoge";
        }

        @Bean(name = "fuga")
        public String str3() {
            return "FUGA";
        }
    }
}
