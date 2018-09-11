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

<title>秀一秀管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$.ajax({
			url:"system/publicTypeForCombo.do?type=5",
			type:'POST',
			dataType:'json',
			success:function(data){
				 var defaultVal = {'id':'','name':'全部'};
				 data.splice(0, 0, defaultVal);
				 $('#publicTypeInput').combobox('loadData',data);
			}
		});
		 
		$('#deleteBtn').click(function(){
			doDelete('system/showInfoAjaxDelete.do');
		});
	});
	
	function formatType(value, row){
		if(value == 0){
			return '图文';
		}else if(value == 1){
			return '小视频';
		}
		return '';
	}
	
	function formatOptions(value, row){
		return '<a href="javascript:void(0);" onclick="toView(\'' + row.id + '\');">查看</a>';
	}
	
	function toView(id){
		window.parent.addTabPanel('秀一秀详情','system/showInfoDetail.do?id=' +id);
	}
	
	function searchData(){
		$('#dg').datagrid('load',{
			publicType:$('#publicTypeInput').combobox('getValue'),
			content:$('#contentInput').val(),
			type:$('#typeInput').val(),
			mobile:$('#mobileInput').val(),
			userName:$('#nicknameInput').val()
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
			data-options="url:'system/showInfoAjaxPage.do', iconCls:'icon-save',
			rownumbers:true, pagination:true, singleSelect:true,nowrap:false,
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'mobile',align:'center',sortable:true" style="width: 15%;">发布账号</th>
					<th data-options="field:'userName',align:'center',sortable:true" style="width: 25%;">发布昵称</th>
					<th data-options="field:'publicTypeName',align:'center',sortable:true" style="width: 10%;">发布类型</th>
					<th data-options="field:'content',align:'center',sortable:true" style="width: 20%;">内容</th>
					<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 5%;">类型</th>					
					<th data-options="field:'createTime',align:'center',sortable:true" style="width: 15%;">发布时间</th>
					<th data-options="field:'city',align:'center',sortable:true" style="width: 5%;">位置</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 5%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 90%;">
					<tr>
						<td class="ftitle">发布类型:</td>
						<td>
							<select class="easyui-combobox" id="publicTypeInput" style="width:100px;" 
								data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'name'"></select>
						</td>
						<td class="ftitle">内容:</td><td><input id="contentInput" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">类型:</td>
						<td>
							<!-- <input id="typeInput" class="easyui-textbox" style="width:120px"> -->
							<select class="easyui-combobox" id="typeInput" style="width:100px;" data-options="panelHeight:'auto',editable:false">
								<option value="">全部</option>
								<option value="0">图文</option>
								<option value="1">小视频</option>
							</select>
						</td>
						<td class="ftitle">发布账号:</td>
						<td>
							<input id="mobileInput" class="easyui-textbox" style="width:120px">
						</td>
						<td class="ftitle">发布昵称:</td>
						<td>
							<input id="nicknameInput" class="easyui-textbox" style="width:120px">
						</td>
						<td><a href="javascript:void(0);" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
					</tr>
				</table>
			</div>
			
			<div style="margin-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>	
			</div>
		</div>
	</div>
</body>
</html>
