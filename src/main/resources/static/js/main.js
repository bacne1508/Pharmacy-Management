function openForm() {
	  document.getElementById("myForm").style.display = "block";
	}
	
	function closeForm() {
	  document.getElementById("myForm").style.display = "none";
	}

    // Kiểm tra nếu trang web đang tải xong, đảm bảo chat popup ẩn ngay từ đầu
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("myForm").style.display = "none";
    });