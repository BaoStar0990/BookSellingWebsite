package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import model.Admin;
import model.Author;

import java.util.List;

public class AdminDB extends ModifyDB<Admin> implements DBInterface<Admin>
{
    public static AdminDB getInstance() {return new AdminDB();}
    public  Admin selectAdmin() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            Admin admin = em.createQuery("from Admin", Admin.class).getResultList().get(0);
            return admin;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    public  Admin checkLogin(String username, String password) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            Admin admin = selectAdmin();
            if(admin.getUsername().equals(username) && admin.getPassword().equals(password)){
                return admin;
            }
            return null;
        }
    }

    @Override
    public List<Admin> selectAll() {
        return List.of();
    }

    @Override
    public Admin selectByID(Object id) {
        return null;
    }
}
