$(document).ready(function () {

    $("#signUp-btn").click(function (event) {
		event.preventDefault(); 
        var formData = getSignUpForm();
        if (!validateSignUpForm(formData)) {
            return;
        }

        postRequest(
            '/api/auth/register',
            formData,
            function (res) {
                if (res.success) {
                    alert("Register successfully");
                    window.location.href = "/index";
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    });

    function getSignUpForm() {
        return {
            username: $('#signUp-name').val(),
            password: $('#signUp-password').val(),
            secondPassword: $('#signUp-second-password').val()
        };
    }

    function validateSignUpForm(data) {
        var isValidate = true;
        if (!data.username || data.username.length < 4 || data.username.length > 10) {
            isValidate = false;
            $('#signUp-name').parent('.input-group').addClass('has-error');
            $('#signUp-name-error').css("visibility", "visible");
        }
        if (!data.password || data.password.length < 6 || data.password.length > 12) {
            isValidate = false;
            $('#signUp-password').parent('.input-group').addClass('has-error');
            $('#signUp-password-error').css("visibility", "visible");
        }

        if (!data.secondPassword) {
            isValidate = false;
            $('#signUp-second-password').parent('.input-group').addClass('has-error');
            $('#signUp-second-password-error').css("visibility", "visible");
            $('#signUp-second-password-error').text("Please enter the password again");
        } else if (data.secondPassword != data.password) {
            isValidate = false;
            $('#signUp-second-password').parent('.input-group').addClass('has-error');
            $('#signUp-second-password-error').css("visibility", "visible");
            $('#signUp-second-password-error').text("Two input passwords are inconsistent");
        }

        return isValidate;
    }
});