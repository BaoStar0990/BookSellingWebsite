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
    
    public boolean addBook(Category c, Book b){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // find Category
            Category categoryFind = em.find(Category.class, c.getId());
            // find Book
            Book bookFind = em.find(Book.class, b.getId());
            if(categoryFind != null && bookFind != null){
                // set Book
                categoryFind.addBook(bookFind);
                // save Author
                em.merge(categoryFind);
            }
            else
                return false;
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
    
    
    public Set<Book> getBooks(Category c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            // find Author
            Category categoryFind = em.find(Category.class, c.getId());
            // find Books
            List<Book> books = em.createQuery("SELECT b FROM Category c JOIN c.books b "
                    + "WHERE c.id = :categoryId", Book.class)
                     .setParameter("categoryId", categoryFind.getId())
                     .getResultList();
            if(books == null)
                return null;
            else
                return new HashSet<>(books);
        }
        catch (NullPointerException e) {
            return null;
        }
        // lỗi truy vấn đối tượng transient
        catch (TransientObjectException | NoResultException ex) {
            return null;
        }
        catch(Exception ex){
            return null;
        } 
    }
    

    @Override
    public boolean delete(Object id, Class<Category> entityClass){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // chuyển thực thể sang trạng thái persistent
            Category entity = em.find(entityClass, id);
            // xóa author khỏi book và book khỏi author
            for(Book b : entity.getBooks()){
                for(Category c : b.getCategories())
                    b.getCategories().remove(c);
                
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
            return false;
        }
        finally{
            if(em != null)
                em.close();
        }
    }
     
}
