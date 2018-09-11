<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

<title>系统用户</title>

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
	
	function doAdd() {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		$('#status').combobox('select', 1);
		$('#uPassCheckDiv').hide();
		$("#uPassDiv").show();
		$("#userPwd").textbox({required:true,validType:'length[2,32]'});
		$("#userPwd2").textbox({required:true,validType:'length[2,32]'});
	}
	
	function doEdit() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('load', row);
			$("#userPwd").textbox('setValue',null);//赋值
			$("#userPwd2").textbox('setValue',null);//赋值
			$("#userPwd").textbox({required:false,validType:'length[0,32]'});
			$("#userPwd2").textbox({required:false,validType:'length[0,32]'});
			$('#uPassCheckDiv').show();
			$("#uPassDiv").hide();
		}
	}
	
	function save() {
		var index ;
		$('#fm').form('submit', {
			onSubmit : function() {
				var rr=$(this).form('enableValidation').form('validate');
				if ($("#userPwd").val()!=$("#userPwd2").val()) {
					$.messager.show({
							title:'提示' , 
							msg:'两次密码输入不一致！'
						});
					return false;
				}
				var mobile = $("#mobile").val();
				var mobile_test = /^1[3|4|5|7|8][0-9]\d{8}$/;
				if ((mobile != "" && !mobile_test.test(mobile)) ) {
					$.messager.show({
						title:'提示' , 
						msg:'请输入正确手机号码'
					});
					return false;
				}
				if (rr) {
					index=layer.load('操作中...请等待！', 0);
				}else{
					return false;
				}
			},
			dataType:'json',
			success : function(result) {
				var result = eval('(' + result + ')');
				layer.close(index);
				if (result.success) {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
				} else {
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
			}
		});
	}
	
	function searchData() {
    	$('#dg').datagrid('load', {
                userName: $('#userName').val()
            }
         );
    }
    function formatRole(value, row) {
		return new Object(row["systemRole"]).name;
	}
	function formatStatus(value, row) {
		if (value==1) {
			return "启用";
		} else {
			return "禁用";
		}
	}
	
	function uPassCheckClick(obj){
		if ($(obj).is(":checked")) {
			$("#uPassDiv").show();
			$("#userPwd").textbox({required:true,validType:'length[6,32]'});
			$("#userPwd2").textbox({required:true,validType:'length[6,32]'});
		} else {
			$("#uPassDiv").hide();
			$("#userPwd").textbox({required:false,validType:'length[0,32]'});
			$("#userPwd2").textbox({required:false,validType:'length[0,32]'});
			$("#userPwd").textbox('setValue',null);//赋值
			$("#userPwd2").textbox('setValue',null);//赋值
		}
	}
	
	function doUpdateStatus(url,status) {
		var row = $('#dg').datagrid('getSelected');
		var msg="";
		if (row) {
			if (status==1) {
				msg="启用";
				if (row.status==1) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '此用户已启用！'
					});
					return;
				}
			}
			if (status==0) {
				msg="禁用";
				if (row.status==0) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '此用户已禁用！'
					});
					return;
				}
			}
			$.messager.confirm('Confirm', '你确定要'+msg+'此用户吗?', function(r) {
				if (r) {
					$.post(url, {
						id : row.id,
						status:status
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
	}
	function formatIsDelete(value, row) {
		if (value==1) {
			return "是";
		} else {
			return "否";
		}
	}
	function isDeleteStyler(value,row,index){
		if (value ==1){
			return 'background-color:#ccc;color:red;';
		}
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;">
		<table id="dg" class="easyui-datagrid"
			style="width:98%;min-height:400px"
			data-options="
		url:'system/systemUserAjaxAll.do',
		iconCls:'icon-save',
		rownumbers:true,
		pagination:true,
		singleSelect:true, 
		toolbar:'#toolbar',
		rowStyler:function(index,row){   
          	if (row.status==0){   
            		return 'color:red;';   
          	}  
     	}  
		">
			<thead>
				<tr>
					<th data-options="field:'userId',align:'center',sortable:true" style="width: 15%;">登录账号</th>
					<th data-options="field:'userName',align:'center',sortable:true" style="width: 15%;">用户名称</th>
					<th data-options="field:'roleId',align:'center',formatter:formatRole,sortable:true" style="width: 15%;">角色</th>
					<th data-options="field:'mobile',align:'center',sortable:true" style="width: 15%;">手机号码</th>
					<th data-options="field:'email',align:'center',sortable:true" style="width: 15%;">email</th>
					<th data-options="field:'description',align:'center',sortable:true" style="width: 15%;">备注</th>
					<th data-options="field:'status',width:80,align:'center',formatter:formatStatus,sortable:true" style="width: 5%;">状态</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				用户名称: <input id="userName" class="easyui-textbox"
					style="width:180px"> <a href="javaScript:void()"
					onclick="searchData()" class="easyui-linkbutton"
					iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="doAdd()">新建</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true" onclick="doEdit()">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('system/systemUserAjaxDelete.do')">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-lock_open" plain="true"
					onclick="doUpdateStatus('system/systemUserAjaxUpdate.do',1)">启用</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-lock" plain="true"
					onclick="doUpdateStatus('system/systemUserAjaxUpdate.do',0)">禁用</a>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:400px;height:500px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/systemUserAjaxSave.do" data-options="novalidate:true">
				<div class="fitem">
					<label>用户名称:</label> <input id="userName" name="userName"
						style="width: 200px" class="easyui-textbox"
						data-options="required:true"> <input type="hidden" id="id"
						name="id">
				</div>
				<div class="fitem">
					<label>登录账号:</label> <input id="userId" name="userId"
						style="width: 200px" class="easyui-textbox"
						data-options="required:true,validType:'length[2,11]'">
				</div>
				<div class="fitem" id="uPassCheckDiv">
					<label></label> <input style="width: 20px;vertical-align: middle;"
						id="uPassCheck" name="uPassCheck" type="checkbox"
						onclick="uPassCheckClick(this)">修改密码
				</div>
				<div id="uPassDiv">
					<div class="fitem">
						<label>登录密码:</label> <input id="userPwd" name="userPwd"
							type="password" style="width: 200px" class="easyui-textbox"
							data-options="validType:'length[2,32]'">
					</div>
					<div class="fitem">
						<label>确认密码:</label> <input id="userPwd2" type="password"
							name="userPwd2" style="width: 200px" class="easyui-textbox"
							data-options="validType:'length[2,32]'">
					</div>
				</div>
				<div class="fitem">
					<label>手机号码:</label> <input id="mobile" name="mobile"
						style="width: 200px" class="easyui-textbox">
				</div>
				<div class="fitem">
					<label>邮箱:</label> <input id="email" name="email"
						style="width: 200px" class="easyui-textbox"
						data-options="required:false,validType:'email'">
				</div>
				<div class="fitem">
					<label>状态:</label> <select id="status" name="status"
						class="easyui-combobox" style="width:100px;"
						data-options="required:true,panelHeight: 'auto',editable:false">
						<option value="1" selected>启用</option>
						<option value="0">禁用</option>
					</select>
				</div>
				<div class="fitem">
					<label>角色:</label> <input class="easyui-combobox" id="roleId"
						name="roleId" style="width:100px"
						data-options="url:'system/systemRoleAjaxAll.do',method:'get',valueField:'id',textField:'name',panelHeight:'auto',editable:false,required:true">
				</div>
				<div class="fitem">
					<label>描述:</label> <input name="description"
						data-options="multiline:true" class="easyui-textbox"
						style="height:60px;width: 200">
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="save()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
	</div>

</body>
</html>
