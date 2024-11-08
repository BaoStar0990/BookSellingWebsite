package test;

import dbmodel.AuthorDB;
import model.Author;

import java.util.List;

public class TestAuthor {
    public static void main(String[] args) {
         List<Author> author =  AuthorDB.getInstance().selectAll();
         for (Author author1 : author) {
             System.out.println(author1.getName());
         }
    }
}
