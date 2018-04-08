package exam;


import exam.database.EntityManager;
import exam.model.Author;
import exam.model.Book;
import exam.model.Catalog;

import org.apache.log4j.PatternLayout;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * MAIN CLASS
 */
public class App {
    @SuppressWarnings("unused")
    private static final Class[] shadeHack = {org.apache.log4j.RollingFileAppender.class,
            org.apache.log4j.ConsoleAppender.class,
            PatternLayout.class};

    static{
        EntityManager.getInstance();
    }

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://192.168.43.150:8282/";
    //public static final String BASE_URI = "http://192.168.1.28:8282/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in rest package
        final ResourceConfig rc = new ResourceConfig().packages("exam").register(new LoggingFeature(java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.SEVERE, LoggingFeature.Verbosity.PAYLOAD_ANY, Integer.MAX_VALUE));


        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {

        // Starting to parse the data from xml file
        startSTAXParsing();

        // Start jersey server for REST api.
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        System.out.println("You typed something, server stop will be executed.");
        server.stop();

    }

    public static void startSTAXParsing(){
        URL url = null;
        try {
            // Opening a file using a stream to read it
            url = new URL("file:///H:/Master/Master 1 - Toulon/D24/Exam_CI_D23/src/main/resources/books.xml");
            InputStream in = url.openStream();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser = factory.createXMLStreamReader(in);
            // String to get final tree of xml file
            StringBuilder sb= new StringBuilder(), tempSb = new StringBuilder();
            // To get path of each iterated element
            List<StringBuilder> listPathsElements = new ArrayList<>();

            // Instantiating all the necessary objects to build our Java objects
            Catalog fetchedCatalog = new Catalog();
            List<Book> listBooks = new ArrayList<>();
            List<Author> listAuthors = new ArrayList<>();
            Book fetchedBook = new Book();
            Author fetchedAuthor = new Author();

            // STAX PARSING STARTED
            System.out.println("STAX PARSER STARTED");
            for (int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next()) {
                //System.out.println("ELEMENT IN DOCUMENT FOUND");
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        sb.append(parser.getLocalName() + " -> ");
                        if(parser.getLocalName().equalsIgnoreCase("Catalog")){
                            System.out.println("CATALOG FOUND");
                        }
                        if(parser.getLocalName().equalsIgnoreCase("Book")){
                            System.out.println("BOOK FOUND");
                            // Iterate every attribute of the element
                            for(int i = 0; i < parser.getAttributeCount(); i++){
                                if(parser.getAttributeName(i).toString().equalsIgnoreCase("id")){
                                    fetchedBook.setId(parser.getAttributeValue(i).toString());
                                } // PAS DE RECHERCHE D'ATTRIBUT PAR VALEUR ?

                            }
                        }
                        if(parser.getLocalName().equalsIgnoreCase("author")){
                            fetchedAuthor.setName(parser.getElementText());
                            listAuthors.add(fetchedAuthor);
                            fetchedAuthor = new Author();
                        }
                        if(parser.getLocalName().equalsIgnoreCase("title")){
                            fetchedBook.setTitle(parser.getElementText());
                        }
                        if(parser.getLocalName().equalsIgnoreCase("genre")){
                            fetchedBook.setType(parser.getElementText());
                        }
                        if(parser.getLocalName().equalsIgnoreCase("price")){
                            fetchedBook.setPrice(Double.valueOf(parser.getElementText()));
                        }
                        if(parser.getLocalName().equalsIgnoreCase("publish_date")){
                            fetchedBook.setPublishedDate(parser.getElementText());
                        }
                        if(parser.getLocalName().equalsIgnoreCase("description")){
                            fetchedBook.setDescription(parser.getElementText());
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        // Reinitializing the objects before getting a new one
                        if(fetchedBook.getDescription() != null){
                            listBooks.add(fetchedBook);
                            fetchedBook.setListAuthor(listAuthors);
                            listAuthors = new ArrayList<>();
                            fetchedBook = new Book();
                            System.out.println("book and listAuthors reinitialisées");
                        }
                        System.out.println("book saved");
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        break;
                    case XMLStreamConstants.CDATA:
                        break;
                }



            }
            parser.close();

            // Displaying final results to check if parsing succeed
            System.out.println(listBooks.toString());
            fetchedCatalog.setListBooks(listBooks);
            System.out.println("ListBooks size : " + listBooks.size());
            System.out.println("ListAuthors size : " + listAuthors.size());

            persistData(fetchedCatalog);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method used to persist fetched data from xml to db
     * @param catalogToPersist
     */
    public static void persistData(Catalog catalogToPersist){

        // Trying to persist only one object to persis everything thanks to cascades PERSIST
        EntityManager.getEntityTransaction().begin();
        EntityManager.getEntityManager().persist(catalogToPersist);
        EntityManager.getEntityTransaction().commit();

        // To debug cascade persist, iterate each object
        /*for(Book b : catalogToPersist.getListBooks()){
            //System.out.println(b.toString());
            //EntityManager.getEntityManager().persist(b);
            for(Author a : b.getListAuthor()){
                EntityManager.getEntityTransaction().begin();
                //EntityManager.getEntityManager().persist(a);
                EntityManager.getEntityTransaction().commit();
            }
        }*/

    }
}
