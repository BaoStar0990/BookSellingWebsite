<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.Comparator"%>
<%@page import="model.Address"%>
<%@page import="java.util.List"%>
<%@page import="model.Customer"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Naoki</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Poppins, Swiper, Bootstrap, Favicon, Fontawesome, and Custom CSS --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/css/splide.min.css" integrity="sha256-5uKiXEwbaQh9cgd2/5Vp6WmMnsUr3VZZw0a8rKnOKNU=" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@4.1.4/dist/js/splide.min.js" integrity="sha256-FZsW7H2V5X9TGinSjjwYJ419Xka27I8XPDmWryGlWtw=" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/logos/square-logo.png" type="image/x-icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/order.css" />
</head>
<body>
<%-- Header --%>
<jsp:include page="WEB-INF/views/header.jsp">
    <jsp:param name="currentTab" value="" />
</jsp:include>
<%-- end Header --%>

<%-- Order Form --%>
    <div class="container mt-4 fw-medium">
        <div class="row">
            <!-- Address Form -->
            <div class="col-lg-6 mb-3">
                <div class="card p-3">
                    <h4 class="fw-semibold mb-3">Thông tin nhận hàng</h4>
                    <div class="mb-3">
                        <label class="mb-1">Sổ địa chỉ</label>
                        <select class="form-select" id ="addressBook" name="addressBook" onchange="onChangeAddress()">
                            <c:forEach var="address" items="${addresses}">
                                <option value='<c:out value="${address.createJson()}"/>'>${address.toString()}</option>
                            </c:forEach> 
                           <option value="otherAddress">Địa chỉ khác...</option>
                        </select>                      
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Email</label>
                        <input type="email" value = "${sessionScope.user.getEmail()}" name="email"
                               class="form-control" required readonly />
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Họ và tên</label>
                        <input type="text" id ="fullName" name="fullName" class="form-control" required  />
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Số điện thoại (tùy chọn)</label>
                        <input type="tel" id ="phone" name="phone" class="form-control" required />
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Số nhà</label>
                        <input type="text" id ="street" name="street" class="form-control" />
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Tỉnh thành</label>
                        <select class="form-select" id="tinh" name="province" title="Chọn Tỉnh Thành">
                            <option selected value="0">Tỉnh Thành</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Quận huyện (tùy chọn)</label>
                        <select class="form-select" id="quan" name="district" title="Chọn Quận Huyện">
                            <option selected value="0">Quận Huyện</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Phường xã (tùy chọn)</label>
                        <select class="form-select" id="phuong" name="ward" title="Chọn Phường Xã">
                            <option selected value="0">Phường Xã</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="mb-1">Ghi chú (tùy chọn)</label>
                        <textarea name="note" id ="note" class="form-control"></textarea>
                    </div>
                </div>
            </div>

            <!-- Shipping, Payment, and Order Summary -->
            <div class="col-lg-6 mb-3">
                <div class="card p-3 mb-3">
                    <h4 class="fw-semibold mb-3">Vận chuyển</h4>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="shippingMethod" id="homeDelivery" value="homeDelivery" checked />
                        <label class="form-check-label" for="homeDelivery">
                            Giao hàng tận nơi - 40.000₫
                        </label>
                    </div>
                </div>

                <div class="card p-3 mb-3">
                    <h4 class="fw-semibold mb-3">Thanh toán</h4>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod" id="cod" value="cod" checked />
                        <label class="form-check-label" for="cod">
                            Thanh toán khi giao hàng (COD)
                        </label>
                    </div>
                </div>
                
                <div class="card p-3">
                    <h4 class="fw-semibold mb-3">Đơn hàng (${listOrderDetails.size()} sản phẩm)</h4>
                    <div class="border mb-2" style="max-height: 160px; overflow-y: auto;">
                        <ul class="list-group list-group-flush mb-3">
                            <!-- tạo biến total cost -->
                            <c:set var="totalCost" value="0.0" />
                            <c:if test = "${not empty listOrderDetails}">
                                <c:forEach var = "orderDetail" items="${listOrderDetails}"> 
                                    <c:set var="totalCost" value="${totalCost + orderDetail.getBook().sellingPrice*(1 - orderDetail.getBook()
                                                           .getDiscountCampaign().getPercentDiscount()) * orderDetail.getQuantity()}"/>
                                    <li class="list-group-item d-flex align-items-start">
                                        <img src="${orderDetail.getBook().getUrlImage()}" alt="book" style="width: 50px; height: 50px; margin-right: 10px;" />
                                        <div class="flex-grow-1 pe-4">
                                            <h6>${orderDetail.getBook().getTitle()}</h6>
                                            <p class="mb-0"><fmt:formatNumber value="${orderDetail.getBook().sellingPrice*(1 - orderDetail.getBook()
                                                                   .getDiscountCampaign().getPercentDiscount())}" 
                                                              type="number" pattern="#,##0" />đ x ${orderDetail.getQuantity()}</p>
                                        </div>
                                        <span class="fw-semibold">
                                                <fmt:formatNumber value="${orderDetail.getBook().sellingPrice*(1 - orderDetail.getBook()
                                                                   .getDiscountCampaign().getPercentDiscount()) * orderDetail.getQuantity()}" type="number" pattern="#,##0" />đ
                                            </span>
                                    </li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
