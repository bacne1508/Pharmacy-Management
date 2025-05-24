let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Username</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="usernameInput"  name="username" />
	        </div>
	        
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

    renderSelect();

	// Initial load
	fetchUsers(currentPage);

	$("#btnSearch").on('click', function(event) {
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

	$("#btnClear").on('click', function(event) {
		document.getElementById('usernameInput').value = '';
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
        fetch('/api/auth/user/edit', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(editForm)
	    })
	    .then(res => res.json())
	    .then(data => {
	        if (data.success) {
	            alert("Modification successful!");
	            fetchUsers(currentPage);
	            $('#editUserModal').modal("hide");
	        } else {
	            alert("Error: " + data.message);
	        }
	    });

    });

    $('#add-submit-btn').click(function () {
        var form = getAddForm();
        if (!validateEditForm(form)) {
            return;
        }
        fetch('/api/auth/user/add', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(form)
	    })
	    .then(res => res.json())
	    .then(data => {
	        if (data.success) {
	            alert('Added new Account successfully!');
                    fetchUsers(currentPage);
                    $('#addRoleModal').modal("hide");
                    $('.modal-backdrop').remove();
	        } else {
	            alert("Error: " + data.message);
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
        username: $('#edit-usn-input').val(),
        fullName: $('#edit-full-name-input').val(),
        email: $('#edit-email-input').val(),
        phone: $('#edit-phone-input').val(),
        address: $('#edit-address-input').val()
    };
}

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const userName = document.getElementById('usernameInput').value.trim();
	const fullName = document.getElementById('fullNameInput').value.trim();
	const email = document.getElementById('emailInput').value.trim();
	const phone = document.getElementById('phoneInput').value.trim();

	let url = `/api/auth/user/all?page=${page}&size=${size}`;
	if (userName) {
		url += `&userName=${encodeURIComponent(userName)}`;
	}
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
	fetch(`/api/auth/user/all?page=${page}&size=${size}`)
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				renderRole(data.content.users);
				renderPagination(data.content.totalPages, data.content.currentPage);
			} else {
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
	for (let roleAcc of roles) {
		roleTableContent +=
			'<tr>' +
			'<td>' + safeValue(roleAcc.username) + '</td>' +
			'<td>' + safeValue(roleAcc.fullName) + '</td>' +
			'<td>' + safeValue(roleAcc.email) + '</td>' +
			'<td>' + safeValue(roleAcc.phone) + '</td>' +
			'<td>' + safeValue(roleAcc.address) + '</td>' +
			'<td>' + safeValue(roleAcc.isActive) + '</td>' +
			'<td>' + safeValue(roleAcc.createdBy) + '</td>' +
			'<td>' + formatDateStr(roleAcc.createdDate) + '</td>' +
			'<td>' + safeValue(roleAcc.updatedBy) + '</td>' +
			'<td>' + formatDateStr(roleAcc.updatedDate) + '</td>' +
			'<td class="text-center min-wd-100">' + getEditBtn(roleAcc.fullName, roleAcc.username, roleAcc.id, roleAcc.email, roleAcc.role, roleAcc.phone, roleAcc.address) + '</td>' +
			'<td class="text-center min-wd-100">' + getDelBtn(roleAcc.id, roleAcc.auth) + '</td>' +
			'</tr>';
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
function getEditBtn(fullName, username, id, email, auth, phone, address) {
	if (auth == 'ADMIN') {
		return "<button class='btn btn-primary' disabled='disabled' onclick='editRole(\"" + id + "\",\"" + username + "\",\"" + safeValue(fullName) 
		+ "\",\"" + safeValue(email) + "\",\"" + safeValue(phone) + "\",\"" + safeValue(address) + "\")'>Edit</button>";
	} else {
		return "<button class='btn btn-primary' onclick='editRole(\"" + id + "\",\"" + username + "\",\"" + safeValue(fullName) + "\",\"" 
		+ safeValue(email) + "\",\"" + safeValue(phone) + "\",\"" + safeValue(address) + "\")'>Edit</button>";
	}
}

/**
 * Get the HTML of the edit button
 * @author Bac
 * @date 2025-5-20
 * @param id
 * @return
 */
function getDelBtn(id, auth) {
	if (auth == 1) {
		return "<button class='btn btn-danger' disabled='disabled' onclick='delRole(\"" + id + "\")'>Delete</button>";
	} else {
		return "<button class='btn btn-danger' onclick='delRole(\"" + id + "\")'>Delete</button>";
	}
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
		fetch(`/api/auth/user/delete?id=${id}`)
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
 * Render dropdown menu based on identity
 * @author Bac
 * @date 2025-5-21
 */
function renderSelect() {
    if (sessionStorage.getItem('auth') !== 1) {
        var managerContent = "<option selected='selected'>PATIENT</option>" +
            "<option>ADMIN</option>" +
            "<option>RECEPTION</option>" +
            "<option>DOCTOR</option>" +
            "<option>LAB_ASSISTANT</option>" +
            "<option>PHARMACY</option>";
        $('#edit-auth-input').html(managerContent);
        $('#add-auth-input').html(managerContent);
    }
}

/**
 * Modify character information
 * @param id
 * @param usn
 * @param pwd
 */
function editRole(id, usn, fullName, email, phone, address) {
    presentId = id;
    //Rendering the original information
    $('#edit-usn-input').val(usn);
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
        username: $('#edit-usn-input').val(),
        fullName: $('#edit-full-name-input').val(),
        email: $('#edit-email-input').val(),
        phone: $('#edit-phone-input').val(),
        address: $('#edit-address-input').val()
    };
}