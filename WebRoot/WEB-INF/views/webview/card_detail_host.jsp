<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>${cardDetail['cardInfo']['publicTypeName']}-卡片详情</title>
<link rel="stylesheet" type="text/css" href="resource/webview/css/public.css" />
<link rel="stylesheet" type="text/css" href="resource/webview/css/style.css" />
<link rel="stylesheet" type="text/css" href="css/app/style.css"/>
<link rel="stylesheet" type="text/css" href="resource/webview/font-awesome-4.6.3/css/font-awesome.min.css" />
<!--点击图片放大strat-->
<link rel="stylesheet" type="text/css" href="resource/webview/css/mui.min.css"/>
<link rel="stylesheet" type="text/css" href="resource/webview/css/preview.css"/>
<!--点击图片放大end-->
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
	<div class="myCenter_header">
		<c:choose>
			<c:when test="${not empty cardDetail['actorInfo']['headImgUrl']}">
				<img class="head_bg" src="${cardDetail['actorInfo']['headImgUrl']}" width="100%" height="100%"/>
			</c:when>
			<c:otherwise>
				<img class="head_bg" src="resource/webview/img/head_bg.png" width="100%" height="100%"/>
			</c:otherwise>
		</c:choose>
		<div class="zz"></div>
		<div class="flex p-flex">
			<c:choose>
				<c:when test="${cardDetail['actorInfo']['authenticateLevel'] == 1}">
					<div class="grade-box grade-green">
						实名认证
					</div>
				</c:when>
				<c:when test="${cardDetail['actorInfo']['authenticateLevel'] == 2}">
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
				<div class="head_p_c" onclick="getActorInfo('${cardDetail['cardInfo']['creater']}','${cardDetail['actorInfo']['name']}','${cardDetail['actorInfo']['headImgUrl']}')">
					<div class="img_c">
						<img src="${cardDetail['actorInfo']['headImgUrl']}" width="100%"/>
					</div>
					
				</div>
			</div>
			<div class="name-box">
				${cardDetail['actorInfo']['name']}
			</div>
			<div class="head_p_c_infor_card" style="top: 84%;">
				<!--3.6显示3颗星 往下截取整数个显示 最多6颗星 最少3颗星 带评分的都改这样了-->
				<div class="star-box">
					<c:forEach begin="1" end="${cardDetail['actorInfo']['starCount']}">
						<span>★</span>
					</c:forEach>
					<h3>${cardDetail['actorInfo']['avgScore']}</h3>
				</div>
			</div>
		</div>
	</div>
	<div class="model-info-box clearfix" style="background-color: #FC7F49;">
		<!--具体角色-->
		<span class="float-left">
			${cardDetail['cardInfo']['detailRole']}
		</span>
		<!--场薪-->
		<span class="float-right">
			￥${cardDetail['cardInfo']['price']}
		</span>
	</div>
	<div class="myCenter_content">
		<div class="myCenter_content_c card_details" style="padding: 0;">
			<ul class="pic_list model-pic-list">
				<c:if test="${not empty cardDetail['cardInfo']['cardImgUrls']}">
					<c:forEach var="imgUrl" items="${cardDetail['cardInfo']['cardImgUrls']}">
						<li><img src="${imgUrl}" width="100%"></li>
					</c:forEach>
				</c:if>
			</ul>
			<ul class="message_list" style="padding: 0 16px;">
				<li class="clearfix">
					<span class="float-left">艺名</span> 
					<span class="float-right">${cardDetail['cardInfo']['cardName']}</span>
				</li>
				<li class="clearfix">
					<span class="float-left">性别</span> 
					<span class="float-right">
						<c:choose>
							<c:when test="${cardDetail['cardInfo']['sex'] == 1}">男</c:when>
							<c:otherwise>女</c:otherwise>
						</c:choose>
					</span>
				</li>
				<!--
				<li class="clearfix">
					<span class="float-left">具体角色</span>
					<span class="float-right">${cardDetail['cardInfo']['detailRole']}</span>
				</li>
				-->
				<li class="clearfix">
					<span class="float-left">所在城市</span>
					<span class="float-right">${cardDetail['cardInfo']['city']}</span>
				</li>
				<li class="clearfix li-born">
					<span class="float-left">出生日期</span>
					<span class="float-right">${cardDetail['cardInfo']['birthDate']}</span>
				</li>
				<!--
				<li class="clearfix">
					<span class="float-left">场薪</span>
					<span class="float-right">${cardDetail['cardInfo']['price']}</span>
				</li>
				-->
				<li class="clearfix">
					<span class="float-left">身高</span>
					<span class="float-right">${cardDetail['cardInfo']['height']}</span>
				</li>
				<li class="clearfix">
					<span class="float-left">体重</span>
					<span class="float-right">${cardDetail['cardInfo']['weight']}</span>
				</li>
				<li class="clearfix">
					<span class="float-left">鞋码</span>
					<span class="float-right">${cardDetail['cardInfo']['shoesSize']}</span>
				</li>
				<li class="clearfix">
					<span class="float-left">三围</span>
					<span class="float-right">${cardDetail['cardInfo']['size']}</span>
				</li>
			</ul>
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
	<script src="resource/webview/js/web-view-bridge.js"></script>
	<script>
		$(function(){
//				初始化head
				var w=$(window).width();
				$(".myCenter_header").height(w/1.75);
				setImg(".img_c");
//				初始化video链接
				var video_w=$(".video_p").width();
				$(".video_p").height(video_w/2.02);
			});
	</script>
</body>
</html>
