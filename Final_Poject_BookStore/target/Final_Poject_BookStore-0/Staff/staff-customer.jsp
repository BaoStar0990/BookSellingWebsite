<%--
  Created by IntelliJ IDEA.
  User: vuxua
  Date: 07/11/2024
  Time: 9:57 CH
  To change this template use File | Settings | File Templates.git pull origin main
--%>
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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/main.css">
</head>
<body>
<!-- Header -->
<jsp:include page="sidebar.jsp">
  <jsp:param name="currentTab" value="customer" />
</jsp:include>
<!-- end Header -->

<main class="content" style="margin-left: 250px; padding: 20px;">
  <div class="container fw-medium">
    <div class="row mb-3">
      <div class="col-md-4 d-flex flex-colum">
        <input type="text" class="form-control fw-medium" id="searchBox" placeholder="Search customer...">
      </div>
      <div class="col-md-8 d-flex justify-content-end">
        <button class="primary-btn" data-bs-toggle="modal" data-bs-target="#addCategoryModal">Add Customer</button>
      </div>
    </div>
    <div class="table-responsive border border-2 p-2" style="max-height: 75%; overflow: auto;">
      <table class="table table-bordered" style="min-width: 1000px;">
        <thead>
        <tr>
          <th></th>
          <th>ID</th>
          <th>Tài khoản</th>
          <th>Họ tên</th>
          <th>Tuổi</th>
          <th>SĐT</th>
          <th>Email</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="customer" items="${sessionScope.customers}">
          <tr class="fw-medium">
            <td class="d-flex">
              <button class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editCategoryModal" onclick="editCategory(${customer.getId()}, '${customer.getUsername()}', '${customer.getPassword()}', '${customer.getFullName()}', '${customer.getAge()}', '${customer.getNumberPhone()}', '${customer.getEmail()}')">
                <i class="fas fa-edit"></i>
              </button>
            </td>
            <td>${fn:escapeXml(customer.getId())}</td>
            <td>${fn:escapeXml(customer.getUsername())}</td>
            <td>${customer.getFullName()}</td>
            <td>${customer.getAge()}</td>
            <td>${customer.getNumberPhone()}</td>
            <td>${customer.getEmail()}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</main>

<!-- Add Category Modal -->
<div class="modal fade fw-medium" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="ms_customer" method="post">
        <input type="hidden" name="csrf" value="${_csrf.token}"/>
        <input type="hidden" name="action" value="add"/>
        <div class="modal-header">
          <h5 class="modal-title fw-semibold" id="addCategoryModalLabel">Add Customer</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="customerAccount" class="form-label">Tài khoản</label>
            <input type="text" class="form-control" id="customerAccount" name="account" required>
          </div>
          <div class="mb-3">
            <label for="customerPassword" class="form-label">Mật khẩu</label>
            <input type="text" class="form-control" id="customerPassword" name="password" required>
          </div>
          <div class="mb-3">
            <label for="customerFullName" class="form-label">Họ tên</label>
            <input type="text" class="form-control" id="customerFullName" name="fullname" required>
          </div>
          <div class="mb-3">
            <label for="customerAge" class="form-label">Tuổi</label>
            <input type="text" class="form-control" id="customerAge" name="age" required>
          </div>
          <div class="mb-3">
            <label for="customerPhone" class="form-label">SĐT</label>
            <input type="tel" class="form-control" id="customerPhone" name="phone" required>
          </div>
          <div class="mb-3">
            <label for="customerEmail" class="form-label">Email</label>
            <input type="email" class="form-control" id="customerEmail" name="email" required>
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

<!-- Edit Category Modal -->
<div class="modal fade fw-medium" id="editCategoryModal" tabindex="-1" aria-labelledby="editCategoryModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="ms_customer" method="post">
        <%-- Validate CSRF token --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="action" value="edit"/>
        <div class="modal-header">
          <h5 class="modal-title fw-semibold" id="editCategoryModalLabel">Edit Customer</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" id="editCustomerId" name="id"/>
          <div class="mb-3">
            <label for="editCustomerUsername" class="form-label">Tài khoản</label>
            <input type="text" class="form-control" id="editCustomerUsername" name="username" required>
          </div>
          <div class="mb-3">
            <label for="editCustomerPassword" class="form-label">Mật khẩu</label>
            <input type="text" class="form-control" id="editCustomerPassword" name="password" required rows="5"/>
          </div>
          <div class="mb-3">
            <label for="editCustomerFullName" class="form-label">Họ tên</label>
            <input type="text" class="form-control" id="editCustomerFullName" name="fullname" required rows="5"/>
          </div>
          <div class="mb-3">
            <label for="editCustomerAge" class="form-label">Tuổi</label>
            <input type="text" class="form-control" id="editCustomerAge" name="age" required rows="5"/>
          </div>
          <div class="mb-3">
            <label for="editCustomerPhoneNumber" class="form-label">SĐT</label>
            <input type="tel" class="form-control" id="editCustomerPhoneNumber" name="phone" required rows="5"/>
          </div>
          <div class="mb-3">
            <label for="editCustomerEmail" class="form-label">Email</label>
            <input type="email" class="form-control" id="editCustomerEmail" name="email" required rows="5"/>
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
  function editCategory(id, account, password, fullname, age, phone, email) {
    document.getElementById('editCustomerId').value = id;
    document.getElementById('editCustomerUsername').value = account;
    document.getElementById('editCustomerPassword').value = password;
    document.getElementById('editCustomerFullName').value = fullname;
    document.getElementById('editCustomerAge').value = age;
    document.getElementById('editCustomerPhoneNumber').value = phone;
    document.getElementById('editCustomerEmail').value = email;
  }

  function deleteCategory() {
    console.log(document.getElementById("deleteForm"));
    if (confirm('Are you sure you want to delete this category?')) {
      document.getElementById("deleteForm").submit();
    }
  }

  document.getElementById('searchBox').addEventListener('input', function() {
    let filter = this.value.toUpperCase();
    let rows = document.querySelector("tbody").rows;
    for (let i = 0; i < rows.length; i++) {
      let firstCol = rows[i].cells[2].textContent.toUpperCase();
      let secondCol = rows[i].cells[3].textContent.toUpperCase();
      if (firstCol.indexOf(filter) > -1 || secondCol.indexOf(filter) > -1) {
        rows[i].style.display = "";
      } else {
        rows[i].style.display = "none";
      }
    }
  });
</script>
</body>
</html>
