function goBack() {
    window.history.back();
}

function getBillContent() {
    return `
        <html>
        <head>
            <title>Hóa đơn</title>
            <style>
                .container { width: 100%; padding: 20px; }
                .text-danger { color: red; }
            </style>
        </head>
        <body>
            <div>
                <img src="${pageContext.request.contextPath}/assets/images/logos/logo-1.png" alt="Naoki Logo" style="width: 100px; margin-bottom: 16px;">
                <p>Địa chỉ công ty: Số 1, Võ Văn Ngân, Quận Thủ Đức, Tp. Hồ Chí Minh</p>
                <p>Số điện thoại: 0912345678</p>
                <p>Email: support@naoki.com</p>
                <p>Mã số thuế: 12345</p>
                <hr>
                <h3>Hóa đơn</h3>
                <p><strong>Số hóa đơn:</strong> ${bill.getId()}</p>
                <p><strong>Ngày đặt hàng:</strong> ${bill.getOrderDate()}</p>
                <p><strong>Ngày giao hàng:</strong> ${bill.getDeliveryDate()}</p>
                <p><strong>Khách hàng:</strong> ${bill.getCustomer().getFullName()}</p>
                <p><strong>Người nhận hàng:</strong> <span>${bill.getRecipientName()}</span></p>
                <p><strong>Sđt người nhận hàng:</strong> <span>${bill.getRecipientPhone()}</span></p>
                <p><strong>Địa chỉ giao hàng:</strong> <span>${bill.getRecipientAddress()}</span></p>
                <p><strong>Phương thức thanh toán:</strong> ${bill.getPaymentMethod()}</p>
                <p><strong>Tình trạng thanh toán:</strong> ${bill.getStatusPayment()}</p>
                <hr>
                <h4>Sách trong đơn hàng</h4>
                <table border="1" width="100%">
                    <thead>
                        <tr>
                            <th>Tên sách</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="detail" items="${bill.getOrderDetails()}">
                            <tr>
                                <td>${detail.getBook().getTitle()}</td>
                                <td>${detail.getQuantity()}</td>
                                <td>${detail.getUnitPrice()}</td>
                                <td>${detail.getTotalPrice()}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <hr>
                <p><strong>Tạm tính:</strong> ${bill.getSubtotal()}</p>
                <p><strong>Thuế GTGT:</strong> ${bill.getVAT() * 100}%</p>
                <p><strong>Phí vận chuyển:</strong> ${bill.getShippingFee()}</p>
                <p class="fs-5"><strong class="text-danger fw-bold">Tổng số tiền:</strong> <span class="text-danger fw-bold">${bill.getGrandTotal()}</span></p>
                <hr>
                <h4>Điều khoản & Điều kiện</h4>
                <a href="https://www.naoki.com/terms_and_conditions">https://www.naoki.com/terms_and_conditions</a>
            </div>
        </body>
        </html>
    `;
}

function printBill() {
    const printContent = getBillContent();
    const originalContent = document.body.innerHTML;

    document.body.innerHTML = printContent;
    window.print();
    document.body.innerHTML = originalContent;
}

function downloadBill() {
    const billContent = getBillContent();
    const opt = {
        margin: 1,
        filename: `bill_${bill.getId()}.pdf`,
        image: { type: 'jpeg', quality: 0.98 },
        html2canvas: { scale: 2 },
        jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
    };
    html2pdf().set(opt).from(billContent).save();
}

document.getElementById('statusButton').addEventListener('click', function() {
    const statusOrder = "${bill.getStatusOrder()}";
    let message = '';

    switch (statusOrder) {
        case 'Processing':
            message = 'Xác nhận đóng hàng';
            break;
        case 'Packing':
            message = 'Xác nhận giao hàng';
            break;
        case 'Delivering':
            message = 'Xác nhận đã giao hàng';
            break;
        case 'Delivered':
            message = 'Xác nhận hoàn thành';
            break;
        case 'Cancelled':
            message = 'Xác nhận xử lý đơn hàng';
            break;
        default:
            message = 'Xác nhận hành động';
    }

    document.getElementById('confirmMessage').textContent = message;
    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    document.getElementById('confirmButton').addEventListener('click', function() {
        document.getElementById('statusForm').submit();
    });
});

document.getElementById('cancelButton').addEventListener('click', function() {
    document.getElementById('confirmMessage').textContent = 'Xác nhận hủy đơn hàng';
    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    document.getElementById('confirmButton').addEventListener('click', function() {
        document.getElementById('cancelForm').submit();
    });
});