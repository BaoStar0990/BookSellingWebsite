/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.BookDB;
import java.io.IOException;
import java.io.PrintWriter;

import dbmodel.DiscountCampaignDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;
import model.Book;

/**
 *
 * @author PC
 */
@WebServlet(name = "BookListController", urlPatterns = {"/filterbook/*"})
public class BookListController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        //String action
        String action = request.getPathInfo().substring(1);
        if(action.equals("bookdiscount"))
        {
            List<Book> bookDiscount = null;
            if(session.getAttribute("bookIsBeingDiscounted") != null) {
                System.out.println("Book discounted exist in session");
                bookDiscount   = (List<Book>)session.getAttribute("bookIsBeingDiscounted");
            }else{
                System.out.println("Book discounted doesn't exist in session");
                List<Book> allBook = null;
                if(session.getAttribute("allBook") == null) {
                    System.out.println("--------------------------------------------");
                    System.out.println("Book doesn't exist on session");
                    allBook = BookDB.getInstance().selectAll();
                    session.setAttribute("allBook", allBook);
                }else{ // lan truy cap sau,  lay book trong session
                    System.out.println("--------------------------------------------");
                    System.out.println("Book exist on session");
                    allBook = (List<Book>)session.getAttribute("allBook");
                }
                bookDiscount = allBook.stream().filter(b ->
                        {
                            if(b.getDiscountCampaign() != null) {
                                System.out.println(b.getDiscountCampaign().getCampaignName());
                                if (DiscountCampaignDB.getInstance().isNotExpired(b.getDiscountCampaign()))
                                    return true;
                            }
                            return false;

                        }
                        ).collect(Collectors.toList());
                session.setAttribute("bookIsBeingDiscounted", bookDiscount);
            }
            
            request.setAttribute("books", bookDiscount);
            request.setAttribute("nameOfCategory","Sách đang giảm giá");
            request.setAttribute("currentTab", "bookdiscount"); // set current tab
            request.getServletContext().getRequestDispatcher("/bookdisplay.jsp").forward(request, response);
            System.out.println("--------------------Book discount in session-------------------------------");
        }
//        else if(action.equals("bestsellingbook")) {
//            List<Book> bookBestSelling = null;
//            if(session.getAttribute("bookIsBeingDiscounted") != null) {
//                bookBestSelling   = (List<Book>)session.getAttribute("bookBestSelling");
//            }else{
//                List<Book> allBook = null;
//                if(session.getAttribute("allBook") == null) {
//                    System.out.println("--------------------------------------------");
//                    System.out.println("Book doesn't exist on session");
//                    allBook = BookDB.getInstance().selectAll();
//                    session.setAttribute("allBook", allBook);
//                }else{ // lan truy cap sau,  lay book trong session
//                    System.out.println("--------------------------------------------");
//                    System.out.println("Book exist on session");
//                    allBook = (List<Book>)session.getAttribute("allBook");
//                }
//                bookBestSelling = allBook.stream().filter(b -> DiscountCampaignDB.getInstance().isNotExpired(b.getDiscountCampaign())).collect(Collectors.toList());
//                session.setAttribute("bookBestSelling", bookBestSelling);
//            }
//            request.setAttribute("books", bookBestSelling);
//            request.setAttribute("nameOfCategory","Sách bán chạy");
//            request.setAttribute("currentTab", "bestsellingbook"); // set current tab
//            request.getServletContext().getRequestDispatcher("/bookdisplay.jsp").forward(request, response);
//            System.out.println("--------------------Book discount in session-------------------------------");
//        }else{
//
//        }

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
