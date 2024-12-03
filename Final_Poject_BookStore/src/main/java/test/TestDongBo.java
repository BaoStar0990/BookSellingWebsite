/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dbmodel.BookDB;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Book;
import model.OrderDetail;

public class TestDongBo {
    public static void main(String[] args) {
        System.out.println("------Lấy các sản phẩm-------");
        List<Book> listBooks = new ArrayList<>();
        for(int i = 0; i <= 4; i++){
            Book book = BookDB.getInstance().selectByID(i);
            listBooks.add(book);
        }
        
        List<Book> listBooks1 = new ArrayList<>();
        listBooks1.add(listBooks.get(1));
        listBooks1.add(listBooks.get(2));
        listBooks1.add(listBooks.get(4));
        
        List<Book> listBooks2 = new ArrayList<>();
        listBooks2.add(listBooks.get(2));
        listBooks2.add(listBooks.get(3));
        listBooks2.add(listBooks.get(4));
        
        Thread t1 = new Thread(() -> {
            for(Book sp : listBooks1){
                sp.increaseStocks(1);
            }
        }, "Luồng 1");
        
        Thread t2 = new Thread(() -> {
            for(Book sp : listBooks2){
                sp.increaseStocks(2);
            }
        }, "Luồng 2");
        
        System.out.println("------Bắt đầu tiến trình-------");
        t1.start();
        t2.start();
    }
}
