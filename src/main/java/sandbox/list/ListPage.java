package sandbox.list;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.Collections;
import java.util.List;

public class ListPage extends WebPage {

    public ListPage() {
        List<?> list = Collections.emptyList();
        add(new ListView("list", list) {
            @Override
            protected void populateItem(ListItem item) {

            }
        });
    }
}
