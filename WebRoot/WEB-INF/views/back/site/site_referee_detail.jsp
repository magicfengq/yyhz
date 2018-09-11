<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>场地推荐</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript" src="js/system/keditor.js"></script>
<script type="text/javascript" src="js/stream/js/stream-v1.js"></script>
<script type="text/javascript" src="js/stream/js/stream-upload-util.js"></script>
<link href="css/fa/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="resource/website/css/jquery.fancybox.min.css"/>
<script type="text/javascript" src="resource/website/js/jquery.fancybox.min.js"></script>
<script type="text/javascript" src="resource/website/js/fancybox-i18n-zh.js"></script>
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
.fitem img{
	height:100px;
	width:100px;
	margin: 5px;
}

.fitem label {
	display: inline-block;
	width: 90px;
}

.fitem input {
	width: 280px;
}
.ddv{
	height:200px;
}
</style>
</style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west'" style="width:350px; padding: 10px">
		<form id="fm" name="fm" method="post" action="back/siteListAjaxSave.do">
			<div class="ftitle">场地信息</div>

			<div class="fitem">
				<label>场地名称:</label>
				<input id="siteNameLabel" name="siteName" style="width: 200px" class="easyui-textbox" value="${info.siteName }" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>场地地址:</label>
				<input id="addressLabel" name="address" style="width: 200px" class="easyui-textbox" value="${info.address}" } readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>通道详情:</label>
				<input id="passagewayLabel" name="passageway" style="width: 200px" class="easyui-textbox" value="${info.passageway}" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>场地电话:</label>
				<input id="phoneLabel" name="phone" style="width: 200px" class="easyui-textbox" value="${info.phone }" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>推荐人姓名:</label>
				<input id="refereeNameLabel" name="refereeName" style="width: 200px" class="easyui-textbox" value="${info.refereeName }" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>推荐人联系方式:</label>
				<input id="refereePhoneLabel" name="refereePhone" style="width: 200px" class="easyui-textbox" value="${info.refereePhone }" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>经度:</label>
				<input id="longitudeLabel" name="longitude" style="width: 200px" class="easyui-textbox" value="${info.longitude }" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>纬度:</label>
				<input id="latitudeLabel" name="latitude" style="width: 200px" class="easyui-textbox" value="${info.latitude }" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label style="vertical-align: top;">其他说明:</label>
				<textarea id="textAreaLabel" name="textArea" style="width: 200px;height:50px;resize:none;" class="easyui-textarea" readonly="readonly">${info.textArea }</textarea>
			</div>
			
			<input type="hidden" id="idLabel" name="id" value="${info.id }"/>
			<input type="hidden" id="imgUuidLabel" name="imgUuid" value="${info.imgUuid }">
			<input type="hidden" id="type" name="type" value="1">
		</form>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">			
			<div data-options="region:'center'" style="padding: 10px">
				<div class="ftitle">场地照片</div>
				<div id="photos" class="fitem">
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var editor;
var index;
var lng = '${info.longitude }';
var lat = '${info.latitude }';
var address = '${info.address}';
function save(){
	if(!validation()){
		return false;
	}
	$('#fm').form('submit', {
	    dataType: 'json',
	    success: function(result) {
	      var result = eval('(' + result + ')');
	      layer.close(index);
	      if (result.success) {
	    	  $.messager.alert('保存成功', result.msg, 'info');
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

$(function(){
	$.ajax({
		url:"system/siteListPictureAjaxAll.do?siteId=${info.id }",
		type:'POST',
		dataType:'json',
		success:function(data){
			if(data && data.length > 0){
				$.each(data,function(i,v){
					var content = '<a class="fancyboxCls" data-fancybox="gallery" href="downFileResult.do?urlPath='+v.systemPictureInfo.urlPath+'">' + 
					'<img src=\'downFileResult.do?urlPath='+v.systemPictureInfo.urlPath+'\'/></a>';
					$('#photos').append(content);
				});
				fancybox();
			}
			
		}
	});
	
	KindEditor.ready(function(K) {
		editor = K.create('#contentHtml', {
			width : '730px',
			height:'300px',
			cssPath : '../js/kingeditor/plugins/code/prettify.css',
			//uploadJson : 'jsp/upload_json.jsp',
			uploadJson : basePath + 'keUpload.do?model=website_news_content',
			fileManagerJson : 'jsp/file_manager_json.jsp',
			allowFileManager : true,
			items : [ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
	    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
	    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
	    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
	    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
	    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
	    'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
	    'anchor', 'link', 'unlink', 'media'],
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
	
});
</script>
</body>
</html>
