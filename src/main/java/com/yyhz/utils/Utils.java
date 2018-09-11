package com.yyhz.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Utils {
	public static boolean isPass = false;
	public static String TOKEN = "";
	public static String EncodingAesKey = "";

	public void init() {
		Properties p = getProperties("properties/config");
		TOKEN = p.getProperty("TOKEN");
		EncodingAesKey = p.getProperty("EncodingAesKey");
	}

	public static String getStringFromObj(Object obj) {
		String result = null;
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	/**
	 * 获取属性文件
	 * 
	 * @param name
	 * @return
	 */
	public Properties getProperties(String name) {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(name + ".properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p;
	}
	
	/**
	 * 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(HttpServletRequest request,String sPath) {
		boolean flag = false;
		HttpSession session = request.getSession();      
		ServletContext  application  = session.getServletContext();    
		String serverRealPath = application.getRealPath("/") ;
		File file = new File(serverRealPath+sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 硬件设备控制查询通用方法
	 * 初始化参数
	 * @return
	 */
	public static Map<String, String> initParamMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cs1", MessagesUtils.getString("Control.default_value"));
		map.put("cs2", MessagesUtils.getString("Control.default_value"));
		map.put("cs3", MessagesUtils.getString("Control.default_value"));
		map.put("cs4", MessagesUtils.getString("Control.default_value"));
		map.put("cs5", MessagesUtils.getString("Control.default_value"));
		return map;
	}
	
	 /**
     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
 
        int len = source.length();
 
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
 
            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }
 
        return false;
    }
 
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
 
    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
 
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;
 
        int len = source.length();
 
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
 
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
 
                buf.append(codePoint);
            } else {
            }
        }
 
        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
 
    }
    
    private static final double EARTH_RADIUS = 6378137.0;

    // 返回单位是千米
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
		
	public static void main(String[] args) throws Exception {
		// 计算距离 116.35658741； 纬度：39.90720146
		double lat1 = 39.9323475;// 纬度
		double lng1 = 116.35566473;// 经度
		double lat2 =  39.90720146;// 纬度
		double lng2 =  116.35658741;// 经度
		
		double dis = getDistance(lng1, lat1, lng2, lat2);
		
		System.out.println(new DecimalFormat("#").format(dis));

	}
}
