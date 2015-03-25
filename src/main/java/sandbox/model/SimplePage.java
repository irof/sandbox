package sandbox.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class SimplePage extends WebPage {

    public SimplePage() {
        BookShelf model = new BookShelf();
        model.add(new Book("俺の本", "俺", 100));
        model.add(new Book("彼の本", "彼", 110));

        Form form = new Form("form");
        add(form);
        form.add(new DataView<Book>("books", new ListDataProvider<>(model.getBooks())) {
            @Override
            protected void populateItem(Item<Book> item) {
                item.add(new Label("title", new PropertyModel<>(item.getModelObject(), "title")));
                item.add(new Label("author", new PropertyModel<>(item.getModelObject(), "author")));
                item.add(new Label("pages", new PropertyModel<>(item.getModelObject(), "pages")));
            }
        });

        Model<Book> b = new Model<>(new Book("彼の本", "彼", 110));
        form.add(new TextField<>("title", b));
        form.add(new TextField<>("author", b));
        form.add(new TextField<>("pages", b));

        form.add(new Button("add"));
    }
}
