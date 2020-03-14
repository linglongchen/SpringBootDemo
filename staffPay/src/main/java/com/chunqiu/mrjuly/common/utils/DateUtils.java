package com.chunqiu.mrjuly.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	public static final String PATTERN_DATE_SHORT = "yyyyMMdd";
	public static final String PATTERN_DATE_LONG = "yyyyMMddHHmmss";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_TIME1 = "yyyy-MM-ddHH:mm:ss";
	public static final String PATTERN_DATE_TIME_NOSECOND = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_DATE_MINUTES = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_TIME_INT = "HHmmss";
	public static final String PATTEN_DATE_HOUR = "HH";

	private static String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(new Date());
		Date dateTime = formatter.parse(date);
		return dateTime;
	}

	/**
	 * 根据固定的格式，将字符串转化为Date
	 * @param str
	 * @param ftm
	 * @return
	 */
	public static Date parseDate(String str, String ftm) {
		if (str == null){
			return null;
		}
		try {
			return new SimpleDateFormat(ftm).parse(str);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

	/**
	 * 获取两个日期之间的天数，Date类型
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	/**
	 * 获取两个日期之间的天数，字符串格式
	 *
	 * @param before
	 * @param after
	 * @param fmt : 字符串的日期格式
	 * @return
	 */
	public static double getDistanceOfTwoDate(String before, String after, String fmt){
		Date beforeD = parseDate(before, fmt);
		Date afterD = parseDate(after, fmt);

		return getDistanceOfTwoDate(beforeD, afterD);
	}
	/**
	 * @Description: 获取两个日期之间的小时数
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistHoursOfTwoDate(Date before, Date after){
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60);
	}
	/**
	 * @Description: 获取两个时间相差的分钟数
	 * @param start
	 * @param end
	 * @return
	 */
	public static Integer diffMinute(Date start, Date end){
		return  (int)Math.ceil((double)(end.getTime() - start.getTime()) / (1000 * 60));
	}

	/**
	 * 通过毫秒时间戳获取小时数和分钟数
	 * @param time
	 * @return
	 */
	public static String getHourAndMinute(long time){
		int minute = (int)Math.ceil((double)(time) / (1000 * 60));
		int hours = (int) Math.floor((double)minute / 60);
		minute = minute % 60;
		StringBuilder sb = new StringBuilder();
		if (hours > 0){
			sb.append(hours).append("小时");
		}
		if (minute > 0){
			sb.append(minute).append("分");
		}
		return sb.toString();
	}


	/***
	 * @comments 计算两个时间的时间差
	 * @param strTime1
	 * @param strTime2
	 */
	public static String getTimeDifference(String strTime1,String strTime2) throws ParseException {
		//格式日期格式，在此我用的是"2018-01-24 19:49:50"这种格式
		//可以更改为自己使用的格式，例如：yyyy/MM/dd HH:mm:ss 。。。
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = df.parse(strTime1);
		Date date=df.parse(strTime2);
		String  dateTime = String.valueOf(now.getTime()-date.getTime());       //获取时间差
		return dateTime;
	}


	/***
	 * @comments 计算两个时间的时间差
	 * @param strTime1
	 */
	public static String getTimeDifference1(String strTime1) throws ParseException {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String strTime2 = df1.format(new Date());// new Date()为获取当前系统时间
		//格式日期格式，在此我用的是"2018-01-24 19:49:50"这种格式
		//可以更改为自己使用的格式，例如：yyyy/MM/dd HH:mm:ss 。。。
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now = df.parse(strTime1);
		Date date=df.parse(strTime2);
		String  dateTime = String.valueOf(now.getTime()-date.getTime());       //获取时间差
		return dateTime;
	}


		public static String leaseTime(String time) throws ParseException {
				String aa =	getTimeDifference1(time);
				if (Integer.parseInt(aa)<=0){
					aa = "租赁期已到";
					return aa;
				}else {
					return  getTime1(aa);
				}
		}

	public static String getTime1(String dateTime) throws ParseException {
		String dateTime1 = dateTime.replace(".0","");
		long date = Long.parseLong(dateTime1);
		long day=date/(24*60*60*1000);
		long hour=(date/(60*60*1000)-day*24);
		long min=((date/(60*1000))-day*24*60-hour*60);
		long s=(date/1000-day*24*60*60-hour*60*60-min*60);
		String time =  day+"天";
		return time;
	}


	/**
	 * 时间转换
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String getTime(String dateTime) throws ParseException {
		String dateTime1 = dateTime.replace(".0","");
		long date = Long.parseLong(dateTime1);
		long day=date/(24*60*60*1000);
		long hour=(date/(60*60*1000)-day*24);
		long min=((date/(60*1000))-day*24*60-hour*60);
		long s=(date/1000-day*24*60*60-hour*60*60-min*60);
		String time =  day+"天"+hour+"时"+min+"分"+s+"秒";
		return time;
	}
	public static void main(String[] args) throws ParseException {
		String dateStr = "2019-05-17";
		String dateStr2 = "2019-06-15";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			System.out.println("两个日期的差距：" + differentDays(date,date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	/**
	 * date2比date1多的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			return day2-day1;
		}
	}



	public static String date(String date1,String date2) throws ParseException {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");

		Date d1=sdf.parse(date1);

		Date d2=sdf.parse(date2);

		long daysBetween=(d2.getTime()-d1.getTime()+1000000)/(60*60*24*1000);
		return String.valueOf(daysBetween);

	}


	public static String fomartDate(Date date) throws ParseException {
		Date time =new Date(String.valueOf(date));
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeFormat = sd.format(time);
		return timeFormat;

	}



	public static List<String> getBetweenDate(String begin, String end){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<String> betweenList = new ArrayList<String>();

		try{
			Calendar startDay = Calendar.getInstance();
			startDay.setTime(format.parse(begin));
			startDay.add(Calendar.DATE, -1);

			while(true){
				startDay.add(Calendar.DATE, 1);
				Date newDate = startDay.getTime();
				String newend=format.format(newDate);
				betweenList.add(newend);
				if(end.equals(newend)){
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return betweenList;
	}
	
	/**
	 * 获取以当前时间偏移月份的第一天的日期
	 * @param monthDiff 偏移量：0~无偏移，1~递增一个月，-1~递减一个月
	 * @return yyyy-MM-dd日期格式字符串
	 * @author wy
	 */
	public static String getMonthFirstDay(int monthDiff){
		Calendar calendar=Calendar.getInstance();//获取当前时间 
		calendar.add(Calendar.MONTH, monthDiff);//设置月份偏移
		calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天 
		String firstDay = DateFormatUtils.format(calendar.getTime(), PATTERN_DATE);
		return firstDay;
	}
	
	/**
	 * 获取以当前时间偏移月份的最后一天的日期
	 * @param monthDiff 偏移量：0~无偏移，1~递增一个月，-1~递减一个月
	 * @return yyyy-MM-dd日期格式字符串
	 * @author wy
	 */
	public static String getMonthLastDay(int monthDiff){
		Calendar calendar=Calendar.getInstance();//获取当前时间 
		calendar.add(Calendar.MONTH, monthDiff);//设置月份偏移
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));//最后一天
		String lastDay = DateFormatUtils.format(calendar.getTime(), PATTERN_DATE);
		return lastDay;
	}
	
	/**
	 * 获取指定月份的第一天的日期
	 * @param month 月份(1-12月)
	 * @return yyyy-MM-dd日期格式字符串
	 * @author wy
	 */
	public static String getDetermineMonthFirstDay(int month){
		Calendar calendar=Calendar.getInstance();//获取当前时间 
		calendar.set(Calendar.MONTH, month - 1);//设置月份
		calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天 
		String firstDay = DateFormatUtils.format(calendar.getTime(), PATTERN_DATE);
		return firstDay;
	}
	
	/**
	 * 获取指定月份的最后一天的日期
	 * @param month 月份(1-12月)
	 * @return yyyy-MM-dd日期格式字符串
	 * @author wy
	 */
	public static String getDetermineMonthLastDay(int month){
		Calendar calendar=Calendar.getInstance();//获取当前时间 
		calendar.set(Calendar.MONTH, month - 1);//设置月份
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));//最后一天
		String lastDay = DateFormatUtils.format(calendar.getTime(), PATTERN_DATE);
		return lastDay;
	}
	
	/**
	 * 获取以当前时间偏移月份的总天数
	 * @param monthDiff 偏移量：0~无偏移，1~递增一个月，-1~递减一个月
	 * @return 天数
	 * @author wy
	 */
	public static int getDaysOfMonthDiff(int monthDiff) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, monthDiff);//设置月份偏移
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取指定月份的总天数
	 * @param month 月份(1-12月)
	 * @return 天数
	 * @author wy
	 */
	public static int getDaysOfMonth(int month){
		Calendar calendar=Calendar.getInstance();//获取当前时间 
		calendar.set(Calendar.MONTH, month - 1);//设置月份
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
