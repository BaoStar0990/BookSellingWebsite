package test;

import dbmodel.AuthorDB;
import dbmodel.BookDB;
import dbmodel.CategoryDB;
import dbmodel.PublisherDB;
import java.util.List;
import java.util.Set;
import model.Author;
import model.Book;
import model.Category;
import model.Publisher;
import model.Review;

public class TestBook {
    public static void main(String[] args) {
//         System.out.println("--------------Book---------------");
//        // test select all book (bao gồm các thông tin như nhà xuất bản, tác giả, thể loại)
//        List<Book> allBooks = BookDB.getInstance().selectAll();
//        for(Book b : allBooks){
//            System.out.println("--------");
//            System.out.println(b.getId() + " " + b.getTitle() + " " +
//                    b.getLanguage() + " "+b.getDescription());
//            // lấy thông tin tác giả của sách
//            Set<Author> authors = b.getAuthors();        
//            for(Author a : authors){
//                System.out.println(a.getName());
//            }
//        }
//        System.out.println("--------Lấy các lượt review của 1 cuốn sách-------");
//        Book book1 = BookDB.getInstance().selectByID(1);
//        List<Review> rv = book1.getReviews();
//        for(Review r : rv){
//            System.out.println(r.getRate() + " " + r.getDescription() +
//                    " " + r.getCustomer().getFullName());
//        }
//        Publisher P2 = PublisherDB.getInstance().selectByID(2);
//        Author a = AuthorDB.getInstance().selectByID(3);
//        Category c = CategoryDB.getInstance().selectByID(2);
//        Book newBook = new Book("acbs", "abs", "01567", 4.5, 3.5, 12, "adv", 4, "TV", P2);
//        if(BookDB.getInstance().insert(newBook)){
//            if(AuthorDB.getInstance().addBook(a, newBook)){
//                System.out.println("Thêm tác giả thành công");
//            }
//            if(CategoryDB.getInstance().addBook(c, newBook)){
//                System.out.println("Thêm thể loại thành công");
//            }
//        }
//        else{
//            System.out.println("Lỗi chèn sách");
//        }
        
        Book b = BookDB.getInstance().selectByID(67);
        BookDB.getInstance().delete(b.getId(), Book.class);
    }
}
