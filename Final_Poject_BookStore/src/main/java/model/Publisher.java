package model;

import dbmodel.PublisherDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Publisher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "publisher",  cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<Book>();

    public Publisher(){

    }
    public Publisher(int id, String name) {
        this.id = id;
        this.name = name;

    }
    public Publisher(String name) {
        this.name = name;
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

    public Set<Book> getBooks() {
        return PublisherDB.getInstance().getBooks(this);
    }
    
}
