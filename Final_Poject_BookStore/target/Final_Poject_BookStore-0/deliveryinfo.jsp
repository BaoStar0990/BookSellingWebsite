<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Naoki</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Poppins, Swiper, Bootstrap, Favicon, Fontawesome, and Custom CSS --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="icon" href="assets/images/logos/square-logo.png" type="image/x-icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/order.css" />

</head>
<body>
<%-- Header --%>
<jsp:include page="WEB-INF/views/header.jsp">
    <jsp:param name="currentTab" value="" />
</jsp:include>
<%-- end Header --%>

<%-- Order Form --%>
<form action="${pageContext.request.contextPath}/confirmation.jsp" method="post">
    <div class="container mt-4 fw-medium">
        <div class="row">
            <!-- Address Form -->
            <div class="col-lg-6 mb-3">
                <div class="card p-3">
                    <h4 class="fw-semibold mb-3">Thông tin nhận hàng</h4>
                    <div class="mb-3">
                        <label class="mb-1">Sổ địa chỉ</label>
                        <select class="form-select" name="addressBook">
                            <option>Địa chỉ khác...</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Email</label>
                        <input type="email" name="email" class="form-control" required />
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Họ và tên</label>
                        <input type="text" name="fullName" class="form-control" required />
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Số điện thoại (tùy chọn)</label>
                        <input type="tel" name="phone" class="form-control" />
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Tỉnh thành</label>
                        <select class="form-select" id="tinh" name="province" title="Chọn Tỉnh Thành">
                            <option selected value="0">Tỉnh Thành</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Quận huyện (tùy chọn)</label>
                        <select class="form-select" id="quan" name="district" title="Chọn Quận Huyện">
                            <option selected value="0">Quận Huyện</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Phường xã (tùy chọn)</label>
                        <select class="form-select" id="phuong" name="ward" title="Chọn Phường Xã">
                            <option selected value="0">Phường Xã</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Ghi chú (tùy chọn)</label>
                        <textarea name="note" class="form-control"></textarea>
                    </div>
                </div>
            </div>

            <!-- Shipping, Payment, and Order Summary -->
            <div class="col-lg-6 mb-3">
                <div class="card p-3 mb-3">
                    <h4 class="fw-semibold mb-3">Vận chuyển</h4>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="shippingMethod" id="homeDelivery" value="homeDelivery" checked />
                        <label class="form-check-label" for="homeDelivery">
                            Giao hàng tận nơi - 40.000₫
                        </label>
                    </div>
                </div>

                <div class="card p-3 mb-3">
                    <h4 class="fw-semibold mb-3">Thanh toán</h4>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod" id="cod" value="cod" checked />
                        <label class="form-check-label" for="cod">
                            Thanh toán khi giao hàng (COD)
                        </label>
                    </div>
                </div>

                <div class="card p-3">
                    <h4 class="fw-semibold mb-3">Đơn hàng (18 sản phẩm)</h4>
                    <div class="border mb-2" style="max-height: 160px; overflow-y: auto;">
                        <ul class="list-group list-group-flush mb-3">
                            <c:forEach var="i" begin="1" end="6">
                                <li class="list-group-item d-flex align-items-start">
                                    <img src="https://www.netabooks.vn/Data/Sites/1/Product/70144/loi-chua-noi.jpg" alt="" style="width: 50px; height: 50px; margin-right: 10px;" />
                                    <div class="flex-grow-1 pe-4">
                                        <h6>Nhà Giả Kim - Cuốn sách thay đổi cuộc đời bạn mãi mãi</h6>
                                        <p class="mb-0"><fmt:formatNumber value="200000" type="number" pattern="#,##0" />đ x 3</p>
                                    </div>
                                    <span class="fw-semibold">
                                            <fmt:formatNumber value="200000" type="number" pattern="#,##0" />đ
                                        </span>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Nhập mã giảm giá</label>
                        <div class="input-group">
                            <input type="text" name="discountCode" class="form-control" placeholder="Mã giảm giá" />
                            <button class="primary-btn">Áp dụng</button>
                        </div>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Tạm tính</span>
                        <span><fmt:formatNumber value="99999999" type="number" pattern="#,##0" />đ</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Phí vận chuyển</span>
                        <span><fmt:formatNumber value="40000" type="number" pattern="#,##0" />đ</span>
                    </div>
                    <div class="d-flex justify-content-between fw-semibold fs-5 text-primary">
                        <span>Tổng cộng</span>
                        <span><fmt:formatNumber value="99999999" type="number" pattern="#,##0" />đ</span>
                    </div>
                </div>

                <a href="./deliveryinfo.jsp" class="text-decoration-none">
                    <button type="submit" class="primary-btn mt-3 w-100">
                        ĐẶT HÀNG
                    </button>
                </a>

                <div class="text-center mt-3">
                    <a href="./cart.jsp" class="text-decoration-none text-primary fw-semibold">Quay về giỏ hàng</a>
                </div>
            </div>
        </div>
    </div>

</form>
<%-- End Order Form --%>

<%-- Footer --%>
<jsp:include page="WEB-INF/views/footer.jsp" />
<%-- end Footer --%>

<script src="https://esgoo.net/scripts/jquery.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascript/GetAddress.js"></script>

</body>
</html>
