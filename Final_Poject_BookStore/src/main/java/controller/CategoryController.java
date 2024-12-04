package controller;

import dbmodel.AuthorDB;
import dbmodel.BookDB;
import dbmodel.CategoryDB;
import dbmodel.CustomerDB;
import firebasecloud.FirebaseStorageUploader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mail.Mail;
import model.Book;
import model.Category;
import model.Customer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import model.Author;

@WebServlet("/category/*")
public class CategoryController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String requestOfUser = pathInfo.substring(1);

        HttpSession session = request.getSession();
        List<Book> allBook = null;
        Set<Book> bookDisplay = null;
        List<Category> allCategory = null;
        //Nếu có all book trong session
        if(session.getAttribute("allBook") != null && session.getAttribute("categories") != null) {
            allBook = (List<Book>) session.getAttribute("allBook");
            allCategory = (List<Category>) session.getAttribute("categories");
        }else if(session.getAttribute("allBook") == null && session.getAttribute("categories") != null){ // Nếu không co all book trong session
            allBook = BookDB.getInstance().selectAll();
        }else if(session.getAttribute("allBook") == null && session.getAttribute("categories") == null){
            allBook = BookDB.getInstance().selectAll();
            allCategory = CategoryDB.getInstance().selectAll();

            session.setAttribute("allBook", allBook);
            session.setAttribute("categories", allCategory);
        }


        //Xử ly sach can hien thi
        if(allBook != null && allCategory != null) {
            if(requestOfUser.equals("all")){
                request.setAttribute("books", allBook);
                request.setAttribute("nameOfCategory","Tất cả");
                request.getRequestDispatcher("/bookdisplay.jsp").forward(request, response);
                return;
            }else{ // hien thị theo từng mục
                try{
                    int id = Integer.parseInt(requestOfUser);
                    if(id > -1){
                        Category category = allCategory.stream().filter(c -> c.getId() == id).collect(Collectors.toList()).get(0);
                        if(category != null){
                            bookDisplay = category.getBooks();
                            request.setAttribute("nameOfCategory",category.getName());
                            request.setAttribute("currentTab",category.getName());
                        }else{
                            request.setAttribute("nameOfCategory","Không tồn tại danh mục sách này");
                        }

                    }
                }catch(Exception e){
                    response.sendRedirect("/404error.jsp");
                }
            }
        }


        if(bookDisplay != null) {
            request.setAttribute("books", bookDisplay);

            request.getRequestDispatcher("/bookdisplay.jsp").forward(request, response);
        }else{
            response.sendRedirect("/404error.jsp");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //processRequest(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}

