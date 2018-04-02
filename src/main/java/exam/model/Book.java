package exam.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Book")
public class Book {

    /**
     * INTERN STATE
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToMany( cascade = CascadeType.PERSIST )
    @JoinTable(name = "AUTHOR_BOOK")
    private List<Author> listAuthor;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "publish_date")
    private String publishedDate;

    @Column(name = "description")
    private String description;

    //@Column(name = "catalog")
    @ManyToOne
    @JoinColumn ( name = "CATALOG_ID" )
    private Catalog catalog;

    /**
     * CONSTRUCTORS
     */
    public Book() {
        listAuthor = new ArrayList<>();
    }

    public Book(String title) {
        this.title = title;
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Author> getListAuthor() {
        return listAuthor;
    }

    public void setListAuthor(List<Author> author) {
        this.listAuthor = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * OVERRIDED METHODS
     */
    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", listAuthor=" + listAuthor +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", publishedDate='" + publishedDate + '\'' +
                ", description='" + description + '\'' +
                ", catalog=" + catalog +
                '}';
    }
}
