package sandbox.misc;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.util.Locale;

public class ButtonPanel extends Panel {

    Model<String> fieldProps = Model.of("FIELD");

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

        Model<String> left = Model.of("hoge");
        Model<String> right = Model.of("fuga");

        WebMarkupContainer p1 = new WebMarkupContainer("p1");
        p1.add(new Label("leftLabel", left));
        p1.add(new Label("rightLabel", right));
        form.add(p1.setOutputMarkupId(true));

        WebMarkupContainer p2 = new WebMarkupContainer("p2");
        p2.add(new Label("leftLabel", left));
        p2.add(new Label("rightLabel", right));
        form.add(p2.setOutputMarkupId(true));

        form.add(new AjaxButton("leftButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                left.setObject(left.getObject() + "e");
                target.add(p1);
            }
        });

        form.add(new AjaxButton("rightButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                right.setObject(right.getObject() + "a");
                target.add(p2);
            }
        });
    }

    private void buttons() {
        Form form = new Form("form");
        add(form);

        form.add(new Label("label", fieldProps));

        Button button = new Button("lowerButton") {
            @Override
            public void onSubmit() {
                fieldProps.setObject(fieldProps.getObject().toLowerCase(Locale.US));
            }
        };
        button.add(new Label("label", fieldProps));
        form.add(button);

        form.add(new Button("upperButton", fieldProps) {
            @Override
            public void onSubmit() {
                fieldProps.setObject(fieldProps.getObject().toUpperCase(Locale.US));
            }
        });
    }
}
