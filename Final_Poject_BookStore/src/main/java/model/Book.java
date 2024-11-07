package model;

import dbmodel.AuthorDB;
import dbmodel.BookDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="Book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookID;
    private String title;
    @Lob
    private String description;
    private String Isbn;
    private Double costPrice; // ?
    private Double sellingPrice; // ?
    private int stocks;
    private String urlImage;
    
    private int publishYear;
    private String language;

    //hibernate, xóa sách thì xóa reviews
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Review> reviews; // = new ArrayList<Review>();

    //hibernate, xóa sách không xóa tác giả, chèn và cập nhật sách thì cập nhật bên tác giả
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER, 
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Author> authors; // = new ArrayList<Author>();


    //one Category, xóa sách không xóa thể loại
    @ManyToOne()
    @JoinColumn(name="categoryID")
    private Category category;

    //one Publisher, xóa sách không xóa nhà xuất bản
    @ManyToOne()
    @JoinColumn(name="publisherID")
    private Publisher publisher;

    //many orderdetail
    @OneToMany(mappedBy = "book")
    private Set<OrderDetail> orderDetails; // = new HashSet<OrderDetail>();

    //many discount detail
    @ManyToOne()
    @JoinColumn(name = "campaignID")
    private DiscountCampaign discountCampaign;
    public Book() {

    }
    public Book(String title, Publisher publisher, 
            int bookID, String description, String isbn, Double costPrice, 
            Double sellingPrice, int stocks, String urlImage, Category category, 
            int publishYear, String language) {
        this.title = title;
        this.publisher = publisher;
        this.bookID = bookID;
        this.description = description;
        this.Isbn = isbn;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.stocks = stocks;
        this.urlImage = urlImage;
        this.category = category;
        this.publishYear = publishYear;
        this.language = language;
    }

    public Book(String title, String description, String Isbn, 
            Double costPrice, Double sellingPrice, int stocks, String urlImage, 
            int publishYear, String language, Category category, Publisher publisher) {
        this.title = title;
        this.description = description;
        this.Isbn = Isbn;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.stocks = stocks;
        this.urlImage = urlImage;
        this.publishYear = publishYear;
        this.language = language;
        this.category = category;
        this.publisher = publisher;
    }
    
    public String getTitle() {
        return title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return bookID;
    }

    public String getIsbn() {
        return Isbn;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public Category getCategory() {
        return category;
    }

    public int getStocks() {
        return stocks;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int bookID) {
        this.bookID = bookID;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsbn(String isbn) {
        Isbn = isbn;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPublishDate(int publishYear) {
        this.publishYear = publishYear;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public DiscountCampaign getDiscountCampaign() {
        return discountCampaign;
    }

    public void setDiscountCampaign(DiscountCampaign discountCampaign) {
        this.discountCampaign = discountCampaign;
    }
    
    public List<Review> getReviews() {
        return BookDB.getInstance().getReviews(this);
    }
    
    public List<Author> getAuthors() {
        return authors;
    }
        
    // bussiness    
}
