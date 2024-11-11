package ms_controller;

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

@WebServlet("/ms_customer")
public class MSCustomerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> allCustomers = CustomerDB.getInstance().selectAll();

        HttpSession session = req.getSession();
        session.setAttribute("customers", allCustomers);

        req.getServletContext().getRequestDispatcher("/Management-System/ms-customer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/ms_customer");
    }
}
