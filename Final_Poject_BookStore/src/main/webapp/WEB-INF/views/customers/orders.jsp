<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Bill"%>
<%@page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h4 class="mb-3 fw-semibold">Đơn hàng của bạn</h4>
<table class="table table-striped custom-table fw-medium">
  <thead>
  <tr class="text-start">
    <th scope="col">Đơn hàng</th>
    <th scope="col">Ngày đặt</th>
    <th scope="col">Giá trị đơn hàng</th>
    <th scope="col">PT thanh toán</th>
    <th scope="col">Trạng thái</th>
  </tr>
  </thead>
  <tbody>
      <!--lấy các đơn hàng của khách hàng-->
      
  <c:forEach var="order" items = "${sessionScope.user.getOrders()}">
    <tr>
      <td>${order.getId()}</td>
      <td>${order.getOrderDate()}</td>
      <c:set var = "total" value = "0"/>
          <c:forEach var="orderDetail" items="${order.getOrderDetails()}">
                <c:set var="total" value="${totalCost + orderDetail.getUnitPrice() * orderDetail.getQuantity()}"/>
          </c:forEach>
      <td>${total}đ</td>
      <td>${order.getPaymentMethod()}</td>
      <td>${order.getStatusOrder()}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
