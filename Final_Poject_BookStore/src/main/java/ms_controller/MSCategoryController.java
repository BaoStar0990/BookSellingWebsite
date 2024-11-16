package ms_controller;

import dbmodel.CategoryDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MSCategoryController", urlPatterns = {"/mscategory"})
public class MSCategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("categories") == null) {
            List<Category> categories = CategoryDB.getInstance().selectAll();
            session.setAttribute("categories", categories);
        }

        req.getServletContext().getRequestDispatcher("/Management-System/ms-category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
//                    CategoryDB.getInstance().delete(Integer.parseInt(req.getParameter("id")));
                    break;
                }
            }
            HttpSession session = req.getSession();
            List<Category> categories = CategoryDB.getInstance().selectAll();
            session.removeAttribute("categories");
            session.setAttribute("categories", categories);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/mscategory");
    }
}
