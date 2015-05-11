package sandbox.misc;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SimplePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public SimplePage(final PageParameters parameters) {
        super(parameters);

        add(new ButtonPanel("panel.button"));
        add(new AjaxButtonPanel("panel.button.ajax"));
        add(new ListPanel("panel.list"));
    }
}
