package sandbox.model;

import java.io.Serializable;

public class Book implements Serializable {
    String author;
    String title;
    int pages;

    Book(String author, String title, int pages) {
        this.author = author;
        this.title = title;
        this.pages = pages;
    }
}
