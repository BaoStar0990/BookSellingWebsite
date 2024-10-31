<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/6/2024
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Naoki</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">--%>
    <%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>--%>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/bookdetails.css" />

    <%-- CSS for book list--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/book-list.css" />
</head>
<body>

    <%--    Header--%>
    <jsp:include page="WEB-INF/views/header.jsp">
        <jsp:param name="currentTab" value="" />
    </jsp:include>

    <%--end Header--%>

<%--    attribute : List<Book>--%>

    <!-- Link -->
    <div class="container mt-2">
        <p class="fw-semibold">
            <a href="/" class="text-decoration-none text-dark">Trang chủ</a>
            <i class="fa-solid fa-chevron-right"></i>
            Lịch sử
            <i class="fa-solid fa-chevron-right"></i>
            ${book.title}
        </p>
    </div>
    <!-- end Link -->

    <div class="container my-4">
        <!-- Short Intro -->
        <div class="row ">
            <div class="col-md-5 text-center" style="padding: 32px 78px; border: 1px solid #f1f1f1;">
                <img src="${pageContext.request.contextPath}${book.imageURL}" class="img-fluid rounded shadow-sm" alt="${book.title}">
            </div>

            <div class="col-md-7 mt-4 ps-4 ps-2">
                <h2 class="fw-bold">${book.title}</h2>

                <!-- Authors (multiple) -->
                <p class="text-muted mt-3">Tác giả:
                    <c:forEach var="author" items="${book.authors}" varStatus="status">
                        <span class="fw-semibold">${author.name}</span><c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </p>

                <!-- Publishers (multiple) -->
                <p class="text-muted">Nhà xuất bản:
                    <c:forEach var="publisher" items="${book.publishers}" varStatus="status">
                        <span class="fw-semibold">${publisher.name}</span><c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </p>

                <!-- Rating -->
                <div class="d-flex align-items-center">
                    <%-- Calculate Average Rating --%>
                    <c:set var="totalRating" value="0" />
                    <c:forEach var="review" items="${book.reviews}">
                        <c:set var="totalRating" value="${totalRating + review.rating}" />
                    </c:forEach>

                    <c:set var="averageRating" value="${totalRating / fn:length(book.reviews)}" />
                    <div class="text-warning fs-5 me-2">
                        <div class="text-warning fs-5 me-2 d-flex">
                            <%-- Render filled stars for the rounded average rating --%>
                            <c:set var="roundedRating" value="${(totalRating / fn:length(book.reviews))}" />
                            <c:forEach var="i" begin="1" end="5">
                                <c:choose>
                                    <%-- Check if current star should be filled based on roundedRating --%>
                                    <c:when test="${i <= roundedRating}">
                                        <i class="fas fa-star text-warning"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="far fa-star text-warning"></i>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>

                    <%-- Round the rating to 1 decimal place and display --%>
                    <span class="text-muted fs-6">
                        <fmt:formatNumber value="${roundedRating}" type="number" maxFractionDigits="1" />
                        (${fn:length(book.reviews)} Đánh giá)
                    </span>
                </div>

                <!-- Price and Discount -->
                <div class="mt-3 d-flex align-items-center">
                    <span class="text-primary fw-semibold fs-3">
                        <fmt:formatNumber value="${book.sellingPrice}" type="number" pattern="#,##0" />đ
                    </span>
                        <span class="text-muted ms-3 text-decoration-line-through mt-1">
                        <fmt:formatNumber value="${book.sellingPrice + (book.sellingPrice * book.discountPercent / 100)}" type="number" pattern="#,##0" />đ
                    </span>
                        <span class="badge background-primary ms-2 mt-1">
                        -<fmt:formatNumber value="${book.discountPercent}" type="number" pattern="#,##0" />%
                    </span>
                </div>

                <!-- Quantity Selector -->
                <div class="mt-3">
                    <label for="quantity" class="me-2">Số lượng</label>
                    <div class="input-group mt-1" style="width: 120px;">
                        <button class="btn btn-outline-secondary" onclick="setQuantity(Math.max(1, ${book.stock}))">-</button>
                        <input type="text" class="form-control text-center" id="quantity" value="1" readonly>
                        <button class="btn btn-outline-secondary" onclick="setQuantity(Math.min(100, ${book.stock}))">+</button>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="mt-4">
                    <button class="btn secondary-btn me-3 d-inline-block">
                        <i class="fas fa-shopping-cart me-2"></i> Thêm vào giỏ hàng
                    </button>
                    <button class="btn primary-btn d-inline-block">
                        Mua ngay
                    </button>
                </div>
            </div>
        </div>
        <!-- end Short Intro -->

        <!-- Detailed Intro -->
        <div class="row mt-5">
            <!-- Book Description -->
            <div class="col-8 pe-5 px-0">
                <h4 class="fw-semibold">Giới thiệu sách</h4>
                <p class="fw-medium">${book.description}</p>
            </div>

            <!-- Product Details -->
            <div class="col-4 py-3 px-4 border rounded">
                <h4 class="fw-semibold mb-3">Thông tin chi tiết</h4>

                <!-- Authors (multiple) -->
                <p class="text-muted mt-3">Tác giả:
                    <c:forEach var="author" items="${book.authors}" varStatus="status">
                        <span class="fw-semibold">${author.name}</span><c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </p>

                <!-- Publishers (multiple) -->
                <p class="text-muted">Nhà xuất bản:
                    <c:forEach var="publisher" items="${book.publishers}" varStatus="status">
                        <span class="fw-semibold">${publisher.name}</span><c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </p>

                <!-- Year of Publication -->
                <p class="text-muted">Năm xuất bản:<span class="fw-semibold"> ${book.publishYear}</span></p>

                <!-- Categories (multiple) -->
                <p class="text-muted">Danh mục:
                    <c:forEach var="category" items="${book.categories}" varStatus="status">
                        <span class="fw-semibold">${category.name}</span><c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </p>

                <!-- Language -->
                <p class="text-muted">Ngôn ngữ: <span class="fw-semibold">${book.language}</span> </p>

                <!-- Stock Quantity -->
                <p class="text-muted">Số lượng còn: <span class="fw-semibold">${book.stock}</span> </p>
            </div>
        </div>
        <!-- end Detailed Intro -->
    </div>

