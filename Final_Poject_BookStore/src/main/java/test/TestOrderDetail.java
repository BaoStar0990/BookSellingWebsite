
package test;

import database.DBUtil;
import dbmodel.BillDB;
import dbmodel.CustomerDB;
import dbmodel.OrderDetailDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import model.Bill;
import model.Book;
import model.Customer;
import model.OrderDetail;
import model.StatusOrder;


public class TestOrderDetail {
    public static void main(String[] args) {
//        System.out.println("---------abc------------");
//        if(!OrderDetailDB.getInstance().delete(5, OrderDetail.class)){
//            System.out.println("----------------Xóa không thành công");
//        }
//        else{
//             System.out.println("----------------Xóa thành công");
//        }
//        EntityManager em = null;
//        EntityTransaction tr = null;
//        try{
//            em = DBUtil.getEmFactory().createEntityManager();
//            tr = em.getTransaction();
//            tr.begin();
//            // chuyển thực thể sang trạng thái persistent
//            OrderDetail entity = em.find(OrderDetail.class, 5);
////            OrderDetail entity = OrderDetailDB.getInstance().selectByID(6);
//           
//            if(entity == null){
//                System.out.println("----------------null ");
//            }
//            else{
//                 System.out.println(entity.getBook().getTitle());
//                // xóa thực thể
//                em.remove(entity);
//            }
//            
//            tr.commit();
//        }
//         catch(Exception ex){
//             ex.printStackTrace();
//            if(tr != null && tr.isActive())
//                tr.rollback();
//        }
//        finally{
//            if(em != null)
//                em.close();
//        }

//        EntityManager em = null;
//        EntityTransaction tr = null;
//
//        try {
//            em = DBUtil.getEmFactory().createEntityManager();
//            tr = em.getTransaction();
//            tr.begin();
//            OrderDetail entity = em.find(OrderDetail.class, 5);
//            // Xóa trực tiếp bằng câu truy vấn JPQL
//            em.createQuery("DELETE FROM OrderDetail o WHERE o.id = :id")
//              .setParameter("id", entity.getId())
//              .executeUpdate();
//
//            tr.commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            if (tr != null && tr.isActive()) {
//                tr.rollback();
//            }
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }

        Customer customer = CustomerDB.getInstance().selectByID(4);
    EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // find cart
            Bill cartFind = BillDB.getInstance().selectByID(18);
            // kiểm tra trong cart có sp nào không, hoặc có phải là cart không
 
            // kiểm tra số lượng đặt có vượt quá số lượng trong kho


            // chuyển trạng thái từ "storing" sang "Processing"
            cartFind.setStatusOrder(StatusOrder.Processing);
              
//                             Thread.sleep(5000);       
            // trừ số lượng sách trong kho khi đặt
            final EntityManager emFinal = em;           
            cartFind.getOrderDetails().forEach(o ->{
                        // lấy cuốn sách cập nhật số lượng trong kho
                        var book = emFinal.find(Book.class, o.getBook().getId());
                        book.setStocks(book.getStocks() - o.getQuantity());
                        emFinal.merge(book);
                    });
            // cập nhật cart thành đơn hàng
            em.merge(cartFind);  

            // Tạm dừng luồng hiện tại trong 5 giây (5000 ms)
            // cập nhật lại số lượng các OrderDetail trong các cart của khách hàng khác có
            // còn hợp lệ không
            // Cập nhật lại số lượng các OrderDetail trong các cart của khách hàng khác
    List<Bill> allCart = em.createQuery(
            "SELECT b FROM Bill b WHERE b.statusOrder = :status AND b.id != :cartId", Bill.class)
            .setParameter("status", StatusOrder.Storing)
            .setParameter("cartId", cartFind.getId())
            .getResultList();

    for (Bill cart : allCart) {
        Iterator<OrderDetail> iterator = cart.getOrderDetails().iterator();
        while (iterator.hasNext()) {
            OrderDetail o = iterator.next();

            // Nếu số lượng = 0, xóa OrderDetail này
            if (o.getBook().getStocks() == 0) {
                iterator.remove();
                em.remove(o); // Xóa khỏi cơ sở dữ liệu
            } 
            // Nếu số lượng > số lượng trong kho, thì set về số lượng trong kho
            else if (o.getQuantity() > o.getBook().getStocks()) {
                o.setQuantity(o.getBook().getStocks());
                em.merge(o); // Cập nhật OrderDetail
            }
        }
    }
            // tạo cart mới cho khách hàng         
            Bill newCart = new Bill();
            newCart.setCustomer(customer);
            newCart.setStatusOrder(StatusOrder.Storing);
            em.persist(newCart);  
            
            tr.commit();
        }
        catch(Exception ex){
            if(tr != null && tr.isActive())
                tr.rollback();
            ex.printStackTrace();
        }
        finally{
            if(em != null)
                em.close();
        }
}
}
