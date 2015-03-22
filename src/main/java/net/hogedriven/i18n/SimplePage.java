package net.hogedriven.i18n;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Collections;

public class SimplePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public SimplePage(final PageParameters parameters) {
        super(parameters);

        add(new Label("resource.static", new ResourceModel("hello.message3")));
        add(new Label("resource.dynamic", new StringResourceModel("hello.message4", this, null, null, "Dynamic")));

        add(new Label("hello", new StringResourceModel("hello.message5", this,
                new MapModel<String, String>(Collections.singletonMap("name", "irof")))));
    }
}
