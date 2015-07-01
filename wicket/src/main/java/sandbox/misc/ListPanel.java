package sandbox.misc;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.ArrayList;
import java.util.List;

public class ListPanel extends Panel {

    private final List<String> list = new ArrayList<>();

    public ListPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        RepeatingView repeatingList = new RepeatingView("repeatingList");
        repeatingList.add(new Label(repeatingList.newChildId(), "repeating1"));
        repeatingList.add(new Label(repeatingList.newChildId(), "repeating2"));
        add(repeatingList);

        add(new ListView<String>("list", list) {
            @Override
            protected void populateItem(ListItem<String> item) {
                item.add(new Label("item", item.getModelObject()));
            }
        });

        Form<Void> form = new Form<Void>("form");
        form.add(new Button("singleton") {
            @Override
            public void onSubmit() {
                list.clear();
                list.add("hoge");
            }
        });
        add(form);
    }
}
