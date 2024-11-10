/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.BookDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;

import dbmodel.CategoryDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;
import model.Category;

/**
 *
 * @author PC
 */
@WebServlet(name = "BookDetailController", urlPatterns = {"/bookdetails/*"})
public class BookDetailController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //Category
       // List<Category> categories = null;
        List<Book> books = null;
        HttpSession session = request.getSession();
//        if(session.getAttribute("categories") == null) {
//            categories = CategoryDB.getInstance().selectAll();
//            session.setAttribute("categories", categories);
//        }
//        else{
//            categories = (List<Category>)session.getAttribute("categories");
//        }
        //Book
        if(session.getAttribute("books") == null) {
            books = BookDB.getInstance().selectAll();
            session.setAttribute("books", books);
        }else{ // lan truy cap sau,  lay book trong session
            books = (List<Book>)session.getAttribute("books");
        }


        // lấy id của book

        // Lấy cuốn sách
        try{
            String pathInfo = request.getPathInfo();
            int id = Integer.parseInt(pathInfo.substring(1));
            Book book = books.stream().filter(b -> {
                return b.getId() == id;
            }).findFirst().get();
            request.setAttribute("book", book);
            String url = "/bookdetails.jsp";
            System.out.println("Called Bookdetail");
            request.getRequestDispatcher(url).forward(request, response);
        }catch(NumberFormatException ex){
            System.out.println("Vui lòng nhập đúng dữ liệu");
        }catch (NoSuchElementException ex){
            System.out.println("Không tìm thấy sách");
        }
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
