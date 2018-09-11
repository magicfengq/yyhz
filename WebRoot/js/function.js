$(document).ready(function () {
 $(window).on("load resize",function () {
	var w=$(".photo").width();
	var ww=$(window).width();
	$(".photo,.name_info").height(w);
	/*$(".person_info").css("padding-left",w+25);*/
	$(".comment li").each(function () {
		if (ww>600) {
		//alert(147);
		var w1=$(this).find(".b2").height();
		var w2=$(this).find(".comment_box").height();
		$(".b1").height(w);
		if (w>w1+w2) {
			$(this).find(".comment_block").height(w+10);
		}
		else if(w<w1+w2){
			$(this).find(".comment_block").height(w1+w2+10);
		}
		
	}else{$(this).find(".comment_block").height("auto");}
	});
	
	
	});
});