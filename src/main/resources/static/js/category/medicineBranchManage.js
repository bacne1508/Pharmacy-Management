let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Branches Code</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="branchesCodeInput"  name="BranchesCode" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Branches Name</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="branchesNameInput"  name="BranchesName" />
	        </div>
	    </div>
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Manager</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="managerInput"  name="manager" />
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
		document.getElementById('branchesCodeInput').value = '';
		document.getElementById('branchesNameInput').value = '';
		document.getElementById('managerInput').value = '';
		document.getElementById('phoneInput').value = '';
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});

    $('#edit-submit-btn').click(function () {
        var editForm = getEditForm(presentId);
        if (!validateEditForm(editForm)) {
            return;
        }
        fetch('/api/auth/medicine/branch/edit', {
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
        fetch('/api/auth/medicine/branch/add', {
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
    
    loadAddAddress("add-province-input","add-district-input","add-ward-input");
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
        branchesCode: $('#edit-Branches-code-input').val(),
        branchesName: $('#edit-Branches-name-input').val(),
        manager: $('#edit-manager-input').val(),
        phone: $('#edit-phone-input').val(),
        provinceCode: $('#edit-province-input').val(),
        province: $('#hidden-edit-province-name').val(),
        districtCode: $('#edit-district-input').val(),
        district: $('#hidden-edit-district-name').val(),
        wardCode: $('#edit-ward-input').val(),
        ward: $('#hidden-edit-ward-name').val(),
        address: $('#edit-address-input').val(),
        email: $('#edit-email-input').val()
    };
}

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const branchesCode = document.getElementById('branchesCodeInput').value.trim();
	const branchesName = document.getElementById('branchesNameInput').value.trim();
	const manager = document.getElementById('managerInput').value.trim();
	const phone = document.getElementById('phoneInput').value.trim();

	let url = `/api/auth/medicine/branch/all?page=${page}&size=${size}`;
	if (branchesCode) {
		url += `&branchesCode=${encodeURIComponent(branchesCode)}`;
	}
	if (branchesName) {
		url += `&branchesName=${encodeURIComponent(branchesName)}`;
	}
	if (manager) {
		url += `&manager=${encodeURIComponent(manager)}`;
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
	fetch(`/api/auth/medicine/branch/all?page=${page}&size=${size}`)
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
				'<td>' + safeValue(roleAcc.branchesCode) + '</td>' +
				'<td>' + safeValue(roleAcc.branchesName) + '</td>' +
				'<td>' + safeValue(roleAcc.province) + '</td>' +
				'<td>' + safeValue(roleAcc.district) + '</td>' +
				'<td>' + safeValue(roleAcc.ward) + '</td>' +
				'<td>' + safeValue(roleAcc.address) + '</td>' +
				'<td>' + safeValue(roleAcc.phone) + '</td>' +
				'<td>' + safeValue(roleAcc.manager) + '</td>' +
				'<td>' + safeValue(roleAcc.email) + '</td>' +
				'<td>' + safeValue(roleAcc.isActive) + '</td>' +
				'<td>' + safeValue(roleAcc.createdBy) + '</td>' +
				'<td>' + formatDateStr(roleAcc.createdDate) + '</td>' +
				'<td>' + safeValue(roleAcc.updatedBy) + '</td>' +
				'<td>' + formatDateStr(roleAcc.updatedDate) + '</td>' +
				'<td class="text-center min-wd-100">' + getEditBtn(roleAcc.branchesCode, roleAcc.id, roleAcc.branchesName, 
				roleAcc.provinceCode, roleAcc.districtCode, roleAcc.wardCode, roleAcc.address, roleAcc.phone, roleAcc.manager, roleAcc.email) + '</td>' +
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
function getEditBtn(branchesCode, id, branchesName, provinceCode, districtCode, wardCode, address, phone, manager, email) {
	return "<button class='btn btn-primary' onclick='editRole(\"" + id  + "\",\"" + safeValue(branchesCode) + "\",\"" 
		+ safeValue(branchesName) + "\",\"" + safeValue(provinceCode) + "\",\"" + safeValue(districtCode)+ "\",\"" + safeValue(wardCode) + "\",\"" + 
		safeValue(address) + "\",\"" + safeValue(phone) + "\",\"" + safeValue(manager) + "\",\"" + safeValue(email) + "\")'>Edit</button>";
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
		fetch(`/api/auth/medicine/branch/delete?id=${id}`)
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
function editRole(id, branchesCode, branchesName, provinceCode, districtCode, wardCode, address, phone, manager, email) {
    presentId = id;
    //Rendering the original information
    $('#edit-Branches-code-input').val(branchesCode);
    $('#edit-Branches-name-input').val(branchesName);
    $('#edit-address-input').val(address);
    $('#edit-phone-input').val(phone);
    $('#edit-manager-input').val(manager);
    $('#edit-email-input').val(email);
    
    loadEditAddress(provinceCode, districtCode, wardCode);
    
    $('#editUserModal').modal("toggle");
}

/**
 * Inspection and editing information form
 * @author Bac
 * @date 2025-5-20
 * @param form
 */
function validateEditForm(form) {
    if (!form.branchesCode) {
        alert("Please enter the Branches code!");
        return false;
    }
    if (!form.branchesName) {
        alert("Please enter the Branches name!");
        return false;
    }
    if (!form.provinceCode) {
        alert("Please enter the province!");
        return false;
    }
    if (!form.districtCode) {
        alert("Please enter the district!");
        return false;
    }
    if (!form.wardCode) {
        alert("Please enter the ward!");
        return false;
    }
    if (!form.manager) {
        alert("Please enter the manager!");
        return false;
    }
    if (!form.email) {
        alert("Please enter the email!");
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
        branchesCode: $('#add-Branches-code-input').val(),
        branchesName: $('#add-Branches-name-input').val(),
        manager: $('#add-manager-input').val(),
        phone: $('#add-phone-input').val(),
        provinceCode: $('#add-province-input').val(),
        province: $('#hidden-province-name').val(),
        districtCode: $('#add-district-input').val(),
        district: $('#hidden-district-name').val(),
        wardCode: $('#add-ward-input').val(),
        ward: $('#hidden-ward-name').val(),
        address: $('#add-address-input').val(),
        email: $('#add-email-input').val()
    };
}

/*
* Hàm load địa chỉ cho phần thêm mới
* @author Bac
* @date 2025-5-27
* @description Hàm này sẽ load danh sách tỉnh/thành, quận/huyện, phường/xã vào các select tương ứng
* và gán giá trị đã chọn vào các hidden input để gửi lên server khi thêm địa chỉ.
* Lưu ý: Hàm này sử dụng fetch API để lấy dữ liệu từ server.
* @example
* loadAddAddress();
* @description
* Hàm này sẽ được gọi khi người dùng mở modal thêm mới địa chỉ, nó sẽ tự động điền các giá trị đã lưu
* và gán các sự kiện để load danh sách quận/huyện, phường/xã khi người dùng chọn tỉnh/thành hoặc quận/huyện.
*/
function loadAddAddress() {
  const provinceSelect = document.getElementById("add-province-input");
  const districtSelect = document.getElementById("add-district-input");
  const wardSelect = document.getElementById("add-ward-input");

  const hiddenProvinceName = document.getElementById("hidden-province-name");
  const hiddenDistrictName = document.getElementById("hidden-district-name");
  const hiddenWardName = document.getElementById("hidden-ward-name");

  // Hàm gọi API và render options
  async function fetchOptions(url, selectElement, defaultText, idField = "id", nameField = "name") {
    try {
      const response = await fetch(url);
      const data = await response.json();

      selectElement.innerHTML = `<option value="">-- ${defaultText} --</option>`;
      data.forEach(item => {
        const option = document.createElement("option");
        option.value = item[idField];
        option.textContent = item[nameField];
        option.setAttribute("data-name", item[nameField]); // Gán tên để lưu vào hidden input
        selectElement.appendChild(option);
      });
    } catch (e) {
      console.error("Fetch failed:", e);
    }
  }

  // ✅ Load danh sách tỉnh/thành khi trang mở
  fetchOptions('/api/auth/provinces', provinceSelect, 'Chọn tỉnh/thành', 'maTp', 'name');

  // ✅ Khi chọn tỉnh => load quận/huyện
  provinceSelect.addEventListener("change", function () {
    const provinceId = this.value;

    districtSelect.innerHTML = `<option value="">-- Chọn quận/huyện --</option>`;
    wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;
    hiddenDistrictName.value = "";
    hiddenWardName.value = "";

    const selectedOption = this.options[this.selectedIndex];
    hiddenProvinceName.value = selectedOption.getAttribute("data-name") || "";

    if (provinceId) {
      fetchOptions(`/api/auth/districts?provinceCode=${provinceId}`, districtSelect, 'Chọn quận/huyện', 'maQh', 'name');
    }
  });

  // ✅ Khi chọn quận => load phường/xã
  districtSelect.addEventListener("change", function () {
    const districtId = this.value;

    wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;
    hiddenWardName.value = "";

    const selectedOption = this.options[this.selectedIndex];
    hiddenDistrictName.value = selectedOption.getAttribute("data-name") || "";

    if (districtId) {
      fetchOptions(`/api/auth/wards?districtCode=${districtId}`, wardSelect, 'Chọn phường/xã', 'xaId', 'name');
    }
  });

  // ✅ Khi chọn phường
  wardSelect.addEventListener("change", function () {
    const selectedOption = this.options[this.selectedIndex];
    hiddenWardName.value = selectedOption.getAttribute("data-name") || "";
  });
}

/*
* Hàm load địa chỉ cho phần chỉnh sửa
* @param provinceCode
* @param districtCode
* @param wardCode
* @returns {Promise<void>}
* @author Bac
* @date 2025-5-27
* @description Hàm này sẽ load danh sách tỉnh/thành, quận/huyện, phường/xã vào các select tương ứng
* và gán giá trị đã chọn vào các hidden input để gửi lên server khi chỉnh sửa địa chỉ.
* Lưu ý: Hàm này sử dụng fetch API để lấy dữ liệu từ server.
* @example
* loadEditAddress('01', '001', '00001');
* @description
* Hàm này sẽ được gọi khi người dùng mở modal chỉnh sửa địa chỉ, nó sẽ tự động điền các giá trị đã lưu
*/
async function loadEditAddress(provinceCode, districtCode, wardCode) {
  const provinceSelect = document.getElementById("edit-province-input");
  const districtSelect = document.getElementById("edit-district-input");
  const wardSelect = document.getElementById("edit-ward-input");

  const hiddenProvinceName = document.getElementById("hidden-edit-province-name");
  const hiddenDistrictName = document.getElementById("hidden-edit-district-name");
  const hiddenWardName = document.getElementById("hidden-edit-ward-name");

  // Reset select
  provinceSelect.innerHTML = `<option value="">-- Chọn tỉnh/thành --</option>`;
  districtSelect.innerHTML = `<option value="">-- Chọn quận/huyện --</option>`;
  wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;

  try {
    // 1. Load provinces
    const provinces = await fetch('/api/auth/provinces').then(res => res.json());
    provinces.forEach(item => {
      const option = document.createElement("option");
      option.value = item.maTp;
      option.textContent = item.name;
      option.setAttribute("data-name", item.name);
      provinceSelect.appendChild(option);
    });
    provinceSelect.value = provinceCode;
    const selectedProvince = provinceSelect.querySelector(`option[value="${provinceCode}"]`);
    hiddenProvinceName.value = selectedProvince ? selectedProvince.getAttribute("data-name") : "";

    // 2. Load districts
    const districts = await fetch(`/api/auth/districts?provinceCode=${provinceCode}`).then(res => res.json());
    districts.forEach(item => {
      const option = document.createElement("option");
      option.value = item.maQh;
      option.textContent = item.name;
      option.setAttribute("data-name", item.name);
      districtSelect.appendChild(option);
    });
    districtSelect.value = districtCode;
    const selectedDistrict = districtSelect.querySelector(`option[value="${districtCode}"]`);
    hiddenDistrictName.value = selectedDistrict ? selectedDistrict.getAttribute("data-name") : "";

    // 3. Load wards
    const wards = await fetch(`/api/auth/wards?districtCode=${districtCode}`).then(res => res.json());
    wards.forEach(item => {
      const option = document.createElement("option");
      option.value = item.xaId;
      option.textContent = item.name;
      option.setAttribute("data-name", item.name);
      wardSelect.appendChild(option);
    });
    wardSelect.value = wardCode;
    const selectedWard = wardSelect.querySelector(`option[value="${wardCode}"]`);
    hiddenWardName.value = selectedWard ? selectedWard.getAttribute("data-name") : "";

  } catch (err) {
    console.error("Lỗi khi load địa chỉ:", err);
  }

  // ===== GẮN SỰ KIỆN CHO EDIT =====

  provinceSelect.addEventListener("change", async function () {
    const provinceCode = this.value;
    const selectedOption = this.options[this.selectedIndex];
    hiddenProvinceName.value = selectedOption ? selectedOption.getAttribute("data-name") : "";

    districtSelect.innerHTML = `<option value="">-- Chọn quận/huyện --</option>`;
    wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;
    hiddenDistrictName.value = "";
    hiddenWardName.value = "";

    if (provinceCode) {
      const districts = await fetch(`/api/auth/districts?provinceCode=${provinceCode}`).then(res => res.json());
      districts.forEach(item => {
        const option = document.createElement("option");
        option.value = item.maQh;
        option.textContent = item.name;
        option.setAttribute("data-name", item.name);
        districtSelect.appendChild(option);
      });
    }
  });

  districtSelect.addEventListener("change", async function () {
    const districtCode = this.value;
    const selectedOption = this.options[this.selectedIndex];
    hiddenDistrictName.value = selectedOption ? selectedOption.getAttribute("data-name") : "";

    wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;
    hiddenWardName.value = "";

    if (districtCode) {
      const wards = await fetch(`/api/auth/wards?districtCode=${districtCode}`).then(res => res.json());
      wards.forEach(item => {
        const option = document.createElement("option");
        option.value = item.xaId;
        option.textContent = item.name;
        option.setAttribute("data-name", item.name);
        wardSelect.appendChild(option);
      });
    }
  });

  wardSelect.addEventListener("change", function () {
    const selectedOption = this.options[this.selectedIndex];
    hiddenWardName.value = selectedOption ? selectedOption.getAttribute("data-name") : "";
  });
}
