<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<form id="fm_1" name="fm_1" method="post">
	<div class="fitem">
		<label>真实姓名:</label>
		<input id="realNameLabel" name="realName" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${info.realName }"/>
	</div>
	<div class="fitem">
		<label>手机(登录账号):</label>
		<input id="mobileLabel" name="mobile" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${info.mobile }"/>
	</div>
	<div class="fitem">
		<label>证件号码:</label>
		<input id="idcardLabel" name="idcard" style="width: 200px" class="easyui-textbox" readonly="readonly" value="${info.idcard }"/>
	</div>
</form>