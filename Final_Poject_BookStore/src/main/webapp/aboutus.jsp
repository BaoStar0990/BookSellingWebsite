<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us</title>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/author.css" />
</head>
<body>
<%--    Header--%>
<jsp:include page="WEB-INF/views/header.jsp">
    <jsp:param name="currentTab" value="aboutus" />
</jsp:include>

<!-- Link -->
<div class="container mt-2">
    <p class="fw-semibold">
        <a href="/" class="text-decoration-none text-dark">Trang chủ</a>
        <i class="fa-solid fa-chevron-right"></i>
        <a href="#" class="text-decoration-none text-dark">Về Naoki</a>
    </p>
</div>
<!-- end Link -->

<%--end Header--%>
<div class="container p-0 position-relative mt-5">
    <!-- Image with slogan -->
    <div class="row">
        <div class="col-12 position-relative">
            <img
                    src="https://t3.ftcdn.net/jpg/07/06/22/60/360_F_706226081_PBi9Uc7Kae6oSrz6XcyidRsvW72weSM0.jpg"
                    class="img-fluid w-100"
                    alt="Slogan"
                    style="filter: brightness(50%);"
            />
            <div class="position-absolute top-50 start-50 translate-middle text-center text-white p-3" style="width: 100%;">
                <h1 class="fw-bold display-6 mx-2">
                    Cuộc đời chúng ta thay đổi qua những con người ta gặp và những cuốn sách ta đọc.
                </h1>
            </div>
        </div>
    </div>

    <!-- Member Section -->
    <div class="container p-0 position-relative mt-5">
        <h2 class="text-center mb-4 mt-3 font-bold">Members</h2>
        <div class="row justify-content-center">
            <!-- Member 1 -->
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 text-center">
                <div class="card shadow-sm text-center" style="border-radius: 15px; padding: 20px;">
                    <div class="position-relative">
                        <div class="border-wrapper">
                            <img
                                    src="${pageContext.request.contextPath}/assets/images/authors/author.jpg"
                                    alt="Hà Đăng Quang"
                                    class="img-fluid rounded-circle m-0"
                                    style="width: 150px; height: 150px; object-fit: cover;"
                            />
                        </div>
                    </div>
                    <h5 class="card-title fw-bold mt-3">Hà Đăng Quang</h5>
                    <p class="card-text fw-semibold">222110210</p>
                    <p class="card-text fw-semibold">Leader</p>
                    <div class="social-icons">
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-facebook"></i></a>
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-github"></i></a>
                        <a href="#" class="text-danger fs-4"><i class="fab fa-linkedin"></i></a>
                    </div>
                </div>
            </div>

            <!-- Member 2 -->
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 text-center">
                <div class="card shadow-sm text-center" style="border-radius: 15px; padding: 20px;">
                    <div class="position-relative">
                        <div class="border-wrapper">
                            <img
                                    src="${pageContext.request.contextPath}/assets/images/authors/author.jpg"
                                    alt="Vũ Xuân Quang"
                                    class="img-fluid rounded-circle m-0"
                                    style="width: 150px; height: 150px; object-fit: cover;"
                            />
                        </div>
                    </div>
                    <h5 class="card-title fw-bold mt-3">Vũ Xuân Quang</h5>
                    <p class="card-text fw-semibold">22110212</p>
                    <p class="card-text fw-semibold">Member</p>
                    <div class="social-icons">
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-facebook"></i></a>
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-github"></i></a>
                        <a href="#" class="text-danger fs-4"><i class="fab fa-linkedin"></i></a>
                    </div>
                </div>
            </div>

            <!-- Member 3 -->
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 text-center">
                <div class="card shadow-sm text-center" style="border-radius: 15px; padding: 20px;">
                    <div class="position-relative">
                        <div class="border-wrapper">
                            <img
                                    src="${pageContext.request.contextPath}/assets/images/authors/author.jpg"
                                    alt="Cao Thị Xuân Hương"
                                    class="img-fluid rounded-circle m-0"
                                    style="width: 150px; height: 150px; object-fit: cover;"
                            />
                        </div>
                    </div>
                    <h5 class="card-title fw-bold mt-3">Cao Thị Xuân Hương</h5>
                    <p class="card-text fw-semibold">22110212</p>
                    <p class="card-text fw-semibold">Member</p>
                    <div class="social-icons">
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-facebook"></i></a>
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-github"></i></a>
                        <a href="#" class="text-danger fs-4"><i class="fab fa-linkedin"></i></a>
                    </div>
                </div>
            </div>

            <!-- Member 4 -->
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 text-center">
                <div class="card shadow-sm text-center" style="border-radius: 15px; padding: 20px;">
                    <div class="position-relative">
                        <div class="border-wrapper">
                            <img
                                    src="${pageContext.request.contextPath}/assets/images/authors/author.jpg"
                                    alt="Trần Đinh Gia Bảo"
                                    class="img-fluid rounded-circle m-0"
                                    style="width: 150px; height: 150px; object-fit: cover;"
                            />
                        </div>
                    </div>
                    <h5 class="card-title fw-bold mt-3">Trần Đinh Gia Bảo</h5>
                    <p class="card-text fw-semibold">22110212</p>
                    <p class="card-text fw-semibold">Member</p>
                    <div class="social-icons">
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-facebook"></i></a>
                        <a href="#" class="text-danger fs-4 me-2"><i class="fab fa-github"></i></a>
                        <a href="#" class="text-danger fs-4"><i class="fab fa-linkedin"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--Footer--%>
<jsp:include page="WEB-INF/views/footer.jsp"/>
<%-- end   Footer--%>

</body>
</html>
