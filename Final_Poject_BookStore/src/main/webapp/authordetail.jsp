<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/16/2024
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
  <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logos/square-logo.png" type="image/x-icon">
  <%-- Fontawesome --%>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <%-- Custom CSS --%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/book-list.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
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
      Trang chủ
      <i class="fa-solid fa-chevron-right"></i>
      Tác giả
    </p>
  </div>

  <div class="container mt-4 p-0" style="background: none;">
      <div class="row align-items-center py-4"
           style="background-color: #fff; background-image: repeating-linear-gradient(90deg, #f9d7d7 20px, #ffffff 20px, #ffffff 40px); border-radius: 10px;">
        <!-- Image Column -->
        <div class="col-12 col-md-4 text-center">
          <img src="${author.imageURL}" alt="Image of author" class="img-fluid rounded-circle shadow-sm mb-3"
               style="width: 250px; height: 250px; object-fit: cover;" />
        </div>
        <!-- Author Details Column -->
        <div class="col-12 col-md-8">
          <h2 class="fw-bold">${author.name}</h2>
          <p class="fw-medium">${author.description}</p>
        </div>
      </div>


    <%--    attribute : List<Book>--%>
<%--    Author's book list--%>
    <!-- Book List -->
    <%--    Thay bằng book-list.jsp trên trang động--%>
    <jsp:include page="WEB-INF/views/book/book-list.jsp">
      <jsp:param name="bookListName" value="Sách liên quan" />
      <jsp:param name="booksAttribute" value="books" />
      <jsp:param name="seeMoreLink" value="/additionalbook/author/${author.authorID}" />
    </jsp:include>
<%--    <div class="row book-list">--%>
<%--      <div class="d-flex border-bottom mb-2">--%>
<%--        <h2 class="font-semibold">Sách Liên Quan</h2>--%>
<%--        <a href="${pageContext.request.contextPath}/bookdisplay.jsp" class="ms-auto font-medium align-self-end mb-1 text-decoration-none">--%>
<%--          Xem thêm <i class="fas fa-chevron-right"></i>--%>
<%--        </a>--%>
<%--      </div>--%>
<%--      <c:forEach items="${books}" var="book">--%>
<%--        <div class="col-6 col-md-4 col-lg-2 p-2">--%>
<%--          <a href="/bookdetails/${book.bookID}" class="d-block text-decoration-none card-shadow" style="color: inherit;">--%>
<%--            <div class="card">--%>
<%--              <!-- book image -->--%>
<%--              <img src="${book.urlImage}" class="card-img-top" alt="Sample Book Title">--%>
<%--              <div class="card-body">--%>
<%--                <!-- book title -->--%>
<%--                <h6 class="card-title mb-1">${book.title}</h6>--%>
<%--                <!-- author name -->--%>
<%--                <c:choose>--%>
<%--                  <c:when test = "${not empty book.getAuthors()}">--%>
<%--                      <span class="fw-semibold card-text">--%>
<%--                      <c:forEach var="author" items="${book.getAuthors()}" varStatus="status">--%>
<%--                          ${author.getName()}<c:if test="${!status.last}">, </c:if>--%>
<%--                      </c:forEach>        --%>
<%--                              </span>--%>
<%--                  </c:when>--%>
<%--                  <c:otherwise>--%>
<%--                    <span class="fw-semibold">Chưa có tác giả</span>--%>
<%--                  </c:otherwise>--%>
<%--                </c:choose>--%>
<%--                <div class="mt-2">--%>
<%--                  <!--  price -->--%>
<%--                  <span class="text-danger font-semibold">--%>
<%--                      <fmt:formatNumber value="100000" type="number" pattern="#,##0" />đ--%>
<%--                  </span>--%>
<%--                  <!--  discount -->--%>
<%--                  <span class="badge bg-danger ms-2">-10%</span>--%>
<%--                </div>--%>
<%--              </div>--%>
<%--            </div>--%>
<%--          </a>--%>
<%--        </div>--%>
<%--      </c:forEach>--%>
<%--    </div>--%>
    <!--end Book List -->
  </div>

  <%--    Footer--%>
  <jsp:include page="WEB-INF/views/footer.jsp"/>
  <%-- end Footer--%>
  <script src="./assets/javascript/header.js"></script>
</body>
</html>
