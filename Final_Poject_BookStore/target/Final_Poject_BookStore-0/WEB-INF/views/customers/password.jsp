<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
  <h4 class="border-bottom pb-2 mb-3 fw-bold">Thay đổi Mật khẩu</h4>
  <div class="card p-4 shadow-sm">
    <div id="errorMessage" class="alert alert-danger d-none"></div>
    <div id="successMessage" class="alert alert-success d-none"></div>

    <form action="" id="changePasswordForm" onsubmit="handleChangePassword(event)">
      <div class="mb-3">
        <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
        <div class="input-group">
          <span class="input-group-text">&#128274;</span>
          <input type="password" id="currentPassword" class="form-control" placeholder="Nhập mật khẩu hiện tại">
        </div>
      </div>

      <div class="mb-3">
        <label for="newPassword" class="form-label">Mật khẩu mới</label>
        <div class="input-group">
          <span class="input-group-text">&#128274;</span>
          <input type="password" id="newPassword" class="form-control" placeholder="Nhập mật khẩu mới">
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
  function handleChangePassword(event) {
    event.preventDefault();

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
      return;
    }
    if (newPassword !== confirmPassword) {
      errorMessage.innerHTML = "Mật khẩu mới không khớp.";
      errorMessage.classList.remove("d-none");
      return;
    }
    if (newPassword.length < 6) {
      errorMessage.innerHTML = "Mật khẩu mới phải có ít nhất 6 ký tự.";
      errorMessage.classList.remove("d-none");
      return;
    }

    // Simulate successful password change
    successMessage.innerHTML = "Mật khẩu đã được thay đổi thành công!";
    successMessage.classList.remove("d-none");

    // Clear input fields
    document.getElementById("currentPassword").value = "";
    document.getElementById("newPassword").value = "";
    document.getElementById("confirmPassword").value = "";
  }
</script>