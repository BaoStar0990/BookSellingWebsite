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

// Create Tag Function
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

// Add Event Listener for Author and Category Checkboxes
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

// Add Event Listener for Author and Category Checkboxes
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

// Search Author and Category
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

// Search Author and Category
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

// Add Event Listener for Author and Category Checkboxes
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
    document.getElementById('urlImage').required = true;
    document.getElementById('addEditBookForm').action = '/ms/msbook?action=add';
    document.getElementById('addEditBookModalLabel').textContent = 'Thêm Sách';
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

// When the modal is hidden, reset the form
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
    document.getElementById('urlImage').required = true;
    document.getElementById('addEditBookForm').action = '/ms/msbook?action=add';
    document.getElementById('addEditBookModalLabel').textContent = 'Thêm Sách';
});

// When the modal is shown, set the form values
function editBook(button) {
    const bookId = button.getAttribute('data-book-id');
    const bookTitle = button.getAttribute('data-book-title');
    const costPrice = button.getAttribute('data-book-cost-price');
    const sellingPrice = button.getAttribute('data-book-selling-price');
    const stocks = button.getAttribute('data-book-stocks');
    const urlImage = button.getAttribute('data-book-url-image');
    const description = button.getAttribute('data-book-description');
    const publisher = button.getAttribute('data-book-publisher');
    const publishYear = button.getAttribute('data-book-publish-year');
    const language = button.getAttribute('data-book-language');
    const discountCampaign = button.getAttribute('data-book-discount-campaign');
    const isbn = button.getAttribute('data-book-isbn');

    document.getElementById('bookId').value = bookId;
    document.getElementById('bookTitle').value = bookTitle;
    document.getElementById('costPrice').value = costPrice;
    document.getElementById('sellingPrice').value = sellingPrice;
    document.getElementById('stocks').value = stocks;
    document.getElementById('description').value = description;
    document.getElementById('publisher').value = publisher;
    document.getElementById('publishYear').value = publishYear;
    document.getElementById('language').value = language;
    document.getElementById('discountCampaign').value = discountCampaign;
    document.getElementById('isbn').value = isbn;

    // Set selected authors
    const selectedAuthors = button.getAttribute('data-book-authors');
    if (selectedAuthors) {
        selectedAuthors.split(',').forEach(function(authorId) {
            const authorCheckbox = document.getElementById('author' + authorId);
            if (authorCheckbox) {
                authorCheckbox.checked = true;
                createTag(authorCheckbox.id, authorCheckbox.nextElementSibling.textContent, 'selectedAuthors');
            }
        });
    }

    // Set selected categories
    const selectedCategories = button.getAttribute('data-book-categories');
    if (selectedCategories) {
        selectedCategories.split(',').forEach(function(categoryId) {
            const categoryCheckbox = document.getElementById('category' + categoryId);
            if (categoryCheckbox) {
                categoryCheckbox.checked = true;
                createTag(categoryCheckbox.id, categoryCheckbox.nextElementSibling.textContent, 'selectedCategories');
            }
        });
    }

    if (urlImage) {
        document.getElementById('imagePreview').src = urlImage;
        document.getElementById('imagePreview').style.display = 'block';
        document.getElementById('urlImage').required = false;
    } else {
        document.getElementById('imagePreview').style.display = 'none';
        document.getElementById('urlImage').required = true;
    }

    document.getElementById('addEditBookForm').action = '/ms/msbook?action=edit';
    document.getElementById('addEditBookModalLabel').textContent = 'Chỉnh sửa Sách';
}