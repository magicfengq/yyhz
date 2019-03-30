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

<title>秀一秀详情</title>

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
</head>
<body>

	<div class="easyui-panel" title="举报信息" style="width:99%;">
		<div class="ftitle">举报信息</div>
			<div class="fitem">
				<label>举报人:</label>
				<input id="reportCreaterLabel" name="reportCreater" style="width: 200px" class="easyui-textbox" value="${reportDetail.creater }" data-options="required:true,validType:'length[1,100]'"/>
			
				<label>举报原因:</label>
				<input id="reportReasonLabel" name="reportReason" style="width: 200px" class="easyui-textbox" value="${reportDetail.reason }" data-options="required:true,validType:'length[1,100]'"/>
			
				<label>举报时间:</label>
				<input id="reportCreateTimeLabel" name="reportCreateTime" style="width: 200px" class="easyui-textbox" value="${reportDetail.createTime }" data-options="required:true,validType:'length[1,100]'"/>
				<c:if test="${reportDetail.status eq '0'}">
				<a href="javaScript:void(0);" onclick="passReport()" class="easyui-linkbutton" iconCls="icon-save">举报通过</a>
				<a href="javaScript:void(0);" onclick="refuseReport()" class="easyui-linkbutton" iconCls="icon-save">举报拒绝</a>
				</c:if>
				<a href="javaScript:void(0);" onclick="back()" class="easyui-linkbutton" iconCls="icon-save">返回</a>
			</div>
	</div>
	<div class="easyui-panel" title="图片或视频" style="width:99%;height:60%;">
		<c:choose>
			<c:when test="${showInfo.type eq '0' }">
				<div id="photos" class="fitem">
					<c:forEach var="pic" items="${picList}">
						<a class="fancyboxCls" data-fancybox="showPic" href="downFileResult.do?urlPath=${pic.systemPictureInfo.urlPath }">
							<img style="padding: 10px;" src='downFileResult.do?urlPath=${pic.systemPictureInfo.urlPath }' width="150" height="150"/>
						</a>
					</c:forEach>
				</div>
			</c:when>
			<c:when test="${showInfo.type eq '1' }">
				<div id="photos" class="fitem" style="width: 100%;height:95%;">
					<video style="width: 100%;height:100%;" src="${showInfo.systemVideoInfo.urlPath }" controls="controls"></video>
				</div>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	</div>
	
    <div id="pp" style="width:100%;position:relative">
        <div style="width:30%;"></div>
        <div style="width:70%;height: 50%;"></div>
    </div>
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
    <script type="text/javascript">
    var basePath = "<%=basePath%>";
        var panels = [
            {id:'p1',title:'发布人',height:'auto',href:'system/showInfoBasic.do?id=${id}'},
            {id:'p2',title:'发布内容',height:'auto',href:'system/showInfoContent.do?id=${id}'},
            {id:'p4',title:'评论与点赞',height:'90%',href:'system/showInfoAbout.do?id=${id}'}
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
            //addPanels('p1,p2:p3,p4');
            addPanels('p1,p2:p4');
            $('#pp').portal('resize');
            
            fancybox();
        });
        function passReport() {
    		$.messager.confirm('提示', '通过举报后，内容将被删除?', function(r) {
    			if (r) {
    				$.post('back/passReport.do', {
    					id : '${reportDetail.id}'
    				}, function(result) {
    					if (result.success) {
    						/* window.parent.refreshTab('举报列表');
    						window.parent.delTabPanel('举报详情'); */
    						back();
    					} else {
    						$.messager.show({ // show error message
    							title : '提示',
    							msg : result.msg
    						});
    					}
    				}, 'json');
    			}
    		});
    	}
    	function refuseReport() {
    		
    		$.messager.confirm('提示', '忽略该举报吗', function(r) {
    			if (r) {
    				$.post('back/refuseReport.do', {
    					id : '${reportDetail.id}'
    				}, function(result) {
    					if (result.success) {
    						/* window.parent.refreshTab('举报列表');
    						window.parent.delTabPanel('举报详情'); */
    						back();
    					} else {
    						$.messager.show({ // show error message
    							title : '提示',
    							msg : result.msg
    						});
    					}
    				}, 'json');
    			}
    		});
    	}
    	function back() {
    		
    		location.href = basePath +'back/contentReportInfoList.do';
    	}
    </script>
</body>
</html>
