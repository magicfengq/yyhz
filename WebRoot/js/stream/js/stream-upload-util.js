function singleCommonUpload(model_name,complete){
	/**
	 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
	 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
	 * 是在ID为i_stream_message_container的页面元素中写日志
	 */
	var config = {
			customered:true,
			browseFileId : "showImage", /** 选择文件的ID, 默认: i_select_files */
			browseFileBtn : "<div></div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
			dragAndDropArea: "showImage", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
			dragAndDropTips: "<div></div>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
			//filesQueueId : "showImage", /** 文件上传容器的ID, 默认: i_stream_files_queue */
			//messagerId : "showImage",
			autoUploading:false,
			extFilters:['.bmp','.jpg','.jpeg','.png','.gif'],
			tokenURL:"/perform/tk",
			uploadURL:"/perform/upload",
			postVarsPerFile:{model: model_name},
			multipleFiles:false,
			simLimit:1,
			maxSize:1048576,
			onSelect: function(list) {
			},
			onFileCountExceed: function(selected, limit) {
				console.log("最多文件数量： " + limit + " 但已选择" + selected + "个");
			},
			onMaxSizeExceed: function(file) {
				$.messager.show({ // show error message
					title : '提示',
					msg : "最大文件大小： " + bytesToSize(file.limitSize) + " 但是" + file.name + " 是：" + bytesToSize(file.size)
				});
			},
			onExtNameMismatch:function(file){
				console.log(file);
			},
			onRepeatedFile: function(file) {
			  console.log("文件： " + file.name
			   + " 大小：" + file.size + "已存在于上传队列中");
			   return false;
			},
			onAddTask: function(file, dat) {
				// 图片回显
				console.log(dat.file);
				var reader = new FileReader();
		        reader.readAsDataURL(dat.file);
		        reader.onload = function(evt) {
	          		var img = $('#imgShow');
	          		img.attr('src', reader.result);
	          		img.attr('alt', file.name);
	          		img.attr('width', '120px');
	          		$('#operType').val('edit');
	            }
		        $('#addImg').hide();
		        $('#imgShow').show();
			},onComplete : function(file) {
			  var msg = eval('(' + file.msg + ')');	
			  file.suffix = file.name.substring(file.name.lastIndexOf('.'),file.name.length);			  
			  file.urlPath = msg.urlPath;
			  var imgSrc = $("#imgShow").attr("src");
			  getImageWidth(imgSrc,function(w,h){
				  file.fwidth = w;
				  file.fheight = h;
				  
			  });
			  console.log("文件： " + file.name + "|" + file.size + " 已上传成功！");
			  complete(file);
			}
	};
	return new Stream(config);
}

function singleIconCommonUpload(model_name,complete){
	/**
	 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
	 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
	 * 是在ID为i_stream_message_container的页面元素中写日志
	 */
	var config = {
			customered:true,
			browseFileId : "iconImgShow", /** 选择文件的ID, 默认: i_select_files */
			browseFileBtn : "<div></div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
			dragAndDropArea: "iconImgShow", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
			dragAndDropTips: "<div></div>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
			autoUploading:false,
			extFilters:['.bmp','.jpg','.jpeg','.png','.gif'],
			tokenURL:"/perform/tk",
			uploadURL:"/perform/upload",
			postVarsPerFile:{model: model_name},
			simLimit:1,
			onSelect: function(list) {
				
			},
			onExtNameMismatch:function(file){
				console.log(file);
			},
			onAddTask: function(file, dat) {
				// 图片回显
				var reader = new FileReader();
		        reader.readAsDataURL(dat.file);
		        reader.onload = function(evt) {
	          		var img = $('#iconImgShow');
	          		img.attr('src', reader.result);
	          		img.attr('alt', file.name);
	          		$('#iconOperType').val('edit');
	            }
			},onComplete : function(file) {
			  var msg = eval('(' + file.msg + ')');
			  file.suffix = file.name.substring(file.name.lastIndexOf('.'),file.name.length);			  
			  file.urlPath = msg.urlPath;
			  var imgSrc = $("#iconImgShow").attr("src");
			  getImageWidth(imgSrc,function(w,h){
				  file.fwidth = w;
				  file.fheight = h;
			  });
			  console.log(file);
			  console.log("文件： " + file.name + "|" + file.size + " 已上传成功！");
			  complete(file);
			}
	};
	return new Stream(config);
}

