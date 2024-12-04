<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // Map tabs to their titles, icons, and URLs
  Map<String, String[]> tabs = new LinkedHashMap<>();
  tabs.put("dashboard", new String[]{"Tổng quan", "fa-gauge", String.format("%s/ms/msdashboard", pageContext.getServletContext().getContextPath())});
  tabs.put("book", new String[]{"Sách", "fa-book", String.format("%s/ms/msbook", pageContext.getServletContext().getContextPath())});
  tabs.put("category", new String[]{"Danh mục sách", "fa-layer-group", String.format("%s/ms/mscategory", pageContext.getServletContext().getContextPath())});
  tabs.put("order", new String[]{"Đơn hàng", "fa-box",  String.format("%s/ms/msorder", pageContext.getServletContext().getContextPath())});
  tabs.put("customer", new String[]{"Khách hàng", "fa-user", String.format("%s/ms/mscustomer", pageContext.getServletContext().getContextPath())});
  tabs.put("campaign", new String[]{"Chiến dịch", "fa-tags", String.format("%s/ms/mscampaign", pageContext.getServletContext().getContextPath())});
  tabs.put("review", new String[]{"Đánh giá", "fa-message", String.format("%s/ms/msreview", pageContext.getServletContext().getContextPath())});
  tabs.put("author", new String[]{"Tác giả", "fa-square-pen", String.format("%s/ms/msauthor", pageContext.getServletContext().getContextPath())});
  tabs.put("publisher", new String[]{"Nhà xuất bản", "fa-print", String.format("%s/ms/mspublisher", pageContext.getServletContext().getContextPath())});
  tabs.put("staff", new String[]{"Nhân viên", "fa-clipboard-user", String.format("%s/ms/ms_staff", pageContext.getServletContext().getContextPath())});
  tabs.put("signout", new String[]{"Đăng xuất", "fa-right-from-bracket", String.format("%s/signout/manage/admin", pageContext.getServletContext().getContextPath())});

  String currentTab = request.getParameter("currentTab") != null ? request.getParameter("currentTab") : "dashboard";
  String tabTitle = tabs.containsKey(currentTab) ? tabs.get(currentTab)[0] : "Tổng quan";
%>
<script>
  const socket = new WebSocket(`${window.location.host}/websocket`);
        // Khi kết nối mở
        socket.onopen = () => {
            console.log("WebSocket connection established.");
            // Gửi heartbeat ping mỗi 5 giây
            setInterval(() => {
                if (socket.readyState === WebSocket.OPEN) {
                    socket.send(JSON.stringify({ type: "heartbeat", timestamp: Date.now() }));
                }
            }, 5000); // 5 giây
        };

        // Khi kết nối đóng
        socket.onclose = () => {
            console.log("WebSocket connection closed.");
        };
</script>
<%--<script>--%>
<%--  let isTabActive = true;--%>
<%--  function updateTabCount(increment) {--%>
<%--    const key = "tab-count";--%>
<%--    let count = parseInt(localStorage.getItem(key)) || 0;--%>
<%--    count += increment;--%>
<%--    localStorage.setItem(key,count);--%>
<%--  }--%>

<%--  // Khi tab được mở--%>
<%--  window.onload = () => {--%>
<%--    updateTabCount(1); // Tăng số lượng tab--%>
<%--  };--%>
<%--  window.onunload = () => {--%>
<%--    updateTabCount(-1);--%>
<%--    if (parseInt(localStorage.getItem("tab-count")) === 0) {--%>
<%--      fetch("/signout/manage/admin", { method: "POST" });--%>
<%--    }--%>
<%--  };--%>
<%--  window.addEventListener("storage", (event) => {--%>
<%--    if (event.key === "tab-count") {--%>
<%--      console.log("Tab count updated:", event.newValue);--%>
<%--    }--%>
<%--  });--%>
<%--</script>--%>
<%--<script>--%>
<%--let isNavigating = false;--%>

<%--// Danh sách các URL cần bỏ qua--%>
<%--const excludedUrls = [--%>
<%--'/ms/msbook', // Đường dẫn nội bộ--%>
<%--'/ms/mscategory',--%>
<%--'/ms/msdashboard',--%>
<%--'/ms/msorder',--%>
<%--'/ms/mscustomer',--%>
<%--'/ms/mscampaign',--%>
<%--'/ms/msreview',--%>
<%--'/ms/msauthor',--%>
<%--'/ms/mspublisher',--%>
<%--'/ms/ms_staff'--%>
<%--];--%>

<%--// Lắng nghe sự kiện chuyển hướng nội bộ--%>
<%--document.addEventListener('click', (event) => {--%>
<%--const target = event.target.closest('a'); // Phát hiện thẻ <a> gần nhất--%>
<%--  if (target && target.href) {--%>
<%--  const targetUrl = new URL(target.href, window.location.origin);--%>
<%--  const relativePath = targetUrl.pathname; // Lấy đường dẫn tương đối--%>

<%--  // Kiểm tra nếu URL thuộc danh sách được loại trừ--%>
<%--  if (excludedUrls.includes(relativePath) || excludedUrls.includes(target.href)) {--%>
<%--  isNavigating = true; // Đánh dấu là chuyển hướng hợp lệ--%>
<%--  }--%>
<%--  }--%>
<%--  });--%>

<%--  // Xử lý sự kiện trước khi rời khỏi trang--%>
<%--  window.addEventListener('beforeunload', (event) => {--%>
<%--  if (!isNavigating) {--%>
<%--  // Chỉ xử lý khi không phải các URL được loại trừ--%>
<%--  navigator.sendBeacon('/signout/manage/admin');--%>
<%--  console.log('Logout request sent as browser is being closed or navigated away');--%>
<%--  }--%>
<%--  });--%>
<%-- </script>--%>
<aside class="sidebar bg-dark text-light d-flex flex-column p-3" style="width: 250px; height: 100vh; position: fixed;">
  <div class="brand mb-3 text-center">
    <img src="${pageContext.request.contextPath}/assets/images/logos/logo-2.png" alt="logo" style="height: 40px;">
  </div>
  <ul class="nav flex-column fs-5">
    <% for (Map.Entry<String, String[]> entry : tabs.entrySet()) { %>
    <li class="nav-item">
      <a class="nav-link text-light <%= currentTab.equals(entry.getKey()) ? "active" : "" %>" href="<%= entry.getValue()[2] %>">
        <i class="fa-solid <%= entry.getValue()[1] %> me-2"></i> <%= entry.getValue()[0] %>
      </a>
    </li>
    <% } %>
  </ul>
</aside>
<%--<script>--%>
<%--  function callLogout(){--%>
<%--    console.log('callogout');--%>
<%--    navigator.sendBeacon('/signout/manage/admin');--%>
<%--    console.log('have called log out');--%>
<%--  }--%>
<%--  window.addEventListener('unload', (event) => {--%>
<%--    event.preventDefault();--%>
<%--    callLogout();--%>
<%--    console.log('beforeunload');--%>
<%--    event.returnValue = ''; // Bắt buộc để kích hoạt thông báo mặc địn--%>

<%--  });--%>
<%--</script>--%>

<nav class="topbar bg-light px-4 shadow-sm"
     style="margin-left: 250px; height: 60px; display: flex; align-items: center; justify-content: space-between;">
  <span class="fw-bold fs-4"><%= tabTitle %></span>
  <span class="fw-semibold fs-5">Administrator <i class="fa-solid fa-user ms-2"></i></span>
</nav>
