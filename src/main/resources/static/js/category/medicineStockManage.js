let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Batch No</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="batchNoInput"  name="batchNo" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Medicine</label>
	        <div class="col-sm-3">
	            <select id="medicineInput" class="form-control select2"></select>
	        </div>
	    </div>
	`;
	searchContainer.innerHTML = searchHtml;
	renderSelect();
	// Initial load
	fetchUsers(currentPage);
	
	$('#add-expiry-input').datepicker({
			format : "dd/mm/yyyy",
			changeMonth : true,
			changeYear : true,
			autoclose : true,
			keyboardNavigation : true
	});
	$('.datepicker > input').attr("placeholder", "dd/MM/yyyy");
	$('.datepicker > input').on('change', function(){
		$(this).valid();
	});
	const effectiveDate = new Date();
	var expiredDate = $("#add-expiry-input").val();
	changeDatepickerById(effectiveDate, expiredDate, '',
			'#add-product-expiry-date-input');
	
	$('#edit-expiry-input').datepicker({
			format : "dd/mm/yyyy",
			changeMonth : true,
			changeYear : true,
			autoclose : true,
			keyboardNavigation : true
	});
	$('.datepicker > input').attr("placeholder", "dd/MM/yyyy");
	$('.datepicker > input').on('change', function(){
		$(this).valid();
	});

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
        /*if (!validateEditForm(editForm)) {
            return;
        }*/
        fetch('/api/auth/medicine/stock/edit', {
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
        fetch('/api/auth/medicine/stock/add', {
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
					alert('Added new stock successfully!');
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
        expiryDate: $('#edit-expiry-input').val()
    };
}

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const name = document.getElementById('nameInput').value.trim();
	const code = document.getElementById('codeInput').value.trim();

	let url = `/api/auth/medicine/stock/all?page=${page}&size=${size}`;
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
	fetch(`/api/auth/medicine/stock/all?page=${page}&size=${size}`)
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
					'<td>' + safeValue(roleAcc.medicineCode) + '</td>' +
					'<td>' + safeValue(roleAcc.batchNo) + '</td>' +
					'<td>' + safeValue(roleAcc.expiryDate) + '</td>' +
					'<td>' + safeValue(roleAcc.quantity) + '</td>' +
					'<td>' + safeValue(roleAcc.unitPrice) + '</td>' +
					'<td>' + safeValue(roleAcc.lockedQuantity) + '</td>' +
					'<td>' + safeValue(roleAcc.usedQuantity) + '</td>' +
					'<td>' + safeValue(roleAcc.warehouseCode) + '</td>' +
					'<td>' + safeValue(roleAcc.createdBy) + '</td>' +
					'<td>' + formatDateStr(roleAcc.createdDate) + '</td>' +
					'<td class="text-center min-wd-100">' + getEditBtn(roleAcc.id, roleAcc.medicineCode, roleAcc.batchNo, 
					 roleAcc.expiryDate, roleAcc.quantity, roleAcc.unitPrice, roleAcc.lockedQuantity, roleAcc.usedQuantity, roleAcc.warehouseCode
					  ) + '</td>' +
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
function getEditBtn(id, medicineId, batchNo, expiryDate, quantity, unitPrice, lockedQuantity, usedQuantity, warehouseId) {
	return `<button class='btn btn-primary'
                data-id="${id}"
                data-medicine="${safeValue(medicineId)}"
                data-batchno="${safeValue(batchNo)}"
                data-expirydate="${safeValue(expiryDate)}"
                data-quantity="${safeValue(quantity)}"
                data-unitprice="${safeValue(unitPrice)}"
                data-lockquantity="${safeValue(lockedQuantity)}"
                data-usedquantity="${safeValue(usedQuantity)}"
                data-warehouse="${safeValue(warehouseId)}"
                onclick='handleEditClick(this)'>Edit</button>`;
}

function handleEditClick(btn) {
    const id = btn.dataset.id;
    const medicineId = btn.dataset.medicine;
    const batchNo = btn.dataset.batchno;
    const expiryDate = btn.dataset.expirydate;
    const quantity = btn.dataset.quantity;
    const unitPrice = btn.dataset.unitprice;
    const lockedQuantity = btn.dataset.lockquantity;
    const usedQuantity = btn.dataset.usedquantity;
    const warehouseId = btn.dataset.warehouse;

    editRole(id, medicineId, batchNo, expiryDate, quantity, unitPrice, lockedQuantity, usedQuantity, warehouseId);
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
		fetch(`/api/auth/medicine/stock/delete?id=${id}`)
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
function editRole(id, medicineId, batchNo, expiryDate, quantity, unitPrice, lockedQuantity, usedQuantity, warehouseId) {
    presentId = id;
    //Rendering the original information
    $('#edit-medicine-input').val(medicineId);
    $('#edit-batch-no-input').val(batchNo);
    $('#edit-expiry-input').val(expiryDate);
    $('#edit-quantity-input').val(quantity);
    $('#edit-unit-price-input').val(unitPrice);
    $('#edit-locked-quantity-input').val(lockedQuantity);
    $('#edit-used-quantity-input').val(usedQuantity);
    $('#edit-warehouse-input').val(warehouseId);
    
    $('#editUserModal').modal("toggle");
}

/**
 * Inspection and editing information form
 * @author Bac
 * @date 2025-5-20
 * @param form
 */
function validateEditForm(form) {
    
    if (!form.medicineId) {
        alert("Please enter the medicine!");
        return false;
    }
    if (!form.batchNo) {
        alert("Please enter the batch no!");
        return false;
    }
    if (!form.expiryDate) {
        alert("Please enter the expiry date!");
        return false;
    }
    if (!form.quantity) {
        alert("Please enter the quantity!");
        return false;
    }
    if (!form.unitPrice) {
        alert("Please enter the unit price!");
        return false;
    }
	if (!form.warehouseId) {
		alert("Please enter the warehouse!");
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
        medicineId: $('#add-medicine-input').val(),
        batchNo: $('#add-batch-no-input').val(),
        expiryDate: $('#add-expiry-input').val(),
        quantity: $('#add-quantity-input').val(),
        unitPrice: $('#add-unit-price-input').val(),
        warehouseId: $('#add-warehouse-input').val(),
    };
}


async function loadMedicineSelectOptions() {
  await Promise.all([
    loadSelectOptions('/api/auth/medicine/medicine-groups', 'add-medicine-input', ''),
    loadSelectOptions('/api/auth/medicine/storage/storage-groups', 'add-warehouse-input', '')
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
      option.value = item.id;
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



/**
 * Render dropdown menu based on identity
 * @author Bac
 * @date 2025-6-4
 */
function renderSelect() {
    var status = "<option selected='selected'>PENDING</option>" +
            "<option>APPROVED</option>" +
            "<option>LINKED</option>" +
            "<option>REJECTED</option>";
        $('#medicineInput').html(status);
}