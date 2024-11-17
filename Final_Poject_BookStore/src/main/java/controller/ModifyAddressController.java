/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.AddressDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import model.Address;
import model.Customer;

/**
 *
 * @author PC
 */
@WebServlet(name = "AddAddressController", urlPatterns = {"/modifyaddress"})
public class ModifyAddressController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        
        String url = "/WEB-INF/views/customers/address.jsp";
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
        }
        else{
            // lấy các thông tin address
            String fullName = request.getParameter("name");
            String phonenumber = request.getParameter("phone");
            String tinh = request.getParameter("tinh");
            String quan = request.getParameter("quan");
            String phuong = request.getParameter("phuong");
            String street = request.getParameter("addressDetail");
            // lấy khách hàng
            Customer customer = (Customer) session.getAttribute("user");
            if(customer != null){
                // lấy action
                String action = request.getParameter("action");
                if(action.equals("add")){               
                    Address a = new Address(fullName, phonenumber, street,
                            tinh, phuong, quan, false, customer);
                    if(!AddressDB.getInstance().insert(a))
                        System.out.println("Lưu không thành công");
                    
                }
                else if(action.equals("edit")){
                    String idStr = request.getParameter("addressId");
                    try{
                        // find Address
                        int id = Integer.parseInt(idStr);   
                        Address a = AddressDB.getInstance().selectByID(id);
                        if(a != null){
                            a.setFullName(fullName);
                            a.setPhonenumber(phonenumber);
                            a.setDistrict(quan);
                            a.setProvince(tinh);
                            a.setWard(phuong);
                            a.setStreet(street);
                            // lưu cập nhật
                            if(!AddressDB.getInstance().update(a))
                                System.out.println("Cập nhật không thành công");
                        }
                        else
                            System.out.println("Không tìm thấy địa chỉ");

                    }catch(NumberFormatException ex){
                        System.out.println("Vui lòng nhập đúng dữ liệu");
                    }catch (NoSuchElementException ex){
                        System.out.println("Không tìm thấy sách");
                    }
                }
            }
        }
        // chuyển trang
        request.getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
