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
	/*getMovieList();*/

	$("#movie-form-btn").click(function() {
		var formData = getMovieForm();
		if (!validateMovieForm(formData)) {
			return;
		}
		postRequest(
			'/movie/add',
			formData,
			function(res) {
				getMovieList();
				$("#movieModal").modal('hide');
			},
			function(error) {
				alert(error);
			});
	});

	function getMovieForm() {
		return {
			name: $('#movie-name-input').val(),
			startDate: $('#movie-date-input').val(),
			posterUrl: $('#movie-img-input').val(),
			description: $('#movie-description-input').val(),
			type: $('#movie-type-input').val(),
			length: $('#movie-length-input').val(),
			country: $('#movie-country-input').val(),
			starring: $('#movie-star-input').val(),
			director: $('#movie-director-input').val(),
			screenWriter: $('#movie-writer-input').val(),
			language: $('#movie-language-input').val()
		};
	}

	function getMovieList() {
		getRequest(
			'/movie/all',
			function(res) {
				renderMovieList(res.content);
			},
			function(error) {
				alert(error);
			}
		);
	}

	function renderMovieList(list) {
		$('.movie-on-list').empty();
		// Click event of movieItem to jump to details
		const onItemClick = movieId => window.location.href = "/admin/movieDetail?id=" + movieId;

		list.forEach(movie => {
			movie.description = movie.description || '';
			const movieItem = createDOMFromString(`<div></div>`);
			mount(new MovieItem({ movie: movie, onDetailClick: movieId => onItemClick(movieId) }), movieItem);
			$('.movie-on-list').append(movieItem);
		});
	}

	/**
	 * @Date:   2019-5-7
	 * @Author: moon
	 * @Intro:  Verify form information integrity
	 */
	function validateMovieForm(data) {
		if (!data.name) {
			$('#movie-name-input').parent('.form-group').addClass('has-error');
			alert("Please fill in the movie title!");
			return false;
		}
		if (!data.posterUrl) {
			$('#movie-img-input').parent('.form-group').addClass('has-error');
			alert("Please fill in the movie poster!");
			return false;
		}
		if (!data.startDate) {
			$('#movie-date-input').parent('.form-group').addClass('has-error');
			alert("Please fill in the show time!");
			return false;
		}
		if (!data.length) {
			$('#movie-length-input').parent('.form-group').addClass('has-error');
			alert("Please fill in the length of the film!");
			return false;
		}
		return true;
	}
});