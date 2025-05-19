(function($) {
	
	var datatables;
	
    datatables = function( options ) {
    
        // Establish our default settings
        var settings = $.extend({
            url          : null,
            type         : "GET",
            dataDefault  : null,
            setData      : null, // Function
            complete     : null
        }, options);
        
        var defaults = datatables.defaults;
        
        var boxTables = this;
        // Registry event for list page
        var pageLst = boxTables.find(".pagination a");
        pageLst.each( function() {
            $(this).on('click', function( event ) {
            	event.preventDefault();
            	
            	if ( $.isFunction( settings.setData ) ) {
            		settings.dataDefault = settings.setData.call( this );
                    settings.dataDefault = trimData(settings.dataDefault);
                }
            	
            	var elementThis = $(this);
            	
            	var params = settings.dataDefault;
      				if (params == null) {
      					params = [];
      				}
				var page = elementThis.data("page");
            	params["page"] = page;
      				/* page of Size */
      				var pageSizeSelected = boxTables.find(".form-control.input-sm.select-page.select2");
      				if (pageSizeSelected != null && pageSizeSelected.length > 0) {
      					var pageSize = $(pageSizeSelected).val();
      					params["pageSize"] = parseInt(pageSize);
      				}
            	
            	$.ajax({
                    url : settings.url,
                    type: settings.type,
                    data : params,
                    success:function(data, textStatus, jqXHR) {
                    	if ( $.isFunction( settings.complete ) ) {
                    		settings.complete( data );
                        } else {
                        	drawTable( boxTables, data );
                        }
                    	
                    	anchorTop( elementThis );
    				},
    				error : function(xhr, textStatus, error) {
    					console.log(xhr);
    					console.log(textStatus);
    					console.log(error);
    				}
                });
            });
        });
        
        // Registry event for list page
        var pageSelected = boxTables.find(".pagination-select select.select-page");
        $(pageSelected).on('change', function( event ) {
        	event.preventDefault();
        	
        	if ( $.isFunction( settings.setData ) ) {
        		settings.dataDefault = settings.setData.call( this );
                settings.dataDefault = trimData(settings.dataDefault);
            }
        	
        	var elementThis = $(this);
        	
        	var params = settings.dataDefault;
        	var page = elementThis.val();
        	params["page"] = page;
			/* page of Size */
			var pageSizeSelected = boxTables.find(".page-size-select select.select-page");
			if (pageSizeSelected != null) {
				var pageSize = $(pageSizeSelected).val();
				params["pageSize"] = parseInt(pageSize);
			}
        	
        	$.ajax({
                url : settings.url,
                type: settings.type,
                data : params,
                success:function(data, textStatus, jqXHR) {
                	if ( $.isFunction( settings.complete ) ) {
                		settings.complete( data );
                    } else {
                    	drawTable( boxTables, data );
                    }
                	
                	anchorTop( elementThis );
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
                }
            });
        });
		var pageSizeSelected = boxTables.find(".page-size-select select.select-page");
		$(pageSizeSelected).on('change', function(event) {
			event.preventDefault();
			if ($.isFunction(settings.setData)) {
				settings.dataDefault = settings.setData.call(this);
                settings.dataDefault = trimData(settings.dataDefault);
			}

			var elementThis = $(this);

			var params = settings.dataDefault;
			var pageSize = elementThis.val();
			params["pageSize"] = pageSize;
			$.ajax({
				url : settings.url,
				type : settings.type,
				data : params,
				success : function(data, textStatus, jqXHR) {
					if ($.isFunction(settings.complete)) {
						settings.complete(data);
					} else {
						drawTable(boxTables, data);
					}

					anchorTop(elementThis);
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
		});
		// @author: HungHT
		var gotoPage = boxTables.find(".btn-goto-page");
		$(".txt-goto-page").on('click', function() { 
			$(this).select(); 
		});
		$(".txt-goto-page").unbind('keydown').bind("keydown", function(event) {
			if(event.which == 13) {
				$(gotoPage).trigger('click');
		  	}
	    });
		$(gotoPage).unbind('click').bind('click', function(event) {
			event.preventDefault();
			if ($.isFunction(settings.setData)) {
				settings.dataDefault = settings.setData.call(this);
                settings.dataDefault = trimData(settings.dataDefault);
			}
			var elementThis = $(this);

			var params = settings.dataDefault;
			if (params == null) {
				params = [];
			}
			var elementPage = elementThis.parent().parent().parent().find(".txt-goto-page");
			var totalPage = elementThis.data("page");
			var page = elementPage.val();
			if (page < 1) {
				page = 1;
			}
			if (page > totalPage) {
				page = totalPage;
			}
			params["page"] = page;
			/* page of Size */
			var pageSizeSelected = boxTables.find(".page-size-select select.select-page");
			if (pageSizeSelected != null && pageSizeSelected.length > 0) {
				var pageSize = $(pageSizeSelected).val();
				params["pageSize"] = parseInt(pageSize);
			}

			$.ajax({
				url : settings.url,
				type : settings.type,
				data : params,
				success : function(data, textStatus, jqXHR) {
					if ($.isFunction(settings.complete)) {
						settings.complete(data);
					} else {
						drawTable(boxTables, data);

					}
					anchorTop(elementThis);
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
		});
        // Show message no data
        var tbody = boxTables.find("table.table tbody");
        var tbodyData = tbody.html();
        tbodyData = $.trim(tbodyData)
        if( tbodyData == "" ) {
        	 var countCol = boxTables.find("table.table thead th").length;
    		 var tr = $("<tr>", {	
    	         "class"     : "odd"
    	     });
    		 
			 var td = $("<td>", {	
		         "class"     : "dataTables_empty",
		         "valign"    : "top",
		         "colspan"   : countCol
		     });
			 td.text(MSG_EMPTY_TABLE);
			 
			 tr.append(td);
			 tbody.append(tr);
        }
        
        function drawTable( element, data ) {
        	element.html(data);
        }
        
        function anchorTop( element ) {
        	var popup = element.parents("div.modal-dialog");
        	
        	var eBody = "body";
        	if( popup.hasClass("modal-dialog") ) {
        		eBody = "";
        	}
        	
        	if( eBody != null && eBody != '' ) {
        		$('html, body').animate({
        	        scrollTop: $(eBody).offset().top
        	    }, 0);
        	}
        }
    }
    
    datatables.defaults = {
		"language": {
	    	"emptyTable"    :     "No data"
	    }
    }
    
    $.fn.datatables = datatables;
    
    $.fn.datatables.defaults = datatables.defaults;
    
    return $.fn.datatables;
}(jQuery));