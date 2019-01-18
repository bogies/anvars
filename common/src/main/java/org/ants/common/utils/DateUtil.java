package org.ants.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期处理工具类
 * 
 * @author dylan_xu
 * @date Mar 11, 2012
 * @modified by
 * @modified date
 * @since JDK1.6
 * @see com.util.DateUtil
 */

public class DateUtil {
	// ~ Static fields/initializers
	/** =============================================  */
	private static String defaultDatePattern = null;
	private static String timePattern = "HH:mm";
	private static Calendar cale = Calendar.getInstance();
	public static final String TS_FORMAT = DateUtil.getDatePattern() + " HH:mm:ss.S";
	/** 日期格式yyyy-MM字符串常量 */
	private static final String MONTH_FORMAT = "yyyy-MM";
	private static final String ONLY_MONTH_FORMAT = "MM";
	private static final String[] CHINESEMONTH = { "", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月",
			"十一月", "十二月" };
	/** 日期格式yyyy-MM-dd字符串常量 */
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	/** 日期格式HH:mm:ss字符串常量 */
	private static final String HOUR_FORMAT = "HH:mm:ss";
	/** 日期格式yyyy-MM-dd HH:mm:ss字符串常量 */
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 某天开始时分秒字符串常量 00:00:00 */
	@SuppressWarnings("unused")
	private static final String DAY_BEGIN_STRING_HHMMSS = " 00:00:00";
	/** 某天结束时分秒字符串常量 23:59:59 */
	public static final String DAY_END_STRING_HHMMSS = " 23:59:59";

	/** ===========================FORMAT==============================*/
	private static String sdfDateFormat(Date date){
		return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
	private static Date sdfDateParse(String date,ParsePosition pos) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return pos==null?sdf.parse(date):sdf.parse(date,pos);
    } 
	
	private static String sdfHourFormat(Date date){
		return new SimpleDateFormat(HOUR_FORMAT).format(date);
    } 
	private static Date sdfHourParse(String date) throws ParseException{
		return new SimpleDateFormat(HOUR_FORMAT).parse(date);
    } 
	
