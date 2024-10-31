package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="author")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorID;
    private String description;
    private String imageURL;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "AuthorDetail",
            joinColumns = @JoinColumn(name = "authorID"),
            inverseJoinColumns = @JoinColumn(name = "bookID")
    )
    private Set<Book>  books   = new HashSet<Book>();


    public Author() {

    }
    public Author(String name,String description,String imageURL) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
    }

    public Author(String name) {
        this.name = name;
    }
    
    public int getId() {
        return authorID;
    }
    public void setId(int id) {
        this.authorID = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void addBook(Book b){
        this.books.add(b);
    }
}
