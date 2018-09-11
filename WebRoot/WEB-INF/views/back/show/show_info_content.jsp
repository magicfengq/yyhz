<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<form id="fm" name="fm" method="post">
<div class="fitem">
	<label>内容:</label>
	<input id="contentLabel" name="content" style="width: 280px;height: 100px;" class="easyui-textbox" readonly="readonly" data-options="multiline:true" value="${showInfo.content }"/>
</div>
</form>