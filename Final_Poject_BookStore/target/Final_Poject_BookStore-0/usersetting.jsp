
<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/25/2024
  Time: 11:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/usersetting.css" />

</head>
<body>
    <!-- Header -->
    <jsp:include page="WEB-INF/views/header.jsp">
        <jsp:param name="currentTab" value="" />
    </jsp:include>
    <!-- end Header -->

    <!-- Customer -->
    <div class="container mt-4 pt-2">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3">
                <!-- Greeting Section -->
                <div class="col-12 mb-3">
                    <h5 class="greeting fw-semibold">
                        Xin chào <span class="text-primary">${user.name}</span>!
                    </h5>
                </div>
                <ul class="list-group fw-semibold">
                    <a class="list-group-item border-0 ${param.setting == 'profile' ? 'active' : ''}" href="usersetting.jsp?setting=profile">Thông tin khách hàng</a>
                    <a class="list-group-item border-0 ${param.setting == 'orders' ? 'active' : ''}" href="usersetting.jsp?setting=orders">Đơn hàng của bạn</a>
                    <a class="list-group-item border-0 ${param.setting == 'address' ? 'active' : ''}" href="usersetting.jsp?setting=address">Thông tin địa chỉ</a>
                    <a class="list-group-item border-0 ${param.setting == 'change-password' ? 'active' : ''}" href="usersetting.jsp?setting=change-password">Đổi mật khẩu</a>

                    <form class="m-0" action="signin" method="post">
                        <input type="hidden" name="action" value="logout">
                        <a href="javascript:" class="list-group-item border-0" onclick="parentNode.submit()">Đăng xuất</a>
                    </form>
                </ul>
            </div>

            <!-- Main content -->
            <div class="col-md-9 mt-4">
                <c:choose>
                    <c:when test="${param.setting == 'profile'}">
                        <%@ include file="WEB-INF/views/customers/profile.jsp" %>
                    </c:when>
                    <c:when test="${param.setting == 'orders'}">
                        <%@ include file="WEB-INF/views/customers/orders.jsp" %>
                    </c:when>
                    <c:when test="${param.setting == 'address'}">
                        <%@ include file="WEB-INF/views/customers/address.jsp" %>
                    </c:when>
                    <c:when test="${param.setting == 'change-password'}">
                        <%@ include file="WEB-INF/views/customers/password.jsp" %>
                    </c:when>
                    <c:when test="${param.setting == 'logout'}">

                    </c:when>
                    <c:otherwise>
                        <%@ include file="WEB-INF/views/customers/profile.jsp" %>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <!-- end Customer -->

    <%--    Footer--%>
    <jsp:include page="WEB-INF/views/footer.jsp"/>
    <%-- end Footer--%>
</body>
</html>
