package sandbox.misc.switching;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * なんか条件によってLabelかTextFieldかを切り替えるパネルさん。
 */
public class LabelOrTextField extends Panel {

    public LabelOrTextField(String id, Model<String> model, Model<Boolean> canEdit) {
        super(id);

        add(new Label("label", model) {
            @Override
            protected void onConfigure() {
                setVisibilityAllowed(!canEdit.getObject());
            }
        });
        add(new TextField<String>("textField", model) {
            @Override
            protected void onConfigure() {
                setVisibilityAllowed(canEdit.getObject());
            }
        });
    }
}
