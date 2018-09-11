$(function(){
	//lightbox
	$(window).on("load",function(){
		$('.boxer').boxer({
			labels: {
				close: "关闭",
				count: "/",
				next: "下一个",
				previous: "上一个"
			}
		});
	});
});