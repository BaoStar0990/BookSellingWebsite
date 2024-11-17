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
import java.util.Iterator;

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

            // Manage authors
            Set<Author> managedAuthors = new HashSet<>();
            for (Author author : book.getAuthors()) {
                Author managedAuthor = em.find(Author.class, author.getId());
                if (managedAuthor != null) {
                    managedAuthor.getBooks().add(book);
                    managedAuthors.add(managedAuthor);
                } else {
                    managedAuthor = em.merge(author);
                    managedAuthors.add(managedAuthor);
                }
            }
            book.setAuthors(managedAuthors);

            // Manage categories
            Set<Category> managedCategories = new HashSet<>();
            for (Category category : book.getCategories()) {
                Category managedCategory = em.find(Category.class, category.getId());
                if (managedCategory != null) {
                    managedCategory.getBooks().add(book);
                    managedCategories.add(managedCategory);
                } else {
                    managedCategory = em.merge(category);
                    managedCategories.add(managedCategory);
                }
            }
            book.setCategories(managedCategories);

            // Persist the book itself
            em.persist(book);

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

    public boolean update(Book book) {
        EntityManager em = null;
        EntityTransaction tr = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();

            // Fetch the existing managed Book entity if it exists in the session
            Book existingBook = em.find(Book.class, book.getId());
            if (existingBook != null) {
                // Update properties of the managed entity
                existingBook.setTitle(book.getTitle());
                existingBook.setIsbn(book.getIsbn());
                existingBook.setPublishDate(book.getPublishYear());
                existingBook.setCostPrice(book.getCostPrice());
                existingBook.setSellingPrice(book.getSellingPrice());
                existingBook.setStocks(book.getStocks());
                if (book.getUrlImage() != null) {
                    existingBook.setUrlImage(book.getUrlImage());
                }
                existingBook.setDescription(book.getDescription());
                existingBook.setLanguage(book.getLanguage());
                existingBook.setPublisher(book.getPublisher());
                existingBook.setDiscountCampaign(book.getDiscountCampaign());

                // Manually update collections if needed, and make sure cascading is handled correctly
                Set<Author> managedAuthors = new HashSet<>();
                for (Author author : book.getAuthors()) {
                    Author managedAuthor = em.find(Author.class, author.getId());
                    if (managedAuthor != null) {
                        managedAuthors.add(managedAuthor);
                    } else {
                        em.persist(author);
                        managedAuthors.add(author);
                    }
                }
                existingBook.setAuthors(managedAuthors);

                Set<Category> managedCategories = new HashSet<>();
                for (Category category : book.getCategories()) {
                    Category managedCategory = em.find(Category.class, category.getId());
                    if (managedCategory != null) {
                        managedCategories.add(managedCategory);
                    } else {
                        em.persist(category);
                        managedCategories.add(category);
                    }
                }
                existingBook.setCategories(managedCategories);

            } else {
                // If no managed entity exists, use merge to persist a new entity
                em.merge(book);
            }

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

    @Override
    public boolean delete(Object id, Class<Book> entityClass){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // chuyển thực thể sang trạng thái persistent
            Book entity = em.find(entityClass, id);
            if(entity == null){
                tr.rollback();
                return false;
            }
            // Xóa liên kết với Author
            Iterator<Author> authorIterator = entity.getAuthors().iterator();
            while (authorIterator.hasNext()) {
                Author author = authorIterator.next();
                author.getBooks().remove(entity); // Xóa book khỏi author
                authorIterator.remove();         // Xóa author khỏi book
            }

            // Xóa liên kết với Category
            Iterator<Category> categoryIterator = entity.getCategories().iterator();
            while (categoryIterator.hasNext()) {
                Category category = categoryIterator.next();
                category.getBooks().remove(entity); // Xóa book khỏi category
                categoryIterator.remove();          // Xóa category khỏi book
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
