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

    private Double unitPrice;

    public OrderDetail() {
        this.unitPrice = 0.0;
    }

    public OrderDetail(int id, int quantity, Book book, Bill bill, Double unitPrice) {
        this.id = id;
        this.quantity = quantity;
        this.book = book;
        this.bill = bill;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(int quantity, Book book, Bill bill, Double unitPrice) {
        this.quantity = quantity;
        this.book = book;
        this.bill = bill;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(int id, int quantity, Book book) {
        this.id = id;
        this.quantity = quantity;
        this.book = book;
        if(book != null){
            double discount = (book.getDiscountCampaign() != null) 
                    ? book.getDiscountCampaign().getPercentDiscount() : 0.0;
            this.unitPrice = book.getSellingPrice() * (1 - discount);
        }
        else
            this.unitPrice = 0.0;
    }

    public OrderDetail(int quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
        if(book != null){
            double discount = (book.getDiscountCampaign() != null) 
                    ? book.getDiscountCampaign().getPercentDiscount() : 0.0;
            this.unitPrice = book.getSellingPrice() * (1 - discount);
        }
        else
            this.unitPrice = 0.0;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Double getTotalPrice() {
        return (unitPrice != null ? unitPrice : 0) * quantity;
    }
}
