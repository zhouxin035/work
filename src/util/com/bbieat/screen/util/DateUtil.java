package com.bbieat.screen.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 时间日期工具方法
 */
public class DateUtil extends DateUtils {

    public static final SimpleDateFormat DATE_FORMAT_FULL         = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT_MEDIUM       = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat DATE_FORMAT_SHORT        = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_FORMAT_MONTH        = new SimpleDateFormat("yyyy-MM");

    public static final SimpleDateFormat DATE_FORMAT_YEAR         = new SimpleDateFormat("yyyy");

    public static final SimpleDateFormat DATE_FORMAT_YM           = new SimpleDateFormat("yyMM");

    public static final SimpleDateFormat DATE_FORMAT_MEDIUM_BBS   = new SimpleDateFormat("MM-dd HH:mm");

    public static final SimpleDateFormat DATE_FORMAT_SHORT_BBS    = new SimpleDateFormat("MM-dd");

    public static final SimpleDateFormat DATE_FORMAT_SHORT_BBSFEN = new SimpleDateFormat("HH:mm");

    public static final SimpleDateFormat DATE_FORMAT_FULL_ZH      = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    public static final SimpleDateFormat DATE_FORMAT_MEDIUM_ZH    = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

    public static final SimpleDateFormat DATE_FORMAT_SHORT_ZH     = new SimpleDateFormat("yyyy年MM月dd日");

    public static final SimpleDateFormat DATE_FORMAT_DAY_ZH       = new SimpleDateFormat("dd日");

    /**
     * 根据传进来的format格式 以及 需要格式化的String类型的字符串 转化为日期型值 String ---- Date
     * 
     * @param aMask format对象
     * @param strDate 需要格式化的string类型字符串
     * @return 日期型
     */
    public static final Date String2Date(String aMask, String strDate) {
        SimpleDateFormat df = new SimpleDateFormat(aMask);
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return date;
    }

    /**
     * 得到当前日期
     * 
     * @return String
     */
    public static String getCurrentlyDate() {
        Calendar calendar = Calendar.getInstance();
        return getCalendarStr(calendar);
    }

