package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import sandbox.bookmark.StatefulPage;
import sandbox.bookmark.StatelessPage;
import sandbox.i18n.ResourcePage;
import sandbox.misc.LinkPage;
import sandbox.misc.MiscPage;
import sandbox.spring.SpringPage;
import sandbox.structure.FragmentSamplePage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new BookmarkablePageLink<>("i18n.link", ResourcePage.class));
        add(new BookmarkablePageLink<>("structure.link", FragmentSamplePage.class));

        add(new BookmarkablePageLink<>("misc", MiscPage.class));
        add(new BookmarkablePageLink<>("misc.links", LinkPage.class));

        add(new BookmarkablePageLink<>("state.stateful", StatefulPage.class));
        add(new BookmarkablePageLink<>("state.stateless", StatelessPage.class));

        add(new BookmarkablePageLink<>("spring.link", SpringPage.class));
    }
}
