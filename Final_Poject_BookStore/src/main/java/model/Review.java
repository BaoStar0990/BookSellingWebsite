package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;
    private int rate;
    private String description;
    private Date reviewDate;

    // xóa review không xóa customer
    @ManyToOne()
    @JoinColumn(name="customerID")
    private Customer customer;
    
    // xóa review không xóa book
    @ManyToOne()
    @JoinColumn(name="bookID")
    private Book book;


    //constructor
    public Review() {

    }

    public Review(int reviewID,  int rate, String description, Date reviewDate , Customer customer, Book book) {
        this.reviewID = reviewID;
        this.rate = rate;
        this.description = description;
        this.customer = customer;
        this.book = book;
        this.reviewDate = reviewDate;
    }
    public Review(int rate, String description, Date reviewDate , Customer customer, Book book) {
        this.rate = rate;
        this.description = description;
        this.customer = customer;
        this.book = book;
        this.reviewDate = reviewDate;
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

    public Date getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
}
