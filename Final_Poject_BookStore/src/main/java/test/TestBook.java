package test;

import dbmodel.BookDB;
import java.util.List;
import java.util.Set;
import model.Author;
import model.Book;
import model.Review;

public class TestBook {
    public static void main(String[] args) {
         System.out.println("--------------Book---------------");
        // test select all book (bao gồm các thông tin như nhà xuất bản, tác giả, thể loại)
        List<Book> allBooks = BookDB.getInstance().selectAll();
        for(Book b : allBooks){
            System.out.println("--------");
            System.out.println(b.getId() + " " + b.getTitle() + " " +
                    b.getLanguage() + " "+b.getDescription());
            // lấy thông tin tác giả của sách
            Set<Author> authors = b.getAuthors();        
            for(Author a : authors){
                System.out.println(a.getName());
            }
        }
//        System.out.println("--------Lấy các lượt review của 1 cuốn sách-------");
//        Book book1 = BookDB.getInstance().selectByID(1);
//        List<Review> rv = book1.getReviews();
//        for(Review r : rv){
//            System.out.println(r.getRate() + " " + r.getDescription() +
//                    " " + r.getCustomer().getFullName());
//        }
        
    }
}
