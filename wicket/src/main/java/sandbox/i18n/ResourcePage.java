package sandbox.i18n;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Collections;

public class ResourcePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public ResourcePage(final PageParameters parameters) {
        super(parameters);

        add(new Label("resource.static", new ResourceModel("hello.message3")));
        add(new Label("resource.dynamic", new StringResourceModel("hello.message4", this, null, null, "Dynamic")));

        add(new Label("hello", new StringResourceModel("hello.message5", this,
                new MapModel<String, String>(Collections.singletonMap("name", "irof")))));

        add(new Label("markup.label.pattern1", new ResourceModel("markup.pattern1")));
        add(new Label("markup.label.pattern2", new ResourceModel("markup.pattern2")));
        add(new Label("markup.label.pattern3", new ResourceModel("markup.pattern3")));

        add(new Label("markup.label.pattern1.unescape", new ResourceModel("markup.pattern1")).setEscapeModelStrings(false));
        add(new Label("markup.label.pattern2.unescape", new ResourceModel("markup.pattern2")).setEscapeModelStrings(false));
        add(new Label("markup.label.pattern3.unescape", new ResourceModel("markup.pattern3")).setEscapeModelStrings(false));
    }
}
