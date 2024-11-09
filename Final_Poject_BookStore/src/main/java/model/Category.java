package model;

import dbmodel.CategoryDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CategoryDetail",
            joinColumns = @JoinColumn(name = "categoryID"),
            inverseJoinColumns = @JoinColumn(name = "bookID")
    )
    private Set<Book> books = new HashSet<Book>();

    public Category() {

    }
    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;

    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Book> getBooks() {
        return CategoryDB.getInstance().getBooks(this);
    }
    
    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    public void addBook(Book b){
        this.books.add(b);
    }
}
