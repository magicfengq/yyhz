window.onload = window.onresize = function() {
	var page = new pageResponse({
		class: 'mainbox', //模块的类名，使用class来控制页面上的模块(1个或多个)
		mode: 'auto', // auto || contain || cover ，默认模式为auto 
		width: '320', //输入页面的宽度，只支持输入数值，默认宽度为320px
		height: '504' //输入页面的高度，只支持输入数值，默认高度为504px
	});
};