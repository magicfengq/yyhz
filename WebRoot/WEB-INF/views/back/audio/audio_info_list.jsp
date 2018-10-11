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

<title>音频管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>

<script type="text/javascript">
	$(function() {
		
	  $('#addBtn').click(function(){
		  doAdd(function(){
		  });
	  });
	  
	  $('#editBtn').click(function(){
		  	
		  var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg1').dialog('open').dialog('setTitle', '查看');
				$('#fm1').form('load', row);
			    //$('#uploadFileNameLabel').textbox('setValue','downFileResult.do?urlPath='+row.downloadUrl);
			    $('#audioUrl').attr('src','downFileResult.do?urlPath='+row.downloadUrl);
			}
	  });
	  
	  $('#deleteBtn').click(function(){
		  doDelete('back/audioInfoAjaxDelete.do');
	  });
	});
	
	function formatUrl(value,row){
		return 'downFileResult.do?urlPath='+row.downloadUrl;
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
	<div style="width:100%;height:100%">
			<div id="toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBtn">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editBtn">查看</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>	
			</div>
		
		<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'back/audioInfoAjaxPage.do', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'audioName',align:'center',sortable:true" style="width: 15%;">音频名称</th>
					<th data-options="field:'singerName',align:'center',sortable:true" style="width: 15%;">演唱者</th>
					<th data-options="field:'duration',align:'center',sortable:true" style="width: 15%;">时长(毫秒)</th>
					<th data-options="field:'createTime',align:'center',sortable:true" style="width: 20%;">创建时间</th>
					
				</tr>
			</thead>
		</table>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:450px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="back/audioInfoInsert.do">
				<div class="fitem">
					<label style="width: 70px;"><font color="red">*</font>音频名称:</label>
					<input id="audioNameLabel" name="audioName" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
					<input type="hidden" id="idLabel" name="id" />
					<input type="hidden" id="operType" name="operType" />
				</div>
				<div class="fitem">
					<label style="width: 70px;"><font color="red">*</font>演唱者:</label>
					<input id="singernameLabel" name="singerName" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
				</div>
				<div class="fitem">
					<label style="width: 70px;"><font color="red">*</font>时长(毫秒):</label>
					<input id="durationLabel" name="duration" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
				</div>
				<div class="fitem">
					<label style="width: 70px;"><font color="red">*</font>音频文件:</label>
					<input id="uploadFileNameLabel" name="uploadFileName" style="width: 200px" class="easyui-textbox" readonly="readonly" data-options="required:true,validType:'length[1,100]'"/>
					<div id="fileSelect" style="width: 90px;display: inline-block;"><a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-upload" >请选择音频</a></div>
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="uploadAndSave()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
		</div>
		<div id="dlg1" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:450px;padding:10px 20px" closed="true"
			>
			<form id="fm1" name="fm" method="post" action="">
				<div class="fitem">
					<label style="width: 70px;"><font color="red">*</font>音频名称:</label>
					<input id="nameLabel" name="audioName" style="width: 200px" class="easyui-textbox" readonly="true"/>
				</div>
				<div class="fitem">
					<label style="width: 70px;"><font color="red">*</font>演唱者:</label>
					<input id="singernameLabel" name="singerName" style="width: 200px" class="easyui-textbox" readonly="true"/>
				</div>
				<div class="fitem">
					<label style="width: 70px;"><font color="red">*</font>时长(毫秒):</label>
					<input id="durationLabel" name="duration" style="width: 200px" class="easyui-textbox" readonly="true"/>
				</div>
				<div class="fitem">
					<audio id="audioUrl" name="audio" src="" controls="controls" width="30px"></audio>
				</div>
			</form>
		</div>
		
	</div>

<script type="text/javascript" src="js/stream/js/stream-v1.js"></script>
<script type="text/javascript" src="js/stream/js/stream-upload-util.js"></script>
<script type="text/javascript">
	var index;
	var stream = singleFileUpload('version',function(file){
		var inputs = ''; 
	    for(var prop in file){
		  var value = file[prop];
		  if(prop == 'id'){
			  continue;
		  }
		  if($('input[name="' + prop + '"]').size() <= 0){
			  inputs += '<input type="hidden" id="'+prop+'" name="'+prop+'" value="'+value+'" />';
		  }else{
			  $('input[name="' + prop + '"]').remove();
			  inputs += '<input type="hidden" id="'+prop+'" name="'+prop+'" value="'+value+'" />';
		  }
	    }  
	    $('#fm').append(inputs);
		save();
	});
	//console.log(stream);
	
	function uploadAndSave(){
		var operType = $('#operType').val();
		if(operType == ''){
			//不上传图片,直接进行操作
			if(!validation()){
				return false;
			}
			save();
			return false;	
		}
		if(!validation()){
			return false;
		}
		stream.upload();
	}
	
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
		var val = $('#apkFileLabel').val();
		if (val == '') {
	        $.messager.alert('错误信息', '请上传音频文件!', 'error');
	        return false;
	    }
		var rr = $('#fm').form('enableValidation').form('validate');
	    if (rr) {
	    	index = layer.load('操作中...请等待！', 0);
	    } else {
	        return false;
	    }
	    return true;
	}
</script>	
</body>
</html>
