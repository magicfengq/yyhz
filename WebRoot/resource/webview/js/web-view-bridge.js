/**
 * 
 */

function setupWebViewJavascriptBridge(callback) {
	logWebView('setupWebViewJavascriptBridge begin!');
    if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
    if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
    
    logWebView('setupWebViewJavascriptBridge 111111111!');
    
    window.WVJBCallbacks = [callback];
    var WVJBIframe = document.createElement('iframe');
    WVJBIframe.style.display = 'none';
    WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
    document.documentElement.appendChild(WVJBIframe);
    setTimeout(function() {
        logWebView('setupWebViewJavascriptBridge 2222222222!');

    	document.documentElement.removeChild(WVJBIframe) 
    	}, 0)
}

function getCardDetail(id,nickName,cardName,headImgUrl,firstImgUrl,createrId,publicType) {
	try {
		if(isAndroid()) {
			getCardDetailAndroid(id,nickName,cardName,headImgUrl,firstImgUrl,createrId,publicType);
		}else {
			getCardDetailIOS(id,nickName,cardName,headImgUrl,firstImgUrl,createrId,publicType);
		}
	}
	catch(err) {
		logWebView('exception:' + err.message);
	}

}

function getCardDetailAndroid(id,nickName,cardName,headImgUrl,firstImgUrl,createrId,publicType) {
	window.JavaScriptInterface.toCardDetail(id,nickName,cardName,headImgUrl,firstImgUrl,createrId,publicType);
}

function getCardDetailIOS(id,nickName,cardName,headImgUrl,firstImgUrl,createrId,publicType) {
	setupWebViewJavascriptBridge(function(bridge) {
		bridge.callHandler("toCardDetail", {
			'id':id,
			'nickName':nickName,
			'cardName':cardName,
			'headImgUrl':headImgUrl,
			'firstImgUrl':firstImgUrl,
			'createrId':createrId,
			'publicType':publicType
			}, function(response) {
			logWebView('handler response : ' + response);
		});
	})
}

function getShowDetail(id,type,creater) {
	try {
		if(isAndroid()) {
			getShowDetailAndroid(id,type,creater);
		}else {
			getShowDetailIOS(id,type,creater);
		}
	}
	catch(err) {
		logWebView('exception:' + err.message);
	}
}

function getShowDetailAndroid(id,type,creater) {
	window.JavaScriptInterface.toShowDetail(id,type,creater);
}

function getShowDetailIOS(id,type,creater) {
	setupWebViewJavascriptBridge(function(bridge) {
		bridge.callHandler("toShowDetail", {
			'id':id,
			'type':type,
			'creater':creater
			}, function(response) {
			logWebView('handler response : ' + response);
		});
	})
}

function getActorInfo(id,nickName,headImgUrl) {
	logWebView('getActorInfo begin!');
	try {
		if(isAndroid()) {
			getActorInfoAndroid(id,nickName,headImgUrl);
		}else {
			getActorInfoIOS(id,nickName,headImgUrl);
		}
	}
	catch(err) {
		logWebView('exception:' + err.message);
	}
	logWebView('getActorInfo end!');
}

function getActorInfoAndroid(id,nickName,headImgUrl) {
	logWebView('getActorInfoAndroid begin!');
	window.JavaScriptInterface.toHisInfo(id,nickName,headImgUrl);
	logWebView('getActorInfoAndroid end!');
}

function getActorInfoIOS(id,nickName,headImgUrl) {
    logWebView('getActorInfoIOS begin!');

	setupWebViewJavascriptBridge(function(bridge) {
		logWebView('call ios handler begin!');

		bridge.callHandler("toHisInfo", {
			'id':id,
			'nickName':nickName,
			'headImgUrl':headImgUrl
			}, function(response) {
				logWebView('handler response : ' + response);
			});
		
		logWebView('call ios handler end!');
	})
}

function getActorCommentList(id,type) {
	logWebView('getActorCommentList begin!');
	try {
		if(isAndroid()) {
			getActorCommentListAndroid(id,type);
		}else {
			getActorCommentListIOS(id,type);
		}
	}
	catch(err) {
		logWebView('exception:' + err.message);
	}
	logWebView('getActorCommentList end!');
}

function getActorCommentListAndroid(id,type) {
	logWebView('getActorCommentListAndroid begin!');
	window.JavaScriptInterface.toCommentList(id,type);
	logWebView('getActorCommentListAndroid end!');
}

function getActorCommentListIOS(id,type) {
	logWebView('getActorCommentListIOS begin!');
	setupWebViewJavascriptBridge(function(bridge) {
		logWebView('call ios handler begin!');

		bridge.callHandler("toCommentList", {'id' : id,'type' : type}, function(response) {
			logWebView('handler response : ' + response);
		});
		
		logWebView('call ios handler end!');
	})
}

function isAndroid() { 
	return /Android/i.test(navigator.userAgent);
}

function logWebView(message) {
	/*
	var ul = document.querySelector(".message_list");
	var html = '<li class="clearfix">';
	html += '<span class="float-left"><i style="color: #fff;">*</i>' + message + '</span>';
	html += '</li>';
	
	ul.innerHTML += html;
	*/
}