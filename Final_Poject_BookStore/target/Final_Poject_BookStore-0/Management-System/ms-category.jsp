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
<%--  <script>--%>
<%--    let isNavigating = false;--%>

<%--    // Danh sách các URL cần bỏ qua--%>
<%--    const excludedUrls = [--%>
<%--      '/ms/msbook', // Đường dẫn nội bộ--%>
<%--      '/ms/mscategory',--%>
<%--      '/ms/msdashboard',--%>
<%--      '/ms/msorder',--%>
<%--      '/ms/mscustomer',--%>
<%--      '/ms/mscampaign',--%>
<%--      '/ms/msreview',--%>
<%--      '/ms/msauthor',--%>
<%--      '/ms/mspublisher',--%>
<%--      '/ms/ms_staff'--%>
<%--    ];--%>

<%--    // Lắng nghe sự kiện chuyển hướng nội bộ--%>
<%--    document.addEventListener('click', (event) => {--%>
<%--      const target = event.target.closest('a'); // Phát hiện thẻ <a> gần nhất--%>
<%--      if (target && target.href) {--%>
<%--        const targetUrl = new URL(target.href, window.location.origin);--%>
<%--        const relativePath = targetUrl.pathname; // Lấy đường dẫn tương đối--%>

<%--        // Kiểm tra nếu URL thuộc danh sách được loại trừ--%>
<%--        if (excludedUrls.includes(relativePath) || excludedUrls.includes(target.href)) {--%>
<%--          isNavigating = true; // Đánh dấu là chuyển hướng hợp lệ--%>
<%--        }--%>
<%--      }--%>
<%--    });--%>

<%--    // Xử lý sự kiện trước khi rời khỏi trang--%>
<%--    window.addEventListener('beforeunload', (event) => {--%>
<%--      if (!isNavigating) {--%>
<%--        // Chỉ xử lý khi không phải các URL được loại trừ--%>
<%--        navigator.sendBeacon('/signout/manage/admin');--%>
<%--        console.log('Logout request sent as browser is being closed or navigated away');--%>
<%--      }--%>
<%--    });--%>
<%--  </script>--%>
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
    <jsp:param name="currentTab" value="category"/>
  </jsp:include>
  <!-- end Header -->

  <main class="content" style="margin-left: 250px; padding: 20px;">
    <div class="container fw-medium">
      <div class="row mb-3">
        <div class="col-md-4 d-flex flex-column">
          <input type="text" class="form-control fw-medium" id="searchBox" placeholder="Search categories...">
        </div>
        <div class="col-md-8 d-flex justify-content-end">
          <button class="primary-btn" data-bs-toggle="modal" data-bs-target="#addCategoryModal">Add Category</button>
        </div>
      </div>
      <div class="table-responsive border border-2 p-2" style="max-height: 75%; overflow: auto;">
        <table class="table table-bordered" style="min-width: 1000px;">
          <thead>
          <tr>
            <th></th>
            <th>ID</th>
            <th>Tên</th>
            <th>Mô tả</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="category" items="${categories}">
            <tr class="fw-medium">
              <td class="d-flex">
                <button class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editCategoryModal" onclick="editCategory(${category.getId()}, '${category.getName()}', '${category.getDescription()}')">
                  <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-danger btn-sm" onclick="deleteCategory(${category.getId()})">
                  <i class="fas fa-trash"></i>
                </button>
              </td>
              <td>${fn:escapeXml(category.getId())}</td>
              <td>${fn:escapeXml(category.getName())}</td>
              <td>${fn:escapeXml(category.getDescription())}</td>
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
        <form action="/ms/mscategory?action=add" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="modal-header">
            <h5 class="modal-title fw-semibold" id="addCategoryModalLabel">Add Category</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="categoryName" class="form-label">Name</label>
              <input type="text" class="form-control" id="categoryName" name="name" required>
            </div>
            <div class="mb-3">
              <label for="categoryDescription" class="form-label">Description</label>
              <textarea class="form-control" id="categoryDescription" name="description" required rows="5"></textarea>
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
        <form action="/ms/mscategory?action=edit" method="post">
          <%-- Validate CSRF token --%>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="modal-header">
            <h5 class="modal-title fw-semibold" id="editCategoryModalLabel">Edit Category</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <input type="hidden" id="editCategoryId" name="id">
            <div class="mb-3">
              <label for="editCategoryName" class="form-label">Name</label>
              <input type="text" class="form-control" id="editCategoryName" name="name" required>
            </div>
            <div class="mb-3">
              <label for="editCategoryDescription" class="form-label">Description</label>
              <textarea class="form-control" id="editCategoryDescription" name="description" required rows="5"></textarea>
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
    function editCategory(id, name, description) {
      document.getElementById('editCategoryId').value = id;
      document.getElementById('editCategoryName').value = name;
      document.getElementById('editCategoryDescription').value = description;
    }

    function deleteCategory(id) {
      if (confirm('Bạn muốn xóa danh mục này?')) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = '/ms/mscategory?action=delete';
        
        const inputId = document.createElement('input');
        inputId.type = 'hidden';
        inputId.name = 'id';
        inputId.value = id;
        form.appendChild(inputId);

        document.body.appendChild(form);
        form.submit();
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
