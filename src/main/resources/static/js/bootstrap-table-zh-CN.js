(function (global, factory) {
  if (typeof define === "function" && define.amd) {
    define([], factory);
  } else if (typeof exports !== "undefined") {
    factory();
  } else {
    var mod = {
      exports: {}
    };
    factory();
    global.bootstrapTableZhCN = mod.exports;
  }
})(this, function () {
  'use strict';

  /**
   * Bootstrap Table Chinese translation
   * Author: Zhixin Wen<wenzhixin2010@gmail.com>
   */
  (function ($) {
    $.fn.bootstrapTable.locales['zh-CN'] = {
      formatLoadingMessage: function formatLoadingMessage() {
        return 'Try to load the data, please wait';
      },
      formatRecordsPerPage: function formatRecordsPerPage(pageNumber) {
	    return 'Showing ' + pageNumber + ' records per page';
	  },
	  formatShowingRows: function formatShowingRows(pageFrom, pageTo, totalRows) {
	    return 'Showing ' + pageFrom + ' to ' + pageTo + ' records, Total entries: ' + totalRows + ',';
	  },
	  formatDetailPagination: function formatDetailPagination(totalRows) {
	    return 'Total ' + totalRows + ' records';
	  },
      formatSearch: function formatSearch() {
        return 'search';
      },
      formatNoMatches: function formatNoMatches() {
        return 'No record of matching';
      },
      formatPaginationSwitch: function formatPaginationSwitch() {
        return 'hide/Display pagination';
      },
      formatRefresh: function formatRefresh() {
        return 'refresh';
      },
      formatToggle: function formatToggle() {
        return 'Switch';
      },
      formatColumns: function formatColumns() {
        return 'List';
      },
      formatFullscreen: function formatFullscreen() {
        return 'full screen';
      },
      formatAllRows: function formatAllRows() {
        return 'all';
      },
      formatAutoRefresh: function formatAutoRefresh() {
        return 'Automatically refresh';
      },
      formatExport: function formatExport() {
        return 'Export data';
      },
      formatClearFilters: function formatClearFilters() {
        return 'Empty filtration';
      },
      formatJumpto: function formatJumpto() {
        return 'Jump';
      },
      formatAdvancedSearch: function formatAdvancedSearch() {
        return 'High -level search';
      },
      formatAdvancedCloseButton: function formatAdvancedCloseButton() {
        return 'closure';
      }
    };

    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);
  })(jQuery);
});