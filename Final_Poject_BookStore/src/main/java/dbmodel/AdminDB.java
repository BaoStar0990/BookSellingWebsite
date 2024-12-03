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
    public  List<Admin> selectAdmin() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Admin> admin = em.createQuery("from Admin", Admin.class).getResultList();
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
            List<Admin> admin = selectAdmin();
            for(Admin a : admin){
                if(a.getUsername().equals(username) && a.getPassword().equals(password)){
                    return a;
                }
            }
            return null;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
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
