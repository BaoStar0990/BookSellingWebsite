package StaffController;

import dbmodel.PublisherDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Publisher;

import java.io.IOException;
import java.util.List;

@WebServlet("/staff/staffpublisher")
public class StaffPublisherController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(session.getAttribute("publishers") == null){
            List<Publisher> publisher = PublisherDB.getInstance().selectAll();
            session.setAttribute("publishers", publisher);
        }

        req.getServletContext().getRequestDispatcher("/Staff/staff-publisher.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        System.out.println(action);
        if(action != null) {
            switch (action) {
                case "edit":
                {
                    System.out.println(Integer.parseInt(req.getParameter("publisherId")));
                    Publisher publisher = PublisherDB.getInstance().selectByID(Integer.parseInt(req.getParameter("publisherId")));
                    publisher.setName(req.getParameter("publisher"));
                    PublisherDB.getInstance().update(publisher);
                    break;
                }
                case "delete":
                {
                    PublisherDB.getInstance().delete(Integer.parseInt(req.getParameter("deleteId")), Publisher.class);
                    break;
                }
                case "add":
                {
                    Publisher publisher = new Publisher(req.getParameter("publisher"));
                    PublisherDB.getInstance().insert(publisher);
                    break;
                }
            }
            HttpSession session = req.getSession();
            List<Publisher> publisher = PublisherDB.getInstance().selectAll();
            session.removeAttribute("publishers");
            session.setAttribute("publishers", publisher);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/staff/staffpublisher");

    }
}
