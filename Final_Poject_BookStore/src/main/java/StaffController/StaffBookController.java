package StaffController;

import dbmodel.*;
import firebasecloud.FirebaseStorageUploader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/staff/staffbook")
@MultipartConfig
public class StaffBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        initializeSessionAttributes(session);

        request.setAttribute("books", session.getAttribute("books"));
        request.setAttribute("authors", session.getAttribute("authors"));
        request.setAttribute("categories", session.getAttribute("categories"));
        request.setAttribute("publishers", session.getAttribute("publishers"));
        request.setAttribute("discountCampaigns", session.getAttribute("discountCampaigns"));
        String url = "/Staff/staff-book.jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void initializeSessionAttributes(HttpSession session) {
        if (session.getAttribute("books") == null) {
            session.setAttribute("books", BookDB.getInstance().selectAll());
        }
        if (session.getAttribute("authors") == null) {
            session.setAttribute("authors", AuthorDB.getInstance().selectAll());
        }
        if (session.getAttribute("categories") == null) {
            session.setAttribute("categories", CategoryDB.getInstance().selectAll());
        }
        if (session.getAttribute("publishers") == null) {
            session.setAttribute("publishers", PublisherDB.getInstance().selectAll());
        }
        if (session.getAttribute("discountCampaigns") == null) {
            session.setAttribute("discountCampaigns", DiscountCampaignDB.getInstance().selectAll());
        }
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
        switch (action) {
            case "add":
                handleAddOrEditBook(request, response, true);
                break;
            case "edit":
                handleAddOrEditBook(request, response, false);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            default:
                processRequest(request, response);
                break;
        }
    }

    private void handleAddOrEditBook(HttpServletRequest request, HttpServletResponse response, boolean isAdd)
            throws ServletException, IOException {
        String bookTitle = request.getParameter("bookTitle");
        Double costPrice = Double.parseDouble(request.getParameter("costPrice"));
        Double sellingPrice = Double.parseDouble(request.getParameter("sellingPrice"));
        int stocks = Integer.parseInt(request.getParameter("stocks"));
        String isbn = request.getParameter("isbn");
        String description = request.getParameter("description");
        int publisherId = Integer.parseInt(request.getParameter("publisher"));
        int publishYear = Integer.parseInt(request.getParameter("publishYear"));
        String discountCampaignIdStr = request.getParameter("discountCampaign");
        String language = request.getParameter("language");
        String[] selectedAuthors = request.getParameter("selectedAuthors").split(",");
        String[] selectedCategories = request.getParameter("selectedCategories").split(",");

        Publisher publisher = PublisherDB.getInstance().selectByID(publisherId);
        DiscountCampaign discountCampaign = discountCampaignIdStr.isEmpty() ? null : DiscountCampaignDB.getInstance().selectByID(Integer.parseInt(discountCampaignIdStr));
        Set<Author> authors = new HashSet<>();
        for (String authorId : selectedAuthors) {
            if (!authorId.isEmpty()) {
                Author author = new Author();
                author.setId(Integer.parseInt(authorId));
                authors.add(author);
            }
        }

        Set<Category> categories = new HashSet<>();
        for (String categoryId : selectedCategories) {
            if (!categoryId.isEmpty()) {
                Category category = new Category();
                category.setId(Integer.parseInt(categoryId));
                categories.add(category);
            }
        }
        
        Book book;
        if (isAdd) {
            book = new Book();
        } else {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            book = BookDB.getInstance().selectByID(bookId);
            if (book == null) {
                request.setAttribute("errorMessage", "Book not found.");
                processRequest(request, response);
                return;
            }
        }
        book.setTitle(bookTitle);
        book.setCostPrice(costPrice);
        book.setSellingPrice(sellingPrice);
        book.setStocks(stocks);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setPublisher(publisher);
        book.setPublishDate(publishYear);
        book.setLanguage(language);
        book.setDiscountCampaign(discountCampaign);

        // Handle file upload
        Part filePart = request.getPart("urlImage");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            InputStream inputStream = filePart.getInputStream();
            String contentType = filePart.getContentType();
            String urlImage = FirebaseStorageUploader.uploadImage(inputStream, contentType, fileName);
            book.setUrlImage(urlImage);
        }
        
       
        boolean isSuccess = isAdd ? BookDB.getInstance().insertBookAuthorsCategories(book, authors, categories) : BookDB.getInstance().updateBookAuthorsCategories(book, authors, categories);

        if (isSuccess) {
            HttpSession session = request.getSession();
            session.setAttribute("books", BookDB.getInstance().selectAll());
            response.sendRedirect(request.getContextPath() + "/staff/staffbook");
        } else {
            request.setAttribute("errorMessage", isAdd ? "Failed to add book." : "Failed to update book.");
            processRequest(request, response);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        Book b = BookDB.getInstance().selectByID(bookId);

        boolean isDeleted = BookDB.getInstance().delete(b.getId(), Book.class);

        if (isDeleted) {
            HttpSession session = request.getSession();
            session.setAttribute("books", BookDB.getInstance().selectAll());
            response.sendRedirect(request.getContextPath() + "/staff/staffbook");
        } else {
            request.setAttribute("errorMessage", "Failed to delete book.");
            processRequest(request, response);
        }
    }
}
