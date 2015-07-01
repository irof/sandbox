package sandbox.misc;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import sandbox.misc.switching.LabelOrTextField;

/**
 * いろいろごった煮。
 *
 * @author irof
 */
public class MiscPage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ButtonPanel("panel.button"));
        add(new ListPanel("panel.list"));

        Model<String> hoge1 = Model.of("hogehoge");
        add(new LabelOrTextField("panel.switching.input", hoge1, Model.of(true)));
        add(new LabelOrTextField("panel.switching.label", Model.of("fugafuga"), Model.of(false)));
    }
}
