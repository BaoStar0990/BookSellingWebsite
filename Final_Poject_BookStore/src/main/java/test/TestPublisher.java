package test;

import dbmodel.PublisherDB;
import model.Book;
import model.Publisher;

public class TestPublisher {
    public static void main(String[] args) {
        // lấy danh sách các cuốn sách của 1 nhà xuất bản
        Publisher p = PublisherDB.getInstance().selectByID(1);
        for(Book b : p.getBooks()){
            System.out.println(b.getTitle());
        }
    }
}
