let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Warehouse Code</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="codeInput"  name="code" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Warehouse Name</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="nameInput"  name="name" />
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
		document.getElementById('nameInput').value = '';
		document.getElementById('codeInput').value = '';
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

    $('#edit-submit-btn').click(function () {
        var editForm = getEditForm(presentId);
        if (!validateEditForm(editForm)) {
            return;
        }
        fetch('/api/auth/medicine/storage/edit', {
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
        if (!validateEditForm(form)) {
            return;
        }
        fetch('/api/auth/medicine/storage/add', {
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
					alert('Added new storage successfully!');
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
    
    loadMedicineSelectOptions();
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
        warehouseCode: $('#edit-warehouse-code-input').val(),
        warehouseName: $('#edit-warehouse-name-input').val(),
        warehouseType: $('#edit-warehouse-type-input').val(),
        branchesCode: $('#edit-branches-code-input').val(),
        managerName: $('#edit-manager-name-input').val(),
        phone: $('#edit-phone-input').val()
    };
}

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const name = document.getElementById('nameInput').value.trim();
	const code = document.getElementById('codeInput').value.trim();

	let url = `/api/auth/medicine/storage/all?page=${page}&size=${size}`;
	if (name) {
		url += `&name=${encodeURIComponent(name)}`;
	}
	if (code) {
		url += `&code=${encodeURIComponent(code)}`;
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
	fetch(`/api/auth/medicine/storage/all?page=${page}&size=${size}`)
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
	if(roles.message !== "No data"){
		for (let roleAcc of roles) {
			roleTableContent +=
				'<tr>' +
					'<td>' + safeValue(roleAcc.warehouseCode) + '</td>' +
					'<td>' + safeValue(roleAcc.warehouseName) + '</td>' +
					'<td>' + safeValue(roleAcc.warehouseType) + '</td>' +
					'<td>' + safeValue(roleAcc.branchesCode) + '</td>' +
					'<td>' + safeValue(roleAcc.managerName) + '</td>' +
					'<td>' + safeValue(roleAcc.phone) + '</td>' +
					'<td>' + safeValue(roleAcc.isActive) + '</td>' +
					'<td>' + safeValue(roleAcc.createdBy) + '</td>' +
					'<td>' + formatDateStr(roleAcc.createdDate) + '</td>' +
					'<td>' + safeValue(roleAcc.updatedBy) + '</td>' +
					'<td>' + formatDateStr(roleAcc.updatedDate) + '</td>' +
					'<td class="text-center min-wd-100">' + getEditBtn(roleAcc.id, roleAcc.warehouseCode, roleAcc.warehouseName, 
					 roleAcc.warehouseType, roleAcc.branchesCode, roleAcc.managerName, roleAcc.phone ) + '</td>' +
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
function getEditBtn(id, warehouseCode, warehouseName, warehouseType, branchesCode, managerName, phone) {
	return `<button class='btn btn-primary'
                data-id="${id}"
                data-code="${safeValue(warehouseCode)}"
                data-name="${safeValue(warehouseName)}"
                data-type="${safeValue(warehouseType)}"
                data-branchescode="${safeValue(branchesCode)}"
                data-managername="${safeValue(managerName)}"
                data-phone="${safeValue(phone)}"
                onclick='handleEditClick(this)'>Edit</button>`;
}

function handleEditClick(btn) {
    const id = btn.dataset.id;
    const warehouseCode = btn.dataset.code;
    const warehouseName = btn.dataset.name;
    const warehouseType = btn.dataset.type;
    const branchesCode = btn.dataset.branchescode;
    const managerName = btn.dataset.managername;
    const phone = btn.dataset.phone;

    editRole(id, warehouseCode, warehouseName, warehouseType, branchesCode, managerName, phone);
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
		fetch(`/api/auth/medicine/storage/delete?id=${id}`)
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
function editRole(id, warehouseCode, warehouseName, warehouseType, branchesCode, managerName, phone) {
    presentId = id;
    //Rendering the original information
    $('#edit-warehouse-code-input').val(warehouseCode);
    $('#edit-warehouse-name-input').val(warehouseName);
    $('#edit-warehouse-type-input').val(warehouseType);
    $('#edit-branches-code-input').val(branchesCode);
    $('#edit-manager-name-input').val(managerName);
    $('#edit-phone-input').val(phone);
    
    loadSelectOptions('/api/auth/medicine/branch/branch-groups', 'edit-branches-code-input', branchesCode)
    $('#editUserModal').modal("toggle");
}

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
        warehouseCode: $('#add-warehouse-code-input').val(),
        warehouseName: $('#add-warehouse-name-input').val(),
        warehouseType: $('#add-warehouse-type-input').val(),
        branchesCode: $('#add-branches-code-input').val(),
        managerName: $('#add-manager-name-input').val(),
        phone: $('#add-phone-input').val()
    };
}


async function loadMedicineSelectOptions() {
  await Promise.all([
    loadSelectOptions('/api/auth/medicine/branch/branch-groups', 'add-branches-code-input', '')
  ]);
}

async function loadSelectOptions(apiUrl, selectId, selectedValue = '') {
  try {
    const res = await fetch(apiUrl);
    const data = await res.json();

    const select = document.getElementById(selectId);
    if (!select) return;

    select.innerHTML = `<option value="">-- Chọn --</option>`;

    data.forEach(item => {
      const option = document.createElement("option");
      option.value = item.code;
      option.textContent = `${item.code} - ${item.name}`;
      if (item.code === selectedValue) {
        option.selected = true;
      }
      select.appendChild(option);
    });
  } catch (err) {
    console.error(`Lỗi khi load dữ liệu từ ${apiUrl}:`, err);
  }
}