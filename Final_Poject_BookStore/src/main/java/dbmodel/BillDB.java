package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.Bill;

public class BillDB extends ModifyDB<Bill> implements DBInterface<Bill> {
    public static BillDB getInstance(){
        return new BillDB();
    }
    @Override
    public List<Bill> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Bill> rs = em.createQuery("from Bill", Bill.class).getResultList();
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
    public Bill selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Bill b where b.id =: id ", Bill.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public boolean update(Bill bill) {
        return super.update(bill);
    }
    
}
