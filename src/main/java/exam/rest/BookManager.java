package exam.rest;

import exam.Logger;
import exam.database.EntityManager;
import exam.model.Book;

import javax.persistence.PrePersist;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Root element (exposed at "books" path)
 */
@Path("bookManager")
@Produces({"application/json", "application/xml"})
public class BookManager {

    /**
     * INTERN STATE
     */
    /**
     * VARIABLES
     */
    private static List<Book> localBookList;
    /**
     * JPQL QUERIES
     */
    private static Query findAllBooks;


    /**
     * Static bloc called on class onload
     */
    static{
        // TODO ?
        findAllBooks  = EntityManager.getEntityManager().createQuery("SELECT b FROM Book b");
        localBookList = findAllBooks.getResultList();
        System.out.println("Taille liste locale " + localBookList.size());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("books")
    public List<Book> getAllBooks(){
        List<Book> resultList;
        System.out.println("User trying to get all books.");
        resultList = findAllBooks.getResultList();
        System.out.println(resultList.size() + " books found.");
        return resultList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("books/{id}")
    public Book getBookById(@PathParam("id") String bookId){
        Book fetchedBook;
        try{
            System.out.println("Trying to get book id : " + bookId);
            fetchedBook = (Book) EntityManager.getEntityManager().createQuery("SELECT b FROM Book b WHERE b.id=:bookId").setParameter("bookId",bookId).getSingleResult();
            System.out.println(fetchedBook.toString());
        }catch (Exception e){
            System.out.println(e);
            return new Book();
        }

        return fetchedBook;
    }

    @PUT
    @Path("books")
    @Consumes("application/json")
    public void updateBook(Book book){
        System.out.println("trying to put " + book.toString());
        for(Book bk : localBookList){
            if(bk.getId() == book.getId()){
                Book bookToUpdate = EntityManager.getEntityManager().find(Book.class, bk.getId());
                bookToUpdate = book;
                EntityManager.getEntityTransaction().begin();
                EntityManager.getEntityManager().merge(bookToUpdate);
                EntityManager.getEntityTransaction().commit();
                System.out.println("Book updated : " + bk.getId());
            }
        }

    }

    @POST
    @Path("books")
    @Consumes(MediaType.APPLICATION_JSON)
    public Book createBook(Book book){
        System.out.println("Trying to create " + book.toString());
        try{
            EntityManager.getEntityTransaction().begin();
            EntityManager.getEntityManager().persist(book);
            EntityManager.getEntityTransaction().commit();
            EntityManager.getEntityManager().refresh(book);
            System.out.println("Book created " + book.toString());
            localBookList.add(book);
            return book;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new Book();
        }
    }

    @DELETE
    @Path("books/{id}")
    public void deleteBook(@PathParam("id") String id){
        System.out.println("Trying to delete " + id);
            for(Book b : localBookList){
                System.out.println("Current book id : " + b.getId());
                if(b.getId().equals(id)){
                    Book bookToDelete = EntityManager.getEntityManager().find(Book.class, id);
                    EntityManager.getEntityTransaction().begin();
                    EntityManager.getEntityManager().remove(bookToDelete);
                    EntityManager.getEntityTransaction().commit();
                    System.out.println("Book deleted : " + id);
                }
            }
    }

    @PrePersist
    public void prePersist(){
        Logger.getInstance().log.info("Persist done !");
    }
}
