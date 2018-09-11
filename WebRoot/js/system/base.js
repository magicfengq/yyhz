	function doAdd(callback) {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		if(callback && typeof(callback)=="function"){
			callback();
		}
	}
	function doEdit(callback) {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('load', row);
			if(callback && typeof(callback)=="function"){
				callback(row);
			}
		}
	}
	
	function doDelete(url) {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '你确定要删除吗?', function(r) {
				if (r) {
					$.post(url, {
						id : row.id
					}, function(result) {
						if (result.success) {
							$('#dg').datagrid('reload'); // reload the user data
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
	}
	
	function doUpdateStatus(url,status) {
		console.log(status);
		var row = $('#dg').datagrid('getSelected');
		var msg="";
		if (row) {
			if (status==0) {
				msg="启用";
				if (row.status==0) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '已启用！'
					});
					return;
				}
			}
			if (status==1) {
				msg="禁用";
				if (row.status==1) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '已禁用！'
					});
					return;
				}
			}
			$.messager.confirm('确认操作', '你确定要'+msg+'吗?', function(r) {
				if (r) {
					$.post(url, {
						id : row.id,
						status : status
					}, function(result) {
						if (result.success) {
							$('#dg').datagrid('reload'); // reload the user data
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
	}
	
	function isimg(src){
		var ext = ['.gif', '.jpg', '.jpeg', '.png'];
		var s = src.toLowerCase();
		var r = false;
		for(var i = 0; i < ext.length; i++){
			if (s.indexOf(ext[i]) > 0){
				r = true;
				break;
			}
		}	
		return r;
	}
	
	function isValid(obj){
		if(obj != null && typeof(obj) != 'undefined'){
			return true;
		}
		return false;
	}
	
	function isValidStr(str){
		if($.trim(str) != '' && str != null && typeof(str) != 'undefined'){
			return true;
		}
		return false;
	}
		
	function doBatchAudit(url,replyType,status,id){	
		var ids='';
		var relationIds='';
		if(isValidStr(id)){
			ids = id;
		}else{
			var data = $('#dg').datagrid('getSelections');
			if(data.length <= 0){
				$.messager.show({
					title : '提示',
					msg : '请选择至少一行记录进行操作！'
				});
				return;
			}
			for(var i=0;i<data.length;i++){
	            ids=ids+data[i].id+',';
	            relationIds=relationIds+data[i].id+',';
	            if(ids!=''){  
	                ids=ids.substring(0, ids.length); 
	            }
	            if(relationIds!=''){
	                relationIds=relationIds.substring(0, relationIds.length); 
	            }
	        }
			ids=ids.substring(0, ids.length-1);
	        relationIds=relationIds.substring(0, relationIds.length-1);
		}                        
        $('#ids').val(ids);
        $('#relationIds').val(relationIds);
		
		if(status == '1'){
			$.messager.confirm('Confirm', '你确定要执行操作吗?', function(r) {
				if(r){
					doAudit(url,status,'');
				}
			});			
		}else{
			$('#dlg_refuse_reply').dialog('open').dialog('setTitle', '推送');
			var data = $('#refuse_pg').propertygrid('getData');
			if(data.rows.length <= 0){			
			    var self_content_row = {
			      name: '自定义内容',
			      value: '<textarea id="replyContent" class="datagrid-editable-input" style="width: 280px;"></textarea>',
			      group: 'reply',
			      width:'100%'
			    };
			    $('#refuse_pg').propertygrid('appendRow', self_content_row);
			    
			    getReplyContent(replyType);
			}
		}						
	}
	
	function getReplyContent(type){
		//获取主用户信息
		var ajaxOptions = {
	     url: 'system/systemCommonReplyAjaxPage.do',
	     data:'type='+type+'&page=1&rows=5',
	     type: 'post',
	     async: false,    
	     dataType: 'json',
	     success: function(data) {
	       if(isValid(data)) {
	         if(data.rows.length > 0){
	        	var rows = data.rows;
	        	$.each(rows,function(index,obj){
	        		var replyRow =  {          		      
          		      group: 'reply',
          		      value:'<input class="easyui-textbox" style="width: 280px;" type="text" name="refuseContent' +(index+1)+ '" value="'+obj.content+'"/>'          		      
          		    };
	        		$('#refuse_pg').propertygrid('appendRow', replyRow);
	        	});	        	
	         }	    	   	    	   
	       }else{
	       	 $.messager.alert('错误信息', '获取用户主信息失败!', 'error');
	       }
	     }
	   };
	   $.ajax(ajaxOptions);
	}
	
	//批量拒绝
	function doBatchRefuse(url,status) {
		//自定义内容与可选内容都不为空
		var replyContent = $.trim($('#replyContent').val());
		if(replyContent == ''){
			if($('input[name^="refuseContent"]').size() > 0){
				$('input[name^="refuseContent"]').each(function(i){
					var contentVal = $(this).val();
					if(i == ($('input[name^="refuseContent"]').size()-1)){
						replyContent += contentVal;
					}else{
						replyContent += contentVal + '<br/>';
					}					 
				});
			}
		}		
		if(status == '2' && replyContent == ''){
			$.messager.show({
					title : '提示',
					msg : '请填写反馈内容！'
				});
				return;
		}
		$.messager.confirm('Confirm', '你确定要执行操作吗?', function(r) {
			if(r){
				doAudit(url,status,replyContent);
			}				
		});		
	}
	
	function doAudit(url,status,replyContent){
		$.post(url, {
			ids : $('#ids').val(),
			status:status,
			relationIds:$('#relationIds').val(),
			replyContent:replyContent
		}, function(result) {
			if (result.success) {
				$('#dg').datagrid('reload'); // reload the user data
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				$('#dlg_refuse_reply').dialog('close');
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				$('#dlg_refuse_reply').dialog('close');
			}			
		}, 'json');
	}