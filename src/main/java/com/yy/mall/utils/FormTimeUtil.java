package com.yy.mall.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public class FormTimeUtil {

	public static Date strToDate(String time,String format) throws ParseException {
		DateFormat dateFormat=new SimpleDateFormat(format);

		Date date = dateFormat.parse(time);
		return date;

	}
	public static String dateToStr(Date date,String format) throws ParseException {
		DateFormat dateFormat=new SimpleDateFormat(format);


	    String	format1= dateFormat.format(date);
		return format1;

	}

	public static void main(String[] args) {
		try {
			String s = dateToStr(new Date(), "yyyy-MM-dd hh:mm:ss");
			System.out.println(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
