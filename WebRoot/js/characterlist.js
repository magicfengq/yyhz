$(document).ready(function () {
 $(window).on("load resize",function () {
	var w1=$(".c_photo").width();
	var w2=$(".actor_pic").width();
	$(".c_photo,.c_name_info").height(w1);
	$(".c_person_info").css("padding-left",w1+11);
	$(".actorlist,.actor_detail").height(w2);
	$(".actor_detail").css("padding-left",w2+6);
	});
	$(".script_detail").hide();
     $(".c_1,.c_2").click(function(){
       $(this).find("h2").toggleClass("up");
 	   $(this).find(".script_detail").slideToggle();
 });
});