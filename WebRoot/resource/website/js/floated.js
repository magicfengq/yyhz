$(function(){
//悬浮层
				$(".items li").each(function(){
					$(this).mouseenter(function(){
						$(this).addClass("selected").siblings().removeClass("selected");
							$(this).find(".wx_box").show();
					});
					$(this).mouseleave(function(){
						$(".items li").removeClass("selected");
						$(this).find(".wx_box").hide();
					});
					
				});
});