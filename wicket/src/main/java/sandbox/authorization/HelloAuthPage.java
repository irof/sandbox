package sandbox.authorization;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 * 認可が必要なページ
 *
 * @author irof
 */
public class HelloAuthPage extends WebPage {
    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("session", Model.of(getSession())));
    }
}
