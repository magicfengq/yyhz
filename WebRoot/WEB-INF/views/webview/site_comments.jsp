<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>场地详情</title>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/css/style.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/font-awesome-4.6.3/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="resource/webview/scroll5/css/iscroll5.css"/>
	</head>
	<body onload="loaded()">
		<div class="main">
			 <div id="wrapper">
		        <div id="scroller">
		            <div id="pullDown">
		                <span>下拉刷新…</span>
		            </div>
						<div class="evaluate bg-ff">
							<div class="evaluate-t clearfix">
								<span class="float-left">评论(${siteInfo['commentTotal']})</span>
							</div>
							<ul class="evaluate-list">
								<c:forEach var="comment" items="${siteInfo['comments']}">
									<li class="clearfix">
										<div class="evaluate-p float-left">
											<c:if test="${empty comment['headImgUrl']}">
												 <img src="resource/webview/img/head_bg.png" width="100%">
											</c:if>	 
											<c:if test="${not empty comment['headImgUrl']}">
												 <img src="${comment['headImgUrl']}" width="100%"/>
											</c:if>
										</div>
										<div class="evaluate-c-t float-left clearfix">
											<span class="float-left">${comment['createrName']}</span>
											<span class="float-right c999">${comment['createTime']}</span>
										</div>
										<b class="clearfix"></b>
										<p>${comment['content']}</p>
									</li>
								</c:forEach>							
							</ul>
						</div>
		    		<div id="pullUp">
		                <span>上拉加载更多…</span>
		            </div>
				</div>
			</div>
			<div id="spinner">
		        <div></div>
		        <div></div>
		    </div>
		</div>
	<script src="resource/webview/scroll5/js/iscroll.js"></script>
	<script src="resource/webview/scroll5/js/iscroll5.js"></script>
	<script src="resource/webview/scroll5/js/iscroll-probe.js"></script>
	<script src="resource/webview/js/jquery.min.js"></script>
	<script src="resource/webview/js/common.js"></script>
	<script>
		page = ${siteInfo["page"]};
		pages = ${siteInfo["pages"]};
		pageSize = ${siteInfo["pageSize"]};
		total = ${siteInfo["total"]};
		siteId = "${siteInfo['id']}";
		setImg(".evaluate-list .evaluate-p");
	</script>
	</body>
</html>
