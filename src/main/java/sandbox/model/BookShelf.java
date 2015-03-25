package sandbox.model;

import java.util.ArrayList;
import java.util.List;

public class BookShelf {
    List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void add(Book book) {
        books.add(book);
    }
}
