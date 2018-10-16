<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>名角APP管理系统</title>
<meta content="webkit" name="renderer" />
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link href="css/com.css" rel="stylesheet" />
<%@ include file="/WEB-INF/views/common/style.jsp"%>
<%@ include file="/WEB-INF/views/common/script.jsp"%>
<script type="text/javascript">
	if (window != top)
		top.location.href = location.href;
	function checkUserForm() {
		var userName = $("#userId").val();
		var userPassword = $("#userPwd").val();
		var validateCode = $("#validateCode").val();
		if (userName == "") {
			$.messager.show({
				title : '系统提示',
				msg : '用户名不能为空!'
			});
			return false;
		}
		if (userPassword == "") {
			$.messager.show({
				title : '系统提示',
				msg : '密码不能为空!'
			});
			return false;
		}
		if (validateCode == "" || validateCode.length != 4) {
			$.messager.show({
				title : '系统提示',
				msg : '请输入验证码！'
			});
			return false;
		}
		document.loginForm.submit();
	}

	function changeauthimg() {
		document.getElementById("validateCodeImg").src = "getAuthImg.do?"
				+ Math.random();
	}
</script>
</head>

<body class="back">
	<form name="loginForm" action="./login.do" method="post">
		<div class="xitong">
			<div class="text">
				<ul class="ul-wrap">
					<li class="list listing"><input
						class="left input list_size botom pad_left" type="text"
						placeholder="用户名.." name="userId" id="userId" value="root"><span><img
							src="images/img/lian.png"> </span>
					</li>
					<li class="list listing"><input
						class="left input list_size botom pad_left" type="password"
						placeholder="密码.." name="userPwd" id="userPwd" value="123456"><span><img
							src="images/img/yaoshi.png"> </span>
					</li>
					<li>
						<div class="yzm">
							<img src="getAuthImg.do" id="validateCodeImg" onclick="javescript:changeauthimg();" alt="看不清,换一张"><input
								class="yzm_text botom" id="validateCode" name="validateCode" data-options="prompt:'验证码'" maxlength="4">
							<button class="button" onclick="checkUserForm();"></button>
						</div>
						<div class="yzm" style="color: red;"></div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		if('${LOGIN_ERROR }'!=""){
			var msg='${LOGIN_ERROR }';
			$.messager.show({
				title : '系统提示',
				msg : msg
			});
		}
	</script>
</body>
</html>
