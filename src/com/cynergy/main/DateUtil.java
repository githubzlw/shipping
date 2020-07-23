package com.cynergy.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 字符串转换成日期
	 * @param date
	 * @return date
	 */
	public static String date2Str(Date date) {
		return ft.format(date);

	}
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (Exception e) {
	    e.printStackTrace();
	   }
	   return date;
    }
	/**
	 * 根据当前时间得到前两周的时间
	 * @return
	 */
	public static String getTwoWeeksDate(){
        Calendar c = Calendar.getInstance();
        //过去两周
        c.setTime(new Date());
        c.add(Calendar.DATE, - 14);
        Date d = c.getTime();
        String date = format.format(d);
		return date;
	}
	/**
	 * 根据当前时间得到前两周的时间
	 * @return
	 */
	public static String getDate(int day){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, day);
        String date = format.format(c.getTime());
		return date;
	}

	/**
	 * 根据时间计算和当前时间差值
	 * @return
	 * @throws ParseException
	 */
	public static Boolean calExpires(String date) throws ParseException{
		long endDate = format.parse(date).getTime();
		long now = new Date().getTime();
		int days = (int) ((now - endDate)/(1000 * 60 * 60 * 24));
		return days>0?true:false;
	}
	/**
	 * 根据时间计算和当前时间差值
	 * @return
	 * @throws ParseException
	 */
	public static int calDifference(String date) throws ParseException{
		long endDate = format.parse(date).getTime();
		long now = new Date().getTime();
		int days = (int) ((now - endDate)/(1000 * 60 * 60 * 24));
		return days;
	}


	public static void main(String xp[]){
		System.out.print(getDate(-29));
	}
}
