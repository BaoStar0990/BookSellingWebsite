
package test;

import dbmodel.BillDB;
import dbmodel.CustomerDB;
import model.Bill;
import model.Customer;
import model.OrderDetail;
import model.StatusOrder;

import java.time.LocalDate;

public class TestBill {
    public static void main(String[] args) {
        Bill bill = BillDB.getInstance().selectByID(10);
//        BillDB.getInstance().delete(bill.getId(), Bill.class);
         // tạo cart mới cho khách hàng  
//         Customer customer = CustomerDB.getInstance().selectByID(2);
//            Bill newCart = new Bill();
//            newCart.setCustomer(customer);
//            newCart.setStatusOrder(StatusOrder.Storing);
//            BillDB.getInstance().insert(newCart);
        for(OrderDetail o : bill.getOrderDetails()){
            System.out.println(o.getBook().getTitle());
        }
        LocalDate date = LocalDate.now();
        System.out.println(date);
    }
    
}
