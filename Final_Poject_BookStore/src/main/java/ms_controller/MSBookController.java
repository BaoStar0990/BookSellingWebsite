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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "MSBookController", urlPatterns = {"/msbook"})
@MultipartConfig
public class MSBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//          if(request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect("signin.jsp");
//            return;
//        }
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        if (session.getAttribute("books") == null) {
            List<Book> books = BookDB.getInstance().selectAll();
            books.sort((b1, b2) -> Integer.compare(b2.getId(), b1.getId()));
            session.setAttribute("books", books);
        }

        if (session.getAttribute("authors") == null) {
            List<Author> authors = AuthorDB.getInstance().selectAll();
            session.setAttribute("authors", authors);
        }

        if (session.getAttribute("categories") == null) {
            List<Category> categories = CategoryDB.getInstance().selectAll();
            session.setAttribute("categories", categories);
        }

        if (session.getAttribute("publishers") == null) {
            List<Publisher> publishers = PublisherDB.getInstance().selectAll();
            session.setAttribute("publishers", publishers);
        }

        if (session.getAttribute("discountCampaigns") == null) {
            List<DiscountCampaign> discountCampaigns = DiscountCampaignDB.getInstance().selectAll();
            session.setAttribute("discountCampaigns", discountCampaigns);
        }

        // Prepare JSON strings for authors and categories
        List<Book> books = (List<Book>) session.getAttribute("books");
        for (Book book : books) {
            String authorsJson = book.getAuthors().stream()
                    .map(a -> String.format("{\"id\": %d, \"name\": \"%s\"}", a.getId(), a.getName()))
                    .collect(Collectors.joining(", ", "[", "]"));
            String categoriesJson = book.getCategories().stream()
                    .map(c -> String.format("{\"id\": %d, \"name\": \"%s\"}", c.getId(), c.getName()))
                    .collect(Collectors.joining(", ", "[", "]"));
            book.setAuthorsJson(authorsJson);
            book.setCategoriesJson(categoriesJson);
        }

        request.setAttribute("books", books);
        request.setAttribute("authors", session.getAttribute("authors"));
        request.setAttribute("categories", session.getAttribute("categories"));
        request.setAttribute("publishers", session.getAttribute("publishers"));
        request.setAttribute("discountCampaigns", session.getAttribute("discountCampaigns"));
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
        } else if ("delete".equals(action)) {
            deleteBook(request, response);
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
        String discountCampaignIdStr = request.getParameter("discountCampaign");
        String language = request.getParameter("language");
        String[] selectedAuthors = request.getParameter("selectedAuthors").split(",");
        String[] selectedCategories = request.getParameter("selectedCategories").split(",");

        // Handle file upload
        Part filePart = request.getPart("urlImage");
        String fileName = filePart.getSubmittedFileName();
        InputStream inputStream = filePart.getInputStream();
        String contentType = filePart.getContentType();
        String urlImage = FirebaseStorageUploader.uploadImage(inputStream, contentType, fileName);

        Publisher publisher = PublisherDB.getInstance().selectByID(publisherId);
        DiscountCampaign discountCampaign = discountCampaignIdStr.isEmpty() ? null : DiscountCampaignDB.getInstance().selectByID(Integer.parseInt(discountCampaignIdStr));

        Book book = new Book(bookTitle, description, isbn, costPrice, sellingPrice, stocks, urlImage, publishYear, language, publisher);
        book.setDiscountCampaign(discountCampaign);

        for (String authorId : selectedAuthors) {
            if (!authorId.isEmpty()) {
                Author author = AuthorDB.getInstance().selectByID(Integer.parseInt(authorId));
                book.getAuthors().add(author);
            }
        }

        for (String categoryId : selectedCategories) {
            if (!categoryId.isEmpty()) {
                Category category = CategoryDB.getInstance().selectByID(Integer.parseInt(categoryId));
                book.getCategories().add(category);
            }
        }

        boolean isInserted = BookDB.getInstance().insert(book);

        if (isInserted) {
            HttpSession session = request.getSession();
            List<Book> books = BookDB.getInstance().selectAll();
            session.setAttribute("books", books);
            response.sendRedirect(request.getContextPath() + "/msbook");
        } else {
            request.setAttribute("errorMessage", "Failed to add book.");
            processRequest(request, response);
        }
    }

    private void editBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
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

        // Handle file upload
        Part filePart = request.getPart("urlImage");
        String fileName = filePart.getSubmittedFileName();
        String urlImage = null;
        if (fileName != null && !fileName.isEmpty()) {
            InputStream inputStream = filePart.getInputStream();
            String contentType = filePart.getContentType();
            urlImage = FirebaseStorageUploader.uploadImage(inputStream, contentType, fileName);
        }

        Publisher publisher = PublisherDB.getInstance().selectByID(publisherId);
        DiscountCampaign discountCampaign = discountCampaignIdStr.isEmpty() ? null : DiscountCampaignDB.getInstance().selectByID(Integer.parseInt(discountCampaignIdStr));

        Book book = BookDB.getInstance().selectByID(bookId);
        book.setTitle(bookTitle);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setCostPrice(costPrice);
        book.setSellingPrice(sellingPrice);
        book.setStocks(stocks);
        if (urlImage != null) {
            book.setUrlImage(urlImage);
        }
        book.setPublishDate(publishYear);
        book.setLanguage(language);
        book.setPublisher(publisher);
        book.setDiscountCampaign(discountCampaign);

        book.getAuthors().clear();
        for (String authorId : selectedAuthors) {
            if (!authorId.isEmpty()) {
                Author author = AuthorDB.getInstance().selectByID(Integer.parseInt(authorId));
                book.getAuthors().add(author);
            }
        }

        book.getCategories().clear();
        for (String categoryId : selectedCategories) {
            if (!categoryId.isEmpty()) {
                Category category = CategoryDB.getInstance().selectByID(Integer.parseInt(categoryId));
                book.getCategories().add(category);
            }
        }

        boolean isUpdated = BookDB.getInstance().update(book);

        if (isUpdated) {
            HttpSession session = request.getSession();
            List<Book> books = BookDB.getInstance().selectAll();
            session.setAttribute("books", books);
            response.sendRedirect(request.getContextPath() + "/msbook");
        } else {
            request.setAttribute("errorMessage", "Failed to edit book.");
            processRequest(request, response);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        boolean isDeleted = BookDB.getInstance().delete(bookId);

        if (isDeleted) {
            HttpSession session = request.getSession();
            List<Book> books = BookDB.getInstance().selectAll();
            session.setAttribute("books", books);
            response.sendRedirect(request.getContextPath() + "/msbook");
        } else {
            request.setAttribute("errorMessage", "Failed to delete book.");
            processRequest(request, response);
        }
    }

}
