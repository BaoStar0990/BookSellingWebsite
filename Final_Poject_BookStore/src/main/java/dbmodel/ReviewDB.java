package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
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

    // Get all reviews of a book by bookID
    public List<Review> selectReviewsByBookID(int bookID) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Review r where r.book.id =: id ", Review.class)
                    .setParameter("id", bookID).getResultList();
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    // Get all reviews of a book by bookID and userID
    public List<Review> selectReviewByBookIDOfACustomer(int bookID, int userID) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Review r where r.book.id =: bookID and r.customer.id =: userID", Review.class)
                    .setParameter("bookID", bookID)
                    .setParameter("userID", userID)
                    .getResultList();
        }
        catch(NoResultException ex){
            return Collections.emptyList();
        }
        catch(Exception ex){
            return Collections.emptyList();
        }
    }

    @Override
    public boolean insert(Review review) {
        // Check if the review of the book by the customer already exists
        if (!selectReviewByBookIDOfACustomer(review.getBook().getId(), review.getCustomer().getId()).isEmpty()) {
            return false;
        }

        return super.insert(review);
    }

    public boolean deleteReview(int reviewID) {
        EntityManager em = null;
        EntityTransaction tr = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            Review review = em.find(Review.class, reviewID);
            if (review == null) {
                tr.rollback();
                return false;
            }
            em.remove(review);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr != null && tr.isActive())
                tr.rollback();
            return false;
        } finally {
            if (em != null)
                em.close();
        }
    }

    public double getAverageRate(int bookID) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("select avg(r.rate) from Review r where r.book.id =: id", Double.class)
                    .setParameter("id", bookID).getSingleResult();
        }
        catch(NoResultException ex){
            return 0;
        }
        catch(Exception ex){
            return 0;
        }
    }

    public Map<Integer, Long> getRatingStatistics(int bookID) {
        EntityManager em = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            List<Object[]> results = em.createQuery(
                "SELECT r.rate, COUNT(r) FROM Review r WHERE r.book.id = :bookID GROUP BY r.rate ORDER BY r.rate DESC", Object[].class)
                .setParameter("bookID", bookID)
                .getResultList();

            Map<Integer, Long> ratingStatistics = new HashMap<>();
            
            for (int i = 1; i <= 5; i++) {
                ratingStatistics.put(i, 0L);
            }

            for (Object[] result : results) {
                ratingStatistics.put((Integer) result[0], (Long) result[1]);
            }

            // Sort the map in descending order by key
            Map<Integer, Long> sortedRatingStatistics = new TreeMap<>(Collections.reverseOrder());
            sortedRatingStatistics.putAll(ratingStatistics);

            return sortedRatingStatistics;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyMap();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
