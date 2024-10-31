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
  <c:forEach var="i" begin="1" end="5">
    <tr>
      <td>DH001</td>
      <td>28/10/2024</td>
      <td>9999999</td>
      <td>COD</td>
      <td>Giao Thành Công</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
