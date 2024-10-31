package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import model.Book;

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
    public Book selectByID(int id) {
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

//    @Override
//    public boolean insert(Book t){
//        EntityManager em = null;
//        EntityTransaction tr = null;
//        try{
//            em = DBUtil.getEmFactory().createEntityManager();
//            tr = em.getTransaction();
//            tr.begin();
//            // find Category
//            Category c = em.find(Category.class, t.getCategory().getId());
//            // find Publisher
//            Publisher p = em.find(Publisher.class, t.getPublisher().getId());
//            
//            if(c != null && p != null){
//                t.setCategory(c);
//                t.setPublisher(p);
//                em.persist(t);
//            }
//            else
//                return false;
//            tr.commit();
//            return true;
//        }
//        catch(Exception ex){
//            if(tr != null && tr.isActive())
//                tr.rollback();
//            ex.printStackTrace();
//            return false;
//        }
//        finally{
//            if(em != null)
//                em.close();
//        }
//    }
    
}
