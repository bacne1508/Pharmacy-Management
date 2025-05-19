/*!
 * vinhlt custom
 */
(function ($) {
	$.fn.select2ToTree = function (options) {
		
		if(options=="destroy"){
			var s2inst = this.select2();
			var s2data = s2inst.data("select2");
			if(isNotNull(s2data) && $(s2data.$dropdown.find(".select2-results"))[0].classList.contains('jstree')){
				if(typeof s2data.$dropdown.find(".select2-results").jstree().settings!='undefined'){
					s2data.$dropdown.find(".select2-results").jstree().unbind('changed.jstree').unbind('loaded.jstree').unbind('select_node.jstree');
					s2data.$dropdown.find(".select2-results").jstree('destroy');
				}
			}
			if (isNotNull(s2data))
				s2data.$dropdown.find(".select2-search__field").unbind('input');
			this.select2().unbind('select2:open').unbind('select2:closing');
			this.select2('destroy');
			this.find('option').remove();
		}else{
			var opts = $.extend({}, options);
			opts._templateResult = opts.templateResult;
			opts.templateResult = function (data, container) {
				var label = data.text;
				if (typeof opts._templateResult === "function") {
					label = opts._templateResult(data, container);
				}
				var $iteme = $("<span class='item-label'></span>").append(label);
				if (data.element) {
					var ele = data.element;
					container.setAttribute("data-val", ele.value);
					if (ele.className) container.className += " " + ele.className;
					if (ele.getAttribute("data-pup")) {
						container.setAttribute("data-pup", ele.getAttribute("data-pup"));
					}
					if ($(container).hasClass("non-leaf")) {
						return $.merge($('<span class="expand-collapse" onmouseup="expColMouseupHandler(event);"></span>'), $iteme);
					}
				}
				return $iteme;
			};
	
			window.expColMouseupHandler = function (evt) {
				toggleSubOptions(evt.target || evt.srcElement);
				/* prevent Select2 from doing "select2:selecting","select2:unselecting","select2:closing" */
				evt.stopPropagation ? evt.stopPropagation() : evt.cancelBubble = true;
				evt.preventDefault ? evt.preventDefault() : evt.returnValue = false;
			}
			
			var s2inst = this.select2(opts).on('select2:clearing',function(e){
				$(this).attr('id-tree','');
			});
			
			
			
			//select tag
			var formParent = this;
			s2inst.on("select2:open", function (evt) {
				var s2data = s2inst.data("select2");
				s2data.$dropdown.addClass("s2-to-tree");
				s2data.$dropdown.removeClass("searching-result");
				var $allsch = s2data.$dropdown.find(".select2-search__field").add( s2data.$container.find(".select2-search__field") );
				$allsch.off("input", inputHandler);
				$allsch.on("input", inputHandler);
				
				if(typeof opts["core"]!== 'undefined'&&opts["core"]!=={}){
					if(!$(s2data.$dropdown.find(".select2-results"))[0].classList.contains('jstree')){
						var viewMode = opts.view_mode;
						s2data.$dropdown.find(".select2-results").on('changed.jstree', function(e, data) {
							//extended of changed_jstree
							if(typeof opts.changed_jstree !== 'undefined'){
								opts.changed_jstree(e,data);
							}
							formParent.select2("close");
			        	}).on('select_node.jstree', function(e, data) {
			        		buildSelectTree(data.node, formParent,viewMode);
			        		//extended of select_node_jstree
							if(typeof opts.select_node_jstree !== 'undefined'){
								opts.select_node_jstree(e,data);
							}
			        	}).jstree({
			            	core:opts["core"],
				            search:opts["search"],
			                plugins: opts["plugins"]
			            }).bind('loaded.jstree', function(e, data) {
			            	var treeSelect = $(this).jstree(true);
			            	if(typeof $(formParent).attr("id-tree") !== 'undefined' &&$(formParent).attr("id-tree") !== ''){
								var nodeSelect = treeSelect.get_node($(formParent).attr("id-tree"));
								if(typeof nodeSelect !== 'undefined'){
									if(typeof nodeSelect.parent !== 'undefined' && nodeSelect.parent !== ""&&nodeSelect.parent!=='#'){
										treeSelect.open_node("#"+nodeSelect.parent);
									}else{
										treeSelect.open_node("#"+$(formParent).attr("id-tree"));
									}
									treeSelect.select_node("#"+$(formParent).attr("id-tree"));
								}
			            	}
			            });
						s2data.$dropdown.find(".select2-search__field").bind("input",function(e){
							e.preventDefault();
							s2data.$dropdown.find(".select2-results").jstree('search',$(this).val());	
						});
					} else {
						var treeSelect = s2data.$dropdown.find(".select2-results").jstree(true);
		            	if(typeof $(formParent).attr("id-tree") !== 'undefined' &&$(formParent).attr("id-tree") !== ''){
							var nodeSelect = treeSelect.get_node($(formParent).attr("id-tree"));
							if(typeof nodeSelect !== 'undefined'){
								if(typeof nodeSelect.parent !== 'undefined' && nodeSelect.parent !== "" && nodeSelect.parent !== "#"){
									treeSelect.open_node($("#"+nodeSelect.parent));
								}else{
									treeSelect.open_node($("#"+$(formParent).attr("id-tree")));
								}
								treeSelect.select_node($("#"+$(formParent).attr("id-tree")));
							}
		            	}
					}
				}
				
				
				
			}).on("select2:closing", function (evt) {
				var s2data = s2inst.data("select2");
				s2data.$dropdown.find(".select2-results").jstree("clear_search");
			});
			if(typeof opts["core"]!== 'undefined'&&opts["core"]!=={}){
				if(typeof $(formParent).attr("id-tree") !== 'undefined' &&$(formParent).attr("id-tree") !== ''){
					s2inst.trigger('select2:open');
		    	}
			}
			
	
			/* Show search result options even if they are collapsed */
			function inputHandler(evt) {
				var s2data = s2inst.data("select2");
				if ($(this).val().trim().length > 0) {
					s2data.$dropdown.addClass("searching-result");
				}
				else {
					s2data.$dropdown.removeClass("searching-result");
				}
			}
		}
		
		return s2inst;
	};

	
	//vinhlt custome add new funtion to select node in tree
	function buildSelectTree(nodeSelect, $el,viewMode) {
		function buildOptions(dataArr,view_list,separator) {
			var data = nodeSelect || {};
			var $opt = $("<option></option>");
			if(typeof view_list !== 'undefined' && view_list !== []){
				var texts = [];
				for(var i =0; i<view_list.length;i++){
					if(isNotNull(data.original[view_list[i]]))
						texts.push(data.original?data.original[view_list[i]]:'');
				}
				$opt.text(texts.join(separator));
			}else{
				$opt.text(data["text"]);
			}
			$opt.val(data.original?data.original["treeId"]:'');
			$opt.attr("id",data["id"]);
			$opt.prop("selected", true);
			if($opt.val() === "") {
				$opt.prop("disabled", true);
				$opt.val(getUniqueValue());
			}
			$el.html($opt);
			$el.attr("id-tree",data["id"]);
		}
		buildOptions(nodeSelect,viewMode.view_list,viewMode.separator);
	}

	var uniqueIdx = 1;
	function getUniqueValue() {
		return "autoUniqueVal_" + uniqueIdx++;
	}

	function toggleSubOptions(target) {
		$(target.parentNode).toggleClass("opened");
		showHideSub(target.parentNode);
	}

	function showHideSub(ele) {
		var curEle = ele;
		var $options = $(ele).parent(".select2-results__options");
		var shouldShow = true;
		do {
			var pup = ($(curEle).attr("data-pup") || "").replace(/'/g, "\\'");
			curEle = null;
			if (pup) {
				var pupEle = $options.find(".select2-results__option[data-val='" + pup + "']");
				if (pupEle.length > 0) {
					if (!pupEle.eq(0).hasClass("opened")) { // hide current node if any parent node is collapsed
						$(ele).removeClass("showme");
						shouldShow = false;
						break;
					}
					curEle = pupEle[0];
				}
			}
		} while (curEle);
		if (shouldShow) $(ele).addClass("showme");

		var val = ($(ele).attr("data-val") || "").replace(/'/g, "\\'");
		$options.find(".select2-results__option[data-pup='" + val + "']").each(function () {
			showHideSub(this);
		});
	}
})(jQuery);
