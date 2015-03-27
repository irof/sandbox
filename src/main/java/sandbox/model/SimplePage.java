package sandbox.model;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class SimplePage extends WebPage {

    public SimplePage() {
        final BookShelf model = new BookShelf();
        model.add(new Book("俺の本", "俺", 100));
        model.add(new Book("彼の本", "彼", 110));

        Form form = new Form("form");
        add(form);
        form.add(new ListView<Book>("books", model.getBooks()) {
            @Override
            protected void populateItem(ListItem<Book> item) {
                item.add(new Label("title", new PropertyModel<>(item.getModelObject(), "title")));
                item.add(new Label("author", new PropertyModel<>(item.getModelObject(), "author")));
                item.add(new Label("pages", new PropertyModel<>(item.getModelObject(), "pages")));
            }
        });

        final TextField<String> title = new TextField<>("title", Model.of());
        form.add(title);
        final TextField<String> author = new TextField<>("author", Model.of());
        form.add(author);
        final TextField<String> pages = new TextField<>("pages", Model.of());
        form.add(pages);

        form.add(new Button("add") {
            @Override
            public void onSubmit() {
                model.add(new Book(title.getModelObject(), author.getModelObject(),
                        // TODO うまいやりかた。。。setTypeはなんか微妙
                        Integer.valueOf(pages.getModelObject())));
            }
        });
    }
}
