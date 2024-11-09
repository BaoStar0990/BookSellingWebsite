<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Naoki</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%--    Poppins--%>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <%--    Swiper--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
    <%--    Bootstrap--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <%-- Favicon --%>
    <link rel="icon" href="assets/images/logos/square-logo.png" type="image/x-icon">
    <%-- Fontawesome --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <%-- Custom CSS --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/book-list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/author.css" />
</head>
<body>
    <!-- Header -->
    <jsp:include page="WEB-INF/views/header.jsp">
        <jsp:param name="currentTab" value="home" />
    </jsp:include>
    <!-- end Header -->

    <%-- New book section--%>
    <jsp:include page="WEB-INF/views/book/book-list.jsp">
        <jsp:param name="bookListName" value="Sách mới" />
        <jsp:param name="booksAttribute" value="bestsellerBooks" />
        <jsp:param name="seeMoreLink" value="/Naoki/books/newbook" />
    </jsp:include>
<%--    --%>
<%--    &lt;%&ndash; On discount section&ndash;%&gt;--%>
    <jsp:include page="WEB-INF/views/book/book-list.jsp">
        <jsp:param name="bookListName" value="Giảm đến 50%" />
        <jsp:param name="booksAttribute" value="bestsellerBooks" />
        <jsp:param name="seeMoreLink" value="/Naoki/books/ondiscount" />
    </jsp:include>
<%--    &lt;%&ndash; Up comming section&ndash;%&gt;--%>
    <jsp:include page="WEB-INF/views/book/book-list.jsp">
        <jsp:param name="bookListName" value="Sắp xuất bản" />
        <jsp:param name="booksAttribute" value="bestsellerBooks" />
        <jsp:param name="seeMoreLink" value="/Naoki/books/upcomming" />
    </jsp:include>

<%--    Author--%>

<%--end    Author--%>
        <jsp:include page="WEB-INF/views/authors.jsp"/>
<%--    Footer--%>
    <jsp:include page="WEB-INF/views/footer.jsp"/>
<%-- end Footer--%>
    <script src="./assets/javascript/header.js"></script>
</body>
</html>