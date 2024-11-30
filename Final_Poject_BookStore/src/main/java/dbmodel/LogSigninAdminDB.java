package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import model.Author;
import model.Category;
import model.LogSinginAdmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Review;


public class LogSigninAdminDB extends ModifyDB<LogSinginAdmin> implements DBInterface<LogSinginAdmin> {
    public static LogSigninAdminDB getInstance(){
        return new LogSigninAdminDB();
    }
    
    @Override
    public boolean insert(LogSinginAdmin t) {
        EntityManager em = null;
        EntityTransaction tr = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();

            em.persist(t);
            tr.commit();
            System.out.println("Inserted successfully logging signin admin");
            return true;
        } catch (Exception ex) {
            if (tr != null && tr.isActive())
                tr.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            if (em != null)
                em.close();
        }
    }

    @Override
    public List<LogSinginAdmin> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LogSinginAdmin selectByID(Object id) {
       try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from LogSigninAdmin r where r.logID =: id ",LogSinginAdmin.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
}
