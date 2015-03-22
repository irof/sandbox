package net.hogedriven.components;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SimplePage extends WebPage {
    private static final long serialVersionUID = 1L;

    String fieldProps = "FIELD";

    public SimplePage(final PageParameters parameters) {
        super(parameters);

        Form property = new Form("property");
        add(property);

        property.add(new Label("field", new PropertyModel<String>(this, "fieldProps")));

        property.add(new Button("button1") {
            @Override
            public void onSubmit() {
                fieldProps = fieldProps.toLowerCase();
            }
        }.add(new Label("field", new PropertyModel<String>(this, "fieldProps"))));

        property.add(new Button("button2", new PropertyModel<String>(SimplePage.this, "fieldProps")) {
            @Override
            public void onSubmit() {
                fieldProps = fieldProps.toLowerCase();
            }
        });
    }
}
