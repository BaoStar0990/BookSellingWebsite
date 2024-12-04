<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="bookList" class="java.util.ArrayList" scope="request" />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Naoki-NewBook</title>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>

        <%--    Swiper--%>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
        <%-- end    Swiper--%>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/csss/all.min.css">
        <%-- Favicon --%>
        <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logos/square-logo.png" type="image/x-icon">
        <%-- Fontawesome --%>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <%-- Custom CSS --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
        <%-- CSS for book list--%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/book-list.css" />
    </head>
    <body>
        <%--    Header--%>
        <jsp:include page="WEB-INF/views/header.jsp">
            <jsp:param name="currentTab" value="${currentTab}" />
        </jsp:include>
        <%--end Header--%>

        <!-- Link -->
        <div class="container mt-2">
            <p class="fw-semibold">
                <a href="${pageContext.request.contextPath}" class="text-decoration-none text-dark">Trang chủ</a>
                <i class="fa-solid fa-chevron-right"></i>
                <a href="#" class="text-decoration-none text-dark">${currentTab}</a>
            </p>
        </div>
        <!-- end Link -->

        <div class="container p-0 mt-4">
            <div class="row">
            <!-- Title and Filter Button -->
            <div class="pb-2 mb-2 border-bottom">
                <div class="d-flex justify-content-between align-items-center">
                    <h2 class="fw-bolder">${nameOfCategory}</h2>
                    <button class="btn secondary-btn" type="button" data-bs-toggle="collapse" data-bs-target="#filterOptions">
                        <i class="fas fa-filter"></i>
                        <span id="filterButtonText">Bộ Lọc</span>
                    </button>
                </div>

                <!-- Filters -->
                <div class="collapse mt-3 " id="filterOptions">
                    <div class="p-3 border rounded bg-light">
                        <h5 class="fw-semibold">Bộ Lọc</h5>
<%--                        <form method="get">--%>
                            <div class="row">
<%--                                <div class="col-md-3">--%>
<%--                                    <label for="authorFilter" class="form-label">Tác giả</label>--%>
<%--                                    <input onchange="filterBooks()" type="text" id="authorFilter" name="authorFilter" class="form-control" value="" placeholder="Enter author name">--%>
<%--                                </div>--%>

                                <div class="col-md-2">
                                    <label for="minPrice" class="form-label">Giá thấp nhất</label>
                                    <input type="number" id="minPrice" name="minPrice" class="form-control" value="0" placeholder="Any">
                                </div>

                                <div class="col-md-2">
                                    <label for="maxPrice" class="form-label">Giá cao nhất</label>
                                    <input type="number" id="maxPrice" name="maxPrice" class="form-control" value="0" placeholder="Any">
                                </div>

                                <div class="col-md-2">
                                    <label for="discountFilter" class="form-label">Giảm giá tối thiểu (%)</label>
                                    <input type="number" id="discountFilter" name="discountFilter" class="form-control" value="" placeholder="Any">
                                </div>

                                <div class="col-md-3 d-flex align-items-end">
                                    <button onclick="filterBooks()" type="button" class="btn primary-btn">Apply Filters</button>
                                </div>
                            </div>
<%--                        </form>--%>
                    </div>
                </div>
            </div>
            <!-- End Filter -->

            <div class="row book-list">
                <c:choose>
                    <c:when test="${books.size() > 0}">

                        <c:forEach var="book" items = "${books}">
                            <div class="col-6 col-md-4 col-lg-2 p-2 pagination-item">
                                <a href="/bookdetails/${book.getId()}" class="d-block text-decoration-none card-shadow" style="color: inherit;">
                                    <div class="card">
                                        <!--book image--> 
                                        <img src=${book.getUrlImage()} class="card-img-top" alt="Sample Book Title">
                                        <div class="card-body">
                                            <!--book title--> 
                                            <h6 class="card-title mb-1">${book.getTitle()}</h6>
                                            <!--author name--> 
                                            <p class="text-muted mb-0 card-text">
                                                <!-- Kiem tra tac gia co rong khong -->
                                                <c:choose>
                                                    <c:when test = "${not empty book.getAuthors()}">
                                                        <span class="fw-semibold">
                                                            <c:forEach var="author" items="${book.getAuthors()}" varStatus="status">
                                                                ${author.getName()}<c:if test="${!status.last}">, </c:if>
                                                            </c:forEach>        
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="fw-semibold">Chưa có tác giả</span>
                                                    </c:otherwise>
                                                </c:choose>

                                            </p>
                                            <div class="bookPriceDiscount mt-2">
                                                <span class="text-danger font-semibold">
                                                    <fmt:formatNumber value="${book.getSellingPrice()}" type="number" pattern="#,##0" />đ
<%--                                                    ${book.getSellingPrice()}--%>
                                                </span>
                                                <c:if test="${not empty book.getDiscountCampaign()}">
                                                    <span class="badge bg-danger ms-2">
                                                        <fmt:formatNumber value="${book.getDiscountCampaign().getPercentDiscount()*100}" type="number" pattern="#,##0" />%
                                                    </span>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h1>Không tìm thấy ${nameOfCategory}</h1>
                        <img src="${pageContext.request.contextPath}/assets/images/notfound_image.svg" alt="Không tìm thấy ${nameOfCategory}" height="300" width="300"/>
                    </c:otherwise>
                </c:choose>
            </div>  


            <!--end Book List -->

            <!-- Pagination -->
            <jsp:include page="WEB-INF/views/pagination.jsp" />
            <!--end Pagination -->
        </div> <!-- Close the container div -->

        <script src="${pageContext.request.contextPath}/assets/javascript/filterBook.js"></script>

    </div> <!-- Close the outer container div -->

    <%--    Footer--%>
    <jsp:include page="WEB-INF/views/footer.jsp" />
    <%--end Footer--%>
</body>
</html>
