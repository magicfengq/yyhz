$(document).ready(function() {
	$(".sizeList").click(function() {
		logic($(this));
	});
	$(".colors").click(function() {
		var obj = $(this);
		if (!$(this).attr("checked")) {
			removemtAllSize();
			colorRemovemt($(this));
		} else {
			$(".sizeList:checked").each(function() {
				addmt(obj, $(this));
				$('.sizeTable').mergeCell({
					cols: [0]
				});
			});
		}
	});
});

function logic(sizeList) {
	if (!!sizeList.attr("checked")) {
		if ($(".colors:checked").length <= 0) {
			$(sizeList).attr("checked", false);
			alert("请先选择颜色");
			return false;
		}
		$(".colors:checked").each(function() {
			addmt($(this), sizeList);
			$('.sizeTable').mergeCell({
				cols: [0]
			});
		});
	} else {
		sizeRemovemt(sizeList);
	}
}

function addmt(colors, sizeList) {
	var priceValue=document.getElementById("price").value;
	if ($("#c" + colors.val()).length > 0) {
		var newRow = "<tr class='s" + sizeList.val() + "' align=\"center\"><td>"+colors.parent().text()+"</td><td>" + sizeList.parent().text() + "</td><td><input type=\"hidden\" class=\"text\" name=\"priceArr\" value=\""+priceValue+"\" /><input type=\"hidden\" value=\""+colors.val()+"\" name=\"colorArr\"/>" +
				"<input type=\"hidden\" value=\""+sizeList.val()+"\" name=\"sizeArr\"/><input class=\"text\" name=\"numberArr\" value=\"0\"/></td><td><input class=\"text\" name=\"codeArr\"/></td></tr>";
		$("#c" + colors.val()).append(newRow);
	} else {
		var newRow = "<tbody id='c" + colors.val() + "'><tr class='s" + sizeList.val() + "' align=\"center\"><td>" + colors.parent().text() + "</td><td>" + sizeList.parent().text() + "</td><td><input type=\"hidden\" value=\""+colors.val()+"\" name=\"colorArr\"/>" +
				"<input type=\"hidden\" value=\""+sizeList.val()+"\" name=\"sizeArr\"/><input type=\"hidden\" class=\"text\" name=\"priceArr\" value=\""+priceValue+"\" /><input class=\"text\" name=\"numberArr\" value=\"0\"/></td><td><input class=\"text\" name=\"codeArr\"/></td></tr></tbody>";
		$(".sizeTable").append(newRow);
	}
}

function sizeRemovemt(sizeList) {
	$(".s" + sizeList.val()).remove();
	$(".sizeTable td").removeAttr("style");
	$('.sizeTable').mergeCell({
				cols: [0]
	});
}

function colorRemovemt(colors) {
	$("#c" + colors.val()).remove();
}
function removemtAllSize() {
	var index=0;
	var colors=document.getElementsByName("colors"); 
	var sizeList=document.getElementsByName("sizeList"); 
	for(var i=0;i<colors.length;i++){ 
	  if(colors[i].checked==true) {
		  index++;
	  }
	}
	if(index==0){
		for(var k=0;k<sizeList.length;k++){ 
			sizeList[k].checked=false;
		}
	}

}