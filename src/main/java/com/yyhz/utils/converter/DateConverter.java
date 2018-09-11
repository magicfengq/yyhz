package com.yyhz.utils.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String,Date> {
	
	@Override
	public Date convert(String source) {
		String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(source);
		boolean dateFlag = m.matches();
		if (dateFlag) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try {
				return simpleDateFormat.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        return null;
	}

}
