package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import model.LogSinginAdmin;
import model.LogSinginStaff;

import java.util.List;


public class LogSigninStaffDB extends ModifyDB<LogSinginStaff> implements DBInterface<LogSinginStaff> {
    public static LogSigninStaffDB getInstance(){
        return new LogSigninStaffDB();
    }
    
    @Override
    public boolean insert(LogSinginStaff t) {
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
    public List<LogSinginStaff> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LogSinginStaff selectByID(Object id) {
       try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from LogSinginStaff r where r.logID =: id ",LogSinginStaff.class)
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
