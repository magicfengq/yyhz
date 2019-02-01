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
<title>交友通告详情</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
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
	width: 65px;
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
	<div data-options="region:'west'" style="width:300px;height:100%; padding: 10px">
		<div class="ftitle">发布人</div>
		<div id="showImage" class="showImage" style="float:left;width:60px;height:60px;border:1px solid;">
			<a class="fancyboxCls" data-fancybox="user" title="${actorInfo.name }" href="downFileResult.do?urlPath=${actorInfo.systemPictureInfo.urlPath }"><img id="imgShow" class="imgShow" src="downFileResult.do?urlPath=${actorInfo.systemPictureInfo.urlPath }" style="width:100%;height:100%;"/></a>
		</div>
		<div style="width: 100px;float: left;font-size: 17px;font-weight: bold; margin-left: 10px;">
			${actorInfo.name }
		</div>
		<div style="float:left;">
			<label style="width:100px;margin-left:10px;">评分:</label>
			<span>
				<c:choose>
			        <c:when test="${!empty actorInfo.avgScore}">					            	
			         <h5 style="color: #ff6600;font-size: 12px;display: inline-block;">
			         	<c:forEach begin="1" end="5" varStatus="status">
			         		<fmt:formatNumber type="number" value="${actorInfo.avgScore}" maxFractionDigits="0" pattern="#" var="starcount"/>
			         		<c:choose>
			         			<c:when test="${actorInfo.avgScore % starcount eq 0}">
			          			<c:choose>
			          				<c:when test="${status.count <= actorInfo.avgScore }">
			          					<i class="fa fa-star"></i>
			          				</c:when>
			          				<c:otherwise>
			          					<i class="fa fa-star-o"></i>
			          				</c:otherwise>
			          			</c:choose>            			
			          		</c:when>
			          		<c:otherwise>
			          			<c:set var="fsmod" value="${actorInfo.avgScore % starcount}"/>
			          			<c:choose>
			          				<c:when test="${status.count <= actorInfo.avgScore }">
			          					<i class="fa fa-star"></i>
			          				</c:when>
			          				<c:when test="${status.count > actorInfo.avgScore and (status.count - actorInfo.avgScore) < 1}">
			          					<i class="fa fa-star-half-empty"></i>
			          				</c:when>
			          				<c:otherwise>
			          					<i class="fa fa-star-o"></i>
			          				</c:otherwise>
			          			</c:choose>
			          		</c:otherwise>
			         		</c:choose>            		            		
			         	</c:forEach>
			         	<fmt:formatNumber type="number" value="${actorInfo.avgScore}" maxFractionDigits="1" minFractionDigits="1" pattern="#" var="starcount"/>
			         	<h4 style="display: inline-block;"><span>${starcount}分</span></h4>
			         </h5>
			        </c:when>
		        	<c:otherwise>						            	
		        		<h4 style="display: inline-block;width: 100px;"><span>暂无评分</span></h4>
		        	</c:otherwise>
		        </c:choose>
			</span>
		</div>
		<div style="clear: both;"></div>
		<form id="fm" name="fm" method="post" action="back/announceInfoAjaxSave.do">
			<div class="ftitle">基本信息</div>
			<div class="fitem">
				<label>交友类型:</label>
				<input id="nameLabel" name="name" style="width: 200px" class="easyui-textbox" value="${info.name }" readonly="readonly" data-options="required:true,validType:'length[1,100]'"/>
			</div>
			<div class="fitem">
				<label>活动时间:</label>
				<input id="showTimeLabel" name="showTime" style="width: 200px" class="easyui-datebox" readonly="readonly" value="${info.showTime}"/>
			
				<%-- <input id="showTimeLabel" name="showTime" style="width: 200px" class="easyui-datebox" readonly="readonly" value="<fmt:formatDate value="${info.showTime}" pattern="yyyy-MM-dd"/>"/> --%></div>
			<div class="fitem">
				<label>进场时间:</label>
				<input id="entranceTimeLabel" name="entranceTime" style="width: 200px" class="easyui-datebox" readonly="readonly" value="<fmt:formatDate value="${info.entranceTime}" pattern="yyyy-MM-dd"/>"/>
			</div>
			<div class="fitem">
				<label>活动城市:</label>
				<input id="cityLabel" name="city" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${info.city }"/>
			</div>
			<div class="fitem">
				<label>活动详址:</label>
				<input id="addressLabel" name="address" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${info.address }"/>
			</div>
			<div class="fitem">
				<label>性别要求:</label>
				<select id="sexLabel" name="sex" style="width: 200px" class="easyui-combobox" readonly="readonly" data-options="panelHeight:'auto',editable:false,required:false">
					<option value="1" ${info.sex==1?'selected':''} >男</option>
					<option value="2" ${info.sex==2?'selected':''}>女</option>
				</select>
			</div>
			<div class="fitem">
				<label>酬金:</label>
				<input id="priceLabel" name="price" style="width: 200px" class="easyui-textbox" value="${info.price }"/>
			</div>
			<div class="fitem">
				<label style="vertical-align: top;">通告详情:</label>
				<textarea id="detailLabel" name="detail" style="width: 200px;height:50px;resize:none;" class="easyui-textarea">${info.detail }</textarea>
			</div>
			<input type="hidden" name="id" value="${info.id }">
			<input type="hidden" name="type" value="${info.type }">
			<input type="hidden" name="publicType" value="${info.publicType }">
			<input type="hidden" name="title" value="${info.title }">
			<input type="hidden" name="creater" value="${info.creater }">
			<input type="hidden" name="createTime" value="${info.createTime }">
			<input type="hidden" name="status" value="${info.status }">
		</form>
		
		<div id="toolbar" style="text-align: center;"> 
			<a href="javaScript:void(0);" onclick="save()" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		</div>
	</div>
	<div data-options="region:'center'" style="padding: 10px">
		<div data-options="region:'north'" style="width:100%; height:40%;">
			<div class="ftitle">照片</div>
			<div id="photos" class="fitem">
			</div>
		</div>
		<div data-options="region:'sourth'" style="width:100%; height:60%;">
			<table id="dg" class="easyui-datagrid" style="width:100%;height:100%"
			data-options="url:'system/annouceEnrollAjaxPage.do?announceId=${info.id }', iconCls:'icon-save', 
			rownumbers:true, pagination:true, singleSelect:true,onLoadSuccess:function(){fancybox();}">
			<thead>
				<tr>
					<th data-options="field:'actorName',align:'center',sortable:true" style="width: 20%;">帐号</th>
					<th data-options="field:'logo',align:'center',sortable:false,formatter:formatImg" style="width: 15%;">头像</th>
					<th data-options="field:'realName',align:'center',sortable:false" style="width: 20%;">名字</th>
					<th data-options="field:'createTime',align:'center',sortable:false" style="width: 20%;">发布时间</th>
					<th data-options="field:'checkStatus',align:'center',sortable:false,formatter:formatStatus" style="width: 10%;">状态</th>
					<th data-options="field:'options',align:'center',sortable:true,formatter:formatOptions" style="width: 15%;">操作</th>
				</tr>
			</thead>
		</table>
		</div>
		
	</div>
</div>
<script type="text/javascript">
	function formatStatus(value, row){
		if(value==0){
			return "待审核";
		}else if(value==1){
			return "通过";
		}else if(value==2){
			return "拒绝";
		}
	}
	function formatOptions(value, row){
		return '<a href="javascript:void(0);" onclick="toView(\'' + row.actorId + '\');">查看</a>';
	}
	
	function toView(id){
		window.parent.addTabPanel('用户信息','system/actorInfoDetail.do?id=' +id);
	}
	function formatImg(value, row) {
		if(row.systemPictureInfo){
			
			var url = 'downFileResult.do?urlPath=' + row.systemPictureInfo.urlPath;
			var content = '<a class="fancyboxCls" data-fancybox="actors" href="'+ url +'">' + 
        				  	'<img src="'+url+'" style=\"height:45px;width:80px;\"/>' + 
        				  '</a>';
			return content;
		}	
	}
	var index;
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
			url:"system/announcePictureAjaxAll.do?announceId=${info.id }",
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
	});
</script>
</body>
</html>