<%--    attribute : relatedBooks--%>

    <!-- Related Books  -->
    <jsp:include page="WEB-INF/views/book/book-list.jsp">
        <jsp:param name="bookListName" value="Sách liên quan" />
        <jsp:param name="booksAttribute" value="relatedBooks" />
        <jsp:param name="seeMoreLink" value="/Naoki/books/relatedbook" />
    </jsp:include>
    <!-- end Related Books  -->

    <!-- User Review -->
    <div class="container mt-4 p-0">
        <h2 class="font-semibold">Đánh giá sản phẩm</h2>
        <hr>
        <%----%>
        <div class="row">
            <div class="col-2 d-flex flex-column justify-content-center align-items-center">
                <%-- Calculate Average Rating --%>
                <c:set var="totalRating" value="0" />
                <c:forEach var="review" items="${book.reviews}">
                    <c:set var="totalRating" value="${totalRating + review.rating}" />
                </c:forEach>

                <%-- Round the rating to 1 decimal place and display --%>
                <h1 class="fw-bold mt-2">
                    <fmt:formatNumber value="${roundedRating}" type="number" maxFractionDigits="1" />/5
                </h1>

                <c:set var="averageRating" value="${totalRating / fn:length(book.reviews)}" />
                <!-- Display the stars based on average rating -->
                <div class="d-flex align-items-center fs-4">
                    <c:forEach var="i" begin="1" end="5">
                        <c:choose>
                            <c:when test="${i <= roundedRating}">
                                <i class="fas fa-star text-warning"></i> <!-- Filled star -->
                            </c:when>
                            <c:otherwise>
                                <i class="far fa-star text-warning"></i> <!-- Empty star -->
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <span class="m-2 fs-5">(${fn:length(book.reviews)} Đánh giá)</span>
            </div>

            <!-- Rating Breakdown -->
            <div class="col-3 mt-2 d-flex flex-column justify-content-center align-items-center">
                <c:forEach var="rating" items="${[5, 4, 3, 2, 1]}">
                    <div class="d-flex align-items-center py-1">
                        <span style="width:16px">${rating}</span>
                        <i class="fas fa-star text-warning"></i>
                        <div class="progress mx-2" style="width: 150px; height: 8px;">
                            <!-- You will need to calculate the percentage of reviews for each rating in your backend logic -->
                            <div class="progress-bar bg-warning" role="progressbar"
                                 style="width: ${rating * 20}%;"
                                 aria-valuenow="${rating}" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>
                        <span>${rating * 10}%</span> <!-- Example percentage, adjust based on logic -->
                    </div>
                </c:forEach>
            </div>

            <!-- Add a Review Section -->
            <div class="col-7 ps-4">
                <h5 class="fw-bold mb-3">Thêm đánh giá của bạn</h5>
                <div class="mb-3">
                    <div class="d-flex align-items-center">
                        <!-- Render stars that the user can click to add a review -->
                        <c:forEach var="i" begin="1" end="5">
                            <i class="fas fa-star fs-4 me-2"
                               style="cursor: pointer; color: ${i <= userRating ? 'gold' : 'gray'};"
                               onclick="setUserRating(${i})"></i>
                        </c:forEach>
                    </div>
                    <textarea class="form-control mt-3" rows="3" placeholder="Viết đánh giá của bạn..."></textarea>
                </div>
                <button class="btn primary-btn">Gửi đánh giá</button>
            </div>
        </div>

        <div class="row mt-5">
            <c:forEach var="review" items="${book.reviews}">
                <div class="row border-top py-3">
                    <!-- User and Date Section -->
                    <div class="col-2 d-flex flex-column mb-2">
                        <h6 class="fw-bold me-3">Taylor Swift</h6>
                        <span class="text-muted">${review.reviewDate}</span>
                    </div>

                    <div class="col-10">
                        <div class="d-flex align-items-center">
                            <c:forEach var="i" begin="1" end="5">
                                <c:choose>
                                    <c:when test="${i <= review.rating}">
                                        <i class="fas fa-star text-warning"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="far fa-star text-muted"></i>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <p class="mt-2">${review.description}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!-- User Review -->

    <!-- Pagination -->
    <div class="container">
        <ul class="pagination justify-content-end pagination-cus font-semibold ">
            <li class="page-item ">
                <a class="page-link px-3 me-1 " href="#">
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

    <%--Footer--%>
    <jsp:include page="WEB-INF/views/footer.jsp"/>
    <%-- end   Footer--%>

    <%--   Pagination--%>
    <script>
        new Splide( '#image-carousel' ).mount();
    </script>
    <!-- end Pagination -->
</body>
</html>
