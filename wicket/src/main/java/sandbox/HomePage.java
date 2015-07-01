package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import sandbox.i18n.ResourcePage;
import sandbox.misc.SimplePage;
import sandbox.spring.SpringPage;
import sandbox.structure.MyPage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new BookmarkablePageLink<>("i18n.link", ResourcePage.class));
        add(new BookmarkablePageLink<>("misc.link", SimplePage.class));
        add(new BookmarkablePageLink<>("structure.link", MyPage.class));
        add(new BookmarkablePageLink<>("spring.link", SpringPage.class));
    }
}
