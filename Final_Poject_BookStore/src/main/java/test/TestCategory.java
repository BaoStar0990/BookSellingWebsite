package test;

import dbmodel.BookDB;
import dbmodel.CategoryDB;
import model.Book;
import model.Category;

import java.util.List;

public class TestCategory {
    public static void main(String[] args) {
        
        // chèn thể 1 cuốn sách vào thể lọai
//        Book b = BookDB.getInstance().selectByID(1);
//        System.out.println("Danh sách thể loại");
        Category c = CategoryDB.getInstance().selectByID(3);
//        System.out.println(c.getName());
        // thêm sách vào thể loại
//        CategoryDB.getInstance().addBook(c, b);
        // lấy danh sách các cuốn sách của 1 thể loại
        for(Book b1 : c.getBooks()){
            System.out.println(b1.getTitle());
        }
//Category a = CategoryDB.getInstance().selectByID(30);
//            CategoryDB.getInstance().delete(a.getId(), Category.class);

    }
}
