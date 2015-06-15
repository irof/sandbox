package sandbox.misc;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sandbox.misc.switching.LabelOrTextField;

public class SimplePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public SimplePage(final PageParameters parameters) {
        super(parameters);

        add(new ButtonPanel("panel.button"));
        add(new AjaxButtonPanel("panel.button.ajax"));
        add(new ListPanel("panel.list"));

        Model<String> hoge1 = Model.of("hogehoge");
        add(new LabelOrTextField("panel.switching.input", hoge1, Model.of(true)));
        add(new LabelOrTextField("panel.switching.label", Model.of("fugafuga"), Model.of(false)));
    }
}
