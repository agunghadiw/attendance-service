/**
 * 
 */
package com.mpe.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Mas AH
 * @create on Jun 23, 2015 5:38:15 PM
 */
public class CommonUtil {
	
	public static String getStringFromTimestamp(Timestamp input, String format) {
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		Date date = new Date(input.getTime());
    		return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getStringFromDate(Date input, String format) {
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		return sdf.format(input);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getStringFromTime(Time input, String format) {
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		return sdf.format(input);
		} catch (Exception e) {
			return null;
		}
	}
    
    public static Calendar getCalendarFromString(String input, String format) {
    	Calendar calendar = new GregorianCalendar();
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		calendar.setTime(sdf.parse(input));
		} catch (Exception e) {
			return null;
		}
    	return calendar;
    }
    
    public static Date getDateFromString(String input, String format) {
    	Calendar calendar = new GregorianCalendar();
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		calendar.setTime(sdf.parse(input));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return calendar.getTime();
    }
    
    public static Time getTimeFromString(String input, String format) {
    	Calendar calendar = new GregorianCalendar();
    	java.sql.Time time = new java.sql.Time(calendar.getTime().getTime());
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		calendar.setTime(sdf.parse(input));
    		time = new java.sql.Time(calendar.getTime().getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return time;
    }
    
    public static java.sql.Timestamp getTimestampFromString(String input, String format) {
    	Calendar calendar = new GregorianCalendar();
    	java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		calendar.setTime(sdf.parse(input));
    		timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return timestamp;
    }

}
