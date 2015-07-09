package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author irof
 */
public class HomePage extends WebPage {

    IModel<String> model = Model.of("hoge");

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<Void> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                model.setObject("fuga");
            }
        };

        form.add(new Label("message", model));
        add(form);

        add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
    }
}
