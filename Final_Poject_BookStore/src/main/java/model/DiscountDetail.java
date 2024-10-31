package model;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
public class DiscountDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discountId;
    private Double percentDiscount;

    //hibernate

    //one discount campaign
    @ManyToOne
    @JoinColumn(name="discoundCampagnID")
    private DiscountCampaign discoundCampaign;
    //one book
    @ManyToOne
    @JoinColumn(name="bookID")
    private Book book;


    public DiscountDetail() {

    }
    public DiscountDetail(int discountId, Book book, Double percentDiscount) {
        this.discountId = discountId;
        this.book = book;
        this.percentDiscount = percentDiscount;
    }

    public DiscountDetail(Double percentDiscount, DiscountCampaign discoundCampaign, Book book) {
        this.percentDiscount = percentDiscount;
        this.discoundCampaign = discoundCampaign;
        this.book = book;
    }
    
    public int getDiscountId() {
        return discountId;
    }
    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public Double getPercentDiscount() {
        return percentDiscount;
    }
    public void setPercentDiscount(Double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
}
