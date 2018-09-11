<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<form id="fm_1" name="fm_1" method="post">
	<div class="fitem_1">
		<label>身高:</label>
		<input id="heightLabel" name="height" style="width: 100px" class="easyui-textbox" readonly="readonly" value="${actorInfo.height }"/>
		
		<label>体重:</label>
		<input id="weightLabel" name="weight" style="width: 100px" class="easyui-textbox" readonly="readonly" value="${actorInfo.weight }"/>
	</div>
	<div class="fitem_1">
		<label>三围:</label>
		<input id="sizeLabel" name="size" style="width: 100px" class="easyui-textbox" readonly="readonly" value="${actorInfo.size }"/>
		
		<label>鞋码:</label>
		<input id="shoesSizeLabel" name="shoesSize" style="width: 100px" class="easyui-textbox" readonly="readonly" value="${actorInfo.shoesSize }"/>
	</div>
</form>