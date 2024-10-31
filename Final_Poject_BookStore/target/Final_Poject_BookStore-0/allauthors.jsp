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
    <jsp:param name="currentTab" value="authors" />
  </jsp:include>
  <!-- end Header -->

  <!-- Link -->
  <div class="container mt-2">
    <p class="fw-semibold">
      <a href="${pageContext.request.contextPath}" class="text-decoration-none text-dark">Trang chủ</a>
      <i class="fa-solid fa-chevron-right"></i>
      <a href="${pageContext.request.contextPath}/authors" class="text-decoration-none text-dark">Tác giả</a>
    </p>
  </div>
  <!-- end Link -->

<%--  attribute : authors List<Author>--%>

<!-- Authors -->

  <div class="container mt-4 author-section pb-4">
    <h2 class="text-start mb-4 font-bold">Các tác giả</h2>

    <div class="row justify-content-center">
      <!-- Loop through authors using JSTL -->
      <c:forEach items="${authors}" var="author">
        <div class="col-6 col-md-4 col-lg-2 text-center mb-4">
          <a href="${pageContext.request.contextPath}/authordetail.jsp" class="text-decoration-none text-dark">
            <div class="author-card">
              <img src="${pageContext.request.contextPath}${author.imageURL}" alt="${author.name}" class="author-image img-fluid rounded-circle" />
              <p class="fw-bold mt-2">${author.name}</p>
            </div>
          </a>
        </div>
      </c:forEach>
    </div>
    </div>

  <!--end Authors -->

    <!-- Pagination -->
    <div class="container mt-4">
      <ul class="pagination justify-content-center pagination-cus font-semibold ">
        <li class="page-item ">
          <a class="page-link px-3 me-1" href="#" >
            <i class="fa-solid text-muted fa-chevron-left fs-5"></i>
          </a>
        </li>
        <li class="page-item active ">
          <a class="page-link text-muted px-3 mx-1" href="#">1</a>
        </li>
        <li class="page-item">
          <a class="page-link text-muted px-3 mx-1" href="#">2</a>
        </li>
        <li class="page-item">
          <a class="page-link text-muted px-3 mx-1" href="#">3</a>
        </li>
        <li class="page-item">
          <a class="page-link text-muted px-3 ms-1" href="#">
            <i class="fa-solid fa-chevron-right fs-5"></i>
          </a>
        </li>
      </ul>
    </div>
    <!--end Pagination -->
<%--<div class="container my-4">--%>
<%--  <h2 class="mb-3 font-semibold">Tác giả</h2>--%>

<%--  <div class="row justify-content-center">--%>
<%--    <c:forEach items="${authors}" var="author">--%>
<%--      <div class="col-lg-3 col-md-4 col-sm-6 my-2 text-center">--%>
<%--        <div class="author-card">--%>
<%--          <img src="${pageContext.request.contextPath}${author.imageURL}" alt="${author.name}" class="author-image">--%>
<%--          <p class="font-semibold mt-1">${author.name}</p>--%>
<%--        </div>--%>
<%--      </div>--%>
<%--    </c:forEach>--%>
<%--  </div>--%>
<%--</div>--%>
<!-- end Authors -->

  <%--    Footer--%>
  <jsp:include page="WEB-INF/views/footer.jsp"/>
  <%-- end Footer--%>
  <script src="./assets/javascript/header.js"></script>
</body>
</html>
