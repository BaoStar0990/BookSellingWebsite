/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Utils.authentication.CSRFUtil;
import Utils.authentication.TokenService;
import dbmodel.BookDB;
import dbmodel.ReviewDB;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;
import model.Review;
import model.Customer;
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
        List<Book> books = null;
        Book book = null;
        HttpSession session = request.getSession();
        String pathInfo = request.getPathInfo();
        int id = -1;
        try {
            id = Integer.parseInt(pathInfo.substring(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Book
        if (id > -1) {
            if (session.getAttribute("allBook") == null) {
                book = BookDB.getInstance().selectByID(id);
                
            } else { // lan truy cap sau,  lay book trong session
                books = (List<Book>) session.getAttribute("allBook");
                try {
                    int id_for_search = id; // nếu lấy id thì sẽ không được 
                    book = books.stream().filter(b -> {
                        return b.getId() == id_for_search;
                    }).findFirst().get();
                } catch (NumberFormatException ex) {
                    System.out.println("Vui lòng nhập đúng dữ liệu");
                } catch (NoSuchElementException ex) {
                    System.out.println("Không tìm thấy sách");
                }
            }
        }

        // Lấy cuốn sách
        if (book != null) {
            List<Review> reviews = ReviewDB.getInstance().selectReviewsByBookID(book.getId());
            reviews.sort(Comparator.comparingInt(Review::getReviewID).reversed());
            
            TokenService tokenService = (TokenService) session.getAttribute("tokenService");
            if(tokenService == null){
                tokenService = new TokenService();
                session.setAttribute("tokenService", tokenService);
            }
            
            String csrfToken = CSRFUtil.getCSRFToken();
            tokenService.storeToken(csrfToken, book.getId());
            
            request.setAttribute("csrfToken", csrfToken);
            session.setAttribute("reviews", reviews);
            request.setAttribute("book", book);
            request.setAttribute("reviews", reviews);

            // Kiểm tra xem người dùng đã review cuốn sách này chưa
            Customer user = (Customer) session.getAttribute("user");
            boolean hasReviewed = false;
            if (user != null) {
                hasReviewed = !ReviewDB.getInstance().selectReviewByBookIDOfACustomer(book.getId(), user.getId()).isEmpty();
            }
            request.setAttribute("hasReviewed", hasReviewed);

            // Get rating breakdown
            Map<Integer, Long> ratingBreakdown = ReviewDB.getInstance().getRatingStatistics(book.getId());
            request.setAttribute("ratingBreakdown", ratingBreakdown);

            String url = "/bookdetails.jsp";
            System.out.println("Called Bookdetail");
            request.getRequestDispatcher(url).forward(request, response);
        } else {
            response.sendRedirect("/404error.jsp");
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