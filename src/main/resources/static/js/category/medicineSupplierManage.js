let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Full Name</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="fullNameInput"  name="fullName" />
	        </div>
	    </div>
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Email</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="emailInput"  name="email" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Phone</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="phoneInput"  name="phone" />
	        </div>
	    </div>
	`;
	searchContainer.innerHTML = searchHtml;

	// Initial load
	fetchUsers(currentPage);

	$("#btnSearch").on('click', function(event) {
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

	$("#btnClear").on('click', function(event) {
		document.getElementById('fullNameInput').value = '';
		document.getElementById('emailInput').value = '';
		document.getElementById('phoneInput').value = '';
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

    $('#edit-submit-btn').click(function () {
        var editForm = getEditForm(presentId);
        /*if (!validateEditForm(editForm)) {
            return;
        }*/
        fetch('/api/auth/medicine/supplier/edit', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(editForm)
	    })
	    .then(res => res.json())
	    .then(data => {
	        if (data.content.success) {
	            alert("Modification successful!");
	            fetchUsers(currentPage);
	            $('#editUserModal').modal("hide");
	        } else {
	            alert("Error: " + data.content.message);
	        }
	    });

    });

    $('#add-submit-btn').click(function () {
        var form = getAddForm();
        if (!validateEditForm(form)) {
            return;
        }
        fetch('/api/auth/medicine/supplier/add', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(form)
	    })
	    .then(res => res.json())
	    .then(data => {
	        if (data.content.success) {
	            alert('Added new Account successfully!');
                    fetchUsers(currentPage);
                    $('#addRoleModal').modal("hide");
                    $('.modal-backdrop').remove();
	        } else {
	            alert("Error: " + data.content.message);
	        }
	    });
    });
});

/**
 * Get the editor information form
 * @author moon
 * @date 2025-5-20
 * @return
 */
function getEditForm(id) {
    return {
        id: id,
        fullName: $('#edit-full-name-input').val(),
        email: $('#edit-email-input').val(),
        phone: $('#edit-phone-input').val(),
        address: $('#edit-address-input').val()
    };
}

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const fullName = document.getElementById('fullNameInput').value.trim();
	const email = document.getElementById('emailInput').value.trim();
	const phone = document.getElementById('phoneInput').value.trim();

	let url = `/api/auth/medicine/supplier/all?page=${page}&size=${size}`;
	if (fullName) {
		url += `&fullName=${encodeURIComponent(fullName)}`;
	}
	if (email) {
		url += `&email=${encodeURIComponent(email)}`;
	}
	if (phone) {
		url += `&phone=${encodeURIComponent(phone)}`;
	}

	fetch(url)
		.then(response => response.json())
		.then(data => {
			if (data.success) {
				renderRole(data.content.users); // dữ liệu page nằm trong content.content
				renderPagination(data.content.totalPages, page);
			} else {
				renderRole(data);
				alert('Lỗi khi tải dữ liệu: ' + data.message);
			}
		})
		.catch(error => {
			renderRole(data);
			console.error('Lỗi khi gọi API:', error);
		});
}

function fetchUsers(page) {
	fetch(`/api/auth/medicine/supplier/all?page=${page}&size=${size}`)
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				renderRole(data.content.users);
				renderPagination(data.content.totalPages, data.content.currentPage);
			} else {
				renderRole(data);
				alert("Error: " + data.message);
			}
		});
}

function renderPagination(totalPages, current) {
	const pagination = document.getElementById("pagination");
	pagination.innerHTML = "";

	const maxVisiblePages = 5;
	let start = Math.max(current - Math.floor(maxVisiblePages / 2), 0);
	let end = start + maxVisiblePages;

	if (end > totalPages) {
		end = totalPages;
		start = Math.max(end - maxVisiblePages, 0);
	}

	// First page
	if (start > 0) {
		pagination.innerHTML += `
            <li class="page-item">
                <a class="page-link" href="#" onclick="fetchUsers(0)">First</a>
            </li>
        `;
		pagination.innerHTML += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
	}

	// Page numbers
	for (let i = start; i < end; i++) {
		pagination.innerHTML += `
            <li class="page-item ${i === current ? 'active' : ''}">
                <a class="page-link" href="#" onclick="fetchUsers(${i})">${i + 1}</a>
            </li>
        `;
	}

	// Last page
	if (end < totalPages) {
		pagination.innerHTML += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
		pagination.innerHTML += `
            <li class="page-item">
                <a class="page-link" href="#" onclick="fetchUsers(${totalPages - 1})">Last</a>
            </li>
        `;
	}
}



/**
 * Rendering the Cinema Character Table
 * @author moon
 * @date 2025-5-20
 * @param roles
 */
function renderRole(roles) {
	var roleTableContent = '';
	if(roles.message !== "No data"){
		for (let roleAcc of roles) {
			roleTableContent +=
				'<tr>' +
				'<td class="text-center">' + safeValue(roleAcc.fullName) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.email) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.phone) + '</td>' +
				'<td>' + safeValue(roleAcc.address) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.createdBy) + '</td>' +
				'<td class="text-center">' + formatDateStr(roleAcc.createdDate) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.updatedBy) + '</td>' +
				'<td class="text-center">' + formatDateStr(roleAcc.updatedDate) + '</td>' +
				'<td class="text-center min-wd-100">' + getEditBtn(roleAcc.fullName, roleAcc.id, roleAcc.email, roleAcc.phone, roleAcc.address) + '</td>' +
				'<td class="text-center min-wd-100">' + getDelBtn(roleAcc.id) + '</td>' +
				'</tr>';
		}
	}
	$('#user-table').html(roleTableContent);
}

/**
 * Get the HTML of the edit button
 * @author Bac
 * @date 2025-5-20
 * @param password
 * @param id
 * @return
 */
function getEditBtn(fullName, id, email, phone, address) {
	return "<button class='btn btn-primary' onclick='editRole(\"" + id  + "\",\"" + safeValue(fullName) + "\",\"" 
		+ safeValue(email) + "\",\"" + safeValue(phone) + "\",\"" + safeValue(address) + "\")'>Edit</button>";
}

/**
 * Get the HTML of the edit button
 * @author Bac
 * @date 2025-5-20
 * @param id
 * @return
 */
function getDelBtn(id) {
	return "<button class='btn btn-danger' onclick='delRole(\"" + id + "\")'>Delete</button>";
}

/**
 * Deleting a Role
 * @author Bac
 * @date 2025-5-20
 * @param id
 */
function delRole(id) {
    $('#roleDelModal').modal("toggle");
         // Confirm delete
    $('#commitRoleDel').off('click').on('click', function () {
		fetch(`/api/auth/medicine/supplier/delete?id=${id}`)
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				alert("Deleted successfully!");
				fetchUsers(currentPage);
				$('#roleDelModal').modal("hide");
			} else {
				alert("Error: " + data.message);
			}
		});
    });
}

/**
 * Modify character information
 * @param id
 * @param usn
 * @param pwd
 */
function editRole(id, fullName, email, phone, address) {
    presentId = id;
    //Rendering the original information
    $('#edit-full-name-input').val(fullName);
    $('#edit-email-input').val(email);
    $('#edit-phone-input').val(phone);
    $('#edit-address-input').val(address);
    $('#editUserModal').modal("toggle");
}

/**
 * Inspection and editing information form
 * @author Bac
 * @date 2025-5-20
 * @param form
 */
function validateEditForm(form) {
    if (!form.fullName) {
        alert("Please enter the full name!");
        return false;
    }
    if (!form.email) {
        alert("Please enter the email!");
        return false;
    }
    if (!form.phone) {
        alert("Please enter the phone!");
        return false;
    }
    if (!form.address) {
        alert("Please enter the address!");
        return false;
    }
    return true;
}

/**
 * Get the add information form
 * @author Bac
 * @date 2025-5-21
 * @return
 */
function getAddForm() {
    return {
        id: 0,
        fullName: $('#add-full-name-input').val(),
        email: $('#add-email-input').val(),
        phone: $('#add-phone-input').val(),
        address: $('#add-address-input').val()
    };
}