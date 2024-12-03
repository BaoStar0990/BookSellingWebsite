package ms_controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;
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
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;

@WebServlet(name = "MSBookController", urlPatterns = {"/ms/msbook"})
@MultipartConfig
public class MSBookController extends HttpServlet {

    // Process GET and POST requests
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
        String url = "/Management-System/ms-book.jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }

    // Get list of books from database and sort them by ID in descending order
    private void getBooks(HttpSession session) {
        session.setAttribute("books", 
            BookDB.getInstance().selectAll().stream()
                .sorted(Comparator.comparing(Book::getId).reversed())
                .toList()
        );
    }

    // Initialize session attributes if they are not already set
    private void initializeSessionAttributes(HttpSession session) {
        if (session.getAttribute("books") == null) {
            getBooks(session);
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

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Handle POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
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

    // Handle adding or editing a book
    private void handleAddOrEditBook(HttpServletRequest request, HttpServletResponse response, boolean isAdd)
            throws ServletException, IOException {

        // Retrieve book details from request
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

        // Retrieve related entities from database
        Publisher publisher = PublisherDB.getInstance().selectByID(publisherId);
        DiscountCampaign discountCampaign = discountCampaignIdStr.isEmpty() ? null : DiscountCampaignDB.getInstance().selectByID(Integer.parseInt(discountCampaignIdStr));
        Set<Author> authors = new HashSet<>();
        System.out.println("----------------------Author ID from request update");
        for (String authorId : selectedAuthors) {
            System.out.println(authorId);
            if (!authorId.isEmpty()) {
                Author author = AuthorDB.getInstance().selectByID(Integer.parseInt(authorId));
                if(author != null){
                    authors.add(author);
                }
            }
        }
        System.out.println("------------------------------------------------------");


        System.out.println("----------------------Cat ID from request update");
        Set<Category> categories = new HashSet<>();
        for (String categoryId : selectedCategories) {
            if (!categoryId.isEmpty()) {
                Category category = CategoryDB.getInstance().selectByID(Integer.parseInt(categoryId));

                if(category != null){
                    categories.add(category);
                }
            }
        }
        System.out.println("------------------------------------------------------");



        System.out.println("-----------------------------------------------");
        System.out.println("Book title"+bookTitle);
        authors.stream().forEach(a -> System.out.println(a.getName()));
        categories.stream().forEach(c -> System.out.println(c.getName()));
        System.out.println("-----------------------------------------------");


        // Add or update book
        Book book;
        if (isAdd) {
            System.out.println("-----------------------------------------------");
            System.out.println("Add book "+bookTitle);
            book = new Book();
        } else {
            System.out.println("-----------------------------------------------");
            System.out.println("update book "+bookTitle);
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
        
       
        // Save book to database
        boolean isSuccess = isAdd ? BookDB.getInstance().insertBookAuthorsCategories(book, authors, categories) : BookDB.getInstance().updateBookAuthorsCategories(book, authors, categories);

        if (isSuccess) {
            HttpSession session = request.getSession();
            getBooks(session);
            response.sendRedirect(request.getContextPath() + "/ms/msbook");
        } else {
            request.setAttribute("errorMessage", isAdd ? "Failed to add book." : "Failed to update book.");
            processRequest(request, response);
        }
    }

    // Handle deleting a book
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        Book b = BookDB.getInstance().selectByID(bookId);

        boolean isDeleted = BookDB.getInstance().delete(b.getId(), Book.class);

        if (isDeleted) {
            HttpSession session = request.getSession();
            getBooks(session);
            response.sendRedirect(request.getContextPath() + "/ms/msbook");
        } else {
            request.setAttribute("errorMessage", "Failed to delete book.");
            processRequest(request, response);
        }
    }
}
