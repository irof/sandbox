package net.hogedriven.components;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SimplePage extends WebPage {
    private static final long serialVersionUID = 1L;

    String fieldProps = "FIELD";

    public SimplePage(final PageParameters parameters) {
        super(parameters);

        WebMarkupContainer property = new WebMarkupContainer("property");
        add(property);
        property.add(new Label("field", new PropertyModel<String>(this, "fieldProps")));
    }
}
