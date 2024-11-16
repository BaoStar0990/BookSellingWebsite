package model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String phonenumber;
    private String street;
    private String province;
    private String ward;
    private String district;
    private boolean defaultAddress;

    //Với bảng customer
    @ManyToOne()
    @JoinColumn(name = "customerID")
    private Customer customer;

    public Address(int id, String fullName, String phonenumber,
            String street, String province, String ward, String district, 
            boolean defaultAddress, Customer customer) {
        this.id = id;
        this.fullName = fullName;
        this.phonenumber = phonenumber;
        this.street = street;
        this.province = province;
        this.ward = ward;
        this.district = district;
        this.defaultAddress = defaultAddress;
        this.customer = customer;
    }

    public Address(String fullName, String phonenumber, String street,
            String province, String ward, String district, 
            boolean defaultAddress, Customer customer) {
        this.fullName = fullName;
        this.phonenumber = phonenumber;
        this.street = street;
        this.province = province;
        this.ward = ward;
        this.district = district;
        this.defaultAddress = defaultAddress;
        this.customer = customer;
    }

    public Address() {

    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getWard() {
        return ward;
    }
    public void setWard(String ward) {
        this.ward = ward;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    
    
    public String createJson() {
        return "{"
                + "\"id\":" + id + ","
                + "\"fullName\":\"" + fullName + "\","
                + "\"phonenumber\":\"" + phonenumber + "\","
                + "\"street\":\"" + street + "\","
                + "\"ward\":\"" + ward + "\","
                + "\"district\":\"" + district + "\","
                + "\"province\":\"" + province + "\""
                + "}";
    }

    @Override
    public String toString(){
        return  street + ", " + ward + ", " + district + ", " + province;
    }
}
