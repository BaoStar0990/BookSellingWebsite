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
            <div class="d-flex justify-content-between mb-3">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/staff/stafforder">
                    <i class="fas fa-arrow-left"></i>
                    Quay lại
                </a>
                <div class="d-flex justify-content-center">
                    <button class="btn btn-info me-2" onclick="printBill()">
                        <i class="fas fa-print"></i>
                        In hóa đơn
                    </button>
                    <button class="btn btn-info me-2" onclick="downloadBill()">
                        <i class="fas fa-download"></i>
                        Tải hóa đơn
                    </button>
                    <!-- <button class="btn btn-info me-2">
                        <i class="fas fa-envelope"></i>
                        Gửi hóa đơn qua email
                    </button> -->
                    
                </div>
                <div class="d-flex justify-content-end">
                    <c:if test="${bill.getStatusOrder() == 'Processing'}">
                        <form id="checkSLForm" action="${pageContext.request.contextPath}/staff/stafforder" method="post" class="mb-0">
                            <input type="hidden" name="action" value="checkSoLuong">
                            <input type="hidden" name="billId" value="${bill.getId()}">
                            <input type="hidden" name="redirectUrl" value="${pageContext.request.requestURL}">
                                <button type="submit" class="btn btn-primary me-2" id="checkSLButton"
                                        <i class="fas fa-box"></i>
                                        Kiểm tra số lượng
                                </button> 
                        </form>
                    </c:if> 
                    <form id="statusForm" action="${pageContext.request.contextPath}/staff/stafforder" method="post" class="mb-0">
                        <input type="hidden" name="action" value="updateStatus">
                        <input type="hidden" name="billId" value="${bill.getId()}">
                        <input type="hidden" name="redirectUrl" value="${pageContext.request.requestURL}">
                        <button type="button" class="btn btn-primary me-2" id="statusButton"
                            <c:if test="${bill.getStatusOrder() == 'Completed'}">style="display:none;" disabled</c:if>
                            <c:choose>
                                <c:when test="${bill.getStatusOrder() == 'Processing'}">
                                    <i class="fas fa-box"></i>
                                    Đóng hàng
                                </c:when>
                                <c:when test="${bill.getStatusOrder() == 'Packing'}">
                                    <i class="fas fa-truck"></i>
                                    Giao hàng
                                </c:when>
                                <c:when test="${bill.getStatusOrder() == 'Delivering'}">
                                    <i class="fas fa-check"></i>
                                    Đã giao hàng
                                </c:when>
                                <c:when test="${bill.getStatusOrder() == 'Delivered'}">
                                    <i class="fas fa-check"></i>
                                    Hoàn thành
                                </c:when>
                                <c:when test="${bill.getStatusOrder() == 'Cancelled'}">
                                    <i class="fas fa-box"></i>
                                    Xử lý đơn hàng
                                </c:when>
                            </c:choose>
                        </button>           
                    </form>
                    <c:if test="${(bill.getStatusOrder() == 'Processing' || bill.getStatusOrder() == 'Packing' || bill.getStatusOrder() == 'Delivering') && bill.getStatusPayment() == 'Unpaid'}">
                        <form id="cancelForm" action="${pageContext.request.contextPath}/staff/stafforder" method="post" class="mb-0">
                            <input type="hidden" name="action" value="cancelOrder">
                            <input type="hidden" name="billId" value="${bill.getId()}">
                            <input type="hidden" name="redirectUrl" value="${pageContext.request.requestURL}">
