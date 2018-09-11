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

<title>推荐场地列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript" src="js/system/keditor.js"></script>
<script type="text/javascript" src="js/stream/js/stream-v1.js"></script>
<script type="text/javascript" src="js/stream/js/stream-upload-util.js"></script>
<script src="http://webapi.amap.com/maps?v=1.3&key=c5885f650224964ded82fb5a684abef9&plugin=AMap.Geocoder"></script>

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
			data-options="url:'back/siteListAjaxPage.do?type=1', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'siteName',align:'center',sortable:true" style="width: 15%;">场地名称</th>
					<th data-options="field:'address',align:'center',sortable:false" style="width: 25%;">场地地址</th>
					<th data-options="field:'phone',align:'center',sortable:false" style="width: 15%;">场地电话</th>
					<th data-options="field:'refereeName',align:'center',sortable:true" style="width: 15%;">推荐人</th>
					<th data-options="field:'refereePhone',align:'center',sortable:false" style="width: 15%;">推荐人联系电话</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 15%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 100%;">
					<tr>
						<td class="ftitle">场地名:</td><td><input id="siteNameInput" class="easyui-textbox" style="width:200px"/></td>
						<td class="ftitle">地址:</td><td><input id="addressInput" class="easyui-textbox" style="width:200px"></td>
						<td><a href="javaScript:;" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
					</tr>
				</table>				
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var basePath = "<%=basePath%>";
	function edit(id){
		window.parent.addTabPanel('场地推荐','back/siteRefereeDetail.do?type=1&id=' +id);
	}
	function del(id) {
		$.messager.confirm('提示', '你确定要删除吗?', function(r) {
			if (r) {
				$.post('back/cardInfoAjaxDelete.do', {
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
	function searchData(){
		$('#dg').datagrid('load',{
			siteName:$('#siteNameInput').val(),
			address:$('#addressInput').val()
		});
	}
	function formatOptions(value, row) {
		return '<a href="javascript:void(0);" onclick="edit(\''
				+ row.id
				+ '\');">查看</a>'
				+ '&nbsp;<a href="javascript:void(0);" onclick="del(\''
				+ row.id + '\');">删除</a>';
	}
</script>
</body>
</html>
