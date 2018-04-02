package exam.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    /**
     * INTERN STATE
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToMany (mappedBy = "listAuthor")
    private List<Book> listBooks;

    /**
     * CONSTRUCTORS
     */
    public Author() {
        listBooks = new ArrayList<>();
    }

    public Author(String name) {
        this.name = name;
    }

    /**
     * GETTERS AND SETTERS
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Book> getListBooks() {
        return listBooks;
    }

    public void setListBooks(List<Book> listBooks) {
        this.listBooks = listBooks;
    }

    /**
     * OVERRIDED METHODS
     */
    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", listBooks=" + listBooks +
                '}';
    }
}
