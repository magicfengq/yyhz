//校验字段是否为空
function notNull(fieldname, string) {
	stringtemp = string.val();
	if (stringtemp == undefined || stringtemp == null || stringtemp == "") {
		$.messager.show({
			title:'消息提示' , 
			msg:fieldname + '不能为空!'
		});
		string.focus();
		return false;
	}
	return true;
}

function dateCheck(startDateTime,endDateTime) {
	var startDate=startDateTime.val();  
	 if(startDate == undefined || startDate == null || startDate=='') {
	 	return true;
	 }
	 var endDate=endDateTime.val();  
    if(endDate == undefined || endDate == null || endDate=='') {
	 	return true;
	 }
	 
	 var d1 = new Date(startDate.replace(/\-/g, "\/"));  
	 var d2 = new Date(endDate.replace(/\-/g, "\/"));  
	 if(startDate!=""&&endDate!=""&&d1 >=d2) {  
	    $.messager.show({
			title:'消息提示' , 
			msg:'结束时间必须晚于开始时间'
		});
	    return false;  
	 }
	 return true;
}
function timeCheck(startDateTime, endDateTime) {
	var startDate = startDateTime.val();
	if (startDate == undefined || startDate == null || startDate == '') {
		return true;
	}
	var endDate = endDateTime.val();
	if (endDate == undefined || endDate == null || endDate == '') {
		return true;
	}
	if (startDate.length != 5) {
		$.messager.show({
			title : '消息提示',
			msg : '开始时间格式不合法'
		});
		return false;
	}
	if (endDate.length != 5) {
		$.messager.show({
			title : '消息提示',
			msg : '结束时间格式不合法'
		});
		return false;
	}
	var startHour = parseInt(startDate.substr(0, 2));
	var startMin = parseInt(startDate.substr(3, 2));
	var endHour = parseInt(endDate.substr(0, 2));
	var endMin = parseInt(endDate.substr(3, 2));
	if (startHour > endHour || (startHour == endHour && startMin > endMin)) {
		$.messager.show({
			title : '消息提示',
			msg : '结束时间必须晚于开始时间'
		});
		return false;
	}
	return true;
}
function checkPic(fileArray) {
	if (fileArray != null && fileArray.length > 0) {
		for (var index=0 , length = fileArray.length ; index < length ; index++) {
			var fileName = $(fileArray[index]).val();
			
			if (!fileName || fileName == "") {
				continue;
			} else {
				fileName = fileName.toLowerCase();
				if (fileName.lastIndexOf(".gif") != -1) {
					$.messager.show({
						title:'消息提示' , 
						msg:'不能上传GIF格式图片!'
					});
					return false;
				}
			}
		}
	}
	return true;
}

