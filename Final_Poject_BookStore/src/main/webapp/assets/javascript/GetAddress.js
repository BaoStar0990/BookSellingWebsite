// Function to load provinces (Tinh Thanh)
function loadProvinces() {
    $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function(data_tinh) {
        if (data_tinh.error === 0) {
            $("#tinh").empty().append('<option value="0">Tỉnh Thành</option>');
            $.each(data_tinh.data, function(key_tinh, val_tinh) {
                $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
            });
        }
    });
}

// Function to load districts (Quan Huyen) based on selected province
function loadDistricts(provinceId) {
    $.getJSON('https://esgoo.net/api-tinhthanh/2/' + provinceId + '.htm', function(data_quan) {
        if (data_quan.error === 0) {
            $("#quan").empty().append('<option value="0">Quận Huyện</option>');
            $.each(data_quan.data, function(key_quan, val_quan) {
                $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
            });
        }
    });
}

// Function to load wards (Phuong Xa) based on selected district
function loadWards(districtId) {
    $.getJSON('https://esgoo.net/api-tinhthanh/3/' + districtId + '.htm', function(data_phuong) {
        if (data_phuong.error === 0) {
            $("#phuong").empty().append('<option value="0">Phường Xã</option>');
            $.each(data_phuong.data, function(key_phuong, val_phuong) {
                $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
            });
        }
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