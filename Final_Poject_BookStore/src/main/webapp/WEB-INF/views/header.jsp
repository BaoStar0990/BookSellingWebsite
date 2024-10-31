<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/6/2024
  Time: 11:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header class="container p-0 border-bottom">
    <nav class="navbar navbar-expand-md pt-3">

        <button type="button" class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#headerToggle">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="headerToggle">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 p-0 d-flex align-items-center">
                        <form class="d-flex" action="google.com" role="search">
                            <button onmouseover="showSearch(this)" class="btn" type="submit">
                                <i class="fs-5 fa-solid fa-magnifying-glass"></i>
                            </button>
                            <input class="form-control border-2 border-dark ms-2 search-input" type="search" name="" id="" placeholder="Search">
                        </form>
                    </div>
                    <div class="col-md-4 my-2 d-flex justify-content-center">
                        <a href="${pageContext.request.contextPath}" class="nav-brand d-flex align-items-center justify-content-center">
                            <img src="${pageContext.request.contextPath}/assets/images/logos/logo-1.png" alt="logo" width="90%">
                        </a>
                    </div>

                    <%--Need to disable onclick--%>
                    <div class="col-md-4 my-2 d-flex justify-content-end align-items-center p-0">
                        <div class="dropdown dropstart" id="user">
                            <i class="btn fs-5 fa-solid fa-user header-icon" data-bs-toggle="dropdown"></i>
                            <ul class="dropdown-menu">
                                <c:choose>
                                    <c:when test="${sessionScope.user == null}">
                                        <div class="px-3 py-2">
                                            <a href="signin" class="primary-btn w-100 mb-2">Đăng nhập</a>
                                            <a href="signup" class="secondary-btn w-100">Đăng ký</a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="dropdown-header fs-6 fw-bold">Xin chào, ${sessionScope.user.name}</li>
                                        <div class="dropdown-divider"></div>
                                        <li><a href="${pageContext.request.contextPath}/usersetting.jsp?setting=profile" class="dropdown-item"><i class="fa-solid fa-user me-2"></i>Tài khoản của tôi</a></li>
                                        <li><a href="#" class="dropdown-item"><i class="fa-solid fa-cart-shopping me-2"></i>Đơn hàng của tôi</a></li>
                                        <li><a href="#" class="dropdown-item"><i class="fa-solid fa-gear me-2"></i>Cài đặt</a></li>
                                        <div class="dropdown-divider"></div>
                                        <li>
                                            <form class="m-0" action="signin" method="post">
                                                <input type="hidden" name="action" value="logout">
                                                <a href="javascript:" class="dropdown-item" onclick="parentNode.submit()"><i class="fa-solid fa-right-from-bracket"></i>Đăng xuất</a>
                                            </form>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                            </ul>
                        </div>
                        <a href="${pageContext.request.contextPath}/cart.jsp" class="btn">
                            <i class="fa-solid fa-cart-shopping header-icon pt-1"></i>
                            <span
                                    class="position-absolute translate-middle badge rounded-pill bg-danger text-light"
                                    style="top: calc(50% - 2.5rem)"
                            >
                                2
                            </span>
                        </a>
                    </div>
                </div>
                <ul class="navbar-nav d-flex justify-content-center fw-semibold mt-3 mb-0">
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}" class="nav-link ${param.currentTab eq 'home' ? 'active' : ''}">Trang chủ</a>
                    </li>

                    <li id="danhmuc" class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Danh mục</a>
                        <ul id="dropdowns" class="dropdown-menu mx-auto multi-column columns-3 dropdown-menu-center">

                            <%--attribute : categories List<Category>--%>
                            <c:set var = "count" scope = "request" value = "${4}"/>
                            <c:forEach items="${categories}" var="category">
                                <c:if test="${count > 3}">
                                    <div class="row">
                                    <c:set var = "count" scope = "request" value = "${1}"/>
                                </c:if>
                                <c:if test="${count <= 3}">
                                    <div class="col-md-4">
                                        <li><a href="#" class="dropdown-item btn btn-outline-danger">${category.name}</a></li>
                                    </div>
                                    <c:set var = "count" scope = "request" value = "${count + 1}"/>
                                </c:if>
                                <c:if test="${count > 3}">
                                    </div>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>

                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/bookdisplay.jsp" class="nav-link ${param.currentTab eq 'newbooks' ? 'active' : ''}">Sách mới</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/bookdisplay.jsp" class="nav-link ${param.currentTab eq 'bestsellers' ? 'active' : ''}">Sách bán chạy</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/authors" class="nav-link ${param.currentTab eq 'authors' ? 'active' : ''}">Tác giả</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/aboutus" class="nav-link ${param.currentTab eq 'aboutus' ? 'active' : ''}">Về Naoki</a>
                    </li>
                </ul>
            </div>
        </div>

    </nav>
</header>
