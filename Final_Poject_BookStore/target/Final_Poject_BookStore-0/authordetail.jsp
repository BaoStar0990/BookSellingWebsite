<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/16/2024
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
  <title>Naoki</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
  <%--    Swiper--%>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
  <%-- end    Swiper--%>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
  <%-- Favicon --%>
  <link rel="icon" href="assets/images/logos/square-logo.png" type="image/x-icon">
  <%-- Fontawesome --%>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <%-- Custom CSS --%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/book-list.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
</head>
<body>

  <!-- Header -->
  <jsp:include page="WEB-INF/views/header.jsp">
    <jsp:param name="currentTab" value="home" />
  </jsp:include>
  <!-- end Header -->

  <!-- Link -->
  <div class="container mt-2">
    <p class="fw-semibold">
      Trang chủ
      <i class="fa-solid fa-chevron-right"></i>
      Tác giả
    </p>
  </div>

  <div class="container mt-4 p-0" style="background: none;">
      <div class="row align-items-center py-4"
           style="background-color: #fff; background-image: repeating-linear-gradient(90deg, #f9d7d7 20px, #ffffff 20px, #ffffff 40px); border-radius: 10px;">
        <!-- Image Column -->
        <div class="col-12 col-md-4 text-center">
          <img src="${pageContext.request.contextPath}/assets/images/authors/author.jpg" alt="Paulo Coelho" class="img-fluid rounded-circle shadow-sm mb-3"
               style="width: 250px; height: 250px; object-fit: cover;" />
        </div>
        <!-- Author Details Column -->
        <div class="col-12 col-md-8">
          <h2 class="fw-bold">Paulo Coelho</h2>
          <p class="fw-medium">Paulo Coelho sinh năm 1947 tại Rio de Janeiro (Brazil) trong một gia đình trung lưu.
            Năm 1970, ông bỏ dở việc học để lên đường du lịch qua nhiều nơi thuộc châu Mỹ,
            châu Âu và Bắc Phi trước khi trở về Brazil và kết hôn với vợ, họa sĩ Christina Oiticica.
            Sau chuyến hành hương năm 1986, Coelho quyết định từ bỏ sự nghiệp sáng tác lời ca khúc đang
            rất thành công để quay lại theo đuổi giấc mơ văn chương thời trẻ. Nhà giả kim (1988)
            đã đưa ông trở thành tên tuổi được biết đến trên toàn thế giới. Cho tới nay Coelho
            đã cho ra đời hơn 30 cuốn sách, dịch ra trên 80 thứ tiếng, phát hành ở hơn 170 nước trên thế giới.
            Ông là tác giả viết tiếng Bồ Đào Nha được đọc nhiều nhất mọi thời đại, và cũng là tác giả được
            theo dõi nhiều nhất trên mạng xã hội. Năm 2007, ông được bổ nhiệm làm Sứ giả hòa bình của Liên Hiệp Quốc.</p>
        </div>
      </div>


    <%--    attribute : List<Book>--%>
<%--    Author's book list--%>
    <!-- Book List -->
    <%--    Thay bằng book-list.jsp trên trang động--%>
    <div class="row book-list">
      <div class="d-flex border-bottom mb-2">
        <h2 class="font-semibold">Sách Liên Quan</h2>
        <a href="#" class="ms-auto font-medium align-self-end mb-1 text-decoration-none">
          Xem thêm <i class="fas fa-chevron-right"></i>
        </a>
      </div>
      <c:forEach var="i" begin="1" end="6">
        <div class="col-6 col-md-4 col-lg-2 p-2">
          <a href="bookdetails" class="d-block text-decoration-none card-shadow" style="color: inherit;">
            <div class="card">
              <!-- book image -->
              <img src="https://thpthuongphung.quangtri.edu.vn/upload/32303/fck/files/Picture1(8).jpg" class="card-img-top" alt="Sample Book Title">
              <div class="card-body">
                <!-- book title -->
                <h6 class="card-title mb-1">Trên đường băng</h6>
                <!-- author name -->
                <p class="text-muted mb-0 card-text">
                  <span class="fw-semibold">Tony Buổi Sáng</span>
                </p>
                <div class="mt-2">
                  <!--  price -->
                  <span class="text-danger font-semibold">
                      <fmt:formatNumber value="100000" type="number" pattern="#,##0" />đ
                  </span>
                  <!--  discount -->
                  <span class="badge bg-danger ms-2">-10%</span>
                </div>
              </div>
            </div>
          </a>
        </div>
      </c:forEach>
    </div>
    <!--end Book List -->
  </div>

  <%--    Footer--%>
  <jsp:include page="WEB-INF/views/footer.jsp"/>
  <%-- end Footer--%>
  <script src="./assets/javascript/header.js"></script>
</body>
</html>
