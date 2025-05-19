let currentPage = 0;
const size = 5;

$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	const searchContainer = document.querySelector(".account-search-list");
	const searchHtml = `
	    <div class="row form-group align-item-center">
	        <label class="col-sm-2 col-form-label">Username</label>
	        <div class="col-sm-4">
	            <input type="text" class="form-control" name="username" />
	        </div>
	    </div>
	`;
	searchContainer.innerHTML = searchHtml;
	/*getAccountList();*/

	/*function getAccountList() {
		getRequest(
			'/api/auth/admin/account/all',
			function(res) {
				renderRole(res.content);
			},
			function(error) {
				alert(error);
			}
		);
	}*/

	// Initial load
	fetchUsers(currentPage);
});

function fetchUsers(page) {
	fetch(`/api/auth/admin/account/all?page=${page}&size=${size}`)
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
			'<td>' + roleAcc.username + '</td>' +
			'<td>' + roleAcc.password + '</td>' +
			'<td>' + roleAcc.role + '</td>' +
			'<td class="text-center min-wd-100">' + getEditBtn(roleAcc.password, roleAcc.id, roleAcc.username, roleAcc.auth) + '</td>' +
			'<td class="text-center min-wd-100">' + getDelBtn(roleAcc.password, roleAcc.id, roleAcc.auth) + '</td>' +
			'</tr>';
	}
	$('#role-table').html(roleTableContent);
}

/**
 * Get the HTML of the edit button
 * @author Bac
 * @date 2025-5-20
 * @param password
 * @param id
 * @return
 */
function getEditBtn(password, id, username, auth) {
	if (auth == 1) {
		return "<button class='btn btn-primary' disabled='disabled' onclick='editRole(\"" + id + "\",\"" + username + "\",\"" + password + "\",\"" + auth + "\")'>Edit</button>";
	} else {
		return "<button class='btn btn-primary' onclick='editRole(\"" + id + "\",\"" + username + "\",\"" + password + "\",\"" + auth + "\")'>Edit</button>";
	}
}

/**
 * Get the HTML of the edit button
 * @author Bac
 * @date 2025-5-20
 * @param password
 * @param id
 * @return
 */
function getDelBtn(password, id, auth) {
	if (auth == 1) {
		return "<button class='btn btn-danger' disabled='disabled' onclick='delRole(\"" + id + "\")'>Delete</button>";
	} else {
		return "<button class='btn btn-danger' onclick='delRole(\"" + id + "\")'>Delete</button>";
	}
}