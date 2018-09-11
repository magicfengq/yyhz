<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
			+ request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript">
	function formatOptions(value, row){
		return '<a href="javascript:void(0);" onclick="doSelect(\''+row.id+'\',\''+row.name+'\');">选择</a>'
	}
	
	function searchData(){
		$('#dg').datagrid('load',{
			name:$('#nameInput').val(),
			realName:$('#realNameInput').val(),
			city:$('#cityInput').val()
		});
	}
	
	function doSelect(userId,userName){
		if(window.parent.selectOver){
			window.parent.selectOver(userId,userName);	
		}		
	}
</script>
<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}

div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	text-align: left;
	padding: 2px;
}
</style>
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
	/* border-bottom: 1px solid #ccc; */
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 65px;
}

.fitem input {
	width: 280px;
}
</style>
</head>

<body>
	<div style="width:100%;height:100%">
		<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'system/actorInfoAjaxPage.do', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'name',align:'center',sortable:true" style="width: 15%;">艺名</th>				
					<th data-options="field:'realName',align:'center',sortable:true" style="width: 15%;">姓名</th>
					<th data-options="field:'mobile',align:'center',sortable:true" style="width: 15%;">电话</th>
					<th data-options="field:'city',align:'center',sortable:true" style="width: 15%;">注册地</th>
					<th data-options="field:'createTime',align:'center',sortable:true" style="width: 20%;">注册时间</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 15%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 80%;">
					<tr>
						<td class="ftitle">艺名:</td><td><input id="nameInput" class="easyui-textbox" style="width:100px"/></td>
						<td class="ftitle">姓名:</td><td><input id="realNameInput" class="easyui-textbox" style="width:100px"/></td>
						<td class="ftitle">注册地:</td><td><input id="cityInput" class="easyui-textbox" style="width:100px"></td>
						<td><a href="javaScript:void()" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>						
					</tr>
				</table>				
			</div>
		</div>
	</div>
</body>
</html>
