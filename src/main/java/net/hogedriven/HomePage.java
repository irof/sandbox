package net.hogedriven;

import net.hogedriven.i18n.SimplePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

        add(new Link<Void>("simplePage") {

            @Override
            public void onClick() {
                setResponsePage(SimplePage.class);
            }
        });

        add(new Link<Void>("components.link") {

            @Override
            public void onClick() {
                setResponsePage(net.hogedriven.components.SimplePage.class);
            }
        });
    }
}
