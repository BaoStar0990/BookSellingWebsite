package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.Review;

public class ReviewDB extends ModifyDB<Review> implements DBInterface<Review> {
    public static ReviewDB getInstance(){
        return new ReviewDB();
    }
    @Override
    public List<Review> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Review> rs = em.createQuery("from Review", Review.class).getResultList();
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
    public Review selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Review r where r.id =: id ", Review.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }

//    public Review getPercentR(Book b) {
//        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
//            return em.createQuery("from Review r where r.id =: id ", Review.class)
//                    .setParameter("id", id).getSingleResult();
//        }
//        catch(NoResultException ex){
//            return null;
//        }
//        catch(Exception ex){
//            return null;
//        }
//    }

    
}
