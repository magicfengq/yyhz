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

	$(function(){
		  $('#lockOpenBtn').click(function(){
			  doUpdateStatus('system/actorInfoAjaxUpdate.do',0);
		  });
		  
		  $('#lockBtn').click(function(){
			  doUpdateStatus('system/actorInfoAjaxUpdate.do',1);
		  });
		  
		  $('#deleteBtn').click(function(){
			  doDelete('system/actorInfoAjaxDelete.do');
		  });
		  
	});

	function formatRegisterType(value, row){
		if(value == 1){
			return 'QQ';
		}else if(value == 2){
			return '微信';
		}else if(value == 3){
			return '微博'
		}else{
			return '普通注册';
		}
	}
	
	function formatSex(value,row){
		if(value == 1){
			return '男';
		}else if(value == 2){
			return '女';
		}
	}
	
	function formatLevel(value,row){
		if(value == '0'){
			return '普通用户';
		}else if(value == '1'){
			return '实名认证';
		}else if(value == '2'){
			return '资历认证';
		}
	}
	
	function formatStatus(value,row){
		if(value == '0'){
			return '启用';
		}else if(value == '1'){
			return '禁用';
		}
	}
	
	function formatOptions(value, row){
		return '<a href="javascript:void(0);" onclick="toView(\'' + row.id + '\');">查看</a>';
	}
	
	function toView(id){
		window.parent.addTabPanel('用户信息','system/actorInfoDetail.do?id=' +id);
	}
	
	function searchData(){
		$('#dg').datagrid('load',{
			mobile:$('#mobileInput').val(),
			roleName:$('#roleNameInput').val(),
			name:$('#nameInput').val(),
			authenticateLevel:$('#authenticateLevelInput').val(),
			sex:$('#sexInput').val(),
			//registerType:$('#registerTypeInput').val(),
			status:$('#statusInput').val()
		});
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
					<th data-options="field:'mobile',align:'center',sortable:true" style="width: 10%;">账号</th>
					<th data-options="field:'name',align:'center',sortable:true" style="width: 15%;">艺名</th>
					<th data-options="field:'realName',align:'center',sortable:true" style="width: 10%;">真实姓名</th>
					<th data-options="field:'idcard',align:'center',sortable:true" style="width: 10%;">身份证</th>
					<!-- <th data-options="field:'registerType',align:'center',sortable:true,formatter:formatRegisterType" style="width: 15%;">第三方账号</th> -->
					<th data-options="field:'sex',align:'center',sortable:true,formatter:formatSex" style="width: 5%;">性别</th>
					<th data-options="field:'authenticateLevel',align:'center',sortable:true,formatter:formatLevel" style="width: 5%;">等级</th>
					<th data-options="field:'status',align:'center',sortable:true,formatter:formatStatus" style="width: 5%;">状态</th>
					<th data-options="field:'roleName',align:'center',sortable:true" style="width: 10%;">角色</th>
					<th data-options="field:'createTime',align:'center',sortable:true" style="width: 15%;">注册时间</th>
					<th data-options="field:'areaCode',align:'center',sortable:true" style="width: 10%;">注册地</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 10%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 80%;">
					<tr>
						<td class="ftitle">账号:</td><td><input id="mobileInput" class="easyui-textbox" style="width:120px"/></td>
						<td class="ftitle">角色:</td><td><input id="roleNameInput" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">艺名:</td><td><input id="nameInput" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">等级:</td>
						<td>
							<select class="easyui-combobox" id="authenticateLevelInput" style="width:120px;" data-options="panelHeight:'auto',editable:false,required:false">
								<option value="">全部</option>
								<option value="0">普通用户</option>
								<option value="1">实名认证</option>
								<option value="2">资历认证</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="ftitle">性别:</td>
						<td>
							<select class="easyui-combobox" id="sexInput" style="width:120px;" data-options="panelHeight:'auto',editable:false">
								<option value="">全部</option>
								<option value="1">男</option>
								<option value="2">女</option>
							</select>
						</td>
						<!--<td class="ftitle">平台:</td>
						 <td>
							<select class="easyui-combobox" id="registerTypeInput" style="width:120px;" data-options="panelHeight:'auto',editable:false">
								<option value="">全部</option>
								<option value="0">普通注册</option>
								<option value="1">QQ</option>
								<option value="2">微信</option>
								<option value="3">微博</option>
							</select>
						</td> -->
						<td class="ftitle">状态:</td>
						<td>
							<select class="easyui-combobox" id="statusInput" style="width:120px;" data-options="panelHeight:'auto',editable:false">
								<option value="">全部</option>
								<option value="0">启用</option>
								<option value="1">禁用</option>
							</select>
						</td>
						<td><a href="javaScript:void()" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div style="margin-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="lockOpenBtn">启用</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="lockBtn">禁用</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>
			</div>
		</div>
	</div>
</body>
</html>
