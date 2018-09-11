$(function(){
				$(".select_menu").mouseenter(function(){
					$(".second_menu").stop().slideDown();
				});
				$(".select_menu").mouseleave(function(){
					$(".second_menu").stop().slideUp();
				});
				$(".second_menu").mouseenter(function(){
					$(this).find("span").css("border-bottom","0px");
				});
				$(".second_menu").mouseleave(function(){
					$(this).find("span").css("border-bottom","1px solid #e6e6e6");
				});
				//下层页banner
				var w=$(window).width();
				$(".banner").height(w/3);
		});