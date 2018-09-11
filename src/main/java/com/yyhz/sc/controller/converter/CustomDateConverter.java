package com.yyhz.sc.controller.converter;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.expression.ParseException;

import com.yyhz.utils.DateFormatUtil;
import com.yyhz.utils.DateUtils;

public class CustomDateConverter implements Converter<String,Date>{
    public Date convert(String s) {
    	if(StringUtils.isBlank(s)){
    		return null;
    	}
        try {
            //转成直接返回
            return DateUtils.formatDate(DateFormatUtil.FormatDate(s), DateUtils.DATETIME_DEFAULT_FORMAT);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //如果参数绑定失败返回null
        return null;
    }
}