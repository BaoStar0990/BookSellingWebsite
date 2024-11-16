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
    <link rel="icon" href="assets/images/logos/square-logo.png" type="image/x-icon">
    <%-- Fontawesome --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  
    <%--Custom CSS--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
</head>
<body>
  <!-- Header -->
  <jsp:include page="sidebar.jsp">
    <jsp:param name="currentTab" value="orderdetail"/>
  </jsp:include>
  <!-- end Header -->

  <main class="content" style="margin-left: 250px; padding: 20px;">
    <div class="container fw-medium">
      <div class="row mb-3">
        <div class="col-md-4 d-flex flex-colum">
          <input type="text" class="form-control fw-medium" id="searchBox" placeholder="Search bills...">
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-md-12">
          <div class="d-flex justify-content-between">
            <button style="font-size: 18px;" class="badge bg-light text-dark" onclick="filterByStatus('All')">All: ${bills.size()}</button>
            <button style="font-size: 18px;" class="badge bg-secondary" onclick="filterByStatus('Storing')">Storing: ${storingCount}</button>
            <button style="font-size: 18px;" class="badge bg-info" onclick="filterByStatus('Processing')">Processing: ${processingCount}</button>
            <button style="font-size: 18px;" class="badge bg-warning" onclick="filterByStatus('Packing')">Packing: ${packingCount}</button>
            <button style="font-size: 18px;" class="badge bg-primary" onclick="filterByStatus('Delivering')">Delivering: ${deliveringCount}</button>
            <button style="font-size: 18px;" class="badge bg-success" onclick="filterByStatus('Delivered')">Delivered: ${deliveredCount}</button>
            <button style="font-size: 18px;" class="badge bg-danger" onclick="filterByStatus('Cancelled')">Cancelled: ${cancelledCount}</button>
            <button style="font-size: 18px;" class="badge bg-success" onclick="filterByStatus('Completed')">Completed: ${completedCount}</button>
          </div>
        </div>
      </div>
      <div class="accordion" id="billAccordion">
        <c:forEach var="bill" items="${bills}">
          <div class="accordion-item" data-status="${bill.getStatusOrder()}">
            <h2 class="accordion-header" id="heading${bill.getId()}">
              <button class="accordion-button" type="button" aria-expanded="true">
                Mã Đơn Hàng: ${fn:escapeXml(bill.getId())} - Khách Hàng: ${fn:escapeXml(bill.getCustomer().getFullName())} - Tình trạng: 
                <span class=" ms-2 badge 
                  <c:choose>
                    <c:when test="${bill.getStatusOrder() == 'Storing'}">bg-secondary</c:when>
                    <c:when test="${bill.getStatusOrder() == 'Processing'}">bg-info</c:when>
                    <c:when test="${bill.getStatusOrder() == 'Packing'}">bg-warning</c:when>
                    <c:when test="${bill.getStatusOrder() == 'Delivering'}">bg-primary</c:when>
                    <c:when test="${bill.getStatusOrder() == 'Delivered'}">bg-success</c:when>
                    <c:when test="${bill.getStatusOrder() == 'Cancelled'}">bg-danger</c:when>
                    <c:when test="${bill.getStatusOrder() == 'Completed'}">bg-success</c:when>
                    <c:otherwise>bg-secondary</c:otherwise>
                  </c:choose>">
                  ${fn:escapeXml(bill.getStatusOrder())}
                </span>
              </button>
            </h2>
            <div class="accordion-body">
              <div class="table-responsive border border-2 p-2" style="max-height: 75%; overflow: auto;">
                <table class="table table-bordered" style="min-width: 1000px;">
                  <thead>
                    <tr>
                      <th>Tên sách</th>
                      <th>Số lượng</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="orderDetail" items="${bill.getOrderDetails()}">
                      <tr class="fw-medium">
                        <td>${fn:escapeXml(orderDetail.getBook().getTitle())}</td>
                        <td>${fn:escapeXml(orderDetail.getQuantity())}</td>
                        <td class="d-flex">
                          <button class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editOrderModal" onclick="editOrder(${orderDetail.getId()}, ${orderDetail.getQuantity()})">
                            <i class="fas fa-edit"></i>
                          </button>
                          <button class="btn btn-danger btn-sm" onclick="deleteOrder(${orderDetail.getId()})">
                            <i class="fas fa-trash"></i>
                          </button>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
              <div class="d-flex justify-content-end mt-2">
                <c:choose>
                  <c:when test="${bill.getStatusOrder() == 'Processing'}">
                    <button class="btn btn-primary btn-sm me-3" onclick="updateBillStatus(${bill.getId()}, 'Packing')">
                      <i class="fas fa-box"></i> Đóng hàng
                    </button>
                  </c:when>
                  <c:when test="${bill.getStatusOrder() == 'Packing'}">
                    <button class="btn btn-primary btn-sm me-2" onclick="updateBillStatus(${bill.getId()}, 'Delivering')">
                      <i class="fas fa-truck"></i> Vận chuyển
                    </button>
                  </c:when>
                  <c:otherwise>
                    <!-- <span class="badge bg-secondary">${fn:escapeXml(bill.getStatusOrder())}</span> -->
                  </c:otherwise>
                </c:choose>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </main>

  <!-- Edit Order Modal -->
  <div class="modal fade fw-medium" id="editOrderModal" tabindex="-1" aria-labelledby="editOrderModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="msorder?action=edit" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="modal-header">
            <h5 class="modal-title fw-semibold" id="editOrderModalLabel">Edit Order</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <input type="hidden" id="editOrderId" name="id">
            <div class="mb-3">
              <label for="editOrderQuantity" class="form-label">Quantity</label>
              <input type="number" class="form-control" id="editOrderQuantity" name="quantity" required>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="secondary-btn" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="primary-btn">Save</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script>
    function filterByStatus(status) {
      let rows = document.querySelectorAll(".accordion-item");
      for (let i = 0; i < rows.length; i++) {
        if (status === 'All' || rows[i].getAttribute("data-status") === status) {
          rows[i].style.display = "";
        } else {
          rows[i].style.display = "none";
        }
      }
    }

    function editOrder(id, quantity) {
      document.getElementById('editOrderId').value = id;
      document.getElementById('editOrderQuantity').value = quantity;
    }

    function deleteOrder(id) {
      if (confirm('Are you sure you want to delete this order?')) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = 'msorder?action=delete';
        
        const inputId = document.createElement('input');
        inputId.type = 'hidden';
        inputId.name = 'id';
        inputId.value = id;
        form.appendChild(inputId);

        document.body.appendChild(form);
        form.submit();
      }
    }

    function approveBill(id) {
      if (confirm('Are you sure you want to approve this bill?')) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = 'msorder?action=approve';
        
        const inputId = document.createElement('input');
        inputId.type = 'hidden';
        inputId.name = 'id';
        inputId.value = id;
        form.appendChild(inputId);

        document.body.appendChild(form);
        form.submit();
      }
    }

    function updateBillStatus(id, status) {
      if (confirm(`Are you sure you want to update the status to ${status}?`)) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = 'msorder?action=updateStatus';
        
        const inputId = document.createElement('input');
        inputId.type = 'hidden';
        inputId.name = 'id';
        inputId.value = id;
        form.appendChild(inputId);

        const inputStatus = document.createElement('input');
        inputStatus.type = 'hidden';
        inputStatus.name = 'status';
        inputStatus.value = status;
        form.appendChild(inputStatus);

        document.body.appendChild(form);
        form.submit();
      }
    }

    document.getElementById('searchBox').addEventListener('input', function() {
      let filter = this.value.toUpperCase();
      let rows = document.querySelectorAll(".accordion-item");
      for (let i = 0; i < rows.length; i++) {
        let firstCol = rows[i].querySelector(".accordion-button").textContent.toUpperCase();
        if (firstCol.indexOf(filter) > -1) {
          rows[i].style.display = "";
        } else {
          rows[i].style.display = "none";
        }
      }
    });
  </script>
</body>
</html>
