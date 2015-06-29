package sandbox.bookmark;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author irof
 */
public class StatelessPage extends WebPage {

    private final Model<String> code;

    public StatelessPage(PageParameters params) {
        code = Model.of(params.get("code").toString());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("code", code));
        add(new Label("instance", this));
    }
}
