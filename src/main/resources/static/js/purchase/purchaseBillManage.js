let currentPage = 0;
const size = 5;
let currentAction = null;
let currentId = null;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	renderSelect();
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Bill Code</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="billCodeInput"  name="billCode" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Bill Type</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="billTypeInput"  name="billType" />
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
		document.getElementById('billCodeInput').value = '';
		document.getElementById('billTypeInput').value = '';
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

    $('#edit-submit-btn').click(function () {
        var editForm = getEditForm(presentId);
        /*if (!validateEditForm(editForm)) {
            return;
        }*/
        fetch('/api/auth/purchase/bill/edit', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(editForm)
	    })
	    .then(res => res.json())
	    .then(data => {
	        if (data.success) {
				if(data.content.success){
		            alert("Modification successful!");
		            fetchUsers(currentPage);
		            $('#editUserModal').modal("hide");
                    $('.modal-backdrop').remove();
				}else{
					alert("Error: " + data.content.message);
				}
	        } else {
	            alert("Error: " + data.message);
	        }
	    });

    });

    $('#add-submit-btn').click(function () {
        var form = getAddForm();
        /*if (!validateEditForm(form)) {
            return;
        }*/
        fetch('/api/auth/purchase/bill/add', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(form)
	    })
	    .then(res => res.json())
	    .then(data => {
	        if (data.success) {
	            if(data.content.success){
					alert('Added new request successfully!');
                    fetchUsers(currentPage);
                    $('#addRoleModal').modal("hide");
                    $('.modal-backdrop').remove();
				}else{
					alert("Error: " + data.content.message);
				}
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
        status: $('#edit-status-input').val()
    };
}

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const billCode = document.getElementById('billCodeInput').value.trim();
	const billType = document.getElementById('billTypeInput').value.trim();

	let url = `/api/auth/purchase/bill/all?page=${page}&size=${size}`;
	if (billCode) {
		url += `&billCode=${encodeURIComponent(billCode)}`;
	}
	if (billType) {
		url += `&billType=${encodeURIComponent(billType)}`;
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
			console.error('Lỗi khi gọi API:', error);
		});
}

function fetchUsers(page) {
	fetch(`/api/auth/purchase/bill/all?page=${page}&size=${size}`)
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

function renderPaginationDetails(totalPages, current) {
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
                <a class="page-link" href="#" onclick="fetchUsersDetail(0)">First</a>
            </li>
        `;
		pagination.innerHTML += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
	}

	// Page numbers
	for (let i = start; i < end; i++) {
		pagination.innerHTML += `
            <li class="page-item ${i === current ? 'active' : ''}">
                <a class="page-link" href="#" onclick="fetchUsersDetail(${i})">${i + 1}</a>
            </li>
        `;
	}

	// Last page
	if (end < totalPages) {
		pagination.innerHTML += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
		pagination.innerHTML += `
            <li class="page-item">
                <a class="page-link" href="#" onclick="fetchUsersDetail(${totalPages - 1})">Last</a>
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
		for (let order of roles) {
			roleTableContent +=
				'<tr>' +
					'<td class="text-center">' + safeValue(order.billCode) + '</td>' +
					'<td class="text-center">' + safeValue(order.billType) + '</td>' +
					'<td class="text-center">' + safeValue(order.customerName) + '</td>' +
					'<td class="text-center">' + safeValue(order.employee) + '</td>' +
					'<td class="text-center">' + safeValue(order.totalAmount) + '</td>' +
					'<td class="text-center">' + safeValue(order.status) + '</td>' +
					'<td class="text-center">' + safeValue(order.createdFrom) + '</td>' +
					'<td class="text-center">' + safeValue(order.createdBy) + '</td>' +
					'<td class="text-center">' + formatDateStr(order.createdDate) + '</td>' +
					'<td class="text-center min-wd-100">' + editRole(order.id, order.status) + '</td>' +
				'</tr>';
		}
	}
	$('#user-table').html(roleTableContent);
}


