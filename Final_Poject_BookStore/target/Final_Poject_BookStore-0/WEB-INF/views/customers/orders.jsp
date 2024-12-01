<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Bill"%>
<%@page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h4 class="mb-3 fw-semibold">Đơn hàng của bạn</h4>
<c:choose>
        <c:when test="${requestScope.errorMessage == null}">
            <div id="errorMessage" class="alert alert-danger d-none"></div>
        </c:when>
        <c:otherwise>
            <div id="errorMessage" class="alert alert-danger">${requestScope.errorMessage}</div>
        </c:otherwise>
</c:choose>
<table class="table table-striped custom-table fw-medium">
  <thead>
  <tr class="text-start">
    <th scope="col">Đơn hàng</th>
    <th scope="col">Ngày đặt</th>
    <th scope="col">Giá trị đơn hàng</th>
    <th scope="col">PT thanh toán</th>
    <th scope="col">Trạng thái</th>
    <th scope="col">Action</th>
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
                <c:set var="total" value="${total + orderDetail.getUnitPrice() * orderDetail.getQuantity()}"/>
          </c:forEach>
          <c:set var="total" value="${total + order.getShippingFee()}"/>
      <td>${total}đ</td>
      <td>${order.getPaymentMethod()}</td>
      <td>${order.getStatusOrder()}</td>
      <td>
        <c:if test="${order.getStatusOrder() == 'Delivered'}">
            <form action="/confirmcancelorder" method = "post">
                <input type ="hidden" name ="action" value="confirm">
                <input type="hidden" name="orderId" value="${order.getId()}">
                <button class="form-control text-bg-success rounded-3 ">Xác nhận</button>
            </form>
        </c:if>
        <%-- <c:if test="${(order.getStatusOrder() == 'Processing') || (order.getStatusOrder() == 'Packing') }">
         <form action="/confirmcancelorder">
                <input type ="hidden" name ="action" value="cancel">
                <input type="hidden" name="orderId" value="${order.getId()}">
                <button class="form-control primary-btn rounded-3" >Hủy</button>
        </form>
        </c:if> --%>
        
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
