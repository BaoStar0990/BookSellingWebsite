package ms_controller;

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
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

@WebServlet("/ms/msreview")
public class MSReviewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(session.getAttribute("reviews") == null){
            List<Review> reviews = ReviewDB.getInstance().selectAll();
            reviews.sort((p1, p2) -> Integer.compare(p2.getReviewID(), p1.getReviewID()));
            session.setAttribute("reviews", reviews);
        }

        req.getServletContext().getRequestDispatcher("/Management-System/ms-review.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if(action != null) {
            switch (action) {
                case "edit":
                {
                    Book book = BookDB.getInstance().selectByID(Integer.parseInt(req.getParameter("bookId")));
                    Customer customer = CustomerDB.getInstance().selectByID(Integer.parseInt(req.getParameter("customerId")));
                    ReviewDB.getInstance().update(new Review(Integer.parseInt(req.getParameter("reviewId")), Integer.parseInt(req.getParameter("rate")), req.getParameter("description"), Date.valueOf(LocalDate.now()), customer, book));
                    break;
                }
                case "delete":
                {
                    System.out.println(req.getParameter("deleteId"));
                    ReviewDB.getInstance().delete(Integer.parseInt(req.getParameter("deleteId")), Review.class);
                    break;
                }
            }
            HttpSession session = req.getSession();
            List<Review> reviews = ReviewDB.getInstance().selectAll();
            reviews.sort((p1, p2) -> Integer.compare(p2.getReviewID(), p1.getReviewID()));
            session.removeAttribute("reviews");
            session.setAttribute("reviews", reviews);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/ms/msreview");

    }
}
