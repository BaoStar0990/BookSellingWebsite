package controller;

import dbmodel.AuthorDB;
import dbmodel.BookDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Author;
import model.Book;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@WebServlet(name = "AuthorDetailController", urlPatterns = {"/authordetail/*"})
public class AuthorDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //set UTF8 - Tiếng việt
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //Category
        List<Author> authors = null;
        Author author = null;
        HttpSession session =  req.getSession();
        String pathInfo = req.getPathInfo();
        int id = -1;
        try {
            id = Integer.parseInt(pathInfo.substring(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Book
        if (id > -1) {
            if (session.getAttribute("authors") == null) {
                author = AuthorDB.getInstance().selectByID(id);
            } else { // lan truy cap sau,  lay book trong session
                authors = (List<Author>) session.getAttribute("authors");
                try {
                    int id_for_search = id; // nếu lấy id thì sẽ không được
                    author = authors.stream().filter(a -> {
                        return a.getId() == id_for_search;
                    }).findFirst().get();
                } catch (NumberFormatException ex) {
                    System.out.println("Vui lòng nhập đúng dữ liệu");
                } catch (NoSuchElementException ex) {
                    System.out.println("Không tìm thấy tác giả");
                }
            }
        }

        // Lấy cuốn sách
        if (author != null) {
            Set<Book> books = author.getBooks();
            req.setAttribute("author", author);
            if(books != null) {
                req.setAttribute("books", books);
            }
            String url = "/authordetail.jsp";
            System.out.println("Called Bookdetail");
            req.getRequestDispatcher(url).forward(req, resp);
        } else {
            resp.sendRedirect("/404error.jsp");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
