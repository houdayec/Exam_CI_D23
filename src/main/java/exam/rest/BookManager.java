package exam.rest;

import exam.database.EntityManager;
import exam.model.Book;

import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
