window.onload = function() {
    setTimeout(() => {
        // Ẩn màn hình loading
        document.getElementById('loading-screen').style.display = 'none';
        // Hiển thị nội dung chính
        document.getElementById('container').style.display = 'block';
    }, 0); // Không cần delay trong trường hợp tải trang hoàn tất
};



