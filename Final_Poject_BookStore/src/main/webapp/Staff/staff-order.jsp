<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Naoki - Management System</title>
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
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logos/square-logo.png" type="image/x-icon">
    <%-- Fontawesome --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <%--Custom CSS--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
</head>
<body>
    <!-- Header -->
    <jsp:include page="sidebar.jsp">
        <jsp:param name="currentTab" value="order"/>
    </jsp:include>
    <!-- end Header -->

    <main class="content" style="margin-left: 250px; padding: 20px;">
        <div class="container fw-medium">
            <div class="row mb-3">
                <div class="col-md-4 d-flex flex-column">
                    <label for="searchOrderId">Tìm kiếm bằng mã đơn hàng:</label>
                    <input type="text" id="searchOrderId" class="form-control fw-medium" placeholder="Enter Order ID">
                </div>
                <div class="col-md-4 d-flex flex-column">
                    <label for="statusOrderFilter">Lọc bằng tình trạng đơn hàng:</label>
                    <select id="statusOrderFilter" class="form-control fw-medium">
                        <option value="">All</option>
                        <c:forEach var="statusOrder" items="${StatusOrder}">
                            <option value="${statusOrder}">${statusOrder}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 d-flex flex-column">
                    <label for="statusPaymentFilter">Lọc bằng tình trạng thanh toán:</label>
                    <select id="statusPaymentFilter" class="form-control fw-medium">
                        <option value="">All</option>
                        <c:forEach var="statusPayment" items="${StatusPayment}">
                            <option value="${statusPayment}">${statusPayment}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="table-responsive border border-2 p-2" style="max-height: 75%; overflow: auto;">
                <table class="table table-bordered" style="min-width: 1000px;">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Tình trạng</th>
                        <th>Mã đơn hàng</th>
                        <th>Khách hàng</th>
                        <th>Tổng số tiền</th>
                        <th>Ngày đặt</th>
                        <th>Tình trạng thanh toán</th>
                    </tr>
                    </thead>
                    <tbody id="billList">
                        <c:forEach var="bill" items="${bills}">
                            <tr>
                                <td class="d-flex">
                                    <form action="${pageContext.request.contextPath}/staff/stafforder" method="post" style="display:inline;">
                                        <input type="hidden" name="billId" value="${bill.getId()}">
                                        <button type="submit" class="btn btn-secondary btn-sm me-2" >
                                            <i class="fas fa-eye "></i>
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <span class="badge 
                                        <c:choose>
                                            <c:when test="${bill.getStatusOrder() == 'Processing'}">bg-warning</c:when>
                                            <c:when test="${bill.getStatusOrder() == 'Packing'}">bg-info</c:when>
                                            <c:when test="${bill.getStatusOrder() == 'Delivering'}">bg-primary</c:when>
                                            <c:when test="${bill.getStatusOrder() == 'Delivered'}">bg-success</c:when>
                                            <c:when test="${bill.getStatusOrder() == 'Cancelled'}">bg-danger</c:when>
                                            <c:when test="${bill.getStatusOrder() == 'Completed'}">bg-success</c:when>
                                            <c:otherwise>bg-secondary</c:otherwise>
                                        </c:choose>">
                                        ${bill.getStatusOrder()}
                                    </span>
                                </td>
                                <td>${bill.getId()}</td>
                                <td>${bill.getCustomer().getFullName()}</td>
                                <td>${bill.getGrandTotal()}</td>
                                <td>${bill.getOrderDate()}</td>
                                <td>
                                    <span class="badge 
                                        <c:choose>
                                            <c:when test="${bill.getStatusPayment() == 'Unpaid'}">bg-danger</c:when>
                                            <c:when test="${bill.getStatusPayment() == 'Processing'}">bg-warning</c:when>
                                            <c:when test="${bill.getStatusPayment() == 'Paid'}">bg-success</c:when>
                                            <c:otherwise>bg-secondary</c:otherwise>
                                        </c:choose>">
                                        ${bill.getStatusPayment()}
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

    <script>
        document.getElementById('searchOrderId').addEventListener('input', filterBills);
        document.getElementById('statusOrderFilter').addEventListener('change', filterBills);
        document.getElementById('statusPaymentFilter').addEventListener('change', filterBills);

        function filterBills() {
            const orderId = document.getElementById('searchOrderId').value.toUpperCase();
            const statusOrder = document.getElementById('statusOrderFilter').value;
            const statusPayment = document.getElementById('statusPaymentFilter').value;
            const rows = document.querySelectorAll('#billList tr');

            rows.forEach(row => {
                const billId = row.cells[2].textContent.toUpperCase();
                const billStatusOrder = row.cells[1].textContent.trim();
                const billStatusPayment = row.cells[6].textContent.trim();

                const matchesOrderId = billId.includes(orderId);
                const matchesStatusOrder = !statusOrder || billStatusOrder === statusOrder;
                const matchesStatusPayment = !statusPayment || billStatusPayment === statusPayment;

                if (matchesOrderId && matchesStatusOrder && matchesStatusPayment) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }
    </script>
</body>
</html>