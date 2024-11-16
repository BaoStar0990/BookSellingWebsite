package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Book;
import model.Category;
import model.Publisher;
import org.hibernate.TransientObjectException;

public class PublisherDB extends ModifyDB<Publisher> implements DBInterface<Publisher> {
    public static PublisherDB getInstance(){
        return new PublisherDB();
    }
    @Override
    public List<Publisher> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Publisher> rs = em.createQuery("from Publisher", Publisher.class).getResultList();
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
    public Publisher selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Publisher p where p.id =: id ", Publisher.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    public Set<Book> getBooks(Publisher p){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
               List<Book> listBooks = em.createQuery("from Book b where b.publisher = :publisher", 
                        Book.class).setParameter("publisher", p).getResultList();
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
    @Override
    public boolean delete(Object id, Class<Publisher> entityClass){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // chuyển thực thể sang trạng thái persistent
            Publisher entity = em.find(entityClass, id);
            // set null cho publisher, campaign
            for(Book b : entity.getBooks()){
                b.setPublisher(null);
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
