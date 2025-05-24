$(document).ready(function() {
	mount(new AdminPanel({ active: 0 }), document.querySelector(".nav-left-container"));
	// Cập nhật giờ và ngày cho dashboard
	const timeElement = document.getElementById('dashboardCurrentTime');
	const dateElement = document.getElementById('dashboardCurrentDate');

	function updateDateTime() {
		const now = new Date();

		// Định dạng giờ: HH:MM:SS
		const hours = String(now.getHours()).padStart(2, '0');
		const minutes = String(now.getMinutes()).padStart(2, '0');
		const seconds = String(now.getSeconds()).padStart(2, '0');
		if (timeElement) {
			timeElement.textContent = `${hours}:${minutes}:${seconds}`;
		}

		// Định dạng ngày: Thứ X, DD Tháng MM, YYYY (Tiếng Việt)
		const daysOfWeek = ["Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"];
		const dayName = daysOfWeek[now.getDay()];
		const dayOfMonth = String(now.getDate()).padStart(2, '0');
		const month = String(now.getMonth() + 1).padStart(2, '0'); // Tháng trong JS từ 0-11
		const year = now.getFullYear();
		if (dateElement) {
			dateElement.textContent = `${dayName}, ${dayOfMonth} Tháng ${month}, ${year}`;
		}
	}

	if (timeElement || dateElement) {
		updateDateTime(); // Cập nhật lần đầu
		setInterval(updateDateTime, 1000); // Cập nhật mỗi giây
	}
});