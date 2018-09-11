package com.yyhz.constant;

public class AppRetCode 
{
	public static int ERROR = -1;
	public static int NORMAL = 1;
	public static String NORMAL_TEXT = "操作成功";
	
	public static int PASSOWRD_IS_NULL = 101;
	public static String PASSOWRD_IS_NULL_TEXT = "密码为空";
	
	public static int MOBILE_IS_NULL = 102;
	public static String MOBILE_IS_NULL_TEXT = "手机号码为空";

	public static int PASSOWRD_ERROR = 103;
	public static String PASSOWRD_ERROR_TEXT = "密码错误";

	public static int ACCOUNT_NOT_EXIST = 104;
	public static String ACCOUNT_NOT_EXIST_TEXT = "账号不存在";
	
	public static int ACCOUNT_EXIST = 105;
	public static String ACCOUNT_EXIST_TEXT = "账号已存在";
	
	public static int ACCOUNT_DELETED = 105;
	public static String ACCOUNT_DELETED_TEXT = "账号已删除";

	public static int CREATE_ACCOUNT_FAILED = 106;
	public static String CREATE_ACCOUNT_FAILED_TEXT = "创建账号失败除";

	public static int PASSWORD_IS_INCONSISTENT = 107;
	public static String PASSWORD_IS_INCONSISTENT_TEXT = "密码不一致";
	
	public static int RESET_PASSWORD_FAILED = 108;
	public static String RESET_PASSWORD_FAILED_TEXT = "重置密码失败";

	public static int OTHER_LOGIN_KEY_IS_NULL = 109;
	public static String OTHER_LOGIN_KEY_IS_NULL_TEXT = "第三方key为空";
	
	public static int REGIST_TYPE_IS_NULL = 110;
	public static String REGIST_TYPE_IS_NULL_TEXT = "第三方key为空";

	public static int UPLOAD_FILE_FAILED = 111;
	public static String UPLOAD_FILE_FAILED_TEXT = "上传文件失败";

	public static int PARAM_ERROR = 111;

	public static int SERVER_EXCEPTION = 401;
	public static String SERVER_EXCEPTION_TEXT = "服务器异常";
	
}
