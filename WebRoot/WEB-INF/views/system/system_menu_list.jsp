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

<title>系统菜单管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/ztree.js"></script>

<SCRIPT type="text/javascript">
		var setting = {
			
			edit: {
				drag: {
					autoExpandTrigger: true,
					prev: dropPrev,
					inner: dropInner,
					next: dropNext
				},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				simpleData: {
					enable : false
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				beforeDragOpen: beforeDragOpen,
				onDrag: onDrag,
				onDrop: onDrop,
				onExpand: onExpand,
				onRightClick: OnRightClick,
				onClick:onClick
			}
		};
		function onClick(event, treeId, treeNode){
			$('#dgAct').datagrid({
				url: 'system/systemMenuActAjaxAll.do?menuId='+treeNode.id,
				method: 'get',
				title:treeNode.name,
				fitColumns: false,
				singleSelect: true,
				columns:[[
					{field:'actName',title:'名称',width:80},
					{field:'actCode',title:'代码',width:80},
					{field:'position',title:'位置',width:80},
					{field:'orderList',title:'排序',width:80}
				]],
				onClickRow: function (index, row) {
					$('#dgActUrl').datagrid({
						url: 'system/systemMenuActUrlAjaxAll.do?actId='+row.id,
						method: 'get',
						title:zTree.getSelectedNodes()[0].name+'-'+row.actName,
						fitColumns: false,
						singleSelect: true,
						columns:[[
							{field:'name',title:'名称',width:80},
							{field:'url',title:'url'}
						]]
					});
			    }
			});
		}
		function dropPrev(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropInner(treeId, nodes, targetNode) {
		
			if (targetNode && targetNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					if (!targetNode && curDragNodes[i].dropRoot === false) {
						return false;
					} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropNext(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}

		var className = "dark", curDragNodes, autoExpandNode;
		function beforeDrag(treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					curDragNodes = null;
					return false;
				} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
					curDragNodes = null;
					return false;
				}
			}
			curDragNodes = treeNodes;
			return true;
		}
		function beforeDragOpen(treeId, treeNode) {
			autoExpandNode = treeNode;
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			className = (className === "dark" ? "":"dark");
			return true;
		}
		function onDrag(event, treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
		}
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			
			className = (className === "dark" ? "":"dark");
			//alert(curDragNodes[0].id+"/"+targetNode ? targetNode.id : "root"+"/"+moveType);
			var pid=targetNode ? targetNode.id : "root";
			$.ajax({
	            url:'./system/systemMenuDrag.do',
	            data:{
					id:curDragNodes[0].id,
					pid:pid,
					moveType:moveType
				},
	            type:'post',
	            dataType:'json',
	            success:function(msg){
	            	var date = eval("(" + msg + ")");
	                if(date.result){
						//alert(1);
					}
	            }
	             
	        });
		}
		function onExpand(event, treeId, treeNode) {
			if (treeNode === autoExpandNode) {
				className = (className === "dark" ? "":"dark");
			}
		}

		function setTrigger() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
		}
		
		function OnRightClick(event, treeId, treeNode) {
			
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
			$('#rMenu').menu('show',{
                left: event.clientX,
                top: event.clientY
            });
		}

		function showRMenu(type, x, y) {
			
			if (type=="root") {
				$("#menuModify").hide();
				$("#menuDelete").hide();
			} else {
				$("#menuModify").show();
				$("#menuDelete").show();
			}
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

		}
		
		function checkTreeNode(checked) {
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length>0) {
				zTree.checkNode(nodes[0], checked, true);
			}
		}
		function resetTree() {
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}

		var zTree, rMenu;
		
		$(document).ready(function(){
			$.ajax({
				type : "POST",
				dataType : "JSON",
				url : "./system/systemMenuAjaxAll.do",
				success : function(data) {
					//var z=jQuery.parseJSON(data);alert(data);
					$.fn.zTree.init($("#treeDemo"), setting, data);
					zTree=$.fn.zTree.getZTreeObj("treeDemo");
				}
			});
			$("#callbackTrigger").bind("change", {}, setTrigger);
			rMenu = $("#rMenu");
		});
		function save() {
			$.ajax({
				cache: true,
				type: "POST",
				url:"./system/systemMenuAjaxSave.do",
				data:$('#fm').serialize(),// 你的formid
				async: false,
				dataType : "JSON",
				success : function(data) {
					//var z=jQuery.parseJSON(data);alert(data);
					if ($("#id").val()=='') {
						if (zTree.getSelectedNodes()[0]) {
							data.checked = zTree.getSelectedNodes()[0].checked;
							zTree.addNodes(zTree.getSelectedNodes()[0], data);
						}else {
							zTree.addNodes(null, data);
						}
					} else {
						zTree.getSelectedNodes()[0].name=data.name;
						zTree.getSelectedNodes()[0].menuUrl=data.menuUrl;
						zTree.getSelectedNodes()[0].description=data.description;
						zTree.updateNode(zTree.getSelectedNodes()[0],false);
					}
					 
					$('#dlg').dialog('close');
				}
			});
		}
		function doAdd(){
			$('#dlg').dialog('open').dialog('setTitle', '新建');
			$('#fm').form('clear');
			if (zTree.getSelectedNodes()[0]) {
				$("#pid").val(zTree.getSelectedNodes()[0].id);
			}
		}
		function doEdit(){
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('clear');
			if (zTree.getSelectedNodes()[0]) {
				var node=zTree.getSelectedNodes()[0];
				$('#fm').form('load', node);
			}
		}
		function doDelete(){
			if(!confirm("你确定要删除吗")){
				return;
			}
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length>0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
					if (confirm(msg)==true){
						$.ajax({
							cache: true,
							type: "POST",
							url:"./system/systemMenuAjaxDelete.do",
							data:{
								id:zTree.getSelectedNodes()[0].id
							},// 你的formid
							async: false,
							dataType : "JSON",
							success : function(data) {
								var result = eval("(" + data + ")");
								if(result){
									zTree.removeNode(nodes[0]);
								}
							}
						});
					}
				} else {
					$.ajax({
						cache: true,
						type: "POST",
						url:"./system/systemMenuAjaxDelete.do",
						data:{id:zTree.getSelectedNodes()[0].id},// 你的formid
						async: false,
						dataType : "JSON",
						success : function(data) {
							var result = eval("(" + data + ")");
							if(result){
								zTree.removeNode(nodes[0]);
							}
						}
					});
				}
			}
			
		}
		/* 动作操作start */
		function doActAdd() {
			$('#dlgAct').dialog('open').dialog('setTitle', '新建');
			$('#fmAct').form('clear');
		}
		function doActEdit() {
			var row = $('#dgAct').datagrid('getSelected');
			if (row) {
				if(row.actCode=='menu'){
					$.messager.show({
						title : '提示',
						msg : '此项不能修改'
					});
					return;
				}
				$('#dlgAct').dialog('open').dialog('setTitle', '修改');
				$('#fmAct').form('load', row);
				$('#actId').val(row.id);
			}
		}
		function saveAct() {
			if ($("#actCode").val()=="") {
				$.messager.show({
					title : '提示',
					msg : '代码不能为空'
				});
				return ;
			}
			if ($("#actName").val()=="") {
				$.messager.show({
					title : '提示',
					msg : '名称不能为空'
				});
				return ;
			}
			
			if ($("#orderList").val()=="") {
				$.messager.show({
					title : '提示',
					msg : '排序不能为空'
				});
				return ;
			}
			
			var index=layer.load('操作中...请等待！', 0);
			$.ajax({  
                type : 'post',  
                url : 'system/systemMenuActAjaxSave.do',  
                data: {
                	id:$("#actId").val(),
                	actName:$("#actName").val(),
                	actCode:$("#actCode").val(),
					menuId:zTree.getSelectedNodes()[0].id,
					position:$("#position").val(),
					orderList:$("#orderList").val()
                },  
                dataType : 'json',  
                //同步  
                async : false,  
                success : function(result) {  
					layer.close(index);
					if (result.success) {
						$('#dlgAct').dialog('close'); // close the dialog
						$('#dgAct').datagrid('reload'); // reload the user data
					} else {
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					}
                } 
            }); 
		}
		function doActDelete() {
			var row = $('#dgAct').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm', '你确定要删除吗?', function(r) {
					if (r) {
						$.post('system/systemMenuActAjaxDelete.do', {
							id : row.id
						}, function(result) {
							if (result.success) {
								$('#dgAct').datagrid('reload'); // reload the user data
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
		/* 动作操作end */
		/* 资源操作start */
		function doUrlAdd() {
			$('#dlgUrl').dialog('open').dialog('setTitle', '新建');
			$('#fmUrl').form('clear');
			var row = $('#dgAct').datagrid('getSelected');
			$("#urlActId").val(row.id);
		}
		function doUrlEdit() {
			var row = $('#dgActUrl').datagrid('getSelected');
			if (row) {
				$('#dlgUrl').dialog('open').dialog('setTitle', '修改');
				$('#fmUrl').form('load', row);
				$('#actUrlId').val(row.id);
				$('#urlActId').val(row.actId);
				$('#url').val(row.url);
				$('#actUrlName').textbox('setValue',row.name);
			}
		}
		function saveUrl() {
			var index=layer.load('操作中...请等待！', 0);
			$.ajax({  
                type : 'post',  
                url : 'system/systemMenuActUrlAjaxSave.do',  
                data: {
                	id:$("#actUrlId").val(),
                	actId:$("#urlActId").val(),
					url:$("#url").val(),
					name:$("#actUrlName").val()
                },  
                dataType : 'json',  
                //同步  
                async : false,  
                success : function(result) {  
					layer.close(index);
					if (result.success) {
						$('#dlgUrl').dialog('close'); // close the dialog
						$('#dgActUrl').datagrid('reload'); // reload the user data
					} else {
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					}
                } 
            }); 
		}
		function doUrlDelete() {
			var row = $('#dgActUrl').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm', '你确定要删除吗?', function(r) {
					if (r) {
						$.post('system/systemMenuActUrlAjaxDelete.do', {
							id : row.id
						}, function(result) {
							if (result.success) {
								$('#dgActUrl').datagrid('reload'); // reload the user data
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
		/* 资源操作end */
		</SCRIPT>
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
	width: 40px;
}

.fitem input {
	width: 280px;
}
</style>
</head>

<body>
	<div class="easyui-layout" style="width:100%;height:420px;">
		<div data-options="region:'east',split:true" title="资源"
			style="width:350px;overflow: hidden;">
			<table id="dgActUrl" class="easyui-datagrid"
				style="width:100%;min-height:380px" rownumbers="true"
				pagination="false" singleSelect="true" toolbar="#toolbarUrl">
			</table>
		</div>
		<div data-options="region:'west',split:true" title="菜单"
			style="width:280px;">
			<div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
		<div data-options="region:'center',title:'动作'"
			style="overflow: hidden;">
			<table id="dgAct" class="easyui-datagrid"
				style="width:100%;min-height:380px" rownumbers="true"
				pagination="false" singleSelect="true" toolbar="#toolbarAct">
			</table>
		</div>
	</div>
	<!-- 菜单工具 start-->
	<div id="rMenu" class="easyui-menu" style="width:120px;">
		<div onclick="doAdd()" data-options="iconCls:'icon-add'">添加</div>
		<div id="menuModify" onclick="doEdit()"
			data-options="iconCls:'icon-edit'">修改</div>
		<div id="menuDelete" onclick="doDelete()"
			data-options="iconCls:'icon-remove'">删除</div>
	</div>

	<div id="dlg" class="easyui-dialog "
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="width:400px;height:280px;padding:10px 20px;top:30px"
		closed="true" buttons="#dlg-buttons" title="新建">
		<div class="ftitle">请完善以下信息！</div>
		<form id="fm" name="fm" method="post" novalidate
			enctype="multipart/form-data">
			<div class="fitem">
				<label>名称:</label> <input class="easyui-textbox" type="text"
					id="name" name="name" data-options="required:true" /> <input
					type="hidden" name="id" id="id" /> <input type="hidden" name="pid"
					id="pid" />
			</div>
			<div class="fitem">
				<label>URL:</label> <input class="easyui-textbox" type="text"
					id="menuUrl" name="menuUrl" />
			</div>
			<div class="fitem">
				<label>描述:</label> <input name="description" id="description"
					data-options="multiline:true" class="easyui-textbox"
					style="height:60px;">
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
	<!-- 菜单工具end -->
	<!-- 动作工具 start-->
	<div id="toolbarAct">
		<div style="margin-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="doActAdd()">新建</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="doActEdit()">修改</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="doActDelete()">删除</a>
		</div>
	</div>

	<div id="dlgAct" class="easyui-dialog right"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="width:400px;height:280px;padding:10px 20px;top:30px"
		closed="true" buttons="#dlgAct-buttons" title="新建">
		<div class="ftitle">请完善以下信息！</div>
		<form id="fmAct" name="fmAct" method="post"
			data-options="novalidate:true" enctype="multipart/form-data">
			<div class="fitem">
				<label>名称:</label> <input class="easyui-textbox" type="text"
					id="actName" name="actName" data-options="required:true" /> <input
					type="hidden" name="actId" id="actId" />
			</div>
			<div class="fitem">
				<label>代码:</label> <input class="easyui-textbox" type="text"
					id="actCode" name="actCode" data-options="required:true" />
			</div>
			<div class="fitem">
				<label>位置:</label> <input class="easyui-textbox" type="text"
					id="position" name="position" />
			</div>
			<div class="fitem">
				<label>排序:</label> <input class="easyui-textbox" type="text"
					id="orderList" name="orderList" data-options="required:true" />
			</div>
		</form>
	</div>
	<div id="dlgAct-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="saveAct()" style="width:90px">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#dlgAct').dialog('close')" style="width:90px">取消</a>
	</div>
	<!-- 动作工具end -->
	<!-- 资源工具 start-->
	<div id="toolbarUrl">
		<div style="margin-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="doUrlAdd()">新建</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="doUrlEdit()">修改</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="doUrlDelete()">删除</a>
		</div>
	</div>

	<div id="dlgUrl" class="easyui-dialog right"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		buttons="#dlgUrl-buttons" title="新建">
		<div class="ftitle">请完善以下信息！</div>
		<form id="fmUrl" name="fmUrl" method="post"
			data-options="novalidate:true" enctype="multipart/form-data">
			<div class="fitem">
				<label>名称:</label> <input class="easyui-textbox" type="text"
					id="actUrlName" name="actUrlName" data-options="required:true" />
				<input type="hidden" name="actUrlId" id="actUrlId" /> <input
					type="hidden" name="urlActId" id="urlActId" />
			</div>
			<div class="fitem">
				<label>url:</label> <input class="easyui-textbox" type="text"
					id="url" name="url" />
			</div>

		</form>
	</div>
	<div id="dlgUrl-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="saveUrl()" style="width:90px">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#dlgUrl').dialog('close')" style="width:90px">取消</a>
	</div>
	<!-- 资源工具end -->
</body>
</html>
