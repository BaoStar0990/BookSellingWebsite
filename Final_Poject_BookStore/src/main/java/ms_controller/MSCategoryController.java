package ms_controller;

import dbmodel.CategoryDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MSCategoryController", urlPatterns = {"/mscategorycontroller"})
public class MSCategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("admin") == null) {
            return;
        }
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action != null) {
            // Validate CSRF token
            String csrfToken = request.getParameter("_csrf");
            if (!isValidCsrfToken(csrfToken)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                return;
            }
            if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                CategoryDB.getInstance().delete(id);
            } else if (action.equals("add")) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Category category = new Category(name, description);
                CategoryDB.getInstance().insert(category);
            } else if (action.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Category category = new Category(id, name, description);
                CategoryDB.getInstance().update(category);
            }
            response.sendRedirect("mscategorycontroller");
            return;
        }

        List<Category> categories = CategoryDB.getInstance().selectAll();
        request.setAttribute("categories", categories);
        String url = "\\Management-System\\ms-category.jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }

    private boolean isValidCsrfToken(String csrfToken) {
        // Implement CSRF token validation logic
        return true;
    }

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
