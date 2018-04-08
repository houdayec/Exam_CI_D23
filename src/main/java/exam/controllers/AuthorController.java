package exam.controllers;

import exam.model.Author;

public class AuthorController {
    private static AuthorController ourInstance = new AuthorController();

    public static AuthorController getInstance() {
        return ourInstance;
    }

    private AuthorController() {
    }

    public void addAuthor(Author author){

    }

    public void updateAuthor(Author author){

    }

    public void deleteAuthor(String id){

    }
}
