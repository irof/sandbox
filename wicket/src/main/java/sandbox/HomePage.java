package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sandbox.i18n.ResourcePage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new BookmarkablePageLink<>("i18n.link", ResourcePage.class));
        add(new BookmarkablePageLink<>("misc.link", sandbox.misc.SimplePage.class));
        add(new BookmarkablePageLink<>("list.link", sandbox.list.ListPage.class));
        add(new BookmarkablePageLink<>("structure.link", sandbox.structure.MyPage.class));
        add(new BookmarkablePageLink<>("model.link", sandbox.model.SimplePage.class));
    }
}
