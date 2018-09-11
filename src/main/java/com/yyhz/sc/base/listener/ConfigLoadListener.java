package com.yyhz.sc.base.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConfigLoadListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties prop = new Properties();
        try {
            InputStream in = this.getClass().getResourceAsStream("/config.properties");
            prop.load(in);
           /* CfgConstant.APPID = prop.getProperty("wechat_pub_appID").trim();
            CfgConstant.APPSECRET = prop.getProperty("wechat_pub_appsecret").trim();
            CfgConstant.TOKEN = prop.getProperty("wechat_pub_token").trim();
            CfgConstant.DEFAULT_HQ_NAME = prop.getProperty("default_hq_name").trim();
            CfgConstant.DEFAULT_HQ_POINT_LNG = prop.getProperty("default_hq_point_lng").trim();
            CfgConstant.DEFAULT_HQ_POINT_LAT = prop.getProperty("default_hq_point_lat").trim();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