	private static String sdfDatetimeFormat(Date date){
		return new SimpleDateFormat(DATETIME_FORMAT).format(date);
    } 
	private static Date sdfDatetimeParse(String date,ParsePosition pos) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
		return pos==null?sdf.parse(date):sdf.parse(date,pos);
    }
	
	private static String sdfMonthFormat(Date date){
		return new SimpleDateFormat(MONTH_FORMAT).format(date);
    } 
	@SuppressWarnings("unused")
	private static Date sdfMonthParse(String date) throws ParseException{
		return new SimpleDateFormat(MONTH_FORMAT).parse(date);
    } 
							
	private static String sdfOnlyMonthFormat(Date date){
		return new SimpleDateFormat(ONLY_MONTH_FORMAT).format(date);
    } 
	@SuppressWarnings("unused")
	private static Date sdfOnlyMonthParse(String date) throws ParseException{
		return new SimpleDateFormat(ONLY_MONTH_FORMAT).parse(date);
    }
	/** ===========================Methods==============================*/
	public static void main(String[] args) throws Exception {
		
		System.out.println(getYear() + "|" + getMonth() + "|" + getDate());
		System.out.println(addDay("2016-07-01 10:33:10", 31));
		System.out.println(getDateTimeAddOrMinusYear(getDateTime(), 12, -5));
	}
	public DateUtil() {
	}

	/**
	 * date to String
	 * @param date date
	 * @param type dateType 1yyyy-MM-dd 2HH:mm:ss 3yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateToString(Date date, Integer type) {
		switch (type) {
		case 1:
			return sdfDateFormat(date);
		case 2:
			return sdfHourFormat(date);
		case 3:
			return sdfDatetimeFormat(date);
		case 4:
			return sdfMonthFormat(date);
		default: return null;
		}
	}

	/**
	 * String to Date
	 * @param date date
	 * @param type dateType 1yyyy-MM-dd 2HH:mm:ss 3yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Date getStringToDate(String date, Integer type) throws ParseException {
		switch (type) {
		case 1:
			return sdfDateParse(date, null);
		case 2:
			return sdfHourParse(date);
		case 3:
			return sdfDatetimeParse(date,null);
		default: return null;
		}
	}

	/**
	 * 获得服务器当前日期及时间，以格式为：yyyy-MM-dd HH:mm:ss的日期字符串形式返回
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getDateTime() {
		try {
			return sdfDatetimeFormat(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得服务器当前日期，以格式为：yyyy-MM-dd的日期字符串形式返回
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getDate() {
		try {
			return sdfDateFormat(cale.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得服务器当前时间，以格式为：HH:mm:ss的日期字符串形式返回
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getTime() {
		String temp = " ";
		try {
			temp += sdfHourFormat(cale.getTime());
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 统计时开始日期的默认值
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getStartDate() {
		try {
			return getYear() + "-01-01";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 统计时结束日期的默认值
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getEndDate() {
		try {
			return getDate();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得服务器当前日期的年份
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getYear() {
		try {
			return String.valueOf(cale.get(Calendar.YEAR));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得服务器当前日期的月份
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getMonth() {
		try {
			java.text.DecimalFormat df = new java.text.DecimalFormat();
			df.applyPattern("00;00");
			return df.format((cale.get(Calendar.MONTH) + 1));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得服务器在当前月中天数
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getDay() {
		try {
			return String.valueOf(cale.get(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 比较两个日期相差的天数
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMargin(String date1, String date2) {
		int margin;
		try {
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = sdfDateParse(date1, pos);
			Date dt2 = sdfDateParse(date2, pos1);
			long l = dt1.getTime() - dt2.getTime();
			margin = (int) (l / (24 * 60 * 60 * 1000));
			return margin;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 比较两个日期相差的天数
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static double getDoubleMargin(String date1, String date2) {
		double margin;
		try {
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = sdfDatetimeParse(date1, pos);
			Date dt2 = sdfDatetimeParse(date2, pos1);
			long l = dt1.getTime() - dt2.getTime();
			margin = (l / (24 * 60 * 60 * 1000.00));
			return margin;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 比较两个日期相差的月数
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getMonthMargin(String date1, String date2) {
		int margin;
		try {
			margin = (Integer.parseInt(date2.substring(0, 4)) - Integer.parseInt(date1.substring(0, 4))) * 12;
			margin += (Integer.parseInt(date2.substring(4, 7).replaceAll("-0", "-"))
					- Integer.parseInt(date1.substring(4, 7).replaceAll("-0", "-")));
			return margin;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 返回日期加X天后的日期
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date
	 * @param i
	 * @return
	 */
	public static String addDay(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
			gCal.add(GregorianCalendar.DATE, i);
			return sdfDateFormat(gCal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return getDate();
		}
	}

	/**
	 * 返回日期加X月后的日期
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date
	 * @param i
	 * @return
	 */
	public static String addMonth(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
			gCal.add(GregorianCalendar.MONTH, i);
			return sdfDateFormat(gCal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return getDate();
		}
	}

	/**
	 * 返回日期加X年后的日期
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param date
	 * @param i
	 * @return
	 */
	public static String addYear(String date, int i) {
		Integer day = 1;
		boolean noDay = false;
		try {
			day = Integer.parseInt(date.substring(8, 10));
		} catch (Exception e) {
			day = 1;
			noDay = true;
		}
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, day);
			gCal.add(GregorianCalendar.YEAR, i);
			if (noDay) {
				return sdfMonthFormat(gCal.getTime());
			} else {
				return sdfDateFormat(gCal.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 返回某年某月中的最大天
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMaxDay(int iyear, int imonth) {
		
		switch (imonth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: 
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default:;
		}
		boolean bl = ((0 == (iyear % 4)) && (0 != (iyear % 100)) || (0 == (iyear % 400)));
		if(bl) {
			return 29;
		}else {
			return 28;
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param orgDate
	 * @param Type
	 * @param Span
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String rollDate(String orgDate, int type, int span) {
		try {
			String temp = "";
			int iyear, imonth, iday;
			int iPos = 0;
			char seperater = '-';
			int len = 6;
			if (orgDate == null || orgDate.length() < len) {
				return "";
			}

			iPos = orgDate.indexOf(seperater);
			if (iPos > 0) {
				iyear = Integer.parseInt(orgDate.substring(0, iPos));
				temp = orgDate.substring(iPos + 1);
			} else {
				iyear = Integer.parseInt(orgDate.substring(0, 4));
				temp = orgDate.substring(4);
			}

			iPos = temp.indexOf(seperater);
			if (iPos > 0) {
				imonth = Integer.parseInt(temp.substring(0, iPos));
				temp = temp.substring(iPos + 1);
			} else {
				imonth = Integer.parseInt(temp.substring(0, 2));
				temp = temp.substring(2);
			}

			int imonthLen = 11;
			imonth--;
			if (imonth < 0 || imonth > imonthLen) {
				imonth = 0;
			}
			int idayLen = 31;
			iday = Integer.parseInt(temp);
			if (iday < 1 || iday > idayLen) {
				iday = 1;
			}

			Calendar orgcale = Calendar.getInstance();
			orgcale.set(iyear, imonth, iday);
			temp = this.rollDate(orgcale, type, span);
			return temp;
		} catch (Exception e) {
			return "";
		}
	}

	public static String rollDate(Calendar cal, int type, int span) {
		try {
			String temp = "";
			Calendar rolcale;
			rolcale = cal;
			rolcale.add(type, span);
			temp = sdfDateFormat(rolcale.getTime());
			return temp;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 返回默认的日期格式
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static synchronized String getDatePattern() {
		defaultDatePattern = "yyyy-MM-dd";
		return defaultDatePattern;
	}

	/**
	 * 将指定日期按默认格式进行格式代化成字符串后输出如：yyyy-MM-dd
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param aDate
	 * @return
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 取得给定日期的时间字符串，格式为当前默认时间格式
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param theTime
	 * @return
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * 取得当前时间的Calendar日历对象
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 * @throws ParseException
	 */
	public Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * 将日期类转换成指定格式的字符串形式
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param aMask
	 * @param aDate
	 * @return
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 将指定的日期转换成默认格式的字符串形式
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param aDate
	 * @return
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * 将日期字符串按指定格式转换成日期类型
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param aMask
	 *            指定的日期格式，如:yyyy-MM-dd
	 * @param strDate
	 *            待转换的日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw pe;
		}
		return (date);
	}

	/**
	 * 将日期字符串按默认格式转换成日期类型
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return aDate;
	}

	/**
	 * 返回一个JAVA简单类型的日期字符串
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getSimpleDateFormat() {
		SimpleDateFormat formatter = new SimpleDateFormat();
		return formatter.format(new Date());
	}
	//
	// /**
	// * 将指定字符串格式的日期与当前时间比较
	// * @author DYLAN
	// * @date Feb 17, 2012
	// * @param strDate 需要比较时间
	// * @return
	// * <p>
	// * int code
	// * <ul>
	// * <li>-1 当前时间 < 比较时间 </li>
	// * <li> 0 当前时间 = 比较时间 </li>
	// * <li>>=1当前时间 > 比较时间 </li>
	// * </ul>
	// * </p>
	// */
	// public static int compareToCurTime (String strDate) {
	// if (StringUtils.isBlank(strDate)) {
	// return -1;
	// }
	// Date curTime = cale.getTime();
	// String strCurTime = null;
	// try {
	// strCurTime = sdf_datetime_format.format(curTime);
	// } catch (Exception e) {
	// if (logger.isDebugEnabled()) {
	// logger.debug("[Could not format '" + strDate + "' to a date, throwing
	// exception:" + e.getLocalizedMessage() + "]");
	// }
	// }
	// if (StringUtils.isNotBlank(strCurTime)) {
	// return strCurTime.compareTo(strDate);
	// }
	// return -1;
	// }

	/**
	 * 为查询日期添加最小时间
	 * 
	 * @param 目标类型Date
	 * @param 转换参数Date
	 * @return
	 */
	public static Date addStartTime(Date param) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(param);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 为查询日期添加最大时间
	 * 
	 * @param 目标类型Date
	 * @param 转换参数Date
	 * @return
	 */
	public static Date addEndTime(Date param) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(param);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 返回系统现在年份中指定月份的天数
	 * 
	 * @param 月份month
	 * @return 指定月的总天数
	 */
	public static String getMonthLastDay(int month) {
		int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
		int year = Calendar.getInstance().get(Calendar.YEAR);
		boolean bl = (year % 4 == 0 && year % 100 != 0 || year % 400 == 0);
		if (bl) {
			return day[1][month] + "";
		} else {
			return day[0][month] + "";
		}
	}

	/**
	 * 返回指定年份中指定月份的天数
	 * 
	 * @param 年份year
	 * @param 月份month
	 * @return 指定月的总天数
	 */
	public static String getMonthLastDay(int year, int month) {
		int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
		boolean bl = (year % 4 == 0 && year % 100 != 0 || year % 400 == 0);
		if (bl) {
			return day[1][month] + "";
		} else {
			return day[0][month] + "";
		}
	}

	/**
	 * 判断是平年还是闰年
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param year
	 * @return
	 */
	public static boolean isLeapyear(int year) {
		boolean bl = ((year % 4 == 0 && year % 100 != 0) || (year % 400) == 0);
		if (bl) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取得当前时间的日戳
	 * 
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @return
	 */
	public static String getTimestamp() {
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

		return timestamp;
	}

	/**
	 * 取得指定时间的日戳
	 * 
	 * @return
	 */
	public static String getTimestamp(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String timestamp = "" + (cal.get(Calendar.YEAR)) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.MINUTE)
				+ cal.get(Calendar.SECOND) + cal.getTime().getTime();
		return timestamp;
	}

	/**
	 * 加减 年月日时分秒
	 * 
	 * @param nowDate
	 *            需计算的日期
	 * @param ti
	 *            月份:cal.add(2, i); 星期:cal.add(3, i); 每日:cal.add(5, i);
	 *            小时:cal.add(10, i); 分钟:cal.add(12, i); 秒 :cal.add(13, i);
	 * @param i
	 *            比如后i=1，取前i=-1
	 * @return
	 * @throws Exception
	 */
	public static String getDateTimeAddOrMinusYear(String nowDate, int ti, int i) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
		Date date = sdfDatetimeParse(nowDate,null);
		cal.setTime(date);
		cal.add(ti, i);
		return sdfDatetimeFormat(cal.getTime());
	}

	public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		// 格式化为年月
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()).replace("-", ""));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/** 获取中文月 */
	public static String getChineseMonth(Date date) {
		String month = sdfOnlyMonthFormat(date);
		int mi = Integer.valueOf(month);

		return CHINESEMONTH[mi];
	}
}