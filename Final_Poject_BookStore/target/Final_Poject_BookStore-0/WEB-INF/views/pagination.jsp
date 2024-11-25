<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="d-flex justify-content-center align-items-center mt-4 fs-5">
  <button
    class="btn btn-outline-secondary border-0 d-flex align-items-center"
    onclick="handlePreviousPage()"
    id="prevPageBtn"
    style="padding: 0.5rem 1rem;"
  >
    <i class="fa-solid fa-angle-left me-2"></i>
    <span>Trước</span>
  </button>
  
  <span class="mx-3 fw-semibold" id="paginationInfo">
    Trang <span id="currentPage">1</span> / <span id="totalPages">1</span>
  </span>
  
  <button
    class="btn btn-outline-secondary border-0 d-flex align-items-center"
    onclick="handleNextPage()"
    id="nextPageBtn"
    style="padding: 0.5rem 1rem;"
  >
    <span>Sau</span>
    <i class="fa-solid fa-angle-right ms-2"></i>
  </button>
</div>

<script>
  let currentPage = 1;
  const itemsPerPage = 18;
  const totalItems = document.querySelectorAll('.pagination-item').length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);

  function updatePagination() {
    document.getElementById('currentPage').innerText = currentPage;
    document.getElementById('totalPages').innerText = totalPages;
    document.getElementById('prevPageBtn').disabled = currentPage === 1;
    document.getElementById('nextPageBtn').disabled = currentPage === totalPages;

    const items = document.querySelectorAll('.pagination-item');
    items.forEach((item, index) => {
      item.style.display = (index >= (currentPage - 1) * itemsPerPage && index < currentPage * itemsPerPage) ? 'block' : 'none';
    });
  }

  function handlePreviousPage() {
    if (currentPage > 1) {
      currentPage--;
      updatePagination();
    }
  }

  function handleNextPage() {
    if (currentPage < totalPages) {
      currentPage++;
      updatePagination();
    }
  }

  // Initial pagination setup
  updatePagination();
</script>

<!-- Usage Instructions:
1. Include this pagination.jsp file in your main JSP file where you want the pagination controls to appear.
2. Ensure that the items you want to paginate have the class "pagination-item".
3. Adjust the itemsPerPage variable if needed.
4. The pagination controls will automatically update based on the number of items with the class "pagination-item".
-->
