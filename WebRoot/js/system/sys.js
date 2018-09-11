$(function(){
	$("#tt").tabs({
		onAdd:function(title,index){
			tabPanels.push(title);
		},
		onClose:function(title,index){
			tabPanels.splice($.inArray(title,tabPanels),1);
		}
	});
});

var setting = {
	data : {
		simpleData : {
			enable : false
		}
	},
	callback : {
		onClick : onClick
	}
};

function onClick(event, treeId, treeNode) {
	if (typeof (treeNode.menuUrl)) {
		if (treeNode.menuUrl != "") {
			if($.inArray(treeNode.name, tabPanels) < 0){
				$("#tt").tabs('add',{
					title : treeNode.name,
					width : "100%",
					height : "600px",
					content : '<iframe title="content" frameborder="0" height="100%"'
							+ 'width="100%" allowTransparency="true" align="center"'
							+ 'src='
							+ treeNode.menuUrl
							+ '></iframe>',
					closable : true
				});
			}else{
				$("#tt").tabs('select',treeNode.name);
			}
		}
	}
}
$(document).ready(function() {
	$.ajax({
		type : "POST",
		dataType : "JSON",
		url : "./system/initSystemMenuTree.do",
		success : function(data) {
			// var z=jQuery.parseJSON(data);alert(data);
			$.fn.zTree.init($("#tree"), setting, data);
			zTree = $.fn.zTree.getZTreeObj("tree");
		}
	});
});
