<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<%--    <script>--%>
<%--        const socket = new WebSocket("ws://localhost:8080/websocket");--%>

<%--        // Khi kết nối mở--%>
<%--        socket.onopen = () => {--%>
<%--            console.log("WebSocket connection established.");--%>
<%--            // Gửi heartbeat ping mỗi 5 giây--%>
<%--            setInterval(() => {--%>
<%--                if (socket.readyState === WebSocket.OPEN) {--%>
<%--                    socket.send(JSON.stringify({ type: "heartbeat", timestamp: Date.now() }));--%>
<%--                }--%>
<%--            }, 5000); // 5 giây--%>
<%--        };--%>

<%--        // Khi kết nối đóng--%>
<%--        socket.onclose = () => {--%>
<%--            console.log("WebSocket connection closed.");--%>
<%--        };--%>
<%--    </script>--%>
<%--    <script>--%>
<%--        let isNavigating = false;--%>

<%--        // Danh sách các URL cần bỏ qua--%>
<%--        const excludedUrls = [--%>
<%--            '/ms/msbook', // Đường dẫn nội bộ--%>
<%--            '/ms/mscategory',--%>
<%--            '/ms/msdashboard',--%>
<%--            '/ms/msorder',--%>
<%--            '/ms/mscustomer',--%>
<%--            '/ms/mscampaign',--%>
<%--            '/ms/msreview',--%>
<%--            '/ms/msauthor',--%>
<%--            '/ms/mspublisher',--%>
<%--            '/ms/ms_staff'--%>
<%--        ];--%>

<%--        // Lắng nghe sự kiện chuyển hướng nội bộ--%>
<%--        document.addEventListener('click', (event) => {--%>
<%--            const target = event.target.closest('a'); // Phát hiện thẻ <a> gần nhất--%>
<%--            if (target && target.href) {--%>
<%--                const targetUrl = new URL(target.href, window.location.origin);--%>
<%--                const relativePath = targetUrl.pathname; // Lấy đường dẫn tương đối--%>

<%--                // Kiểm tra nếu URL thuộc danh sách được loại trừ--%>
<%--                if (excludedUrls.includes(relativePath) || excludedUrls.includes(target.href)) {--%>
<%--                    isNavigating = true; // Đánh dấu là chuyển hướng hợp lệ--%>
<%--                }--%>
<%--            }--%>
<%--        });--%>

<%--        // Xử lý sự kiện trước khi rời khỏi trang--%>
<%--        window.addEventListener('beforeunload', (event) => {--%>
<%--            if (!isNavigating) {--%>
<%--                // Chỉ xử lý khi không phải các URL được loại trừ--%>
<%--                navigator.sendBeacon('/signout/manage/admin');--%>
<%--                console.log('Logout request sent as browser is being closed or navigated away');--%>
<%--            }--%>
<%--        });--%>
<%--    </script>--%>
    <script>
        window.alert("Phiên làm việc của admin chỉ tồn tại sau 30 phút");
    </script>
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
    <%-- Chart.js --%>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <%-- jsPDF --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <%-- html2canvas --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
    <%--Custom CSS--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
