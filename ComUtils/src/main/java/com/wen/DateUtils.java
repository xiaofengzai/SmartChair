package com.wen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wenfeng on 2017/10/24.
 */
public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        return calendar;
    }

    public static String getCurrentFormat() {
        return sdf.format(new Date());
    }

    public static String getCurrentDate() {
        Long currentTime=System.currentTimeMillis();
        Date date=new Date(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATETIME);
        return sdf.format(date);
    }
}
