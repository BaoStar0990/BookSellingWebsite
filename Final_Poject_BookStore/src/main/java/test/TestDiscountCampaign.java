package test;

import dbmodel.BookDB;
import dbmodel.DiscountCampaignDB;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import model.Book;
import model.DiscountCampaign;

public class TestDiscountCampaign {
    public static void main(String[] args) {
        // chèn chiến dịch
//        HashSet<Book> findBooks = new HashSet();
//        Book b1 = BookDB.getInstance().selectByID(1);
//        findBooks.add(b1);
//        Book b2 = BookDB.getInstance().selectByID(2);
//        findBooks.add(b2);
//
//        // tạo transaction
//        DiscountCampaign dc = new DiscountCampaign("chiến dịch tháng 11", LocalDate.of(2024, 10, 27),
//                LocalDate.of(2024, 11, 12), 12.6);
//        // dc.setBooks(findBooks);
//        // lưu chiến dịch
//        DiscountCampaignDB.getInstance().insert(dc);
//        // cập nhật khóa ngoại campaign cho sách
//        for(Book b : findBooks){
//            b.setDiscountCampaign(dc);
//            BookDB.getInstance().update(b);
//        }
        // lấy các sách của chiến dịch
        List<DiscountCampaign> dc = DiscountCampaignDB.getInstance().selectAll();
        for(DiscountCampaign d : dc){
            System.out.println(d.getCampaignName());
            for(Book b : d.getBooks()){
                System.out.println(b.getTitle());
            }
        }
//        System.out.println("------------");
//        // lấy 1 chiến dịch
//        DiscountCampaign d1 = DiscountCampaignDB.getInstance().selectByID(4);
//        System.out.println(d1.getCampaignName());
//        for(Book b : d1.getBooks()){
//            System.out.println(b.getTitle());
//        }
        
    }
}
