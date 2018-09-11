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

<title>轮播图管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript" src="js/system/keditor.js"></script>
<script type="text/javascript" src="js/template.js"></script>
<link rel="stylesheet" type="text/css" href="resource/website/css/jquery.fancybox.min.css"/>
<script type="text/javascript" src="resource/website/js/jquery.fancybox.min.js"></script>
<script type="text/javascript" src="resource/website/js/fancybox-i18n-zh.js"></script>

<script>
	var basePath = "<%=basePath%>";
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('#contentHtml', {
			width : '750px',
			height:'300px',
			cssPath : '../js/kingeditor/plugins/code/prettify.css',
			//uploadJson : 'jsp/upload_json.jsp',
			uploadJson : basePath + 'keUpload.do?model=advert',
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
	
</script>

<script type="text/html" id="tpl">
	<iframe id="detailFrame" name="detailFrame" frameborder="0" height="100%" width="100%" align="center"></iframe>	
</script>

<script type="text/javascript">

	$(function() {
		
	  $('#addAdvertBtn').click(function(){
		  doAdd(function(){
			  $('#imgShow').attr('src','');
			  $('#addImg').show();
			  $('#imgShow').hide();
			  $('#status').combobox('select', 1);
			  $('#typeLabel').val('0');
			  //$('#contentHtml').show();
			  editor.html('');
			  
			  //显示富文本框
			  $('#contentDiv').show();
			  
			  //隐藏用户
			  $('#userNameDiv').hide();
		  });
	  });
	  
	  $('#addPersonalBtn').click(function(){
		  doAdd(function(){
			  $('#imgShow').attr('src','');
			  $('#addImg').show();
			  $('#imgShow').hide();
			  $('#status').combobox('select', 1);
			  $('#typeLabel').val('1');
			  
			  //隐藏富文本框
			  editor.html('');
			  $('#contentDiv').hide();
			  
			  //显示用户
			  $('#userNameDiv').show();
		  });
	  });
	  
	  $('#editBtn').click(function(){
		  doEdit(function(row){
			  $('#addImg').hide();			  
			  $('#imgShow').attr('src','downFileResult.do?urlPath=' + row.systemPictureInfo.urlPath);
			  $('#imgShow').show();
			  if(row.type == 1){
				  //个人秀
				  $('#typeLabel').val('1');		  
				  //隐藏富文本框
				  editor.html('');
				  $('#contentDiv').hide();				  
				  //显示用户
				  $('#userNameDiv').show();
				  //启用校验
				  $('#userNameLabel').textbox('enableValidation');
			  }else{
				  editor.html(row.params);	
					//显示富文本框
				  $('#contentDiv').show();
				  
				  //隐藏用户
				  $('#userNameDiv').hide();
			  }
		  });
	  });
	  
	  $('#deleteBtn').click(function(){
		  doDelete('system/advertAjaxDelete.do');
	  });
	  
	  $('#selectUserBtn').tooltip({
		 content: template("tpl"),
		 showEvent: 'click',
		 hideEvent:'none',
		 position:'right',
         onShow: function(){
        	 var t = $(this);
        	 $("#detailFrame").attr('src', "system/userSelect.do");
        	 t.tooltip('tip').unbind().bind('mouseenter', function(){
                 //t.tooltip('show');
             }).bind('mouseleave', function(){
                 t.tooltip('hide');
             });
        	 t.tooltip('tip').css({
        		 width:'60%',
        		 height:'60%'
        	 });
        	 t.tooltip('reposition');
         }
	  });
	});

	function formatImg(value, row) {
		if(row.systemPictureInfo){
			//var url = 'http://175.102.18.47:8084/perform/downFileResult.do?urlPath=' + row.systemPictureInfo.urlPath;
			var url = 'downFileResult.do?urlPath=' + row.systemPictureInfo.urlPath;
			var content = '<a class="fancyboxCls" data-fancybox="gallery" title="' + row.title + '" href="'+ url +'">' + 
        				  	'<img src="'+url+'" style=\"height:90px;width:160px;\"/>' + 
        				  '</a>';
			return content;	
		}		
	}
	
	function formatType(value, row) {
		var view = (row.type == 1) ? '个人秀' : '广告';
		return view;
	}
	
	function selectOver(userId,userName){
		$('#userIdLabel').val(userId);
		$('#userNameLabel').textbox('setValue',userName);
		$('#selectUserBtn').tooltip('hide');
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
</style>
</head>

<body>
	<div style="width:100%;height:100%">
		<div id="toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addAdvertBtn">添加广告</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addPersonalBtn">添加个人秀</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editBtn">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>	
		</div>
		<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'system/advertAjaxPage.do', iconCls:'icon-save',
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar',onLoadSuccess:function(data){fancybox();}">
			<thead>
				<tr>
					<th data-options="field:'title',align:'center',sortable:true" style="width: 20%;">标题</th>
					<th data-options="field:'userName',align:'center',sortable:true" style="width: 20%;">关联用户</th>
					<th data-options="field:'createTime',align:'center',sortable:true" style="width: 20%;">创建时间</th>
					<th data-options="field:'status',align:'center',sortable:true,formatter:formatType" style="width: 10%;">类型</th>
					<th data-options="field:'urlPath',align:'center',sortable:true,formatter:formatImg" style="width: 15%;">图标</th>
				</tr>
			</thead>
		</table>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:100%;height:100%;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/advertAjaxSave.do">
				<div class="fitem">
					<label><font color="red">*</font>标题:</label>
					<input id="titleLabel" name="title" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
				</div>
				<div class="fitem">
					<div style="float: left;margin-top: 25px;"><font color="red">*</font>图片:</div>
					<div id="showImage" class="showImage" style="width:160px;height:90px;border:1px solid;margin-left:70px;cursor:pointer;text-align:center;" >
						<!-- <img id="imgShow" class="imgShow" src="images/add.png" style="width:50px;height:50px;"/> -->
						<img id="addImg" class="addImg" src="images/add.png" style="width:50px;height:50px;padding-top: 20px;"/>
						<img id="imgShow" class="imgShow" src="" style="display:none;width:100%;height:100%;"/>
					</div>
					<div style="width:160px;margin-left:70px;text-align:center;" >建议比例(16:9)</div>
					<!-- <img id="imgShow" class="imgShow" style="margin-left: 40px;cursor:pointer;background-color:#434343" src="images/add.png" /> -->
					<input type="file" id="up_img" name="uploadFile" style="display: none;"/>
					<input type="hidden" id="idLabel" name="id" />
					<input type="hidden" id="imgUuidLabel" name="imgUuid">
					<input type="hidden" id="operType" name="operType">
					<input type="hidden" id="typeLabel" name="type">
					<input type="hidden" id="userIdLabel" name="userId">
				</div>
				<div id="userNameDiv" class="fitem">
					<label>跳转用户:</label>
					<input id="userNameLabel" name="userName" style="width: 200px" class="easyui-textbox" readonly="readonly" />
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" id="selectUserBtn">选择用户</a>
				</div>
				<div id="contentDiv" class="fitem">
					<label>内容:</label> <textarea id="contentHtml" name="params"></textarea>
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
		
	</div>
	<script type="text/javascript" src="js/stream/js/stream-v1.js"></script>
	<script type="text/javascript" src="js/stream/js/stream-upload-util.js"></script>
	<script type="text/javascript">
	 	  var index;
		  var stream = singleCommonUpload('advert',function(file){
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
			var src = $('#imgShow').attr('src');
			if (src == '') {
		        $.messager.alert('错误信息', '请上传图片!', 'error');
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
