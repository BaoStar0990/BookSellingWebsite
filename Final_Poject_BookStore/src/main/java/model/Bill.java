package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billID;
    private String paymentMethod;
    private String statusPayment;
    private Double totalDiscountCost;
    private Double totalCost;
    private Double VAT;
    private Date orderDate;
    private Date deliveryDate;


    //hibernate, xóa bill không xóa customer; cập nhật, chèn bill thì tự thay đổi bên customer 
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="customerID")
    private Customer customer;

    //many orderdetails, tất cả các thay đổi ở Order sẽ ảnh hưởng đến OrderDetail
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();


    public Bill() {
    }
    public Bill(int billID,Customer customer,String paymentMethod,Double totalDiscountCost,Double totalCost,Double VAT,Date orderDate,Date deliveryDate) {
        this.billID = billID;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.totalDiscountCost = totalDiscountCost;
        this.totalCost = totalCost;
        this.VAT = VAT;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }

    public Bill(String paymentMethod, String statusPayment, Double totalDiscountCost, Double totalCost, Double VAT, Date orderDate, Date deliveryDate, Customer customer) {
        this.paymentMethod = paymentMethod;
        this.statusPayment = statusPayment;
        this.totalDiscountCost = totalDiscountCost;
        this.totalCost = totalCost;
        this.VAT = VAT;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.customer = customer;
    }
    
    public int getId() {
        return billID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public Double getTotalDiscountCost() {
        return totalDiscountCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Double getVAT() {
        return VAT;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setId(int billID) {
        this.billID = billID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderDetails(HashSet<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public void setTotalDiscountCost(Double totalDiscountCost) {
        this.totalDiscountCost = totalDiscountCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
