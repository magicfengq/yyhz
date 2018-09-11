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

<title>场地列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script type="text/javascript" src="js/system/keditor.js"></script>
<script type="text/javascript" src="js/stream/js/stream-v1.js"></script>
<script type="text/javascript" src="js/stream/js/stream-upload-util.js"></script>
<script src="http://webapi.amap.com/maps?v=1.3&key=4c40dde05cb0b945706190749055490d&plugin=AMap.Geocoder"></script>
<link rel="stylesheet" type="text/css" href="resource/website/css/jquery.fancybox.min.css"/>
<script type="text/javascript" src="resource/website/js/jquery.fancybox.min.js"></script>
<script type="text/javascript" src="resource/website/js/fancybox-i18n-zh.js"></script>

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
	display: inline-block;
    width: 50%;
    float: left;
}
.fitem-row {
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
			data-options="url:'back/siteListAjaxPage.do?type=0', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar',onLoadSuccess:function(data){fancybox();}">
			<thead>
				<tr>
					<th data-options="field:'siteName',align:'center',sortable:true" style="width: 25%;">场地名</th>
					<th data-options="field:'address',align:'center',sortable:false" style="width: 40%;">地址</th>
					<th data-options="field:'logo',align:'center',sortable:false,formatter:formatImg" style="width: 10%;">logo</th>
					<th data-options="field:'phone',align:'center',sortable:false" style="width: 20%;">电话</th>
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true" id="siteInfoBtn">评论管理</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBtn">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editBtn">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteBtn">删除</a>	
		</div>
		<div id="dlg2" class="easyui-dialog"
			data-options="iconCls:'icon-tip',resizable:true,modal:true"
			style="width:100%;height:100%;padding:10px 20px;top:10;" closed="true">
			<table id="dg2" class="easyui-datagrid" style="width:100%;height:100%"
				data-options=" iconCls:'icon-save', 
				rownumbers:true, pagination:true, singleSelect:true, 
				toolbar:'#toolbar2'">
				<thead>
					<tr>
						<th data-options="field:'createrMobile',align:'center',sortable:true" style="width: 15%;">发送人账号</th>
						<th data-options="field:'createrName',align:'center',sortable:true" style="width: 15%;">艺名</th>
						<th data-options="field:'headimg',align:'center',sortable:false,formatter:formatImg" style="width: 15%;">头像</th>
						<th data-options="field:'createTime',align:'center',sortable:true" style="width: 15%;">发布时间</th>
						<th data-options="field:'content',align:'center',sortable:false" style="width: 45%;">内容</th>
					</tr>
				</thead>
			</table>
		</div>
			<div id="toolbar2">
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteSBtn">删除</a>	
			</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:800px;height:100%;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">场地详情</div>
			<form id="fm" name="fm" method="post" action="back/siteListAjaxSave.do">
				<div class="fitem">
					<div class="fitem-row">
						<label><font color="red">*</font>场地名:</label>
						<input id="titleLabel" name="siteName" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
					</div>
					<div class="fitem-row">
						<label><font color="red">*</font>电话:</label>
						<input id="titleLabel" name="phone" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
					</div>
					<div class="fitem-row">
						<label><font color="red">*</font>通道详情:</label>
						<input id="titleLabel" name="passageway" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
					</div>
					<div class="fitem-row">
						<label><font color="red">*</font>场地类型:</label>
						<select class="easyui-combobox" id="publicTypeInput" name="publicType" style="width:200px;" 
									data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'name'"></select>
					</div>
					<div class="fitem-row">
						<label><font color="red">*</font>提醒:</label>
						<input id="titleLabel" name="alert" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:'length[1,100]'"/>
					</div>
					<div class="fitem-row">
						<label><font color="red">*</font>地址:</label>
						<input id="addressLabel" name="address" style="width: 200px" class="easyui-textbox" data-options="required:true,validType:['length[1,100]']"/>
					</div>
				</div>
				<div class="fitem">
					<div class="fitem-row">
						<div style="float: left;">图片:</div>
						<div id="showImage" class="showImage" style="width:195px;height:110px;border:1px solid;margin-left:70px;cursor:pointer;text-align:center;" >
							<img id="addImg" class="addImg" src="images/add.png" style="width:50px;height:50px;padding-top: 30px;"/>
							<img id="imgShow" class="imgShow" src="" style="display:none;width:100%;height:100%;"/>
						</div>
						<input type="hidden" id="idLabel" name="id" />
						<input type="hidden" id="imgUuidLabel" name="imgUuid">
						<input type="hidden" id="operType" name="operType">
						<input type="hidden" id="type" name="type" value="0">
						<input type="hidden" id="isclick" name="isclick" value="0">
					</div>
					<div class="fitem-row">
						<label>经度:</label>
						<input id="longitudelabel" name="longitude" style="width: 200px" class="easyui-textbox" readonly="readonly"/>
					</div>
					<div class="fitem-row">
						<label>维度:</label>
						<input id="latitudeLabel" name="latitude" style="width: 200px" class="easyui-textbox" readonly="readonly"/>
					</div>
				</div>
				<div style="clear:both;"></div>
				<div id="editSiteMap" style="height:200px;" class="fitem-row">
				</div>
				<div class="fitem-row">
					<label>内容:</label> <textarea id="contentHtml" name="textArea"></textarea>
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
	<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var editor,editSiteMap;
	KindEditor.ready(function(K) {
		editor = K.create('#contentHtml', {
			width : '730px',
			height:'300px',
			cssPath : '../js/kingeditor/plugins/code/prettify.css',
			//uploadJson : 'jsp/upload_json.jsp',
			uploadJson : basePath + 'keUpload.do?model=site',
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
	function formatImg(value, row) {
		if(row.systemPictureInfo){
			var url = 'downFileResult.do?urlPath=' + row.systemPictureInfo.urlPath;
			var content = '<a class="fancyboxCls" data-fancybox="gallery" title="' + row.title + '" href="'+ url +'">' + 
						  '<img src="'+url+'" style=\"height:45px;width:80px;\"/></a>'
			return content;
		}	
	}
	function searchData(){
		$('#dg').datagrid('load',{
			siteName:$('#siteNameInput').val(),
			address:$('#addressInput').val()
		});
	}
	function uploadAndSave(){
		var operType = $('#operType').val();
		if(operType == ''){
			//不上传图片,直接进行操作
			if(!validation()){
				return false;
			}
			/* save(); */
			checkAddress($('input[name="address"]').val(),function(){
				save();
			});
			return false;	
		}
		if(!validation()){
			return false;
		}
		checkAddress($('input[name="address"]').val(),function(){
			stream.upload();
		});
		/* stream.upload(); */
	}
	function save(){
		issave = true;
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
	var index;
	var stream = singleCommonUpload('site',function(file){
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
	
	var isclick = false;
	$(function() {
		
		//发布类型初始化
		$.ajax({
			url:"system/publicTypeForCombo.do?type=6",
			type:'POST',
			dataType:'json',
			success:function(data){
				 $('#publicTypeInput').combobox('loadData',data);
			}
		});
		
		$('#siteInfoBtn').click(function(){
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg2').dialog('open').dialog('setTitle', '评论管理');
				$('#dg2').datagrid({
					url:'back/siteInfoAjaxPage.do?siteId='+row.id
				});
			}else{
				$.messager.alert('提示', '请选中一条记录', 'warning');
			}
		});
		$('#deleteSBtn').click(function(){
			var row = $('#dg2').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm', '你确定要删除吗?', function(r) {
					if (r) {
						$.post('back/siteInfoAjaxDelete.do', {
							id : row.id
						}, function(result) {
							if (result.success) {
								$('#dg2').datagrid('reload'); // reload the user data
							} else {
								$.messager.show({ // show error message
									title : '提示',
									msg : result.msg
								});
							}
						}, 'json');
					}
				});
			}else{
				$.messager.alert('提示', '请选中一条记录', 'warning');
			}
		});
		  
		$('#editBtn').click(
			function() {
				var row = $('#dg').datagrid('getSelected');
				if (row) {
					doEdit(function(row) {
						var url = 'downFileResult.do?urlPath=' + row.systemPictureInfo.urlPath;
						if (url) {
							$('#addImg').hide();
							$('#imgShow').attr('src', url);
							$('#imgShow').show();
						}
						editor.html(row.textArea);
						//初始化地图
						editSiteMap = new AMap.Map('editSiteMap', {
						    resizeEnable: true,
						    zoom:11
						});
						if(row.longitude&&row.latitude){
			    			marker = new AMap.Marker({
								position: [row.longitude,row.latitude],
								title: row.address,
								map: editSiteMap
							});
			    			editSiteMap.setFitView();
			    		}
			    		
						editSiteMap.on('click', function(e){	
							var overlays = editSiteMap.getAllOverlays();
							if(overlays.length > 0){
								//清除覆盖物
								editSiteMap.remove(overlays);
							}
							var marker = new AMap.Marker({
						        position : e.lnglat,
						        map : editSiteMap
						    });
							var position = marker.getPosition();
							var geocoder = new AMap.Geocoder({});
							geocoder.getAddress(position, function(status, result) {
					            if (status === 'complete' && result.info === 'OK') {
					            	var address = result.regeocode.formattedAddress; //返回地址描述
					            	$('#addressLabel').textbox('setValue',address);	
					            	$("#longitudelabel").textbox("setValue", position.lng);
						    		$("#latitudeLabel").textbox("setValue", position.lat);
					            }
					        });
							isclick = true;
						});
					});
				}else{
					$.messager.alert('提示', '请选中一条记录', 'warning');
				}
			});
			
			$('#addBtn').click(function() {
				doAdd(function() {
					$('#imgShow').attr('src', '');
					$('#addImg').show();
					$('#imgShow').hide();
					var data = $('#publicTypeInput').combobox('getData');
					$('#publicTypeInput').combobox('select',data[0].id);
					editor.html('');
					//初始化地图
					editSiteMap = new AMap.Map('editSiteMap', {
					    resizeEnable: true,
					    zoom:11
					});
					
					editSiteMap.on('click', function(e){	
						var overlays = editSiteMap.getAllOverlays();
						if(overlays.length > 0){
							//清除覆盖物
							editSiteMap.remove(overlays);
						}
						var marker = new AMap.Marker({
					        position : e.lnglat,
					        map : editSiteMap
					    });
						var position = marker.getPosition();
						var geocoder = new AMap.Geocoder({});
						geocoder.getAddress(position, function(status, result) {
				            if (status === 'complete' && result.info === 'OK') {
				            	var address = result.regeocode.formattedAddress; //返回地址描述
				            	$('#addressLabel').textbox('setValue',address);	
				            	$("#longitudelabel").textbox("setValue", position.lng);
					    		$("#latitudeLabel").textbox("setValue", position.lat);
				            }
				        });
						isclick = true;
					});
				});
			});
			$('#deleteBtn').click(function() {
				var row = $('#dg').datagrid('getSelected');
				if (row) {
					doDelete('back/siteListAjaxDelete.do');
				}else{
					$.messager.alert('提示', '请选中一条记录', 'warning');
				}
			});
			
			$('#addressLabel').textbox('textbox').on("input propertychange",debounce(function (event) {
				getLocation($(this).val());		
			}, 1000));
		});
	
	$.extend($.fn.validatebox.defaults.rules, {
		checkAddress: {
	        validator: function(value, param){
	        	//校验地址
	        	getLocation(value);
	        	return true;
	        },
	        message: ''
	    }
	});
	
	
	function getLocation(keyword){
		if(isclick){
			isclick = false;
			return;
		}
		var geocoder = new AMap.Geocoder({});
		geocoder.getLocation(keyword, function(status, result) {
	    	if (status === 'complete' && result.info === 'OK') {
	    		var lat = result.geocodes[0].location.lat;
	    		var lng = result.geocodes[0].location.lng;
	    		var address = result.geocodes[0].formattedAddress;
	    		$("#longitudelabel").textbox("setValue", lng);
	    		$("#latitudeLabel").textbox("setValue", lat);
	    		var overlays = editSiteMap.getAllOverlays(type);
				if(overlays.length > 0){
					//清除覆盖物
					editSiteMap.remove(overlays);
				}
				var marker = new AMap.Marker({
			        position : [lng,lat],
			        title: address,
			        map : editSiteMap
			    });
	    		editSiteMap.setFitView();
	        }
	    });
	}
	function checkAddress(keyword,callback){
		var geocoder = new AMap.Geocoder({});
		geocoder.getLocation(keyword, function(status, result) {
	    	if (status === 'complete' && result.info === 'OK') {
	    		//var lat = result.geocodes[0].location.lat;
	    		//var lng = result.geocodes[0].location.lng;
	    		//var address = result.geocodes[0].formattedAddress;
	    		//$("#longitudelabel").textbox("setValue",lng);
	    		//$("#latitudeLabel").textbox("setValue",lat);
	    		//$("#addressLabel").textbox("setValue",keyword);	    		
	    		callback();
	        }else{
	        	layer.close(index);
	        	$.messager.alert('错误信息', '地址信息错误!', 'error');
	        }
	    });
	}
	function debounce(fn, delay) {
	  var timer = null;
	  return function () {
	    var context = this, args = arguments;
	    clearTimeout(timer);
	    timer = setTimeout(function () {
	      fn.apply(context, args);
	    }, delay);
	  };
	}
	</script>
</body>
</html>
