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
</head>
<body>
<%-- Header --%>
<jsp:include page="WEB-INF/views/header.jsp">
    <jsp:param name="currentTab" value="" />
</jsp:include>
<%-- end Header --%>

    <div class="container mt-4 fw-medium">
        <div class="row">
            <!-- Left Section: Customer Information -->
            <div class="col-md-6">
                <h3 class="fw-bold">Cảm ơn vì đã tin tưởng chúng tôi!</h3>
                <p>Đơn hàng của bạn sẽ được bàn giao cho đơn vị chuyển phát trong 24 giờ tới. Xin hãy theo dõi thiết bị liên lạc.</p>
                <h5 class="mt-4 fw-semibold">Thông tin khách hàng</h5>
                <div class="row mt-3">
                    <div class="col-6">
                        <p class="mb-1 fw-bold">Họ và tên</p>
                        <p>Nguyễn Văn A</p>
                    </div>
                    <div class="col-6">
                        <p class="mb-1 fw-bold">Email</p>
                        <p>nguyenvana@gmail.com</p>
                    </div>
                    <div class="col-6">
                        <p class="mb-1 fw-bold">Số điện thoại</p>
                        <p>0912345678</p>
                    </div>
                    <div class="col-6">
                        <p class="mb-1 fw-bold">Địa chỉ</p>
                        <p>1 Võ Văn Ngân, Phường Linh Chiểu, Thành phố Thủ Đức , Thành phố Hồ Chí Minh , Việt Nam</p>
                    </div>
                </div>
                <a href="index.jsp" class="primary-btn mt-3 mb-4 d-inline-block">Tiếp tục mua sắm</a>
            </div>

            <!-- Right Section: Order Summary -->
            <div class="col-md-6">
                <div class="border p-3 rounded bg-light">
                    <h5 class="fw-bold">Đơn hàng</h5>
                    <hr/>
                    <div class="d-flex justify-content-between">
                        <p>Ngày đặt</p>
                        <p>28/10/2024</p>
                    </div>
                    <div class="d-flex justify-content-between">
                        <p>Số hiệu đơn</p>
                        <p>DH12345678</p>
                    </div>
                    <div class="d-flex justify-content-between">
                        <p>Dự kiến</p>
                        <p>28/10/2024</p>
                    </div>
                    <hr/>

                    <!-- Order Items List -->
                    <div style="max-height: 200px; overflow-y: auto;">
                        <c:forEach var="i" begin="1" end="6">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <img src="https://www.netabooks.vn/Data/Sites/1/Product/70144/loi-chua-noi.jpg" alt="product" width="60" class="me-3"/>
                                <div class="flex-grow-1 pe-4">
                                    <h6>Nhà Giả Kim - Cuốn sách thay đổi cuộc đời bạn mãi mãi</h6>
                                    <p class="mb-0"><fmt:formatNumber value="200000" type="number" pattern="#,##0" />đ x 3</p>
                                </div>
                                <span class="fw-semibold">
                                    <fmt:formatNumber value="200000" type="number" pattern="#,##0" />đ
                                </span>
                            </div>
                        </c:forEach>

                    </div>

                    <hr/>
                    <div class="d-flex justify-content-between">
                        <p>Giá sản phẩm</p>
                        <p><fmt:formatNumber value="9999999" type="number" pattern="#,##0" /> đ</p>
                    </div>
                    <div class="d-flex justify-content-between">
                        <p>Giá vận chuyển</p>
                        <p>40,000 đ</p>
                    </div>
                    <hr/>
                    <div class="d-flex justify-content-between fw-bold fs-5">
                        <p>Tổng hóa đơn</p>
                        <p class="text-danger"><fmt:formatNumber value="99999999" type="number" pattern="#,##0" /> đ</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%-- Footer --%>
<jsp:include page="WEB-INF/views/footer.jsp" />
<%-- end Footer --%>


</body>
</html>
