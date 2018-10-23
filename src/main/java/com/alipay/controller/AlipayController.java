package com.alipay.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.ZhimaCreditScoreGetRequest;
import com.alipay.api.request.ZhimaCustomerCertificationCertifyRequest;
import com.alipay.api.request.ZhimaCustomerCertificationInitializeRequest;
import com.alipay.api.request.ZhimaCustomerCertificationQueryRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.ZhimaCreditScoreGetResponse;
import com.alipay.api.response.ZhimaCustomerCertificationCertifyResponse;
import com.alipay.api.response.ZhimaCustomerCertificationInitializeResponse;
import com.alipay.api.response.ZhimaCustomerCertificationQueryResponse;
import com.alipay.config.AlipayConfig;



/**
 * @Name: AlipayController.java 
 * @Desc:   支付宝 芝麻刷脸 and 芝麻分
 * @createTime: 2018-04-11 14:55:44  
 * @author: james.durant wuwei    
 * @version: v1.0  
*/  
    
@Controller
@RequestMapping("/alipay")
public class AlipayController{
	
	/**
	 * @desc: 人脸识别 开始认证  返回一个url 给手机端 用于打开支付宝客户端
	 * @createTime: 2018年7月9日 下午6:42:00 
	 * @author: james.durant  
	 * @param name
	 * @param idCard
	 * @return
	 * @throws AlipayApiException Object
	 */
	@RequestMapping("/getAuthentication")
	@ResponseBody
	public Object start(@RequestParam String name,@RequestParam String idCard) throws AlipayApiException {
		//first step
		String BizNo = ZhimaInitializeRequest4BizNo(name,idCard);
	 
		Map<String,String> map = new HashMap<String,String>();
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset,
				AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		ZhimaCustomerCertificationCertifyRequest request = new ZhimaCustomerCertificationCertifyRequest();
		// 设置业务参数,必须要biz_no
		request.setBizContent("{\"biz_no\":\""+ BizNo +"\"}");	
		//request.setReturnUrl("alipay://www.jamesdurant.top:8888/alipay/returnZmFace?BizNo=" + BizNo );			
		request.setReturnUrl("myapp://");	// james add it , 4 return immeditelly
		ZhimaCustomerCertificationCertifyResponse response = alipayClient.pageExecute(request, "GET");
		if (response.isSuccess()) {
			String url = response.getBody();
			System.out.println("调用芝麻认证成功::generateCertifyUrl url:" + url);
			map.put("url",url);
			return map;
		} else {
			System.out.println("调用芝麻认证失败");
			map.put("url","");
			return map;	
		}
	}
	
	

	/**
	 * @desc: 初始化认证方法
	 * @createTime: 2018年7月9日 下午6:38:45 
	 * @author: james.durant  
	 * @param name
	 * @param idCard
	 * @return
	 * @throws AlipayApiException String
	 */
	@ResponseBody
	public String ZhimaInitializeRequest4BizNo(@RequestParam String name,@RequestParam String idCard) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset,
				AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		String transaction_id = genertorTransactionId();
		
