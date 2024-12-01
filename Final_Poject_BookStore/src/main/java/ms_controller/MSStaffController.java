package ms_controller;

import dbmodel.StaffDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Staff;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/ms/ms_staff")
public class MSStaffController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(session.getAttribute("staffs") == null){
            List<Staff> staffs = StaffDB.getInstance().selectAll();
            session.setAttribute("staffs", staffs);
        }

        req.getServletContext().getRequestDispatcher("/Management-System/ms-staff.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if(action != null) {
            switch (action) {
                case "edit":
                {
                    String status = req.getParameter("status");
                    boolean status_1 = false;
                    if(status.equals("on")){
                        status_1 = true;
                    }
                    Staff staff = new Staff(Integer.parseInt(req.getParameter("staffId")),
                            req.getParameter("account"),
                            req.getParameter("password"),
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("age")),
                            req.getParameter("phone"),
                            req.getParameter("email"),
                            Double.parseDouble(req.getParameter("salary")),
                            LocalDate.parse(req.getParameter("dob")),
                            status_1
                    );
                    StaffDB.getInstance().update(staff);
                    break;
                }
                case "delete":
                {
                    StaffDB.getInstance().delete(Integer.parseInt(req.getParameter("deleteId")), Staff.class);
                    break;
                }
                case "add":
                {
                    Staff staff = new Staff(req.getParameter("account"),
                            req.getParameter("password"),
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("age")),
                            req.getParameter("phone"),
                            req.getParameter("email"),
                            Double.parseDouble(req.getParameter("salary")),
                            LocalDate.parse(req.getParameter("dob")));
                    StaffDB.getInstance().insert(staff);
                    break;
                }
            }
            HttpSession session = req.getSession();
            List<Staff> staff = StaffDB.getInstance().selectAll();
            session.removeAttribute("staffs");
            session.setAttribute("staffs", staff);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/ms/ms_staff");

    }
}
