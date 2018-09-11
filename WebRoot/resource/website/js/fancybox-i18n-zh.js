function fancybox(){
	$("[data-fancybox]").fancybox({
		buttons:[
	        'slideShow',
			'thumbs',
			'close'
		],
		lang : 'zh',
		i18n : {
			'zh' : {
				CLOSE       : '关闭',
				NEXT        : '下一张',
				PREV        : '上一张',
				ERROR       : '图片加载失败.请确认是否上传到正确的路径.',
				PLAY_START  : '开始幻灯片',
				PLAY_STOP   : '停止幻灯片',
				FULL_SCREEN : '全屏',
				THUMBS      : '预览'
		  	}
	   }
	});
}