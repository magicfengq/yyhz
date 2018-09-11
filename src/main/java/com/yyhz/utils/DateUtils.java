package com.yyhz.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期工具类
 * 
 */
public class DateUtils {

	// 默认日期格式
	public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

	// 默认时间格式
	public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
	
	public static final String DATE_TIME_MIN_FORMAT="yyyy-MM-dd HH:mm";

	// 日期格式化
	private static DateFormat dateFormat = null;

	// 时间格式化
	private static DateFormat dateTimeFormat = null;
	
	private static DateFormat dateTimeMinFormat = null;

	private static DateFormat timeFormat = null;

	private static Calendar gregorianCalendar = null;

	static {
		dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
		dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
		timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
		dateTimeMinFormat = new SimpleDateFormat(DATE_TIME_MIN_FORMAT);
		gregorianCalendar = new GregorianCalendar();
	}

	/**
	 * 日期格式化yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期格式化yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateFormat(Date date) {
		if(date == null) {
			return null;
		}
		return dateFormat.format(date);
	}

	/**
	 * 日期格式化yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTimeFormat(Date date) {
		if(date == null) {
			return null;
		}
		return dateTimeFormat.format(date);
	}
	
	/**
	 * 日期格式化yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTimeMinFormat(Date date) {
		if(date == null) {
			return null;
		}
		return dateTimeMinFormat.format(date);
	}

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @return HH:mm:ss
	 */
	public static String getTimeFormat(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateFormat(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateTimeFormat(String date) {
		try {
			return dateTimeFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前日期(yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNowDate() {
		return DateUtils.getDateFormat(dateFormat.format(new Date()));
	}

	/**
	 * 获取当前日期星期一日期
	 * 
	 * @return date
	 */
	public static Date getFirstDayOfWeek() {
		gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		gregorianCalendar.setTime(new Date());
		gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取当前日期星期日日期
	 * 
	 * @return date
	 */
	public static Date getLastDayOfWeek() {
		gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		gregorianCalendar.setTime(new Date());
		gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取日期星期一日期
	 * 
	 * @param 指定日期
	 * @return date
	 */
	public static Date getFirstDayOfWeek(Date date) {
		if (date == null) {
			return null;
		}
		gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取日期星期一日期
	 * 
	 * @param 指定日期
	 * @return date
	 */
	public static Date getLastDayOfWeek(Date date) {
		if (date == null) {
			return null;
		}
		gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取当前月的第一天
	 * 
	 * @return date
	 */
	public static Date getFirstDayOfMonth() {
		gregorianCalendar.setTime(new Date());
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取当前月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		gregorianCalendar.setTime(new Date());
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
		gregorianCalendar.add(Calendar.MONTH, 1);
		gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取指定月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取指定月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
		gregorianCalendar.add(Calendar.MONTH, 1);
		gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取日期前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBefore(Date date) {
		gregorianCalendar.setTime(date);
		int day = gregorianCalendar.get(Calendar.DATE);
		gregorianCalendar.set(Calendar.DATE, day - 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取日期后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayAfter(Date date) {
		gregorianCalendar.setTime(date);
		int day = gregorianCalendar.get(Calendar.DATE);
		gregorianCalendar.set(Calendar.DATE, day + 1);
		return gregorianCalendar.getTime();
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static int getNowYear() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static int getNowMonth() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当月天数
	 * 
	 * @return
	 */
	public static int getNowMonthDay() {
		Calendar d = Calendar.getInstance();
		return d.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 获取时间段的每一天
	 * 
	 * @param 开始日期
	 * @param 结算日期
	 * @return 日期列表
	 */
	public static List<Date> getEveryDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		// 格式化日期(yy-MM-dd)
		startDate = DateUtils.getDateFormat(DateUtils.getDateFormat(startDate));
		endDate = DateUtils.getDateFormat(DateUtils.getDateFormat(endDate));
		List<Date> dates = new ArrayList<Date>();
		gregorianCalendar.setTime(startDate);
		dates.add(gregorianCalendar.getTime());
		while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
			// 加1天
			gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(gregorianCalendar.getTime());
		}
		return dates;
	}

	/**
	 * 获取提前多少个月
	 * 
	 * @param monty
	 * @return
	 */
	public static Date getFirstMonth(int monty) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -monty);
		return c.getTime();
	}

	/**
	 * 根据给定的格式化参数，将日期转换为字符串
	 * 
	 * @param date
	 * @param dateFormat
	 * @return String
	 */
	public static String toString(java.util.Date date, String dateFormat) {
		if ("".equals(date) || date == null) {
			return "bug: date is null";
		}
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 获取当前日期后第n天
	 * 
	 * @param n
	 * @return String
	 */
	public static String getNextDate(int n) {
		// 获取当前日期
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
		// 通过日历获取下一天日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, +n);
		String nextDate_1 = sf.format(cal.getTime());
		System.out.println(nextDate_1);
		return nextDate_1;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtils.getDateTimeMinFormat(new Date()));
	}
}
