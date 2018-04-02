package exam.rest;

import exam.database.EntityManager;
import exam.model.Author;
import exam.model.Book;

import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Root element (exposed at "authors" path)
 */
@Path("authorManager")
@Produces({"application/json", "application/xml"})
public class AuthorManager {

    /**
     * INTERN STATE
     */
    /**
     * VARIABLES
     */
    private static List<Author> localListAuthor;

    /**
     * JPQL QUERIES
     */
    private static Query findAllAuthors;

    /**
     * Static bloc called on class onload
     */
    static{
        findAllAuthors  = EntityManager.getEntityManager().createQuery("SELECT a FROM Author a");
        localListAuthor = findAllAuthors.getResultList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("authors")
    public List<Author> getAllAuthors(){
        List<Author> resultList;
        System.out.println("User trying to get all authors.");
        resultList = findAllAuthors.getResultList();
        System.out.println(resultList.size() + " authors found.");
        return resultList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("authors/{id}")
    public Author getAuthorById(@PathParam("id")int authorId){
        System.out.println("Trying to get author by id " + authorId);
        Response rp = null;
        //Author auth = EntityManager.getEntityManager().find(Author.class, authorId);
        Author auth = (Author) EntityManager.getEntityManager().createQuery("SELECT a FROM Author a WHERE a.id=:id").setParameter("id", authorId).getSingleResult();
        while(auth != null){
            System.out.println(auth.toString());
        }
        //rp = Response.status(200).entity(auth).build();
        return auth;
    }

    @PUT
    @Path("authors")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateAuthor(Author auth){
        for (Author p:localListAuthor) {
            if(p.getId() == auth.getId()){
                EntityManager.getEntityTransaction().begin();
                EntityManager.getEntityManager().refresh(p);
                EntityManager.getEntityTransaction().commit();
            }
        }
    }

    @POST
    @Path("authors")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author auth) {
        String result = "POST retrieved " + auth;
        System.out.println(result + " created");
        EntityManager.getEntityTransaction().begin();
        EntityManager.getEntityManager().persist(auth);
        EntityManager.getEntityTransaction().commit();
        return Response.status(201).entity(result).build();
    }

    @DELETE
    @Path("delete/{id}")
    public void deleteAuthor(@PathParam("id") int authId) {
        System.out.println("Person with id " + authId + " has been deleted.");
        localListAuthor.remove(authId);
        Author a = EntityManager.getEntityManager().find(Author.class, authId);
        EntityManager.getEntityTransaction().begin();
        EntityManager.getEntityManager().remove(a);
        EntityManager.getEntityTransaction().commit();
    }

}
