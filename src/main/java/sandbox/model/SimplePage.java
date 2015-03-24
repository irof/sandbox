package sandbox.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

public class SimplePage extends WebPage {

    public SimplePage() {
        BackendModel model = new BackendModel();

        add(new Label("label", new PropertyModel<>(model, "value")));
    }
}
