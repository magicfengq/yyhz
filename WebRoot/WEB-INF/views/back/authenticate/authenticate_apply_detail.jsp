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

<title>认证申请详情</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<link href="css/fa/font-awesome.min.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-easyui-1.5.1/extension/jquery.portal.js"></script>
<link rel="stylesheet" type="text/css" href="resource/website/css/jquery.fancybox.min.css"/>
<script type="text/javascript" src="resource/website/js/jquery.fancybox.min.js"></script>
<script type="text/javascript" src="resource/website/js/fancybox-i18n-zh.js"></script>
<style type="text/css">
       .demo-rtl .portal-column-left{
           padding-left: 10px;
           padding-right: 10px;
       }
       .demo-rtl .portal-column-right{
           padding-left:10px;
           padding-right: 0;
       }
       #fm,#fm_1 {
		margin: 0;
		padding: 10px 5px;
	}
	
	.ftitle {
		font-size: 12px;
		font-weight: bold;
		padding: 5px 0;
		margin-bottom: 10px;
	}
	
	.fitem,.fitem_1 {
		margin-bottom: 5px;
	}
	
	.fitem label {
		display: inline-block;
		width: 86px;
	}
	
	.fitem_1 label {
		display: inline-block;
		width: 45px;
	}
	
	.fitem input {
		width: 280px;
	}
	
	.fitem h4>span {
	    display: inline-block;
	    font-size: 12px;
	    background: #ff6600;
	    color: #fff;
	    padding: 1px 5px;
	    border-radius: 4px;
	    margin-right: 5px;
	}
</style>
</head>
<body>
    <div id="pp" style="width:100%;position:relative">
        <div style="width:30%;"></div>
        <div style="width:70%;"></div>
    </div>
    <div style="text-align: center;margin: 10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="showPassDialog()" style="width:90px">通过</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="showRefuseDialog()" style="width:90px">拒绝</a>
	</div>
	<div id="passDlg" class="easyui-dialog"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="padding:10px 20px;" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">请确认是否通过？</div>
		<form id="fm" name="fm" method="post" action="system/advertAjaxSave.do">
			<!--<div class="fitem">
				<label><font color="red">*</font>认证级别:</label>
				<select id="userCurrentLevelLabel" name="userCurrentLevel" class="easyui-combobox" style="width:100px;" 
							data-options="panelHeight:'auto',editable:false">
							<option value="1" selected="selected">实名认证</option>
							 <option value="2">资历认证</option>
						</select>
			</div> -->
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="doPass()" style="width:90px">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#passDlg').dialog('close')" style="width:90px">取消</a>
	</div>
	<div id="refuseDlg" class="easyui-dialog"
		data-options="iconCls:'icon-save',resizable:true,modal:true"
		style="padding:10px 20px;" closed="true"
		buttons="#refuseDlg-buttons">
		<form id="fm" name="fm" method="post" action="system/advertAjaxSave.do">
			<div class="fitem">
				<label><font color="red">*</font>拒绝理由:</label>
				<input id="refuseContentLabel" name="refuseContent" class="easyui-textbox" multiline="true" value="" style="width:360px;height:120px"/>		
			</div>
		</form>
	</div>
	<div id="refuseDlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="doRefuse()" style="width:90px">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#refuseDlg').dialog('close')" style="width:90px">取消</a>
	</div>
    <script type="text/javascript">
        var panels = [
            {id:'p1',title:'基本信息',height:'auto',href:'system/actorInfoBasic.do?id=${info.actorId}'},
            {id:'p2',title:'认证信息',height:'auto',href:'system/authenticateInfo.do?id=${info.id}'},
            {id:'p3',title:'证件信息',height:'auto',href:'system/credentialsInfo.do?id=${info.id}'}
        ];
        function getPanelOptions(id){
            for(var i=0; i<panels.length; i++){
                if (panels[i].id == id){
                    return panels[i];
                }
            }
            return undefined;
        }
        function addPanels(portalState){
            var columns = portalState.split(':');
            for(var columnIndex=0; columnIndex<columns.length; columnIndex++){
                var cc = columns[columnIndex].split(',');
                for(var j=0; j<cc.length; j++){
                    var options = getPanelOptions(cc[j]);
                    if (options){
                        var p = $('<div/>').attr('id',options.id).appendTo('body');
                        p.panel(options);
                        $('#pp').portal('add',{
                            panel:p,
                            columnIndex:columnIndex
                        });
                    }
                }
            }
        }
        
        $(function(){
            $('#pp').portal({
            	width:$('body').width()*0.99
            });
            addPanels('p1:p2,p3');
            $('#pp').portal('resize');
        });
        
        function showPassDialog(){
        	$('#passDlg').dialog('open').dialog('setTitle', '审核通过');
        } 
        
        function showRefuseDialog(){
        	$('#refuseDlg').dialog('open').dialog('setTitle', '审核拒绝');
        }
        
        function doPass(){
        	var checkStatus = '${info.checkStatus}';
        	if(checkStatus == 0){
        		pass();
        	}else if(checkStatus == 1){
        		$.messager.confirm('提示','当前已是审核通过状态,请问要重新审核为通过状态吗?', function(r){
        			if (r) pass();
        		});
        		return false;        		
        	}else if(checkStatus == 2){
        		 $.messager.confirm('提示', '当前已是拒绝状态,请问要重新审核为通过状态吗?', function(r){
                     if (r) pass();
                 });
        	}
        }
        
        function pass(){
        	$.ajax({
    			url:"system/authenticateAuditPass.do",
    			data:{id:'${info.id}',actorId:'${info.actorId}'},		
    			type:'POST',
    			dataType:'json',
    			success:function(data){
    				if(data.success){
    					$('#passDlg').dialog('close');
    					$.messager.alert('提示',data.msg);
    				}else{
    					$.messager.alert('提示',data.msg,'error');
    				}
    			}
    		});
        }
        
        function doRefuse(){
        	var checkStatus = '${info.checkStatus}';
        	if(checkStatus == 0){
        		refuse();
        	}else if(checkStatus == 1){
        		$.messager.confirm('提示', '当前已是通过状态,请问要重新审核为拒绝状态吗?', function(r){
                    if (r) refuse();
                });
        		
        	}else if(checkStatus == 2){
        		$.messager.alert('提示','当前已是审核拒绝状态!');
        		return false;
        	}
        }
        
        function refuse(){
        	var content = $('#refuseContentLabel').val();
        	if($.trim(content) == ''){
        		$.messager.alert('提示','请填写拒绝理由!','error');
        		return false;
        	}
        	$.ajax({
    			url:"system/authenticateAuditRefuse.do",
    			data:{id:'${info.id}',actorId:'${info.actorId}',refuseContent:$('#refuseContentLabel').val()},	
    			type:'POST',
    			dataType:'json',
    			success:function(data){
    				if(data.success){
    					$('#refuseDlg').dialog('close');
    					$.messager.alert('提示',data.msg);
    				}else{
    					$.messager.alert('提示',data.msg,'error');
    				}
    			}
    		});
        }
    </script>
</body>
</html>
