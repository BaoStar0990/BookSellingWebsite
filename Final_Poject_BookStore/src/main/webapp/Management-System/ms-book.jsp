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
    <jsp:param name="currentTab" value="book" />
  </jsp:include>
  <!-- end Header -->

  <main class="content" style="margin-left: 250px; padding: 20px;">
    <div class="container fw-medium">
      <div class="row mb-3">
        <div class="col-md-4 d-flex flex-colum">
          <input type="text" class="form-control fw-medium" id="searchBox" placeholder="Search books...">
        </div>
        <div class="col-md-8 d-flex justify-content-end">
            <button class="primary-btn" data-bs-toggle="modal" data-bs-target="#addCategoryModal">Thêm Sách</button>
        </div>
      </div>
      <div class="table-responsive border border-2 p-2" style="max-height: 75%; overflow: auto;">
        <table class="table table-bordered" style="min-width: 1500px;">
          <thead class="table-danger">
          <tr>
            <th>Action</th>
            <th>ID</th>
            <th>Tên sách</th>
            <th>Tác giả</th>
            <th>Loại sách</th>
            <th>Giá vốn(vnđ)</th>
            <th>Giá bán(vnđ)</th>
            <th>Tồn kho</th>
            <th>Hình ảnh</th>
            <th>Mô tả</th>
            <th>Nhà xuất bản</th>
            <th>Năm xuất bản</th>
            <th>Ngôn ngữ</th>
            <th>Số lượt Review</th>
            
            <th>Chiến dịch giảm giá</th>
            <th>ISBN</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="book" items="${books}">
            <tr class="fw-medium">
                <td class="d-flex">
                    <button class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" >
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-danger btn-sm">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
              <td>${fn:escapeXml(book.getId())}</td>
              <td>${fn:escapeXml(book.getTitle())}</td>
              <td>
                <c:forEach var="author" items="${book.getAuthors()}" varStatus="status">
                  ${fn:escapeXml(author.getName())}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
              </td>
              <td>
                <c:forEach var="category" items="${book.getCategories()}" varStatus="status">
                  ${fn:escapeXml(category.getName())}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
              </td>
              <td>${fn:escapeXml(book.getCostPrice())}</td>
              <td>${fn:escapeXml(book.getSellingPrice())}</td>
              <td>${fn:escapeXml(book.getStocks())}</td>
              <td><img src="${fn:escapeXml(book.getUrlImage())}" alt="Book Image" style="width: 100px;"></td>
              <td>${fn:escapeXml(book.getDescription())}</td>
              <td>${fn:escapeXml(book.getPublisher().getName())}</td>
              <td>${fn:escapeXml(book.getPublishYear())}</td>
              <td>${fn:escapeXml(book.getLanguage())}</td>
              <td>${fn:escapeXml(book.getReviews().size())}</td>
              <td>${fn:escapeXml(book.getDiscountCampaign() != null ? book.getDiscountCampaign().getCampaignName(): "N/A")}</td>
              <td>${fn:escapeXml(book.getIsbn())}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </main>

  <script>
    document.getElementById('searchBox').addEventListener('input', function() {
      let filter = this.value.toUpperCase();
      let rows = document.querySelector("tbody").rows;
      for (let i = 0; i < rows.length; i++) {
        let firstCol = rows[i].cells[1].textContent.toUpperCase();
        let secondCol = rows[i].cells[2].textContent.toUpperCase();
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
