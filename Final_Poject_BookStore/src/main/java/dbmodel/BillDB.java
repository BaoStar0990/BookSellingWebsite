package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.*;
import java.util.stream.Collectors;

import model.Bill;
import model.Book;
import model.OrderDetail;
import model.StatusPayment;
import org.hibernate.TransientObjectException;
import org.hibernate.query.Order;

public class BillDB extends ModifyDB<Bill> implements DBInterface<Bill> {
    public static BillDB getInstance(){
        return new BillDB();
    }
    @Override
    public List<Bill> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Bill> rs = em.createQuery("from Bill", Bill.class).getResultList();
            return rs;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        } 
    }

    @Override
    public Bill selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Bill b where b.id =: id ", Bill.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public boolean addBookBill(Book book, int quantity , Bill bill){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // find Bill 
            Bill cart = em.find(Bill.class, bill.getId());
            // tạo Orderdetail
            OrderDetail order = new OrderDetail(quantity, book);
//            em.merge(order); // lưu nếu chưa có và cập nhật nếu co
            // Thêm orderdetail vào bill
            cart.addOrderDetail(order);
            // lưu cập nhật
            em.merge(cart);
            tr.commit();
            return true;
        }
        catch(Exception ex){
            if(tr != null && tr.isActive())
                tr.rollback();
            ex.printStackTrace();
            return false;
        }
        finally{
            if(em != null)
                em.close();
        }
    }
    public OrderDetail FindOrderDetailFromBill(Book book, Bill cart){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
               OrderDetail orderDetail = em.createQuery("from OrderDetail o where o.bill =: bill "
                       + "AND o.book = :book", OrderDetail.class)
                       .setParameter("bill", cart).setParameter("book", book).getSingleResult();
               return orderDetail;
        }
        catch (TransientObjectException ex) {
            return null;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        } 
    }
    public Set<OrderDetail> getOrderDetails(Bill b){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
               List<OrderDetail> listOrderDetails = em.createQuery("from OrderDetail o where o.bill = :bill", 
                        OrderDetail.class).setParameter("bill", b).getResultList();
               Set<OrderDetail> rs = new HashSet<>(listOrderDetails);
               return rs;
        }
        catch (TransientObjectException ex) {
            return null;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }     
    }
}
