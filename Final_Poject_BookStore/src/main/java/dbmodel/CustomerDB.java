package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Address;
import model.Bill;
import model.Customer;
import org.hibernate.TransientObjectException;

public class CustomerDB  extends ModifyDB<Customer> implements DBInterface<Customer>{
    public static CustomerDB getInstance(){
        return new CustomerDB();
    }
    @Override
    public List<Customer> selectAll() { 
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Customer> rs = em.createQuery("from Customer", Customer.class).getResultList();
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
    public Customer selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Customer c where c.id =: id ", Customer.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public List<Address> getAddressCustomer(Customer c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            return em.createQuery("from Address a where a.customer = :customer", 
                     Address.class).setParameter("customer", c).getResultList();
        }
        catch (TransientObjectException ex) {
            return null;
        } 
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        } 
    }
    
    public Set<Bill> getBillsCustomer(Customer c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Bill> listBill = em.createQuery("from Bill b where b.customer = :customer", 
                     Bill.class).setParameter("customer", c).getResultList();
            // mọi phần tử trùng lặp sẽ bị bỏ
            Set<Bill> rs = new HashSet<>(listBill);
            return rs;
        }
        catch (TransientObjectException ex) {
            return null;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    public Customer checkExistEmail(String email){
        List<Customer> customers = selectAll();
        for(Customer c : customers){
            if(c.getEmail().equals(email)){
                return c;
            }
        }
        return null;
    }
    public boolean updateCustomer(Customer c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
