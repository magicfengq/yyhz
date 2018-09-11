<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div style="margin:20px 0 10px 0;"></div>
<div id="tt" class="easyui-tabs" style="width:97%;height:97%;">
    <div title="评论" style="padding:10px;width:100%;height:100%;">
        <table id="commentDg" class="easyui-datagrid" style="width:99%;height:100%"
			data-options="iconCls:'icon-save',url:'system/showCommentAjaxPage.do?showId=${id }',
			rownumbers:true, pagination:true, singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'actorInfo.mobile',align:'center',sortable:false,formatter:formatMobile" style="width: 25%;">评论账号</th>
					<th data-options="field:'urlPath',align:'center',sortable:false,formatter:formatImage" style="width: 15%;">头像</th>
					<th data-options="field:'createTime',align:'center',sortable:false" style="width: 20%;">发布时间</th>
					<th data-options="field:'content',align:'center',sortable:false" style="width: 43%;">内容</th>
				</tr>
			</thead>
		</table>
    </div>
    
    <div title="点赞" style="padding:10px;width:100%;height:100%;">
         <table id="praiseDg" class="easyui-datagrid" style="width:99%;height:100%"
			data-options="iconCls:'icon-save',
			rownumbers:true, pagination:true, singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'actorInfo.mobile',align:'center',sortable:false,formatter:formatMobile" style="width: 25%;">点赞账号</th>
					<th data-options="field:'urlPath',align:'center',sortable:false,formatter:formatImage" style="width: 15%;">头像</th>
					<th data-options="field:'createTime',align:'center',sortable:false" style="width: 20%;">点赞时间</th>
				</tr>
			</thead>
		</table>
    </div>
    
</div>
<script type="text/javascript">
	$(function(){
		$('#tt').tabs({
		  onSelect: function(title,index){
			if(index == 1){
				var options = $('#praiseDg').datagrid('options');
				if(!options.url){
					options.url = 'system/showPraiseAjaxPage.do?showId=${id}';
					$('#praiseDg').datagrid(options);
				}				
			}
		  }
		});
	});
	function formatMobile(value,row){
		return row.actorInfo.mobile;
	}
	function formatImage(value,row){
		if(row.actorInfo.systemPictureInfo){
			var url = 'downFileResult.do?urlPath='+row.actorInfo.systemPictureInfo.urlPath;
			return "<img src="+ url +" style=\"height:50px;width:50px;\"/>";	
		}
	}
</script>