package com.aiteu.reading.core.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {
	
	public static String getOffsetDayOffNow(int offset){
		Calendar c= Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, offset);
		return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(c.getTime());
	}
}
