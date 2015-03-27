package sandbox.model;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SimplePageTest {

    WicketTester tester = new WicketTester();

    @Test
    public void instantiation() {
        SimplePage page = new SimplePage();
        tester.startPage(page);
    }

    @Test
    public void addBook() throws Exception {
        tester.startPage(SimplePage.class);

        FormTester form = tester.newFormTester("form");
        form.setValue("title", "幻の本");
        form.setValue("author", "不明");
        form.setValue("pages", "9999");

        form.submit("add");

        List<?> list = Arrays.asList(new Book("俺の本", "俺", 100), new Book("彼の本", "彼", 110), new Book("幻の本", "不明", 9999));
        tester.assertListView("form:books", list);
    }
}
