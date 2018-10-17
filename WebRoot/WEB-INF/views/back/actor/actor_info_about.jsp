<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div style="margin:20px 0 10px 0;"></div>
<div id="tt" class="easyui-tabs" style="width:97%;height:97%;">
    <div title="Ta的关注" style="padding:10px;width:100%;height:100%;">
        <table id="collectDg" class="easyui-datagrid" style="width:99%;height:100%"
			data-options="iconCls:'icon-save',url:'system/actorCollectAjaxPage.do?creater=${id }',
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'actorInfo.mobile',align:'center',sortable:false,formatter:formatMobile" style="width: 15%;">账户</th>
					<th data-options="field:'actorInfo.name',align:'center',sortable:false,formatter:formatName" style="width: 15%;">艺名</th>
					<th data-options="field:'urlPath',align:'center',sortable:false,formatter:formatImage" style="width: 15%;">头像</th>
					<th data-options="field:'createTime',align:'center',sortable:false" style="width: 20%;">关注时间</th>
				</tr>
			</thead>
		</table>
    </div>
    <div title="Ta的发布" style="padding:10px;width:100%;height:100%;">
         <table id="announceDg" class="easyui-datagrid" style="width:99%;height:100%"
			data-options="iconCls:'icon-save',
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'title',align:'center',sortable:false" style="width: 40%;">通告主题</th>
					<th data-options="field:'readCount',align:'center',sortable:false" style="width: 10%;">浏览量</th>
					<th data-options="field:'city',align:'center',sortable:false" style="width: 15%;">活动城市</th>
					<th data-options="field:'type',align:'center',sortable:false,formatter:formatAnnounceType" style="width: 15%;">通告类型</th>
					<th data-options="field:'createTime',align:'center',sortable:false" style="width: 20%;">发布时间</th>
				</tr>
			</thead>
		</table>
    </div>
    <div title="Ta的报名" style="padding:10px">
        <table id="enrollDg" class="easyui-datagrid" style="width:99%;height:100%"
			data-options="iconCls:'icon-save',
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'title',align:'center',sortable:false" style="width: 50%;">通告主题</th>
					<th data-options="field:'type',align:'center',sortable:false,formatter:formatAnnounceType" style="width: 20%;">通告类型</th>
					<th data-options="field:'createTime',align:'center',sortable:false" style="width: 20%;">报名时间</th>
					<th data-options="field:'checkStatus',align:'center',sortable:false,formatter:formatCheckStatus" style="width: 10%;">审核状态</th>
				</tr>
			</thead>
		</table>
    </div>
    <div title="Ta的评论" style="padding:10px">
        <table id="commentDg" class="easyui-datagrid" style="width:99%;height:100%"
			data-options="iconCls:'icon-save',
			rownumbers:true, pagination:true, singleSelect:true, 
			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'actorInfo.mobile',align:'center',sortable:false,formatter:formatMobile" style="width: 15%;">账号</th>
					<th data-options="field:'actorInfo.name',align:'center',sortable:false,formatter:formatName" style="width: 15%;">艺名</th>
					<th data-options="field:'urlPath',align:'center',sortable:false,formatter:formatImage" style="width: 10%;">头像</th>
					<th data-options="field:'createTime',align:'center',sortable:false" style="width: 20%;">评论时间</th>
					<th data-options="field:'score',align:'center',sortable:false" style="width: 5%;">评分</th>
					<th data-options="field:'content',align:'center',sortable:false" style="width: 35%;">内容</th>
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
				var options = $('#announceDg').datagrid('options');
				if(!options.url){
					options.url = 'back/announceInfoAjaxPage.do?creater=${id}';
					$('#announceDg').datagrid(options);
				}				
			}else if(index == 2){
				var options = $('#enrollDg').datagrid('options');
				if(!options.url){
					options.url = 'system/annouceEnrollAjaxPageForUser.do?actorId=${id}';
					$('#enrollDg').datagrid(options);
				}
			}else if(index == 3){
				var options = $('#commentDg').datagrid('options');
				if(!options.url){
					options.url = 'system/actorCommentAjaxPageForUser.do?creater=${id}';
					$('#commentDg').datagrid(options);
				}
			}
		  }
		});
	});
	function formatMobile(value,row){
		if(row.actorInfo){
			return row.actorInfo.mobile;	
		}
		return '';
	}
	function formatName(value,row){
		if(row.actorInfo){
			return row.actorInfo.name;	
		}
		return '';
	}
	function formatImage(value,row){
		if(row.actorInfo && row.actorInfo.systemPictureInfo){
			var url = 'downFileResult.do?urlPath='+row.actorInfo.systemPictureInfo.urlPath;
			return "<img src="+ url +" style=\"height:50px;width:50px;\"/>";	
		}
	}
	function formatAnnounceType(value,row){
		if(row.type == 1){
			return '艺人';
		}else if(row.type == 2){
			return '租借';
		}else if(row.type == 3){
			return '策划/创意';
		}else if(row.type == 4){
			return '婚礼/派对';
		}
		return '';
	}
	function formatCheckStatus(value,row){
		if(row.checkStatus == 0){
			return '待审核';
		}else if(row.checkStatus == 1){
			return '已通过';
		}else if(row.checkStatus == 2){
			return '已拒绝';
		}
		return '';
	}
</script>