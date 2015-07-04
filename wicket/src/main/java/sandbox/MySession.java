package sandbox;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

/**
 * 認証情報とか持つSessionの実装。
 * とりあえず wicket-auth-roles は使わない形でやってみる。
 *
 * @author irof
 */
public class MySession extends WebSession {

    private volatile boolean signedIn = false;

    public MySession(Request request) {
        super(request);
    }

    public void signIn() {
        signedIn = true;
        bind();
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    @Override
    public void invalidate() {
        signedIn = false;
        super.invalidate();
    }
}
