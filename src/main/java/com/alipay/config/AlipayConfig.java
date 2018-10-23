package com.alipay.config;

/**
 * 基本信息配置
 * @author james.durant 20180302
 *
 */
public final class AlipayConfig {
	
	//沙箱APPID
	public static final  String app_id = "2018031602388277";
	//沙箱私钥
	public static final  String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC8CPvC8bW0pVEXQhp9T8VkSn5EkrBgFE0BrrB0bDalMJvzMEnGzJvJsXDWTD1nFVA4rphj72r+Wf82XiZtrw6ZEmpAmHarc9CZOvFfG63VkMJmtUfyILIqhIsYqswiXqnSQJrXvknbUsXhbJj3/HGZQziA3mWK0NnArfrisO6JgM9MAcgPav4KIPaFXVCg/DxmE4CIOUVemTnCrCfKAWH4EMKqudKMJKNnW3RAQb/wfHFcFOg5+C0W+DfROtb4+B1ywFgdjq0MCelUuqbYt17B5IZAMT7nVuayC5smeaTe04jnVNcTy8VafVbaQ5kqYBpBZ8r/btvs/Iw+Jd/CtYi/AgMBAAECggEBAJ5CVVtrQorrRQf1TARCOpnBlOq0J5yGGZYb6Enktk1sTyVsc/vNRCWKobTL8DYtmxpJ4NrjrEKtjn682U0htICjHiiF6gM988iz8mkhC7sfHm+Bji8805ezfLrjLfKTiAz88RY6A0GENiYX19uIbjgXepwEEqS2ST/BbtKTJgE1pGTZ9ltH8WcTB0/B+k3F2NnUL1OYvL4FeQgEItkqyRzkhHzd7bwwFYx6iFzkUDp7dvwkQ4qgVNIZPRo6NTuA2tVf1QLUqnicOuULK7f1Gk2UNOmpPTlMQ7lKwheWo7jsIIjxyIX9DPNcLGX88hcTruW7XS+L1Kwzi3bCKfqsvJkCgYEA88Bchj6ZToy4qh98naYzQdWFNUX/HhyFHNHqIHhzo6lH2hmljiEgKJ5L1k9S+oMxVc03h4Xd8wQnYPL3mFO/yrdyIu+ixLazs9kSQforzjsHPE/+t5UK071lbxigDkB5vYG/2s1fPe+9lXBIrorSOPo3AFUTAIXrR36KLStUmhUCgYEAxXvh8rrQaSFacC/LFvqBgwPt8mjyovpRAlER4TyYlqHael7g+ICnwEifPfccjizcrdpXnzTDr7EOBbtvKCykobHbkyBtNm7XVQEald/Yc/K+vRcFC8ub/oJHul0Ikt2szbnwCZ6QylcUUWispLyu2JaFKvIy4ldILxZAIR7b8IMCgYAjVF9Uyd/4pabiaZvrUbqeMS+UkCexGpskmTxhJnREZXXXo93+IskBeigt0OOLEdblw8/nobyQy6pVgAHpH0JuRHaTn74s2vpi9/SQgY014BJlaIbX3w9pr0rzdlNEnY3HrPW85gWG+ae18EdonRBpV8L/FlWhvsdiXnHyqBQkHQKBgQCbKKkuz65dWzI2QKa9KBbHfDfgpKo2eJtbSZzVrYlpmEMSsCbruBGU1roesl+CKSc9hPYhBRDWojKVOF2uX9z9mcMdxnVoNhADHnnn1Va4Srmn0UVKri+i+HMorl3FHlvdy0AYHrg98crp52b1i7WIQELiqiZmhRMWw9Gg9CDzxwKBgQDENsszexUzBTw7tHEs5ljuNoymureX40OurIUi/u80PY1r8IDcqNFa7TwhCnPEZ9inwHpj7MOK2iZ8Be1DY7MDLTG4x3hIpRKAO4UK0z8mBHVIitIcTUA+kZaZ65sVwCgrT4xEvmNJS/CMpC0XmP6AJnrXARf4ZuYyV5Yn5up16Q==";
	//支付宝公钥
	public static final  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgRvMVGdQ6dqPz8b8KKa6KC+vtbUuwZmU9IfLLL9UWqwtuZ1FgOnl6DLxoqLZOWmlkNKUheCXiojxeFgE3x750s5lg2BSjJyfnIYcDu6fj4p+hrl3yakGi2fGhNDreyLhbxx9NwhFIPeHXDUj84Te8BFaOLWXUWl+YTGeG8Tg0HPcAVjlBjxqEAr3hzE3ImwA2n0y7tWy0UAeUNC0LHDnoE/CHhvEpWA1GRJZ3HN9n3fre2/nlPs1gnZLiwUSXBgSW2wzW/RqWs2W2Hpw7Xo0GjsSAoheZ9Vtr4aLZb5be/1eEdJJlaZ8o0SGY74hD8EL56Bkm/92cExuXZvvCWFd1wIDAQAB";
	//沙箱网关地址
	public static final  String gatewayUrl = "https://openapi.alipay.com/gateway.do";

   // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://www.jamesdurant.top";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";

	//
	public static String format = "json";

}


