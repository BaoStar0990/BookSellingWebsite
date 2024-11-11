package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
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
    public boolean insert(DiscountCampaign o) {
        return super.insert(o);
    }

    @Override
    public boolean update(DiscountCampaign discountCampaign) {
        return super.update(discountCampaign);
    }

    @Override
    public boolean delete(Object id, Class<DiscountCampaign> entityClass) {
        return super.delete(id, entityClass);
    }
}
