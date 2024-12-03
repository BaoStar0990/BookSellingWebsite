<%@page import="model.Customer"%>
<%@page import="model.Address"%>
<%@page import="Utils.authentication.CSRFUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container address-setting fw-medium">
  <h4 class="pb-2 mb-3 fw-bold">Thông tin Địa chỉ</h4>
  
  <!-- Address List -->
  <div class="row">
    <c:forEach var="address" items="${sessionScope.user.getListAddresses()}">
    <%-- Lấy token --%>
      <div class="col-12 mb-3">
        <div class="card p-3 shadow-sm h-100">
          <div class="d-flex justify-content-between align-items-center mb-2">
            <p class="mb-0">
              <strong>Họ tên:</strong> <span class="me-3">${address.getFullName()}</span>
              <c:if test="${address.isDefaultAddress()}"> <!-- Check if this is the default address -->
                  <span class="text-success d-block d-md-inline-block mt-1 mt-md-0">
                      <i class="fas fa-check-circle mb-1 me-2"></i> Địa chỉ thanh toán mặc định
                  </span>
              </c:if>
            </p>
            <div class="d-flex align-items-center">
              <a href="#" class="text-primary me-2" data-bs-toggle="modal"
                 data-bs-target="#addressModal" 
                 onclick="setModalForEdit('<c:out value="${address.createJson()}" />')">
                <i class="fas fa-edit mb-1"></i>
              </a>
                <form action="/modifyaddress" method="post" class="d-inline my-1" id="deleteAdderessForm" onsubmit="return confirmDelete();">                    
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" id="idDelete" name="idDelete" value="${address.getId()}"/>
                    <button class=" btn no-border text-primary text-decoration-none mb-0 p-0" type="submit">
                        <i class="fas fa-trash mb-1"></i>
                    </button>
                  <!--</a>-->
                </form>        
            </div>
          </div>
                    
          <p class="mb-2"><strong>Địa chỉ: </strong>${address.toString()}</p>
          <p class="mb-1"><strong>Số điện thoại: </strong>${address.getPhonenumber()}</p>
          
          <c:if test="${!address.isDefaultAddress()}"> <!-- Check if this is not the default address -->
            <form action="/modifyaddress" method="post" class="d-inline my-1" id ="defaultAddressForm">
                <input type="hidden" name="action" value="defaultAddress" />              
                <input type="hidden" id="idDefault" name="idDefault" value="${address.getId()}"/>
                <button class="btn no-border text-primary text-decoration-none mb-0 p-0" 
                  onclick="document.getElementById('defaultAddressForm').submit();">
                    Đặt làm mặc định
                </button>
            </form>
          </c:if>       
        </div>
      </div>
    </c:forEach>
  </div>

  <!-- Button to trigger modal for adding new address -->
  <button type="button" class="primary-btn mt-2" data-bs-toggle="modal" 
          data-bs-target="#addressModal" onclick="setModalForAdd()">
    Thêm địa chỉ
  </button>

  <!-- Modal for Adding/Editing Address -->
  <div class="modal fade" id="addressModal" tabindex="-1" aria-labelledby="addressModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg"> <!-- Modal enlarged with modal-lg class -->
      <div class="modal-content">
        <form action="/modifyaddress" method="post" id="addressForm">
            <input type="hidden" name="action" id ="action" value ="">
          <div class="modal-header">
            <h5 class="modal-title fs-semibold" id="addressModalLabel">Thêm địa chỉ giao hàng</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" onclick="resetAddress()" aria-label="Close"></button>
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
                    <select class="form-select" id="tinh" name="tinh" title="Chọn Tỉnh Thành" required>
                    <option selected value="0" disabled selected >Tỉnh Thành</option>
                  </select>
                    <input type ="hidden" name="tinhText" id ="tinhText" value ="">
                </div>
                <div class="col-md-4">
                    <select class="form-select" id="quan" name="quan" title="Chọn Quận Huyện" required>
                    <option selected value="0" disabled selected >Quận Huyện</option>
                  </select>
                    <input type ="hidden" name="quanText" id ="quanText" value ="">
                </div>
                <div class="col-md-4">
                    <select class="form-select" id="phuong" name="phuong" title="Chọn Phường Xã" required>
                        <option selected value="0" disabled selected>Phường Xã</option>
                  </select>
                    <input type ="hidden" name="phuongText" id ="phuongText" value ="">
                </div>
              </div>
            </div>
            <div class="mb-3">
              <label for="addressDetail" class="form-label">Địa chỉ chi tiết:</label>
              <input type="text" class="form-control" id="addressDetail" name="addressDetail" required />
            </div>
            <input type="hidden" id="addressId" name="addressId" />
          </div>
          <div class="modal-footer">
            <button type="submit" class="primary-btn" onclick="onclickSave()">Lưu</button>
            <button type="button" class="secondary-btn" data-bs-dismiss="modal" onclick="resetAddress()">Hủy</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<!-- Ensure jQuery is loaded before using it -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascript/GetAddress.js"></script>
<script>
    function resetAddress(){
        setLocationByNames("Tỉnh Thành", "Quận Huyện", "Phường Xã");
    }

  // JavaScript to handle Add/Edit modal functionality
  function setModalForAdd() {
    document.getElementById("action").value = "add";
    document.getElementById("addressModalLabel").innerText = "Thêm địa chỉ giao hàng";
    document.getElementById("name").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("addressDetail").value = "";
    document.getElementById("addressId").value = "";
    
    setLocationByNames("Tỉnh Thành", "Quận Huyện", "Phường Xã");
  }

  function setModalForEdit(valueJson) {
    // lấy address
    var address = JSON.parse(valueJson);

    document.getElementById("action").value = "edit";
    document.getElementById("addressModalLabel").innerText = "Chỉnh sửa địa chỉ giao hàng";
    document.getElementById("name").value = address.fullName;
    document.getElementById("phone").value = address.phonenumber;
    // set các giá trị options địa chỉ
    setLocationByNames(address.province, address.district, address.ward);

    document.getElementById("addressDetail").value = address.street;
    document.getElementById("addressId").value = address.id;
    
    // set các giá trị Text
    document.getElementById("tinhText").value = $("#tinh option:selected").text();
    document.getElementById("quanText").value = $("#quan option:selected").text();
    document.getElementById("phuongText").value = $("#phuong option:selected").text();
//    alert($("#tinh option:selected").text());
//    alert(address.id);
//    loadProvinces(); // Load province data for edit
  }
  
  function onclickSave(){
      // set các giá trị Text
        document.getElementById("tinhText").value = $("#tinh option:selected").text();
        document.getElementById("quanText").value = $("#quan option:selected").text();
        document.getElementById("phuongText").value = $("#phuong option:selected").text();
        // Lấy nội dung địa chỉ, loại bỏ các thẻ HTML
        document.getElementById("phone").value = document.getElementById("phone").value.replace(/<\/?[^>]+(>|$)/g, "");
        document.getElementById("name").value = document.getElementById("name").value.replace(/<\/?[^>]+(>|$)/g, "");
        document.getElementById("addressDetail").value = document.getElementById("addressDetail").value.replace(/<\/?[^>]+(>|$)/g, "");
  }
  
  function confirmDelete() {
        return confirm("Bạn có chắc chắn muốn xóa địa chỉ này không?");
  }
</script>


