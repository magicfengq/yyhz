<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<meta http-equiv="X-UA-Compatible" content="IE=edge" >
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
 		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<title>个人中心-资料</title>
		<link rel="stylesheet" type="text/css" href="/resource/webview/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="/resource/webview/css/style.css"/>
		<link rel="stylesheet" type="text/css" href="/resource/webview/font-awesome-4.6.3/css/font-awesome.min.css"/>
	</head>
	<body>
		<ul class="myWatchlist">
			<li class="clearfix">
				<div class="header_p float-left">
					<div class="header_p_c"><img src="img/head_bg.png"/></div>
					<img src="img/grade_iocn.png" width="28%"/>
				</div>
				<div class="myWatchlist_t float-left model">张馨予</div>
			</li>
			<li class="clearfix">
				<div class="header_p float-left">
					<div class="header_p_c"><img src="img/head_bg.png"/></div>
					<img src="img/grade_iocn.png" width="28%"/>
				</div>
				<div class="myWatchlist_t float-left singer">张馨予</div>
			</li>
			<li class="clearfix">
				<div class="header_p float-left">
					<div class="header_p_c"><img src="img/head_bg.png"/></div>
					<img src="img/grade_iocn.png" width="28%"/>
				</div>
				<div class="myWatchlist_t float-left host">张馨予</div>
			</li>
		</ul>
		<script src="js/jquery.min.js"></script>
		<script src="js/common.js"></script>
		<script>
			$(function(){
				setImg(".header_p_c");
			});
			$(".myWatchlist li").on("touchend",function(){
				window.location.href="他的资料.html";
			});
		</script>
	</body>
</html>
