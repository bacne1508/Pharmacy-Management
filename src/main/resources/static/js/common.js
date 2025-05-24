function formatDate(element) {
	let date = new Date(element); // Chuyển chuỗi thành Date object
	let year = date.getFullYear();
	let month = (date.getMonth() + 1).toString().padStart(2, '0'); // Tháng (01-12)
	let day = date.getDate().toString().padStart(2, '0'); // Ngày (01-31)
	return `${year}-${month}-${day}`;
}

/**
 * cut string
 * @param str
 * @returns str
 */
function cutString(str) {
	if (str != null && str != '') {
		if (str.length > 2) {
			if (str.indexOf('["') == 0) {
				str = str.substring(2, str.length);
			}
			if (str.indexOf('"]') == str.length - 2) {
				str = str.substring(0, str.length - 2);
			}
		}
	}

	return str;
}

/**
 * ajax search method post
 * @param url
 * @param condition
 * @param tableId
 * @param element
 * @param event
 */
function ajaxSearch(url, condition, tableId, element, event) {
	event.preventDefault();

	var me = $(element);
	if (me.data('requestRunning')) {
		return;
	}
	me.data('requestRunning', true);

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data) {
			$("#" + tableId).html(data);
		},
		complete : function(result) {
			me.data('requestRunning', false);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/**
 * Redirect url method GET
 * 
 * @param url
 * @returns
 */
function redirect(url) {
    var ua        = navigator.userAgent.toLowerCase(),
        isIE      = ua.indexOf('msie') !== -1,
        version   = parseInt(ua.substr(4, 2), 10);

    // Internet Explorer 8 and lower
    if (isIE && version < 9) {
        var link = document.createElement('a');
        link.href = url;
        document.body.appendChild(link);
        link.click();
    }

    // All other browsers can use the standard window.location.href (they don't lose HTTP_REFERER like IE8 & lower does)
    else { 
        window.location.href = url; 
    }
}

/**
 * Open popup confirm
 * 
 * @param msgConfirm
 * @param methodCallback
 * @returns
 */
function popupConfirm( msgConfirm, methodCallback) {
	bootbox.setLocale( APP_LOCALE );
	return bootbox.confirm( msgConfirm, methodCallback ); 
}

/**
 * Open popup confirm
 * 
 * @param msgConfirm
 * @param methodCallback
 * @returns
 */
function popupConfirmWithButtons( msgConfirm, buttons, methodCallback) {
	bootbox.setLocale( APP_LOCALE );
	
	return bootbox.confirm({
		message: msgConfirm,
	    buttons: buttons,
	    callback: methodCallback
	}); 
}

/**
 * Open popup alert
 * 
 * @param msgAlert
 * @returns
 */
function popupAlert( msgAlert ) {
	bootbox.setLocale( APP_LOCALE );
	return bootbox.alert( msgAlert ); 
}

/**
 * Validation checked list
 * @param elementList
 * @returns boolean
 */
function validationCheckedList( elementList ) {
	var isChecked = true;
	
	$(elementList).each(function() {
    	if( $(this).is(':checked') == false ) {
    		isChecked = false;
    		return false;
    	};
	});
	
	return isChecked;
}

function parseNumber2(valStr) {
	if ($.trim(valStr).length > 0) {
		valStr = $.parseNumber(valStr, {
			format : FORMAT_NUMBER,
			locale : APP_LOCALE
		});
		return parseFloat(valStr);
	} else {
		return 0;
	}
}

function formatNumber(element, formatNumber) {
	if( formatNumber == null || formatNumber == '' ) {
		formatNumber = FORMAT_NUMBER;
	}
	
    $(element).parseNumber({format: formatNumber, locale: APP_LOCALE});
    $(element).formatNumber({format: formatNumber, locale: APP_LOCALE});
}

/**
 * ajax redirect method get
 * @param url
 */
function ajaxRedirect(url) {
	$.ajax({
		type : "GET",
		url : url,
		success : function(data) {
			var content = $(data).find('.body-content');
			$(".main_content").html(content);
			window.history.pushState('', '', url);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/**
 * ajax submit method POST
 * @param url
 * @param condition
 * @param event
 */
function ajaxSubmit(url, condition, event) {
	event.preventDefault();

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data) {
			var content = $(data).find('.body-content');
			$(".main_content").html(content);
			
			var urlPage = $(data).find('#url').val();
			if (urlPage != null && urlPage != '') {
				window.history.pushState('', '', BASE_URL + urlPage);
			}
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

function isNumber(evt, element) {

    var charCode = (evt.which) ? evt.which : event.keyCode
    // only accept number in range 0->9 and . and backspace 		
    if ((charCode >= 48 && charCode <= 57) || charCode == 46 || charCode == 8){
    	if (charCode == 46) {
    		// only 1 element .
    		var text = $(element).val();
            if (text.toString().indexOf(".") != -1) {
                return false;
            }
        }
    	return true;
    }        
    return false;
}  

function isFormatVnd(number){
	return Number(number).toLocaleString('vi-VN');
}

function formatDateStr(dateStr) {
	if (!dateStr) return '';
	const date = new Date(dateStr);
	if (isNaN(date)) return '';
	return date.toLocaleDateString('vi-VN'); // "dd/MM/yyyy" format theo locale Việt Nam
}

function safeValue(value) {
	return value == null ? '' : value;
}