		ZhimaCustomerCertificationInitializeRequest request = new ZhimaCustomerCertificationInitializeRequest();
		String bizContent = "{"
		        + "\"transaction_id\":\""+ transaction_id + "\","
		        + "\"product_code\":\"w1010100000000002978\","
		        + "\"biz_code\":\"FACE\","
		        + "\"identity_param\":\"{\\\"identity_type\\\":\\\"CERT_INFO\\\",\\\"cert_type\\\":\\\"IDENTITY_CARD\\\",\\\"cert_name\\\":\\\""+ name +"\\\",\\\"cert_no\\\":\\\""+idCard+"\\\"}\","
		        + "\"ext_biz_param\":\"{}\"" + "  }";
		bizContent = bizContent.replaceAll("\"idCard", "\""+idCard);
		bizContent = bizContent.replaceAll("\"name", "\""+name);
		request.setBizContent(bizContent);
		ZhimaCustomerCertificationInitializeResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("初始化成功::BizNo:"+ response.getBizNo());
			return response.getBizNo();

		} else {
			System.out.println("初始化失败");
			return "初始化失败";
		}
	}

	/**
	 * 人脸识别回调H5  点击返回到应用, 弃用!
	 */
	@RequestMapping("/returnZmFace")
	public String returnUrl(HttpServletRequest request) throws AlipayApiException{
		String BizNo =request.getParameter("BizNo");
		System.out.println(BizNo);
		request.setAttribute("result", "true");
		return "returnZmFace";
	}
	
	
	
	//===========================芝麻分 华丽的分割线=============================================
	
	/**
	 * @desc: 芝麻分 回调函数界面
	 * @createTime: 2018年7月9日 下午6:42:36 
	 * @author: james.durant  
	 * @param auth_code
	 * @param request
	 * @return
	 * @throws AlipayApiException String
	 */
	@RequestMapping("getZmScore")
	public String returnPage(@RequestParam String auth_code,HttpServletRequest request) throws AlipayApiException{
		//获取芝麻分
		String  ZmScore = getZhima(auth_code);	
		
		if(ZmScore!=""){
			System.out.println(ZmScore);
			request.setAttribute("ZmScore", ZmScore);
			return "returnZmScore";
		}
			return "returnZmScoreFail";
	}
	
	
	/**
	 * @desc: 获取芝麻分
	 * @createTime: 2018年7月9日 下午6:39:06 
	 * @author: james.durant  
	 * @param auth_code
	 * @return
	 * @throws AlipayApiException String
	 */
		@ResponseBody
		public  String getZhima(@RequestParam String auth_code) throws AlipayApiException{
			//first step
			String accessToken = zhimaTokenRequest(auth_code);
			
			String transaction_id = genertorTransactionId();
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
					AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset,
					AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			ZhimaCreditScoreGetRequest request = new ZhimaCreditScoreGetRequest();
			request.setBizContent("{" +
			" \"transaction_id\":\"" + transaction_id +"\"," +
			" \"product_code\":\"w1010100100000000001\"" +
			" }");
			ZhimaCreditScoreGetResponse response = alipayClient.execute(request,accessToken);
			if(response.isSuccess()){
			    System.out.println("调用成功::芝麻分=" + response.getZmScore());
			    return response.getZmScore();
			} else {    
			    // 处理各种异常
			    System.out.println("调用失败::查询芝麻分错误 code=" + response.getCode());
			    return "";
			}
		}
		
		/**
		 * @desc: 获取token
		 * @createTime: 2018年7月9日 下午6:35:03 
		 * @author: james.durant  
		 * @param code
		 * @return
		 * @throws AlipayApiException String
		 */
		public String zhimaTokenRequest(@RequestParam String code) throws AlipayApiException{
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
					AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset,
					AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setGrantType("authorization_code");
			request.setCode(code);
			AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
			System.out.println("调用成功::"+response.getAccessToken());
			} else {
			System.out.println("调用失败");
			}
			return response.getAccessToken();
		}
			
		/**
		 * @desc: transaction_id 生成器
		 * @createTime: 2018年7月9日 下午6:35:28 
		 * @author: james.durant    
		 * @return String
		 */
		public String genertorTransactionId (){
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH");
	           String str=sdf.format(new Date());
	           String transaction_id ="xinzheng" + str + new Date().getTime();
			return transaction_id;
		}
		
		
		/**
		 * @desc: 查询人脸认证是否成功(备用)
		 * @createTime: 2018年7月9日 下午6:36:05 
		 * @author:  james.durant      
		 * @param BizNo
		 * @return
		 * @throws AlipayApiException String
		 */
		public String requestResultState(String BizNo) throws AlipayApiException{
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
			ZhimaCustomerCertificationQueryRequest request = new ZhimaCustomerCertificationQueryRequest();
			request.setBizContent("{\"biz_no\":\""+ BizNo +"\"}");
			ZhimaCustomerCertificationQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功");
				return "Success";
			} else {
				System.out.println("调用失败");
				return "Failed";
			}
		}
		


}
