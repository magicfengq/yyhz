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
	
	var basePath = "<%=basePath%>";
	function audioUrlFormater(value, row) {
		return '<audio src="'+row.audioUrl+'" controls="controls" width="30px"></audio>';
	}
	function formatOptions(value, row) {
		return '<a href="javascript:void(0);" onclick="edit(\''
				+ row.id
				+ '\');">查看</a>'
				+ '&nbsp;<a href="javascript:void(0);" onclick="del(\''
				+ row.id + '\');">删除</a>';
	}
	
	function searchData() {
		$('#dg').datagrid('load', {
			name : $('#nameInput').val()
		});
	}
	function edit(id){
		
		var row = $('#dg').datagrid('getSelected');
		console.log(row);
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '查看');
			$('#fm').form('load', row);
			$('#audioUrl').attr('src',row.audioUrl);
		}
	}
	function del(id) {
		$.messager.confirm('提示', '你确定要删除吗?', function(r) {
			if (r) {
				$.post('back/audioInfoAjaxDelete.do', {
					id : id
				}, function(result) {
					if (result.success) {
						$('#dg').datagrid('reload'); // reload the user data
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
			data-options="url:'back/audioInfoAjaxPage.do', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'name',align:'center',sortable:true" style="width: 15%;">音频名称</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 15%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 90%;">
					<tr>
						<td class="ftitle">音频名称:</td><td>
							<input id="nameInput" class="easyui-textbox" style="width:200px"/>
						</td>
						<td><a href="javaScript:;" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
						
					</tr>
				</table>
			</div>
			
			<div style="margin-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-add" id="addBtn">添加</a>
			</div>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" name="fm" method="post" action="">
		
			<div class="fitem">
				<label>音频名称:</label>
				<input id="nameLabel" name="name" style="width: 200px" class="easyui-textbox" readonly="true"/>
			</div>
			<div class="fitem">
				
				<audio id="audioUrl" name="audio" src="" controls="controls" width="30px"></audio>
				
				<input type="hidden" id="idLabel" name="id" />
			</div>
		</form>
	</div>
	
</body>
</html>
