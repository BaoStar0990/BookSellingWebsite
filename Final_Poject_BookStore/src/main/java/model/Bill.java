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
    
    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;
    @Enumerated(EnumType.STRING)   
    private StatusOrder statusOrder;
    private Double VAT;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String shippingAddress;
    private Double shippingFee;
    @Lob
    private String notes;

    //hibernate, xóa bill không xóa customer; cập nhật, chèn bill thì tự thay đổi bên customer 
    @ManyToOne()
    @JoinColumn(name="customerID")
    private Customer customer;

    //many orderdetails, tất cả các thay đổi ở Order sẽ ảnh hưởng đến OrderDetail
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();


    public Bill() {
    }
    public Bill(int billID,Customer customer,String paymentMethod, StatusPayment statusPayment, 
            StatusOrder statusOrder, Double VAT, LocalDate orderDate,LocalDate deliveryDate, 
            String shippingAddress, Double shippingFee, String notes) {
        this.billID = billID;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.statusPayment = statusPayment;
        this.statusOrder = statusOrder;
        this.VAT = VAT;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.shippingAddress = shippingAddress;
        this.shippingFee = shippingFee;
        this.notes = notes;
        orderDetails = new HashSet<>();
    }

    public Bill(String paymentMethod, StatusPayment statusPayment, StatusOrder statusOrder, Double VAT,
            LocalDate orderDate, LocalDate deliveryDate, Customer customer, String shippingAddress,
            Double shippingFee, String notes) {
        this.paymentMethod = paymentMethod;
        this.statusPayment = statusPayment;
        this.statusOrder = statusOrder;
        this.VAT = VAT;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.shippingFee = shippingFee;
        this.notes = notes;
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

    public StatusPayment getStatusPayment() {
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

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
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

    public void setStatusPayment(StatusPayment statusPayment) {
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

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
