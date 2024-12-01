package ms_controller;

import dbmodel.DiscountCampaignDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DiscountCampaign;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/ms/ms_campaign")
public class MSCampaignController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(session.getAttribute("campaigns") == null){
            List<DiscountCampaign> discountCampaigns = DiscountCampaignDB.getInstance().selectAll();
            session.setAttribute("campaigns", discountCampaigns);
        }

        req.getServletContext().getRequestDispatcher("/Management-System/ms-campaign.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if(action != null) {
            switch (action) {
                case "add":
                {
                    DiscountCampaign discountCampaign = new DiscountCampaign(req.getParameter("campaign"), LocalDate.parse(req.getParameter("start")),LocalDate.parse(req.getParameter("end")), Double.parseDouble(req.getParameter("discount")));
                    DiscountCampaignDB.getInstance().insert(discountCampaign);
                    break;
                }
                case "edit":
                {
                    DiscountCampaign discountCampaign = new DiscountCampaign(Integer.parseInt(req.getParameter("id")), req.getParameter("campaign"), LocalDate.parse(req.getParameter("start")),LocalDate.parse(req.getParameter("end")), Double.parseDouble(req.getParameter("discount")));
                    DiscountCampaignDB.getInstance().update(discountCampaign);
                    break;
                }
                case "delete":
                {
                    System.out.println(Integer.parseInt(req.getParameter("id")));
                    DiscountCampaignDB.getInstance().delete(req.getParameter("id"), DiscountCampaign.class);
                    break;
                }
            }
            HttpSession session = req.getSession();
            List<DiscountCampaign> discountCampaigns = DiscountCampaignDB.getInstance().selectAll();
            session.removeAttribute("campaigns");
            session.setAttribute("campaigns", discountCampaigns);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/ms/ms_campaign");

    }
}
