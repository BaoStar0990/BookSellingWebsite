package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import model.Admin;
import model.Author;

import java.util.List;

public class AdminDB
{
    public static Admin selectAdmin() {
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
}
