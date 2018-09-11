<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>系统角色</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
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

.fitem label {
	display: inline-block;
	width: 40px;
}

.fitem input {
	width: 160px;
}
</style>

<script type="text/javascript">
	
    function formatAct(value, row){
    	var result="";
    	
    	if (row.systemMenuActs.length>0) {
	    	for(var o in row.systemMenuActs){
	    		if (row.systemMenuActs[o].isCheck =="1") {
					result+="<input id='"+row.id+"_"+row.systemMenuActs[o].id+"' type='checkbox' value='' style='vertical-align: middle;' onclick=\"clickMenuAct('"+row.id+"_"+row.systemMenuActs[o].id+"')\" checked='checked'/><span>"+row.systemMenuActs[o].actName+"</span>";
				} else {
					result+="<input id='"+row.id+"_"+row.systemMenuActs[o].id+"' type='checkbox' value='' style='vertical-align: middle;' onclick=\"clickMenuAct('"+row.id+"_"+row.systemMenuActs[o].id+"')\"/><span>"+row.systemMenuActs[o].actName+"</span>";
				}
	    	}
		}
    	return result;
    }
    function formatName(value, row){
    var result="";
    	if (row.isCheck=="1") {
			result="<input id='"+row.id+"' pid='"+row.pid+"' type='checkbox' value='' onclick=\"clickMenu('"+row.id+"')\" checked='checked'/>";
		} else {
			result="<input id='"+row.id+"' pid='"+row.pid+"' type='checkbox' value='' onclick=\"clickMenu('"+row.id+"')\"/>";
		}
    	result+=""+row.name;
    	
    	return result;
    }
    function clickMenu(menuId){
    	var isCheck=$("input[type='checkbox'][id='"+menuId+"']").is(":checked");
    	if (isCheck) {
    		checkMenuItem(menuId,true);
			checkMenuParent(menuId,true);
		}else{
			checkMenuItem(menuId,false);
			//checkMenuParent(menuId,false);
		}
    	
    }
    function clickMenuAct(menuActId){
    	var menuId=menuActId.substr(0,32);
    	var isCheck=$("input[type='checkbox'][id='"+menuActId+"']").is(":checked");
    	if (isCheck) {
	    	$("input[type='checkbox'][id='"+menuId+"']").prop("checked",true);
	    	checkMenuParent(menuId,true);
    	}
    }
    function checkMenuParent(menuId,checked){
    	var pid=$("input[type='checkbox'][id='"+menuId+"']").attr("pid");
    	$("input[type='checkbox'][id='"+menuId+"']").prop("checked",checked);
    	if (pid!=0) {
			checkMenuParent(pid,checked);
		}
    }
    var i=0;
    function checkMenuItem(menuId,checked){
    	var menus=$("input[type='checkbox'][pid='"+menuId+"']");
    	$("input[type='checkbox'][id='"+menuId+"']").prop("checked",checked);
    	$("input[type='checkbox'][id^='"+menuId+"_']").prop("checked",checked);
    	if (menus.length>0) {
    	menus.each(function(){
	       checkMenuItem(this.id,checked);
	     });
		}
    }
    function doSave(){ 
    var index =layer.load('操作中...请等待！', 0);
	    var idList = ""; 
	     $("input:checked").each(function(){
	        var id = $(this).attr("id");
	            idList += id+',';
	     });
	     
	    $.ajax({  
	        type : 'post',  
	        url : 'system/systemRoleAuthorityAjaxSave.do',  
	        data: {
	        	ids:idList,
	        	roleId:$("#roleId").combobox("getValue")
	        },  
	        dataType : 'json',  
	        //同步  
	        async : true,  
	        success : function(result) {  
				layer.close(index);
				if (result.success=="true") {
					$('#tdg').treegrid('reload'); // reload the user data
				} else {
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
	        } 
	    }); 
	}
</script>
</head>

<body>
	<form action="">
		<table id="tdg" title="角色授权" class="easyui-treegrid"
			style="width:98%;height:430px" data-options="" toolbar="#toolbar">
		</table>
	</form>
	<div id="toolbar">
		<div style="margin-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-save" plain="true" onclick="doSave()">保存</a>
		</div>
		<div>
			角色名称: <input id="roleId" class="easyui-combobox" name="roleId"
				data-options="
                    url:'system/systemRoleAjaxAll.do',
                    method:'get',
                    valueField:'id',
                    textField:'name',
                    panelHeight:'auto',
                    editable:false,
                    onChange:function(newValue,oldValue){  
				        $('#tdg').treegrid({
			                url: 'system/systemMenuAjaxAllByRole.do?roleId='+newValue,
							method: 'post',
							rownumbers: true,
							idField: 'id',
							treeField: 'name',
							checkbox:true,
							columns:[[
								{field:'name',formatter:formatName,width:260},
								{field:'id',formatter:formatAct,width:500}
							]]
			            	}
			         	);  
			     	} 
            ">
		</div>
	</div>
</body>
</html>
