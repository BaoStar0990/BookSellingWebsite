<%--&lt;%&ndash;--%>
<%--  Created by IntelliJ IDEA.--%>
<%--  User: vuxua--%>
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
<%--            }, 5000);--%>
<%--        };--%>
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
  <title>Naoki - Management System</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
<%--      Poppins--%>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<%--      Swiper--%>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
<%--      Bootstrap--%>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<%--   Favicon--%>
  <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logos/square-logo.png" type="image/x-icon">
<%--   Fontawesome--%>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<%--  Custom CSS--%>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/main.css">
</head>
<body>
<!-- Header -->
<jsp:include page="sidebar.jsp">
  <jsp:param name="currentTab" value="author" />
</jsp:include>
<!-- end Header -->

<main class="content" style="margin-left: 250px; padding: 20px;">
  <div class="container fw-medium">
    <div class="row mb-3">
      <div class="col-md-4 d-flex flex-colum">
        <input type="text" class="form-control fw-medium" id="searchBox" placeholder="Search author...">
      </div>
      <div class="col-md-8 d-flex justify-content-end">
        <button class="primary-btn" data-bs-toggle="modal" data-bs-target="#addCategoryModal">Add Author</button>
      </div>
        <!-- Title and Filter Button -->
        <div class="pb-2 my-3 border-bottom">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="fw-bolder">${nameOfCategory}</h2>
                <button class="btn secondary-btn" type="button" data-bs-toggle="collapse" data-bs-target="#filterOptions">
                    <i class="fas fa-filter"></i>
                    <span id="filterButtonText">Bộ Lọc</span>
                </button>
            </div>

            <!-- Filters -->
            <div class="collapse mt-3 " id="filterOptions">
                <div class="p-3 border rounded bg-light">
                    <h2 class="fw-semibold">Bộ Lọc</h2>
                    <form method="get">
                        <div class="row">
                            <div class="col-md-3">
                                <b class="text-danger">Quốc tịch</b>
                                <div class="form-check">
                                    <input onclick="filterNationality(this)" type="checkbox" id="asia" name="nationality" class="form-check-input" value="Châu Á" >
                                    <label for="asia" class="form-check-label">Châu Á</label>
                                </div>
                                <div class="form-check">
                                    <input onclick="filterNationality(this)" type="checkbox" id="europe" name="nationality" class="form-check-input" value="Châu Âu" >
                                    <label for="europe" class="form-check-label">Châu Âu</label>
                                </div>
                                <div class="form-check">
                                    <input onclick="filterNationality(this)" type="checkbox" id="americas" name="nationality" class="form-check-input" value="Châu Mỹ" >
                                    <label for="americas" class="form-check-label">Châu Mỹ</label>
                                </div>
                                <div class="form-check">
                                    <input onclick="filterNationality(this)" type="checkbox" id="africa" name="nationality" class="form-check-input" value="Châu Phi">
                                    <label for="africa" class="form-check-label">Châu Phi</label>
                                </div>
                                <div class="form-check">
                                    <input onclick="filterNationality(this)" type="checkbox" id="oceania" name="nationality" class="form-check-input" value="Châu Đại Dương" >
                                    <label for="oceania" class="form-check-label">Châu Đại Dương</label>
                                </div>
                                <div class="form-check">
                                    <input onclick="filterNationality(this)" type="checkbox" id="territory" name="nationality" class="form-check-input" value="Lãnh thổ và các vùng phụ thuộc" >
                                    <label for="territory" class="form-check-label">Vùng lãnh thổ</label>
                                </div>

                            </div>
                            <div class="col-md-8"></div>
                        </div>
                    </form>
                </div>
            </div>
            </div>
        <!-- End Filter -->
    </div>
    <div class="table-responsive border border-2 p-2" style="max-height: 75%; overflow: auto;">
      <table class="table table-bordered" style="min-width: 1000px;">
        <thead>
        <tr>
          <th></th>
          <th>ID</th>
          <th>Tác giả</th>
          <th>Ảnh tác giả</th>
          <th>Mô tả</th>
          <th>Quốc tịch</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="author" items="${sessionScope.authors}">
          <tr class="fw-medium">
            <td class="d-flex">
              <button class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editCategoryModal"
                      onclick="editCategory('${author.getId()}', '${author.getName()}', '${author.getImageURL()}', '${author.getDescription()}', '${author.getNationality()}')">
                <i class="fas fa-edit"></i>
              </button>
              <form id="deleteForm" method="post" action="${pageContext.request.contextPath}/ms/msauthor" class="mb-0">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="deleteId" value="${author.getId()}"/>
                <button type="submit" class="btn btn-danger btn-sm">
                  <i class="fas fa-trash"></i>
                </button>
              </form>
            </td>
            <td>${fn:escapeXml(author.getId())}</td>
            <td>${fn:escapeXml(author.getName())}</td>
            <td><img style="object-fit: cover" width="200px" height="200px" src="${author.getImageURL()}" alt="author_img"></td>
            <td>${author.getDescription()}</td>
            <td>${fn:escapeXml(author.getNationality())}</td>
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
      <form action="${pageContext.request.contextPath}/ms/msauthor" method="post">
        <input type="hidden" name="csrf" value="${_csrf.token}"/>
        <input type="hidden" name="action" value="add"/>
        <div class="modal-header">
          <h5 class="modal-title fw-semibold" id="addCategoryModalLabel">Add Author</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="authorName" class="form-label">Tác giả</label>
            <input type="text" class="form-control" id="authorName" name="author" required>
          </div>
          <div class="mb-3">
            <label for="authorAvatar" class="form-label">Ảnh đại diện</label>
            <input type="text" class="form-control" id="authorAvatar" name="avatar" required>
          </div>
          <div class="mb-3">
            <label for="authorDescription" class="form-label">Mô tả</label>
            <input type="text" class="form-control" id="authorDescription" name="description" required>
          </div>
          <div class="mb-3">
            <label for="authorNationality" class="form-label">Quốc tịch</label>
            <select class="form-control" id="authorNationality" name="nationality" required>
              <option value="">Select an option</option>
            </select>
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
      <form action="${pageContext.request.contextPath}/ms/msauthor" method="post">
        <%-- Validate CSRF token --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="action" value="edit"/>
        <div class="modal-header">
          <h5 class="modal-title fw-semibold" id="editCategoryModalLabel">Edit Author</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" id="editAuthorId" name="author_id"/>
          <div class="mb-3">
            <label for="editAuthorName" class="form-label">Author</label>
            <input type="text" class="form-control" id="editAuthorName" name="author" required>
          </div>
          <div class="mb-3">
            <label for="editAuthorAvatar" class="form-label">Ảnh đại diện</label>
            <input type="text" class="form-control" id="editAuthorAvatar" name="avatar" required>
          </div>
          <div class="mb-3">
            <label for="editAuthorDescription" class="form-label">Mô tả</label>
            <input type="text" class="form-control" id="editAuthorDescription" name="description" required>
          </div>
          <div class="mb-3">
            <label for="editAuthorNationality" class="form-label">Quốc tịch</label>
            <select class="form-control" id="editAuthorNationality" name="nationality" required>
              <option value="">Select an option</option>
            </select>
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

<script src="${pageContext.request.contextPath}/assets/javascript/management-system/ms-author.js"></script>

<script>
  function editCategory(id, name, avatar, description, nationality) {
    document.getElementById('editAuthorId').value = id;
    document.getElementById('editAuthorName').value = name;
    document.getElementById('editAuthorAvatar').value = avatar;
    console.log(encodeURIComponent(description))
    document.getElementById('editAuthorDescription').value = description;
    document.getElementById('editAuthorNationality').value = nationality;

  }

  function deleteCategory() {
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
