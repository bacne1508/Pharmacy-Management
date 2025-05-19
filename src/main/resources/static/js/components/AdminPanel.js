class AdminPanel extends Component {
	constructor(props) {
		super(props);
		this.state = {
			user: {
				username: sessionStorage.getItem('username'),
				id: sessionStorage.getItem('id'),
				auth: sessionStorage.getItem('auth')
			},
			active: this.props.active,
		}
	}

	// Are you sure you want to log out?
	onLogoutListener() {
		confirm('Are you sure you want to log out？') && postRequest('/logout', null, () => window.location.href = '/index');
	}

	// Trong AdminPanel.js
	renderDOM() {
		const el_html = `
	        <div class="admin-panel-container">
	         	<div class="admin-panel-main-content">
		            <div class="nav-user-container" style="margin-bottom: 1px;">
		                <img class="avatar-lg" src="/images/logo.jpg" />
		                <p class="title">${this.state.user.username}</p>
		            </div>
		            <ul class="nav nav-pills nav-stacked" id="adminMenu"> 
		                <li role="presentation" class="has-submenu" data-menu-id="user-management">
		                    <a href="#" data-toggle="submenu"> 
		                        <i class="fa fa-home"></i> System Management
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;"> 
		                        <li role="presentation" data-menu-id="cinema-manage-list"><a href="/admin/account/manage"><i class="fa fa-list-ul sub-icon"></i> Account Managament</a></li>
		                        <li role="presentation" data-menu-id="role-manage"><a href="/admin/role/manage"><i class="fa fa-id-badge sub-icon"></i> User Management</a></li>
		                    </ul>
		                </li>
		
		                <li role="presentation" class="has-submenu" data-menu-id="pharmacity-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-bars"></i> Category Management
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="user-list"><a href="/admin/customer/list"><i class="fa fa-users sub-icon"></i> Employee</a></li>
		                        <li role="presentation" data-menu-id="role-manage"><a href="/admin/medicine/manage"><i class="fa fa-medkit sub-icon"></i> Medicine</a></li>
		                        <li role="presentation" data-menu-id="role-manage"><a href="/admin/storage/manage"><i class="fa fa-recycle sub-icon"></i> Storage</a></li>
		                    </ul>
		                </li>
		                
						<li role="presentation" class="has-submenu" data-menu-id="partner-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-handshake-o"></i> Business Partner
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="user-list"><a href="/admin/user/list"><i class="fa fa-users sub-icon"></i> Customer</a></li>
		                        <li role="presentation" data-menu-id="role-manage"><a href="/admin/supplier/manage"><i class="fa fa-id-badge sub-icon"></i> Supplier</a></li>
		                    </ul>
		                </li>
		                
						<li role="presentation" class="has-submenu" data-menu-id="transaction-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-credit-card"></i> Transaction
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="user-list"><a href="/admin/prescription/list"><i class="fa fa-pencil-square-o sub-icon"></i> Prescription</a></li>
		                        <li role="presentation" data-menu-id="role-manage"><a href="/admin/bill/manage"><i class="fa fa-money sub-icon"></i> Bill</a></li>
		                    </ul>
		                </li>
		                <li role="presentation" id="logout-menu-item"><a href="#"><i class="fa fa-sign-out"></i> Log out</a></li>
		            </ul>
		        </div>
		        
		   </div>
	    `;
		this.el = createDOMFromString(el_html);
		this.logoutButton = this.el.querySelector(".avatar-lg");
		// Cập nhật cách lấy panelItems để bao gồm cả các mục chính và mục con nếu cần xử lý active chung
		// Hoặc chỉ lấy các mục menu chính nếu chỉ xử lý active cho chúng
		this.panelItems = Array.from(this.el.querySelector("#adminMenu").children).filter(child => child.tagName === 'LI');


		// Xử lý active class
		// Logic xác định `activeMenuId` sẽ phức tạp hơn, cần biết cả trang con nào đang active
		// Ví dụ, nếu trang hiện tại là /admin/cinema/add, thì cả "cinema-management" và "cinema-add-new" nên active
		const currentPath = window.location.pathname;
		let activeMenuId = null;
		let activeSubMenuId = null;

		// Xác định active dựa trên URL (ví dụ đơn giản)
		this.panelItems.forEach(li => {
			const link = li.querySelector('a');
			if (link && link.getAttribute('href') === currentPath) {
				activeMenuId = li.dataset.menuId;
			}
			const submenus = li.querySelectorAll('.submenu li');
			submenus.forEach(subli => {
				const sublink = subli.querySelector('a');
				if (sublink && sublink.getAttribute('href') === currentPath) {
					activeMenuId = li.dataset.menuId; // Mục cha cũng active
					activeSubMenuId = subli.dataset.menuId;
				}
			});
		});

		// Gán sự kiện cho nút logout
		this.logoutMenuItem = this.el.querySelector("#logout-menu-item a");
		this.logoutMenuItem.addEventListener('click', () => this.onLogoutListener());

		// Áp dụng class active
		this.panelItems.forEach(li => {
			if (li.dataset.menuId === activeMenuId) {
				li.classList.add('active');
				// Nếu mục cha có menu con và nó đang active, có thể muốn mở menu con đó ra
				const submenu = li.querySelector('.submenu');
				if (submenu && activeSubMenuId) { // Chỉ mở nếu có submenu con active
					submenu.style.display = 'block';
					const arrowIcon = li.querySelector('.submenu-arrow i');
					if (arrowIcon) arrowIcon.classList.replace('fa-chevron-down', 'fa-chevron-up');
				}
			}
			if (li.querySelector('.submenu')) { // Gán lại sự kiện cho các mục có submenu
				const submenus = li.querySelectorAll('.submenu li');
				submenus.forEach(subli => {
					if (subli.dataset.menuId === activeSubMenuId) {
						subli.classList.add('active');
					}
				});
			}
		});


		/*this.logoutButton.addEventListener('click', () => this.onLogoutListener());*/

		// Thêm event listener cho các mục có menu con
		this.el.querySelectorAll('a[data-toggle="submenu"]').forEach(toggleLink => {
			toggleLink.addEventListener('click', (event) => {
				event.preventDefault(); // Ngăn chuyển trang nếu href="#"
				const parentLi = toggleLink.closest('.has-submenu');
				const submenu = parentLi.querySelector('.submenu');
				const arrowIcon = toggleLink.querySelector('.submenu-arrow i');

				if (submenu.style.display === 'none' || submenu.style.display === '') {
					submenu.style.display = 'block';
					if (arrowIcon) arrowIcon.classList.replace('fa-chevron-down', 'fa-chevron-up');
				} else {
					submenu.style.display = 'none';
					if (arrowIcon) arrowIcon.classList.replace('fa-chevron-up', 'fa-chevron-down');
				}
			});
		});


		// Permission display
		if (this.state.user.auth != 1) {
			for (let i = this.panelItems.length - 1; i >= 0; i--) {
				if (i >= 2)
					this.panelItems[i].remove();
				if (i === this.state.active)
					this.panelItems[i].querySelector("a").href = "#";
			}
		}

		return this.el;
	}
}