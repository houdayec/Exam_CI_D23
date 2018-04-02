package exam.model;

import javax.persistence.*;
import java.util.List;

@Entity

@Table(name = "Catalog")
public class Catalog {

    /**
     * INTERN STATE
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.PERSIST )
    private List<Book> listBooks;

    /**
     * CONSTRUCTORS
      */
    public Catalog() {
    }

    public Catalog(List<Book> listBooks) {
        this.listBooks = listBooks;
    }

    /**
     * GETTERS AND SETTERS
     */
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
        return "Catalog{" +
                "id=" + id +
                ", listBooks=" + listBooks +
                '}';
    }
}
