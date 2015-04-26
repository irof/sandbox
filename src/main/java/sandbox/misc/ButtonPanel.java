package sandbox.misc;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class ButtonPanel extends Panel {

    String fieldProps = "FIELD";

    public ButtonPanel(String id) {
        super(id);

        Form form = new Form("form");
        add(form);

        form.add(new Label("label", new PropertyModel<String>(this, "fieldProps")));

        form.add(new Button("button1") {
            @Override
            public void onSubmit() {
                fieldProps = fieldProps.toLowerCase();
            }
        }.add(new Label("label", new PropertyModel<String>(this, "fieldProps"))));

        form.add(new Button("button2", new PropertyModel<String>(this, "fieldProps")) {
            @Override
            public void onSubmit() {
                fieldProps = fieldProps.toLowerCase();
            }
        });
    }
}
