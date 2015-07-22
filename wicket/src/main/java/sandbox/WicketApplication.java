package sandbox;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;
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

        mountResource("/images/${key}", new ImageResourceReference());

        introduceSpring();

        authorize();
    }

    private void authorize() {
        getSecuritySettings().setAuthorizationStrategy(
                new IAuthorizationStrategy() {
                    @Override
                    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
                        if (!componentClass.getPackage().getName().equals("sandbox.authorization")) {
                            return true;
                        }
                        MySession session = (MySession) Session.get();
                        if (session.isSignedIn()) {
                            return true;
                        }
                        throw new RestartResponseAtInterceptPageException(MySignInPage.class);
                    }

                    @Override
                    public boolean isActionAuthorized(Component component, Action action) {
                        // Actionは常にtrueにしておく
                        return true;
                    }
                }
        );
    }

    /**
     * Springを使う時のあれこれ
     */
    private void introduceSpring() {
        // WicketからSpringを使う程度ならこのタイミングで作るのがシンプルっぽい。設定もここだけでいいしね。
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        // とりあえず2つアクティブにしとく
        ctx.getEnvironment().addActiveProfile("active1");
        ctx.getEnvironment().addActiveProfile("active2");
        // Springで遊ぶパッケージ
        ctx.scan("sandbox.spring");
        ctx.refresh();

        // Wicketのコンポーネント生成時に @SpringBean にプロキシをインジェクションするリスナー。
        // ApplicationContext の生成を WebApplication#init で行わないなら、
        // 第二引数は設定せず1引数のコンストラクタを使用する。
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));

        // SpringWebApplicationFactory を WicketFilter に設定するのは WebApplication をSpring管理下にしたいとき。
        // その際の ApplicationContext の生成は外でやる。
        // Wicketの外でSpring使う時は ContextLoaderListener とか AbstractContextLoaderInitializer を実装する。
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new MySession(request);
    }
}
