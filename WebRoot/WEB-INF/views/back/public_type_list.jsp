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

<title>发布类型管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>

<script type="text/javascript">
	$(function() {
	  $('#addBtn').click(function(){
		  doAdd(function(){
			  var tab = $('#type-tabs').tabs('getSelected');
			  var index = $('#type-tabs').tabs('getTabIndex',tab);
			  var type = index + 1;			  			 
			  $('#typeLabel').combobox('select', type);
		  });
	  });
	});
	
	function formatType(value,row){
		if(row.type == 1){
			return '艺人';
		}else if(row.type == 2){
			return '租借';
		}else if(row.type == 3){
			return '策划/创意';
		}else if(row.type == 4){
			return '婚礼/派对';
		}else if(row.type == 5){
			return '秀一秀';
		}else if(row.type == 6){
			return '场地';
		}
		return '';
	}
	
	function formatOptions(value,row){
		return '<a href="javascript:void(0)" class="type-edit" onclick="onEdit(\'' + row.id + '\')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;' + 
		       '<a href="javascript:void(0)" class="type-delete" onclick="onDelete(\'' + row.id + '\')">删除</a>';
	}
	
	function searchData(){
		var tab = $('#type-tabs').tabs('getSelected');
		var index = $('#type-tabs').tabs('getTabIndex',tab);
		var type = index + 1;
		$('#dg'+type).datagrid('load',{
			type:type,
			name:$('#nameInput').val()
		});
	}
	
	var index;
	function save(){
	  $('#fm').form('submit', {
	    dataType: 'json',
	    success: function(result) {
	      var result = eval('(' + result + ')');
	      layer.close(index);
	      if (result.success) {
	        $('#dlg').dialog('close'); // close the dialog
	        
	        var tab = $('#type-tabs').tabs('getSelected');
			var index = $('#type-tabs').tabs('getTabIndex',tab);
			var type = index + 1;
	        $('#dg'+type).datagrid('reload'); // reload the menu data
	      } else {
	        $.messager.alert('错误信息', result.msg, 'error');
	        return false;
	      }
	    }
	  });
	}
	
	function validation(){
		var rr = $('#fm').form('enableValidation').form('validate');
	    if (rr) {
	    	index = layer.load('操作中...请等待！', 0);
	    } else {
	        return false;
	    }
	    return true;
	}
	
	function renderUI(data){
		$('.type-edit').linkbutton({text:'编辑',iconCls:'icon-edit'});
		$('.type-delete').linkbutton({text:'删除',iconCls:'icon-remove'});
	}
	
	function onEdit(id){
		var tab = $('#type-tabs').tabs('getSelected');
		var index = $('#type-tabs').tabs('getTabIndex',tab);
		var type = index + 1;
		$('#dg'+type).datagrid('selectRecord',id);
		var row = $('#dg'+type).datagrid('getSelected');
		console.log(row);
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('load', row);
		}
	}
	
	function onDelete(id){
		var url = 'system/publicTypeAjaxDelete.do';
		var tab = $('#type-tabs').tabs('getSelected');
		var index = $('#type-tabs').tabs('getTabIndex',tab);
		var type = index + 1;
		$('#dg'+type).datagrid('selectRecord',id);
		var row = $('#dg'+type).datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '你确定要删除吗?', function(r) {
				if (r) {
					$.post(url, {
						id : row.id
					}, function(result) {
						if (result.success) {
							$('#dg'+type).datagrid('reload'); // reload the user data
						} else {
							$.messager.show({ // show error message
								title : '提示',
								msg : result.msg
							});
						}
					}, 'json');
				}
			});
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

	<div>
		<div>
			<table style="width: 30%;">
				<tr>
					<td class="ftitle">类型名称:</td><td><input id="nameInput" class="easyui-textbox" style="width:120px"></td>
					<td><a href="javascript:void(0);" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
				</tr>
			</table>
		</div>
		
		<div style="margin-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-add" id="addBtn">添加</a>
		</div>
	</div>
	<div id="type-tabs" class="easyui-tabs" style="width:700px;height:'auto'">
		
		<div title="艺人" style="padding: 10px;width:100%;height:100%;">
			<table id="dg1" class="easyui-datagrid" style="width:100%;height:100%"
				data-options="url:'system/publicTypeAjaxPage.do?type=1', iconCls:'icon-save', 
				rownumbers:true, pagination:true, singleSelect:true,idField:'id',onLoadSuccess:renderUI">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 20%;">发布类型</th>
						<th data-options="field:'name',align:'center',sortable:true" style="width: 20%;">发布类型名称</th>
						<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 30%;">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div title="租借" style="padding: 10px;width:100%;height:100%;">
			<table id="dg2" class="easyui-datagrid" style="width:100%;height:100%"
				data-options="url:'system/publicTypeAjaxPage.do?type=2', iconCls:'icon-save', 
				rownumbers:true, pagination:true, singleSelect:true,idField:'id',onLoadSuccess:renderUI">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 20%;">发布类型</th>
						<th data-options="field:'name',align:'center',sortable:true" style="width: 20%;">发布类型名称</th>
						<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 30%;">操作</th>
					</tr>
				</thead>
			</table>
		</div>
			
		<!-- <div title="策划/创意" style="padding: 10px;width:100%;height:100%;">
			<table id="dg3" class="easyui-datagrid" style="width:100%;height:100%"
				data-options="url:'system/publicTypeAjaxPage.do?type=3', iconCls:'icon-save', 
				rownumbers:true, pagination:true, singleSelect:true,idField:'id',onLoadSuccess:renderUI">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 20%;">发布类型</th>
						<th data-options="field:'name',align:'center',sortable:true" style="width: 20%;">发布类型名称</th>
						<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 30%;">操作</th>
					</tr>
				</thead>
			</table>
		</div>
			
		<div title="婚礼/派对" style="padding: 10px;width:100%;height:100%;">
			<table id="dg4" class="easyui-datagrid" style="width:100%;height:100%"
				data-options="url:'system/publicTypeAjaxPage.do?type=4', iconCls:'icon-save', 
				rownumbers:true, pagination:true, singleSelect:true,idField:'id',onLoadSuccess:renderUI">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 20%;">发布类型</th>
						<th data-options="field:'name',align:'center',sortable:true" style="width: 20%;">发布类型名称</th>
						<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 30%;">操作</th>
					</tr>
				</thead>
			</table>
		</div> -->
		<div title="秀一秀" style="padding: 10px;width:100%;height:100%;">
			<table id="dg5" class="easyui-datagrid" style="width:100%;height:100%"
				data-options="url:'system/publicTypeAjaxPage.do?type=5', iconCls:'icon-save', 
				rownumbers:true, pagination:true, singleSelect:true,idField:'id',onLoadSuccess:renderUI">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 20%;">发布类型</th>
						<th data-options="field:'name',align:'center',sortable:true" style="width: 20%;">发布类型名称</th>
						<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 30%;">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- <div title="场地" style="padding: 10px;width:100%;height:100%;">
			<table id="dg6" class="easyui-datagrid" style="width:100%;height:100%"
				data-options="url:'system/publicTypeAjaxPage.do?type=6', iconCls:'icon-save', 
				rownumbers:true, pagination:true, singleSelect:true,idField:'id',onLoadSuccess:renderUI">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 20%;">发布类型</th>
						<th data-options="field:'name',align:'center',sortable:true" style="width: 20%;">发布类型名称</th>
						<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 30%;">操作</th>
					</tr>
				</thead>
			</table>
		</div> -->
	</div>
		<div id="dlg" class="easyui-dialog"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">请完善以下信息！</div>
		<form id="fm" name="fm" method="post" action="system/publicTypeAjaxSave.do">
			<div class="fitem">
				<label>发布类型:</label>
				<select readOnly="readOnly" id="typeLabel" name="type" class="easyui-combobox" style="width:100px;" 
					data-options="panelHeight:'auto',editable:false"
					>
					<option value="1" selected="selected">艺人</option>
					<option value="2" selected="selected">租借</option>
					<option value="3" selected="selected">策划/创意</option>
					<option value="4" selected="selected">婚礼/派对</option>
					<option value="5" selected="selected">秀一秀</option>
					<option value="6" selected="selected">场地</option>
				</select>
			</div>
			<div class="fitem">
				<label><font color="red">*</font>类型名称:</label>
				<input id="nameLabel" name="name" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
				<input type="hidden" id="idLabel" name="id" />
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="save()" style="width:90px">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
	</div>

	<!-- <div style="width:100%;height:100%">
		<div id="toolbar">
			<div>
				<table style="width: 40%;">
					<tr>
						<td class="ftitle">发布类型:</td>
						<td>
							<select id="typeInput" class="easyui-combobox" style="width:100px;" 
								data-options="panelHeight:'auto',editable:false">
								<option value="" selected="selected">全部</option>
								<option value="1">艺人</option>
								<option value="2">租借</option>
								<option value="3">策划/创意</option>
								<option value="4">婚礼/派对</option>
								<option value="5">秀一秀</option>
							</select>
						</td>
						<td class="ftitle">类型名称:</td><td><input id="nameInput" class="easyui-textbox" style="width:120px"></td>
						<td><a href="javaScript:void()" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
					</tr>
				</table>
			</div>
			
			<div style="margin-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBtn">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editBtn">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>	
			</div>
		</div>
		<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'system/publicTypeAjaxPage.do', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true,
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'type',align:'center',sortable:true,formatter:formatType" style="width: 10%;">发布类型</th>
					<th data-options="field:'name',align:'center',sortable:true" style="width: 10%;">发布类型名称</th>
				</tr>
			</thead>
		</table>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/publicTypeAjaxSave.do">
				<div class="fitem">
					<label>发布类型:</label>
					<select id="typeLabel" name="type" class="easyui-combobox" style="width:100px;" 
						data-options="panelHeight:'auto',editable:false"
						>
						<option value="1" selected="selected">艺人</option>
						<option value="2" selected="selected">租借</option>
						<option value="3" selected="selected">策划/创意</option>
						<option value="4" selected="selected">婚礼/派对</option>
						<option value="5" selected="selected">秀一秀</option>
					</select>
				</div>
				<div class="fitem">
					<label><font color="red">*</font>类型名称:</label>
					<input id="nameLabel" name="name" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
					<input type="hidden" id="idLabel" name="id" />
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="save()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
		</div>
		
	</div> -->
</body>
</html>
