<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Set the books list from request scope -->
<c:set var="books" value="${requestScope[param.booksAttribute]}" />
<c:set var="booksCount" value="${fn:length(books)}" />

<div class="container p-0 mt-4">
    <!-- Section Header with Title and Optional 'See More' Link -->
    <div class="d-flex border-bottom mb-2">
        <h2 class="font-semibold">${param.bookListName}</h2>
        <c:if test="${not empty param.seeMoreLink}">
            <a href="${pageContext.request.contextPath}/bookdisplay.jsp" class="ms-auto font-medium align-self-end mb-1 text-decoration-none">
                Xem thêm <i class="fas fa-chevron-right"></i>
            </a>
        </c:if>
    </div>

    <!-- Start of the Book List Grid -->
    <div class="row book-list">

    <%--        attribute : books List<Books>--%>

        <c:forEach var="book" items="${books}" varStatus="status">
            <!-- Each book occupies 2, 4, or 6 columns depending on the screen size -->
            <div class="col-6 col-md-4 col-lg-2 p-2">
                <a href="bookdetails" class="d-block text-decoration-none card-shadow" style="color: inherit;">
                    <div class="card">
                        <img src="${pageContext.request.contextPath}${book.imageURL}" class="card-img-top" alt="${book.title}">
                        <div class="card-body">
                            <h6 class="card-title mb-1">${book.title}</h6>
                            <p class="text-muted mb-0 card-text">
                                <c:forEach var="author" items="${book.authors}" varStatus="status">
                                    <span class="fw-semibold">${author.name}</span><c:if test="${!status.last}">, </c:if>
                                </c:forEach>
                            </p>

                            <div class="mt-2">
                                <span class="text-danger font-semibold">
                                    <fmt:formatNumber value="${book.sellingPrice}" type="number" pattern="#,##0" />đ
                                </span>
                                <c:if test="${not empty book.discountPercent}">
                                    <span class="badge bg-danger ms-2">
                                        -<fmt:formatNumber value="${book.discountPercent}" type="number" pattern="#,##0" />%
                                    </span>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>

</div>
