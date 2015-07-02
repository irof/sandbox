package sandbox.bookmark;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author irof
 */
public class StatefulPage extends WebPage {

    private final Model<String> code;
    private final IModel<Integer> count = Model.of(0);

    public StatefulPage(PageParameters params) {
        code = Model.of(params.get("code").toString());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("code", code));
        add(new Label("instance", this.toString()));

        Form<Void> form = new Form<>("form");
        form.add(new Label("count", count));
        form.add(new AjaxButton("ajaxButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Integer prev = count.getObject();
                count.setObject(prev + 1);
                target.add(form);
            }
        });
        form.add(new Button("button") {
            @Override
            public void onSubmit() {
                setResponsePage(StatefulPage.class);
            }
        });
        form.setOutputMarkupId(true);
        add(form);
    }
}
