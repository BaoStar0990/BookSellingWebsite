package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.Address;


public class AddressDB extends ModifyDB<Address> implements DBInterface<Address> {
    public static AddressDB getInstance(){
        return new AddressDB();
    }
    @Override
    public List<Address> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Address> rs = em.createQuery("from Address", Address.class).getResultList();
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
    public Address selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Address a where a.id =: id ", Address.class)
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
