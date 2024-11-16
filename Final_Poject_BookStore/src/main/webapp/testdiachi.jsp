<%-- 
    Document   : testdiachi.jsp
    Created on : Nov 12, 2024, 11:26:30 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <div class="css_select_div">
     <select class="css_select" id="tinh" name="tinh" title="Chọn Tỉnh Thành">
         <option value="0">Tỉnh Thành</option>
     </select> 
     <select class="css_select" id="quan" name="quan" title="Chọn Quận Huyện">
         <option value="0">Quận Huyện</option>
     </select> 
     <select class="css_select" id="phuong" name="phuong" title="Chọn Phường Xã">
         <option value="0">Phường Xã</option>
     </select>
<!--             <button onclick='setSelectedTinhByName("Thành phố Hồ Chí Minh")'>Xác nhận</button>
            <button onclick='setSelectedQuanByName("Thành phố Thủ Đức")'>Xác nhận</button>
            <button onclick='setSelectedPhuongByName("Phường Linh Trung")'>Xác nhận</button>-->
            <!--<button onclick='getSelectedNames()'>Xác nhận</button>-->
 <button onclick='setLocationByNames("Tỉnh Quảng Ngãi", "Huyện Nghĩa Hành", "Xã Hành Thịnh")'>Xác nhận</button>
         </div>
    </body>
</html>
<script src="https://esgoo.net/scripts/jquery.js"></script>
<style type="text/css">
    .css_select_div{ text-align: center;}
    .css_select{ display: inline-table; width: 25%; padding: 5px; margin: 5px 2%; border: solid 1px #686868; border-radius: 5px;}
</style>
<script>
    $(document).ready(function() {
    // Lấy tỉnh thành
    $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function(data_tinh) {
        if (data_tinh.error == 0) {
            // Điền danh sách tỉnh vào dropdown
            $.each(data_tinh.data, function(key_tinh, val_tinh) {
                $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
            });

            // Gọi hàm khi thay đổi tỉnh
            $("#tinh").change(function() {
                var idtinh = $(this).val();
                if (idtinh != "0") {
                    loadQuanHuyen(idtinh); // Tải quận theo tỉnh
                } else {
                    $("#quan").html('<option value="0">Quận Huyện</option>');
                    $("#phuong").html('<option value="0">Phường Xã</option>');
                }
            });

            // Tự động gọi change khi tỉnh đã được tải để lấy quận
            $("#tinh").trigger("change");
        }
    });

    // Hàm tải danh sách quận theo tỉnh
    function loadQuanHuyen(idtinh) {
        $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function(data_quan) {
            if (data_quan.error == 0) {
                $("#quan").html('<option value="0">Quận Huyện</option>'); // Reset quận
                $("#phuong").html('<option value="0">Phường Xã</option>'); // Reset phường

                // Điền danh sách quận vào dropdown
                $.each(data_quan.data, function(key_quan, val_quan) {
                    $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                });

                // Gọi lại khi chọn quận
                $("#quan").change(function() {
                    var idquan = $(this).val();
                    if (idquan != "0") {
                        loadPhuong(idquan); // Tải phường xã theo quận
                    } else {
                        $("#phuong").html('<option value="0">Phường Xã</option>');
                    }
                });

                // Tự động gọi change khi quận đã được tải để lấy phường
                $("#quan").trigger("change");
            }
        });
    }

    // Hàm tải danh sách phường xã theo quận
    function loadPhuong(idquan) {
        $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function(data_phuong) {
            if (data_phuong.error == 0) {
                $("#phuong").html('<option value="0">Phường Xã</option>'); // Reset phường
                $.each(data_phuong.data, function(key_phuong, val_phuong) {
                    $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
                });
            }
        });
    }
});

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

    
    
    
    function getSelectedNames() {
    // Lấy phần tử đã chọn trong mỗi <select>
    var tinhName = $("#tinh option:selected").text();
    var quanName = $("#quan option:selected").text();
    var phuongName = $("#phuong option:selected").text();
    
    // Hiển thị tên đã chọn
    alert("Tỉnh: " + tinhName + "\nQuận: " + quanName + "\nPhường: " + phuongName);
}

 </script>