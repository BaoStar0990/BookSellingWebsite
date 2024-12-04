package ms_controller;

import dbmodel.AuthorDB;
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

@WebServlet("/ms/msauthor")
public class MSAuthorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
            if (session.getAttribute("authors") == null) {
                List<Author> authors = AuthorDB.getInstance().selectAll();
                authors.sort((p1, p2) -> Integer.compare(p2.getId(), p1.getId()));
                session.setAttribute("authors", authors);
            }
            req.getServletContext().getRequestDispatcher("/Management-System/ms-author.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

            String action = req.getParameter("action");
            if (action != null) {
                switch (action) {
                    case "edit": {
                        AuthorDB.getInstance().update(new Author(Integer.parseInt(req.getParameter("author_id")), req.getParameter("author"), req.getParameter("description"), req.getParameter("avatar"), req.getParameter("nationality")));
                        break;
                    }
                    case "add": {
                        AuthorDB.getInstance().insert(new Author(req.getParameter("author"), req.getParameter("description"), req.getParameter("avatar"), req.getParameter("nationality")));
                        break;
                    }
                    case "delete": {
                        AuthorDB.getInstance().delete(Integer.parseInt(req.getParameter("deleteId")), Author.class);
                        break;
                    }
                }
                List<Author> authors = AuthorDB.getInstance().selectAll();
                authors.sort((p1, p2) -> Integer.compare(p2.getId(), p1.getId()));
                session.removeAttribute("authors");
                session.setAttribute("authors", authors);
            }
//
            resp.sendRedirect(getServletContext().getContextPath() + "/ms/msauthor");
    }
}
