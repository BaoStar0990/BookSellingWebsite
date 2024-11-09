package test;

import dbmodel.CategoryDB;
import model.Book;
import model.Category;

import java.util.List;

public class TestCategory {
    public static void main(String[] args) {
        // lấy danh sách các cuốn sách của 1 thể loại
        System.out.println("Danh sách thể loại");
        List<Category> c = CategoryDB.getInstance().selectAll();
        for(Category cat : c){
            System.out.println(cat.getName());
        }
    }
}
