package sandbox.misc;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class ButtonPanel extends Panel {

    String fieldProps = "FIELD";

    public ButtonPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        buttons();
        ajaxButtons();
    }

    private void ajaxButtons() {
        Form<Void> form = new Form<>("ajaxForm");
        add(form);

        Model<String> model1 = new Model<>("hoge");
        Component label1 = new Label("label1", model1).setOutputMarkupId(true);
        Model<String> model2 = new Model<>("hoge");
        Component label2 = new Label("label2", model2).setOutputMarkupId(true);
        form.add(label1);
        form.add(label2);

        form.add(new AjaxButton("button1") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                model1.setObject("fuga");
                target.add(label1);
            }
        });

        form.add(new AjaxButton("button2") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                model2.setObject("fuga");
                target.add(label2);
            }
        });
    }

    private void buttons() {
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