function mutiFileUpload(model_name,complete,queueComplete){
	/**
	 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
	 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
	 * 是在ID为i_stream_message_container的页面元素中写日志
	 */
	 
		var config = {
			enabled: true, /** 是否启用文件选择，默认是true */
			customered: true,
			multipleFiles: true, /** 是否允许同时选择多个文件，默认是false */	
			autoUploading: false, /** 当选择完文件是否自动上传，默认是true */
			fileFieldName: "FileData", /** 相当于指定<input type="file" name="FileData">，默认是FileData */
			maxSize: 2147483648, /** 当_t.bStreaming = false 时（也就是Flash上传时），2G就是最大的文件上传大小！所以一般需要 */
			simLimit: 20, /** 允许同时选择文件上传的个数（包含已经上传过的） */
			extFilters:['.bmp','.jpg','.jpeg','.png','.gif'],
			browseFileId : "uploadForm", /** 文件选择的Dom Id，如果不指定，默认是i_select_files */
			dragAndDropTips : $('#DropUploadMessage').text().trim(),
			dragAndDropArea: "uploadForm",
			postVarsPerFile:{model: model_name},
			tokenURL:"/perform/tk",
			uploadURL:"/perform/upload",
			onSelect: function(files) {
				for(var i=0; i<files.length; i++) {
					var tmpl = $("#UploadFileModule").text();
					var instance = tmpl.replace(/#FILE_ID#/g, files[i].id)
					.replace(/#FILE_NAME#/g, files[i].name)
					.replace(/#FILE_SIZE#/g, _t.formatBytes(files[i].size));

					$("#uploadForm").append(instance);
					
					$("#"+ files[i].id).click(function(e) {
						e.stopPropagation();
						if ($(this).hasClass('dz-processing')) {
							$(this).remove();	
							delete _t.uploadInfo[$(this).attr('id')];
							for(var i=0; i<_t.waiting.length; i++) {
							    if(_t.waiting[i] == $(this).attr('id')) {
							      _t.waiting.splice(i, 1);
							      break;
							    }
							}
						}
					});
					$('input[type="text"]').click(function(e) {
						e.stopPropagation();						
					});														
				}
			},
			onMaxSizeExceed: function(file) {
				var $module = $("#"+ file.id);
				$module.addClass("dz-error dz-complete");
				$module.find(".dz-error-message span").text("文件[name="+file.name+", size="+file.formatSize+"]超过文件大小限制‵"+file.formatLimitSize+"‵，将不会被上传！");
			},
			onFileCountExceed : function(selected, limit) {
				var $module = $("#"+ file.id);
				$module.addClass("dz-error dz-complete");
				$module.find(".dz-error-message span").text("同时最多上传<strong>"+limit+"</strong>个文件，但是已选择<strong>"+selected+"</strong>个");
			},
			onExtNameMismatch: function(info) {
				var $module = $("#"+ file.id);
				$module.addClass("dz-error dz-complete");
				$module.find(".dz-error-message span").text("<strong>"+info.name+"</strong>文件类型不匹配[<strong>"+info.filters.toString() + "</strong>]");
			},
			onAddTask: function(file, dat) {
				// 图片回显
				if (dat && dat.file && dat.file.type && dat.file.type.indexOf('image') == 0) {
					var $module = $("#"+ file.id);
					$module.addClass('dz-image-preview');
					$module.removeClass('dz-file-preview');
					
					var reader = new FileReader();
		            reader.readAsDataURL(dat.file);
		            reader.onload = function(evt) {
		          		var img = $('#' + file.id).find('.dz-image img');
		          		img.attr('src', reader.result);
		          		img.attr('alt', file.name);
		          		img.attr('width', '173px');
		          		img.attr('height', '100px');
		            }
				}
				
			},
			onUploadProgress: function(file) {
				var $bar = $("#"+file.id).find("div.dz-progress span.dz-upload");
				$bar.css("width", file.percent + "%");
			},
			onStop: function() {
			},
			onCancel: function(file) {
				$("#" + file.id).remove();
			},
			onCancelAll: function(numbers) {
			},
			onComplete: function(file) {				
				var msg = eval('(' + file.msg + ')');
				file.suffix = file.name.substring(file.name.lastIndexOf('.'),file.name.length);			  
				file.urlPath = msg.urlPath;
				var imgSrc =  $("#"+file.id).find('img').attr("src");
				getImageWidth(imgSrc,function(w,h){
					  file.fwidth = w;
					  file.fheight = h;							  
				});
				
				var inputs = '';
				for(var prop in file){
				  var value = file[prop];
				  if(prop == 'id'){
					  continue;
				  }
				  if($('input[name="' + prop + '"]').size() <= 0){
					  inputs += '<input type="hidden" id="'+prop+'" name="'+prop+'" value="'+value+'" />';
				  }else{
					  $('input[name="' + prop + '"]').remove();
					  inputs += '<input type="hidden" id="'+prop+'" name="'+prop+'" value="'+value+'" />';
				  }
			    }  
			    $('#uploadForm').append(inputs);
			    
			    complete(file);			    			    
			},
			onQueueComplete: function(msg) {
				layer.close(index);
				_t.uploadInfo = {};
				$('.dz-edit').each(function(){
					$(this).removeClass('.dz-edit');
					$(this).addClass('.dz-add');
				});
				if(queueComplete && typeof(queueComplete)=="function"){
					queueComplete(msg);
				}
			},
			onUploadError: function(status, msg) {
				$("#i_error_tips > span.text-message").append(msg + ", 状态码:" + status);
			}
		};
		
		var _t = new Stream(config);
		/** 不支持拖拽，隐藏拖拽框 */
		if (!_t.bDraggable) {
			$("#i_stream_dropzone").hide();
		}
		/** Flash最大支持2G */
		if (!_t.bStreaming) {
			_t.config.maxSize = 2147483648;
		}
		
		if (!_t.bDraggable) {
			alert('你的浏览器不支持Dropzone样式的演示特性，换个现代浏览器试试！')
		}
		
		return _t;
}

function getImageWidth(url,callback){
	var img = new Image();
	img.src = url;
	
	// 如果图片被缓存，则直接返回缓存数据
	if(img.complete){
	    callback(img.width, img.height);
	}else{
        // 完全加载完毕的事件
		img.onload = function(){
			callback(img.width, img.height);
		}
	}
}

function singleFileUpload(model_name,complete){
	/**
	 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
	 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
	 * 是在ID为i_stream_message_container的页面元素中写日志
	 */
	var config = {
			customered:true,
			browseFileId : "fileSelect", /** 选择文件的ID, 默认: i_select_files */
			browseFileBtn : "<div></div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
			dragAndDropArea: "fileSelect", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
			dragAndDropTips: "<div></div>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
			//filesQueueId : "showImage", /** 文件上传容器的ID, 默认: i_stream_files_queue */
			//messagerId : "showImage",
			autoUploading:false,
			extFilters:['.apk','.mp3'],
			tokenURL:"/perform/tk",
			uploadURL:"/perform/upload",
			postVarsPerFile:{model: model_name},
			multipleFiles:false,
			simLimit:1,
			retryCount:5,
			onSelect: function(list) {
			},
			onFileCountExceed: function(selected, limit) {
				console.log("最多文件数量： " + limit + " 但已选择" + selected + "个");
			},
			onExtNameMismatch:function(file){
				console.log(file);
			},
			onRepeatedFile: function(file) {
			  console.log("文件： " + file.name
			   + " 大小：" + file.size + "已存在于上传队列中");
			   return false;
			},
			onAddTask: function(file, dat) {
				console.log(file);
				$('#uploadFileNameLabel').textbox('setValue',file.name);
				$('#operType').val('edit');
			},onComplete : function(file) {
				  var msg = eval('(' + file.msg + ')');	
				  file.suffix = file.name.substring(file.name.lastIndexOf('.'),file.name.length);
				  file.urlPath = msg.urlPath;
				  console.log("文件： " + file.name + "|" + file.size + " 已上传成功！");
				  complete(file);
				}
	};
	return new Stream(config);
}

function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1024, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}


