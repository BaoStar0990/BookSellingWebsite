package controller;
import firebasecloud.FirebaseStorageUploader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/uploadServlet")
@MultipartConfig
public class UploadImageController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy file từ request
        Part filePart = request.getPart("imageFile"); // Tên của field trong form
       // String nameOfFileOnCloud = request.getParameter("nameOfFileOnCloud");
        try (InputStream inputStream = filePart.getInputStream()){
            String fileName = filePart.getSubmittedFileName();
            String contentType = filePart.getContentType();
          //  StorageClient.getInstance().bucket().create(fileName, inputStream, filePart.getContentType());
            FirebaseStorageUploader.uploadImage(inputStream,contentType,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
