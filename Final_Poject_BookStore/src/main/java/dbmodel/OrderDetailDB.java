package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.OrderDetail;

public class OrderDetailDB extends ModifyDB<OrderDetail> implements DBInterface<OrderDetail> {

    public static OrderDetailDB getInstance() {
        return new OrderDetailDB();
    }

    @Override
    public List<OrderDetail> selectAll() {
        try (EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            List<OrderDetail> rs = em.createQuery("from OrderDetail", OrderDetail.class).getResultList();
            return rs;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public OrderDetail selectByID(Object id) {
        try (EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from OrderDetail o where o.id =: id ", OrderDetail.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean delete(Object id, Class<OrderDetail> entityClass) 
    {
        EntityManager em = null;
        EntityTransaction tr = null;

        try {
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            OrderDetail entity = em.find(OrderDetail.class, id);
            // Xóa trực tiếp bằng câu truy vấn JPQL
            em.createQuery("DELETE FROM OrderDetail o WHERE o.id = :id")
                    .setParameter("id", entity.getId())
                    .executeUpdate();
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
