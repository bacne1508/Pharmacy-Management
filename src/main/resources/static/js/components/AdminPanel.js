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
		                        <li role="presentation" data-menu-id="employ-manage"><a href="/employ/manage"><i class="fa fa-users sub-icon"></i> Employee</a></li>
		                        <li role="presentation" data-menu-id="customer-manage"><a href="/user/manage"><i class="fa fa-user-circle sub-icon"></i> Customer</a></li>
		                    </ul>
		                </li>
		
		                <li role="presentation" class="has-submenu" data-menu-id="pharmacity-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-bars"></i> Category Medicine Management
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="medicine-manage"><a href="/admin/medicine/manage"><i class="fa fa-medkit sub-icon"></i> Medicine Management</a></li>
		                        <li role="presentation" data-menu-id="medicine-group-manage"><a href="/admin/medicine/group/manage"><i class="fa fa-user-md sub-icon"></i> Medicine Group Management</a></li>
		                        <li role="presentation" data-menu-id="medicine-type-manage"><a href="/admin/medicine/type/manage"><i class="fa fa-plus-square sub-icon"></i> Medicine Type Management</a></li>
		                        <li role="presentation" data-menu-id="medicine-unit-manage"><a href="/admin/medicine/unit/manage"><i class="fa fa-ticket sub-icon"></i> Medicine Unit Management</a></li>
		                        <li role="presentation" data-menu-id="storage-manage"><a href="/admin/medicine/storage/manage"><i class="fa fa-recycle sub-icon"></i> Storage Management</a></li>
		                        <li role="presentation" data-menu-id="stock-manage"><a href="/admin/medicine/stock/manage"><i class="fa fa-recycle sub-icon"></i> Stock Management</a></li>
		                        <li role="presentation" data-menu-id="branch-manage"><a href="/admin/medicine/branch/manage"><i class="fa fa-building sub-icon"></i> Branch Management</a></li>
		                        <li role="presentation" data-menu-id="supplier-manage"><a href="/admin/medicine/supplier/manage"><i class="fa fa-id-badge sub-icon"></i> Supplier</a></li>
		                    </ul>
		                </li>
		                
						<li role="presentation" class="has-submenu" data-menu-id="purchase-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-shopping-bag"></i> Purchase management
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="order-request-manage"><a href="/admin/purchase/order/request/manage"><i class="fa fa-id-badge sub-icon"></i> Order Request Management</a></li>
		                        <li role="presentation" data-menu-id="order-manage"><a href="/admin/purchase/order/manage"><i class="fa fa-id-badge sub-icon"></i> Order Management</a></li>
		                        <li role="presentation" data-menu-id="bill-manage"><a href="/admin/purchase/bill/manage"><i class="fa fa-money sub-icon"></i> Bill</a></li>
		                        <li role="presentation" data-menu-id="purchase-history-manage"><a href="/admin/purchase/history/manage"><i class="fa fa-cogs sub-icon"></i> Purchase History</a></li>
		                    </ul>
		                </li>
		                
						<li role="presentation" class="has-submenu" data-menu-id="warehouse-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-credit-card"></i> Warehouse and inventory
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="prescription-manage"><a href="/admin/inventory/manage"><i class="fa fa-hourglass sub-icon"></i> Inventory Management</a></li>
		                        
		                    </ul>
		                </li>
		                
						<li role="presentation" class="has-submenu" data-menu-id="report-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-file"></i> Reports and Statistics
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="prescription-manage"><a href="/admin/inventory/report/list"><i class="fa fa-file-pdf-o sub-icon"></i> Inventory report by expiry</a></li>
		                        <li role="presentation" data-menu-id="prescription-manage"><a href="/admin/import/report/list"><i class="fa fa-file-pdf-o sub-icon"></i> Report of import - export - inventory by day, month, year</a></li>
		                        <li role="presentation" data-menu-id="prescription-manage"><a href="/admin/revenue/report/list"><i class="fa fa-file-pdf-o sub-icon"></i> Report revenue and profit by item</a></li>
		                        
		                    </ul>
		                </li>
		                
						<li role="presentation" class="has-submenu" data-menu-id="payment-management">
		                    <a href="#" data-toggle="submenu">
		                        <i class="fa fa-credit-card"></i> Payment
		                        <span class="submenu-arrow"><i class="fa fa-chevron-down"></i></span>
		                    </a>
		                    <ul class="submenu nav nav-pills nav-stacked" style="display: none;">
		                        <li role="presentation" data-menu-id="prescription-manage"><a href="/admin/revenue/manage"><i class="fa fa-hourglass sub-icon"></i> Revenue by item</a></li>
		                        
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