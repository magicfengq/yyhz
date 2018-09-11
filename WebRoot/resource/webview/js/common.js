function setImg(obj) {
	var len = $(obj).find("img");
	for(var i = 0; i < len.length; i++) {
		var src = $(obj).find("img").eq(i).attr("src");
		$(obj).find("img").css({
			opacity: '0'
			/*display: 'none'*/
		})
		var w = $(obj).find("img").eq(i).parent().outerWidth();
		$(obj).find("img").eq(i).parent().css({
			height: w + 'px',
			background: 'url(' + src + ') no-repeat',
			backgroundPosition: '50% 50%',
			backgroundSize: 'cover'
		});
	}
}

function setImgList(obj) {
	var len = $(obj).find("img");
	for(var i = 0; i < len.length; i++) {
		var src = $(obj).find("img").eq(i).attr("src");
		$(obj).find("img").eq(i).css({
			opacity: 0
		});
		var w = $(obj).find("img").eq(i).parent().width();
		$(obj).find("img").eq(i).parent().css({
			height: w + 'px',
			background: 'url(' + src + ') no-repeat',
			backgroundPosition: '50% 50%',
			backgroundSize: 'cover'
		});
	}
}
//设置字号
function setFontSize(obj, MinSize, MaxSize) {
	var size = parseInt($(obj).css("font-size"));
	if(size == MinSize) {
		$(obj).css({
			fontSize: MaxSize + 'px'
		});
	} else if(size == MaxSize) {
		$(obj).css({
			fontSize: MinSize + 'px'
		});
	}
}
//点赞
//function like(obj){
//	var urlArr=['dz_icon.png','active_dz_icon.png'];
//	var index=0;
//	var url=$(obj).css("background-image");
//	var newArr=url.split('/');
//	var imgName=newArr[newArr.length-1].substring(0,newArr[newArr.length-1].length-2);
//	
//	for(var i=0; i<urlArr.length; i++){
//		if(urlArr[i]==imgName){
//			index=i;
//		}
//	}
//	if(index==0){
//		$(obj).css("background-image","url(img/"+urlArr[1]+")");
//	}else if(index==1){
//		$(obj).css("background-image","url(img/"+urlArr[0]+")");
//	}
//}//有bug
function like(obj) {
	var urlArr = ['dz_icon01.png', 'dz_icon02.png'];
	var index;
	var url = $(obj).css("background-image");
	for(var i = 0; i < urlArr.length; i++) {
		if(url.indexOf(urlArr[i]) > 0) {
			index = i;
		}
	}
	if(index == 0) {
		var newUrl = url.replace(/dz_icon01.png/, urlArr[1]);
		$(obj).css("background-image", newUrl);
	} else if(index == 1) {
		var newUrl = url.replace(/dz_icon02.png/, urlArr[0]);
		$(obj).css("background-image", newUrl);
	}
}
//工作日志列表
function set_workList(obj, target) {
	var obj = $(obj);
	var target = $(target);
	var li = obj.find("li");
	for(var i = 0; i < li.length; i++) {
		var title = obj.find("li").eq(i).find(".theme").text();
		var mark = title.substring(0, 4);
		var listH = parseInt(obj.find("li").eq(i).height() + 15);
		var li_obj = '<li><div class="pointer"></div><span></span></li>';
		target.append(li_obj);
		target.find("li").eq(i).find("span").height(listH - 30);
		//				if(i>2){
		//					target.find("li").eq(i).find(".pointer").addClass("bg-orange");
		//					target.find("li").eq(1).find(".pointer").addClass("bg-red");
		//					target.find("li").eq(2).find(".pointer").addClass("bg-blue");
		//				}
		switch(mark) {
			case '工作记录':
				target.find("li").eq(i).find(".pointer").addClass("bg-orange");
				break;

			case '我预约了':
				target.find("li").eq(i).find(".pointer").addClass("bg-red");
				break;

			case '提醒预约':
				target.find("li").eq(i).find(".pointer").addClass("bg-blue");
				break;
		}
	}
}
//验证码倒计时读秒
var wait=60;
function time(o){
	if(wait==0){
		$(o).removeAttr("disabled");
		$(o).css({background:'#1c7ddd'});
		$(o).text("发送验证码");
		wait=60;
	}else{
		$(o).attr("disabled",true);
		$(o).css({background:'#c5c5c5'});
		$(o).text("重新发送(" + wait + "s)");
		wait--;
		setTimeout(function(){
			time(o);
		},1000);
	}
}
//等比例缩放图片
function clacImgZoomParam(maxWidth, maxHeight, width, height) {
	var param = {
		top: 0,
		left: 0,
		width: width,
		height: height
	};
	if(width > maxWidth || height > maxHeight) {
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;
		//图片满铺
		if(rateWidth > rateHeight) {//宽大于高
			param.height = maxHeight;
			param.width = Math.round(width / rateHeight);
			
		} else {//高大于宽
			param.width = maxWidth;
			param.height = Math.round(height / rateWidth);
		}
		//图片居中，空余补白
//		if( rateWidth > rateHeight ) { 
//			param.width = maxWidth; 
//			param.height = Math.round(height / rateWidth); 
//		}else { 
//			param.width = Math.round(width / rateHeight); 
//			param.height = maxHeight; 
//		} 
	}

	param.left = Math.round((maxWidth - param.width) / 2);
	param.top = Math.round((maxHeight - param.height) / 2);
	return param;
}
//取消关注
$(".attention").on("touchend",function(){
	if($(this).hasClass("active")){
		$(this).removeClass("active");
		$(this).find("i").removeClass("fa-undo").addClass("fa-plus");
		$(this).find("s").text("关注");
	}else{
		$(this).addClass("active");
		$(this).find("i").addClass("fa-undo").removeClass("fa-plus");
		$(this).find("s").text("取消关注");
	}
});
//资料、卡片、场秀切换
//$(".myCenter_content_t li").on("touchend",function(){
//	var index=$(this).index();
//	switch (index){
//		case 0:
//			window.location.href="他的资料.html";
//			break;
//		case 1:
//			window.location.href="他的卡片.html";
//			break;
//		case 2:
//			window.location.href="他的秀场.html";
//			break;
//		default:
//			alert("亲页面丢失了！");
//			break;
//	}
//});
