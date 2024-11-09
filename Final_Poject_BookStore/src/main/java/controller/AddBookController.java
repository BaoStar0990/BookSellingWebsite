package controller;

import dbmodel.BookDB;
import firebasecloud.FirebaseStorageUploader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Book;
import model.Category;
import model.Publisher;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@WebServlet("/addbookcontroller")
@MultipartConfig
public class AddBookController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy file từ request
        try {

           // int bookID = Integer.parseInt(request.getParameter("bookID"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String description = request.getParameter("description");
            String isbn = request.getParameter("isbn");
            Double costPrice = Double.parseDouble(request.getParameter("costPrice"));
            Double sellingPrice = Double.parseDouble(request.getParameter("sellingPrice"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int publishYear = Integer.parseInt(request.getParameter("publishYear"));
            //String language = request.getParameter("language");
            String language = "Tiếng Việt";
            String urlOfBook = "";
            //Add book oncloud
            Part filePart = request.getPart("imageFile"); // Tên của field trong form
            String fileName = filePart.getSubmittedFileName();
            String nameOfFileOnCloud = request.getParameter("nameOfFileOnCloud");
            InputStream inputStream = filePart.getInputStream();
            String contentType = filePart.getContentType();
            urlOfBook = FirebaseStorageUploader.uploadImage(inputStream, contentType, fileName);


          //  System.out.println("bookId: "+ bookID);
            System.out.println("title: "+ title);
            System.out.println("author: "+ author);
            System.out.println("description: "+ description);
            System.out.println("isbn: "+ isbn);
            System.out.println("costPrice: "+ costPrice);
            System.out.println("sellingPrice: "+ sellingPrice);
            System.out.println("stock: "+ stock);
            System.out.println("language: "+ language);
            System.out.println("Url of image: "+urlOfBook);
            System.out.println("Name of file: "+fileName);
            System.out.println("Publish year: "+publishYear);

//            Category category = null;
            Publisher publisher = null;

            Book book = new Book(title,description,isbn,costPrice,sellingPrice,stock,urlOfBook,publishYear,language,publisher);
            BookDB.getInstance().insert(book);

        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}
