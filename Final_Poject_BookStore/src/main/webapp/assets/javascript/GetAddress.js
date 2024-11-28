// Function to load provinces (Tinh Thanh)
function loadProvinces() {
    $.getJSON('https://provinces.open-api.vn/api/p/', function(data_tinh) {
        $("#tinh").empty().append('<option value="0">Tỉnh Thành</option>');
        $.each(data_tinh, function(key_tinh, val_tinh) {
            $("#tinh").append('<option value="' + val_tinh.code + '">' + val_tinh.name + '</option>');
        });
    });
}

// Function to load districts (Quan Huyen) based on selected province
function loadDistricts(provinceId) {
    if (provinceId === "0") {
        $("#quan").empty().append('<option value="0">Quận Huyện</option>');
        $("#phuong").empty().append('<option value="0">Phường Xã</option>');
        return;
    }

    $.getJSON(`https://provinces.open-api.vn/api/p/${provinceId}?depth=2`, function(data_quan) {
        $("#quan").empty().append('<option value="0">Quận Huyện</option>');
        $.each(data_quan.districts, function(key_quan, val_quan) {
            $("#quan").append('<option value="' + val_quan.code + '">' + val_quan.name + '</option>');
        });
    });
}

// Function to load wards (Phuong Xa) based on selected district
function loadWards(districtId) {
    if (districtId === "0") {
        $("#phuong").empty().append('<option value="0">Phường Xã</option>');
        return;
    }

    $.getJSON(`https://provinces.open-api.vn/api/d/${districtId}?depth=2`, function(data_phuong) {
        $("#phuong").empty().append('<option value="0">Phường Xã</option>');
        $.each(data_phuong.wards, function(key_phuong, val_phuong) {
            $("#phuong").append('<option value="' + val_phuong.code + '">' + val_phuong.name + '</option>');
        });
    });
}

$(document).ready(function() {
    // Initialize by loading provinces
    loadProvinces();

    // Bind change event for province dropdown
    $("#tinh").change(function() {
        var provinceId = $(this).val();
        if (provinceId !== "0") {
            loadDistricts(provinceId);
        }
        $("#quan").empty().append('<option value="0">Quận Huyện</option>');
        $("#phuong").empty().append('<option value="0">Phường Xã</option>');
    });

    // Bind change event for district dropdown
    $("#quan").change(function() {
        var districtId = $(this).val();
        if (districtId !== "0") {
            loadWards(districtId);
        }
        $("#phuong").empty().append('<option value="0">Phường Xã</option>');
    });
});

//function getSelectedNames() {
//    // Lấy phần tử đã chọn trong mỗi <select>
//    var tinhName = $("#tinh option:selected").text();
//    var quanName = $("#quan option:selected").text();
//    var phuongName = $("#phuong option:selected").text();
//    
//    // Hiển thị tên đã chọn
////    alert("Tỉnh: " + tinhName + "\nQuận: " + quanName + "\nPhường: " + phuongName);
//    document.getElementById()
//}

 function setSelectedTinhByName(tinhName, callback) {
    $("#tinh option").filter(function() {
        return $(this).text().trim() === tinhName.trim();
    }).prop('selected', true);
    $("#tinh").trigger("change"); // Kích hoạt change để tải quận
    
    // Chờ một chút để quận được tải
    setTimeout(callback, 500); // Gọi callback sau khi quận tải xong (tăng/giảm thời gian nếu cần)
}

function setSelectedQuanByName(quanName, callback) {
    $("#quan option").filter(function() {
        return $(this).text().trim() === quanName.trim();
    }).prop('selected', true);
    $("#quan").trigger("change"); // Kích hoạt change để tải phường
    
    // Chờ phường được tải
    setTimeout(callback, 500); // Gọi callback sau khi phường tải xong
}

function setSelectedPhuongByName(phuongName) {
    $("#phuong option").filter(function() {
        return $(this).text().trim() === phuongName.trim();
    }).prop('selected', true);
}

function setLocationByNames(tinhName, quanName, phuongName) {
    setSelectedTinhByName(tinhName, function() {
        setSelectedQuanByName(quanName, function() {
            setSelectedPhuongByName(phuongName);
        });
    });
}
   
/*

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

 */