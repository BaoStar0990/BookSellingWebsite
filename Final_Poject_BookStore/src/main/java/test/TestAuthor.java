package test;

import dbmodel.AuthorDB;
import model.Author;

import java.util.List;
import java.util.Set;
import model.Book;

public class TestAuthor {
    public static void main(String[] args) {
         List<Author> author =  AuthorDB.getInstance().selectAll();
         for (Author author1 : author) {
            System.out.println(author1.getName());
            Set<Book> books = author1.getBooks();
            for(Book b : books){
                System.out.println(b.getTitle());
            }
             System.out.println("---------------------------");
         }
//            Author a = AuthorDB.getInstance().selectByID(20);
//            AuthorDB.getInstance().delete(a.getId(), Author.class);
    }
}
