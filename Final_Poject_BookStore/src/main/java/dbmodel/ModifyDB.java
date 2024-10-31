package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public abstract class ModifyDB<T> {
    public boolean insert(T t){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.persist(t);
            tr.commit();
            return true;
        }
        catch(Exception ex){
            tr.rollback();
            return false;
        }
        finally{
            em.close();
        }
    }
    public boolean update(T t){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            
            em.merge(t); // Cập nhật hoặc merge thực thể detached vào persistence context
            tr.commit();
            return true;
        }
        catch(Exception ex){
            tr.rollback();
            return false;
        }
        finally{
            em.close();
        }
    }

    public boolean delete(Object id, Class<T> entityClass){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            // chuyển thực thể sang trạng thái persistent
            T entity = em.find(entityClass, id);
            em.remove(entity);
            tr.commit();
            return true;
        }
        catch(Exception ex){
            tr.rollback();
            return false;
        }
        finally{
            em.close();
        }
    }
}
