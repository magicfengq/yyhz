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

<title>反馈列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>

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
.ddv{
	height:200px;
}
</style>
</head>

<body>
	<div style="width:100%;height:100%">
		<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'back/feedbackAjaxPage.do', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'title',align:'center',sortable:true" style="width: 15%;">标题</th>
					<th data-options="field:'content',align:'center',sortable:true" style="width: 20%;">内容</th>
					<!-- <th data-options="field:'type',align:'center',sortable:true,formatter:typeFormater" style="width: 15%;">类型</th> -->
					<th data-options="field:'createDate',align:'center',sortable:false,formatter:timeFormater" style="width: 15%;">创建时间</th>
					<th data-options="field:'username',align:'center',sortable:true" style="width: 20%;">账号</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 15%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 100%;">
					<tr>
						<td class="ftitle">标题:</td><td>
							<input id="titleInput" class="easyui-textbox" style="width:200px"/>
						</td>
						<td class="ftitle">帐号:</td><td>
							<input id="usernameInput" class="easyui-textbox" style="width:200px"/>
						</td>
						<td><a href="javaScript:;" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
					</tr>
				</table>				
			</div>
		</div>
		
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:500px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">反馈意见</div>
			<form id="fm" name="fm" method="post" action="">
				<div class="fitem">
					<label>标题:</label>
					<input id="titleLabel" name="title" style="width: 200px" class="easyui-textbox" readonly="readonly"/>
				</div>
				<!--<div class="fitem">
					<label>类型:</label>
					<input id="typeLabel" name="type" style="width: 200px" class="easyui-textbox" readonly="readonly"/>
				</div> -->
				<div class="fitem">
					<label>内容:</label>
					<textarea id="contentLabel" name="content" style="width: 200px;height:50px;resize:none;" class="easyui-textarea" readonly="readonly"></textarea>
				</div>
				<div class="fitem">
					<label>账号:</label>
					<input id="usernameLabel" name="username" style="width: 200px" class="easyui-textbox" readonly="readonly"/>
				</div>
				<div class="fitem">
					<label>时间:</label>
					<input id="createDateLabel" name="createDate" style="width: 200px" class="easyui-textbox" readonly="readonly"/>
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:;" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#dlg').dialog('close')" style="width:90px">返回</a>
		</div>
		
	</div>
	<script type="text/javascript">
	var basePath = "<%=basePath%>";
	function timeFormater(value,row){
		if (value == undefined) {
            return "";
        }
		return getFormatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
	}
	function typeFormater(value, row) {
	    if (value == undefined) {
	        return "";
	    }
	    if (value == "0") {
	        return "意见";
	    }else{
	    		return "投诉";
	    }
	}
	//转换日期对象为日期字符串
	function getFormatDate(date, pattern) {
	    if (date == undefined) {
	        date = new Date();
	    }
	    if (pattern == undefined) {
	        pattern = "yyyy-MM-dd hh:mm:ss";
	    }
	    return date.format(pattern);
	}
	Date.prototype.format = function (format) {
	    var o = {
	        "M+": this.getMonth() + 1,
	        "d+": this.getDate(),
	        "h+": this.getHours(),
	        "m+": this.getMinutes(),
	        "s+": this.getSeconds(),
	        "q+": Math.floor((this.getMonth() + 3) / 3),
	        "S": this.getMilliseconds()
	    };
	    if (/(y+)/.test(format)) {
	        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    }
	    for (var k in o) {
	        if (new RegExp("(" + k + ")").test(format)) {
	            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	        }
	    }
	    return format;
	} ;
	function formatOptions(value, row,rowIndex) {
		return '<a href="javascript:void(0);" onclick="edit(\''
				+ row.id
				+ '\',\''+rowIndex+'\');">查看</a>'
				+ '&nbsp;<a href="javascript:void(0);" onclick="del(\''
				+ row.id + '\');">删除</a>';
	}
	function searchData() {
		$('#dg').datagrid('load', {
			title : $('#titleInput').val(),
			username : $('#usernameInput').val()
		});
	}
	function edit(id,rowIndex){
		//window.parent.addTabPanel('反馈意见','back/feedbackDetail.do?id=' +id);
		$('#dg').datagrid('selectRow',rowIndex);
		var row = $('#dg').datagrid('getSelected');
		$('#dlg').dialog('open').dialog('setTitle', '查看');
		$('#fm').form('load', row);
	}
	function del(id) {
		$.messager.confirm('提示', '你确定要删除吗?', function(r) {
			if (r) {
				$.post('back/feedbackAjaxDelete.do', {
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
</script>
</body>
</html>
