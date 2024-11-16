<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h4 class="text-start fw-semibold">Thông tin khách hàng</h4>
<div class="mt-4 fw-medium">
    <p><b>Họ tên : </b>${sessionScope.user.getFullName()}</p>
    <p><b>Email : </b>${sessionScope.user.getEmail()}</p>
</div>