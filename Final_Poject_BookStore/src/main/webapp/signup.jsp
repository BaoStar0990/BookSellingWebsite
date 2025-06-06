<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/6/2024
  Time: 12:03 PM
  To change this template use File | Settings | File Templates.
--%>
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

    <%-- Flatpickr CSS and JS --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

    <%-- Custom CSS --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/signin.css" />
</head>
<body>
<!-- Header -->
<jsp:include page="WEB-INF/views/header.jsp"/>
<!-- end Header -->

<!-- Sign In -->
<div class="container d-flex justify-content-center align-items-center mt-5">
    <div class="signin-container my-3 p-4 rounded" >
        <div class="d-flex flex-column align-items-center">
            <h2 class="text-center fw-bold mb-4">Đăng Ký</h2>
            <div class="container-fluid">
                <form action="signup" method="post" id="myForm">
                    <div class="mb-3">
                        <input type="text" value="${fullName}"  class="form-control input-field" id="fullName" name="fullName" placeholder="Họ và tên" required>
                    </div>
                    <div class="mb-3">
                        <input type="text" value="${dateStr}" class="form-control input-field" id="dob" name="dob" placeholder="Sinh nhật (dd/mm/yyyy)" required>
                    </div>
                    <script>
                        flatpickr("#dob", {
                            dateFormat: "d/m/Y",
                            maxDate: "today",
                            onChange: function(selectedDates, dateStr, instance) {
                                if (new Date(selectedDates[0]) > new Date()) {
                                    alert('Sinh nhật không hợp lệ');
                                    instance.clear();
                                }
                            }
                        });
                    </script>
                    <div class="mb-3">
                        <input type="email" value="${email}" class="form-control input-field" id="email" name="email" placeholder="Email" required>
                    </div>
                    <div class="mb-3">
                        <input type="text" value="${numberPhone}" class="form-control input-field" id="numberPhone" name="numberPhone" placeholder="Số điện thoại" required oninput="this.value = this.value.replace(/[^0-9]/g, '') ">
                    </div>
                    <div class="mb-3">
                        <input type="password" value="${password}" class="form-control input-field" id="password" name="password" placeholder="Nhập mật khẩu" required>
                    </div>
                    <div class="mb-3">
                        <input type="password" class="form-control input-field" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                    </div>

                    <!-- Sign Up button -->
                    <button type="submit" class="primary-btn w-100 mb-4 mt-4">Đăng Ký</button>
                </form>

                <!-- Login link -->
                <p class="text-center mb-0">
                    Hoặc, <a href="signin" class="text-danger fw-bold text-decoration-none">Đăng Nhập</a>
                </p>
            </div>
        </div>
    </div>
</div>
<script>
    function handleChangePassword(newPassword) {
        const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_,.<>?])\S{8,}$/;
        if (!pattern.test(newPassword)) {
            window.alert("Mật khẩu phải có độ dài lớn hơn 8, phải chứa ít nhất 1 chữ cái thường, chữ in hoa, chữ số, ít nhất 1 ký tự " +
                "đặt biệt !@#$%^&*()_  và không chứa khoảng trống.");
            return false;
        }
        return true;
    }
    function validateInputUsername(input) {
        const regex = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯăâêôờỳỵỷỹ\s]+$/;
        if (!regex.test(input)) {
            return false;
        }
        return true;
    }

    document.getElementById('myForm').addEventListener('submit', function(event) {
        const newPassword = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const fullName = document.getElementById('fullName').value;
        console.log(newPassword);
        if(handleChangePassword(newPassword) == false){
            event.preventDefault();
        }else if(newPassword != confirmPassword){
            event.preventDefault();
            window.alert('Mật khẩu không khớp với nhau');
        }else if(validateInputUsername(fullName) == false)
        {
            event.preventDefault();
            alert("Họ và tên không hợp lệ, vui lòng nhập lại ");
        }
    });
</script>
<!-- end Sign In -->

<%--    Footer--%>
<jsp:include page="WEB-INF/views/footer.jsp"/>
<%-- end    Footer--%>
</body>
</html>
