package sandbox.spring;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author irof
 */
public class SpringPage extends WebPage {

    @SpringBean(name = "hoge")
    private String a;

    @SpringBean(name = "fuga")
    private String b;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("profile", "profile"));
        add(new Label("text1", a));
        add(new Label("text2", b));
    }
}
