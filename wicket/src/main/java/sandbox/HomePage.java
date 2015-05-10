package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sandbox.i18n.ResourcePage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new Link<Void>("i18n.link") {

            @Override
            public void onClick() {
                setResponsePage(ResourcePage.class);
            }
        });

        add(new Link<Void>("misc.link") {

            @Override
            public void onClick() {
                setResponsePage(sandbox.misc.SimplePage.class);
            }
        });

        add(new Link<Void>("list.link") {

            @Override
            public void onClick() {

                setResponsePage(sandbox.list.ListPage.class);
            }
        });

        add(new Link<Void>("structure.link") {

            @Override
            public void onClick() {
                setResponsePage(sandbox.structure.MyPage.class);
            }
        });

        add(new Link<Void>("model.link") {

            @Override
            public void onClick() {
                setResponsePage(sandbox.model.SimplePage.class);
            }
        });
    }
}