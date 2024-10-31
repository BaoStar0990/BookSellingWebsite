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
    // true: đặt hàng, false: giỏ hàng
    private boolean statusOrder;
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

    public OrderDetail(int id, boolean statusOrder, int quantity, Book book) {
        this.id = id;
        this.statusOrder = statusOrder;
        this.quantity = quantity;
        this.book = book;
    }
    

    public OrderDetail(boolean statusOrder, int quantity, Book book) {
        this.statusOrder = statusOrder;
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
    public boolean getStatusOrder() {
        return statusOrder;
    }
    public void setStatusOrder(boolean statusOrder) {
        this.statusOrder = statusOrder;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatusOrder() {
        return statusOrder;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
}
