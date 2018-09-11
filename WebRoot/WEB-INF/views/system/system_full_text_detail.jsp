<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>${entity.title }</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
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
	width: 60px;
}

.fitem input {
	width: 160px;
}
</style>

<script type="text/javascript">
	function doEdit(obj) {
		  window.location.href = "${ pageContext.request.contextPath}/system/systemFullTextEdit.do?id="+obj;
	}
</script>
</head>

<body>
	<div class="easyui-panel" title="${entity.title }"
		style="width:700px;height:100%;padding:10px;"
		data-options="iconCls:'icon-filter',closable:false,tools:'#tt'">
		${entity.msgContent }</div>
	<div id="tt">
		<security:act optCode="edit">
			<a href="javascript:void(0)" class="icon-edit"
				onclick="javascript:doEdit('${entity.id}')" title="编辑"></a>
		</security:act>
	</div>
</body>
</html>
