package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.Book;
import model.Category;
import model.Review;
import org.hibernate.TransientObjectException;

public class BookDB extends ModifyDB<Book> implements DBInterface<Book> {
    public static BookDB getInstance(){
        return new BookDB();
    }
    @Override
    public List<Book> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Book> rs = em.createQuery("from Book", Book.class).getResultList();
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
    public Book selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Book b where b.id =: id ", Book.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public List<Review> getReviews(Book b){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            return em.createQuery("from Review r where r.book = :book", 
                        Review.class).setParameter("book", b).getResultList();
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
}
