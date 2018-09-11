document.write("<script type='text/javascript' src='js/layer/layer.min.js'></script>");
function alert(t,v,b){
	$.layer({
        type : 0,
        title: [t],
        dialog: {
        type: b,
        msg:v
        },
        border: [0],
        closeBtn: false,
        fadeIn: 300
	});
};