</head>
<body>
    <!-- Header -->
    <jsp:include page="sidebar.jsp">
        <jsp:param name="currentTab" value="dashboard" />
    </jsp:include>
    <!-- end Header -->
     
    <main class="content" style="margin-left: 250px; padding: 20px;">
        <div class="container-fluid">
            <!-- Filters -->
            <div class="row mb-2">
                <div class="col-md-6">
                    <form id="timeRangeForm" method="post" action="${pageContext.request.contextPath}/ms/msdashboard">
                        <label for="timeRange" class="form-label fw-bold">Chọn khoảng thời gian</label>
                        <select id="timeRange" name="timeRange" class="form-select" onchange="document.getElementById('timeRangeForm').submit()">
                            <option value="all" ${selectedTimeRange == 'all' ? 'selected' : ''}>Tất cả</option>
                            <option value="7days" ${selectedTimeRange == '7days' ? 'selected' : ''}>7 ngày qua</option>
                            <option value="1month" ${selectedTimeRange == '1month' ? 'selected' : ''}>1 tháng qua</option>
                            <option value="3months" ${selectedTimeRange == '3months' ? 'selected' : ''}>3 tháng qua</option>
                            <option value="6months" ${selectedTimeRange == '6months' ? 'selected' : ''}>6 tháng qua</option>
                            <option value="1year" ${selectedTimeRange == '1year' ? 'selected' : ''}>1 năm qua</option>
                        </select>
                    </form>
                </div>
                <div class="col-md-6 d-flex justify-content-end">
                    <button id="exportReportBtn" class="btn primary-btn align-self-center">Xuất báo cáo (PDF)</button>
                </div>
            </div>
            <div class="row">
                <!-- Static Displays -->
                <div class="col-md-3 mb-4">
                    <div class="card bg-light mb-4">
                        <div class="card-body">
                            <h5 class="card-title fw-bold" style="width: 100%;">
                                <i class="fa-solid fa-dollar-sign me-2"></i>Doanh thu</i>
                            </h5>
                            <p class="card-text fw-semibold text-info fs-5">
                                <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol=""/> đ
                            </p>
                        </div>
                    </div>
                    <div class="card bg-light mb-4">
                        <div class="card-body">
                            <h5 class="card-title fw-bold"><i class="fa-solid fa-chart-line me-2"></i>Lợi nhuận</h5>
                            <p class="card-text fw-semibold text-success fs-5">
                                <fmt:formatNumber value="${totalProfit}" type="currency" currencySymbol=""/> đ
                            </p>
                        </div>
                    </div>
                    <div class="card bg-light mb-4">
                        <div class="card-body">
                            <h5 class="card-title fw-bold"><i class="fa-solid fa-users me-2"></i>Số khách hàng</h5>
                            <p class="card-text fw-semibold fs-5">
                                <fmt:formatNumber value="${numberOfCustomers}" type="number"/>
                            </p>
                        </div>
                    </div>
                    <div class="card bg-light">
                        <div class="card-body">
                            <h5 class="card-title fw-bold"><i class="fa-solid fa-book me-2"></i>Tổng số đầu sách</h5>
                            <p class="card-text fw-semibold fs-5">
                                <fmt:formatNumber value="${numberOfBooks}" type="number"/>
                            </p>
                        </div>
                    </div>
                </div>
                <!-- Time Range Filter and Revenue by Time Range -->
                <div class="col-md-9 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="card-title fw-bold" style="width: 100%;">
                                    Doanh thu</i>
                                </h5>
                            </div>
                            <canvas id="timeRangeRevenueChart" style="max-height: 390px;"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <!-- Orders -->
                <div class="col-md-4 mb-4">
                    <div class="card bg-light">
                        <div class="card-body">
                            <h5 class="card-title fw-bold"><i class="fa-solid fa-box me-2"></i>
                                Đơn hàng (Tổng: <fmt:formatNumber value="${numberOfOrders}" type="number"/>)
                            </h5>
                            <table class="table table-striped align-middle" style="height: 300px;">
                                <tbody>
                                    <tr>
                                        <td class="fw-semibold"><i class="fa-solid fa-spinner me-2"></i>Đang xử lý:</td>
                                        <td><fmt:formatNumber value="${numberOfProcessingOrders}" type="number"/></td>
                                    </tr>
                                    <tr>
                                        <td class="fw-semibold"><i class="fa-solid fa-box-open me-2"></i>Đang đóng gói:</td>
                                        <td><fmt:formatNumber value="${numberOfPackingOrders}" type="number"/></td>
                                    </tr>
                                    <tr>
                                        <td class="fw-semibold"><i class="fa-solid fa-truck me-2"></i>Đang vận chuyển:</td>
                                        <td><fmt:formatNumber value="${numberOfDeliveringOrders}" type="number"/></td>
                                    </tr>
                                    <tr>
                                        <td class="fw-semibold"><i class="fa-solid fa-check me-2"></i>Đã vận chuyển:</td>
                                        <td><fmt:formatNumber value="${numberOfDeliveredOrders}" type="number"/></td>
                                    </tr>
                                    <tr>
                                        <td class="fw-semibold"><i class="fa-solid fa-check-double me-2"></i>Thành công:</td>
                                        <td><fmt:formatNumber value="${numberOfCompletedOrders}" type="number"/></td>
                                    </tr>
                                    <tr>
                                        <td class="fw-semibold"><i class="fa-solid fa-times me-2"></i>Đã hủy:</td>
                                        <td><fmt:formatNumber value="${numberOfCancelledOrders}" type="number"/></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- Revenue by Book Category -->
                <div class="col-md-8 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title fw-bold pb-3">Doanh thu theo thể loại sách</h5>
                            <canvas id="categoryRevenueChart" style="max-height: 300px;"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <!-- Top 5 Best Selling Books -->
                <div class="col-md-6 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">
                                Top 5 sách bán chạy</i>
                            </h5>
                            <ul class="list-group">
                                <c:forEach var="entry" items="${topFiveBestSellingBooks}">
                                    <li class="list-group-item">
                                        ${entry.key} - ${entry.value} lượt mua
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- Favorite Authors -->
                <div class="col-md-6 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">
                                Tác giả được yêu thích nhất</i>
                            </h5>
                            <ul class="list-group">
                                <c:forEach var="entry" items="${topFiveFavoriteAuthors}">
                                    <li class="list-group-item">
                                        ${entry.key} - ${entry.value} lượt mua
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <!-- Campaign Revenue -->
                <div class="col-md-12 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title fw-bold">Doanh thu của các chiến dịch đang chạy</h5>
                            <canvas id="campaignRevenueChart" style="max-height: 300px;"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <script>
        const revenueByCategory = {
            labels: [],
            data: []
        };
        let otherRevenue = 0;
        let count = 0;
        <c:forEach var="entry" items="${revenueByCategory}">
            if (count < 5) {
                revenueByCategory.labels.push("${entry.key}");
                revenueByCategory.data.push(${entry.value});
            } else {
                otherRevenue += ${entry.value};
            }
            count++;
        </c:forEach>
        if (otherRevenue > 0) {
            revenueByCategory.labels.push("Danh mục khác");
            revenueByCategory.data.push(otherRevenue);
        }

        const revenueByTimeRange = {
            labels: [
                <c:forEach var="entry" items="${revenueByTimeRange}">
                    "${entry.key}",
                </c:forEach>
            ],
            data: [
                <c:forEach var="entry" items="${revenueByTimeRange}">
                    ${entry.value},
                </c:forEach>
            ]
        };

        const revenueByCampaign = {
            labels: [
                <c:forEach var="entry" items="${revenueByCampaign}">
                    "${entry.key}",
                </c:forEach>
            ],
            data: [
                <c:forEach var="entry" items="${revenueByCampaign}">
                    ${entry.value},
                </c:forEach>
            ]
        };

        document.getElementById('exportReportBtn').addEventListener('click', function() {
            exportReport();
        });
    </script>
    <script src="${pageContext.request.contextPath}/assets/javascript/management-system/ms-dashboard.js"></script>
</body>
</html>
