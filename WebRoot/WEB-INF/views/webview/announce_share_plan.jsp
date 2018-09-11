<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<title>${announceInfo['publicType']}-通告详情</title>
			<link rel="stylesheet" type="text/css" href="resource/webview/css/public.css"/>
			<link rel="stylesheet" type="text/css" href="resource/webview/css/style.css"/>
			<link rel="stylesheet" type="text/css" href="resource/webview/font-awesome-4.6.3/css/font-awesome.min.css"/>
			<link rel="stylesheet" type="text/css" href="resource/webview/css/swiper.min.css"/>
			<!--点击图片放大strat-->
			<link rel="stylesheet" type="text/css" href="resource/webview/css/mui.min.css"/>
			<link rel="stylesheet" type="text/css" href="resource/webview/css/preview.css"/>
			<!--点击图片放大end-->
			<style type="text/css">
				.img_c {
				    
				}
				.swiper-button-next{
					-webkit-transform: scaleY(0.9) rotateZ(225deg) translateY(-50%);
					-ms-transform: scaleY(0.9) rotateZ(225deg) translateY(-50%);
					transform: scaleY(0.9) rotateZ(225deg) translateY(-50%);
					display: inline-block;
					width: 15px;
					height: 15px;
					border-bottom: 2px solid #333;
					border-left: 2px solid #333;
					vertical-align: middle;
					background-image: none;
					top: 60%;
					right: -2px;
			        &:hover{
			            opacity: 0.7;
			        }
    			}
    			.swiper-button-prev{
        			-webkit-transform: scaleY(0.9) rotateZ(45deg) translateY(-50%);
					-ms-transform: scaleY(0.9) rotateZ(45deg) translateY(-50%);
					transform: scaleY(0.9) rotateZ(45deg) translateY(-50%);
					display: inline-block;
					width: 15px;
					height: 15px;
					border-bottom: 2px solid #333;
					border-left: 2px solid #333;
					vertical-align: middle;
					background-image: none;
					top: 70%;
					left: -2px;
			        &:hover{
			            opacity: 0.7;
			        }
    			}
			</style>
		</head>
		<body>
			<div class="myCenter_header">
				<c:choose>
					<c:when test="${not empty announceInfo['headImgUrl']}">
						<img class="head_bg" src="${announceInfo['headImgUrl']}" width="100%" height="100%"/>
					</c:when>
					<c:otherwise>
						<img class="head_bg" src="resource/webview/img/head_bg.png" width="100%" height="100%"/>
					</c:otherwise>
				</c:choose>
				<div class="zz"></div>
				<div class="flex p-flex">
					<c:choose>
						<c:when test="${announceInfo['authenticateLevel'] == 1}">
							<div class="grade-box grade-green">
								实名认证
							</div>
						</c:when>
						<c:when test="${announceInfo['authenticateLevel'] == 2}">
							<div class="grade-box grade-yellow">
								执照认证
							</div>
						</c:when>
						<c:otherwise>
							<div class="grade-box grade-red">
								未认证
							</div>
						</c:otherwise>
					</c:choose>
					<div class="head_p">
						<div class="head_p_c" onclick="" style="">
							<div class="img_c">
								<img src="${announceInfo['headImgUrl']}" width="100%"/>
							</div>
						</div>
					</div>
					<div class="name-box">
						${announceInfo['actorName']}
					</div>
				</div>
			</div>
			<div class="myCenter_content bgec">
				<div class="myCenter_content_c card_details bgff" style="margin-top: 10px;">
					<ul class="message_list">
						<li class="clearfix">
							<span class="float-left">定制内容</span>
							<span class="float-right font-grey">${announceInfo['title']}</span>
						</li>
						<li class="clearfix">
							<span class="float-left">定制时间</span>
							<span class="float-right font-grey">${announceInfo['showTime']}</span>
						</li>
						<li class="clearfix">
							<span class="float-left">使用城市</span>
							<span class="float-right font-grey">${announceInfo['city']}</span>
						</li>
						<li class="clearfix">
							<span class="float-left">预算</span>
							<span class="float-right font-grey">${announceInfo['price']}</span>
						</li>
					</ul>
				</div>
				<div class="myCenter_content_c card_details bgff" style="margin-top: 10px;">
					<p class="attr">参考图片</p>
					<div class="refer-img-container">
						<c:forEach var="imgUrl" items="${announceInfo['imgUrls']}">
							<img src="${imgUrl}" data-preview-src="" data-preview-group="2" width="100" height="100"/>
						</c:forEach>
					</div>
				</div>
				<div class="myCenter_content_c card_details bgff" style="margin-top: 10px;">
					<p class="attr">定制要求</p>
					<div class="" style="padding: 25px 0;">
					   ${announceInfo['detail']}
					</div>
				</div>
				<div class="myCenter_content_c card_details bgff" style="margin-top: 10px;">
					<p class="attr">已报名</p>
					<div class="swiper-container">
					    <div class="swiper-wrapper">
					        <c:forEach var="actor" items="${announceInfo['enrollActors']}">
					        	<c:if test="${not empty actor['headImgUrl']}">
									<div class="swiper-slide" style="">
							        	<div class="img_c" style="border: 15px solid #fff;">
											<img src="${actor['headImgUrl']}" width="100%"/>
										</div>
							        </div>
						        </c:if>
							</c:forEach>
					    </div>
					    <div class="swiper-button-prev"></div>
	   					<div class="swiper-button-next"></div>
					    <!--<div class="swiper-pagination"></div>-->
					</div>
				</div>
				<!--<div class="handel_content" style="position: fixed;bottom: 0;left: 0;margin-top: 44px;">
					<a class="call" style="width: 100%;">我要报名</a>
				</div>-->
			</div>
		<script src="resource/webview/js/jquery.min.js"></script>
		<script src="resource/webview/js/common.js"></script>
		<script src="resource/webview/js/swiper.jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<!--点击图片放大strat-->
		<script src="resource/webview/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="resource/webview/js/mui.zoom.js" type="text/javascript" charset="utf-8"></script>
		<script src="resource/webview/js/mui.previewimage.js" type="text/javascript" charset="utf-8"></script>
		<!--点击图片放大end-->
		<script>
			//图片放大
			mui.previewImage();
			$(function(){
//				初始化head
				var w=$(window).width();
				$(".myCenter_header").height(w/2.12);
				setImg(".img_c");
				setImg(".pic_list li img");
			});
			
			//新房轮播图初始化
			var mySwiper = new Swiper ('.card_details .swiper-container', {
			    direction: 'horizontal',
//			    loop: true,
			    slidesPerView :4,
			    slidesPerGroup:1,
			    spaceBetween:-15,
//			    slidesOffsetBefore : 8,
//			    slidesOffsetAfter : 8,
			    // 如果需要分页器
			    pagination: '.card_details .swiper-pagination',
			    
			    // 如果需要前进后退按钮
			    nextButton: '.card_details .swiper-button-next',
			    prevButton: '.card_details .swiper-button-prev',
			});
			
			
			/**
			 * 点击头像跳转至原生页面
			 */
			function toDetail(id){
				try {
					WebViewJavascriptBridge.toDetail({
						id : id
					}, function(responseData) {
//						log('JS got response', responseData);
//						alert('JS got response');
					});
				} catch (e) {
						
				}
				try {
					JavaScriptInterface.toDetail(id);
				} catch (e) {
						
				}
				
			}
			
			
		</script>
		</body>
	</html>
