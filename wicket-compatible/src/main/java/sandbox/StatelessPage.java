package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.time.LocalDateTime;

/**
 * @author irof
 */
public class StatelessPage extends WebPage {

    IModel<String> model = Model.of(LocalDateTime.now().toString());

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<Void> form = new StatelessForm<Void>("form") {

            @Override
            protected void onSubmit() {
                model.setObject(LocalDateTime.now().toString());
            }
        };
        form.add(new Label("message", model));
        add(form);

        add(new StatelessLink("link.instance") {

            @Override
            public void onClick() {
                setResponsePage(new StatelessPage());
            }
        });
        add(new StatelessLink("link.class") {

            @Override
            public void onClick() {
                setResponsePage(StatelessPage.class);
            }
        });
        add(new StatelessLink("link.this") {

            @Override
            public void onClick() {
                setResponsePage(StatelessPage.this);
            }
        });
        add(new BookmarkablePageLink("link.stateful", StatefulPage.class));
        add(new BookmarkablePageLink("link.stateless", StatelessPage.class));

        add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
        add(new Label("page.instance", this.toString()));
    }
}
