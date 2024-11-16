package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.OrderDetail;


public class OrderDetailDB extends ModifyDB<OrderDetail> implements DBInterface<OrderDetail> {
    public static OrderDetailDB getInstance(){
        return new OrderDetailDB();
    }
    @Override
    public List<OrderDetail> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<OrderDetail> rs = em.createQuery("from OrderDetail", OrderDetail.class).getResultList();
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
    public OrderDetail selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from OrderDetail o where o.id =: id ", OrderDetail.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public boolean insert(OrderDetail orderDetail) {
        return super.insert(orderDetail);
    }

    public boolean update(OrderDetail orderDetail) {
        return super.update(orderDetail);
    }

    public boolean delete(int id) {
        try (EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            EntityTransaction tr = em.getTransaction();
            try {
                tr.begin();
                OrderDetail orderDetail = em.find(OrderDetail.class, id);
                if (orderDetail != null) {
                    em.remove(orderDetail);
                    tr.commit();
                    return true;
                }
                return false;
            } catch (Exception ex) {
                if (tr.isActive()) {
                    tr.rollback();
                }
                ex.printStackTrace();
                return false;
            }
        }
    }
}