    /**
     * 从calendar获得字符串表达形式
     * 
     * @param date
     * @param calendar
     * @return
     */
    private static String getCalendarStr(Calendar calendar) {
        StringBuffer date = new StringBuffer();
        date.append(calendar.get(Calendar.YEAR));
        date.append("-");
        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            date.append("0" + (calendar.get(Calendar.MONTH) + 1));
        } else {
            date.append("" + (calendar.get(Calendar.MONTH) + 1));
        }
        date.append("-");
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            date.append("0" + calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            date.append("" + calendar.get(Calendar.DAY_OF_MONTH));
        }
        return date.toString();
    }

    /**
     * 把日期型格式化为时间型 返回为string类型的数值
     * 
     * @param theTime the current time
     * @return the current date/time
     */
    public static String Date2String(String aMask, Date theTime) {
        String returnValue = "";
        SimpleDateFormat df = new SimpleDateFormat(aMask);
        returnValue = df.format(theTime);
        return returnValue;
    }

    /**
     * 得到当前时间 yyyy-MM-dd HH:mm:ss
     * 
     * @param type
     * @param style
     * @return
     */
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return DATE_FORMAT_FULL.format(calendar.getTime());
    }

    /**
     * 日期加减操作
     * 
     * @param source 源日期
     * @param field 项（日，月，年)
     * @param num 数量 + 为加，-为减
     * @return
     */
    public static Date dateRoler(Date source, int field, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(source);
        c.add(field, num);
        return c.getTime();
    }

    /**
     * 日期加减
     * 
     * @param 所给日期
     * @param 需要加减天数
     * @return 返回日期类型数值
     */
    public static String addDays(String date, int days) {
        return Date2String("yyyy-MM-dd", dateRoler(String2Date("yyyy-MM-dd", date), Calendar.DATE, days));
    }

    /**
     * 月份加减
     * 
     * @param days
     * @return
     */
    public static String addMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + months);
        return DATE_FORMAT_SHORT.format(cal.getTime());
    }

    /**
     * 月份加减
     * 
     * @param date
     * @param days
     * @return
     */
    public static String addMonths(String date, int months) {
        return Date2String("yyyy-MM", dateRoler(String2Date("yyyy-MM", date), Calendar.MONTH, months));
    }

    /**
     * 取得起止日期之间的间隔天数
     * 
     * @param startday
     * @param endday
     * @return
     * @throws Exception
     */
    public static int getDays(String start, String end) {
        String fmt = "yyyy-MM-dd";
        long ei = 0;
        // 开始时间和结束时间(Date型)
        Calendar startcal = Calendar.getInstance();
        Calendar endcal = Calendar.getInstance();
        startcal.setTime(String2Date(fmt, start));
        endcal.setTime(String2Date(fmt, end));

        // 分别得到两个时间的毫秒数
        long sl = startcal.getTimeInMillis();
        long el = endcal.getTimeInMillis();

        ei = el - sl;
        // 根据毫秒数计算间隔天数
        return (int) (ei / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取当日日期
     * 
     * @return
     */
    public static String getToday() {
        Calendar cal = Calendar.getInstance();
        return DATE_FORMAT_SHORT.format(cal.getTime());
    }

    /**
     * 获取yymm 年月 用于拼接表名
     * 
     * @return
     */
    public static String getYm() {
        Calendar cal = Calendar.getInstance();
        return DATE_FORMAT_YM.format(cal.getTime());
    }

    /**
     * 获取昨日日期
     * 
     * @return
     */
    public static String getYesterday() {
        return addDays(getToday(), -1);
    }

    /**
     * 获取明日日期
     * 
     * @return
     */
    public static String getTomorrow() {
        return addDays(getToday(), 1);
    }

    /**
     * 获取前一周日期
     * 
     * @return
     */
    public static String getLastWeek() {
        return addDays(getToday(), -7);
    }

    /**
     * 获取后一周日期
     * 
     * @return
     */
    public static String getNextWeek() {
        return addDays(getToday(), 7);
    }

    /**
     * 获取前一月 格式为yyyy-mm
     * 
     * @return
     */
    public static String getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return DATE_FORMAT_MONTH.format(calendar.getTime());
    }

    public static String getNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return DATE_FORMAT_MONTH.format(calendar.getTime());
    }

    /**
     * 根据当日获取前一月当前日期 yyyy-mm-dd
     * 
     * @return
     */
    public static String getLastMonthDay() {
        return addMonths(getToday(), -1);
    }

    /**
     * 根据当日获取后一月当前日期 yyyy-mm-dd
     * 
     * @return
     */
    public static String getNextMonthDay() {
        return addMonths(getToday(), 1);
    }

    /**
     * 根据某日获取上月同日 yyyy-mm-dd
     * 
     * @return
     */
    public static String getLastMonthDay(String day) {
        return Date2String("yyyy-MM-dd", dateRoler(String2Date("yyyy-MM-dd", day), Calendar.MONTH, -1));
    }

    /**
     * 根据某日获取后月同日 yyyy-mm-dd
     * 
     * @return
     */
    public static String getNextMonthDay(String day) {
        return Date2String("yyyy-MM-dd", dateRoler(String2Date("yyyy-MM-dd", day), Calendar.MONTH, 1));
    }

    /**
     * 得到当前月的总天数
     * 
     * @param date
     * @param day
     * @return
     */
    public static int getCountDays() {
        Calendar cld = Calendar.getInstance();
        return cld.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到两个时间的间隔分钟
     */
    public static long getMinuteDiff(Date startDate, Date endDate) {
        long startTime = 0;
        if (null != startDate) {
            startTime = startDate.getTime();
        }
        long endTime = 0;
        if (null != endDate) {
            endTime = endDate.getTime();
        }
        long minute = (endTime - startTime) / (1000 * 60);
        return minute;
    }

    @SuppressWarnings("static-access")
    public static String getGwRwSbjzsj(String sbjgsjdw, int addMin) {
        String re = "";
        int key = Integer.valueOf(sbjgsjdw);
        switch (key) {
            case 0: { // 分
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(cal.MINUTE, addMin); // 当前加基准分和随机分
                re = DATE_FORMAT_FULL.format(cal.getTime());
                break;
            }
            case 1: { // 时
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(cal.HOUR, 1);// 下小时
                cal.add(cal.MINUTE, addMin);
                re = DATE_FORMAT_FULL.format(cal.getTime());
                break;
            }
            case 2: { // 日
                re = getTomorrow() + " " + minTools(addMin); // 明天
                break;
            }
            case 3: { // 月
                re = getNextMonthDay() + " " + minTools(addMin); // 下个月
                break;
            }
            default: {
                break;
            }
        }
        ;
        return re;
    }

    private static String minTools(int addmin) {
        String fm = "";
        if (addmin < 10) {
            fm = "00:" + "0" + String.valueOf(addmin) + ":00";
        } else {
            fm = "00:" + String.valueOf(addmin) + ":00";
        }
        return fm;
    }

    public static int getSbjzsj(int sbjzsj, int sbsjz) {
        return sbjzsj + (int) (Math.random() * Double.valueOf(sbsjz)); // 基准分和随机分
    }

    /**
     * 返回起止日期的小时数组(96点)--画图用
     * 
     * @param startday
     * @param endday
     * @return
     * @throws ParseException
     */
    public static String[] getTime96(String startday, String endday) throws ParseException {
        String[] returnArray = null;
        String[] hours = new String[24];
        String[] minute = { "00", "15", "30", "45" };

        for (int i = 0; i < hours.length; i++) {
            if (i < 10) {
                hours[i] = "0" + String.valueOf(i);
            } else {
                hours[i] = String.valueOf(i);
            }
        }
        int days = getDays(startday, endday) + 1;
        returnArray = new String[days * 96];
        int k = 0;
        String day = startday;
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < hours.length; j++) {
                for (int m = 0; m < minute.length; m++) {
                    returnArray[k] = day + " " + hours[j] + ":" + minute[m];
                    k++;
                }
            }
            day = addDays(day, 1);
        }

        return returnArray;

    }

    /**
     * 得到当前月份，格式为：YYYY-MM
     * 
     * @return String
     */
    public static String getCurrentlyMonth() {
        StringBuffer date = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        date.append(calendar.get(Calendar.YEAR));
        date.append("-");
        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            date.append("0" + (calendar.get(Calendar.MONTH) + 1));
        } else {
            date.append("" + (calendar.get(Calendar.MONTH) + 1));
        }
        return date.toString();
    }

    /**
     * 取得相差月份的日期
     * 
     * @param diff 距今相差的月份
     * @return YYYY-MM-DD
     */
    public static String getDiffMonth(int diff) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + diff);
        return DATE_FORMAT_SHORT.format(cal.getTime());
    }

    /**
     * 获取指定月的天数 日期格式：YYYY-MM
     * 
     * @return
     */
    public static int getDaysInMonth(String month) {
        int count = 0;
        Calendar cal = new GregorianCalendar();
        try {
            Date date = DateUtil.DATE_FORMAT_MONTH.parse(month);
            cal.setTime(date);
            count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Calendar getCalendr(String time, String format) {
        Calendar cld = Calendar.getInstance();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(time);
            cld.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cld;
    }

    /**
     * 获得某日前N日的日期
     * 
     * @param day
     * @param span
     * @return
     * @throws ParseException
     */
    public static String getBeforeDay(String day, int span) {
        String nday = "";
        try {
            java.util.Date date = DATE_FORMAT_SHORT.parse(day);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, -span);
            nday = DATE_FORMAT_SHORT.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nday;
    }

    /**
     * 获取某日去年同期的日期,如果是闰年2月29日，那么去年同期为2月28日
     * 
     * @param day 日期格式为YYYY-MM-DD
     * @return 日期格式为YYYY-MM-DD
     */
    public static String getLastYearCurrDay(String day) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(DATE_FORMAT_SHORT.parse(day));
            cal.add(Calendar.YEAR, -1);
            return DATE_FORMAT_SHORT.format(cal.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据当日获取去年当前日期 yyyy-mm-dd
     * 
     * @return
     */
    public static String getLastYearDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        return DATE_FORMAT_SHORT.format(cal.getTime());
    }

    /**
     * @Description:根据传入的日期，获取传入日期所在月份的最后一天
     * @Author:
     * @Since:
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getLastDayOfMonth(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(DATE_FORMAT_SHORT.parse(date));
            final int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date lastDate = cal.getTime();
            lastDate.setDate(lastDay);
            String resultDate = DATE_FORMAT_SHORT.format(lastDate);
            return resultDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 算出某个日期的所在周的周一的日期
     */
    public static String getMondayByWeek(String day) {
        try {
            Date date = DATE_FORMAT_SHORT.parse(day);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int datForWeek = 7;
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                datForWeek = 7;
            } else {
                datForWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1 - datForWeek);
            return DATE_FORMAT_SHORT.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return returnValue;
    }

    public static String getDatePattern() {
        String defaultDatePattern = null;
        try {
            defaultDatePattern = "yyyy-MM-dd";
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy";
        }
        return defaultDatePattern;
    }

    public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return date;
    }

    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;
        try {
            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }

    /**
     * 获得某月前N月的月份
     * 
     * @param month
     * @param span
     * @return
     */
    public static String getBeforeMonth(String month, int span) {
        if (month == null || "".equals(month.trim())) return "";
        String beforeMonth = "";
        try {
            java.util.Date date = DATE_FORMAT_MONTH.parse(month.trim());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -span);
            beforeMonth = DATE_FORMAT_MONTH.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beforeMonth;
    }

    /**
     * 获得某年前年的年份
     * 
     * @param month
     * @return
     */
    public static String getBeforeYear(String year) {
        if (year == null || "".equals(year)) return "";
        String beforeYear = "";
        try {
            java.util.Date date = DATE_FORMAT_YEAR.parse(year);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, -1);
            beforeYear = DATE_FORMAT_YEAR.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beforeYear;
    }

    /**
     * 获得某年前N年的年份
     * 
     * @param month
     * @param span
     * @return
     */
    public static String getBeforeYear(String year, int span) {
        if (year == null || "".equals(year)) return "";
        String beforeYear = "";
        try {
            java.util.Date date = DATE_FORMAT_YEAR.parse(year);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, -span);
            beforeYear = DATE_FORMAT_YEAR.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beforeYear;
    }

    /**
     * 获得某月前月月份
     * 
     * @param month
     * @return
     */
    public static String getBeforeMonth(String month) {
        if (month == null || "".equals(month)) return "";
        String beforeMonth = "";
        try {
            java.util.Date date = DATE_FORMAT_MONTH.parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            beforeMonth = DATE_FORMAT_MONTH.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beforeMonth;
    }

    public static String DateToString(Date source) {
        return DATE_FORMAT_SHORT.format(source);
    }

    // 验证HH:MM
    public static boolean validTime(String time) {
        boolean flg = true;
        String[] tt = time.split(":");
        try {
            if (tt.length == 2) {
                int hh = Integer.parseInt(tt[0]);
                int mm = Integer.parseInt(tt[1]);
                if (hh < 0 || hh > 23) {
                    flg = false;
                }
                if (mm < 0 || hh > 59) {
                    flg = false;
                }
                if (mm == 0 || mm == 30) {

                } else if (mm == 59 && hh == 23) {

                } else {
                    flg = false;
                }
            } else {
                flg = false;
            }
        } catch (Exception e) {
            flg = false;
        }
        return flg;
    }

    public static boolean isDateBefore(String date1, String date2, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(date1).getTime() < df.parse(date2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * String---Calendar
     * 
     * @param dateString
     * @return Calendar
     */
    public synchronized static Calendar string2Calendar(String dateString) {
        int year = 0, month = 0, date = 0, hour = 0, min = 0, sec = 0, myLen = 0;
        if (dateString == null) {
            return null;
        }
        myLen = dateString.length();
        if (myLen == 10) {
            year = Integer.parseInt(dateString.substring(0, 4));
            month = Integer.parseInt(dateString.substring(5, 7)) - 1;
            date = Integer.parseInt(dateString.substring(8));
        }
        if (myLen == 8 || myLen == 14) {
            year = Integer.parseInt(dateString.substring(0, 4));
            month = Integer.parseInt(dateString.substring(4, 6)) - 1;
            date = Integer.parseInt(dateString.substring(6, 8));
            if (myLen == 14) {
                dateString = dateString.substring(8);
            }
        }

        if (dateString.length() == 6) {
            hour = Integer.parseInt(dateString.substring(0, 2));
            min = Integer.parseInt(dateString.substring(2, 4));
            sec = Integer.parseInt(dateString.substring(4, 6));
        } else if (dateString.length() == 4) {
            hour = Integer.parseInt(dateString.substring(0, 2));
            min = Integer.parseInt(dateString.substring(2, 4));
        }

        Calendar calendarObj = Calendar.getInstance();
        if (myLen == 8 || myLen == 10) {
            calendarObj.set(year, month, date, hour, min, sec);
            calendarObj.set(Calendar.MILLISECOND, 0);
            if (year != calendarObj.get(Calendar.YEAR) || month != (calendarObj.get(Calendar.MONTH))
                || date != calendarObj.get(Calendar.DATE)) {
                return null;
            }
        } else if (myLen == 4) {
            calendarObj.set(Calendar.HOUR_OF_DAY, hour);
            calendarObj.set(Calendar.MINUTE, min);
            if (hour < 0 || hour >= 24 || min < 0 || min >= 60 || sec < 0 || sec >= 60) {
                return null;
            }
        } else if (myLen == 6) {
            calendarObj.set(Calendar.HOUR_OF_DAY, hour);
            calendarObj.set(Calendar.MINUTE, min);
            calendarObj.set(Calendar.SECOND, sec);
            if (hour < 0 || hour >= 24 || min < 0 || min >= 60 || sec < 0 || sec >= 60) {
                return null;
            }
        } else if (myLen == 14) {
            calendarObj.set(year, month, date, hour, min, sec);
            if (year != calendarObj.get(Calendar.YEAR) || month != (calendarObj.get(Calendar.MONTH))
                || date != calendarObj.get(Calendar.DATE) || hour != calendarObj.get(Calendar.HOUR_OF_DAY)
                || min != calendarObj.get(Calendar.MINUTE) || sec != calendarObj.get(Calendar.SECOND)) {
                return null;
            }
        } else {
            return null;
        }
        return calendarObj;
    }

    /**
     * 取得当前时间 yyyy-MM-dd hh24:mi:ss
     * 
     * @return
     */
    public static String getFulltime() {
        Calendar cal = Calendar.getInstance();
        return DATE_FORMAT_FULL.format(cal.getTime());
    }

    /**
     * 得到当前年份，格式为：YYYY
     * 
     * @return String
     */
    public static String getCurrentlyYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 比较两个日期的大小,日期格式为YYYY-MM-DD,oneDate<twoDate返回true
     * 
     * @return
     */
    public static boolean compartDate(String oneDate, String twoDate) {
        String oneYear = oneDate.substring(0, 4);
        String oneMonth = oneDate.substring(5, 7);
        String oneDay = oneDate.substring(8, 10);
        String twoYear = twoDate.substring(0, 4);
        String twoMonth = twoDate.substring(5, 7);
        String twoDay = twoDate.substring(8, 10);
        if (Integer.parseInt(oneYear) < Integer.parseInt(twoYear)) {
            return true;
        } else {
            if (Integer.parseInt(oneYear) == Integer.parseInt(twoYear)) {
                if (Integer.parseInt(oneMonth) < Integer.parseInt(twoMonth)) {
                    return true;
                } else {
                    if (Integer.parseInt(oneMonth) == Integer.parseInt(twoMonth)) {
                        if (Integer.parseInt(oneDay) < Integer.parseInt(twoDay)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * 月份或者日期所在月份的最后一天的日期数(28 29 30 31 )
     * 
     * @param day (eg: 2015-06 2015-06-01)
     * @return
     */
    public static int lastdayNumOfMon(String day) {
        if (StringUtil.isEs(day)) {
            day = getToday();
        }
        day = day.substring(0, 7);
        day = getLastDayOfMonth(day + "-01");
        day = day.substring(8);

        return Integer.valueOf(day);
    }

    /**
     * 得到一个日期所在月的第一天和最后一天
     * 
     * @param sj 时间 格式YYYY-MM-DD
     * @return
     */
    public static String[] getMonthFirsAndLast(String sj) {
        String[] dayResult = { "", "" };
        String strtmp = sj;
        if (sj.length() >= 7) {
            strtmp = strtmp.substring(0, 7);
        }
        Calendar lastCal = Calendar.getInstance();
        // 得到给日期所在月的第一天
        dayResult[0] = strtmp + "-01";
        // 得到给日期所在的月最后一天
        java.sql.Date curDate = java.sql.Date.valueOf(dayResult[0]);
        lastCal.setTime(curDate);
        lastCal.add(Calendar.MONTH, 1);
        lastCal.add(Calendar.DATE, -1);
        String dayStr = lastCal.get(Calendar.DATE) + "";
        if (dayStr.length() == 1) {
            dayStr = "0" + dayStr;
        }
        dayResult[1] = strtmp + "-" + dayStr;

        return dayResult;
    }

    /* 带分秒的时间格式化 */
    public static String TIME_LONG       = "yyyy-MM-dd HH:mm:ss";

    /* 不带秒的时间格式 */
    public static String TIME_NO_SEC     = "yyyy-MM-dd HH:mm";

    /* 只有日期的时间格式化 */
    public static String TIME_SHORT      = "yyyy-MM-dd";
    public static String TIME_SHORT_CN   = "yyyy年MM月dd日";

    public static String DATE_AND_MONTH  = "yyyy-MM-dd HH";

    /* 只有日期的年月格式化 */
    public static String YEAR_MONTH      = "yyMM";

    /* 只有日期的年格式化 */
    public static String YEAR            = "yy";

    public static String YEAR_LONG       = "yyyy";

    /* 只有日期的小时格式化 */
    public static String HOUR            = "HH";

    /* 只格式化小时分钟 */
    public static String HOUR_MIN        = "HH:mm";

    public static String DAY_HOUR_MIN_CN = "dd日HH:mm";

    /* 只格式化日期的天 */
    public static String DAY             = "dd";

    public static String MONTH           = "MM";

    public static String YEARMONTH       = "yyyyMM";

    public static String MONTHTIME       = "yyyy-MM";

    public static String MONTHTIME_CN    = "yyyy年MM月";

    public static String currentDate() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return f.format(c.getTime()).toString();
    }

    /**
     * 得到每月的最大日期数字
     *
     * @param date
     * @return
     */
    public static int getMaxMonthDay(Date date) {
        Calendar cr = Calendar.getInstance();
        cr.setTime(date);
        cr.set(Calendar.DAY_OF_MONTH, 1);
        cr.roll(Calendar.DAY_OF_MONTH, -1);
        return cr.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期按照HH:mm格式转化字符串
     *
     * @param date
     * @return
     */
    public static String DateToHHMMStr(Date date) {
        return DateToStr(date, HOUR_MIN);
    }

    /**
     * 判断方案类型，转换年，月的表名
     *
     * @param date
     * @param schemeId
     * @return
     */
    public static String getDateStrBySchemeId(Date date, Short schemeId) {
        if (CircleTypeEnum.MONTH.getShortValue().equals(schemeId)) {
            return DateUtil.DateToStr(date, DateUtil.YEAR);
        } else {
            return DateUtil.DateToStr(date, DateUtil.YEAR_MONTH);
        }
    }

    /**
     * 日期按照HH格式转化字符串
     *
     * @param date
     * @return
     */
    public static String DateToHHStr(Date date) {
        return DateToStr(date, HOUR);
    }

    /**
     * 日期按照dd格式转化字符串
     *
     * @param date
     * @return
     */
    public static String DateToDayStr(Date date) {
        return DateToStr(date, DAY);
    }

    /**
     * 日期按照yy格式转换字符串
     *
     * @param date
     * @return
     */
    public static String DateToYYStr(Date date) {
        return DateToStr(date, YEAR);
    }

    /**
     * 日期按照yyMM格式转换字符串
     *
     * @param date
     * @return
     */
    public static String DateToYYMMStr(Date date) {
        return DateToStr(date, YEAR_MONTH);
    }

    public static String DateToYYYYMMStr(Date date) {
        return DateToStr(date, MONTHTIME);
    }

    /**
     * 日期按照yyyy-MM-dd HH:mm:ss格式格式转换字符串
     *
     * @param date
     * @param Format
     * @return
     */
    public static String DateToLongStr(Date date) {
        return DateToStr(date, TIME_LONG);
    }

    /**
     * 日期按照yyyy-MM-dd格式格式转换字符串
     *
     * @param date
     * @return
     */
    public static String DateToShortStr(Date date) {
        return DateToStr(date, TIME_SHORT);
    }

    /**
     * 按照指定格式格式转换字符串
     *
     * @param date
     * @param Format
     * @return
     */
    public static String DateToStr(Date date, String Format) {
        if (date == null) return "";
        SimpleDateFormat formater = new java.text.SimpleDateFormat(Format);
        return formater.format(date);
    }

    /**
     * 日期按照yyyy-MM-dd HH:mm格式转化字符串
     *
     * @param date
     * @return
     */
    public static String DateToNosecStr(Date date) {
        return DateToStr(date, TIME_NO_SEC);
    }

    /**
     * 指定的格式yyyy-MM-dd HH:mm:ss的字符串按照转换为日期
     *
     * @param dateStr
     * @param Format
     * @return
     */
    public static Date LongStrToDate(String dateStr) {
        if (dateStr.length() > 16) {
            return StrToDate(dateStr, TIME_LONG);
        } else if (dateStr.length() > 13) {
            return StrToDate(dateStr, TIME_NO_SEC);
        } else if (dateStr.length() > 9) {
            return StrToDate(dateStr, TIME_SHORT);
        } else if (dateStr.length() > 6) {
            return StrToDate(dateStr, MONTHTIME);
        } else if (dateStr.length() > 3) {
            return StrToDate(dateStr, "yyyy");
        } else {
            return null;
        }
    }

    /**
     * 指定的格式yyyy-MM-dd的字符串按照转换为日期
     *
     * @param dateStr
     * @param Format
     * @return
     */
    public static Date ShortStrToDate(String dateStr) {
        return StrToDate(dateStr, TIME_SHORT);
    }

    /**
     * 指定的格式yyyy-MM-dd HH:mm的字符串按照转换为日期
     *
     * @param dateStr
     * @return
     */
    public static Date NosecStrToDate(String dateStr) {
        return StrToDate(dateStr, TIME_NO_SEC);
    }

    /**
     * 指定的格式的字符串按照转换为日期
     *
     * @param dateStr
     * @param Format
     * @return
     */
    public static Date StrToDate(String dateStr, String Format) {
        SimpleDateFormat formater = new java.text.SimpleDateFormat(Format);
        try {
            return formater.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到当前时间的开始的第一年
     *
     * @param date
     * @return
     */
    public static Date getFirstYear(Date date) {
        if (date != null) {
            Calendar cr = Calendar.getInstance();
            cr.setTime(date);
            cr.set(Calendar.MONDAY, 0);
            cr.set(Calendar.DAY_OF_MONTH, 1);
            cr.set(Calendar.HOUR_OF_DAY, 0);
            cr.set(Calendar.MINUTE, 0);
            cr.set(Calendar.SECOND, 0);
            cr.set(Calendar.MILLISECOND, 0);
            return cr.getTime();
        }

        return date;
    }

    /**
     * 计算两个任意时间中间的间隔天数
     *
     * @param startday
     * @param endday
     * @return
     */
    public static int getIntervalDays(Date startday, Date endday) {
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60 * 24));
    }

    public static int getIntervalHours(Date startday, Date endday) {
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60));
    }

    public static int getIntervalMinutes(Date startday, Date endday) {
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60));
    }

    /**
     * 得到date的 小时数字
     *
     * @param date
     * @return
     */
    public static int getHourValue(Date date) {
        Calendar cr = Calendar.getInstance();
        cr.setTime(date);
        return cr.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinuteValue(Date date) {
        Calendar cr = Calendar.getInstance();
        cr.setTime(date);
        return cr.get(Calendar.MINUTE);
    }

    /**
     * 得到date的日数字
     *
     * @param date
     * @return
     */
    public static int getDayValue(Date date) {
        Calendar cr = Calendar.getInstance();
        cr.setTime(date);
        return cr.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到date的月数字
     *
     * @param date
     * @return
     */
    public static int getMonthValue(Date date) {
        Calendar cr = Calendar.getInstance();
        cr.setTime(date);
        return cr.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到date的年数字
     *
     * @param date
     * @return
     */
    public static int getYearValue(Date date) {
        Calendar cr = Calendar.getInstance();
        cr.setTime(date);
        return cr.get(Calendar.YEAR);
    }

    /**
     * 得到每个月的第一天
     *
     * @param date 传入的需要比较的时间
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.set(Calendar.HOUR, 0);
        a.set(Calendar.MINUTE, 0);
        a.set(Calendar.SECOND, 0);
        return a.getTime();
    }

    /**
     * 得到每个月的最后一天
     *
     * @param date 传入的需要比较的时间
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.set(Calendar.DATE, 1); // 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        a.set(Calendar.HOUR, 0);
        a.set(Calendar.MINUTE, 0);
        a.set(Calendar.SECOND, 0);
        return a.getTime();
    }

    public static int getMondayPlus(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    public static Boolean isWorkDay(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 || dayOfWeek == 7) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 得到某个时间的星期1的日期
     *
     * @param date
     * @return
     */
    public static Date getMonday(Date date) {
        int mondayPlus = DateUtil.getMondayPlus(date);
        Date value = DateUtils.addDays(date, mondayPlus);
        return value;
    }

    /**
     * 得到某个时间的星期7的日期
     *
     * @param date
     * @return
     */
    public static Date getSunday(Date date) {
        Date value = DateUtil.getMonday(date);
        return DateUtils.addDays(value, 6);
    }

    /**
     * 得到季度开始时间
     *
     * @param date
     * @return
     */
    public static Date getQuarter(Date date) {
        int month = DateUtil.getMonthValue(date);
        Date firstYear = DateUtil.getFirstYear(date);
        if (month < 4) {
            return firstYear;
        } else if (month < 7) {
            return DateUtils.addMonths(firstYear, 3);
        } else if (month < 10) {
            return DateUtils.addMonths(firstYear, 6);
        } else {
            return DateUtils.addMonths(firstYear, 9);
        }
    }

    /**
     * 得到季度结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndQuarter(Date date) {
        int month = DateUtil.getMonthValue(date);
        Date firstYear = DateUtil.getFirstYear(date);
        if (month < 4) {
            return DateUtils.addMonths(firstYear, 2);
        } else if (month < 7) {
            return DateUtils.addMonths(firstYear, 5);
        } else if (month < 10) {
            return DateUtils.addMonths(firstYear, 8);
        } else {
            return DateUtils.addMonths(firstYear, 11);
        }
    }

    /**
     * 得到当前季度季度
     *
     * @param date
     * @return 1,2,3,4
     */
    public static int getQuarterNumber(Date date) {
        int month = DateUtil.getMonthValue(date);
        if (month < 4) {
            return 1;
        } else if (month < 7) {
            return 2;
        } else if (month < 10) {
            return 3;
        } else {
            return 4;
        }
    }

    public static Date getLastWorkDay(Date date) {
        Date value = DateUtil.addDays(date, -1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 || dayOfWeek == 7) {
            return getLastWorkDay(value);
        }
        return value;
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DATE, 0);
        return calendar.getTime();
    }

    public static Date getLastMonthDayHourOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 11);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    // 月25日
    public static Date get25Day(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getFirstHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getPreHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, getHourValue(date) - 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getSomeHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getSomeMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getSomeMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, month + getMonthValue(date) - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, getHourValue(date));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();

    }

    public static Date getSomeDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, days + getDayValue(date));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastMonthOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 11);
        return calendar.getTime();
    }

    public static Date getFirstMonthOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);

        return calendar.getTime();
    }

    public static Date getPreMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, date.getMonth() + 1);
        return calendar.getTime();
    }

    /**
     * 计算日或者月时段,计算2个日期间的时段，用formatStr格式化,把结果放入list 不改变外面传入的formatStr格式化
     *
     * @param beginDate
     * @param endDate
     * @param formatStr
     * @param calenderConstants Calendar.DAY_OF_MONTH等对应的整形
     * @return
     */
    public static void main(String[] args) {
        System.out.println(DateUtil.getDateBetweenEx(LongStrToDate("2012-08-11 10:10:11"),
                                                     LongStrToDate("2013-10-14 10:10:11"), "yyMM",
                                                     Calendar.DAY_OF_MONTH));
    }

    public static List<String> getDateBetweenEx(Date beginDate, Date endDate, String formatStr, int calenderConstants) {
        DateFormat f = new SimpleDateFormat(formatStr);

        String beginDateStr = DateUtil.DateToStr(beginDate, formatStr);
        String endDateStr = DateUtil.DateToStr(endDate, formatStr);

        Date date1 = DateUtil.StrToDate(beginDateStr, formatStr);
        Date date2 = DateUtil.StrToDate(endDateStr, formatStr);

        List<String> list = new ArrayList<String>();
        if (date1 == null) {
            return list;
        }

        list.add(f.format(date1));

        if (endDate == null || beginDate.after(endDate)) {
            return list;
        }

        while (date1.compareTo(date2) < 0) {
            if (calenderConstants == Calendar.DAY_OF_MONTH) {
                date1 = DateUtils.addDays(date1, 1);
            } else if (calenderConstants == Calendar.MONTH) {
                date1 = DateUtils.addMonths(date1, 1);
            } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
                date1 = DateUtils.addHours(date1, 1);
            } else {
                return list;
            }
            String date1Str = f.format(date1);
            if (!list.contains(date1Str)) { // 避免重复数据
                list.add(date1Str);
            }
        }
        return list;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间 //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getWeek(String pTime) {
        String Week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    /**
     * 获取昨天|明天|后天
     * 
     * @param date
     * @param dates
     * @return
     */
    public static Date getCalculationDate(Date date, int dates) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, dates);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return ShortStrToDate(dateString);
    }

    public static String getLastMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date curDate = java.sql.Date.valueOf(date);
        calendar.setTime(curDate);
        // 取得上一个时间
        calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) - 1);
        // 取得上一个月的下一天
        // calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return sdf.format(calendar.getTime());
    }

    public static String getIntervalDate(String pTime, int dates) {
        String strDate = "";
        if (!StringUtils.isEmpty(pTime)) {
            String[] strDates = pTime.split("-");
            strDate += strDates[0];
            if (strDates[1].length() == 2) {
                strDate += strDates[1];
            } else {
                strDate += "0" + strDates[1];
            }
            if (strDates[2].length() == 2) {
                strDate += strDates[2];
            } else {
                strDate += "0" + strDates[2];
            }
        }
        int intYear = Integer.parseInt(strDate.substring(0, 4));
        int intMonth = Integer.parseInt(strDate.substring(4, 6));
        int intDate = Integer.parseInt(strDate.substring(6, 8));
        Calendar cal = Calendar.getInstance();
        cal.set(intYear, intMonth - 1, intDate);
        cal.add(Calendar.DATE, dates);
        Date date = cal.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 计算日或者月时段,计算2个日期间的时段，用formatStr格式化,把结果放入list
     *
     * @param beginDate
     * @param endDate
     * @param formatStr
     * @param calenderConstants Calendar.DAY_OF_MONTH等对应的整形
     * @return
     */
    public static List<String> getDateBetween(Date beginDate, Date endDate, String formatStr, int calenderConstants) {
        DateFormat f = new SimpleDateFormat(formatStr);

        if (calenderConstants == Calendar.DAY_OF_MONTH) {
            formatStr = DateUtil.TIME_SHORT;
        } else if (calenderConstants == Calendar.MONTH) {
            formatStr = DateUtil.YEARMONTH;
        } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
            formatStr = DateUtil.DATE_AND_MONTH;
        } else if (calenderConstants == Calendar.MINUTE) {
            formatStr = DateUtil.TIME_NO_SEC;
        } else if (calenderConstants == Calendar.YEAR) {
            formatStr = DateUtil.YEAR_LONG;
        }
        String beginDateStr = DateUtil.DateToStr(beginDate, formatStr);
        String endDateStr = DateUtil.DateToStr(endDate, formatStr);

        Date date1 = DateUtil.StrToDate(beginDateStr, formatStr);
        Date date2 = DateUtil.StrToDate(endDateStr, formatStr);

        List<String> list = new ArrayList<String>();
        if (date1 == null) {
            return list;
        }

        list.add(f.format(date1));

        if (endDate == null || beginDate.after(endDate)) {
            return list;
        }

        while (date1.compareTo(date2) < 0) {
            if (calenderConstants == Calendar.DAY_OF_MONTH) {
                date1 = DateUtils.addDays(date1, 1);
            } else if (calenderConstants == Calendar.MONTH) {
                date1 = DateUtils.addMonths(date1, 1);
            } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
                date1 = DateUtils.addHours(date1, 1);
            } else if (calenderConstants == Calendar.MINUTE) {
                date1 = DateUtils.addMinutes(date1, 15);
            } else if (calenderConstants == Calendar.YEAR) {
                date1 = DateUtils.addYears(date1, 1);
            } else {
                return list;
            }
            String date1Str = f.format(date1);
            if (!list.contains(date1Str)) { // 避免重复数据
                list.add(date1Str);
            }
        }
        return list;
    }

    /**
     * 月末20点，日20点
     *
     * @param beginDate
     * @param endDate
     * @param formatStr
     * @param calenderConstants
     * @return
     */
    public static List<String> getDateBetweenLast20Hour(Date beginDate, Date endDate, String formatStr,
                                                        int calenderConstants) {
        DateFormat f = new SimpleDateFormat(formatStr);

        formatStr = DateUtil.TIME_LONG;
        String beginDateStr = DateUtil.DateToStr(beginDate, formatStr);
        String endDateStr = DateUtil.DateToStr(endDate, formatStr);

        Date date1 = DateUtil.StrToDate(beginDateStr, formatStr);
        Date date2 = DateUtil.StrToDate(endDateStr, formatStr);

        List<String> list = new ArrayList<String>();
        if (date1 == null) {
            return list;
        }
        if (calenderConstants == Calendar.DAY_OF_MONTH) {
            list.add(f.format(getLastDayOfMonth(date1, 20)));
        } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
            list.add(f.format(getSomeHour(date1, 20)));
        }

        if (endDate == null || beginDate.after(endDate)) {
            return list;
        }

        while (date1.compareTo(date2) < 0) {
            if (calenderConstants == Calendar.DAY_OF_MONTH) {
                date1 = getLastDayOfMonth(DateUtils.addMonths(date1, 1), 20);// 月末20
            } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
                date1 = getSomeHour(DateUtils.addDays(date1, 1), 20);// 日20点数据
            } else {
                return list;
            }
            String date1Str = f.format(date1);
            if (!list.contains(date1Str)) { // 避免重复数据
                list.add(date1Str);
            }
        }
        return list;
    }

    /**
     * 计算月份时段,计算2个日期间的时段，用formatStr格式化,把结果放入list
     *
     * @param beginDate
     * @param endDate
     * @param formatStr
     * @return
     */
    public static List<String> getMonthBetween(Date beginDate, Date endDate, String formatStr) {
        return getDateBetween(beginDate, endDate, formatStr, Calendar.MONTH);
    }

    /**
     * 计算年份时段,计算2个日期间的时段，用formatStr格式化,把结果放入list
     *
     * @param beginDate
     * @param endDate
     * @param formatStr
     * @return
     */
    public static List<String> getYearBetween(Date beginDate, Date endDate, String formatStr) {
        DateFormat f = new SimpleDateFormat(formatStr);

        String beginDateStr = DateUtil.DateToStr(beginDate, formatStr);
        String endDateStr = DateUtil.DateToStr(endDate, formatStr);

        Date date1 = DateUtil.StrToDate(beginDateStr, formatStr);
        Date date2 = DateUtil.StrToDate(endDateStr, formatStr);

        List<String> list = new ArrayList<String>();
        list.add(beginDateStr);

        if (endDate == null || beginDate.after(endDate)) {
            return list;
        }

        while (date1.compareTo(date2) < 0) {
            date1 = DateUtils.addYears(date1, 1);
            list.add(f.format(date1));
        }
        return list;
    }

    // 将这种格式的"Thu May 10 14:13:02 CST 2012"date 转为 yyyy-MM-dd HH:mm:ss 这种格式
    public static String DateStrTimeToLongStr(String dateStr) {
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
        Date date = null;
        try {
            // 把字符串转换成CST日期类型
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 把CST格式转换成普通日期格式
        return sdf.format(date);
    }

    //

    /**
     * 日期转换成字符串
     *
     * @param aDate 日期
     * @param dateSpan 时间分割0 为没分割
     * @param dateTimeSpan 日期和时间的分割字符 0 没分割符
     * @param timeSpan 时间的分割字符
     * @return Description of the Returned Value
     */
    public static String DateTimeToStr(Date aDate, char dateSpan, char dateTimeSpan, char timeSpan) {
        if (aDate == null) {
            return null;
        }
        StringBuffer dataBuf = new StringBuffer(20);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(aDate);
        dataBuf.append(calendar.get(Calendar.YEAR));
        if (dateSpan != 0) {
            dataBuf.append(dateSpan);
        }
        int month = calendar.get(Calendar.MONTH) + 1;
        appendInt(dataBuf, month);
        if (dateSpan != 0) {
            dataBuf.append(dateSpan);
        }
        int date = calendar.get(Calendar.DATE);
        appendInt(dataBuf, date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        if (hour + min + second > 0) {
            if (dateTimeSpan != 0) dataBuf.append(dateTimeSpan);
            appendInt(dataBuf, hour);
            if (timeSpan != 0) {
                dataBuf.append(timeSpan);
            }
            appendInt(dataBuf, min);
            if (timeSpan != 0) {
                dataBuf.append(timeSpan);
            }
            appendInt(dataBuf, second);
        }
        return dataBuf.toString();
    }

    public static Date getDate(int year, int month, int day, int hour, int min, int sec) {
        Calendar cl = Calendar.getInstance();
        cl.set(year, month, day, hour, min, sec);
        return cl.getTime();
    }

    private static void appendInt(StringBuffer buf, int nDate) {
        if (nDate < 10) {
            buf.append("0");
        }
        buf.append(nDate);
    }

    /**
     * 计算两个日期间的分钟数，截止日期必须大于起始日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long calcMinuteBetween2Dates(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) > 0) {
            return 0;
        }
        long start = startDate.getTime();
        long end = endDate.getTime();
        return (end - start) / (1000 * 60);
    }

    /***
     * 根据周期类型和参考时间来调整时间，不改动原时间
     *
     * @param srcDate
     * @param refDate
     * @param circletypeid
     * @return
     */
    public static Date adjustDate(Date srcDate, Date refDate, Short circletypeid) {
        Calendar src = Calendar.getInstance();
        src.setTime(srcDate);
        src.set(Calendar.SECOND, 0);
        src.set(Calendar.MILLISECOND, 0);
        Calendar ref = Calendar.getInstance();
        ref.setTime(refDate);
        if (circletypeid.intValue() == CircleTypeEnum.HOUR.getValue()) {
            src.set(Calendar.MINUTE, ref.get(Calendar.MINUTE));
        } else if (circletypeid.intValue() == CircleTypeEnum.DAY.getValue()) {
            src.set(Calendar.MINUTE, ref.get(Calendar.MINUTE));
            src.set(Calendar.HOUR_OF_DAY, ref.get(Calendar.HOUR_OF_DAY));
        } else if (circletypeid.intValue() == CircleTypeEnum.MONTH.getValue()) {
            if (ref.getActualMaximum(Calendar.DAY_OF_MONTH) == ref.get(Calendar.DAY_OF_MONTH)) {// 月末
                // src.add(Calendar.MONTH, -1);
                src.set(Calendar.DAY_OF_MONTH, src.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                // if (src.get(Calendar.DAY_OF_MONTH) > 15) {
                // src.add(Calendar.MONTH, -1);
                // }
                src.set(Calendar.DAY_OF_MONTH, ref.get(Calendar.DAY_OF_MONTH));
            }
            src.set(Calendar.MINUTE, ref.get(Calendar.MINUTE));
            src.set(Calendar.HOUR_OF_DAY, ref.get(Calendar.HOUR_OF_DAY));

        }
        return src.getTime();
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /***
     * 根据周期类型和参考时间来调整时间，不改动原时间 根据参考时间做前时标后时标调整，日>15为后时标，<15为前时标，小时<12为前时标>12为后时标
     *
     * @param srcDate
     * @param refDate
     * @param circletypeid
     * @return
     */
    public static Date adjustDateEx(Date srcDate, Date refDate, Short circletypeid) {
        Calendar src = Calendar.getInstance();
        src.setTime(srcDate);
        src.set(Calendar.SECOND, 0);
        src.set(Calendar.MILLISECOND, 0);
        Calendar ref = Calendar.getInstance();
        ref.setTime(refDate);
        if (circletypeid.intValue() == CircleTypeEnum.HOUR.getValue()) {
            src.set(Calendar.MINUTE, ref.get(Calendar.MINUTE));
        } else if (circletypeid.intValue() == CircleTypeEnum.DAY.getValue()) {
            src.set(Calendar.MINUTE, ref.get(Calendar.MINUTE));
            if (src.get(Calendar.HOUR_OF_DAY) > 12) {
                src.add(Calendar.DAY_OF_MONTH, -1);
            }
            src.set(Calendar.HOUR_OF_DAY, ref.get(Calendar.HOUR_OF_DAY));
        } else if (circletypeid.intValue() == CircleTypeEnum.MONTH.getValue()) {
            if (ref.getActualMaximum(Calendar.DAY_OF_MONTH) == ref.get(Calendar.DAY_OF_MONTH)) {// 月末
                src.add(Calendar.MONTH, -1);
                src.set(Calendar.DAY_OF_MONTH, src.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                if (src.get(Calendar.DAY_OF_MONTH) > 15) {
                    src.add(Calendar.MONTH, -1);
                }
                src.set(Calendar.DAY_OF_MONTH, ref.get(Calendar.DAY_OF_MONTH));
            }
            src.set(Calendar.MINUTE, ref.get(Calendar.MINUTE));
            src.set(Calendar.HOUR_OF_DAY, ref.get(Calendar.HOUR_OF_DAY));

        }
        return src.getTime();
    }

    /**
     * 通过周期类型，来获得指定时间往后推的结束时间 <br/>
     * 规则： 如果周期是日，往后推offset日, 月就往后推offset个月,其他依次
     *
     * @param startTime 指定参考时间
     * @param circleTypeId 周期类型
     * @param offset 偏移量
     * @return
     */
    public static Date getCircleEndTime(Date startTime, int circleTypeId, int offset) {
        CircleTypeEnum circleTypeEnum = CircleTypeEnum.getEnmuByValue(circleTypeId);
        if (circleTypeEnum == null || startTime == null) {
            return null;
        }

        if (circleTypeEnum == CircleTypeEnum.HOUR) {
            return DateUtils.addHours(startTime, offset);
        } else if (circleTypeEnum == CircleTypeEnum.DAY) {
            return DateUtils.addDays(startTime, offset);
        } else if (circleTypeEnum == CircleTypeEnum.MONTH) {
            return DateUtils.addMonths(startTime, offset);
        } else if (circleTypeEnum == CircleTypeEnum.WEEK) {
            return DateUtils.addWeeks(startTime, offset);
        }

        return startTime;
    }

    /**
     * 根据周期类型转换date为指定的格式： yymm 或者 yy,作为分表的表名后缀
     *
     * @param date
     * @param circleType
     * @return
     */
    public static String getTableNameByCircleType(Date date, int circleType) {
        if (circleType == CircleTypeEnum.DAY.getValue()) {
            return DateUtil.DateToYYMMStr(date);
        } else if (circleType == CircleTypeEnum.MONTH.getValue()) {
            return DateUtil.DateToYYStr(date);
        }
        return DateUtil.DateToYYMMStr(date);
    }

    /**
     * 每15分钟
     *
     * @param date
     * @return
     */
    public static Date getCyc15Minute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, getHourValue(new Date()));
        int minute = getMinuteValue(new Date()) / 15 * 15;
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static List<String> getLossDateBetween(Date beginDate, Date endDate, String formatStr,
                                                  int calenderConstants, int collectcyc) {
        DateFormat f = new SimpleDateFormat(formatStr);

        // if (calenderConstants == Calendar.DAY_OF_MONTH) {
        // formatStr = DateUtil.TIME_SHORT;
        // } else if (calenderConstants == Calendar.MONTH) {
        // formatStr = DateUtil.YEARMONTH;
        // } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
        // formatStr = DateUtil.DATE_AND_MONTH;
        // }
        String beginDateStr = DateUtil.DateToStr(beginDate, formatStr);
        String endDateStr = DateUtil.DateToStr(endDate, formatStr);

        Date date1 = DateUtil.StrToDate(beginDateStr, formatStr);
        Date date2 = DateUtil.StrToDate(endDateStr, formatStr);

        List<String> list = new ArrayList<String>();
        if (date1 == null) {
            return list;
        }

        list.add(f.format(date1));

        if (endDate == null || beginDate.after(endDate)) {
            return list;
        }

        while (date1.compareTo(date2) < 0) {
            if (calenderConstants == Calendar.DAY_OF_MONTH) {
                date1 = DateUtils.addDays(date1, collectcyc);
            } else if (calenderConstants == Calendar.MONTH) {
                date1 = DateUtils.addMonths(date1, collectcyc);
            } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
                date1 = DateUtils.addHours(date1, collectcyc);
            } else if (calenderConstants == Calendar.MINUTE) {
                date1 = DateUtils.addMinutes(date1, collectcyc);
            } else {
                return list;
            }
            String date1Str = f.format(date1);
            if (!list.contains(date1Str)) { // 避免重复数据
                list.add(date1Str);
            }
        }
        return list;
    }

    public static List<Date> getDateBetween(Date date1, Date date2, int calenderConstants, int collectcyc) {
        List<Date> list = new ArrayList<Date>();
        if (date1 == null) {
            return list;
        }
        list.add(date1);
        if (date2 == null || date1.after(date2)) {
            return list;
        }
        while (date1.compareTo(date2) < 0) {
            if (calenderConstants == Calendar.DAY_OF_MONTH) {
                date1 = DateUtils.addDays(date1, collectcyc);
            } else if (calenderConstants == Calendar.MONTH) {
                date1 = DateUtils.addMonths(date1, collectcyc);
            } else if (calenderConstants == Calendar.HOUR_OF_DAY) {
                date1 = DateUtils.addHours(date1, collectcyc);
            } else if (calenderConstants == Calendar.MINUTE) {
                date1 = DateUtils.addMinutes(date1, collectcyc);
            } else {
                return list;
            }
            if (!list.contains(date1)) { // 避免重复数据
                list.add(date1);
            }
        }
        return list;
    }

    public static List<Date> getLoadDateBetween(Date date1, Date date2, int points) {
        List<Date> list = new ArrayList<Date>();
        if (date1 == null) {
            return list;
        }
        list.add(date1);
        if (date2 == null || date1.after(date2)) {
            return list;
        }
        while (date1.compareTo(date2) < 0) {
            if (points == 24) {
                date1 = DateUtils.addHours(date1, 1);
            } else if (points == 48) {
                date1 = DateUtils.addMinutes(date1, 30);
            } else if (points == 96) {
                date1 = DateUtils.addMinutes(date1, 15);
            } else {
                return list;
            }
            if (!list.contains(date1)) { // 避免重复数据
                list.add(date1);
            }
        }
        return list;
    }

}