<%--                            <button type="button" class="btn btn-danger me-2" id="cancelButton">--%>
<%--                                <i class="fas fa-times"></i>--%>
<%--                                Hủy đơn hàng--%>
<%--                            </button>--%>
                        </form>
                    </c:if>
                    <c:if test="${bill.getStatusOrder() == 'Cancelled'}">
                        <form action="${pageContext.request.contextPath}/staff/stafforder" method="post" class="mb-0" onsubmit="return confirm('Bạn có chắc chắn muốn xóa đơn hàng này không?');">
                            <input type="hidden" name="action" value="deleteOrder">
                            <input type="hidden" name="billId" value="${bill.getId()}">
                            <button type="submit" class="btn btn-danger">
                                <i class="fas fa-trash"></i>
                                Xóa đơn hàng
                            </button>
                        </form>
                    </c:if>
                </div>
            </div>
            <hr>
            <div class="row">
                <!-- hiện thông báo lỗi -->
                <c:choose>
                    <c:when test="${requestScope.errorMessage == null}">
                        <div id="errorMessage" class="alert alert-danger d-none"></div>
                    </c:when>
                    <c:otherwise>
                        <div id="errorMessage" class="alert alert-danger">${requestScope.errorMessage}</div>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.successMessage == null}">
                        <div id="successMessage" class="alert alert-success d-none"></div>
                    </c:when>
                    <c:otherwise>
                        <div id="successMessage" class="alert alert-success">${requestScope.successMessage}</div>
                    </c:otherwise>
                </c:choose>
                        
                <div class="col-md-6">
                    <h5>Chi tiết đơn hàng</h5>
                    <div class="card mb-3">
                        <div class="card-body">
                            <p><strong>Mã đơn hàng:</strong> <span>${bill.getId()}</span></p>
                            <p><strong>Khách hàng:</strong> <span>${bill.getCustomer().getFullName()}</span></p>
                            <p><strong>Ngày đặt hàng:</strong> <span>${bill.getOrderDate()}</span></p>
                            <p><strong>Ngày giao hàng:</strong> <span>${bill.getDeliveryDate()}</span></p>
                            <p><strong>Người nhận hàng:</strong> <span>${bill.getRecipientName()}</span></p>
                            <p><strong>Sđt người nhận hàng:</strong> <span>${bill.getRecipientPhone()}</span></p>
                            <p><strong>Địa chỉ giao hàng:</strong> <span>${bill.getRecipientAddress()}</span></p>
                            <p><strong>Phương thức thanh toán:</strong> <span>${bill.getPaymentMethod()}</span></p>
                            <p><strong>Tình trạng đơn hàng:</strong>
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
                                </span></p>
                            <p><strong>Tình trạng thanh toán:</strong> <span>${bill.getStatusPayment()}</span></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <h5>Sách trong đơn hàng</h5>
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Action</th>
                                    <th>Tên sách</th>
                                    <th>Số lượng</th>
                                    <th>Đơn giá</th>
                                    <th>Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="detail" items="${bill.getOrderDetails()}">
                                    <tr>
                                        <c:if test="${bill.getStatusOrder() == 'Processing'}">
                                            <td>
                                                <div class ="row">
                                                    <div class ="col-6">
                                                        <form action="${pageContext.request.contextPath}/staff/stafforder" method="post" class="mb-0" onsubmit="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này không?');">
                                                           <input type="hidden" name="action" value="deleteOrderDetail">
                                                           <input type="hidden" name="orderDetailID" value="${detail.getId()}">
                                                           <input type="hidden" name="billId" value="${bill.getId()}">
                                                           <button type="submit" class="btn btn-danger">
                                                               <i class="fas fa-trash"></i>
                                                           </button>
                                                       </form>
                                                    </div>
                                                   <div class ="col-6">
                                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editOrderModal"
                                                                onclick="openEditModal('${bill.getId()}', '${detail.getId()}', '${detail.getBook().getTitle()}', '${detail.getQuantity()}')">
                                                            <i class="fas fa-edit"></i>
                                                        </button>
                                                   </div>
                                                </div>
                                               
                                            </td>
                                        </c:if>
                                       
                                        <td>${detail.getBook().getTitle()}</td>
                                        <td>${detail.getQuantity()}</td>
                                        <td>${detail.getUnitPrice()}</td>
                                        <td>${detail.getTotalPrice()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <hr>
            <p><strong>Tạm tính:</strong> ${bill.getSubtotal()}</p>
            <p><strong>Thuế GTGT:</strong> ${bill.getVAT() * 100}%</p>
            <p><strong>Phí vận chuyển:</strong> ${bill.getShippingFee()}</p>
            <p class="fs-5"><strong class="text-danger fw-bold ">Tổng số tiền:</strong> <span class="text-danger fw-bold">${bill.getGrandTotal()}</span></p>
            <hr>
            <h4>Điều khoản & Điều kiện</h4>
            <a href="https://www.naoki.com/terms_and_conditions">https://www.naoki.com/terms_and_conditions</a>
        </div>
    </main>

    <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmModalLabel">Xác nhận hành động</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p id="confirmMessage"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="button" class="btn btn-primary" id="confirmButton">Xác nhận</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade" id="editOrderModal" tabindex="-1" aria-labelledby="editOrderModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/staff/stafforder" method="post" class="mb-0" id = "editOrderDetailForm">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editOrderModalLabel">Cập nhật số lượng sản phẩm</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="action" value="editOrderDetail">
                        <input type="hidden" name="billIdEdit" id="billIdEdit" value="">
                        <input type ="hidden" name="orderDetailID" id="orderDetailID" class="orderDetailID" value ="">
                        <div class="row">
                            <label for="bookName" class="form-label">Tên sách:</label>
                            <input type="text" class="form-control" id="bookName" name="bookName" required disabled/>
                        </div>
                        <div class="row">
                            <label for="quantity" class="form-label">Số Lượng:</label>
                            <input type="number" class="form-control" id="quantity" name="quantity" required min="1"/>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-primary" id="confirmButton">Xác nhận</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/dompurify/2.3.4/purify.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/javascript/management-system/ms-order.js"></script>
    <script>
        function getBillContent() {
            return `
                <html>
                <head>
                    <title>Hóa đơn</title>
                    <style>
                        .container { width: 100%; padding: 20px; }
                        .text-danger { color: red; }
                    </style>
                </head>
                <body>
                    <div>
                        <img src="${pageContext.request.contextPath}/assets/images/logos/logo-1.png" alt="Naoki Logo" style="width: 100px; margin-bottom: 16px;">
                        <p>Địa chỉ công ty: Số 1, Võ Văn Ngân, Quận Thủ Đức, Tp. Hồ Chí Minh</p>
                        <p>Số điện thoại: 0912345678</p>
                        <p>Email: support@naoki.com</p>
                        <p>Mã số thuế: 12345</p>
                        <hr>
                        <h3>Hóa đơn</h3>
                        <p><strong>Số hóa đơn:</strong> ${bill.getId()}</p>
                        <p><strong>Ngày đặt hàng:</strong> ${bill.getOrderDate()}</p>
                        <p><strong>Ngày giao hàng:</strong> ${bill.getDeliveryDate()}</p>
                        <p><strong>Khách hàng:</strong> ${bill.getCustomer().getFullName()}</p>
                        <p><strong>Người nhận hàng:</strong> <span>${bill.getRecipientName()}</span></p>
                        <p><strong>Sđt người nhận hàng:</strong> <span>${bill.getRecipientPhone()}</span></p>
                        <p><strong>Địa chỉ giao hàng:</strong> <span>${bill.getRecipientAddress()}</span></p>
                        <p><strong>Phương thức thanh toán:</strong> ${bill.getPaymentMethod()}</p>
                        <p><strong>Tình trạng thanh toán:</strong> ${bill.getStatusPayment()}</p>
                        <hr>
                        <h4>Sách trong đơn hàng</h4>
                        <table border="1" width="100%">
                            <thead>
                                <tr>
                                    <th>Tên sách</th>
                                    <th>Số lượng</th>
                                    <th>Đơn giá</th>
                                    <th>Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="detail" items="${bill.getOrderDetails()}">
                                    <tr>
                                        <td>${detail.getBook().getTitle()}</td>
                                        <td>${detail.getQuantity()}</td>
                                        <td>${detail.getUnitPrice()}</td>
                                        <td>${detail.getTotalPrice()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <hr>
                        <p><strong>Tạm tính:</strong> ${bill.getSubtotal()}</p>
                        <p><strong>Thuế GTGT:</strong> ${bill.getVAT() * 100}%</p>
                        <p><strong>Phí vận chuyển:</strong> ${bill.getShippingFee()}</p>
                        <p class="fs-5"><strong class="text-danger fw-bold">Tổng số tiền:</strong> <span class="text-danger fw-bold">${bill.getGrandTotal()}</span></p>
                        <hr>
                        <h4>Điều khoản & Điều kiện</h4>
                        <a href="https://www.naoki.com/terms_and_conditions">https://www.naoki.com/terms_and_conditions</a>
                    </div>
                </body>
                </html>
            `;
        }

        function printBill() {
            const printContent = getBillContent();
            const originalContent = document.body.innerHTML;

            document.body.innerHTML = printContent;
            window.print();
            document.body.innerHTML = originalContent;
        }

        function downloadBill() {
            const billContent = getBillContent();
            const opt = {
                margin: 1,
                filename: `bill_${bill.getId()}.pdf`,
                image: { type: 'jpeg', quality: 0.98 },
                html2canvas: { scale: 2 },
                jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
            };
            html2pdf().set(opt).from(billContent).save();
        }
        function openEditModal(billID, orderDetailId, bookName, quantity){ 
            document.getElementById("billIdEdit").value = billID; 
            document.getElementById("orderDetailID").value = orderDetailId;
            document.getElementById("bookName").value = bookName; 
            document.getElementById("quantity").value = quantity; 
        }
            
    </script>
</body>
</html>