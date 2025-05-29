let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Name</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="nameInput"  name="name" />
	        </div>
	        
	        <label class="col-sm-2 col-form-label">Code</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="codeInput"  name="code" />
	        </div>
	    </div>
	`;
	searchContainer.innerHTML = searchHtml;

	$('#add-product-expiry-date-input').datepicker({
			format : "dd/mm/yyyy",
			changeMonth : true,
			changeYear : true,
			autoclose : true,
			keyboardNavigation : true
	});
	$('.datepicker > input').attr("placeholder", "dd/MM/yyyy");
	$('#add-date-of-manufacture-input').datepicker({
			format : "dd/mm/yyyy",
			autoclose : true
	});	
	$('.datepicker > input').on('change', function(){
		$(this).valid();
	});
	var effectiveDate = $("#add-date-of-manufacture-input").val();
	var expiredDate = $("#add-product-expiry-date-input").val();
	changeDatepickerById(effectiveDate, expiredDate, '#add-date-of-manufacture-input',
			'#add-product-expiry-date-input');
	
	
    renderSelect();

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
        fetch('/api/auth/medicine/edit', {
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
        fetch('/api/auth/medicine/add', {
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
					alert('Added new Account successfully!');
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
    
    loadImage();
    loadMedicineSelectOptions();
    loadImageEdit();
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
        code: $('#edit-code-input').val(),
        name: $('#edit-name-input').val(),
        base64Images: JSON.parse($('#edit-base64-images-hidden').val() || "[]"),
        description: $('#edit-desc-input').val(),
        medicineGroupsCode: $('#edit-medicine-groups-code-input').val(),
        medicineUnitsCode: $('#edit-medicine-units-code-input').val(),
        medicineTypesCode: $('#edit-medicine-types-code-input').val(),
        ingredient: $('#edit-ingredient-input').val(),
        strength: $('#edit-strength-input').val(),
        manufacturer: $('#edit-manufacturer-input').val(),
        originCountry: $('#edit-origin-country-input').val(),
        purchasePrice: $('#edit-purchase-price-input').val(),
        salePrice: $('#edit-sale-price-input').val(),
        quantity: $('#edit-quantity-input').val(),
        dateOfManufacture: $('#edit-date-of-manufacture-input').val(),
        productExpiryDate: $('#edit-product-expiry-date-input').val()
    };
}

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const name = document.getElementById('nameInput').value.trim();
	const code = document.getElementById('codeInput').value.trim();

	let url = `/api/auth/medicine/all?page=${page}&size=${size}`;
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
	fetch(`/api/auth/medicine/all?page=${page}&size=${size}`)
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
				'<td>' + renderImage(roleAcc.medicineImages) + '</td>' +
				'<td>' + safeValue(roleAcc.code) + '</td>' +
				'<td>' + safeValue(roleAcc.name) + '</td>' +
				'<td style="width: 150px;min-width: 400px;">' + safeValue(roleAcc.description) + '</td>' +
				'<td>' + safeValue(roleAcc.medicineGroupsCode) + '</td>' +
				'<td>' + safeValue(roleAcc.medicineUnitsCode) + '</td>' +
				'<td>' + safeValue(roleAcc.medicineTypesCode) + '</td>' +
				'<td style="width: 150px;min-width: 300px;">' + safeValue(roleAcc.ingredient) + '</td>' +
				'<td style="width: 150px;min-width: 300px;">' + safeValue(roleAcc.strength) + '</td>' +
				'<td>' + safeValue(roleAcc.manufacturer) + '</td>' +
				'<td>' + safeValue(roleAcc.originCountry) + '</td>' +
				'<td>' + safeValue(roleAcc.purchasePrice) + '</td>' +
				'<td>' + safeValue(roleAcc.salePrice) + '</td>' +
				'<td>' + safeValue(roleAcc.quantity) + '</td>' +
				'<td>' + safeValue(roleAcc.dateOfManufacture) + '</td>' +
				'<td>' + safeValue(roleAcc.productExpiryDate) + '</td>' +
				'<td>' + safeValue(roleAcc.isActive) + '</td>' +
				'<td>' + safeValue(roleAcc.createdBy) + '</td>' +
				'<td>' + formatDateStr(roleAcc.createdDate) + '</td>' +
				'<td>' + safeValue(roleAcc.updatedBy) + '</td>' +
				'<td>' + formatDateStr(roleAcc.updatedDate) + '</td>' +
				'<td class="text-center min-wd-100">' + getEditBtn(roleAcc.id, roleAcc.medicineImages, roleAcc.code, roleAcc.name, roleAcc.description,
				 roleAcc.medicineGroupsCode, roleAcc.medicineUnitsCode, roleAcc.medicineTypesCode, roleAcc.ingredient, roleAcc.strength, roleAcc.manufacturer, roleAcc.originCountry,
				  roleAcc.purchasePrice, roleAcc.salePrice, roleAcc.quantity, roleAcc.dateOfManufacture, roleAcc.productExpiryDate ) + '</td>' +
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
function getEditBtn(id, medicineImages, code, name, description, medicineGroupsCode, medicineUnitsCode, 
		medicineTypesCode, ingredient, strength, manufacturer, originCountry, purchasePrice, salePrice, quantity, dateOfManufacture, productExpiryDate) {
	return `<button class='btn btn-primary'
                data-id="${id}"
                data-code="${safeValue(code)}"
                data-name="${safeValue(name)}"
                data-description="${safeValue(description)}"
                data-group="${safeValue(medicineGroupsCode)}"
                data-unit="${safeValue(medicineUnitsCode)}"
                data-type="${safeValue(medicineTypesCode)}"
                data-ingredient="${safeValue(ingredient)}"
                data-strength="${safeValue(strength)}"
                data-manufacturer="${safeValue(manufacturer)}"
                data-origin="${safeValue(originCountry)}"
                data-purchaseprice="${safeValue(purchasePrice)}"
                data-saleprice="${safeValue(salePrice)}"
                data-quantity="${safeValue(quantity)}"
                data-dateofmanufacture="${safeValue(dateOfManufacture)}"
                data-productexpirydate="${safeValue(productExpiryDate)}"
                data-medicineimages='${medicineImages}'
                onclick='handleEditClick(this)'>Edit</button>`;
}

function handleEditClick(btn) {
    const id = btn.dataset.id;
    const code = btn.dataset.code;
    const name = btn.dataset.name;
    const description = btn.dataset.description;
    const groupCode = btn.dataset.group;
    const unitCode = btn.dataset.unit;
    const typeCode = btn.dataset.type;
    const ingredient = btn.dataset.ingredient;
    const strength = btn.dataset.strength;
    const manufacturer = btn.dataset.manufacturer;
    const originCountry = btn.dataset.origin;
    const purchasePrice = btn.dataset.purchaseprice;
    const salePrice = btn.dataset.saleprice;
    const quantity = btn.dataset.quantity;
    const dateOfManufacture = btn.dataset.dateofmanufacture;
    const productExpiryDate = btn.dataset.productexpirydate;
    const medicineImages = btn.dataset.medicineimages;

    editRole(id, medicineImages, code, name, description, groupCode, unitCode, typeCode, ingredient,
        strength, manufacturer, originCountry, purchasePrice, salePrice, quantity, dateOfManufacture, productExpiryDate);
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
		fetch(`/api/auth/medicine/delete?id=${id}`)
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
function editRole(id, medicineImages, code, name, description, groupCode, unitCode, typeCode, ingredient, strength, 
manufacturer, originCountry, purchasePrice, salePrice, quantity, dateOfManufacture, productExpiryDate) {
    presentId = id;
    //Rendering the original information
    $('#edit-code-input').val(code);
    $('#edit-name-input').val(name);
    $('#edit-desc-input').val(description);
    /*$('#edit-medicine-groups-code-input').val();
    $('#edit-medicine-units-code-input').val();
    $('#edit-medicine-types-code-input').val();*/
    $('#edit-ingredient-input').val(ingredient);
    $('#edit-strength-input').val(strength);
    $('#edit-manufacturer-input').val(manufacturer);
    $('#edit-origin-country-input').val(originCountry);
    $('#edit-purchase-price-input').val(purchasePrice);
    $('#edit-sale-price-input').val(salePrice);
    $('#edit-quantity-input').val(quantity);
    $('#edit-date-of-manufacture-input').val(dateOfManufacture);
    $('#edit-product-expiry-date-input').val(productExpiryDate);
    
    // Hiển thị ảnh preview
    const previewDiv = document.getElementById("edit-image-preview");
    if (medicineImages && medicineImages.startsWith("data:image")) {
        previewDiv.innerHTML = renderImage(medicineImages);
    } else {
        previewDiv.innerHTML = "<p>Không có ảnh</p>";
    }
    
	loadSelectOptions('/api/auth/medicine/group/medicine-groups', 'edit-medicine-groups-code-input', groupCode);
    loadSelectOptions('/api/auth/medicine/unit/medicine-units', 'edit-medicine-units-code-input', unitCode);
    loadSelectOptions('/api/auth/medicine/type/medicine-types', 'edit-medicine-types-code-input', typeCode);
    $('#editUserModal').modal("toggle");
}

/**
 * Inspection and editing information form
 * @author Bac
 * @date 2025-5-20
 * @param form
 */
function validateEditForm(form) {
    if (!form.code) {
        alert("Please enter the code!");
        return false;
    }
    if (!form.name) {
        alert("Please enter the name!");
        return false;
    }
    if (!form.medicineGroupsCode) {
        alert("Please enter the medicine group code!");
        return false;
    }
    if (!form.medicineUnitsCode) {
        alert("Please enter the medicine unit code!");
        return false;
    }
    if (!form.medicineTypesCode) {
        alert("Please enter the medicine type code!");
        return false;
    }
    if (!form.ingredient) {
        alert("Please enter the ingredient!");
        return false;
    }
    if (!form.strength) {
        alert("Please enter the strength!");
        return false;
    }
    if (!form.manufacturer) {
        alert("Please enter the manufacturer!");
        return false;
    }
    if (!form.purchasePrice) {
        alert("Please enter the purchase price!");
        return false;
    }
    if (!form.salePrice) {
        alert("Please enter the sale price!");
        return false;
    }
    if (!form.quantity) {
        alert("Please enter the quantity!");
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
        code: $('#add-code-input').val(),
        name: $('#add-name-input').val(),
        base64Images: JSON.parse($('#base64-images-hidden').val() || "[]"),
        description: $('#add-desc-input').val(),
        medicineGroupsCode: $('#add-medicine-groups-code-input').val(),
        medicineUnitsCode: $('#add-medicine-units-code-input').val(),
        medicineTypesCode: $('#add-medicine-types-code-input').val(),
        ingredient: $('#add-ingredient-input').val(),
        strength: $('#add-strength-input').val(),
        manufacturer: $('#add-manufacturer-input').val(),
        originCountry: $('#add-origin-country-input').val(),
        purchasePrice: $('#add-purchase-price-input').val(),
        salePrice: $('#add-sale-price-input').val(),
        quantity: $('#add-quantity-input').val(),
        dateOfManufacture: $('#add-date-of-manufacture-input').val(),
        productExpiryDate: $('#add-product-expiry-date-input').val()
    };
}

function loadImage() {
  document.getElementById("add-medicine-images-input").addEventListener("change", function (event) {
    const files = event.target.files;
    let fileNames = [];

    for (let i = 0; i < files.length; i++) {
        fileNames.push(files[i].name); // tên file như 'abc.jpg'
    }
    document.getElementById("add-base64-images-hidden-input").value = fileNames.join(',');
    
    const hiddenInput = document.getElementById("base64-images-hidden");
    const previewContainer = document.getElementById("image-preview-list");

    previewContainer.innerHTML = ""; // Clear old previews
    let base64List = [];
    let filesProcessed = 0;

    Array.from(files).forEach(file => {
      const reader = new FileReader();

      reader.onload = function (e) {
        const base64Image = e.target.result;
        base64List.push(base64Image);

        // Append image preview
        const img = document.createElement("img");
        img.src = base64Image;
        img.style.maxWidth = "100px";
        img.style.marginRight = "10px";
        img.style.marginBottom = "10px";
        previewContainer.appendChild(img);

        // Đảm bảo cập nhật hidden input sau khi tất cả ảnh được đọc
        filesProcessed++;
        if (filesProcessed === files.length) {
          hiddenInput.value = JSON.stringify(base64List);
        }
      };

      reader.readAsDataURL(file); // Convert to base64
    });
  });
}


async function loadMedicineSelectOptions() {
  await Promise.all([
    loadSelectOptions('/api/auth/medicine/group/medicine-groups', 'add-medicine-groups-code-input', ''),   
    loadSelectOptions('/api/auth/medicine/unit/medicine-units', 'add-medicine-units-code-input', ''),
    loadSelectOptions('/api/auth/medicine/type/medicine-types', 'add-medicine-types-code-input', '')
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

function loadImageEdit() {
  document.getElementById("edit-medicine-images-input").addEventListener("change", function (event) {
    const files = event.target.files;
    const hiddenInput = document.getElementById("edit-base64-images-hidden");
    const previewContainer = document.getElementById("edit-image-preview");

    previewContainer.innerHTML = ""; // Clear old previews
    let base64List = [];
    let filesProcessed = 0;

    Array.from(files).forEach(file => {
      const reader = new FileReader();

      reader.onload = function (e) {
        const base64Image = e.target.result;
        base64List.push(base64Image);

        // Append image preview
        const img = document.createElement("img");
        img.src = base64Image;
        img.style.maxWidth = "100px";
        img.style.marginRight = "10px";
        img.style.marginBottom = "10px";
        previewContainer.appendChild(img);

        // Đảm bảo cập nhật hidden input sau khi tất cả ảnh được đọc
        filesProcessed++;
        if (filesProcessed === files.length) {
          hiddenInput.value = JSON.stringify(base64List);
        }
      };

      reader.readAsDataURL(file); // Convert to base64
    });
  });
}

function renderImage(base64) {
  if (!base64 || !base64.startsWith("data:image")) return 'Không có ảnh';
  return `<img src="${base64}" alt="Medicine Image" style="max-height: 60px; max-width: 60px;"/>`;
}