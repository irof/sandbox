package sandbox.structure;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;

/**
 * Fragmentのサンプル。
 *
 * @author irof
 */
public class FragmentSamplePage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // 同じフラグメントを2回入れる
        Fragment fragment1 = new Fragment("target1", "myFragment", this);
        fragment1.add(new Label("message", "hoge"));
        add(fragment1);
        Fragment fragment2 = new Fragment("target2", "myFragment", this);
        fragment2.add(new Label("message", "fuga"));
        add(fragment2);
    }
}
