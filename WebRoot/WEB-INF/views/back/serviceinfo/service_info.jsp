<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>服务信息编辑</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript" src="js/system/keditor.js"></script>
<link href="css/fa/font-awesome.min.css" rel="stylesheet" />
<style type="text/css">
<
style type ="text/css">#fm {
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

.fitem img {
	height: 100px;
	width: 100px;
	margin: 5px;
}

.fitem label {
	display: inline-block;
	width: 65px;
}

.fitem input {
	width: 280px;
}

.ddv {
	height: 200px;
}
</style>
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:false"
			style="height: 500px; padding: 10px;">
			<form id="fm" name="fm" method="post"
				action="back/serviceInfoAjaxSave.do">
				<textarea id="contentHtml" name="context">${context }</textarea>
				<br /> <a href="javaScript:;" id="saveBtn" class="easyui-linkbutton"
					iconCls="icon-save">保存</a> <input type="hidden" id="idLabel"
					name="id" value="${id }" /> <input type="hidden" id="typeLabel"
					name="type" value="${type }" />
			</form>
		</div>
	</div>
	<script type="text/javascript">
	
	$(function(){
		var index;
		var basePath = "<%=basePath%>";
		var editor;
			KindEditor.ready(function(K) {
				editor = K.create('#contentHtml', {
					width : '500px',
					height : '400px',
					cssPath : '../js/kingeditor/plugins/code/prettify.css',
					//uploadJson : 'jsp/upload_json.jsp',
					uploadJson : basePath
							+ 'keUpload.do?model=website_news_content',
					fileManagerJson : 'jsp/file_manager_json.jsp',
					allowFileManager : true,
					items : [ 'source', '|', 'undo', 'redo', '|', 'preview',
							'print', 'template', 'code', 'cut', 'copy',
							'paste', 'plainpaste', 'wordpaste', '|',
							'justifyleft', 'justifycenter', 'justifyright',
							'justifyfull', 'insertorderedlist',
							'insertunorderedlist', 'indent', 'outdent',
							'subscript', 'superscript', 'clearhtml',
							'quickformat', 'selectall', '|', 'fullscreen', '/',
							'formatblock', 'fontname', 'fontsize', '|',
							'forecolor', 'hilitecolor', 'bold', 'italic',
							'underline', 'strikethrough', 'lineheight',
							'removeformat', '|', 'image', 'multiimage',
							'table', 'hr', 'emoticons', 'baidumap',
							'pagebreak', 'anchor', 'link', 'unlink', 'media' ],
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
			$('#saveBtn').click(function() {
				$('#fm').form('submit', {
					dataType : 'json',
					success : function(result) {
						var result = eval('(' + result + ')');
						layer.close(index);
						if (result.success) {
							$.messager.alert('操作成功', result.msg, 'info',function(){
								window.location.reload();
							});
						} else {
							$.messager.alert('错误信息', result.msg, 'error');
							return false;
						}
					}
				});
			});
		});
	</script>
</body>
</html>
