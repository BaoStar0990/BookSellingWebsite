package controller;

import Utils.authentication.TokenService;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import dbmodel.BookDB;
import dbmodel.CustomerDB;
import dbmodel.ReviewDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;
import model.Customer;
import model.Review;

@WebServlet(name = "ReviewController", urlPatterns = {"/submitReview", "/deleteReview", "/updateReview"})
public class ReviewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
       
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();
        if ("/submitReview".equals(action)) {
            handleReviewSubmission(request, response);
        } else if ("/updateReview".equals(action)) {
            handleReviewUpdate(request, response);
        } else if ("/deleteReview".equals(action)) {
            handleReviewDeletion(request, response);
        } else {
            processRequest(request, response);
        }
    }

    // Handle review submission
    private void handleReviewSubmission(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get user from session
        HttpSession session = request.getSession();
        Customer user = (Customer) session.getAttribute("user");
        
        // If user is not logged in, return error message
        if (user == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"errorMessage\": \"Vui lòng đăng nhập để viết đánh giá !\"}");
            return;
        }

        // Get bookID, userID, reviewContent, rate from request
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        int userID = user.getId();
        String reviewContent = request.getParameter("reviewContent");
        int rate = Integer.parseInt(request.getParameter("rate"));
        String csrfToken = request.getParameter("csrfToken");
         
        // kiểm tra token
        TokenService tokenService = (TokenService) session.getAttribute("tokenService");

        if (tokenService == null || !tokenService.validateToken(csrfToken, bookID)) {
            response.getWriter().write("{\"errorMessage\":\"Đừng có ngịch.\"}");
            return;
        }
                
        // Create review object
        ReviewDB reviewDB = ReviewDB.getInstance();
        Book book = BookDB.getInstance().selectByID(bookID);
        Customer customer = CustomerDB.getInstance().selectByID(userID);
        Review review = new Review(rate, reviewContent, Date.valueOf(LocalDate.now()), customer, book);
        
        // Insert review to database
        boolean success = reviewDB.insert(review);
        if (success) {
            List<Review> reviews = ReviewDB.getInstance().selectReviewsByBookID(bookID);
            session.setAttribute("reviews", reviews);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"redirect\": \"/bookdetails/" + bookID + "\"}");
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"errorMessage\": \"Đã xảy ra lỗi khi gửi đánh giá !\"}");
        }
    }

    // Handle review update
    private void handleReviewUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Get user from session
        HttpSession session = request.getSession();
        Customer user = (Customer) session.getAttribute("user");
        
        // If user is not logged in, return error message
        if (user == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"errorMessage\": \"Vui lòng đăng nhập để cập nhật đánh giá !\"}");
            return;
        }

        // Get reviewID, reviewContent, rate from request
        int reviewID = Integer.parseInt(request.getParameter("reviewID"));
        String reviewContent = request.getParameter("reviewContent");
        int rate = Integer.parseInt(request.getParameter("rate"));

        // Update review in database
        ReviewDB reviewDB = ReviewDB.getInstance();
        Review review = reviewDB.selectByID(reviewID);

        // Check if review exists and user has permission to update
        if (review != null && review.getCustomer().getId() == user.getId()) {
            review.setDescription(reviewContent);
            review.setRate(rate);
            boolean success = reviewDB.update(review);
            if (success) {
                List<Review> reviews = ReviewDB.getInstance().selectReviewsByBookID(review.getBook().getId());
                session.setAttribute("reviews", reviews);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"redirect\": \"/bookdetails/" + review.getBook().getId() + "\"}");
            } else {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"errorMessage\": \"Đã xảy ra lỗi khi cập nhật đánh giá !\"}");
            }
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"errorMessage\": \"Bạn không có quyền cập nhật đánh giá này !\"}");
        }
    }

    // Handle review deletion
    private void handleReviewDeletion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        Customer user = (Customer) session.getAttribute("user");

        if (user == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"errorMessage\": \"Vui lòng đăng nhập để xóa đánh giá !\"}");
            return;
        }

        int reviewID = Integer.parseInt(request.getParameter("reviewID"));

        ReviewDB reviewDB = ReviewDB.getInstance();
        Review review = reviewDB.selectByID(reviewID);

        // Check if review exists and user has permission to delete
        if (review != null && review.getCustomer().getId() == user.getId()) {
            boolean success = reviewDB.deleteReview(reviewID);
            if (success) {
                List<Review> reviews = ReviewDB.getInstance().selectReviewsByBookID(review.getBook().getId());
                session.setAttribute("reviews", reviews);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"redirect\": \"/bookdetails/" + review.getBook().getId() + "\"}");
            } else {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"errorMessage\": \"Đã xảy ra lỗi khi xóa đánh giá !\"}");
            }
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"errorMessage\": \"Bạn không có quyền xóa đánh giá này !\"}");
        }
    }
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
