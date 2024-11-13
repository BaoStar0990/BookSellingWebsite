document.getElementById('searchBox').addEventListener('input', function() {
    let filter = this.value.toUpperCase();
    let rows = document.querySelector("tbody").rows;
    for (let i = 0; i < rows.length; i++) {
        let firstCol = rows[i].cells[1].textContent.toUpperCase();
        let secondCol = rows[i].cells[2].textContent.toUpperCase();
        if (firstCol.indexOf(filter) > -1 || secondCol.indexOf(filter) > -1) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
});

function createTag(value, text, containerId) {
    const container = document.getElementById(containerId);
    const tag = document.createElement('span');
    tag.className = 'badge background-primary mb-2 me-2';
    tag.textContent = text;
    tag.dataset.id = value;
    const closeButton = document.createElement('button');
    closeButton.type = 'button';
    closeButton.className = 'btn-close btn-close-white ms-2';
    closeButton.ariaLabel = 'Close';
    closeButton.addEventListener('click', function() {
        document.getElementById(value).checked = false;
        container.removeChild(tag);
    });
    tag.appendChild(closeButton);
    container.appendChild(tag);
}

document.querySelectorAll('.author-checkbox').forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        if (this.checked) {
            createTag(this.id, this.nextElementSibling.textContent, 'selectedAuthors');
        } else {
            const tag = document.querySelector(`#selectedAuthors span[data-id="${this.id}"]`);
            if (tag) {
                tag.remove();
            }
        }
    });
});

document.querySelectorAll('.category-checkbox').forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        if (this.checked) {
            createTag(this.id, this.nextElementSibling.textContent, 'selectedCategories');
        } else {
            const tag = document.querySelector(`#selectedCategories span[data-id="${this.id}"]`);
            if (tag) {
                tag.remove();
            }
        }
    });
});

document.getElementById('authorSearch').addEventListener('input', function() {
    let filter = this.value.toUpperCase();
    let checkboxes = document.querySelectorAll('#bookAuthors .form-check');
    checkboxes.forEach(function(checkbox) {
        let label = checkbox.querySelector('label').textContent.toUpperCase();
        if (label.indexOf(filter) > -1) {
            checkbox.style.display = '';
        } else {
            checkbox.style.display = 'none';
        }
    });
});

document.getElementById('categorySearch').addEventListener('input', function() {
    let filter = this.value.toUpperCase();
    let checkboxes = document.querySelectorAll('#bookCategories .form-check');
    checkboxes.forEach(function(checkbox) {
        let label = checkbox.querySelector('label').textContent.toUpperCase();
        if (label.indexOf(filter) > -1) {
            checkbox.style.display = '';
        } else {
            checkbox.style.display = 'none';
        }
    });
});

document.getElementById('addEditBookModal').addEventListener('hidden.bs.modal', function () {
    document.getElementById('addEditBookForm').reset();
    document.getElementById('bookId').value = '';
    document.getElementById('selectedAuthors').innerHTML = '';
    document.getElementById('selectedCategories').innerHTML = '';
    document.querySelectorAll('.author-checkbox').forEach(function(checkbox) {
        checkbox.checked = false;
    });
    document.querySelectorAll('.category-checkbox').forEach(function(checkbox) {
        checkbox.checked = false;
    });
    document.getElementById('imagePreview').style.display = 'none';
});

document.getElementById('addEditBookForm').addEventListener('submit', function(event) {
    const selectedAuthors = Array.from(document.querySelectorAll('.author-checkbox:checked')).map(cb => cb.value).join(',');
    const selectedCategories = Array.from(document.querySelectorAll('.category-checkbox:checked')).map(cb => cb.value).join(',');
    document.getElementById('selectedAuthorsInput').value = selectedAuthors;
    document.getElementById('selectedCategoriesInput').value = selectedCategories;
});

// Image Preview Function
function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();
    reader.onload = function() {
        const dataURL = reader.result;
        const imagePreview = document.getElementById('imagePreview');
        imagePreview.src = dataURL;
        imagePreview.style.display = 'block';
    };
    reader.readAsDataURL(input.files[0]);
}

function editBook(button) {
    const bookId = button.getAttribute('data-book-id');
    const bookTitle = button.getAttribute('data-book-title');
    const bookAuthors = JSON.parse(button.getAttribute('data-book-authors'));
    const bookCategories = JSON.parse(button.getAttribute('data-book-categories'));
    const bookCostPrice = button.getAttribute('data-book-cost-price');
    const bookSellingPrice = button.getAttribute('data-book-selling-price');
    const bookStocks = button.getAttribute('data-book-stocks');
    const bookUrlImage = button.getAttribute('data-book-url-image');
    const bookDescription = button.getAttribute('data-book-description');
    const bookPublisher = button.getAttribute('data-book-publisher');
    const bookPublishYear = button.getAttribute('data-book-publish-year');
    const bookLanguage = button.getAttribute('data-book-language');
    const bookDiscountCampaign = button.getAttribute('data-book-discount-campaign');
    const bookIsbn = button.getAttribute('data-book-isbn');

    document.getElementById('bookId').value = bookId;
    document.getElementById('bookTitle').value = bookTitle;
    document.getElementById('costPrice').value = bookCostPrice;
    document.getElementById('sellingPrice').value = bookSellingPrice;
    document.getElementById('stocks').value = bookStocks;
    document.getElementById('isbn').value = bookIsbn;
    document.getElementById('description').value = bookDescription;
    document.getElementById('publishYear').value = bookPublishYear;
    document.getElementById('language').value = bookLanguage;
    document.getElementById('publisher').value = bookPublisher;
    document.getElementById('discountCampaign').value = bookDiscountCampaign;

    // Set authors
    document.getElementById('selectedAuthors').innerHTML = '';
    bookAuthors.forEach(author => {
        document.getElementById(`author${author.id}`).checked = true;
        createTag(`author${author.id}`, author.name, 'selectedAuthors');
    });

    // Set categories
    document.getElementById('selectedCategories').innerHTML = '';
    bookCategories.forEach(category => {
        document.getElementById(`category${category.id}`).checked = true;
        createTag(`category${category.id}`, category.name, 'selectedCategories');
    });

    // Set image preview
    if (bookUrlImage) {
        const imagePreview = document.getElementById('imagePreview');
        imagePreview.src = bookUrlImage;
        imagePreview.style.display = 'block';
    }

    // Set form action to edit
    document.getElementById('addEditBookForm').action = 'msbook?action=edit';
    document.getElementById('addEditBookModalLabel').textContent = 'Chỉnh sửa thông tin sách';
    document.getElementById('urlImage').required = false; // Make image optional when editing a book
}