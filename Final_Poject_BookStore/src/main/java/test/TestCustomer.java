package test;

import dbmodel.CustomerDB;
import java.util.List;
import java.util.Set;
import model.Address;
import model.Bill;
import model.Customer;
import model.OrderDetail;

public class TestCustomer {
    public static void main(String[] args) {
//        System.out.println("--------------Customer---------------");
//        // test lấy thông tin khách hàng
//        List<Customer> allCustomers = CustomerDB.getInstance().selectAll();
//        for(Customer c : allCustomers){
//            // lấy thông tin địa chỉ của khách hàng
//            List<Address> adresses = c.getAddresses();
// 
        System.out.println("--------------Customer---------------");
        // test lấy thông tin khách hàng
        List<Customer> allCustomers = CustomerDB.getInstance().selectAll();
        for(Customer c : allCustomers){
            System.out.println(c.getUsername() + " " + c.getPassword());
            // lấy thông tin địa chỉ của khách hàng
//            List<Address> adresses = c.getAddresses();
//
//            System.out.println(c.getFullName());
//            for(Address a : adresses){
//                System.out.println(a.getDistrict() + " " + a.getProvince());
//            }
//        }
        }
        // Đặt đơn hàng
        
        System.out.println("--------------Bill của khách---------------");
        // lấy các đơn hàng của 1 khách hàng
        Customer c1 = CustomerDB.getInstance().selectByID(2);
        Set<Bill> bills = c1.getBills();
        if(bills != null)
            for(Bill b : bills){
                System.out.println("-----------");
                System.out.println(b.getId() + " " + b.getStatusOrder());
                // in chi tiết các đơn hàng(lấy thông tin sách được luôn)
                for(OrderDetail o : b.getOrderDetails()){
                    System.out.println(o.getQuantity() + " " + o.getBook().getTitle());
                }
            }
        else
            System.out.println("Null");
    }
}
