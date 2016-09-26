package com.parking.service.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 5/8/15
 * Time: 10:31 AM
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private int month;
    private int year;
    private Calendar calendar;

    public static final String dateFormat = "yyyy-MM-dd hh:mm:SS";

    public static final String intFormat = "yyyyMMdd";

    public static String getDate() {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        return sf.format(new Date());
    }

    public static int formatMaxDateAsInt(Calendar calendar){

        return Integer.valueOf(max(calendar,false));

    }

    public static int formatDateAsInt(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat(intFormat);

        Date d = date(gc(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
        logger.info(d.toString());
        String f = format.format(d);

        return Integer.valueOf(f);

    }

    public static int formatMonthAsInt(int date){
        String s = String.valueOf(date);
        return Integer.valueOf(s.substring(0, s.length() - 2));
    }


    public static Date formatDatefromString(String date){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try{
              return format.parse(date);
        }catch (ParseException pe){
              logger.error(pe.getMessage(), pe);
        }
        return null;
    }


    private static String format(int day,int month,int year,boolean isString) {

        SimpleDateFormat format = new SimpleDateFormat(isString?dateFormat:intFormat);
        return format.format(date(gc(day,month,year)));
    }

    private static Date date(GregorianCalendar c)
    {
        return new java.util.Date(c.getTime().getTime());

    }

    private static GregorianCalendar gc(int day,int month, int year)
    {
        return new GregorianCalendar(year, month, day);
    }


    public static String min(Calendar calendar,boolean isString)
    {
        return format(calendar.getActualMinimum(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR),isString);
    }

    public static String max(Calendar calendar,boolean isString)
    {
        return format(calendar.getActualMaximum(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),isString);
    }


    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date a = format.parse("2016-08-27 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(DateUtils.formatMonthAsInt(DateUtils.formatDateAsInt(cal)));


//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH,8  );
//        System.out.println(DateUtils.min(cal,true));
//        System.out.println(DateUtils.max(cal,false));
//
//        System.out.println(DateUtils.formatDateAsInt(cal));
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
