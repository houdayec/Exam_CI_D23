package exam.controllers;

import exam.model.Book;

public class BookController {
    private static BookController ourInstance = new BookController();

    public static BookController getInstance() {
        return ourInstance;
    }

    private BookController() {
    }

    public void addBook(Book bookToAdd){

    }

    public void deleteBook(String id){

    }

    public void updateBook(Book book){

    }

}
