package model;

import jakarta.persistence.*;
import model.Customer;
import java.io.Serializable;

@Entity
@Table(name="Address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private String province;
    private String ward;
    private String district;

    //Với bảng customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID")
    private Customer customer;


    public Address(String street, String province, String ward, String district) {
        this.street = street;
        this.province = province;
        this.ward = ward;
        this.district = district;
    }
    public Address() {

    }

    public Address(String street, String province, String ward, String district, Customer customer) {
        this.street = street;
        this.province = province;
        this.ward = ward;
        this.district = district;
        this.customer = customer;
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

}
