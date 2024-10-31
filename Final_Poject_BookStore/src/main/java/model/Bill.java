package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billID;
    private String paymentMethod;
    private String statusPayment;
    private Double VAT;
    private LocalDate orderDate;
    private LocalDate deliveryDate;


    //hibernate, xóa bill không xóa customer; cập nhật, chèn bill thì tự thay đổi bên customer 
    @ManyToOne()
    @JoinColumn(name="customerID")
    private Customer customer;

    //many orderdetails, tất cả các thay đổi ở Order sẽ ảnh hưởng đến OrderDetail
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();


    public Bill() {
    }
    public Bill(int billID,Customer customer,String paymentMethod, Double VAT
            ,LocalDate orderDate,LocalDate deliveryDate ) {
        this.billID = billID;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.VAT = VAT;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        orderDetails = new HashSet<>();
    }

    public Bill(String paymentMethod, String statusPayment, Double VAT,
            LocalDate orderDate, LocalDate deliveryDate, Customer customer) {
        this.paymentMethod = paymentMethod;
        this.statusPayment = statusPayment;
        this.VAT = VAT;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.customer = customer;
        orderDetails = new HashSet<>();
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

    public Double getVAT() {
        return VAT;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setId(int billID) {
        this.billID = billID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderDetails(HashSet<OrderDetail> orderDetails) {
        for(OrderDetail order : orderDetails){
            this.addOrderDetail(order);
        }
    }
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setBill(this);
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
