<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div style="width: 100%;height:100%;">
	<c:choose>
		<c:when test="${showInfo.type eq '0' }">
			<div id="photos" class="fitem">
				<c:forEach var="pic" items="${picList}">
					<img src='downFileResult.do?urlPath=${pic.systemPictureInfo.urlPath }' width="100" height="100"/>
				</c:forEach>
			</div>
		</c:when>
		<c:when test="${showInfo.type eq '1' }">
			<div id="photos" class="fitem" style="width: 100%;height:95%;">
				<video style="width: 100%;height:100%;" src="downFileResult.do?urlPath=${showInfo.systemVideoInfo.urlPath }" controls="controls"></video>
			</div>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
</div>