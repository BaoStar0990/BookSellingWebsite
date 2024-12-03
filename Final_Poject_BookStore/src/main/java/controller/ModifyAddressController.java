/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dbmodel.AddressDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import model.Address;
import model.Customer;

/**
 *
 * @author PC
 */
@WebServlet(name = "ModifyAddressController", urlPatterns = {"/modifyaddress"})
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
        
        String errorMessage = null;
        String url = "/usersetting.jsp?setting=address";
        // Lấy session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            url = "/signin.jsp";
        }
        else{
            // lấy khách hàng
            Customer customer = (Customer) session.getAttribute("user");
            if(customer != null){
                // lấy địa chỉ của khách hàng
                List<Address> addresses = customer.getListAddresses();
                // lấy action
                String action = request.getParameter("action");
                if(action.equals("defaultAddress")){
                    String idStr = request.getParameter("idDefault");
                    try{                      
                        // find Address
                        System.out.println("---------------" + idStr);
                        int id = Integer.parseInt(idStr); 
                        Address a = AddressDB.getInstance().selectByID(id);
                        if(a != null){
                            if(isUserAddress(id, addresses)){
                                if(!customer.setDefaultAddress(a))
                                    System.out.println("Lỗi đặt địa chỉ mặc định");
                            }
                        }
                        else
                            System.out.println("Lỗi tìm địa chỉ");
                                
                            
                    }catch(NumberFormatException ex){
                        System.out.println("Vui lòng nhập đúng dữ liệu");
                    }catch (NoSuchElementException ex){
                        System.out.println("Không tìm thấy sách");
                    }
                }
                if(action.equals("delete")){
                    String idStr = request.getParameter("idDelete");
                    try{                      
                        // find Address
                        int id = Integer.parseInt(idStr); 
                        System.out.println("---------------" + idStr);
                        Address a = AddressDB.getInstance().selectByID(id);
                        if(a != null){   
                            if(isUserAddress(id, addresses)){
                                if(AddressDB.getInstance().delete(id, Address.class))
                                    System.out.println("Xóa địa chỉ không thành công");
                            }
                        }
                        else
                            System.out.println("Lỗi tìm địa chỉ");
                                
                            
                    }catch(NumberFormatException ex){
                        System.out.println("Vui lòng nhập đúng dữ liệu");
                    }catch (NoSuchElementException ex){
                        System.out.println("Không tìm thấy sách");
                    }
                }
                else{
                    // lấy các thông tin address
                    String fullName = request.getParameter("name");
                    String phonenumber = request.getParameter("phone");
                    String tinh = request.getParameter("tinhText");
                    String quan = request.getParameter("quanText");
                    String phuong = request.getParameter("phuongText");
                    String street = request.getParameter("addressDetail");

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
                                if(isUserAddress(id, addresses)){
                                    a.setFullName(fullName);
                                    a.setPhonenumber(phonenumber);
                                    a.setDistrict(quan);
                                    a.setProvince(tinh);
                                    a.setWard(phuong);
                                    a.setStreet(street);
                                    // lưu cập nhật
                                    if(!AddressDB.getInstance().update(a)){
                                        errorMessage = "Cập nhật không thành công!";                                    
                                    }
                                }
                            }
                            else{
                                errorMessage = "Lỗi tìm đơn hàng!";
                                
                            }
                        }catch(NumberFormatException ex){
                            errorMessage = "Vui lòng nhập đúng dữ liệu";
                        }catch (NoSuchElementException ex){
                            errorMessage =  "Không tìm thấy đơn hàng";
                            
                        }
                    }
                }
                
            }
        }
        if(errorMessage != null){
            request.setAttribute("errorMessage", errorMessage);
            request.getServletContext()
                       .getRequestDispatcher(url).forward(request, response);
        }
        else
            response.sendRedirect(url);
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
    
    private boolean isUserAddress(int id, List<Address> addresses){
        return addresses.stream()
                .anyMatch(a -> a.getId() == id);
    }
}
