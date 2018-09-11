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
		<title>个人主页</title>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/font-awesome-4.6.3/css/font-awesome.min.css"/>
		<!--点击图片放大strat-->
		<link rel="stylesheet" type="text/css" href="resource/webview/css/mui.min.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/preview.css"/>
		<!--点击图片放大end-->
		<link rel="stylesheet" type="text/css" href="resource/webview/js/video-js/video-js.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/style.css"/>
	</head>
	<body>
		<div class="myCenter_header">
			<c:choose>
				<c:when test="${not empty hisInfo['info']['headImgUrl']}">
					<img class="head_bg" src="${hisInfo['info']['headImgUrl']}" width="100%" height="100%"/>
				</c:when>
				<c:otherwise>
					<img class="head_bg" src="resource/webview/img/head_bg.png" width="100%" height="100%"/>
				</c:otherwise>
			</c:choose>
			<div class="zz"></div>
			<div class="flex p-flex">
				<c:choose>
					<c:when test="${hisInfo['info']['authenticateLevel'] == 1}">
						<div class="grade-box grade-green">
							实名认证
						</div>
					</c:when>
					<c:when test="${hisInfo['info']['authenticateLevel'] == 2}">
						<div class="grade-box grade-yellow">
							资历认证
						</div>
					</c:when>
					<c:otherwise>
						<div class="grade-box grade-red">
							未认证
						</div>
					</c:otherwise>
				</c:choose>
				<div class="head_p">
					<div class="head_p_c">
						<div class="img_c">
							<img src="${hisInfo['info']['headImgUrl']}" width="100%" data-preview-src="" data-preview-group="999999999" />
						</div>
					</div>
				</div>
				<div class="name-box">
					${hisInfo['info']['nameFmt']}
				</div>
			</div>
			<!--星星-->
			<span onclick="getActorCommentList('${hisInfo['info']['id']}',1)" class="evaluate-btn star"></span>
			<!--心-->
			<span onclick="getActorCommentList('${hisInfo['info']['id']}',0)" class="evaluate-btn heart"></span>

		</div>
		<div class="myCenter_content">
			<div class="mui-content">
				<div id="slider" class="mui-slider">
					<div id="sliderSegmentedControl" class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
						<div class="mui-scroll myCenter_content_t">
							<a class="mui-control-item mui-active" href="#item1mobile">
								资料
							</a>
							<a class="mui-control-item" href="#item2mobile">
								卡片
							</a>
							<a class="mui-control-item" href="#item3mobile">
								秀场
							</a>
						</div>
					</div>
					<div class="mui-slider-group">
						<!--资料start-->
						<div id="item1mobile" class="mui-slider-item mui-control-content mui-active" style="height: 400px;overflow-y:auto;">
							<div id="mui-scroll-wrapper">
								<div class="">
									
									<ul class="info-1">
										<li class="clearfix">
											<span class="float-left">艺名：</span>
											<span class="float-left">${hisInfo['info']['name']}</span>
										</li>
										<li class="clearfix">
											<span class="float-left">性别：</span>
											<span class="float-left">
												<c:choose>
													<c:when test="${hisInfo['info']['sex'] == 1}">男</c:when>
													<c:otherwise>女</c:otherwise>
												</c:choose>
											</span>
										</li>
										<li class="clearfix">
											<span class="float-left">所在城市：</span>
											<span class="float-left">${hisInfo['info']['city']}</span>
										</li>
										<li class="clearfix">
											<span class="float-left">角色类型：</span>
											<span class="float-left" style="width: 58%;">
												<c:forEach var="roleName" items="${hisInfo['info']['roleName']}">
													<em>${roleName}</em>
												</c:forEach>
											</span>
										</li>
									</ul>
									<ul class="info-2">
										<li class="clearfix">
											<span class="float-left">出生日期：</span>
											<span class="float-left">${hisInfo['info']['birthDate']}</span>
										</li>
										<li class="clearfix">
											<span class="float-left">身高：</span>
											<span class="float-left">${hisInfo['info']['height']}</span>
										</li>
										<li class="clearfix">
											<span class="float-left">体重：</span>
											<span class="float-left">${hisInfo['info']['weight']}</span>
										</li>
										<li class="clearfix">
											<span class="float-left">鞋码：</span>
											<span class="float-left">${hisInfo['info']['shoesSize']}</span>
										</li>
										<li class="clearfix">
											<span class="float-left">三围：</span>
											<span class="float-left">${hisInfo['info']['size']}</span>
										</li>
									</ul>
									<!--添加个人简介-->
									<ul class="info-2">
										<li class="clearfix">
											<span class="float-left">个人简介：</span>
											<span class="float-left">${hisInfo['info']['introduction']}</span>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<!--资料end-->
						<!--卡片start-->
						<div id="item2mobile" class="mui-slider-item mui-control-content myCenter_content_c card" style="height: 400px;">
							<div class="mui-scroll-wrapper">
								<div class="mui-scroll">
									<ul class="mui-table-view">
										<c:forEach var="card" items="${hisInfo['cardInfo']['rows']}" varStatus="status">
											<li class="mui-table-view-cell" onclick="getCardDetail('${card['id']}','${hisInfo['info']['name']}','${card['cardName']}','${hisInfo['info']['headImgUrl']}','${card['firstImgUrl']}','${card['creater']}','${card['publicType']}')">
												<div class="clearfix">
													<!-- <span class="card-label float-left">${card['cardName']}</span> -->
													<span class="card-label float-left">卡片${status.count}:&nbsp;&nbsp;${card['publicType']}</span>
												</div>
												<div class="card-pics">
													<div class="clearfix">
														<c:forEach var="imgUrl" items="${card['cardImgUrls']}">
															<div class="card-img"><img src="${imgUrl}" width="100%" data-preview-src="" data-preview-group="1" /></div>
														</c:forEach>
													</div>
												</div>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<!--卡片end-->
						<!--秀场start-->
						<div id="item3mobile" class="mui-slider-item mui-control-content myCenter_content_c card" style="height: 400px;">
							<div class="mui-scroll-wrapper">
								<div class="mui-scroll">
									<ul class="mui-table-view">
										<c:forEach var="show" items="${hisInfo['showInfo']['rows']}">
											<li class="mui-table-view-cell" onclick="getShowDetail('${show['id']}','${show['type']}','${show['creater']}')">
												<div class="clearfix">
													<span class="card-label float-left" style="width: auto;">${show['createTime']}</span>
												</div>
												<div class="font-size14">${show['content']}</div>
												<c:choose>
													<c:when test="${show['type'] eq '0'}">
														<div class="card-pics">
															<div class="clearfix">
																<c:forEach var="resUrl" items="${show['resUrls']}">
																	<div class="card-img"><img src="${resUrl}" width="100%" data-preview-src="" data-preview-group="6" /></div>
																</c:forEach>
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<div class="video_p" style="background: url(resource/webview/img/head_bg.png) no-repeat;background-position:left top;background-size:cover;">
															<c:forEach var="resUrl" items="${show['resUrls']}">
																<video class="video video-js vjs-default-skin vjs-big-play-centered" poster="${show['videoPreviewUrl']}" preload="metadata" controls="controls"  width="100%" style="" height="100%" data-setup="{}" style="background:transparent url('resource/webview/img/head_bg.png') 50% 50% no-repeat;" src="${resUrl}"/>
															</c:forEach>
														</div>
													</c:otherwise>
												</c:choose>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<!--秀场end-->
					</div>
				</div>
			</div>
		</div>
	<script src="resource/webview/js/jquery.min.js"></script>
	<script src="resource/webview/js/swiper.jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="resource/webview/js/common.js"></script>
	<!--点击图片放大strat-->
	<script src="resource/webview/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="resource/webview/js/mui.zoom.js" type="text/javascript" charset="utf-8"></script>
	<script src="resource/webview/js/mui.previewimage.js" type="text/javascript" charset="utf-8"></script>
	<!--点击图片放大end-->
	<script src="resource/webview/js/video-js/video.js" type="text/javascript" charset="utf-8"></script>
	<!--下拉刷新start-->
	<script src="resource/webview/js/mui.pullToRefresh.js" type="text/javascript" charset="utf-8"></script>
	<script src="resource/webview/js/mui.pullToRefresh.material.js" type="text/javascript" charset="utf-8"></script>
	<!--下拉刷新end-->
	<script src="resource/webview/js/web-view-bridge.js"></script>
	<script>
		function doDeleteCard(el,cardId){
			$(el).parents('.mui-table-view-cell').remove();
		};
	
		function doDeleteShow(el,showId){
			$(el).parents('.mui-table-view-cell').remove();
		};

		$(function(){
			var w=$(window).width();
			$(".myCenter_header").height(w/1.75);
			setImg(".img_c");
			
			//卡片图片
			setImg(".card-pics .card-img");
			
			//tab点击效果
			var $div_li = $(".myCenter_content_t li");
			$div_li.click(function(){
				$(this).addClass("active").siblings().removeClass("active");
				var div_index = $div_li.index(this);
				$(".myCenter_content_c_box .myCenter_content_c").eq(div_index).show().siblings().hide();
			});
			//w h  352 260
			$('#vjs_video_3_html5_api').height($('#vjs_video_3_html5_api').width()*260/352);
			
		});
		
		//图片放大
		mui.previewImage();
		//设置flash路径，Video.js会在不支持html5的浏览中使用flash播放视频文件
	    videojs.options.flash.swf = "js/video-js/video-js.swf";
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
									loadData(ul, index, self);
								}, 1000);
							}
						}
					});
				});
				
				
				function loadCards(ul, pageInfo, caller) {
					pageInfo.page = Math.min(pageInfo.page + 1, pageInfo.pages + 1);
			    	$.ajax({ url:'webview/getHisCards.do',//从该处获取数据
			    		type:'post',
			    		dataType:'json',
			    		data:{'page':pageInfo.page, 'pageSize':pageInfo.pageSize, 'id':pageInfo.id},
				   	    success:function(ret){
				   	    	if(ret.result == 1) {
					   	    	var data = ret.data;
					   	    	pageInfo.pages = data.pages;
					   	    	pageInfo.total = data.total;
					   	    	pageInfo.pageSize = data.pageSize;
	   	    	
					   	    	for(var index in data.rows) {
					   	    		var number = (pageInfo.page - 1) * pageInfo.pageSize + parseInt(index) + 1;
					   	    		var card = data.rows[index];
						   	        var html = '<li class="mui-table-view-cell" onclick="getCardDetail(';
									html += '\'' + card.id + '\'';
									html += ",'${hisInfo['info']['name']}'";
									html += ',\'' + card.cardName + '\'';
									html += ",'${hisInfo['info']['headImgUrl']}'";
									html += ',\'' + card.firstImgUrl + '\'';
									html += ',\'' + card.publicType + '\'';
									html += ')">';
									html += '<div class="clearfix">';
									html += '<span class="card-label float-left">卡片' + number + ':&nbsp;&nbsp;' + card.publicType + '</span>';
									html += '</div>';
									html += '<div class="card-pics">';
									html += '<div class="clearfix">';
									for(var j in card.cardImgUrls) {
										html += '<div class="card-img"><img src="' + card.cardImgUrls[j] + '" width="100%" data-preview-src="" data-preview-group="1" /></div>';
									}
									html += '</div>';
									html += '</div>';
									html += '</li>';

									ul.innerHTML += html;
					   	    	}
					   	    						   	    	
					   	     	caller.endPullUpToRefresh();
				   	    	}
				   	    }
			    	});
				};

				function loadShow(ul, pageInfo, caller) {
					pageInfo.page = Math.min(pageInfo.page + 1, pageInfo.pages + 1);
			    	$.ajax({ url:'webview/getHisShow.do',//从该处获取数据
			    		type:'post',
			    		dataType:'json',
			    		data:{'page':pageInfo.page, 'pageSize':pageInfo.pageSize, 'id':pageInfo.id},
				   	    success:function(ret){
				   	    	if(ret.result == 1) {
					   	    	var data = ret.data;
					   	    	pageInfo.pages = data.pages;
					   	    	pageInfo.total = data.total;
					   	    	pageInfo.pageSize = data.pageSize;
					   	    	
					   	    	var fragment = document.createDocumentFragment();
					   	    	var li;
					   	    	for(var index in data.rows) {
					   	    		var show = data.rows[index];
					   	    		
					   	    		li = document.createElement('li');
									li.className = 'mui-table-view-cell';
					   	    		var a = function(id,type,creater) {
										li.onclick=function(){getShowDetail(id,type,creater)};
									}
									a(show.id,show.type,show.creater);
									
					   	    		var html = '<div class="clearfix">';
									html += '<span class="card-label float-left" style="width: auto;">' + show.createTime + '</span>';
									html += '</div>';
					   	    		html += '<div class="font-size14">' + show.content + '</div>';
					   	    		if(show.type == 0) {
						   	    		html += '<div class="card-pics">';
						   	    		html += '<div class="clearfix">';
						   	    		for(var j in show.resUrls) {
							   	    		html += '<div class="card-img"><img src="' + show.resUrls[j] + '" width="100%" data-preview-src="" data-preview-group="6" /></div>';
						   	    		}
						   	    		html += '</div>';
						   	    		html += '</div>';

					   	    		} else {
						   	    		html += '<div class="video_p" style="background: url(resource/webview/img/head_bg.png) no-repeat;background-position:left top;background-size:cover;">';
						   	    		for(var k in show.resUrls) {
							   	    		html += '<video class="video video-js vjs-default-skin vjs-big-play-centered" poster="'+ show.videoPreviewUrl + '" preload="metadata" controls="controls"  width="100%" style="" height="100%" data-setup="{}" style="background:transparent url(\'resource/webview/img/head_bg.png\') 50% html += \'50% no-repeat;" src="' + show.resUrls[k] + '"/>';
						   	    		}
						   	    		html += '</div>';					   	    			
					   	    		}
					   	    		
					   	    		li.innerHTML = html;
					   	    		
					   	    		fragment.appendChild(li);				   	    		
					   	    	}				   	    	
					   	    	ul.appendChild(fragment);
					   	    	
					   	     	caller.endPullUpToRefresh();
				   	    	}
				   	    }
			    	});
				};				

				var loadData = function(ul, index, caller) {
					if(index == 0) {
						loadCards(ul, pageInfo[index], caller);
					} else {
						loadShow(ul, pageInfo[index], caller);
					}
				};

				var createFragment = function(ul, index, caller) {
					var length = ul.querySelectorAll('li').length;
					var fragment = document.createDocumentFragment();
					var li;
					for (var i = 0; i < count; i++) {
						li = document.createElement('li');
						li.className = 'mui-table-view-cell';
						li.innerHTML = '第' + (index + 1) + '个带下拉加载的选项卡子项-' + (length + (reverse ? (count - i) : (i + 1)));
						fragment.appendChild(li);
					}
					return fragment;
				};
			});
		})(mui);
	</script>
	<script>
		pageInfo = {
				"0":{
					"page":${hisInfo['cardInfo']['page']},
					"pages":${hisInfo['cardInfo']['pages']},
					"pageSize":${hisInfo['cardInfo']['pageSize']},
					"total":${hisInfo['cardInfo']['total']},
					"id":"${hisInfo['reqId']}"
				},
				"1":{
					"page":${hisInfo['showInfo']['page']},
					"pages":${hisInfo['showInfo']['pages']},
					"pageSize":${hisInfo['showInfo']['pageSize']},
					"total":${hisInfo['showInfo']['total']},
					"id":"${hisInfo['reqId']}"
				}
			}
	</script>
	</body>
</html>
