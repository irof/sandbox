package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new Link<Void>("i18n.link") {

            @Override
            public void onClick() {
                setResponsePage(sandbox.i18n.SimplePage.class);
            }
        });

        add(new Link<Void>("misc.link") {

            @Override
            public void onClick() {
                setResponsePage(sandbox.misc.SimplePage.class);
            }
        });
    }
}