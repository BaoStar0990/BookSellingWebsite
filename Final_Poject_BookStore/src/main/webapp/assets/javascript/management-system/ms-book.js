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
    if (document.querySelectorAll('.author-checkbox:checked').length === 0) {
        alert('Vui lòng chọn ít nhất một tác giả');
        event.preventDefault();
    }
    if (document.querySelectorAll('.category-checkbox:checked').length === 0) {
        alert('Vui lòng chọn ít nhất một loại sách');
        event.preventDefault();
    }

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