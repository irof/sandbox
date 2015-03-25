package sandbox.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.PropertyModel;

public class SimplePage extends WebPage {

    public SimplePage() {
        BookShelf model = new BookShelf();
        model.add(new Book("俺の本", "俺", 100));
        model.add(new Book("彼の本", "彼", 110));

        add(new DataView<Book>("books", new ListDataProvider<>(model.getBooks())) {
            @Override
            protected void populateItem(Item<Book> item) {
                item.add(new Label("title", new PropertyModel<>(item.getModelObject(), "title")));
                item.add(new Label("author", new PropertyModel<>(item.getModelObject(), "author")));
                item.add(new Label("pages", new PropertyModel<>(item.getModelObject(), "pages")));
            }
        });
    }
}
