package sandbox.structure;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;

/**
 * Page, Panel, Fragment を使ってるやつ。
 *
 * @author irof
 */
public class MyPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new MyPanel("myPanel"));

        Fragment fragment1 = new Fragment("contentArea1", "myFragment", this);
        fragment1.add(new Label("message", "hoge"));
        add(fragment1);
        Fragment fragment2 = new Fragment("contentArea2", "myFragment", this);
        fragment2.add(new Label("message", "fuga"));
        add(fragment2);
    }
}
