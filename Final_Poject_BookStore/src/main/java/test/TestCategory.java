package test;

import dbmodel.CategoryDB;
import model.Book;
import model.Category;

public class TestCategory {
    public static void main(String[] args) {
        // lấy danh sách các cuốn sách của 1 thể loại
        Category c = CategoryDB.getInstance().selectByID(1);
        for(Book b : c.getBooks()){
            System.out.println(b.getTitle());
        }
    }
}
