package sandbox.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookShelf implements Serializable {
    ArrayList<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void add(Book book) {
        books.add(book);
    }
}
