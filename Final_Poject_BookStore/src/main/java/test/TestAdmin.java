package test;

import dbmodel.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import model.Author;
import model.Book;
import model.Customer;

import java.util.*;
import java.util.stream.Collectors;

public class TestAdmin {
    public static void main(String[] args) {
        List<Book> allBook = BookDB.getInstance().selectAll();
        List<Book> bookIsBeingDiscounted = allBook.stream().filter(b -> DiscountCampaignDB.getInstance().isNotExpired(b.getDiscountCampaign())).collect(Collectors.toList());
        bookIsBeingDiscounted.forEach(b -> System.out.println(b.getTitle()));
    }
}
