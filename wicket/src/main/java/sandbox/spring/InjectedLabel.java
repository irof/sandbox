package sandbox.spring;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author irof
 */
public class InjectedLabel extends Label {

    @SpringBean(name = "hoge")
    String hoge;

    public InjectedLabel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setDefaultModel(Model.of(hoge));
    }
}
