package ms_controller;

import dbmodel.CategoryDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;
import model.Book;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MSCategoryController", urlPatterns = {"/ms/mscategory"})
public class MSCategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // HttpSession session = req.getSession(false); 
        // if (session == null || session.getAttribute("admin") == null) {
        //     resp.sendRedirect("signin.jsp");
        //     return;
        // }
        updateCategoriesInSession(req);
        req.getServletContext().getRequestDispatcher("/Management-System/ms-category.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // checkAdmin(req, resp);
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "edit": {
                    CategoryDB.getInstance().update(new Category(Integer.parseInt(req.getParameter("id")), req.getParameter("name"), req.getParameter("description")));
                    break;
                }
                case "add": {
                    CategoryDB.getInstance().insert(new Category(req.getParameter("name"), req.getParameter("description")));
                    break;
                }
                case "delete": {
                    int categoryId = Integer.parseInt(req.getParameter("id"));
                    CategoryDB.getInstance().delete(categoryId, Category.class);
                    removeCategoryFromBooksInSession(req, categoryId);
                    break;
                }
            }
            // Update the session attribute after any CRUD operation
            updateCategoriesInSession(req);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/ms/mscategory");
    }

    protected void updateCategoriesInSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        // Get the categories from the database
        List<Category> categories = CategoryDB.getInstance().selectAll();
        // Sort the categories by id in descending order
        categories.sort((c1, c2) -> Integer.compare(c2.getId(), c1.getId()));
        // Update the categories attribute in the session
        session.setAttribute("categories", categories);
    }

    private void removeCategoryFromBooksInSession(HttpServletRequest req, int categoryId) {
        HttpSession session = req.getSession();
        List<Book> books = (List<Book>) session.getAttribute("books");
        if (books != null) {
            for (Book book : books) {
                book.getCategories().removeIf(category -> category.getId() == categoryId);
            }
            session.setAttribute("books", books);
        }
    }

    // protected void checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //     if (req.getSession().getAttribute("admin") == null) {
    //         resp.sendRedirect("signin.jsp");
    //         return;
    //     }
    // }
}