/**
 * Rendering the Cinema Character Table
 * @author moon
 * @date 2025-5-20
 * @param roles
 */
function renderRoleDetails(users) {
    let roleTableContent = '';

    users.forEach(order => {
        roleTableContent +=
            '<tr>' +
                '<td class="text-center">' + safeValue(order.poCode) + '</td>' +
                '<td class="text-center">' + safeValue(order.poRequestId) + '</td>' +
				'<td class="text-center">' + safeValue(order.poRequestGroup) + '</td>' +
				'<td class="text-center">' + safeValue(order.medicineCode) + '</td>' +
				'<td class="text-center">' + safeValue(order.quantity) + '</td>' +
				'<td class="text-center">' + safeValue(order.unitPrice) + '</td>' +
				'<td class="text-center">' + safeValue(order.batchNo) + '</td>' +
                '<td class="text-center">' + formatDateStr(order.expiryDate) + '</td>' +
                '<td class="text-center">' + renderStatusBadge(order.status) + '</td>' +
				'<td class="text-center">' + safeValue(order.description) + '</td>' +
                '<td class="text-center">' + safeValue(order.createdBy) + '</td>' +
                '<td class="text-center">' + formatDateStr(order.createdDate) + '</td>' +
            '</tr>';
    });

    $('#user-table-modal').html(roleTableContent);
}


function fetchUsersDetail(page, poId) {
    fetch(`/api/auth/purchase/bill/allDetail?page=${page}&size=${size}&poId=${poId}`)
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                // Render dữ liệu vào tbody trong modal
                renderRoleDetails(data.content.users); // cần gắn đúng ID
                renderPaginationDetails(data.content.totalPages, data.content.currentPage);

                // Mở modal sau khi đã render xong
                const modal = new bootstrap.Modal(document.getElementById('poDetailModal'));
                modal.show();
            } else {
                alert("Error: " + data.message);
            }
        });
}


function viewDetail(poId) {
	fetchUsersDetail(currentPage, poId);
}

/**
 * Get the HTML of the edit button
 * @author Bac
 * @date 2025-5-20
 * @param password
 * @param id
 * @return
 */
function getEditBtn(id, status) {
	return `<button class='btn btn-sm btn-info btn-view'
                data-id="${id}"
                data-status="${safeValue(status)}"
                onclick='handleEditClick(this)'> <i class="fa fa-eye"></i></button>`;
}

function handleEditClick(btn) {
    const id = btn.dataset.id;
    const status = btn.dataset.status;

    editRole(id, status);
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
		fetch(`/api/auth/purchase/bill/delete?id=${id}`)
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
/*function editRole(id, username, medicineCode, quantity, status) {
    presentId = id;
    //Rendering the original information
    $('#edit-username-input').val(username);
    $('#edit-medicine-code-input').val(medicineCode);
    $('#edit-quantity-input').val(quantity);
    $('#edit-status-input').val(status);
    
    $('#editUserModal').modal("toggle");
}*/

/**
 * Inspection and editing information form
 * @author Bac
 * @date 2025-5-20
 * @param form
 */
function validateEditForm(form) {
    
    if (!form.warehouseName) {
        alert("Please enter the warehouse name!");
        return false;
    }
    if (!form.warehouseType) {
        alert("Please enter the warehouse type!");
        return false;
    }
    if (!form.branchesCode) {
        alert("Please enter the branches code!");
        return false;
    }
    if (!form.managerName) {
        alert("Please enter the manager name!");
        return false;
    }
    if (!form.phone) {
        alert("Please enter the phone!");
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
        userId: $('#add-username-input').val(),
        medicineId: $('#add-medicine-code-input').val(),
        quantity: $('#add-quantity-input').val()
    };
}

/**
 * Render dropdown menu based on identity
 * @author Bac
 * @date 2025-5-21
 */
function renderSelect() {
    var status = "<option selected='selected'>PENDING</option>" +
            "<option>APPROVED</option>" +
            "<option>LINKED</option>" +
            "<option>REJECTED</option>";
        $('#statusInput').html(status);
}
	
	
	