package model;

import jakarta.persistence.*;

import java.io.Serializable;

//note: tinh nang ma giam gia
@Entity
@Table(name="OrderDetail")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;

    //hibernate
    @ManyToOne()
    @JoinColumn(name="bookID")
    private Book book;
    
    @ManyToOne()
    @JoinColumn(name="billID")
    private Bill bill;

    public OrderDetail() {

    }

    public OrderDetail(int id, int quantity, Book book) {
        this.id = id;
        this.quantity = quantity;
        this.book = book;
    }
    

    public OrderDetail(int quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
}
