package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Customer")
public class Customer extends User implements Serializable {
    //many address, cập nhật tại customer thì address cx cập nhật
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    //many reviews, xóa khách hàng thì xóa reviews
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<Review>();


    //many bill, xóa khách hàng thì xóa bill
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Bill> bills = new ArrayList<Bill>();
    
    public Customer() {
    }

    public Customer(int id, String username, String password, String fullName, int age, String numberPhone, String email) {
        super(id, username, password, fullName, age, numberPhone, email);
        addresses = new ArrayList<Address>();
    }

    public Customer(String username, String password, String fullName, int age, String numberPhone, String email) {
        super(username, password, fullName, age, numberPhone, email);
        addresses = new ArrayList<Address>();
    }

//    public Customer() {
//        super();
//        addresses = new ArrayList<Address>();
//    }
//    public Customer(int id, String username, String password,String fullName, int age, String numberPhone, String email) {
//        super(id,username,password,"user",  fullName,  age,  numberPhone,  email);
//        addresses = new ArrayList<Address>();
//    }
    public Customer(String username, String password) {    
        super(username, password);
        addresses = new ArrayList<Address>();
    }

    public List<Address> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
    
}
