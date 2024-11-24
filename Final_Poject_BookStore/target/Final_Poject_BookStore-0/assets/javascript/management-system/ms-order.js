function goBack() {
    window.history.back();
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