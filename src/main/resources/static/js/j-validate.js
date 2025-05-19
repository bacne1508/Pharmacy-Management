$(document).ready(function() {
	
//	jQuery.validator.addMethod("username", function (value, element, params) {
//	    return this.optional( element ) || /^[a-zA-Z0-9]+$/.test( value );
//	}, "Username not valid");
	
	jQuery.validator.addMethod("code", function (value, element, params) {
	    return this.optional( element ) || /^[a-zA-Z0-9-_]+$/.test( value );
	}, "Code must have no special characters and spaces");
	
	jQuery.validator.addMethod("currency", function (value, element, params) {
	    return this.optional( element ) || /^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?\.[0-9]{1,2}$/.test( value );
	}, "Currency must be input with right format");

	jQuery.validator.addMethod("phone", function(value, element, params) {
			return this.optional( element ) || /^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$/.test( value );
		}, "Allow only number and special characters: (, ), +");

	jQuery.validator.addMethod("multiemail", function (value, element) {
	    if (this.optional(element)) {
	        return true;
	    }

	    var emails = value.split(','),
	        valid = true;

	    for (var i = 0, limit = emails.length; i < limit; i++) {
	        value = emails[i];
	        valid = valid && jQuery.validator.methods.email.call(this, value, element);
	    }

	    return valid;
	}, "Invalid email format: please use a comma to separate multiple email addresses.");
	
	jQuery.validator.addMethod("int", function (value, element, params) {
		if (this.optional(element)) {
	        return true;
	    }
		if (isNormalInteger(value))
		{
		   return true;
		}
		
	}, "Input must be integer");
	
	
	jQuery.validator.addClassRules({
//			"j-name":{
//				username:true
//			},
			"j-required":{
				required:true
			},
		    "j-remote":{
		    	remote:true
		    },
		    "j-email":{
		    	email:true
		    },
		    "j-url":{
		    	url:true
		    },
		    "j-date":{
		    	date:true
		    },
		    "j-dateISO":{
		    	dateISO:true
		    },
		    "j-number":{
		    	number:true
		    },
		    "j-digits":{
		    	digits:true
		    },
		    "j-creditcard":{
		    	creditcard:true
		    },
		    "j-equalTo":{
		    	equalTo:name
		    },
		    "j-accept":{
		    	accept:true
		    },
		    "j-maxlength":{
		    	maxlength:255
		    },
		    "j-maxlength-10":{
		    	maxlength:10
		    },
		    "j-minlength":{
		    	minlength:10
		    },
		    "j-rangelength":{
		    	rangelength:[10,255]
		    },
		    "j-range":{
		    	range:[10,100000]
		    },
		    "j-max":{
		    	max:10000
		    },
		    "j-min":{
		    	min:10
		    },
		    "j-code":{
		    	code:true
		    },
		    "j-currency":{
		    	currency:true
		    },
		    "j-multiemail":{
		    	multiemail:true
		    },
		    "j-int":{
		    	int:true
		    },
		    "j-phone":{
				phone: true
			}
		});
	
	$.validator.setDefaults({
	    ignore: "",
		errorPlacement: function (error, element) {
			if (element.hasClass('select2-hidden-accessible') && element.next('.select2-container').length) {
				error.insertAfter(element.next('.select2-container'));
			} else if (element.parent().hasClass("datepicker")) {
				error.insertAfter(element.parent(".datepicker"));
			} else if (element.parent().hasClass('input-group')) {
				  error.insertAfter( element.parent() );
			} else {
				error.insertAfter(element);
			}
		}
	});

	
//	$(".j-currency").maskMoney({thousands:',', decimal:'.', allowNegative: true, allowZero:true,precision: 2, suffix: ' VND'});
});

function isNormalInteger(str) {
    var n = Math.floor(Number(str));
    return n !== Infinity && String(n) === str && n >= 0;
}