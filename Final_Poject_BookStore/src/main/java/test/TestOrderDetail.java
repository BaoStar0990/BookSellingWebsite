
package test;

import database.DBUtil;
import dbmodel.OrderDetailDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.OrderDetail;


public class TestOrderDetail {
    public static void main(String[] args) {
        System.out.println("---------abc------------");
        if(!OrderDetailDB.getInstance().delete(5, OrderDetail.class)){
            System.out.println("----------------Xóa không thành công");
        }
        else{
             System.out.println("----------------Xóa thành công");
        }
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

    }
}
