var myScroll;
var pullDownFlag,pullUpFlag;
var pullDown,pullUp;
var spinner;
var page, pages, pageSize, total;
var siteId;
var layerIndex;
function positionJudge(){
    if(this.y>55){    //判断下拉
        pullDown.innerHTML = "放开刷新页面";
        pullDownFlag = 1;
    }else if(this.y<(this.maxScrollY-40)){   //判断上拉
        pullUp.innerHTML = "放开加载页面";
        pullUpFlag = 1;
    }
}
function action(){
    if(pullDownFlag==1){
        pullDownAction();
        pullDown.innerHTML = "下拉刷新…";
        pullDownFlag = 0;
    }else if(pullUpFlag==1){
        pullUpAction();
        pullUp.innerHTML = "上拉加载…";
        pullUpFlag = 0;
    }
}
function loaded(){
    pullDownFlag = 0;
    pullUpFlag = 0;
    pullDown = document.getElementById("pullDown");
    pullUp = document.getElementById("pullUp");
    spinner = document.getElementById("spinner");
    myScroll = new IScroll("#wrapper",{
        probeType: 3,
        click:true,
//        momentum: false,//关闭惯性滑动
//      mouseWheel: true,//鼠标滑轮开启
//      scrollbars: true,//滚动条可见
//      fadeScrollbars: true,//滚动条渐隐
//      interactiveScrollbars: true,//滚动条可拖动
//      shrinkScrollbars: 'scale', // 当滚动边界之外的滚动条是由少量的收缩
        useTransform: true,//CSS转化
        useTransition: true,//CSS过渡
        bounce: true,//反弹
        freeScroll: true,//只能在一个方向上滑动
//      startX: 0,
        startY: 0,
//        snap: "li",//以 li 为单位
    });
    myScroll.on('scroll',positionJudge);
    myScroll.on("scrollEnd",action);
}
function pullDownAction(){
    layer.open({
        type: 2
        ,content: '加载中'
    });
    spinner.style.display = "block";
    setTimeout(function(){
       	window.location.reload(); 
        spinner.style.display = "none";
        myScroll.refresh();
    },1000);
}
function pullUpAction(){
    layerIndex = layer.open({
        type: 2
        ,content: '加载中'
    });
    spinner.style.display = "block";
    setTimeout(function(){
    	page = Math.min(page + 1, pages + 1);
    	$.ajax({ url:'api/getCommentList.do',//从该处获取数据
    		type:'post',
    		dataType:'json',
    		data:{'page':page, 'pageSize':pageSize, 'id':siteId},
	   	    success:function(ret){
	   	    	if(ret.result == 1) {
		   	    	var data = ret.data;
		   	    	pages = data.pages;
		   	    	total = data.total;
		   	    	pageSize = data.pageSize;
		   	    	for(var index in data.comments) {
		   	    		var comment = data.comments[index];
			   	        var html='<li class="clearfix">';
			         	html+='<div class="evaluate-p float-left">';
			         	html+='<img src="' + (comment.headImgUrl || 'resource/webview/img/head_bg.png') + '" width="100%"/>';
			         	html+='</div>';
			         	html+='<div class="evaluate-c-t float-left clearfix">';
			         	html+='<span class="float-left">' + comment.createrName + '</span>';
			         	html+='<span class="float-right c999">' + comment.createTime + '</span>';
			         	html+='</div>';
			         	html+='<b class="clearfix"></b>';
			         	html+='<p>' + comment.content + '</p>';
			         	html+='</li>';
			         	
				     	$(".evaluate-list").append(html);
		   	    	}
		   	    }
	   	     	setImg(".evaluate-list .evaluate-p");
                layer.close(layerIndex);
	   	        $("#pullUp").html("数据已加载完");
	   	        spinner.style.display = "none";
	   	        myScroll.refresh();
	   	    }
    	});
    },1000);

    /*
     * 
     * 		for(SiteInfo item : pageInfo.getRows()) {
			Map<String, Object> siteInfoMap = new HashMap<String, Object>();
			siteInfoMap.put("id", item.getId());
			siteInfoMap.put("creater", item.getCreater());
			siteInfoMap.put("content", item.getContent());
			siteInfoMap.put("siteId", item.getSiteId());
			siteInfoMap.put("createTime", DateUtils.getDateTimeFormat(item.getCreateTime()));
			siteInfoMap.put("createrName", item.getCreaterName());
			if(item.getSystemPictureInfo() != null) {
				siteInfoMap.put("headImgUrl", Configurations.getDownloadFileUrlPrefix() + item.getSystemPictureInfo().getUrlPath());
			}
			
			comments.add(siteInfoMap);
		}
		
		ret.put("comments", comments);
		ret.put("page", pageInfo.getPage());
		ret.put("pageSize", pageInfo.getPageSize());
		ret.put("total", pageInfo.getTotal());
		ret.put("pages", pageInfo.getPages());

    setTimeout(function(){
        var html='<li class="clearfix">';
        	html+='<div class="evaluate-p float-left">';
        	html+='<img src="img/head_bg.png" width="100%"/>';
        	html+='</div>';
        	html+='<div class="evaluate-c-t float-left clearfix">';
        	html+='<span class="float-left">张伯伦</span>';
        	html+='<span class="float-right c999">2016.02.03</span>';
        	html+='</div>';
        	html+='<b class="clearfix"></b>';
        	html+='<p>2017年3月12日朱家角皇家大酒店需婚礼主持人不错啊，非常好啊</p>';
        	html+='</li>';
    	for(var i=0 ;i<5; i++){
    		$(".evaluate-list").append(html);
    	}
    	setImg(".evaluate-list .evaluate-p");
        spinner.style.display = "none";
        myScroll.refresh();
    },1000);
    */
}
function updatePosition(){
pullDown.innerHTML = this.y>>0;
}
document.addEventListener('touchmove', function (e) {
    e.preventDefault();
}, false);