package com.rosinka.tt.server.base.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilsServer
{
	/**
	 * Set hours, minutes, seconds and milliseconds to 0;
	 * 
	 * @param date
	 */
	public static Date lowerDate(Date date)
	{
		return lowerDate(date, 0);
	}

	/**
	 * Set minutes, seconds and milliseconds to 0; hours - 24
	 * 
	 * @param date
	 */
	public static Date upperDate(Date date)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 24);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Set hours, minutes, seconds, milliseconds to 0 and <br> <code>
	 *  	date.setDay(date.getDay() - countOfDays)
	 *  </code>
	 * 
	 * @param date
	 * @param countOfDays
	 * @return
	 */
	public static Date lowerDate(Date date, int countOfDays)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - countOfDays);

		return calendar.getTime();
	}

	/**
	 * Format date by pattern
	 * @param date
	 * @param format - pattern (dd:MM:yyyy... etc)
	 * @return string representation of date 
	 */
	public static String formatDate(Date date, String format)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(date);
	}

}
