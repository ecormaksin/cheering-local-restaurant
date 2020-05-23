;(function(){
	"use strict";

	$('form').on('submit', function() {
		$("*[data-prevent-double-transmit='true']").prop('disabled', true);
	});
})();
