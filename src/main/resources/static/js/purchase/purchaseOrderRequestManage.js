let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	renderSelect();
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">User Name</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="usernameInput"  name="username" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Status</label>
	        <div class="col-sm-3">
	            <select id="statusInput" class="form-control select2">
										<option value="">all</option>
										<option value="PENDING">PENDING - Chờ duyệt</option>
										<option value="APPROVED">APPROVED - Đã duyệt</option>
										<option value="REJECTED">REJECTED - Từ chối</option>
										<option value="LINKED">LINKED - Đã liên kết</option>
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
		document.getElementById('usernameInput').value = '';
		document.getElementById('statusInput').value = '';
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

    $('#edit-submit-btn').click(function () {
        var editForm = getEditForm(presentId);
        /*if (!validateEditForm(editForm)) {
            return;
        }*/
        fetch('/api/auth/purchase/order/request/edit', {
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
        fetch('/api/auth/purchase/order/request/generate-order-pdf', {
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

					//window.open(data.content.pdfUrl, '_blank'); // mở file PDF đã ký
				}else{
					alert("Error: " + data.content.message);
				}
	        } else {
	            alert("Error: " + data.message);
	        }
	    });
    });
    
    /*document.getElementById('status').addEventListener('change', function () {
		const reasonGroup = document.getElementById('rejectionReasonGroup');
		if (this.value === 'REJECTED') {
			reasonGroup.style.display = 'flex';
		} else {
			reasonGroup.style.display = 'none';
		}
	});*/
	loadMedicineSelectOptions();
	
	// Thêm hàng thuốc mới
	document.getElementById("add-medicine").addEventListener("click", function () {
	  const container = document.getElementById("medicine-container");
	
	  const newRow = document.createElement("div");
	  newRow.classList.add("form-group", "medicine-row");
	  newRow.innerHTML = `
	      <label class="col-sm-2 control-label">Mã thuốc</label>
	      <div class="col-sm-4">
	          <select class="form-control medicine-code" required>
	              ${cachedMedicineOptions}
	          </select>
	      </div>
	      <label class="col-sm-2 control-label">Số lượng</label>
	      <div class="col-sm-3">
	          <input type="number" class="form-control quantity" min="1" required>
	      </div>
	      <div class="col-sm-1">
	          <button type="button" class="btn btn-danger remove-row">X</button>
	      </div>
	  `;
	  container.appendChild(newRow);
	});
	
	// Xóa thuốc
	document.addEventListener("click", function (e) {
	    if (e.target.classList.contains("remove-row")) {
	        e.target.closest(".medicine-row").remove();
	    }
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
	const username = document.getElementById('usernameInput').value.trim();
	const status = document.getElementById('statusInput').value.trim();

	let url = `/api/auth/purchase/order/request/all?page=${page}&size=${size}`;
	if (username) {
		url += `&username=${encodeURIComponent(username)}`;
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
	fetch(`/api/auth/purchase/order/request/all?page=${page}&size=${size}`)
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
		case 'REJECTED':
			colorClass = 'badge-danger'; break;
		case 'PENDING':
			colorClass = 'badge-warning'; break;
		case 'RECEIVED':
			colorClass = 'badge-dark'; break;
		default:
			colorClass = 'badge-light'; break;
	}

	return `<span class="badge badge-pill ${colorClass}">${status}</span>`;
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
			// Bên trong vòng lặp for
			let isPending = order.status === 'PENDING';
			let checkbox = '<input type="checkbox" class="row-checkbox" data-id="' + order.id + '" ' + 
			               (isPending ? '' : 'disabled title="Chỉ được chọn khi trạng thái là PENDING"') + ' />';

			roleTableContent +=
				'<tr>' +
					'<td class="text-center">' + checkbox + '</td>' +
					'<td class="text-center">' + safeValue(order.username) + '</td>' +
					'<td class="text-center">' + safeValue(order.medicineCode) + '</td>' +
					'<td class="text-center">' + safeValue(order.quantity) + '</td>' +
					'<td class="text-center">' + renderStatusBadge(order.status) + '</td>' +
					'<td class="text-center">' + safeValue(order.createdBy) + '</td>' +
					'<td class="text-center">' + formatDateStr(order.createdDate) + '</td>' +
					'<td class="text-center">' + safeValue(order.updatedBy) + '</td>' +
					'<td class="text-center">' + formatDateStr(order.updatedDate) + '</td>' +
					'<td class="text-center min-wd-100">' + getEditBtn(order.id, order.username, order.medicineCode, order.quantity, 
					 order.status) + '</td>' +
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
function getEditBtn(id, username, medicineCode, quantity, status) {
	return `<button class='btn btn-sm btn-info btn-view'
                data-id="${id}"
                data-username="${safeValue(username)}"
                data-medicinecode="${safeValue(medicineCode)}"
                data-quantity="${safeValue(quantity)}"
                data-status="${safeValue(status)}"
                onclick='handleEditClick(this)'> <i class="fa fa-eye"></i></button>`;
}

function handleEditClick(btn) {
    const id = btn.dataset.id;
    const username = btn.dataset.username;
    const medicineCode = btn.dataset.medicinecode;
    const quantity = btn.dataset.quantity;
    const status = btn.dataset.status;

    editRole(id, username, medicineCode, quantity, status);
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
		fetch(`/api/auth/purchase/order/request/delete?id=${id}`)
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
function editRole(id, username, medicineCode, quantity, status) {
    presentId = id;
    //Rendering the original information
    $('#edit-username-input').val(username);
    $('#edit-medicine-code-input').val(medicineCode);
    $('#edit-quantity-input').val(quantity);
    $('#edit-status-input').val(status);
    
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
        customerName: $('#customer-name').val(),
        gender: $('#gender').val(),
        age:  $('#age').val(),
        phone: $('#phone').val(),
        cardNumber: $('#identity').val(),
        address: $('#address').val(),
        diagnosis: $('#diagnosis').val(),
        
        medicines: collectMedicineData()
    };
}

function collectMedicineData() {
    const medicines = [];
    $('.medicine-row').each(function () {
        const code = $(this).find('.medicine-code').val();
        const quantity = parseInt($(this).find('.quantity').val());
        if (code && quantity > 0) {
            medicines.push({
                medicineId: code,
                quantity: quantity
            });
        }
    });
    return medicines;
}

async function loadMedicineSelectOptions() {
  try {
    const res = await fetch('/api/auth/medicine/medicine-groups');
    const data = await res.json();

    cachedMedicineOptions = `<option value="">-- Chọn thuốc --</option>`;
    data.forEach(item => {
      cachedMedicineOptions += `<option value="${item.id}">${item.code} - ${item.name}</option>`;
    });

    // Áp dụng cho mọi select đã có
    document.querySelectorAll('.medicine-code').forEach(select => {
      select.innerHTML = cachedMedicineOptions;
    });
  } catch (err) {
    console.error('Lỗi khi load danh sách thuốc:', err);
  }
}

/*async function loadMedicineSelectOptions() {
  await Promise.all([
    loadSelectOptions('/api/auth/medicine/medicine-groups', 'medicine-code', ''),
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
}*/

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

            const url = '/api/auth/purchase/order/request/approve-multiple'; // URL API duyệt
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
				  success: function (data) {
				    if (data.success || data == '') {
				        alert("Duyệt đơn thành công!");
				        $('#confirmActionModal').modal('hide');
				        location.reload(); // hoặc fetch lại dữ liệu nếu không muốn reload toàn bộ
				    } else {
				      alert("Lỗi: " + (data.message || "Không rõ nguyên nhân"));
				    }
				  },
				  error: function (xhr) {
				    alert("Có lỗi xảy ra: " + (xhr.responseText || "Lỗi hệ thống"));
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
		const url = '/api/auth/purchase/order/request/reject-multiple'; // URL API từ chối
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
			      alert('Từ chối yêu cầu đặt hàng thành công với lý do: ' + reason);
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
	
	
	