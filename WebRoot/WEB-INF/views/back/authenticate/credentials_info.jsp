<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div data-options="region:'north'" style="width:100%; height:40%;">
	<div class="ftitle">照片</div>
	<div id="photos" class="fitem">
		<c:forEach items="${resultInfo }" var="info">
			<div style="display: block;float: left;margin-right: 20px;padding: 5px;">
				<a class="fancyboxCls" data-fancybox="showPic" title="${info.value.text }" href="downFileResult.do?urlPath=${info.value.picInfo.urlPath }">
					<img style="width: 160px;height: 90px;" alt="" src="downFileResult.do?urlPath=${info.value.picInfo.urlPath}" >
				</a>
				<p style="text-align: center;">${info.value.text }</p>
			</div>
		</c:forEach>		
	</div>
</div>
<script type="text/javascript">
$(function(){
   fancybox();
});
</script>