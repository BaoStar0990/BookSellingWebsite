<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/16/2024
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Naoki</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
  <%--    Swiper--%>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
  <%-- end    Swiper--%>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
  <%-- Favicon --%>
  <link rel="icon" href="assets/images/logos/square-logo.png" type="image/x-icon">
  <%-- Fontawesome --%>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <%-- Custom CSS --%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/book-list.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/author.css" />
</head>
<body>
  <!-- Header -->
  <jsp:include page="WEB-INF/views/header.jsp">
    <jsp:param name="currentTab" value="home" />
  </jsp:include>
  <!-- end Header -->

  <!-- Link -->
  <div class="container mt-2">
    <p class="fw-semibold">
      <a href="/" class="text-decoration-none text-dark">Trang chủ</a>
      <i class="fa-solid fa-chevron-right"></i>
      Tác giả
    </p>
  </div>
  <!-- end Link -->

  <%--
  Test Error Page
  http://localhost:8080/error.jsp?code=404
  --%>

  <%-- Simulate the errors array in JSP --%>
  <%
    class Error {
      String code;
      String name;
      String message;

      public Error(String code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
      }

      public String getCode() { return code; }
      public String getName() { return name; }
      public String getMessage() { return message; }
    }

    List<Error> errors = new ArrayList<>();
    errors.add(new Error("404", "Không tìm thấy trang", "Trang bạn đang tìm kiếm không tồn tại hoặc đã bị xóa."));
    errors.add(new Error("500", "Lỗi máy chủ", "Máy chủ đang gặp sự cố, vui lòng thử lại sau."));
    errors.add(new Error("401", "Không được phép", "Bạn không có quyền truy cập trang này."));
    errors.add(new Error("403", "Cấm truy cập", "Bạn không có quyền truy cập trang này."));
    errors.add(new Error("400", "Yêu cầu không hợp lệ", "Yêu cầu của bạn không hợp lệ, vui lòng kiểm tra lại."));
    errors.add(new Error("405", "Phương thức không hợp lệ", "Phương thức yêu cầu không hợp lệ."));
    errors.add(new Error("503", "Dịch vụ không khả dụng", "Dịch vụ đang tạm thời không khả dụng, vui lòng thử lại sau."));

    String errorCode = request.getParameter("code");
    if (errorCode == null) errorCode = "404";

    Error selectedError = null;
    for (Error e : errors) {
      if (e.getCode().equals(errorCode)) {
        selectedError = e;
        break;
      }
    }
  %>

  <%-- Display the error information --%>
  <div class="container text-center d-flex flex-column justify-content-center align-items-center mt-5">
    <% if (selectedError != null) { %>
    <!-- Error Code -->
    <h1 class="display-1 fw-bold text-primary" style="font-size: 7rem;"><%= selectedError.getCode() %></h1>

    <!-- Error Name -->
    <h2 class="text-secondary"><%= selectedError.getName() %></h2>

    <!-- Error Message -->
    <p class="lead"><%= selectedError.getMessage() %></p>

    <!-- Button to go back -->
    <a href="/" class="primary-btn mt-3 text-decoration-none">Go Back Home</a>
    <% } else { %>
    <!-- Default case if no error matches -->
    <h1 class="display-1 fw-bold text-primary" style="font-size: 7rem;">404</h1>
    <h2 class="text-secondary">Không tìm thấy trang</h2>
    <p class="lead">Trang bạn đang tìm kiếm không tồn tại hoặc đã bị xóa.</p>
    <a href="/" class="primary-btn mt-3 text-decoration-none">Go Back Home</a>
    <% } %>
  </div>

  <%--    Footer--%>
  <jsp:include page="WEB-INF/views/footer.jsp"/>
  <%-- end Footer--%>
  <script src="./assets/javascript/header.js"></script>
</body>
</html>
