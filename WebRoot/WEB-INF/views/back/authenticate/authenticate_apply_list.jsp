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

<title>认证申请列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript">
	
	function formatMobile(value, row){
		if(row.actorInfo){
			return row.actorInfo.mobile;	
		}
		return '';
	}
	
	function formatRoleName(value, row){
		if(row.actorInfo){
			return row.actorInfo.roleName;	
		}
		return '';
	}
	
	function formatLevel(value, row){
		var userCurrentLevel = row.userCurrentLevel;
		if(userCurrentLevel == 0){
			return '未认证';
		}else if(userCurrentLevel == 1){
			return '实名认证';
		}else if(userCurrentLevel == 2){
			return '资历认证';
		}
		return '';
	}
	
	function formatImage(value,row){
		if(row.actorInfo){
			var url = 'downFileResult.do?urlPath='+row.actorInfo.systemPictureInfo.urlPath;
			return "<img src="+ url +" style=\"height:50px;width:50px;\"/>";	
		}
	}
	
	function formatCheckStatus(value, row){
		var checkStatus = row.checkStatus;
		if(checkStatus == 0){
			return '待审核';
		}else if(checkStatus == 1){
			return '已通过';
		}else if(checkStatus == 2){
			return '已拒绝';
		}
		return '';
	}
	
	function formatCreateTime(value, row){
		return row.actorInfo.createTime;
	}
	
	function formatOptions(value, row){
		return '<a href="javascript:void(0);" onclick="toView(\'' + row.id + '\');">查看</a>&nbsp;' + 
		       '<a href="javascript:void(0);" onclick="showPassDialog(\'' + row.id + '\',\'' + row.actorId + '\',' + row.checkStatus + ');">通过</a>&nbsp;' + 
		       '<a href="javascript:void(0);" onclick="showRefuseDialog(\'' + row.id + '\',\'' + row.actorId + '\',' + row.checkStatus + ');">拒绝</a>&nbsp;';
	}				
	
	function toView(id){
		window.parent.addTabPanel('认证申请详情','system/authenticateApplyDetail.do?id=' +id);
	}
	
	function searchData(){
		$('#dg').datagrid('load',{
			account:$('#mobileInput').val(),
			realName:$('#realNameInput').val(),
			roleName:$('#roleNameInput').val()
		});
	}
	
	function showPassDialog(id,actorId,checkStatus){
		$('#passFm').form('clear');
		$('#refuseFm').form('clear');
		//$('#userCurrentLevelLabel').combobox('select', 1);
    	$('#passDlg').dialog('open').dialog('setTitle', '审核通过');
    	$('#idLabel').val(id);
    	$('#checkStatusLabel').val(checkStatus);
    	$('#actorIdLabel').val(actorId);
    } 
    
    function showRefuseDialog(id,actorId,checkStatus){
    	$('#passFm').form('clear');
    	$('#refuseFm').form('clear');
    	$('#refuseDlg').dialog('open').dialog('setTitle', '审核拒绝');
    	$('#idLabel').val(id);
    	$('#checkStatusLabel').val(checkStatus);
    	$('#actorIdLabel').val(actorId);
    }
    
    function doPass(){
    	var checkStatus = $('#checkStatusLabel').val();
    	if(checkStatus == 0){
    		pass();
    	}else if(checkStatus == 1){
    		$.messager.confirm('提示','当前已是审核通过状态,请问要重新审核为通过状态吗?', function(r){
    			if (r) pass();
    		});
    	}else if(checkStatus == 2){
    		 $.messager.confirm('提示', '当前已是拒绝状态,请问要重新审核为通过状态吗?', function(r){
                 if (r) pass();
             });
    	}
    }
    
    function pass(){
    	$.ajax({
			url:"system/authenticateAuditPass.do",
			data:{id:$('#idLabel').val(),actorId:$('#actorIdLabel').val()},		
			type:'POST',
			dataType:'json',
			success:function(data){
				if(data.success){
					$('#passDlg').dialog('close');
					$('#dg').datagrid('reload');
				}else{
					$.messager.alert('提示',data.msg,'error');
				}
			}
		});
    }
    
    function doRefuse(){
    	var checkStatus = $('#checkStatusLabel').val();
    	if(checkStatus == 0){
    		refuse();
    	}else if(checkStatus == 1){
    		$.messager.confirm('提示', '当前已是通过状态,请问要重新审核为拒绝状态吗?', function(r){
                if (r) refuse();
            });
    		
    	}else if(checkStatus == 2){
    		$.messager.alert('提示','当前已是审核拒绝状态!');
    		return false;
    	}
    }
    
    function refuse(){
    	var content = $('#refuseContentLabel').val();
    	if($.trim(content) == ''){
    		$.messager.alert('提示','请填写拒绝理由!','error');
    		return false;
    	}
    	$.ajax({
			url:"system/authenticateAuditRefuse.do",
			data:{id:$('#idLabel').val(),actorId:$('#actorIdLabel').val(),refuseContent:$('#refuseContentLabel').val()},	
			type:'POST',
			dataType:'json',
			success:function(data){
				if(data.success){
					$('#refuseDlg').dialog('close');
					$('#dg').datagrid('reload');
				}else{
					$.messager.alert('提示',data.msg,'error');
				}
			}
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
			data-options="url:'system/authenticateApplyAjaxPage.do', iconCls:'icon-save',
			rownumbers:true, pagination:true, singleSelect:true,
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'mobile',align:'center',sortable:true,formatter:formatMobile" style="width: 15%;">账号</th>
					<th data-options="field:'realName',align:'center',sortable:true" style="width: 10%;">姓名</th>
					<th data-options="field:'idcard',align:'center',sortable:true" style="width: 10%;">身份证号</th>
					<th data-options="field:'userCurrentLevel',align:'center',sortable:true,formatter:formatLevel" style="width: 15%;">申请等级</th>
					<th data-options="field:'type',align:'center',sortable:true,formatter:formatRoleName" style="width: 10%;">角色</th>					
					<th data-options="field:'createTime',align:'center',sortable:true,formatter:formatCreateTime" style="width: 15%;">注册时间</th>
					<th data-options="field:'checkStatus',align:'center',sortable:true,formatter:formatCheckStatus" style="width: 10%;">状态</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 15%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 60%;">
					<tr>
						<td class="ftitle">账号:</td><td><input id="mobileInput" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">姓名:</td><td><input id="realNameInput" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">角色:</td><td><input id="roleNameInput" class="easyui-textbox" style="width:120px"></td>												
						<td><a href="javascript:void(0);" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="passDlg" class="easyui-dialog"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="padding:10px 20px;" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">请确认是否通过？</div>
		<form id="passFm" name="passFm" method="post">
			<input type="hidden" name="id" id="idLabel"/>
			<input type="hidden" name="actorId" id="actorIdLabel"/>
			<input type="hidden" name="checkStatus" id="checkStatusLabel"/>
			<!--<div class="fitem">
				<label><font color="red">*</font>认证级别:</label>
				<select id="userCurrentLevelLabel" name="userCurrentLevel" class="easyui-combobox" style="width:100px;" 
							data-options="panelHeight:'auto',editable:false">
							<option value="1" selected="selected">实名认证</option>
							 <option value="2">资历认证</option> 
						</select>-->
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="doPass()" style="width:90px">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#passDlg').dialog('close')" style="width:90px">取消</a>
	</div>
	<div id="refuseDlg" class="easyui-dialog"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="padding:10px 20px;" closed="true"
		buttons="#refuseDlg-buttons">
		<form id="refuseFm" name="refuseFm" method="post" action="system/advertAjaxSave.do">
			<input type="hidden" name="id" id="idLabel"/>
			<input type="hidden" name="actorId" id="actorIdLabel"/>
			<input type="hidden" name="checkStatus" id="checkStatusLabel"/>
			<div class="fitem">
				<label><font color="red">*</font>拒绝理由:</label>
				<input id="refuseContentLabel" name="refuseContent" class="easyui-textbox" multiline="true" value="" style="width:360px;height:120px"/>		
			</div>
		</form>
	</div>
	<div id="refuseDlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="doRefuse()" style="width:90px">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#refuseDlg').dialog('close')" style="width:90px">取消</a>
	</div>
	</div>
</body>
</html>
