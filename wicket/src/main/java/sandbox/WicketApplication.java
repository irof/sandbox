package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import sandbox.bookmark.StatefulPage;
import sandbox.bookmark.StatelessPage;

public class WicketApplication extends WebApplication {
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();

        mountPage("/bookmark/stateless/${code}", StatelessPage.class);
        mountPage("/bookmark/stateful/${code}", StatefulPage.class);

        // WicketからSpringを使う程度ならこのタイミングで作るのがシンプルっぽい。設定もここだけでいいしね。
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        // とりあえず2つアクティブにしとく
        ctx.getEnvironment().addActiveProfile("active1");
        ctx.getEnvironment().addActiveProfile("active2");
        // Springで遊ぶパッケージ
        ctx.scan("sandbox.spring");
        ctx.refresh();

        // Wicketのコンポーネント生成時に @SpringBean にプロキシをインジェクションするリスナー。
        // ApplicationContext の生成を WebApplication#init で行わないならなら、第二引数は基本的に不要。
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));

        // SpringWebApplicationFactory を WicketFilter に設定するのは WebApplication をSpring管理下にしたいとき。
        // その際の ApplicationContext の生成は外でやる。
        // Wicketの外でSpring使う時は ContextLoaderListener とか AbstractContextLoaderInitializer を実装する。
    }
}
