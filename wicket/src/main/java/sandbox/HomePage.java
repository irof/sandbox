package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sandbox.i18n.ResourcePage;
import sandbox.misc.SimplePage;
import sandbox.model.BookShelfPage;
import sandbox.structure.MyPage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new BookmarkablePageLink<>("i18n.link", ResourcePage.class));
        add(new BookmarkablePageLink<>("misc.link", SimplePage.class));
        add(new BookmarkablePageLink<>("structure.link", MyPage.class));
        add(new BookmarkablePageLink<>("model.link", BookShelfPage.class));
    }
}
