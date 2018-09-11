$(document).ready(function () {
    var index = 0;//默认初始化的slide下标
    var sear = window.location.search;//获取浏览器URL参数
    var hash = sear.substr(4,32);
    var dom = $("#"+hash);
    if(-1 != $(dom).index()){
        index = $(dom).index();//获取当前页面要显示的第一张图片的下标
    }

    var len = $(".swiper-slide").length;
    _initGalleryInfo(index, len, dom);


    //初始化轮播图片高度
    initSwiperHeight();
    //切换屏幕窗口大小
    window.onresize = function () {
        initSwiperHeight();
    };



    //初始化滑动
    var mySwiper = new Swiper('.swiper-container',{
        speed:500,
        initialSlide :index, //设置初始化时slide的索引
        // Disable preloading of all images
        preloadImages: false,
        // Enable lazy loading
        lazyLoading : true,
        /*observer:true,*/

        resistance : '100%',
        grabCursor: true,
        watchActiveIndex : true,
        onSlideChangeEnd:function(swiper){
            var nubT = swiper.activeIndex+1;
            var curDom = $(".swiper-slide").eq(swiper.activeIndex);
            var currentId =curDom.attr("id");
            location.hash = "#"+currentId;  //修改hash值
            var imgDom = curDom.find("img");
            var descinfo = imgDom.data("desc");
            $("#page").html(nubT + "/" + len);//修改当前显示的数量
            $("#img_desc").html(descinfo);

        }
    });


    //初始化显示的图片信息
    function  _initGalleryInfo(index, len, curDom) {
        $("#page").html(index + 1+"/" + len);
        if (index==0) {
        	 $("#img_desc").html($("#1").data("desc"));
        } else{
	        var descinfo = curDom.find("img").data("desc");
	        $("#img_desc").html(descinfo);
        }
    }

    //初始化滑动图片容器的高度
    function  initSwiperHeight() {
//      var topH = $("header").height();
        $("#swiper_wrapper").height($(window).height());
    }

});
