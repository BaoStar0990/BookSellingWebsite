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
  <link rel="stylesheet" href="../assets/styles/main.css">
</head>
<body>
  <!-- Header -->
  <jsp:include page="sidebar.jsp">
    <jsp:param name="currentTab" value="category" />
  </jsp:include>
  <!-- end Header -->

  <main class="content" style="margin-left: 250px; padding: 20px;">
    <div class="container fw-medium">
      <div class="row mb-3">
        <div class="col-md-4 d-flex flex-colum">
          <input type="text" class="form-control fw-medium" id="searchBox" placeholder="Search categories...">
          <button class="primary-btn  ms-2 ">Search</button>
        </div>
        <div class="col-md-8 d-flex justify-content-end">
          <button class="primary-btn" data-bs-toggle="modal" data-bs-target="#addCategoryModal">Add Category</button>
        </div>
      </div>
      <table class="table table-bordered">
        <thead>
        <tr>
          <th>Actions</th>
          <th>ID</th>
          <th>Name</th>
          <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
          <tr class="fw-medium">
            <td>
              <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editCategoryModal" onclick="editCategory(${category.id}, '${category.name}', '${category.description}')">
                <i class="fas fa-edit"></i>
              </button>
              <button class="btn btn-danger btn-sm" onclick="deleteCategory(${category.id})">
                <i class="fas fa-trash"></i>
              </button>
            </td>
            <td>${category.id}</td>
            <td>${category.name}</td>
            <td>${category.description}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </main>

  <!-- Add Category Modal -->
  <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="MSCategoryController?action=add" method="post">
          <div class="modal-header">
            <h5 class="modal-title" id="addCategoryModalLabel">Add Category</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="categoryName" class="form-label">Name</label>
              <input type="text" class="form-control" id="categoryName" name="name" required>
            </div>
            <div class="mb-3">
              <label for="categoryDescription" class="form-label">Description</label>
              <textarea class="form-control" id="categoryDescription" name="description" required></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- Edit Category Modal -->
  <div class="modal fade fw-medium" id="editCategoryModal" tabindex="-1" aria-labelledby="editCategoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="MSCategoryController?action=edit" method="post">
          <div class="modal-header">
            <h5 class="modal-title" id="editCategoryModalLabel">Edit Category</h5>
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
              <textarea class="form-control" id="editCategoryDescription" name="description" required></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save</button>
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
      if (confirm('Are you sure you want to delete this category?')) {
        window.location.href = 'MSCategoryController?action=delete&id=' + id;
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