<%--                    <div class="mb-3">--%>
<%--                        <label class="mb-1">Nhập mã giảm giá</label>--%>
<%--                        <div class="input-group">--%>
<%--                            <input type="text" name="discountCode" class="form-control" placeholder="Mã giảm giá" />--%>
<%--                            <button class="primary-btn">Áp dụng</button>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Tạm tính</span>
                        <span><fmt:formatNumber value="${totalCost}" type="number" pattern="#,##0" />đ</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Phí vận chuyển</span>
                        <c:set var="shippingFee" value="40000" />
                        <span><fmt:formatNumber value="${shippingFee}" type="number" pattern="#,##0" />đ</span>
                    </div>
                    <div class="d-flex justify-content-between fw-semibold fs-5 text-primary">
                        <span>Tổng cộng</span>
                        <span><fmt:formatNumber value="${totalCost + shippingFee}" type="number" pattern="#,##0" />đ</span>
                    </div>
                </div>
                    
                <form id ="formDatHang" action="${pageContext.request.contextPath}/order" method = "post" 
                      class="text-decoration-none"  onsubmit="updateAddressSelected()">
                    <input type="hidden" name ="name" id ="name">
                    <input type="hidden" name ="phonenumber" id ="phonenumber">
                    <input type="hidden" value = "" name ="addressSelected" id ="addressSelected">
                    <%--<input type="hidden" value ="${cartId}" name ="cartId">--%>
                    <input type="hidden" name ="payment" id ="payment">
                    <input type="hidden" name ="notes" id ="notes">
                    <input type="hidden" name ="shipping" id ="shipping">
                    
                    <button class="primary-btn mt-3 w-100" onclick="submitForms()">
                        ĐẶT HÀNG
                    </button>
                </form>

                <div class="text-center mt-3">
                    <a href="/viewcart" class="text-decoration-none text-primary fw-semibold">Quay về giỏ hàng</a>
                </div>
            </div>
        </div>
    </div>
<%-- End Order Form --%>

<%-- Footer --%>
<jsp:include page="WEB-INF/views/footer.jsp" />
<%-- end Footer --%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascript/GetAddress.js"></script>
<script>
    window.onload = function() {
        onChangeAddress();
    };
    function onChangeAddress(){
        var selectedValue = document.getElementById("addressBook").value;
//            alert(selectedValue.toString());
        if(!(selectedValue.toString() === "otherAddress")){    
            try {
                var address = JSON.parse(selectedValue.toString()); // Chuyển từ JSON sang đối tượng

                // set các giá trị
                document.getElementById("fullName").value = address.fullName;
                document.getElementById("phone").value = address.phonenumber;
                document.getElementById("street").value = address.street;
                setLocationByNames(address.province, address.district, address.ward);

            } catch (e) {
                alert("Không thể chuyển đổi giá trị thành JSON: " + e);
            }
        }
        else {
            document.getElementById("street").value = "";
            setLocationByNames("Tỉnh Thành", "Quận Huyện", "Phường Xã");
        }
         
    }   
    function updateAddressSelected(){
        var selectElement = document.getElementById("addressBook");
        var selectedText = "";
        if(!(selectElement.value.toString() === "otherAddress")){   
            // Lấy phần tử <select> theo ID
           var selectElement = document.getElementById("addressBook");
           // Lấy <option> được chọn
           var selectedOption = selectElement.options[selectElement.selectedIndex];
           // Lấy giá trị hiển thị (text) của <option> đã chọn
           selectedText = selectedOption.text;
   //            alert(selectedText);
        }
        else{
            // lấy số nhà
            var soNha = document.getElementById("street").value.toString().trim();
            // Lấy phần tử đã chọn trong mỗi <select>
            var tinhName = $("#tinh option:selected").text();
            var quanName = $("#quan option:selected").text();
            var phuongName = $("#phuong option:selected").text();
            
            selectedText =  soNha + ", " + phuongName + ", " + quanName + ", " + tinhName;
        }
//        alert(selectedText);
        document.getElementById("addressSelected").value = selectedText;
    }
    function submitForms() {
        document.getElementById("name").value = document.getElementById("fullName").value.replace(/<\/?[^>]+(>|$)/g, "");
        document.getElementById("phonenumber").value = document.getElementById("phone").value.replace(/<\/?[^>]+(>|$)/g, "");
        document.getElementById("shipping").value = document.querySelector('input[name="shippingMethod"]:checked').value;
        document.getElementById("payment").value = document.querySelector('input[name="paymentMethod"]:checked').value;
        document.getElementById("notes").value = document.getElementById("note").value.replace(/<\/?[^>]+(>|$)/g, "");
        // Submit formDatHang
        document.getElementById('formDatHang').submit();
    }
</script>
</body>
</html>
