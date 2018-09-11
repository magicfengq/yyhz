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

<title>消息管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript" src="js/template.js"></script>
<script type="text/html" id="tpl">
	<iframe id="detailFrame" name="detailFrame" frameborder="0" height="100%" width="100%" align="center"></iframe>	
</script>
<script type="text/javascript">
	$(function() {
	  $('#addBtn').click(function(){
		  doAdd();
	  });
	  
	  $('#editBtn').click(function(){
		  doEdit(function(row){
			  $('#sendLabel').val('');
			  var sendTarget = row.sendTarget;
			  var obj = JSON.parse(sendTarget);
			  $('input[name="sendTargetEntity"]').val(obj.level);
			  if(obj.sendUsers){
				  $('#selectUserDiv').show();
				  var sendusers = obj.sendUsers;
				  var ids = '';
				  $.each(sendusers,function(index,value){
					  ids = ids + value.id + ',';
				  });
				  ids=ids.substring(0,ids.length-1);
				  $('#sendUsersInput').tagbox('loadData', sendusers);
				  $('#sendUsersInput').tagbox('setValues', ids);
			  }
		  });			 
	  });
	  
	  $('#deleteBtn').click(function(){
		  doDelete('system/messageInfoAjaxDelete.do');
	  });
	  
	  $('#sendBtn').click(function(){
		  var row = $('#dg').datagrid('getSelected');
		  if(row.status == '1'){
			  $.messager.show({
					title : '提示',
					msg : '消息已经发送!'
			   });
			  return false;
		  }
	  });
	  
	  $('#selectUserBtn').click(function(){
		  $("#detailFrame").attr('src', "system/userMutiSelect.do");
		  $('#userSelectDlg').dialog('open').dialog('setTitle', '用户选择');
	  });
	  
	  $('input[name="sendTargetEntity"]').click(function(){
		  var index = $('input[name="sendTargetEntity"]').index($(this));
		  console.log(index);
		  if(index == 0){
			if($(this).prop('checked')){
				$('input[name="sendTargetEntity"]:gt(0)').prop('checked',true);
				$('input[name="sendTargetEntity"]:gt(0)').prop('disabled',true);
				$('#selectUserDiv').hide();
			}else{
				$('input[name="sendTargetEntity"]:gt(0)').prop('checked',false);
				$('input[name="sendTargetEntity"]:gt(0)').prop('disabled',false);
			}
		  }else{
			  var isChecked1 = $('input[name="sendTargetEntity"]:eq(1)').prop('checked');
			  var isChecked2 = $('input[name="sendTargetEntity"]:eq(2)').prop('checked');
			  var isChecked3 = $('input[name="sendTargetEntity"]:eq(3)').prop('checked');
			  if(isChecked1 && isChecked2 && isChecked3){
				  $('input[name="sendTargetEntity"]:eq(0)').prop('checked',true);
				  $('input[name="sendTargetEntity"]:gt(0)').prop('checked',true);
				  $('input[name="sendTargetEntity"]:gt(0)').prop('disabled',true);
				  $('#selectUserDiv').hide();
			  }
		  }
	  });
	});
	
	function formatStatus(value,row){
		if(row.status == '0'){
			return '未发送';
		}else if(row.status == '1'){
			return '已发送';
		}
		return '';
	}
	
	function searchData(){
		$('#dg').datagrid('load',{
			title:$('#titleInput').val(),
			sendTarget:$('#sendTargetInput').val(),
			startDate:$('#startDateInput').val(),
			endDate:$('#endDateInput').val()
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
	        $('#dg').datagrid('reload'); // reload the menu data
	      } else {
	        $.messager.alert('错误信息', result.msg, 'error');
	        return false;
	      }
	    }
	  });
	}
	
	function saveAndSend(){
	  $('#sendLabel').val('1');	
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
	
	function selectUsers(obj){
		var $_this = $(obj);
		if($_this.prop('checked')){
			$('#selectUserDiv').show();	
		}else{
			$('#selectUserDiv').hide();
		}
	}
	
	function confirmSelect(){
		var rows = $("#detailFrame")[0].contentWindow.$('#dg').datagrid('getSelections');
		var tagValue = '';
		$.each(rows,function(index,value){
			tagValue = tagValue + value.id + ',';
		});
		tagValue=tagValue.substring(0,tagValue.length-1);
		$('#sendUsersInput').tagbox('loadData', rows);
		$('#sendUsersInput').tagbox('setValues', tagValue);
		$('#userSelectDlg').dialog('close');
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

.fitem input[type="text"] {
	width: 280px;
}
</style>
</head>

<body>
	<div style="width:100%;height:100%">
		<div id="toolbar">
			<div>
				<table style="width: 80%;">
					<tr>
						<td class="ftitle">消息标题:</td>
						<td><input id="titleInput" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">接收人:</td>
						<td><input id="sendTargetInput" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">发送时间:</td>
						<td><input id="startDateInput" class="easyui-datebox" style="width:120px;"> ~ <input id="endDateInput" class="easyui-datebox" style="width:120px;"></td>						
						<td><a href="javaScript:void()" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
					</tr>
				</table>
			</div>
			
			<div style="margin-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBtn">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editBtn">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-send" plain="true" id="sendBtn">发送</a>		
			</div>
		</div>
		<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'system/messageInfoAjaxPage.do', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true,
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'title',align:'center',sortable:true" style="width: 10%;">消息标题</th>
					<th data-options="field:'sendTime',align:'center',sortable:true" style="width: 20%;">发送时间</th>
					<th data-options="field:'content',align:'center',sortable:true" style="width: 30%;">发送内容</th>
					<th data-options="field:'status',align:'center',sortable:true,formatter:formatStatus" style="width: 10%;">状态</th>
				</tr>
			</thead>
		</table>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:100%;height:100%;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/messageInfoAjaxSave.do">
				<div class="fitem">
					<label><font color="red">*</font>消息标题:</label>
					<input id="titleLabel" name="title" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
					<input type="hidden" id="idLabel" name="id" />
					<input type="hidden" id="sendLabel" name="send" />
				</div>
				<div class="fitem">
					<label><font color="red">*</font>发送内容:</label>
					<input id="contentLabel" name="content" multiline="true" style="width: 300px;height: 120px;" class="easyui-textbox" data-options="required:true,validType:'length[1,200]'"/>
				</div>
				<div class="fitem">
					<label><font color="red">*</font>发送范围:</label>
					<input type="checkbox" value="-1" name="sendTargetEntity" id="alluserck"/>所有用户
					<input type="checkbox" value="0" name="sendTargetEntity"/>普通用户
					<input type="checkbox" value="1" name="sendTargetEntity"/>实名用户
					<input type="checkbox" value="2" name="sendTargetEntity"/>高级用户
					<input type="checkbox" value="3" name="sendTargetEntity" onclick="selectUsers(this)"/>具体用户
				</div>
				<div class="fitem" id="selectUserDiv" style="display: none;">
					<label></label>
					<input type="text" class="easyui-tagbox" style="width: 350px;" name="sendUsers" id="sendUsersInput" data-options="valueField: 'id',textField: 'name'"/>
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" id="selectUserBtn">选择用户</a>
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="save()" style="width:90px">保存</a> 
			
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveAndSend()" style="width:90px">保存并发送</a>
				
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
		</div>
		<div id="userSelectDlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:80%;height:80%;padding:10px 20px" closed="true"
			buttons="#userSelectDlg-buttons">
			<iframe id="detailFrame" name="detailFrame" frameborder="0" height="100%" width="100%" align="center"></iframe>			
		</div>
		<div id="userSelectDlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="confirmSelect()" style="width:90px">确定选择</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#userSelectDlg').dialog('close')" style="width:90px">取消</a>
		</div>
	</div>
</body>
</html>
