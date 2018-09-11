$(function () {
	$(window).on("load resize",function(){
		var w=$(".c_photos a").width();
		var ww=$("#video").width();
		$(".photo_pic").height($(".photo_pic").width());
	    $(".c_photos a").height(w*7/5);
	    $("#video").height(ww*3/4);
	     $(".alertbox").height($(window).height()-20);
	     $(".v_photo").click(function () {
	     	$(".alertbox").css("opacity","1");
    	    $("body").addClass("boxer-open");
    	    $(".alertbox").css("position","fixed");    	   
	     });
	      $(".alertbox").click(function () {
    	    $(".alertbox").fadeOut();
    	  });	     
	});
	 
});