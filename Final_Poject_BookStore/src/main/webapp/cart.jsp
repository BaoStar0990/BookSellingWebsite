<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.Comparator"%>
<%@page import="model.OrderDetail"%>
<%@page import="java.util.Set"%>
<%@page import="model.Bill"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: vuxua
  Date: 16/10/2024
  Time: 8:48 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Naoki-Cart</title>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/cart.css" />

</head>
<body>
<%--    Header--%>
<jsp:include page="WEB-INF/views/header.jsp">
    <jsp:param name="currentTab" value="" />
</jsp:include>
<%--end Header--%>
        
<!-- Link -->
<div class="container mt-2">
    <p class="fw-semibold">
        <a href="/" class="text-decoration-none text-dark">Trang chủ</a>
        <i class="fa-solid fa-chevron-right"></i>
        Giỏ hàng
    </p>
</div>
<!-- end Link -->
<div class="container mt-4">
    <!-- Thông báo lỗi -->
    <c:choose>
        <c:when test="${requestScope.errorMessage == null}">
            <div id="errorMessage" class="alert alert-danger d-none"></div>
        </c:when>
        <c:otherwise>
            <div id="errorMessage" class="alert alert-danger">${requestScope.errorMessage}</div>
        </c:otherwise>
    </c:choose>
        
    <h3 class="fw-bold mb-4">Giỏ hàng của bạn</h3>
    <div class="table-responsive" style="height:500px;overflow:auto;">
        <table class="table align-middle text-center">
            <thead class="bg-light" style="position:sticky; top:0; background-color:white;">
            <tr>
                <th>Sản phẩm</th>
                <th class="d-none d-md-table-cell">Thành tiền</th>
                <th>Số lượng</th>
            </tr>
            </thead>
            <tbody>
               <c:set var="totalCost" value="0.0" />

                <c:if test = "${not empty listOrderDetails}">
                    <c:forEach var="orderDetail" items = "${listOrderDetails}">
                        <c:set var="totalCost" value="${totalCost + orderDetail.getBook().sellingPrice*(1 - orderDetail.getBook()
                                                           .getDiscountCampaign().getPercentDiscount()) * orderDetail.getQuantity()}"/>
                        <tr class="cart-item-row"> 
                            <!-- Product Details -->
                            <td class="text-start d-flex align-items-center flex-column flex-md-row">
                                <img src="${orderDetail.getBook().getUrlImage()}"
                                     alt="Nhà Giả Kim" class="img-fluid me-3" style="width: 80px; height: auto;">
                                <div class="text-center text-md-start">
                                    <p class="fw-bold mb-1 cart-item-title">${orderDetail.getBook().getTitle()}</p>
                                    <p class="text-muted fw-semibold mb-1">
                                        <fmt:formatNumber value="${orderDetail.getBook().sellingPrice*(1 - orderDetail.getBook()
                                                                   .getDiscountCampaign().getPercentDiscount())}" type="number" pattern="#,##0" />đ
                                    </p>
                                    <p class="text-muted text-decoration-line-through">
                                        <fmt:formatNumber value="${orderDetail.getBook().getSellingPrice()}" type="number" pattern="#,##0" />đ
                                    </p>
                                </div>
                            </td>
                            <!-- Price for Desktop -->
                            <td class="fw-bold fs-5 text-primary d-none d-md-table-cell">
                                <fmt:formatNumber value="${orderDetail.getBook().sellingPrice*(1 - orderDetail.getBook()
                                                           .getDiscountCampaign().getPercentDiscount()) * orderDetail.getQuantity()}"
                                                  type="number" pattern="#,##0" />đ
                            </td>
                            <!-- Quantity Control -->
                            <td class="text-center">
                                <p class="fw-bold fs-5 text-danger mb-2 d-block d-md-none">
                                    <!-- Tăng quantity, giá thay đổi -->
                                    <fmt:formatNumber value="${orderDetail.getQuantity()}"  type="number" pattern="#,##0" />đ
                                </p>

                                    <div class="d-flex justify-content-center align-items-center">
                                        <div style="display: flex;  align-items: center;">
                                            <form  action ="${pageContext.request.contextPath}/modifyitemcart" method="post">
                                                    <input type="hidden" name ="action" value ="decreate">
                                                    <input type ="hidden" id = "orderDetailId" value="${orderDetail.getId()}" name = "orderDetailId">
                                                    <button type = "submit" name = ""  class="btn btn-outline-secondary">-</button>
                                            </form>
                                                    
                                            <form>
                                                <input type="text" name ="quantity" id = "quantity" value="${orderDetail.getQuantity()}" 
                                                    class="form-control text-center mx-2" style="width: 50px;" readonly>
                                            </form>
                                            

                                            <form  action ="${pageContext.request.contextPath}/modifyitemcart" method="post">
                                                <input type="hidden" name ="action" value ="increate">
                                                <input type ="hidden" id = "orderDetailId" value="${orderDetail.getId()}" name = "orderDetailId"> 
                                                <button  type = "submit" class="btn btn-outline-secondary">+</button>
                                            </form>

                                        </div>

                                        <form  action ="${pageContext.request.contextPath}/modifyitemcart" method="post">
                                            <input type="hidden" name ="action" value ="delete">
                                            <input type ="hidden" id = "orderDetailId" value="${orderDetail.getId()}" name = "orderDetailId">
                                            <button  type = "submit"  class="btn primary-btn ms-3">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </form>
                                   </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                                
            </tbody>
        </table>
    </div>

    <!-- Total -->
    <div class="d-flex justify-content-between align-items-center mt-4">
        <h4 class="fw-bold ms-auto me-4">Tổng: </h4>
        <h4 class="fw-bold text-primary">
            <fmt:formatNumber value="${totalCost}" type="number" pattern="#,##0" />đ
        </h4>
    </div>
    <!-- Checkout Button -->
    <form action ="/payment" method ="post">
        <div class="mt-4 d-flex justify-content-end">
            <%----<input type="hidden" name ="cartId" value="${cartId}">--%>
            <input type ="submit" value ="Thanh toán" class="primary-btn d-inline-block">
        </div>
    </form>
    
</div>


<%--    Footer--%>
<jsp:include page="WEB-INF/views/footer.jsp"/>
<%-- end Footer--%>
<script src="./assets/javascript/header.js"></script>
<script src="./assets/javascript/quantity.js"></script>
</body>
</html>
