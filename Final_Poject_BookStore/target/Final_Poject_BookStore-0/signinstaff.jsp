<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/6/2024
  Time: 12:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    System.out.println("In page sign in staff");
    // Lấy thông báo từ request
    String alertMessage = (String) request.getAttribute("alertMessage");
    if (alertMessage != null) {
%>
<script type="text/javascript">
    alert('<%= alertMessage %>');
</script>
<%
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Naoki</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">--%>
    <%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>--%>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>

    <%--    Swiper--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
    <%-- end    Swiper--%>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <%-- Favicon --%>
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logos/square-logo.png" type="image/x-icon">
    <%-- Fontawesome --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <%-- Custom CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/signin.css" />
</head>
<body>
<!-- Header -->
<%--<jsp:include page="WEB-INF/views/header.jsp"/>--%>
<!-- end Header -->
<script>
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;

            // Gửi thông tin vị trí này đến server để lưu trữ
            var loginForm = document.getElementById("loginForm");
            var latitudeInput = document.createElement("input");
            var longitudeInput = document.createElement("input");

            latitudeInput.type = "hidden";
            longitudeInput.type = "hidden";
            latitudeInput.name = "latitude";
            longitudeInput.name = "longitude";
            latitudeInput.value = latitude;
            longitudeInput.value = longitude;

            loginForm.appendChild(latitudeInput);
            loginForm.appendChild(longitudeInput);

            // Submit form hoặc xử lý khác nếu cần
        }, function(error) {
            console.error("Không thể lấy vị trí người dùng: " + error.message);
        });
    } else {
        console.log("Trình duyệt không hỗ trợ geolocation.");
    }
</script>

<!-- Sign In -->
<div class="container d-flex justify-content-center align-items-center mt-5">
    <div class="signin-container my-3 p-4 rounded" >
        <div class="d-flex flex-column align-items-center">
            <h2 class="text-center fw-bold mb-4">Đăng Nhập</h2>
            <div class="container-fluid">
                <form action="<%=String.format("%s/signin/manage/staff",request.getServletContext().getContextPath())%>" method="post" id="loginForm">
                    <input type="hidden" name="csrfToken" id="csrfToken" >
                    <div class="mb-3">
                        <input type="text" class="form-control input-field" name="email" id="email" value="${cookie.email}" placeholder="Email" required>
                    </div>
                    <div class="mb-3">
                        <input type="password" class="form-control input-field" id="password" name="password" value="${cookie.password}" placeholder="Mật khẩu" required>
                    </div>

                    <!-- Forgot password -->
<%--                    <div class="mb-4">--%>
<%--                        <a href="${pageContext.request.contextPath}/forgotpassword" class="text-primary fw-semibold text-decoration-none link-hover">Quên mật khẩu?</a>--%>
<%--                    </div>--%>

<%--                    <div class="mb-3 form-check d-flex align-items-center">--%>
<%--                        <input type="checkbox" class="form-check-input" name="rememberMe" id="rememberMe">--%>
<%--                        <label class="form-check-label ms-2" for="rememberMe">Remember Me</label>--%>
<%--                    </div>--%>
                    <!-- Login button -->
                    <input type="submit" class="primary-btn w-100 mb-4" value="Đăng nhập"/>
                </form>
<%--                <p class="text-center mb-0">--%>
<%--                    Hoặc, <a href="signup" class="text-danger fw-bold text-decoration-none">Đăng ký</a>--%>
<%--                </p>--%>
            </div>
        </div>
    </div>

</div>
<!-- end Sign In -->
<%
    String csrfToken = (String) request.getAttribute("csrfToken");
    if (csrfToken != null) {
%>
<script>
    // Lưu giá trị csrfToken vào localStorage bằng JavaScript
    //localStorage.setItem('csrfToken', '<%= csrfToken %>');
    document.getElementById('csrfToken').value = '<%= csrfToken %>';
</script>
<%
    }
%>


<%--    Footer--%>
<%--<jsp:include page="WEB-INF/views/footer.jsp"/>--%>
<%-- end    Footer--%>
</body>
</html>
