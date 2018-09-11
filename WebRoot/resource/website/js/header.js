$(function(){	
	//关于
	var ajaxOptions = {
	 url: 'aboutMenu.do',
	 type: 'post',
	 dataType: 'json',
	 success: function(data) {
		 if(data && data.length > 0){
			 //header的处理
			 var headerMenus = '';
			 var footerMenus = '';
			 $.each(data,function(key,value){
				 headerMenus += '<a href="aboutContent.do?id=' + value.id + '"><span>' + value.introduceName + '</span></a>';
				 footerMenus += '<li><a href="aboutContent.do?id=' + value.id + '">' + value.introduceName + '</a></li>';
			 });
			 headerMenus += '<a href="companyQualification.do"><span>企业资质</span></a>';
			 footerMenus += '<li><a href="companyQualification.do">企业资质</a></li>'
			 $('#aboutLi').append('<div class="second_menu">' + headerMenus + '</div>');
			 $('#aboutFooter').append(footerMenus);			 
		 }		 
	 }
	};
	$.ajax(ajaxOptions);
	
	//新闻
	var ajaxOptions = {
	 url: 'newsMenu.do',
	 type: 'post',
	 dataType: 'json',
	 success: function(data) {
		 if(data && data.length > 0){
			 //header的处理
			 var footerMenus = '';
			 $.each(data,function(key,value){
				 footerMenus += '<li><a href="news.do?categoryId=' + value.id + '">' + value.categoryName + '</a></li>';
			 });
			 $('#newsFooter').append(footerMenus);
		 }		 
	 }
	};
	$.ajax(ajaxOptions);
	
	//品牌
	var ajaxOptions = {
	 url: 'brandMenu.do',
	 type: 'post',
	 dataType: 'json',
	 success: function(data) {
		 if(data && data.length > 0){
			 //header的处理
			 var footerMenus = '';
			 $.each(data,function(key,value){
				 footerMenus += '<li><a href="brand.do?id=' + value.id + '">' + value.brandName + '</a></li>';
			 });
			 $('#brandFooter').append(footerMenus);
		 }		 
	 }
	};
	$.ajax(ajaxOptions);
	
	//经典案例
	var ajaxOptions = {
	 url: 'caseMenu.do',
	 type: 'post',
	 dataType: 'json',
	 success: function(data) {
		 if(data && data.length > 0){
			 //header的处理
			 var footerMenus = '';
			 $.each(data,function(key,value){
				 footerMenus += '<li><a href="classicCase.do?id=' + value.id + '">' + value.caseName + '</a></li>';
			 });
			 $('#caseFooter').append(footerMenus);
		 }		 
	 }
	};
	$.ajax(ajaxOptions);
});