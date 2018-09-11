<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" >
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
 		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<title>秀一秀</title>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/style.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/font-awesome-4.6.3/css/font-awesome.min.css"/>
		<!--点击图片放大strat-->
		<link rel="stylesheet" type="text/css" href="resource/webview/css/mui.min.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/preview.css"/>
		<!--点击图片放大end-->
		<style type="text/css">
			.mui-content{background-color: #FFFFFF;}
			.mui-segmented-control{font-size: 12px;}
			.mui-h2,h2{font-size: 12px;}
			.mui-table-view{background-color: #ececec;}
			.bottom-panel .title-box{margin: 0;}
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active{color: #555555;position: relative;}
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active:after{content: "";width: 0;height: 0;border-left: 10px solid transparent;border-right: 10px solid transparent;border-bottom: 10px solid #ececec;position: absolute;left: 15px;bottom: 0;}
			.mui-table-view-cell:after,.mui-table-view-cell:after{background-color:#ececec;}
			.mui-table-view-cell{padding: 10px;padding-top: 0;}
			.mui-table-view-cell h2{flex: 1;margin-left: 10px;font-size: 14px}
			.mui-table-view-cell:first-child{padding-top: 10px;}
			.mui-segmented-control.mui-scroll-wrapper .mui-scroll{width: 100%;}
			.mui-segmented-control.mui-scroll-wrapper .mui-control-item{padding: 0;}
			.mui-slider .mui-segmented-control.mui-segmented-control-inverted~.mui-slider-group .mui-slider-item{border-top:none;border-bottom: none;}
		</style>
	</head>
	<body>
		<div class="wrapper">
			<div class="top-panel">
				<div class="show-title flex">
					<div class="img-container" style="width: 42px;height: 42px;">
						<img src="${showInfo['showDetail']['headImageUrl']}" width="100%"/>
					</div>
					<div class="title-info flex">
						<span class="name">${showInfo['showDetail']['name']}</span>
						<span class="time">${showInfo['showDetail']['publishTime']}</span>
					</div>
				</div>
				
				<div class="show-content">
					<p>${showInfo['showDetail']['showDetail']}</p>
					<div class="item-pic-box clearfix">
						<c:if test="${showInfo['showDetail']['mediaType'] eq '0'}">
							<c:forEach var="show" items="${showInfo['showDetail']['imageList']}">
								<div class="item-pic-container fl" style="width: 75px;height: 75px;">
									<img src="${show['imageUrl']}" data-preview-src="" data-preview-group="1" width="100%"/>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${showInfo['showDetail']['mediaType'] eq '1'}">
							<video class="video video-js vjs-default-skin vjs-big-play-centered" poster="${showInfo['showDetail']['videoPreviewUrl']}" preload="metadata" controls="controls"  width="100%" style="" height="100%" data-setup="{}" style="background:transparent url('resource/webview/img/head_bg.png') 50% 50% no-repeat;" src="${showInfo['showDetail']['videoUrl']}"/>
						</c:if>
					</div>
				</div>
			</div>
			<div class="bottom-panel">
				<div class="mui-content">
					<div id="slider" class="mui-slider">
						<div id="sliderSegmentedControl" class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
							<div class="mui-scroll title-box flex">
								<a class="mui-control-item mui-active tl" href="#item1mobile">
									评论：${showInfo['showDetail']['commentNumber']}
								</a>
								<a class="mui-control-item tr" href="#item2mobile">
									点赞：${showInfo['showDetail']['praiseNumber']}
								</a>
							</div>
						</div>
						<div class="mui-slider-group tabs_box" style="">
							<!--评论start-->
							<div id="item1mobile" class="mui-slider-item mui-control-content mui-active">
								<div class="mui-scroll-wrapper">
									<div class="mui-scroll">
										<ul class="mui-table-view">
											<c:forEach var="comment" items="${showInfo['showComment']['rows']}">
												<li class="mui-table-view-cell">
													<div class="title-info flex">
														<div class="img-container" style="width: 30px;height: 30px;">
															<img src="${comment['headImageUrl']}" width="100%"/>
														</div>
														<h2>${comment['name']}</h2>
													</div>
													<div class="title-content">
														<p class="content">${comment['commentDetail']}</p>
														<p class="time">${comment['commentTime']}</p>
														<span></span>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
							<!--评论end-->
							<!--点赞start-->
							<div id="item2mobile" class="mui-slider-item mui-control-content">
								<div id="scroll1" class="comment-box">
									<ul class="like-img-list clearfix">
										<c:forEach var="praise" items="${showInfo['showPraise']['rows']}">
											<li>
												<div class="img_c_container" style="width: 30px;height: 30px;">
													<img src="${praise['headImageUrl']}" width="100%"/>
												</div>
											</li>
										</c:forEach>
									</ul>
								</div>	
							</div>
							<!--点赞end-->
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="resource/webview/js/jquery.min.js"></script>
		<script src="resource/webview/js/common.js"></script>
		<!--点击图片放大strat-->
		<script src="resource/webview/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="resource/webview/js/mui.zoom.js" type="text/javascript" charset="utf-8"></script>
		<script src="resource/webview/js/mui.previewimage.js" type="text/javascript" charset="utf-8"></script>
		<!--点击图片放大end-->
		<!--下拉刷新start-->
		<script src="resource/webview/js/mui.pullToRefresh.js" type="text/javascript" charset="utf-8"></script>
		<script src="resource/webview/js/mui.pullToRefresh.material.js" type="text/javascript" charset="utf-8"></script>
		<!--下拉刷新end-->
		<script>
		//图片放大
		mui.previewImage();
		//上拉加载
		mui.init();
		(function($) {
			//阻尼系数
			var deceleration = mui.os.ios?0.003:0.0009;
			$('.mui-scroll-wrapper').scroll({
				bounce: false,
				indicators: true, //是否显示滚动条
				deceleration:deceleration
			});
			$.ready(function() {
				//循环初始化所有下拉刷新，上拉加载。
				$.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
					$(pullRefreshEl).pullToRefresh({
//							down: {
//								callback: function() {
//									var self = this;
//									setTimeout(function() {
//										var ul = self.element.querySelector('.mui-table-view');
//										ul.insertBefore(createFragment(ul, index, 10, true), ul.firstChild);
//										self.endPullDownToRefresh();
//									}, 1000);
//								}
//							},
						up: {
							callback: function() {
								var self = this;
								setTimeout(function() {
									var ul = self.element.querySelector('.mui-table-view');
									loadComment(ul, self);
								}, 1000);
							}
						}
					});
				});
				
				function loadComment(ul, caller) {
					page = Math.min(page + 1, pages + 1);
			    	$.ajax({ url:'api/getShowCommentList.do',//从该处获取数据
			    		type:'post',
			    		dataType:'json',
			    		data:{'page':page, 'pageSize':pageSize, 'showId':id},
				   	    success:function(ret){
				   	    	if(ret.result == 1) {
					   	    	var data = ret.data;
					   	    	pages = data.pages;
					   	    	total = data.total;
					   	    	pageSize = data.pageSize;
	   	    	
					   	    	for(var index in data.rows) {
					   	    		var commment = data.rows[index];
						   	        var html = '<li class="mui-table-view-cell">';
									html += '<div class="title-info flex">';
									html += '<div class="img-container" style="width: 30px;height: 30px;">';
									html += '<img src="' + commment.headImageUrl + '" width="100%"/>';
									html += '</div>';
									html += '<h2>' + commment.name + '</h2>';
									html += '</div>';
									html += '<div class="title-content">';
									html += '<p class="content">' + commment.commentDetail + '</p>';
									html += '<p class="time">' + commment.commentTime + '</p>';
									html += 'span></span>';
									html += '</div>';
									html += '</li>';

									ul.innerHTML += html;
					   	    	}
					   	    						   	    	
					   	     	caller.endPullUpToRefresh();
				   	    	}
				   	    }
			    	});
				};
			});
		})(mui);

		$(function(){
			//头像图片
			setImg(".show-title .img-container");
			//评论头像
			setImg(".title-info .img-container");
			//点赞头像
			setImg(".like-img-list .img_c_container");
			//秀一秀图像
			setImg(".item-pic-box .item-pic-container");

			$('.mui-slider-group').height($(window).height()-$('.top-panel').height());
			
		});
		</script>
		<script>
			page = ${showInfo['showComment']["page"]};
			pages = ${showInfo['showComment']["pages"]};
			pageSize = ${showInfo['showComment']["pageSize"]};
			total = ${showInfo['showComment']["total"]};
			id = "${showInfo['showDetail']['id']}";
			setImg(".evaluate-list .evaluate-p");
		</script>
	</body>
</html>
