package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Author;
import model.Book;
import model.Category;
import model.Review;
import org.hibernate.TransientObjectException;
import jakarta.persistence.EntityTransaction;

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
    
    public Set<Review> getReviews(Book b){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Review> reviewList =  em.createQuery("from Review r where r.book = :book", 
                        Review.class).setParameter("book", b).getResultList();
            if(reviewList == null)
                return null;
            else
                return new HashSet<>(reviewList);
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
    public boolean insert(Book book) {
        EntityManager em = null;
        EntityTransaction tr = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            
            // Persist the book
            em.persist(book);
            
            // Merge authors
            Set<Author> managedAuthors = new HashSet<>();
            for (Author author : book.getAuthors()) {
                Author managedAuthor = em.find(Author.class, author.getId());
                if (managedAuthor != null) {
                    managedAuthor.getBooks().add(book);
                    managedAuthors.add(managedAuthor);
                } else {
                    author = em.merge(author);
                    author.getBooks().add(book);
                    managedAuthors.add(author);
                }
            }
            book.setAuthors(managedAuthors);
            
            // Merge categories
            Set<Category> managedCategories = new HashSet<>();
            for (Category category : book.getCategories()) {
                Category managedCategory = em.find(Category.class, category.getId());
                if (managedCategory != null) {
                    managedCategory.getBooks().add(book);
                    managedCategories.add(managedCategory);
                } else {
                    category = em.merge(category);
                    category.getBooks().add(book);
                    managedCategories.add(category);
                }
            }
            book.setCategories(managedCategories);
            
            tr.commit();
            return true;
        } catch (Exception ex) {
            if (tr != null && tr.isActive())
                tr.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            if (em != null)
                em.close();
        }
    }
}
