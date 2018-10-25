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
		<link rel="stylesheet" type="text/css" href="css/app/style.css"/>
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
		<div class="downloadDiv" onclick="downloadApp()">
			<div class="layoutDiv">
				<div class="logoDiv">
					<img class="logo" src="resource/icon/20170704104736.png"></img>
					<span class="appname">中华名角</span>
				</div>
				<span class="downloadButton">免费下载</span>
			</div>
		</div>
		<div class="wrapper-padding-bottom-60">
			
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
					<p style="margin-top:10px;margin-left:10px;margin-right:10px;margin-bottom:10px;line-height: 20px;">
						${showInfo['showDetail']['showDetail']}
					</p>
					<div class="clearfix">
						<c:if test="${showInfo['showDetail']['mediaType'] eq '0'}">
							<c:forEach var="show" items="${showInfo['showDetail']['imageList']}">
								<div class="item-pic-container fl" style="width: 100%">
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
