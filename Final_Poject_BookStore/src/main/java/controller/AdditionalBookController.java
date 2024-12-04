/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.AuthorDB;
import dbmodel.BookDB;
import dbmodel.DiscountCampaignDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Author;
import model.Book;

/**
 *
 * @author hadan
 */
@WebServlet(name = "AdditionalBookController", urlPatterns = {"/additionalbook/*"})
public class AdditionalBookController extends HttpServlet {

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
        // Set UTF-8 encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // Lấy thông tin từ URL
        String pathInfo = request.getPathInfo(); // Lấy phần sau /additionalbook/
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String[] parts = pathInfo.substring(1).split("/"); // Bỏ dấu "/"
            if (parts.length == 2 && "author".equalsIgnoreCase(parts[0])) {
                try {
                    int authorId = Integer.parseInt(parts[1]); // Chuyển đổi phần id
                    System.out.println("Author ID: " + authorId);
                    Author author = AuthorDB.getInstance().selectByID(authorId);
                    // Lấy sách theo authorId từ database
                    Set<Book> booksByAuthor = AuthorDB.getInstance().getBooks(author);
                    request.setAttribute("books", booksByAuthor);
                    request.setAttribute("nameOfCategory", "Sách của tác giả " + author.getName());
                    request.setAttribute("currentTab", author.getName());

                    // Forward đến trang hiển thị
                    request.getServletContext().getRequestDispatcher("/bookdisplay.jsp").forward(request, response);
                    return;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid author ID: " + parts[1]);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid author ID.");
                    return;
                }
            }
        }

        // Xử lý các hành động khác (ví dụ: allbook)
        String action = pathInfo != null ? pathInfo.substring(1) : "";
        if ("allbook".equalsIgnoreCase(action)) {
            List<Book> allBook = null;
            if (session.getAttribute("allBook") != null) {
                System.out.println("Books already exist in session");
                allBook = (List<Book>) session.getAttribute("allBook");
            } else {
                System.out.println("Fetching all books from database");
                allBook = BookDB.getInstance().selectAll();
                session.setAttribute("allBook", allBook);
            }

            request.setAttribute("books", allBook);
            request.setAttribute("nameOfCategory", "Tất cả các sách");
            request.setAttribute("currentTab", "Tất cả các sách");
            request.getServletContext().getRequestDispatcher("/bookdisplay.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found.");
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
        //processRequest(request, response);
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
