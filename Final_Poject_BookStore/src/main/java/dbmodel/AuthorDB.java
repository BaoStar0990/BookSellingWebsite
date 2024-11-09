package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Author;
import model.Book;
import org.hibernate.TransientObjectException;

public class AuthorDB extends ModifyDB<Author> implements DBInterface<Author> {
    public static AuthorDB getInstance(){
        return new AuthorDB();
    }
    @Override
    public List<Author> selectAll() {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Author> rs = em.createQuery("from Author", Author.class).getResultList();
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
    public Author selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Author a where a.id =: id ", Author.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    public boolean addBook(Author a, Book b){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // find Author
            Author authorFind = em.find(Author.class, a.getId());
            // find Book
            Book bookFind = em.find(Book.class, b.getId());
            if(authorFind != null && bookFind != null){
                // set Book
                authorFind.addBook(bookFind);
                // save Author
                em.merge(authorFind);
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
    
    public Set<Book> getBooks(Author a){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            // find Author
            Author authorFind = em.find(Author.class, a.getId());
            // find Books
            List<Book> books = em.createQuery("SELECT b FROM Author c JOIN c.books b "
                    + "WHERE c.id = :authorId", Book.class)
                     .setParameter("authorId", authorFind.getId())
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
}
