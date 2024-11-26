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
        Book b = BookDB.getInstance().selectByID(1);
        if(b.getAuthors().size() > 0){
            BookDB.getInstance().updateBookAuthorsCategories(b,null,null);
        }else{
            System.out.println("Book doesn't have author");
        }
    }
}
