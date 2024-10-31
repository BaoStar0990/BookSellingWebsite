<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container address-setting fw-medium">
  <h4 class="pb-2 mb-3 fw-bold">Thông tin Địa chỉ</h4>

  <!-- Address List -->
  <div class="row">
    <c:forEach var="i" begin="1" end="3">
      <div class="col-12 mb-3">
        <div class="card p-3 shadow-sm h-100">
          <div class="d-flex justify-content-between align-items-center mb-2">
            <p class="mb-0">
              <strong>Họ tên:</strong> <span class="me-3">Vũ Xuân Quang</span>
              <c:if test="${i == 1}"> <!-- Check if this is the default address -->
                  <span class="text-success d-block d-md-inline-block mt-1 mt-md-0">
                      <i class="fas fa-check-circle mb-1 me-2"></i> Địa chỉ thanh toán mặc định
                  </span>
              </c:if>
            </p>
            <div class="d-flex align-items-center">
              <a href="#" class="text-primary me-2" data-bs-toggle="modal" data-bs-target="#addressModal" onclick="setModalForEdit(${i})">
                <i class="fas fa-edit mb-1"></i>
              </a>
              <a href="" class="text-primary">
                <i class="fas fa-trash mb-1"></i>
              </a>
            </div>
          </div>
          <p class="mb-2"><strong>Địa chỉ:</strong> 1 Võ Văn Ngân, Phường Linh Chiểu, Thành phố Thủ Đức , Thành phố Hồ Chí Minh , Việt Nam</p>
          <p class="mb-1"><strong>Số điện thoại:</strong> 0912345678</p>
          <c:if test="${i != 1}"> <!-- Check if this is not the default address -->
            <form action="" method="post" class="d-inline my-1">
              <input type="hidden" name="id" value="" />
              <a type="submit" class="text-primary text-decoration-none mb-0 p-0">
                Đặt làm mặc định
              </a>
            </form>
          </c:if>
        </div>
      </div>
    </c:forEach>
  </div>

  <!-- Button to trigger modal for adding new address -->
  <button type="button" class="primary-btn mt-2" data-bs-toggle="modal" data-bs-target="#addressModal" onclick="setModalForAdd()">
    Thêm địa chỉ
  </button>

  <!-- Modal for Adding/Editing Address -->
  <div class="modal fade" id="addressModal" tabindex="-1" aria-labelledby="addressModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg"> <!-- Modal enlarged with modal-lg class -->
      <div class="modal-content">
        <form action="" method="post" id="addressForm">
          <div class="modal-header">
            <h5 class="modal-title fs-semibold" id="addressModalLabel">Thêm địa chỉ giao hàng</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <!-- Name and Phone Number in the same row -->
            <div class="row g-2 mb-3">
              <div class="col-md-6">
                <label for="name" class="form-label">Họ tên:</label>
                <input type="text" class="form-control" id="name" name="name" required />
              </div>
              <div class="col-md-6">
                <label for="phone" class="form-label">Số điện thoại:</label>
                <input type="text" class="form-control" id="phone" name="phone" required />
              </div>
            </div>

            <!-- Address Fields -->
            <div class="mb-3">
              <label for="address" class="form-label">Địa chỉ chi tiết:</label>
              <div id="address" class="row g-2">
                <div class="col-md-4">
                  <select class="form-select" id="tinh" name="tinh" title="Chọn Tỉnh Thành">
                    <option selected value="0">Tỉnh Thành</option>
                  </select>
                </div>
                <div class="col-md-4">
                  <select class="form-select" id="quan" name="quan" title="Chọn Quận Huyện">
                    <option selected value="0">Quận Huyện</option>
                  </select>
                </div>
                <div class="col-md-4">
                  <select class="form-select" id="phuong" name="phuong" title="Chọn Phường Xã">
                    <option selected value="0">Phường Xã</option>
                  </select>
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="addressDetail" class="form-label">Địa chỉ chi tiết:</label>
              <input type="text" class="form-control" id="addressDetail" name="address" required />
            </div>
            <input type="hidden" id="addressId" name="id" />
          </div>
          <div class="modal-footer">
            <button type="submit" class="primary-btn">Lưu</button>
            <button type="button" class="secondary-btn" data-bs-dismiss="modal">Hủy</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<script src="https://esgoo.net/scripts/jquery.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascript/GetAddress.js"></script>
<script>
  // JavaScript to handle Add/Edit modal functionality
  function setModalForAdd() {
    document.getElementById("addressForm").action = "";
    document.getElementById("addressModalLabel").innerText = "Thêm địa chỉ giao hàng";
    document.getElementById("name").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("addressDetail").value = "";
    document.getElementById("addressId").value = "";
    loadProvinces(); // Reset province dropdown
    $("#quan, #phuong").html('<option value="0">Quận Huyện</option><option value="0">Phường Xã</option>'); // Reset district and ward
  }

  function setModalForEdit(id) {
    document.getElementById("addressForm").action = "";
    document.getElementById("addressModalLabel").innerText = "Chỉnh sửa địa chỉ giao hàng";
    document.getElementById("name").value = "Vũ Xuân Quang";
    document.getElementById("phone").value = "0912345678";
    document.getElementById("addressDetail").value = "1 Võ Văn Ngân, Phường Linh Chiểu, Thành phố Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam";
    document.getElementById("addressId").value = id;
    loadProvinces(); // Load province data for edit
  }
</script>


