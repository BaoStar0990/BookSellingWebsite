package dbmodel;

import database.DBUtil;
import dbmodel.DBInterface;
import dbmodel.ModifyDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.Category;

public class CategoryDB extends ModifyDB<Category> implements DBInterface<Category> {
    public static CategoryDB getInstance(){
        return new CategoryDB();
    }
    @Override
    public List<Category> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Category> rs = em.createQuery("from Category", Category.class).getResultList();
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
    public Category selectByID(int id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Category c where c.id =: id ", Category.class)
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
