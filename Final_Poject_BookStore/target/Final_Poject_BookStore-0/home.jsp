<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Naoki</title>
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
    <%-- Custom CSS --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/book-list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/styles/author.css" />
</head>
<body>
    <!-- Header -->
    <jsp:include page="WEB-INF/views/header.jsp">
        <jsp:param name="currentTab" value="home" />
    </jsp:include>
    <!-- end Header -->

    <%-- Carousel --%>
    <div class="container mt-4 p-0 ">
        <div id="adCarousel" class="carousel slide " data-bs-ride="carousel">
            <div class="carousel-inner">
                <a href="${pageContext.request.contextPath}/filterbook/bookdiscount" class="carousel-item active text-decoration-none d-flex justify-content-center">
                  <div class="row align-items-center slide-ad">
                    <img src="${pageContext.request.contextPath}/assets/images/ads/blackfriday.webp" alt="Ad 1" class="img-fluid character-image" style="max-width: 100%; height: auto;" />
                  </div>
                </a>
                <a href="${pageContext.request.contextPath}/filterbook/bookdiscount" class="carousel-item text-decoration-none">
                  <div class="row align-items-center slide-ad">
                    <img src="${pageContext.request.contextPath}/assets/images/ads/tanviet.webp" alt="Ad 2" class="img-fluid character-image" style="max-width: 100%; height: auto;" />
                  </div>
                </a>
            </div>
              
            <!-- Carousel Controls -->
            <button class="carousel-control-prev" type="button" data-bs-target="#adCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon text-dark" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#adCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon text-dark" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>

    <%-- end Carousel --%>

<%--    &lt;%&ndash; New book section&ndash;%&gt;--%>
<%--    --%>
<%--    &lt;%&ndash; On discount section&ndash;%&gt;--%>
    <jsp:include page="WEB-INF/views/book/book-list.jsp">
        <jsp:param name="bookListName" value="Sách phổ biến" />
        <jsp:param name="booksAttribute" value="homeBooks" />
        <jsp:param name="seeMoreLink" value="/additionalbook/allbook" />
    </jsp:include>
<%--    &lt;%&ndash; Up comming section&ndash;%&gt;--%>
<%--    <jsp:include page="WEB-INF/views/book/book-list.jsp">--%>
<%--        <jsp:param name="bookListName" value="Sách mới" />--%>
<%--        <jsp:param name="booksAttribute" value="bestsellerBooks" />--%>
<%--        <jsp:param name="seeMoreLink" value="/Naoki/books/upcomming" />--%>
<%--    </jsp:include>--%>

    <%-- ads --%>
    <a href="${pageContext.request.contextPath}/filterbook/bookdiscount" class="container d-flex justify-content-center mt-4"> 
        <img src="https://cdn0.fahasa.com/media/wysiwyg/Thang-12-2024/TanVietT12_LDP_tanviet_Tagname_1.jpg" alt="Xmas ads" class="character-image rounded-4" style="max-width: 100%; height: auto;" />
    </a>

<%-- Info section --%>
    <div class="container my-5">
        <div class="row align-items-center">
            <div class="col-md-6">
                <h1 class="fw-bold brand">
                    <span>NA</span><span class="text-primary">OKI</span>
                </h1>
                <p class="text-primary">Cuộc đời chúng ta thay đổi qua những con người ta gặp và những cuốn sách ta đọc.</p>
                <p class="mb-4">
                    Naoki hướng tới trở thành một nền tảng bán sách trực tuyến hàng đầu tại Việt Nam, nơi kết nối độc giả với những cuốn sách giá trị, đa dạng thể loại, và mang lại nguồn cảm hứng bất tận. Naoki mong muốn xây dựng một cộng đồng yêu sách, nơi mà tri thức được chia sẻ và lan tỏa không ngừng, góp phần xây dựng một xã hội văn minh, giàu tri thức.
                </p>
                <a href="/aboutus" class="btn primary-btn d-inline-block px-4 py-2">Xem thêm</a>
            </div>
            <div class="col-md-6">
                <img src="${pageContext.request.contextPath}\assets\images\naoki.jpg" alt="Naoki Bookstore" class="img-fluid rounded" />
            </div>
        </div>
    </div>
<%-- End Info section --%>

<%--    Author--%>
    <jsp:include page="WEB-INF/views/authors.jsp"/>
<%--end    Author--%>

    <%-- Partner --%>
    <div class="container my-5">
        <h2 class="text-center fw-bold mb-4">Các Đối Tác</h2>
        <div class="row">
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/5_NCC_McBook_115x115.png" alt="Partner 1" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/NCC_AlphaBooks_115x115.png" alt="Partner 2" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/3_NCC_TanViet_115x115.png" alt="Partner 3" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/9_NCC_MinhLong_115x115.png" alt="Partner 4" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/7_NCC_SGBook_115x115.png" alt="Partner 5" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/8_NCC_ZenBooks_115x115.png" alt="Partner 6" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/11_NCC_BachViet_115x115.png" alt="Partner 7" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/NCC_DinhTi_115x115.png" alt="Partner 8" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/16_NCC_MegaBooks_115x115.png" alt="Partner 9" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/NCC_SBooks_115x115.png" alt="Partner 10" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/6_NCC_HuyHoang_115x115.png" alt="Partner 11" class="img-fluid" />
            </div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2 text-center mb-3">
                <img src="https://cdn0.fahasa.com/media/wysiwyg/Hien_UI/LogoNCC/NCC_HuongTrang_115x115.png" alt="Partner 12" class="img-fluid" />
            </div>
        </div>
    </div>
    <%-- end Partner --%>

<%--    Footer--%>
    <jsp:include page="WEB-INF/views/footer.jsp"/>
<%-- end Footer--%>
    <script src="./assets/javascript/header.js"></script>
</body>
</html>