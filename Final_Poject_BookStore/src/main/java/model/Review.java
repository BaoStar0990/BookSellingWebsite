package model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;
    private int rate;
    private String description;
    
    // xóa review không xóa customer
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="customerID")
    private Customer customer;
    
    // xóa review không xóa book
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="bookID")
    private Book book;


    //constructor
    public Review() {

    }
    public Review(int reviewID, String description, Customer customer, Book book) {
        this.reviewID = reviewID;
        this.description = description;
        this.customer = customer;
        this.book = book;
    }
    public Review(String description, Customer customer, Book book) {
        this.description = description;
        this.customer = customer;
        this.book = book;
    }
    
    //get & set
    
    public int getReviewID() {
        return reviewID;
    }
    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public int getRate() {
        return rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
}
