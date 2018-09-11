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

<title>主持模特卡片列表</title>

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
}

.fitem {
	margin-bottom: 5px;
}

/* .fitem.col50{
	
} */

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
			data-options="url:'back/cardInfoAjaxPage.do?type=1', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'cardName',align:'center',sortable:true" style="width: 15%;">艺名</th>
					<th data-options="field:'detailRole',align:'center',sortable:false" style="width: 10%;">角色</th>
					<th data-options="field:'sex',align:'center',sortable:true,formatter:sexFormatter" style="width: 10%;">性别</th>
					<th data-options="field:'city',align:'center',sortable:true" style="width: 10%;">所在地</th>
					<th data-options="field:'type',align:'center',sortable:false,formatter:cardTypeFormater" style="width: 10%;">卡片类型</th>
					<th data-options="field:'createrName',align:'center',sortable:true" style="width: 15%;">发布账号</th>
					<th data-options="field:'createTime',align:'center',sortable:true,formatter:timeFormater" style="width: 15%;">发布时间</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 15%;">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 100%;">
					<tr>
						<td class="ftitle">艺名:</td><td>
							<input id="cardNameInput" class="easyui-textbox" style="width:200px"/>
						</td>
						<td class="ftitle">性别:</td><td>
							<select id="sexInput" class="easyui-combobox" style="width:120px;" data-options="panelHeight:'auto',editable:false,required:false">
								<option value="" selected="selected">全部</option>
								<option value="1">男</option>
								<option value="2">女</option>
							</select>
						</td>
						<td class="ftitle">角色:</td><td>
							<input id="detailRoleInput" class="easyui-textbox" style="width:200px"/>
						</td>
						<td class="ftitle">城市:</td><td>
							<input id="cityInput" class="easyui-textbox" style="width:200px">
						</td>
						<td><a href="javaScript:;" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
					</tr>
				</table>				
			</div>
		</div>
	</div>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	function formatOptions(value, row) {
		return '<a href="javascript:void(0);" onclick="edit(\''
				+ row.id
				+ '\');">查看|修改</a>'
				+ '&nbsp;<a href="javascript:void(0);" onclick="del(\''
				+ row.id + '\');">删除</a>';
	}
	function sexFormatter(value, row) {
		if (value == undefined) {
			return "";
		}
		if (value == '1') {
			return '男';
		} else if (value == '2') {
			return '女';
		}
	}
	function cardTypeFormater(value, row) {
		return "主持/模特";
	}
	function timeFormater(value, row) {
		if (value == undefined) {
			return "";
		}
		return getFormatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
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
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			"S" : this.getMilliseconds()
		};
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	};
	function searchData() {
		$('#dg').datagrid('load', {
			cardName : $('#cardNameInput').val(),
			sex : $('#sexInput').val(),
			detailRole : $('#detailRoleInput').val(),
			city : $('#cityInput').val()
		});
	}
	function edit(id){
		window.parent.addTabPanel('主持/模特','back/cardListDetail.do?type=1&id=' +id);
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
</script>
</body>
</html>
