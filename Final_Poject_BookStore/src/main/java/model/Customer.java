package model;

import dbmodel.BillDB;
import dbmodel.CustomerDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Customer")
public class Customer extends User implements Serializable {
    //many address, cập nhật tại customer thì address cx cập nhật
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private Set<Address> addresses;

    //many reviews, xóa khách hàng thì xóa reviews
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Review> reviews;


    //many bill, xóa khách hàng thì xóa bill
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private Set<Bill> bills;
    
    public Customer() {
    }

    public Customer(int id, String username, String password, String fullName, int age, String numberPhone, String email) {
        super(id, username, password, fullName, age, numberPhone, email);
        addresses = new HashSet<>();
    }

    public Customer(String username, String password, String fullName, int age, String numberPhone, String email) {
        super(username, password, fullName, age, numberPhone, email);
        addresses = new HashSet<>();
    }

    public Customer(String username, String password) {    
        super(username, password);
        addresses = new HashSet<>();
    }

    public Set<Address> getAddresses() {
        return CustomerDB.getInstance().getAddressCustomer(this);
    }
    public void setAddresses(Set<Address> addresses) {
        for(Address a : addresses){
            this.addAddress(a);
        }
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Set<Bill> getBills() {
        return CustomerDB.getInstance().getBillsCustomer(this);
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }
    public void addAddress(Address a){
        addresses.add(a);
        a.setCustomer(this);
    }
    
    // chức năng đặt hàng
    public boolean makeAnOrder(Bill cart){
        return CustomerDB.getInstance().makeAnOrder(cart, this);
    }
    // chức năng thêm vào giỏ hàng
    public boolean addToCart(OrderDetail orderDetail){
        
        return true;
    }
}
