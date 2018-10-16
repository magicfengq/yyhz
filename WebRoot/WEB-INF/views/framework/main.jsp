<%@ include file="/WEB-INF/views/common/include.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="cache-control" CONTENT="no-cache">
<META HTTP-EQUIV="cache-control" CONTENT="no-store">
<title>名角APP管理系统</title>
<%@ include file="/WEB-INF/views/common/style.jsp"%>
<script type="text/javascript">
	var tabPanels = [];
	function addTabPanel(title,url){
		if($.inArray(title, tabPanels) < 0){
			$("#tt").tabs('add',{
				title : title,
				width : "100%",
				height : "600px",
				content : '<iframe title="content" frameborder="0" height="100%"'
						+ 'width="100%" allowTransparency="true" align="center"'
						+ 'src='
						+ url
						+ '></iframe>',
				closable : true
			});
		}else{
			$("#tt").tabs('select',title);
		}
	}
</script>
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/sys.js"></script>
<script type="text/javascript" src="js/system/ztree.js"></script>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 5px;
}

.ftitle {
	font-size: 12px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.fitem input {
	width: 160px;
}

.mytable td, a {
	color: #ffffff;
}

.panel-header {
	height: 18px;
	padding-top: 10px;
	background-color: #387DB4;
}

.layout-split-west {
	background-color: #387DB4;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height: 70px; background-image: url('images/L1_01.png'); background-repeat: repeat-x;">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="mytable">
			<tr>
				<td width="20%"><img align="middle"
					src="${pageContext.request.contextPath}/images/L2_04.png" /></td>
				<td width="70%">&nbsp;</td>
				<td nowrap="nowrap">欢迎您：
					${sessionScope.SESSION_USER.userName}</td>
				<td nowrap="nowrap"><a href="./logout.do" target="_parent">退出</a>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'west',split:true" title="演艺互助后台管理系统"
		style="width: 200px; overflow: auto;">
		<ul id="tree" class="ztree"></ul>
	</div>
	<div data-options="region:'south',border:false"
		style="height: 30px; background-image: url('images/L3_04.png'); background-repeat: repeat-x;">
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td align="center" style="vertical-align: middle;">Copyright &copy; 2017 
					<a href="javascript:void(0);" target="_blank">演艺互助</a> All Rights Reserved
				</td>
			</tr>
		</table>
	</div>
	<div id="tt" class="easyui-tabs"
		data-options="tabWidth:150,tabHeight:30,region:'center',tools:'#tab-tools'"></div>
</body>
</html>