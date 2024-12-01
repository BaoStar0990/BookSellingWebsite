package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

import model.Admin;
import model.Staff;

public class StaffDB extends ModifyDB<Staff> implements DBInterface<Staff> {
    public static StaffDB getInstance(){
        return new StaffDB();
    }
    @Override
    public List<Staff> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Staff> rs = em.createQuery("from Staff", Staff.class).getResultList();
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
    public Staff selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Staff s where s.id =: id ", Staff.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    public Staff checkLogin(String username, String password) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            Staff staff = selectAll().get(0);
            if(staff.getUsername().equals(username) && staff.getPassword().equals(password)){
                return staff;
            }
            return null;
        }
    }
    
}
