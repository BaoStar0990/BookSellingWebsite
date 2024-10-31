<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/29/2024
  Time: 9:21 PM
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/orderdetail.css" />

  <%--Jquery    --%>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<%--    Header--%>
<jsp:include page="WEB-INF/views/header.jsp">
  <jsp:param name="currentTab" value="" />
</jsp:include>
<%--end Header--%>

<!-- Link -->
<!-- Breadcrumb của BS -->
<div class="container">
  <nav style="--bs-breadcrumb-divider:'>'" aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item"><a class="text-decoration-none text-dark" href="#">Trang chủ</a></li>
      <li class="breadcrumb-item"><a class="text-decoration-none text-dark" href="#">Giỏ hàng</a></li>
      <li class="text-decoration-none text-dark breadcrumb-item active" aria-current="page">Thông tin giao hàng</li>
    </ol>
  </nav>
</div>
<!-- end Link -->

<!-- Order -->
<div class="container my-3">
  <h3 class="mb-4">Thông tin giao hàng</h3>
  <p class="mb-3 fw-semibold">Bạn đã có tài khoản? <a class="text-decoration-none text-danger" href="#">Đăng nhập</a></p>
  <div class="row">
    <div class="col-md-4">
      <form action="#">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="name" placeholder="Steve">
          <label class="fw-lighter" for="name">Họ và tên</label>
        </div>
        <div class="form-floating mb-3">
          <input type="email" class="form-control" id="email" placeholder="name@example.com">
          <label class="fw-lighter" for="email">Email</label>
        </div>
        <div class="form-floating mb-3">
          <input type="tel" class="form-control" id="phone" placeholder="0123456789">
          <label class="fw-lighter" for="phone">Số điện thoại</label>
        </div>
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="address" placeholder="name@example.com">
          <label class="fw-lighter" for="address">Địa chỉ</label>
        </div>
        <div class="">
          <select class="form-select mb-3 fw-lighter" id="tinh" name="tinh" title="Chọn Tỉnh Thành">
            <option value="0">Tỉnh Thành</option>
          </select>
          <select class="form-select mb-3 fw-lighter" id="quan" name="quan" title="Chọn Quận Huyện">
            <option value="0">Quận Huyện</option>
          </select>
          <select class="form-select mb-3 fw-lighter" id="phuong" name="phuong" title="Chọn Phường Xã">
            <option value="0">Phường Xã</option>
          </select>
        </div>
        <div class="form-floating mb-3">
          <textarea style="height: 100px;" class="form-control" placeholder="note" id="note"></textarea>
          <label class="fw-lighter" for="note">Ghi chú</label>
        </div>
        <h3 class="mb-3">Thông tin giao hàng</h3>
        <div class="form-check border p-2 d-flex justify-content-around align-items-center rounded mb-2">
          <input type="radio" name="cod" id="cod1">
          <label for="cod1" class="form-check-label">Thanh toán khi nhận hàng</label>
        </div>
        <div class="form-check border p-2 d-flex justify-content-around align-items-center rounded mb-2">
          <input type="radio" name="cod" id="cod2">
          <label for="cod2" class="form-check-label">Thanh toán khi nhận hàng</label>
        </div>
        <div class="form-check border p-2 d-flex justify-content-around align-items-center rounded mb-2">
          <input type="radio" name="cod" id="cod3">
          <label for="cod3" class="form-check-label">Thanh toán khi nhận hàng</label>
        </div>
      </form>
    </div>

    <div class="col-md-8 pt-4">
      <div class="table-responsive" style="height:250px;overflow:auto;">
        <table class="table align-middle text-center">
          <thead class="bg-light" style="position:sticky; top:0; background-color:white;">
          <tr>
            <th>Sản phẩm</th>
            <th class="d-none d-md-table-cell">Thành tiền</th>
            <th>Số lượng</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="i" begin="1" end="5">
            <tr class="cart-item-row">
              <!-- Product Details -->
              <td class="text-start d-flex align-items-center flex-column flex-md-row">
                <img src="https://bizweb.dktcdn.net/thumb/1024x1024/100/363/455/products/nhagiakimnew03.jpg?v=1705552576547"
                     alt="Nhà Giả Kim" class="img-fluid me-3" style="width: 80px; height: auto;">
                <div class="text-center text-md-start">
                  <p class="fw-bold mb-1 cart-item-title">Nhà Giả Kim - Cuốn sách thay đổi cuộc đời bạn mãi mãi</p>
                  <p class="text-muted fw-semibold mb-1">
                    <fmt:formatNumber value="200000" type="number" pattern="#,##0" />đ
                  </p>
                  <p class="text-muted text-decoration-line-through">
                    <fmt:formatNumber value="160000" type="number" pattern="#,##0" />đ
                  </p>
                </div>
              </td>
              <!-- Price for Desktop -->
              <td class="fw-bold fs-5 text-primary d-none d-md-table-cell">
                <fmt:formatNumber value="200000" type="number" pattern="#,##0" />đ
              </td>
              <!-- Quantity Control -->
              <td class="text-center">
                <p class="fw-bold fs-5 text-danger mb-2 d-block d-md-none">
                  <fmt:formatNumber value="200000" type="number" pattern="#,##0" />đ
                </p>

                <div class="d-flex justify-content-center align-items-center">
                  <button class="btn btn-outline-secondary" onclick="">-</button>
                  <input type="text" value="1" class="form-control text-center mx-2" style="width: 50px;" readonly>
                  <button class="btn btn-outline-secondary" onclick="">+</button>
                  <button class="btn primary-btn ms-3" onclick="">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
      <hr>
      <div class="">
        <form class="px-5   d-flex justify-content-start" action="#">
          <input class="form-control" type="text" name="" id="" placeholder="Mã giảm giá">
          <input class="ms-5 btn btn-danger" type="submit" value="Sử dụng">
        </form>
      </div>
      <hr>
      <div class="">
        <div class="px-5 d-flex justify-content-between">
          <p>Tạm tính</p>
          <p>481.800đ</p>
        </div>
        <div class="px-5 d-flex justify-content-between">
          <p>Phí vận chuyển</p>
          <p>Miễn phí</p>
        </div>
      </div>
      <hr>
      <div class="">
        <div class="px-5 d-flex justify-content-between">
          <h4>Tổng cộng</h4>
          <h4>481.800đ</h4>
        </div>
      </div>
      <div class="mt-3 px-5 d-flex justify-content-between align-items-center">
        <a class="text-decoration-none text-danger" href="#"><i class="fa-solid fa-chevron-left"></i> Giỏ hàng</a>
        <a class="btn btn-danger fs-4" href="#">Đặt hàng</a>
      </div>
    </div>
  </div>
</div>
<!--end Order -->

<%--    Footer--%>
<jsp:include page="WEB-INF/views/footer.jsp"/>
<%-- end Footer--%>
<script src="./assets/javascript/orderdetail.js"></script>
</body>
</html>
