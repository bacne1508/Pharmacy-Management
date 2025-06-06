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
	        <label class="col-sm-2 col-form-label">PO Code</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="poCodeInput"  name="poCode" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Status</label>
	        <div class="col-sm-3">
	            <select id="statusInput" class="form-control select2">
										<option value="">all</option>
										<option value="APPROVED">APPROVED - Đã duyệt</option>
										<option value="CANCELLED">CANCELLED - Đã hủy</option>
										<option value="SENT">SENT - Đã gửi</option>
										<option value="RECEIVED">RECEIVED - Đã nhận hàng</option>
									</select>
	        </div>
	    </div>
	`;
	searchContainer.innerHTML = searchHtml;
	// Initial load
	fetchUsers(currentPage);
	flowApproveAndReject();

	$("#btnSearch").on('click', function(event) {
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

	$("#btnClear").on('click', function(event) {
		document.getElementById('poCodeInput').value = '';
		document.getElementById('statusInput').value = '';
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

    $('#edit-submit-btn').click(function () {
        var editForm = getEditForm(presentId);
        /*if (!validateEditForm(editForm)) {
            return;
        }*/
        fetch('/api/auth/purchase/order/edit', {
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
        fetch('/api/auth/purchase/order/add', {
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
    
    loadMedicineSelectOptions();
    
    /*document.getElementById('status').addEventListener('change', function () {
		const reasonGroup = document.getElementById('rejectionReasonGroup');
		if (this.value === 'REJECTED') {
			reasonGroup.style.display = 'flex';
		} else {
			reasonGroup.style.display = 'none';
		}
	});*/
	
	

	$('#confirmActionBtn').off('click').on('click', function () {
	  if (!currentAction || !currentId) return;
	
	  let url = `/api/auth/purchase/order/${currentId}/${currentAction}`;
	  $.ajax({
	    url: url,
	    method: 'POST',
	    success: function (data) {
	      if (data.success) {
	         alert(data.content);
	         $('#confirmActionModal').modal('hide');
	         location.reload(); 
	      } else {
	        alert("Lỗi: " + data.message);
	      }
	    },
	    error: function () {
	      alert("Có lỗi xảy ra khi thực hiện thao tác.");
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
	const poCode = document.getElementById('poCodeInput').value.trim();
	const status = document.getElementById('statusInput').value.trim();

	let url = `/api/auth/purchase/order/all?page=${page}&size=${size}`;
	if (poCode) {
		url += `&poCode=${encodeURIComponent(poCode)}`;
	}
	if (status) {
		url += `&status=${encodeURIComponent(status)}`;
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
	fetch(`/api/auth/purchase/order/all?page=${page}&size=${size}`)
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

function renderStatusBadge(status) {
	let colorClass = '';
	switch (status) {
		case 'DRAFT':
			colorClass = 'badge-secondary'; break; // màu xám nhạt
		case 'APPROVED':
			colorClass = 'badge-success'; break;
		case 'CANCELLED':
			colorClass = 'badge-danger'; break;
		case 'SENT':
			colorClass = 'badge-warning'; break;
		case 'RECEIVED':
			colorClass = 'badge-dark'; break;
		default:
			colorClass = 'badge-light'; break;
	}

	return `<span class="badge badge-pill ${colorClass}">${status}</span>`;
}

function confirmAction(action, id) {
  currentAction = action;
  currentId = id;

  // Gán nội dung cho modal
  let message = '';
  if (action === 'APPROVED') message = 'Bạn có chắc chắn muốn <b>phê duyệt</b> đơn hàng này?';
  else if (action === 'CANCELLED') message = 'Bạn có chắc chắn muốn <b>hủy</b> đơn hàng này?';
  else if (action === 'SENT') message = 'Bạn có chắc chắn muốn <b>gửi hàng</b>?';
  else if (action === 'RECEIVED') message = 'Xác nhận đã <b>nhận hàng</b>?';

  $('#confirmActionMessage').html(message);
  $('#confirmActionModal').modal('show');
}

function editRole(id, status) {
	presentId = id;
	let btnHtml = '';

	switch (status) {
		case 'DRAFT':
			btnHtml += `
				<button class="btn btn-sm btn-primary" onclick="confirmAction('APPROVED','${id}')">Approve</button>
				<button class="btn btn-sm btn-danger" onclick="confirmAction('CANCELLED','${id}')">Cancel</button>
			`;
			break;

		case 'APPROVED':
			btnHtml += `
				<button class="btn btn-sm btn-success" onclick="confirmAction('SENT','${id}')">Send</button>
			`;
			break;

		case 'SENT':
			btnHtml += `
				<button class="btn btn-sm btn-warning" onclick="confirmAction('RECEIVED','${id}')">Receive</button>
			`;
			break;

		case 'RECEIVED':
		case 'CANCELLED':
			btnHtml += `<span class="text-muted"></span>`;
			break;

		default:
			btnHtml += `<span class="text-muted"></span>`;
	}

	return btnHtml;
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
					'<td class="text-center">' + safeValue(order.poCode) + '</td>' +
					'<td class="text-center">' + safeValue(order.supplierCode) + '</td>' +
					'<td class="text-center">' + formatDateStr(order.expectedDeliveryDate) + '</td>' +
					'<td class="text-center">' + renderStatusBadge(order.status) + '</td>' +
					'<td class="text-center">' + safeValue(order.createdBy) + '</td>' +
					'<td class="text-center">' + formatDateStr(order.createdDate) + '</td>' +
					'<td class="text-center min-wd-100">' + editRole(order.id, order.status) + '</td>' +
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
		fetch(`/api/auth/purchase/order/delete?id=${id}`)
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


async function loadMedicineSelectOptions() {
  await Promise.all([
    loadSelectOptions('/api/auth/medicine/medicine-groups', 'add-medicine-code-input', ''),
    loadSelectOptions('/api/auth/user/user-groups', 'add-username-input', '')
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
 * @date 2025-5-21
 */
function renderSelect() {
    var status = "<option selected='selected'>PENDING</option>" +
            "<option>APPROVED</option>" +
            "<option>LINKED</option>" +
            "<option>REJECTED</option>";
        $('#statusInput').html(status);
}

/** * Handle the flow of approving and rejecting purchase order requests
 * @author Bac
 * @date 2025-5-21
 */
function flowApproveAndReject() {
	const $bulkActionSelect = $("#bulkActionSelect");
	const $btnPerform = $("#btnPerformBulkAction");
	const $bulkRejectModal = $("#bulkRejectModal");
	
	// Bật/tắt nút "Thực hiện"
	function toggleBulkActionButton() {
		const anyChecked = $(".row-checkbox:checked").length > 0;
		const actionSelected = $bulkActionSelect.val() !== "";
		$btnPerform.prop("disabled", !(anyChecked && actionSelected));
	}
	
	// Chọn tất cả
	$("#selectAllCheckbox").on("change", function () {
		 $(".row-checkbox:not(:disabled)").prop("checked", $(this).prop("checked"));
		toggleBulkActionButton();
	});
	
	// Checkbox thay đổi
	$(document).on("change", ".row-checkbox", toggleBulkActionButton);
	$bulkActionSelect.on("change", toggleBulkActionButton);
	
	// Xử lý nút Thực hiện
	$btnPerform.on("click", function () {
		const action = $bulkActionSelect.val();
		if (action === "APPROVED") {
			// Gọi xử lý duyệt hàng loạt
			const ids = $(".row-checkbox:checked").map(function () {
				return $(this).data("id");
			}).get();

            const url = '/api/auth/purchase/order/approve-multiple'; // URL API duyệt
			// Gửi request duyệt ở đây
			console.log("Duyệt các ID:", ids);
			// TODO: Gọi API duyệt
			const payload = {
			    ids: ids
			  };
			  $.ajax({
			    url: url,
			    method: 'POST',
			    contentType: 'application/json',
			    data: JSON.stringify(payload),
			    success: function () {
			      alert('Thao tác thành công!');
			      $('#confirmActionModal').modal('hide');
			      location.reload();
			    },
			    error: function () {
			      alert('Có lỗi xảy ra.');
			    }
			  });

		} else if (action === "REJECTED") {
			// Mở modal lý do từ chối
			$bulkRejectModal.modal("show");
		}
	});
	
	// Xác nhận từ chối hàng loạt
	$("#confirmBulkReject").on("click", function () {
		const reason = $("#bulkRejectionReason").val();
		const ids = $(".row-checkbox:checked").map(function () {
			return $(this).data("id");
		}).get();

		if (!reason) {
			alert("Vui lòng nhập lý do từ chối.");
			return;
		}
		const url = '/api/auth/purchase/order/reject-multiple'; // URL API từ chối
		// Gửi request từ chối ở đây
		console.log("Từ chối các ID:", ids, "với lý do:", reason);
		// TODO: Gọi API từ chối
		const payload = {
			    ids: ids,
			    reason: $('#bulkRejectionReason').val()
			  };
			  $.ajax({
			    url: url,
			    method: 'POST',
			    contentType: 'application/json',
			    data: JSON.stringify(payload),
			    success: function () {
			      alert('Thao tác thành công!');
			      $('#confirmActionModal').modal('hide');
			      location.reload();
			    },
			    error: function () {
			      alert('Có lỗi xảy ra.');
			    }
			  });

		$bulkRejectModal.modal("hide");
	});
}	
	
	
	