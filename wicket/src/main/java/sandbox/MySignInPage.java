package sandbox;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.StatelessForm;

/**
 * @author irof
 */
public class MySignInPage extends WebPage {

    public MySignInPage() {
        add(new StatelessForm<Void>("signInForm") {
            @Override
            protected void onSubmit() {
                MySession session = (MySession) getSession();
                session.signIn();
                // とりあえずホームに遷移しとく
                setResponsePage(getApplication().getHomePage());
            }
        });
    }
}
