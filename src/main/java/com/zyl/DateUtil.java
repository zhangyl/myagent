package com.zyl;



import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhou
 * @version 1.0
 * @className DateUtil
 * @description 时间工具
 * @date 2021/08/27 10:42
 **/

public class DateUtil {

    public static final String DATE_TIME = "YYYY-MM-dd HH:mm:ss";

    public static final String DATE = "yyyy-MM-dd";

    private static Pattern pattern = Pattern.compile("[0-9]*");

    /**
     * @param strDate 字符串时间
     * @return java.util.Date
     * @description 字符串转换为日期
     * @author zhou
     * @create 2021/8/27 10:47
     **/
    public static Date strToDateTime(String strDate, String format) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        //全是数字
        if(isNumeric(strDate)) {
            long timeStamp = Long.valueOf(strDate);
            DateTime dateTime = new DateTime(timeStamp);
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
            String dateTimeStr = dateTime.toString(formatter);
            return DateTime.parse(dateTimeStr, formatter).toDate();
        }
        //String 类型
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        return formatter.parseDateTime(strDate).toDate();
//        Date date = null;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//        try {
//            date = simpleDateFormat.parse(strDate);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//            //TODO: 日志
//        }
//
//        return date;

    }

    public static void main(String[] args) {
        {
            String s = "1666863400000";
            Date date = strToDateTime(s, "yyyy-MM-dd HH:mm:ss");
            System.out.println(date);
        }
        {
            String s = "1666863400000";
            Date date = strToDateTime(s, "yyyy-MM-dd");
            System.out.println(date);
        }
        {
            String s = "2022-12-12 02:08:33";
            Date date = strToDateTime(s, "yyyy-MM-dd HH:mm:ss");
            System.out.println(date);
        }
    }
    private static boolean isNumeric(String str){
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差天数,向上进位
     * @author zhou
     * @create 2021/8/28 21:17
     **/
    public static int diffDays(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double day = (1000 * 3600 * 24);
        return (int) Math.ceil(diff / day);
    }

    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差天数
     * @author zhou
     * @create 2021/8/28 21:17
     **/
    public static int diffDaysFloor(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double day = (1000 * 3600 * 24);
        return (int) (diff/day);
    }


    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差小时数,向上进位
     * @author zhou
     * @create 2021/8/29 17:39
     **/
    public static int diffHours(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double hour = (1000 * 3600);
        return (int) Math.ceil(diff / hour);
    }

    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差小时数
     * @author zhou
     * @create 2021/8/29 17:39
     **/
    public static int diffHoursFloor(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double hour = (1000 * 3600);
        return (int) (diff / hour);
    }

    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差分钟数,向上进位
     * @author zhou
     * @create 2021/8/29 17:39
     **/
    public static int diffMinutes(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double minute = (1000 * 60);
        return (int) Math.ceil(diff / minute);
    }

    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差分钟数
     * @author zhou
     * @create 2021/8/29 17:39
     **/
    public static int diffMinutesFloor(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double minute = (1000 * 60);
        return (int) (diff / minute);
    }

    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差秒数,向上进位
     * @author zhou
     * @create 2021/8/29 17:39
     **/
    public static int diffSeconds(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double seconds = (1000);
        return (int) Math.ceil(diff / seconds);
    }

    /**
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return java.lang.Integer
     * @description 返回相差秒数
     * @author zhou
     * @create 2021/8/29 17:39
     **/
    public static int diffSecondsFloor(Date startDate, Date endDate) {
        double diff = (endDate.getTime() - startDate.getTime());
        double seconds = (1000);
        return (int) (diff / seconds);
    }
    /**
     * @param now  起始日期
     * @param days 天数
     * @return java.util.Date
     * @description 日期增加
     * @author zhou
     * @create 2021/8/28 21:55
     **/
    public static Date addDays(Date now, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * @description 时间转字符串
     * @author zhou
     * @create 2021/9/3 15:15
     * @param now 当前时间
     * @return java.lang.String
     **/
    public static String dateToStr(Date now){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME);
        return simpleDateFormat.format(now);
    }

}
