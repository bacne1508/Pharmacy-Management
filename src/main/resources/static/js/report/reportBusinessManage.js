let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".user-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">File Name</label>
	        <div class="col-sm-3">
	            <input type="text" class="form-control" id="fileNameInput"  name="fileName" />
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
		document.getElementById('fileNameInput').value = '';
		currentPage = 0;
		fetchUsersSearch(currentPage, size);
	});
});

// Hàm fetch data search
function fetchUsersSearch(page, size) {
	const fileNameInput = document.getElementById('fileNameInput').value.trim();

	let url = `/api/auth/report/report-business/all?page=${page}&size=${size}`;
	if (fileNameInput) {
		url += `&fileName=${encodeURIComponent(fileNameInput)}`;
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
	fetch(`/api/auth/report/report-business/all?page=${page}&size=${size}`)
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
 * @author Bac
 * @date 2025-6-14
 * @param roles
 */
function renderRole(roles) {
	var roleTableContent = '';
	if(roles.message !== "No data"){
		for (let roleAcc of roles) {
			roleTableContent +=
				'<tr>' +
				'<td class="text-center min-wd-100">' + getDownloadPdf(roleAcc.id) + ' ' + getViewPdf(roleAcc.id) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.fileName) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.fileSize) + ' ' + 'Kb' + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.fileType) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.description) + '</td>' +
				'<td class="text-center">' + safeValue(roleAcc.createdBy) + '</td>' +
				'<td class="text-center">' + formatDateStr(roleAcc.createdDate) + '</td>' +
				'</tr>';
		}
	}
	$('#user-table').html(roleTableContent);
}

function getDownloadPdf(id) {
    return `<a href="/api/auth/report/report-business/download/${id}" class="btn btn-sm btn-primary">
                <i class="fas fa-download"></i> Download
            </a>`;
}

function getViewPdf(id) {
    return `<a href="/api/auth/report/report-business/view/${id}" class="btn btn-sm btn-info btn-view" target="_blank">
                <i class="fa-solid fa-eye"></i> View
            </a>`;
}
