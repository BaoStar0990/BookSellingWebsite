package model;

import dbmodel.CustomerDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Customer")
public class Customer extends User implements Serializable {
    //many address, cập nhật tại customer thì address cx cập nhật
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    //many reviews, xóa khách hàng thì xóa reviews
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<Review>();


    //many bill, xóa khách hàng thì xóa bill
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private Set<Bill> bills = new HashSet<Bill>();
    
    public Customer() {
    }

    public Customer(int id, String username, String password, String fullName, int age, String numberPhone, String email) {
        super(id, username, password, fullName, age, numberPhone, email);
        addresses = new ArrayList<>();
    }

    public Customer(String username, String password, String fullName, int age, String numberPhone, String email) {
        super(username, password, fullName, age, numberPhone, email);
        addresses = new ArrayList<>();
    }

    public Customer(String username, String password) {    
        super(username, password);
        addresses = new ArrayList<>();
    }

    public List<Address> getAddresses() {
        return CustomerDB.getInstance().getAddressCustomer(this);
    }
    public void setAddresses(List<Address> addresses) {
        for(Address a : addresses){
            this.addAddress(a);
        }
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Set<Bill> getBills() {
        return CustomerDB.getInstance().getBillsCustomer(this);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }
    public void addAddress(Address a){
        addresses.add(a);
        a.setCustomer(this);
    }
    
    // chức năng đặt hàng
    public boolean makeAnOrder(HashSet<OrderDetail> orderDetails){
        
        return true;
    }
    // chức năng thêm vào giỏ hàng
    public boolean addToCart(OrderDetail orderDetail){
        
        return true;
    }
}
