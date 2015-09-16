package sandbox.misc;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.CheckboxMultipleChoiceSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * CheckboxやRadioなど選択肢のやつ
 *
 * @author irof
 */
public class ChoicePanel extends Panel {


    public ChoicePanel(String id) {
        super(id);
        Form<Void> form = new Form<>("form");

        ListModel<String> selections = new ListModel<>(new ArrayList<>(Arrays.asList("AAB", "BAB")));

        CheckBoxMultipleChoice<String> groupA = new CheckBoxMultipleChoice<>("groupA",
                selections, Arrays.asList("AAA", "AAB", "AAC"));
        form.add(new CheckboxMultipleChoiceSelector("selectorGroupA", groupA));
        form.add(groupA);

        Component selectionsLabel = new Label("selections", selections);
        form.add(selectionsLabel.setOutputMarkupId(true));
        form.add(new AjaxButton("show") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                target.add(selectionsLabel);
            }
        });
        add(form);
    }
}
