package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.Book;
import model.DiscountCampaign;

public class DiscountCampaignDB extends ModifyDB<DiscountCampaign> implements DBInterface<DiscountCampaign> {
    public static DiscountCampaignDB getInstance(){
        return new DiscountCampaignDB();
    }
    @Override
    public List<DiscountCampaign> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<DiscountCampaign> rs = em.createQuery("from DiscountCampaign",
                    DiscountCampaign.class).getResultList();
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
    public DiscountCampaign selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from DiscountCampaign d where d.id =: id ", DiscountCampaign.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    @Override
    public boolean delete(Object id, Class<DiscountCampaign> entityClass){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // chuyển thực thể sang trạng thái persistent
            DiscountCampaign entity = em.find(entityClass, id);
            // set null cho publisher, campaign
            for(Book b : entity.getBooks()){
                b.setDiscountCampaign(null);
                if(!BookDB.getInstance().update(b)){
                    tr.rollback();
                    return false;
                }
            }           
            // xóa thực thể
            em.remove(entity);
            tr.commit();
            return true;
        }
         catch(Exception ex){
            if(tr != null && tr.isActive())
                tr.rollback();
            ex.printStackTrace();
            return false;
        }
        finally{
            if(em != null)
                em.close();
        }
    } 
}
