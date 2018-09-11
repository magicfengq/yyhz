<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<form id="fm" name="fm" method="post">
<div id="showImage" class="showImage" style="width:100px;height:100px;border:1px solid;margin-left:32%;text-align:center;">
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
	<label>艺名/公司简称:</label>
	<input id="nameLabel" name="name" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${actorInfo.name }"/>
</div>
<div class="fitem">
	<label>认证信息:</label>
	<input id="authenticateLevelNameLabel" name="authenticateLevelName" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${actorInfo.authenticateLevelName }"/>
</div>
<div class="fitem">
	<label>发布类型:</label>
	<input id="publicTypeLabel" name="publicType" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${showInfo.publicTypeName }"/>
</div>
<div class="fitem">
	<label>位置:</label>
	<input id="cityLabel" name="city" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${showInfo.city }"/>
</div>
<script type="text/javascript">
	$(function(){
		fancybox();
	});
</script>