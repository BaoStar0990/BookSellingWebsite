package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Book;
import model.Category;
import org.hibernate.TransientObjectException;

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
    public Category selectByID(Object id) {
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
    
    public Set<Book> getBooks(Category c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
               List<Book> listBooks = em.createQuery("from Book b where b.category = :category", 
                        Book.class).setParameter("category", c).getResultList();
               Set<Book> rs = new HashSet<>(listBooks);
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
}
