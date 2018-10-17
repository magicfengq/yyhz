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
<title>租借详情</title>
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
	<div data-options="region:'west'" style="width:350px; padding: 10px">
		<form id="fm" name="fm" method="post" action="back/cardInfoAjaxSave.do">
			<div class="ftitle">基本信息</div>
			<div id="showImage" class="showImage" style="width:100px;height:100px;border:1px solid;margin-left:32%;cursor:pointer;text-align:center;">
				<a class="fancyboxCls" data-fancybox="user" title="${actorInfo.name }" href="downFileResult.do?urlPath=${actorInfo.systemPictureInfo.urlPath }"><img id="imgShow" class="imgShow" src="downFileResult.do?urlPath=${actorInfo.systemPictureInfo.urlPath }" style="width:100%;height:100%;"/></a>
			</div>
			<div class="fitem" style="height: 40px;">
				<label>Ta的评分:</label>
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
			        		<h4 style="display: inline-block;"><span>暂无评分</span></h4>
			        	</c:otherwise>
			        </c:choose>
				</span>
			</div>
			<div class="fitem">
				<label>名称:</label>
				<input id="cardNameLabel" name="cardName" style="width: 200px" class="easyui-textbox" value="${cardDetail.cardName }" data-options="required:true,validType:'length[1,100]'"/>
			</div>
			<div class="fitem">
				<label>用途:</label>
				<input id="detailRoleLabel" name="detailRole" style="width: 200px" class="easyui-textbox" value="${cardDetail.detailRole}" }/>
			</div>
			<div class="fitem">
				<label>尺寸:</label>
				<input id="sizeLabel" name="size" style="width: 200px" class="easyui-textbox" value="${cardDetail.size}"/>
			</div>
			<div class="fitem">
				<label>所在地:</label>
				<input id="cityLabel" name="city" style="width: 200px" class="easyui-textbox" value="${cardDetail.city }"/>
			</div>
			<div class="fitem">
				<label>活动范围:</label>
				<input id="actCitiesLabel" name="actCities" style="width: 200px" class="easyui-textbox" value="${cardDetail.actCities }"/>
			</div>
			<div class="fitem">
				<label>所属机构:</label>
				<input id="institutionLabel" name="institution" style="width: 200px" class="easyui-textbox" value="${cardDetail.institution }"/>
			</div>
			<div class="fitem">
				<label>场租:</label>
				<input id="priceLabel" name="price" style="width: 200px" class="easyui-textbox" value="${cardDetail.price }"/>
			</div>
			<div class="fitem">
				<label>装备说明:</label>
				<textarea id="detailsLabel" name="details" style="width: 200px" class="easyui-textbox">${cardDetail.details }</textarea>
			</div>
			<input type="hidden" name="id" value="${cardDetail.id }">
			<input type="hidden" name="type" value="${cardDetail.type }">
			<input type="hidden" name="publicType" value="${cardDetail.publicType }">
			<input type="hidden" name="imgUuid" value="${cardDetail.imgUuid }">
			<input type="hidden" name="mutiImgUuid" value="${cardDetail.mutiImgUuid }">
			<input type="hidden" name="creater" value="${cardDetail.creater }">
			<input type="hidden" name="createTime" value="${cardDetail.createTime }">
			<input type="hidden" name="status" value="${cardDetail.status }">
		</form>
		
		<div id="toolbar" style="text-align: center;"> 
			<a href="javaScript:void(0);" onclick="save()" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		</div>
	</div>
	<div data-options="region:'center'" style="padding: 10px">
		<div class="ftitle">卡片详情</div>
		<div id="photos" class="fitem">
		</div>
	</div>
</div>
<script type="text/javascript">
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
		url:"system/cardPictureAjaxAll.do?cardId=${cardDetail.id }",
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
