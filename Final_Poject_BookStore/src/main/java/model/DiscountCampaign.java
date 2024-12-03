package model;

import dbmodel.DiscountCampaignDB;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Entity
public class DiscountCampaign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignId;
    private String campaignName;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private Double percentDiscount;
    
    @OneToMany(mappedBy = "discountCampaign", cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();
    
    public DiscountCampaign(){
        
    }
    public DiscountCampaign(int campaignId, String campaignName, LocalDate startDate, LocalDate endDate, Double percentDiscount) {
        this.campaignId = campaignId;
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentDiscount = percentDiscount;
        this.books = new HashSet<>();
    }

    public DiscountCampaign(String campaignName, LocalDate startDate, LocalDate endDate, Double percentDiscount) {
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentDiscount = percentDiscount;
        this.books = new HashSet<>();
    }

    public int getCampaignId() {
        return campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Double getPercentDiscount() {
        return percentDiscount;
    }

    public Set<Book> getBooks() {
        return DiscountCampaignDB.getInstance().getBooks(this);
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPercentDiscount(Double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public void setBooks(Set<Book> books) {
        for(Book b : books){
            this.addBook(b);
        }
    }
    
    public void addBook(Book b){
        books.add(b);
        b.setDiscountCampaign(this);
    }
    
}
