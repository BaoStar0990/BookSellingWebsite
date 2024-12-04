package StaffController;

import dbmodel.CustomerDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;

import java.io.IOException;
import java.util.List;

@WebServlet("/staff/staffcustomer")
public class StaffCustomerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute("customers") == null){
            List<Customer> allCustomers = CustomerDB.getInstance().selectAll();
            session.setAttribute("customers", allCustomers);
        }
        req.getServletContext().getRequestDispatcher("/Staff/staff-customer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if(action != null) {
            switch (action) {
                case "add": {
                    Customer customer = new Customer(req.getParameter("account"), req.getParameter("password"), req.getParameter("fullname"), Integer.parseInt(req.getParameter("age")), req.getParameter("phone"), req.getParameter("email"));
                    CustomerDB.getInstance().insertCustomer(customer);
                    break;
                }
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    CustomerDB.getInstance().deleteCustomer(id);
                    break;
                case "edit": {
                    Customer customer = new Customer(Integer.parseInt(req.getParameter("id")), req.getParameter("username"), req.getParameter("password"), req.getParameter("fullname"), Integer.parseInt(req.getParameter("age")), req.getParameter("phone"), req.getParameter("email"));
                    CustomerDB.getInstance().updateCustomer(customer);
                    break;
                }
                    
            }
            HttpSession session = req.getSession();
            List<Customer> allCustomers = CustomerDB.getInstance().selectAll();
            session.removeAttribute("customers");
            session.setAttribute("customers", allCustomers);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/staff/staffcustomer");
    }
}
