<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
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
        <table class="table table-bordered" style="min-width: 2500px;">
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
                    <button class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#addEditBookModal" 
                            data-book-id="${book.getId()}" data-book-title="${book.getTitle()}" 
                            data-book-cost-price="${book.getCostPrice()}" data-book-selling-price="${book.getSellingPrice()}"
                            data-book-stocks="${book.getStocks()}" data-book-url-image="${book.getUrlImage()}"
                            data-book-description="${book.getDescription()}" data-book-publisher="${book.getPublisher().getId()}"
                            data-book-publish-year="${book.getPublishYear()}" data-book-language="${book.getLanguage()}"
                            data-book-discount-campaign="${book.getDiscountCampaign() != null ? book.getDiscountCampaign().getCampaignId() : ''}"
                            data-book-isbn="${book.getIsbn()}"
                            data-book-authors="<c:forEach var='author' items='${book.getAuthors()}' varStatus='status'>${author.getId()}${status.last ? '' : ','}</c:forEach>"
                            data-book-categories="<c:forEach var='category' items='${book.getCategories()}' varStatus='status'>${category.getId()}${status.last ? '' : ','}</c:forEach>"
                            onclick="editBook(this)">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-danger btn-sm" data-book-id="${book.getId()}" onclick="confirmDelete(this)">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
              <td>${fn:escapeXml(book.getId())}</td>
              <td style="min-width: 200px;">${fn:escapeXml(book.getTitle())}</td>
              <td  style="min-width: 200px;">
                <ul>
                    <c:forEach var="author" items="${book.getAuthors()}">
                        <li>${fn:escapeXml(author.getName())}</li>
                    </c:forEach>
                </ul>
              </td>
              <td  style="min-width: 200px;">
                <ul>
                  <c:forEach var="category" items="${book.getCategories()}">
                    <li>${fn:escapeXml(category.getName())}</li>
                  </c:forEach>
                </ul>
              </td>
              <td>${fn:escapeXml(book.getCostPrice())}</td>
              <td>${fn:escapeXml(book.getSellingPrice())}</td>
              <td>${fn:escapeXml(book.getStocks())}</td>
              <td><img src="${fn:escapeXml(book.getUrlImage())}" alt="Book Image" style="width: 100px;"></td>
              <td>${fn:escapeXml(book.getDescription())}</td>
              <td>${fn:escapeXml(book.getPublisher().getName())}</td>
              <td style="min-width: 100px;">${fn:escapeXml(book.getPublishYear())}</td>
              <td>${fn:escapeXml(book.getLanguage())}</td>
              <td>${fn:escapeXml(book.getReviews().size())}</td>
              <td>
                <c:choose>
                  <c:when test="${book.getDiscountCampaign() != null}">
                    ${fn:escapeXml(book.getDiscountCampaign().getCampaignName())} 
                    (<fmt:formatNumber value="${book.getDiscountCampaign().getPercentDiscount()}" type="number" minFractionDigits="0" maxFractionDigits="2"/>%)
                  </c:when>
                  <c:otherwise>
                    N/A
                  </c:otherwise>
                </c:choose>
              </td>
              <td>${fn:escapeXml(book.getIsbn())}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </main>

  <!-- Add/Edit Book Modal -->
  <div class="modal fade fw-medium" id="addEditBookModal" tabindex="-1" aria-labelledby="addEditBookModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
                  <h5 class="modal-title fw-semibold" id="addEditBookModalLabel">Thêm Sách</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                  <form id="addEditBookForm" method="post" action="${pageContext.request.contextPath}/ms/msbook?action=add" enctype="multipart/form-data">
                      <input type="hidden" id="bookId" name="bookId">
                      <input type="hidden" id="selectedAuthorsInput" name="selectedAuthors">
                      <input type="hidden" id="selectedCategoriesInput" name="selectedCategories">
                      <div class="row">
                          <div class="col-md-6 border-end">
                              <div class="mb-3">
                                  <label for="bookTitle" class="form-label">Tên Sách</label>
                                  <input type="text" class="form-control" id="bookTitle" name="bookTitle" required>
                              </div>
                              <div class="mb-3">
                                  <label for="bookAuthors" class="form-label">Tác giả</label>
                                  <input type="text" class="form-control mb-2" id="authorSearch" placeholder="Search authors...">
                                  <div id="selectedAuthors"></div>
                                  <div class="border border-2 p-2 rounded-2" id="bookAuthors" style="max-height: 150px; overflow-y: auto;">
                                      <!-- Dynamically load authors -->
                                      <c:forEach var="author" items="${authors}">
                                          <div class="form-check">
                                              <input class="form-check-input author-checkbox" type="checkbox" value="${author.getId()}" id="author${author.getId()}" >
                                              <label class="form-check-label" for="author${author.getId()}">${fn:escapeXml(author.getName())}</label>
                                          </div>
                                      </c:forEach>
                                  </div>
                              </div>
                              <div class="mb-3">
                                  <label for="bookCategories" class="form-label">Loại sách</label>
                                  <input type="text" class="form-control mb-2" id="categorySearch" placeholder="Search categories...">
                                  <div id="selectedCategories"></div>
                                  <div class="border border-2 p-2 rounded-2" id="bookCategories" style="max-height: 150px; overflow-y: auto;">
                                        <!-- Dynamically load categories -->
                                        <c:forEach var="category" items="${categories}">
                                            <div class="form-check">
                                                <input class="form-check-input category-checkbox" type="checkbox" value="${category.getId()}" id="category${category.getId()}">
                                                <label class="form-check-label" for="category${category.getId()}">${fn:escapeXml(category.getName())}</label>
                                            </div>
                                        </c:forEach>
                                  </div>
                              </div>
                              <div class="mb-3">
                                  <label for="costPrice" class="form-label">Giá vốn (VND)</label>
                                  <input type="number" class="form-control" id="costPrice" name="costPrice" required min="0"
                                         oninvalid="this.setCustomValidity('Giá vốn không được âm')" oninput="this.setCustomValidity('')">
                              </div>
                              <div class="mb-3">
                                  <label for="sellingPrice" class="form-label">Giá bán (VND)</label>
                                  <input type="number" class="form-control" id="sellingPrice" name="sellingPrice" required min="0"
                                         oninvalid="this.setCustomValidity('Giá bán không được âm')" oninput="this.setCustomValidity('')">
                              </div>
                          </div>
                          <div class="col-md-6">
                              <div class="mb-3">
                                  <label for="stocks" class="form-label">Tồn kho</label>
                                  <input type="number" class="form-control" id="stocks" name="stocks" required min="0"
                                         oninvalid="this.setCustomValidity('Tồn kho không được âm')" oninput="this.setCustomValidity('')">
                              </div>
                              <div class="mb-3">
                                  <label for="isbn" class="form-label">ISBN</label>
                                  <input type="text" class="form-control" id="isbn" name="isbn" required pattern="\d{13}" title="ISBN Phải có 13 kí tự">
                              </div>
                              <div class="mb-3 d-flex align-items-center">
                                  <div class="me-3">
                                      <img id="imagePreview" src="#" alt="Image Preview" style="display: none; width: 100px; height: 100px; object-fit: cover;">
                                  </div>
                                  <div>
                                      <label for="urlImage" class="form-label">Hình ảnh</label>
                                      <input type="file" class="form-control" id="urlImage" name="urlImage" accept="image/png, image/jpeg, image/jpg, image/webp" onchange="previewImage(event)">
                                  </div>
                              </div>
                              <div class="mb-3">
                                  <label for="description" class="form-label">Mô tả</label>
                                  <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                              </div>
                                <div class="mb-3">
                                    <label for="language" class="form-label">Ngôn ngữ</label>
                                    <select class="form-select" id="language" name="language" required>
                                        <option value="Tiếng Việt">Tiếng Việt</option>
                                        <option value="Tiếng Anh">Tiếng Anh</option>
                                        <option value="Tiếng Pháp">Tiếng Pháp</option>
                                        <option value="Tiếng Đức">Tiếng Đức</option>
                                        <option value="Tiếng Trung Giản Thể">Tiếng Trung Giản Thể</option>
                                        <option value="Tiếng Nhật">Tiếng Nhật</option>
                                    </select>
                                </div>
                              <div class="mb-3">
                                  <label for="publisher" class="form-label">Nhà xuất bản</label>
                                  <select class="form-select" id="publisher" name="publisher" required
                                          oninvalid="this.setCustomValidity('Vui lòng chọn nhà xuất bản')" oninput="this.setCustomValidity('')">
                                        <!-- Dynamically load publishers-->
                                        <c:forEach var="publisher" items="${publishers}">
                                            <option value="${publisher.getId()}">${fn:escapeXml(publisher.getName())}</option>
                                        </c:forEach>
                                  </select>
                              </div>
                              <div class="mb-3">
                                  <label for="publishYear" class="form-label">Năm xuất bản</label>
                                  <input type="number" class="form-control" id="publishYear" name="publishYear" required min="1000" max="9999"
                                         oninvalid="this.setCustomValidity('Năm xuất bản không hợp lệ')" oninput="this.setCustomValidity('')">
                              </div>
                              <div class="mb-3">
                                  <label for="discountCampaign" class="form-label">Campaign</label>
                                  <select class="form-select" id="discountCampaign" name="discountCampaign">
                                      <option value="">None</option>
                                        <!-- Dynamically load campaigns -->
                                        <c:forEach var="campaign" items="${discountCampaigns}">
                                            <option value="${campaign.getCampaignId()}">${fn:escapeXml(campaign.getCampaignName())}</option>
                                        </c:forEach>
                                  </select>
                              </div>
                              
                          </div>
                      </div>
                      <div class="modal-footer">
                          <button type="button" class="btn secondary-btn" data-bs-dismiss="modal">Hủy</button>
                          <button type="submit" class="btn primary-btn">Lưu</button>
                      </div>
                  </form>
              </div>
          </div>
      </div>
  </div>
  <!-- End Add/Edit Book Modal -->

    <!-- Hidden form for delete action -->
    <form id="deleteBookForm" method="post" action="/ms/msbook?action=delete" style="display: none;">
        <input type="hidden" name="bookId" id="deleteBookId">
    </form>

    <!-- ms-book.js -->
    <script src="${pageContext.request.contextPath}/assets/javascript/management-system/ms-book.js"></script>
    <script>
        function confirmDelete(button) {
            const bookId = button.getAttribute('data-book-id');
            if (confirm('Are you sure you want to delete this book?')) {
                document.getElementById('deleteBookId').value = bookId;
                document.getElementById('deleteBookForm').submit();
            }
        }
    </script>
</body>
</html>
