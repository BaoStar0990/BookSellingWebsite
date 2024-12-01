<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
  <h4 class="border-bottom pb-2 mb-3 fw-bold">Thay đổi Mật khẩu</h4>
  <div class="card p-4 shadow-sm">
    <c:choose>
        <c:when test="${requestScope.errorMessage == null}">
            <div id="errorMessage" class="alert alert-danger d-none"></div>
        </c:when>
        <c:otherwise>
            <div id="errorMessage" class="alert alert-danger">${requestScope.errorMessage}</div>
        </c:otherwise>
    </c:choose>
      
    <div id="successMessage" class="alert alert-success d-none"></div>

    <form action="/changepassword" id="changePasswordForm" method = "post" onsubmit="handleChangePassword(event)">
      <div class="mb-3">
        <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
        <div class="input-group">
          <span class="input-group-text">&#128274;</span>
          <input type="password" id="currentPassword" name ="currentPassword" class="form-control" placeholder="Nhập mật khẩu hiện tại">
        </div>
      </div>

      <div class="mb-3">
        <label for="newPassword" class="form-label">Mật khẩu mới</label>
        <div class="input-group">
          <span class="input-group-text">&#128274;</span>
          <input type="password" id="newPassword" name ="newPassword" class="form-control" placeholder="Nhập mật khẩu mới">
        </div>
      </div>

      <div class="mb-3">
        <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
        <div class="input-group">
          <span class="input-group-text">&#128274;</span>
          <input type="password" id="confirmPassword" class="form-control" placeholder="Xác nhận mật khẩu mới">
        </div>
      </div>

      <button type="submit" class="primary-btn mt-4">Thay đổi mật khẩu</button>
    </form>
  </div>
</div>

<script>
//    const currentPasswordFromServer = "<%--${sessionScope.user.password} --%>";
//    document.getElementById("currentPassword").addEventListener('input', function(event) {
//        const errorMessage = document.getElementById("errorMessage");
//        const inputPassword = document.getElementById("currentPassword").value;
//        // Reset messages
//        errorMessage.classList.add("d-none");
//        // So sánh mật khẩu
//        if (currentPasswordFromServer !== inputPassword) {
//            errorMessage.classList.remove("d-none");
//            errorMessage.innerHTML = "Mật khẩu hiện tại không đúng";
//        }
//    });
  function handleChangePassword(event) {
    // Get elements
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorMessage = document.getElementById("errorMessage");
    const successMessage = document.getElementById("successMessage");

    // Reset messages
    errorMessage.classList.add("d-none");
    successMessage.classList.add("d-none");

    // Basic validation
    if (!currentPassword || !newPassword || !confirmPassword) {
      errorMessage.innerHTML = "Vui lòng điền tất cả các trường.";
      errorMessage.classList.remove("d-none");
      event.preventDefault(); // Ngăn form submit
      return;
    }
    if (newPassword !== confirmPassword) {
      errorMessage.innerHTML = "Mật khẩu mới không khớp.";
      errorMessage.classList.remove("d-none");
      event.preventDefault(); // Ngăn form submit
      return;
    }
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_,.<>?])\S{8,}$/;
    if(!pattern.test(newPassword)){
      errorMessage.innerHTML = "Mật khẩu phải có độ dài lớn hơn 8, phải chứa ít nhất 1 chữ cái thường, chữ in hoa, chữ số, ít nhất 1 ký tự " +
              "đặt biệt !@#$%^&*()_  và không chứa khoảng trống.";
      errorMessage.classList.remove("d-none");
      event.preventDefault(); // Ngăn form submit
      return false;
    }
    // Simulate successful password change
//    successMessage.innerHTML = "Mật khẩu đã được thay đổi thành công!";
    successMessage.classList.remove("d-none");
  }
</script>