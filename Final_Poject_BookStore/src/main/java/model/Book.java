package model;

import dbmodel.BookDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookID;
    private String title;
    @Lob
    private String description;
    private String Isbn;
    private Double costPrice;
    private Double sellingPrice;
    private int stocks;
    private String urlImage;
    private int publishYear;
    private String language;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private Set<Review> reviews;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "AuthorDetail",
            joinColumns = @JoinColumn(name = "bookID"),
            inverseJoinColumns = @JoinColumn(name = "authorID")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "CategoryDetail",
            joinColumns = @JoinColumn(name = "bookID"),
            inverseJoinColumns = @JoinColumn(name = "categoryID")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "publisherID", nullable = true)
    private Publisher publisher;

    @OneToMany(mappedBy = "book")
    private Set<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "campaignID", nullable = true)
    private DiscountCampaign discountCampaign;

    public Book() {
    }

    public Book(String title, String description, String Isbn, Double costPrice, Double sellingPrice, int stocks, String urlImage, int publishYear, String language, Publisher publisher) {
        this.title = title;
        this.description = description;
        this.Isbn = Isbn;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.stocks = stocks;
        this.urlImage = urlImage;
        this.publishYear = publishYear;
        this.language = language;
        this.publisher = publisher;
    }

    public Book(String title, Publisher publisher,
            int bookID, String description, String isbn, Double costPrice,
            Double sellingPrice, int stocks, String urlImage,
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
        this.publishYear = publishYear;
        this.language = language;
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

    public Set<Category> getCategories() {
        return BookDB.getInstance().getCategories(this);
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

    public Set<Review> getReviews() {
        return BookDB.getInstance().getReviews(this);
    }


    public double getAverageRatingStar() {
        int numberOfReview = this.getReviews().size();
        if (numberOfReview == 0) {
            return 0;
        }
        int totalRating = 0;
        for (Review review : this.getReviews()) {
            totalRating += review.getRate();
        }
        return (double) totalRating / numberOfReview;
    }

    public Set<Author> getAuthors() {
        return BookDB.getInstance().getAuthors(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(bookID, book.getId()); // Giả sử `id` là khóa chính
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.bookID;
        return hash;
    }

    public void setAuthors(Set<Author> authors) {
        for (Author a : authors) {
            this.addAuthor(a);
        }
    }

    public void addAuthor(Author a) {
        this.authors.add(a);
        a.addBook(this);
    }

    public void setCategories(Set<Category> categories) {
        for (Category c : categories) {
            this.addCategory(c);
        }
    }

    public void addCategory(Category c) {
        this.categories.add(c);
        c.addBook(this);
    }

    public int getBookID() {
        return bookID;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }


}
