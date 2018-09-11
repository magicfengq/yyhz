<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
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

<title>${entity.title }</title>

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
<link rel="stylesheet" href="js/kingeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="js/kingeditor/kindeditor.js"></script>
<script charset="utf-8" src="js/kingeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="js/kingeditor/plugins/code/prettify.js"></script>
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
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('#msgContent', {
		height:'400px',
			cssPath : 'js/kingeditor/plugins/code/prettify.css',
			uploadJson : 'jsp/upload_json.jsp',
			fileManagerJson : 'jsp/file_manager_json.jsp',
			allowFileManager : true,
			items : [ 'source','indent','outdent','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
					'bold', 'italic', 'underline', 'removeformat', '|', 'indent', 'outdent',
					'justifyleft', 'justifycenter', 'justifyright','justifyfull','lineheight',
					'insertorderedlist', 'insertunorderedlist', '|',
					'emoticons', 'image', 'link','table','baidumap' ],
			afterChange : function() {
				this.sync();// 这个是必须的,如果你要覆盖afterChange事件的话,请记得最好把这句加上.
			},
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					document.forms['fm'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['fm'].submit();
				});
			}
		});
		prettyPrint();
	});
	function save() {
		var index ;
		$('#fm').form('submit', {
			onSubmit : function() {
				var rr=$(this).form('enableValidation').form('validate');
				
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
				$.messager.show({
						title : '提示',
						msg : result.msg
					});
				if (result.success) {
					window.location.href = "${ pageContext.request.contextPath}/system/systemFullTextEntity.do?type="+$("#type").val();
				}
			}
		});
	}
</script>
</head>

<body>
	<form id="fm" action="system/systemFullTextSave.do" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id }">
		<input type="hidden" id="type" name="type" value="${entity.type }">
		<div class="easyui-panel" title="${entity.title }"
			style="width:700px;height:100%;padding:10px;"
			data-options="iconCls:'icon-filter',closable:false,tools:'#tt'">
			<textarea id="msgContent" name="msgContent"
				style="width:100%;height:300px;visibility:hidden;">${entity.msgContent }</textarea>
		</div>
		<div id="tt">
			<a href="javascript:void(0)" class="icon-save"
				onclick="javascript:save()" title="保存"></a>
		</div>
	</form>
</body>
</html>
