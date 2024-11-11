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
            <button class="primary-btn" data-bs-toggle="modal" data-bs-target="#addEditBookModal">Thêm Sách</button>
        </div>
      </div>
      <div class="table-responsive border border-2 p-2" style="max-height: 75%; overflow: auto;">
        <table class="table table-bordered" style="min-width: 1500px;">
          <thead>
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
                    <button class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#addEditBookModal">
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

  <!-- Add/Edit Book Modal -->
  <div class="modal fade" id="addEditBookModal" tabindex="-1" aria-labelledby="addEditBookModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
                  <h5 class="modal-title" id="addEditBookModalLabel">Thêm Sách</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                  <form id="addEditBookForm">
                      <div class="mb-3">
                          <label for="bookTitle" class="form-label">Tên Sách</label>
                          <input type="text" class="form-control" id="bookTitle" name="bookTitle" required>
                      </div>
                      <div class="mb-3">
                          <label for="bookAuthors" class="form-label">Tác giả</label>
                          <input type="text" class="form-control mb-2" id="authorSearch" placeholder="Search authors...">
                          <div id="selectedAuthors" class="mb-2"></div>
                          <div id="bookAuthors" style="max-height: 150px; overflow-y: auto;">
                              <!-- Example checkboxes -->
                              <div class="form-check">
                                  <input class="form-check-input author-checkbox" type="checkbox" value="1" id="author1">
                                  <label class="form-check-label" for="author1">Author 1</label>
                              </div>
                              <div class="form-check">
                                  <input class="form-check-input author-checkbox" type="checkbox" value="2" id="author2">
                                  <label class="form-check-label" for="author2">Author 2</label>
                              </div>
                              <div class="form-check">
                                <input class="form-check-input author-checkbox" type="checkbox" value="3" id="author3">
                                <label class="form-check-label" for="author3">Author 3</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input author-checkbox" type="checkbox" value="4" id="author4">
                                <label class="form-check-label" for="author4">Author 4</label>
                            </div>
                          </div>
                      </div>
                      <div class="mb-3">
                          <label for="bookCategories" class="form-label">Loại sách</label>
                          <input type="text" class="form-control mb-2" id="categorySearch" placeholder="Search categories...">
                          <div id="selectedCategories" class="mb-2"></div>
                          <div id="bookCategories" style="max-height: 150px; overflow-y: auto;">
                              <!-- Example checkboxes -->
                              <div class="form-check">
                                  <input class="form-check-input category-checkbox" type="checkbox" value="1" id="category1">
                                  <label class="form-check-label" for="category1">Category 1</label>
                              </div>
                              <div class="form-check">
                                  <input class="form-check-input category-checkbox" type="checkbox" value="2" id="category2">
                                  <label class="form-check-label" for="category2">Category 2</label>
                              </div>
                              <div class="form-check">
                                <input class="form-check-input category-checkbox" type="checkbox" value="1" id="category3">
                                <label class="form-check-label" for="category3">Category 3</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input category-checkbox" type="checkbox" value="2" id="category4">
                                <label class="form-check-label" for="category4">Category 4</label>
                            </div>
                          </div>
                      </div>
                      <div class="mb-3">
                          <label for="costPrice" class="form-label">Giá vốn</label>
                          <input type="number" class="form-control" id="costPrice" name="costPrice" required>
                      </div>
                      <div class="mb-3">
                          <label for="sellingPrice" class="form-label">Giá bán</label>
                          <input type="number" class="form-control" id="sellingPrice" name="sellingPrice" required>
                      </div>
                      <div class="mb-3">
                          <label for="stocks" class="form-label">Tồn kho</label>
                          <input type="number" class="form-control" id="stocks" name="stocks" required>
                      </div>
                      <div class="mb-3">
                          <label for="isbn" class="form-label">ISBN</label>
                          <input type="text" class="form-control" id="isbn" name="isbn" required>
                      </div>
                      <div class="mb-3">
                          <label for="urlImage" class="form-label">Hình ảnh</label>
                          <input type="file" class="form-control" id="urlImage" name="urlImage" required>
                      </div>
                      <div class="mb-3">
                          <label for="description" class="form-label">Mô tả</label>
                          <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                      </div>
                      <div class="mb-3">
                          <label for="publisher" class="form-label">Nhà xuất bản</label>
                          <select class="form-select" id="publisher" name="publisher" required>
                              <!-- Options will be populated dynamically -->
                          </select>
                      </div>
                      <div class="mb-3">
                          <label for="publishYear" class="form-label">Năm xuất bản</label>
                          <input type="number" class="form-control" id="publishYear" name="publishYear" required>
                      </div>
                      <div class="mb-3">
                          <label for="discountCampaign" class="form-label">Campaign</label>
                          <select class="form-select" id="discountCampaign" name="discountCampaign" required>
                              <!-- Options will be populated dynamically -->
                          </select>
                      </div>
                      <div class="modal-footer">
                          <button type="submit" class="btn btn-primary">Lưu</button>
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                      </div>
                  </form>
              </div>
          </div>
      </div>
  </div>
  <!-- End Add/Edit Book Modal -->

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

    function createTag(value, text, containerId) {
        const container = document.getElementById(containerId);
        const tag = document.createElement('span');
        tag.className = 'badge bg-primary me-2';
        tag.textContent = text;
        tag.dataset.id = value;
        const closeButton = document.createElement('button');
        closeButton.type = 'button';
        closeButton.className = 'btn-close btn-close-white ms-2';
        closeButton.ariaLabel = 'Close';
        closeButton.addEventListener('click', function() {
            document.getElementById(value).checked = false;
            container.removeChild(tag);
        });
        tag.appendChild(closeButton);
        container.appendChild(tag);
    }

    document.querySelectorAll('.author-checkbox').forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            if (this.checked) {
                createTag(this.id, this.nextElementSibling.textContent, 'selectedAuthors');
            } else {
                const tag = document.querySelector(`#selectedAuthors span[data-id="\${this.id}"]`);
                if (tag) {
                    tag.remove();
                }
            }
        });
    });

    document.querySelectorAll('.category-checkbox').forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            if (this.checked) {
                createTag(this.id, this.nextElementSibling.textContent, 'selectedCategories');
            } else {
                const tag = document.querySelector(`#selectedCategories span[data-id="\${this.id}"]`);
                if (tag) {
                    tag.remove();
                }
            }
        });
    });

    document.getElementById('authorSearch').addEventListener('input', function() {
        let filter = this.value.toUpperCase();
        let checkboxes = document.querySelectorAll('#bookAuthors .form-check');
        checkboxes.forEach(function(checkbox) {
            let label = checkbox.querySelector('label').textContent.toUpperCase();
            if (label.indexOf(filter) > -1) {
                checkbox.style.display = '';
            } else {
                checkbox.style.display = 'none';
            }
        });
    });

    document.getElementById('categorySearch').addEventListener('input', function() {
        let filter = this.value.toUpperCase();
        let checkboxes = document.querySelectorAll('#bookCategories .form-check');
        checkboxes.forEach(function(checkbox) {
            let label = checkbox.querySelector('label').textContent.toUpperCase();
            if (label.indexOf(filter) > -1) {
                checkbox.style.display = '';
            } else {
                checkbox.style.display = 'none';
            }
        });
    });
  </script>
</body>
</html>
