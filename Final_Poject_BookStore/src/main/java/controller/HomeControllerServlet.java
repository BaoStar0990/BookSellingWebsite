/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import database.DBUtil;
import dbmodel.AuthorDB;
import dbmodel.BookDB;
import dbmodel.CategoryDB;
import dbmodel.CustomerDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.transaction.TransactionManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Author;
import model.Category;
import model.Customer;
import org.hibernate.Session;
import model.Book;

/**
 *
 * @author hadan
 */
@WebServlet("/home")
public class HomeControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param req servlet request
     * @param req servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //set UTF8 - Tiếng việt
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        //Lay session
        HttpSession session = req.getSession();
        //Ba tham so truyen ve cho UI
        List<Category> categories = null;
        List<Book> books = null;
        List<Author> authors = null;



        //lan truy cap dau tien, book chua ton tai trong session
            //Book
        if(session.getAttribute("books") == null) {
            books = BookDB.getInstance().selectAll();
            session.setAttribute("books", books);
        }else{ // lan truy cap sau,  lay book trong session
            books = (List<Book>)session.getAttribute("books");
        }
        if(books != null) {
            if(books.size() > 6)
            {
                books = books.stream().limit(6).collect(Collectors.toList());
            }
        }
            //Category
        if(session.getAttribute("categories") == null) {
            categories = CategoryDB.getInstance().selectAll();
            session.setAttribute("categories", categories);
        }
        else{
            categories = (List<Category>)session.getAttribute("categories");
        }
            //Author
        if(session.getAttribute("authors") == null) {
            authors = AuthorDB.getInstance().selectAll();
            session.setAttribute("authors", authors);
        }else{
            authors = (List<Author>)session.getAttribute("authors");
        }
        if(authors != null)
        {
            if(authors.size() > 6)
            {
                authors = authors.stream().limit(6).collect(Collectors.toList());
            }
        }


        //Kiem tra trong cookie da co tai khoang nguoi dung chua
        String email = null;
        String username = null;
        String password = null;
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("email")) {
                    email = cookie.getValue();
                }
                if(cookie.getName().equals("username")) {
                    username = cookie.getValue();
                }
                if(cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        }
        if (email != null && username != null && password != null) {
            Customer customer = CustomerDB.getInstance().selectCustomerByEmailPassWord(email,password);
            if(customer != null){
                session.setAttribute("user",customer);
            }
        }

        req.setAttribute("categories", categories);
        req.setAttribute("bestsellerBooks", books);
        req.setAttribute("authors", authors);

        String url = "/home.jsp";
        req.getServletContext().getRequestDispatcher(url).forward(req, res);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
