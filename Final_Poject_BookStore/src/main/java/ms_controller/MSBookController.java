package ms_controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import model.Author;
import model.Book;
import model.Category;
import model.Publisher;
import model.DiscountCampaign;
import dbmodel.AuthorDB;
import dbmodel.BookDB;
import dbmodel.CategoryDB;
import dbmodel.PublisherDB;
import dbmodel.DiscountCampaignDB;
import firebasecloud.FirebaseStorageUploader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet(name = "MSBookController", urlPatterns = {"/msbook"})
@MultipartConfig
public class MSBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        if(request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect("signin.jsp");
//            return;
//        }
        //set UTF8 - Tiếng việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Book> books = BookDB.getInstance().selectAll();
        List<Author> authors = AuthorDB.getInstance().selectAll();
        List<Category> categories = CategoryDB.getInstance().selectAll();
        List<Publisher> publishers = PublisherDB.getInstance().selectAll();
        List<DiscountCampaign> discountCampaigns = DiscountCampaignDB.getInstance().selectAll();
        request.setAttribute("books", books);
        request.setAttribute("authors", authors);
        request.setAttribute("categories", categories);
        request.setAttribute("publishers", publishers);
        request.setAttribute("discountCampaigns", discountCampaigns);
        String url = "\\Management-System\\ms-book.jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addBook(request, response);
        } else if ("edit".equals(action)) {
            editBook(request, response);
        } else {
            processRequest(request, response);
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookTitle = request.getParameter("bookTitle");
        Double costPrice = Double.parseDouble(request.getParameter("costPrice"));
        Double sellingPrice = Double.parseDouble(request.getParameter("sellingPrice"));
        int stocks = Integer.parseInt(request.getParameter("stocks"));
        String isbn = request.getParameter("isbn");
        String description = request.getParameter("description");
        int publisherId = Integer.parseInt(request.getParameter("publisher"));
        int publishYear = Integer.parseInt(request.getParameter("publishYear"));
        int discountCampaignId = Integer.parseInt(request.getParameter("discountCampaign"));
        String[] selectedAuthors = request.getParameter("selectedAuthors").split(",");
        String[] selectedCategories = request.getParameter("selectedCategories").split(",");

        // Handle file upload
        Part filePart = request.getPart("urlImage");
        String fileName = filePart.getSubmittedFileName();
        InputStream inputStream = filePart.getInputStream();
        String contentType = filePart.getContentType();
        String urlImage = FirebaseStorageUploader.uploadImage(inputStream, contentType, fileName);

        Publisher publisher = PublisherDB.getInstance().selectByID(publisherId);
        DiscountCampaign discountCampaign = DiscountCampaignDB.getInstance().selectByID(discountCampaignId);

        Book book = new Book(bookTitle, description, isbn, costPrice, sellingPrice, stocks, urlImage, publishYear, "English", publisher);
        book.setDiscountCampaign(discountCampaign);

        for (String authorId : selectedAuthors) {
            Author author = AuthorDB.getInstance().selectByID(Integer.parseInt(authorId));
            book.getAuthors().add(author);
        }

        for (String categoryId : selectedCategories) {
            Category category = CategoryDB.getInstance().selectByID(Integer.parseInt(categoryId));
            book.getCategories().add(category);
        }

        boolean isInserted = BookDB.getInstance().insert(book);

        if (isInserted) {
            response.sendRedirect(request.getContextPath() + "/msbook");
        } else {
            request.setAttribute("errorMessage", "Failed to add book.");
            processRequest(request, response);
        }
    }

    private void editBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Logic to edit an existing book
        // ...code to edit book...
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
