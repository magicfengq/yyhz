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

<title>角色管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>

<script type="text/javascript">
	$(function() {
	  $('#addBtn').click(function(){
		  doAdd();
	  });
	  
	  $('#editBtn').click(function(){
		  doEdit();			 
	  });
	  
	  $('#deleteBtn').click(function(){
		  doDelete('system/roleInfoAjaxDelete.do');
	  });
	});
	
	var index;
	function save(){
	  $('#fm').form('submit', {
	    dataType: 'json',
	    success: function(result) {
	      var result = eval('(' + result + ')');
	      layer.close(index);
	      if (result.success) {
	        $('#dlg').dialog('close'); // close the dialog
	        $('#dg').datagrid('reload'); // reload the menu data
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
	border-bottom: 1px solid #ccc;
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
		<div id="toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBtn">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editBtn">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>	
		</div>
		<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'system/roleInfoAjaxPage.do', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true,
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'name',align:'center',sortable:true" style="width: 10%;">类型名称</th>
					<th data-options="field:'createTime',align:'center',sortable:true" style="width: 20%;">创建时间</th>
					<th data-options="field:'power',align:'center',sortable:true" style="width: 20%;">权重</th>
				</tr>
			</thead>
		</table>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/roleInfoAjaxSave.do">
				<div class="fitem">
					<label><font color="red">*</font>类型名称:</label>
					<input id="nameLabel" name="name" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
					<input type="hidden" id="idLabel" name="id" />
				</div>
				<div class="fitem">
					<label><font color="red">*</font>权重:</label>
					<input id="powerLabel" name="power" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
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
		
	</div>
</body>
</html>
