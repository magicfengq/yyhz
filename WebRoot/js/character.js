$(document).ready(function () {
	$(window).on("load resize",function () {
		var ww=$(window).innerWidth();
		var w=$(".c_photo").width();
		$(".character_detail").width(ww-w-18);
		var h=$(".character_name").height();
	    $(".c_photo,.character_detail").height(w);
	    $(".characteritic").css("margin-top",w-h-26+'px');
	});
	
});