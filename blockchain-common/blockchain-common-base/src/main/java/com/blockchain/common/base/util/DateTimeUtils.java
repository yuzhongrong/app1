package com.blockchain.common.base.util;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    /**
     * 计算精度
     */
    private static final MathContext mc = new MathContext(64, RoundingMode.HALF_UP);

	/* 毫秒级别常量 START */
    /**
     * 一秒包含多少毫秒
     */
    public static final Long MILLISECOND_OF_SECOND = 1000L;

    /**
     * 一分钟包含多少毫秒
     */
    public static final Long MILLISECOND_OF_MINUTE = 60 * MILLISECOND_OF_SECOND;

    /**
     * 一小时包含多少毫秒
     */
    public static final Long MILLISECOND_OF_HOUR = 60 * MILLISECOND_OF_MINUTE;

    /**
     * 一日包含多少毫秒
     */
    public static final Long MILLISECOND_OF_DAY = 24 * MILLISECOND_OF_HOUR;

    /**
     * 一星期包含多少毫秒
     */
    public static final Long MILLISECOND_OF_WEEK = 7 * MILLISECOND_OF_DAY;

	/* 毫秒级别常量 END */
    /* 支持的常用格式 START */
    /**
     * 日期时间毫秒格式
     */
    public static final String DATE_TIME_MILLISECOND_FORMAT = "yyyy-MM-dd HH:mm:ss SSS";

    /**
     * 日期时间格式
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期小时格式
     */
    public static final String DATE_HOUR_FORMAT = "yyyy-MM-dd HH";

    /**
     * 日期小时分钟格式
     */
    public static final String DATE_HOUR_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式
     */
    public static final String YEAR_MONTH_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 年份月份格式
     */
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";

    /**
     * 月日时
     */
    public static final String MONTH_DAY_HOUR_FORMAT = "MM-dd HH";


    /**
     * 月日
     */
    public static final String MONTH_DAY_FORMAT = "MM-dd";

    /**
     * 时间格式
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 小时分钟格式
     */
    public static final String HOUR_MINUTE_FORMAT = "HH:mm";

    /**
     * 4位长度年格式
     */
    public static final String YEAR_4_FORMAT = "yyyy";

    /**
     * 2位长度年格式
     */
    public static final String YEAR_2_FORMAT = "yy";

    /**
     * 月格式
     */
    public static final String MONTH_FORMAT = "MM";

    /**
     * 日格式
     */
    public static final String DAY_FORMAT = "dd";

    /**
     * 24小时格式
     */
    public static final String HOUR_24_FORMAT = "HH";

    /**
     * 12小时格式
     */
    public static final String HOUR_12_FORMAT = "hh";

    /**
     * 分钟格式
     */
    public static final String MINUTE_FORMAT = "mm";

    /**
     * 秒格式
     */
    public static final String SECOND_FORMAT = "ss";


	/* 支持的常用格式 END */

    /**
     * 时间域枚举类
     */
    public enum DateTimeField {
        /**
         * 年
         */
        YEAR(1, Calendar.YEAR, "年"),
        /**
         * 月
         */
        MONTH(2, Calendar.MONTH, "月"),
        /**
         * 周
         */
        WEEK(4, Calendar.DAY_OF_WEEK, "周"),
        /**
         * 日
         */
        DAY(8, Calendar.DATE, "日"),
        /**
         * 时
         */
        HOUR(16, Calendar.HOUR_OF_DAY, "时"),
        /**
         * 分
         */
        MINUTE(32, Calendar.MINUTE, "分"),
        /**
         * 秒
         */
        SECOND(64, Calendar.SECOND, "秒");

        private Integer key;

        private Integer calendarFieldVal;

        private String value;

        private DateTimeField() {
        }

        private DateTimeField(Integer key, Integer calendarFieldVal, String value) {
            this.key = key;
            this.calendarFieldVal = calendarFieldVal;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public Integer getCalendarFieldVal() {
            return calendarFieldVal;
        }

        public void setCalendarFieldVal(Integer calendarFieldVal) {
            this.calendarFieldVal = calendarFieldVal;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "-" + calendarFieldVal + "-" + this.value;
        }
    }

    /**
     * 不支持时间域类型操作异常
     */
    public static class NoSupportDateTimeFieldException extends RuntimeException {

        private static final long serialVersionUID = 1189120547395966542L;

        public NoSupportDateTimeFieldException() {
            super();
        }

        public NoSupportDateTimeFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

        public NoSupportDateTimeFieldException(String message, Throwable cause) {
            super(message, cause);
        }

        public NoSupportDateTimeFieldException(String message) {
            super(message);
        }

        public NoSupportDateTimeFieldException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * 将毫秒格式化成指定格式的字符串
     *
     * @param date      长整型的毫秒
     * @param formatStr 格式字符串
     * @return 返回已经转换成指定格式的字符串
     */
    public static String format(Long date, String formatStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return format(calendar.getTime(), formatStr);
    }

    /**
     * 将Calendar时间格式化成默认格式的字符串
     *
     * @param date Calendar时间
     * @return 返回已经转换成指定格式的字符串
     */
    public static String format(Calendar date) {
        return format(date.getTime());
    }

    /**
     * 将Date时间格式化成默认格式的字符串
     *
     * @param date Date时间
     * @return 返回已经转换成指定格式的字符串
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 将Calendar时间格式化成指定格式的字符串
     *
     * @param date      Calendar时间
     * @param formatStr 格式字符串
     * @return 返回已经转换成指定格式的字符串
     */
    public static String format(Calendar date, String formatStr) {
        return format(date.getTime(), formatStr);
    }

    /**
     * 将Date时间格式化成指定格式的字符串
     *
     * @param date      Date时间
     * @param formatStr 格式字符串
     * @return 返回已经转换成指定格式的字符串
     */
    public static String format(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * 将指定的日期字符串按照默认格式转化成Date对象
     *
     * @param dateStr 日期字符串
     * @return 转化后的Date对象
     */
    public static Date parse(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将指定的日期字符串按照默认格式转化成Date对象
     *
     * @param dateStr   日期字符串
     * @param formatStr 格式字符串
     * @return 转化后的Date对象
     */
    public static Date parse(String dateStr, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将指定的日期字符串按照多个格式转化成Date对象
     *
     * @param dateStr 日期字符串
     * @param formats 格式字符串,自动匹配适合的格式
     * @return 转化后的Date对象
     */
    public static Date parse(String dateStr, String[] formats) {
        if (ArrayUtils.isNotEmpty(formats)) {
            for (int i = 0, len = formats.length; i < len; i++) {
                String format = formats[i];
                Date parseDate = parse(dateStr, format);
                if (null != parseDate) {
                    return parseDate;
                }
            }
        }
        return null;
    }

    /**
     * 将指定的Date对象按照默认格式重新转化成新的Date对象,这个目的是为了排除其他时间域的干扰,比如:排除毫秒、秒等干扰,特殊情况下还可以排除日、月等干扰
     *
     * @param date 指定的Date对象
     * @return 新的Date对象
     */
    public static Date parse(Date date) {
        String dateFormatStr = format(date);
        return parse(dateFormatStr);
    }

    /**
     * 将指定的Date对象按照指定的格式重新转化成新的Date对象,这个目的是为了排除其他时间域的干扰,比如:排除毫秒、秒等干扰,特殊情况下还可以排除日、月等干扰
     *
     * @param date      指定的Date对象
     * @param formatStr 格式字符串
     * @return 新的Date对象
     */
    public static Date parse(Date date, String formatStr) {
        String dateFormatStr = format(date, formatStr);
        return parse(dateFormatStr, formatStr);
    }

    /**
     * 将指定的Calendar对象按照指定的格式重新转化成新的Date对象,这个目的是为了排除其他时间域的干扰,比如:排除毫秒、秒等干扰,特殊情况下还可以排除日、月等干扰
     *
     * @param date      指定的Calendar对象
     * @param formatStr 格式字符串
     * @return 新的Date对象
     */
    public static Date parse(Calendar date, String formatStr) {
        return parse(date.getTime(), formatStr);
    }

    /**
     * 将指定的毫秒按照指定的格式重新转化成新的Date对象,这个目的是为了排除其他时间域的干扰,比如:排除毫秒、秒等干扰,特殊情况下还可以排除日、月等干扰
     *
     * @param date      指定的毫秒
     * @param formatStr 格式字符串
     * @return 新的Date对象
     */
    public static Date parse(Long date, String formatStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return parse(calendar, formatStr);
    }

    /**
     * 获取开始时间
     *
     * @param field 指定的时间域
     * @param date  指定的日期
     * @return 获取指定字段开始的时间（精确级别到毫秒）
     */
    public static Date getStart(DateTimeField field, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field) {
            case YEAR:
                calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
                break;
            case MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
                break;
            case WEEK:
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
                break;
            case DAY:
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
                break;
            case HOUR:
                calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
                break;
            case MINUTE:
                calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
                break;
            case SECOND:
                calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
                break;
            default:
                break;
        }
        return calendar.getTime();
    }

    /**
     * 获取结束时间
     *
     * @param field 指定的域
     * @param date  指定的日期
     * @return 获取指定字段结束的时间（精确级别到秒）
     */
    public static Date getEnd(DateTimeField field, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field) {
            case YEAR:
                calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
                break;
            case MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
                break;
            case WEEK:
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
                break;
            case DAY:
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
                break;
            case HOUR:
                calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
                break;
            case MINUTE:
                calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
                calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
                break;
            case SECOND:
                calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
                break;
            default:
                break;
        }
        return calendar.getTime();
    }

    /**
     * 判断指定的多个Calendar对象是否在同一个时间域内,比如:日、月、年、时、分等等
     *
     * @param field 指定的时间域,日、月、年、时、分等等
     * @param dates 指定的多个Calendar对象
     * @return 是否在同一个时间与内, true/false
     */
    public static Boolean same(DateTimeField field, Calendar dates[]) {
        if (ArrayUtils.isNotEmpty(dates) && 1 < dates.length) {
            for (int i = 1, len = dates.length; i < len; i++) {
                Calendar std = dates[0];// 第一个作为标准比较
                Calendar target = dates[i];
                Boolean sameFlag = same(field, std, target);
                if (sameFlag) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 判断指定的多个Date对象是否在同一个时间域内,比如:日、月、年、时、分等等
     *
     * @param field 指定的时间域,日、月、年、时、分等等
     * @param dates 指定的多个Date对象
     * @return 是否在同一个时间与内, true/false
     */
    public static Boolean same(DateTimeField field, Date dates[]) {
        if (ArrayUtils.isNotEmpty(dates) && 1 < dates.length) {
            Calendar[] parseCalendars = new Calendar[dates.length];
            for (int i = 0, len = dates.length; i < len; i++) {
                Date date = dates[i];
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                parseCalendars[i] = calendar;
            }
            return same(field, parseCalendars);
        } else {
            return true;
        }
    }

    /**
     * 判断指定的两个Date对象是否在同一个时间域内,比如:日、月、年、时、分等等
     *
     * @param field  指定的时间域,日、月、年、时、分等等
     * @param first  第一个Date对象
     * @param second 第二个Date对象
     * @return 是否在同一个时间与内, true/false
     */
    public static Boolean same(DateTimeField field, Date first, Date second) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(first);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(second);
        return same(field, startCalendar, endCalendar);
    }

    /**
     * 判断指定的两个Calendar对象是否在同一个时间域内,比如:日、月、年、时、分等等
     *
     * @param field  指定的时间域,日、月、年、时、分等等
     * @param first  第一个Calendar对象
     * @param second 第二个Calendar对象
     * @return 是否在同一个时间与内, true/false
     */
    public static Boolean same(DateTimeField field, Calendar first, Calendar second) {
        Date parseFirstDate = null;
        Date parseSecondDate = null;
        switch (field) {
            case YEAR:
                parseFirstDate = parse(first, YEAR_4_FORMAT);
                parseSecondDate = parse(second, YEAR_4_FORMAT);
                break;
            case MONTH:
                parseFirstDate = parse(first, YEAR_MONTH_FORMAT);
                parseSecondDate = parse(second, YEAR_MONTH_FORMAT);
                break;
            case WEEK:
                Calendar firstCalendar = Calendar.getInstance();
                Calendar secondCalendar = Calendar.getInstance();
                Date parseFirstCalendarDate = parse(first, YEAR_MONTH_DATE_FORMAT);
                Date parseSecondCalendarDate = parse(second, YEAR_MONTH_DATE_FORMAT);
                firstCalendar.setTime(parseFirstCalendarDate);
                secondCalendar.setTime(parseSecondCalendarDate);
                firstCalendar.set(Calendar.DAY_OF_WEEK, firstCalendar.getActualMinimum(Calendar.DAY_OF_WEEK));
                secondCalendar.set(Calendar.DAY_OF_WEEK, firstCalendar.getActualMinimum(Calendar.DAY_OF_WEEK));
                parseFirstDate = firstCalendar.getTime();
                parseSecondDate = secondCalendar.getTime();
                break;
            case DAY:
                parseFirstDate = parse(first, YEAR_MONTH_DATE_FORMAT);
                parseSecondDate = parse(second, YEAR_MONTH_DATE_FORMAT);
                break;
            case HOUR:
                parseFirstDate = parse(first, DATE_HOUR_FORMAT);
                parseSecondDate = parse(second, DATE_HOUR_FORMAT);
                break;
            case MINUTE:
                parseFirstDate = parse(first, DATE_HOUR_MINUTE_FORMAT);
                parseSecondDate = parse(second, DATE_HOUR_MINUTE_FORMAT);
                break;
            case SECOND:
                parseFirstDate = parse(first, DATE_TIME_FORMAT);
                parseSecondDate = parse(second, DATE_TIME_FORMAT);
                break;
            default:
                parseFirstDate = parse(first, DATE_TIME_MILLISECOND_FORMAT);
                parseSecondDate = parse(second, DATE_TIME_MILLISECOND_FORMAT);
                break;
        }
        return (parseFirstDate.getTime() == parseSecondDate.getTime());
    }

    /**
     * 获取两个Data对象的时间差,并且按照指定的时间域为标准来计算
     *
     * @param field             指定的时间域,日、月、年、时、分等等
     * @param start             第一个Date对象
     * @param end               第二个Date对象
     * @param ignoreMilliSecond 是否忽略毫秒的干扰
     * @return 指定的时间差, 根据field的域来返回指定时间差
     */
    public static BigDecimal difference(DateTimeField field, Date start, Date end, boolean ignoreMilliSecond) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        return difference(field, startCalendar, endCalendar, ignoreMilliSecond);
    }

    /**
     * 获取两个Calendar对象的时间差,并且按照指定的时间域为标准来计算
     *
     * @param field             指定的时间域,日、月、年、时、分等等
     * @param start             第一个Calendar对象
     * @param end               第二个Calendar对象
     * @param ignoreMilliSecond 是否忽略毫秒的干扰
     * @return 指定的时间差, 根据field的域来返回指定时间差
     */
    public static BigDecimal difference(DateTimeField field, Calendar start, Calendar end, boolean ignoreMilliSecond) {
        BigDecimal returnVal = null;
        switch (field) {
            case YEAR:
            case MONTH:
                throw new NoSupportDateTimeFieldException("不支持YEAR/MONTH级别的时间差运算,因为标注参考时间不确定!");
            case WEEK:
                returnVal = (new BigDecimal(difference(start, end, ignoreMilliSecond)).divide(BigDecimal.valueOf(MILLISECOND_OF_WEEK), mc).setScale(4, RoundingMode.HALF_UP));
                break;
            case DAY:
                returnVal = (new BigDecimal(difference(start, end, ignoreMilliSecond)).divide(BigDecimal.valueOf(MILLISECOND_OF_DAY), mc).setScale(4, RoundingMode.HALF_UP));
                break;
            case HOUR:
                returnVal = (new BigDecimal(difference(start, end, ignoreMilliSecond)).divide(BigDecimal.valueOf(MILLISECOND_OF_HOUR), mc).setScale(4, RoundingMode.HALF_UP));
                break;
            case MINUTE:
                returnVal = (new BigDecimal(difference(start, end, ignoreMilliSecond)).divide(BigDecimal.valueOf(MILLISECOND_OF_MINUTE), mc).setScale(4, RoundingMode.HALF_UP));
                break;
            case SECOND:
                returnVal = (new BigDecimal(difference(start, end, ignoreMilliSecond)).divide(BigDecimal.valueOf(MILLISECOND_OF_SECOND), mc).setScale(4, RoundingMode.HALF_UP));
                break;
            default:
                returnVal = new BigDecimal(difference(start, end, ignoreMilliSecond));
                break;
        }
        return returnVal;
    }

    /**
     * 获取两个Calendar对象的时间差,并且按照毫秒为标准来计算
     *
     * @param start             第一个Calendar对象
     * @param end               第二个Calendar对象
     * @param ignoreMilliSecond 是否忽略毫秒的干扰
     * @return 指定的时间差, 根据field的域来返回指定时间差
     */
    public static BigInteger difference(Calendar start, Calendar end, boolean ignoreMilliSecond) {
        if (ignoreMilliSecond) {
            start.clear(Calendar.MILLISECOND);
            end.clear(Calendar.MILLISECOND);
        }
        BigInteger startMilliSecond = BigInteger.valueOf(start.getTimeInMillis());
        BigInteger endMilliSecond = BigInteger.valueOf(end.getTimeInMillis());
        return (endMilliSecond.subtract(startMilliSecond));
    }

    /**
     * 获取两个Date对象的时间差,并且按照毫秒为标准来计算
     *
     * @param start             第一个Date对象
     * @param end               第二个Date对象
     * @param ignoreMilliSecond 是否忽略毫秒的干扰
     * @return 指定的时间差, 根据field的域来返回指定时间差
     */
    public static BigInteger difference(Date start, Date end, boolean ignoreMilliSecond) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        return difference(startCalendar, endCalendar, ignoreMilliSecond);
    }

    /**
     * 将时间毫秒数转化为时间间隔
     *
     * @param timeMillis 间隔时间
     */
    public static String getIntervalTimeInString(long timeMillis) {
        long time = timeMillis;
        long ts = 1000L;//1秒
        long tm = 1000L * 60;//一分钟
        long th = 1000L * 60 * 60;//一小时
        long td = 1000L * 60 * 60 * 24;//一天
        long tM = 1000L * 60 * 60 * 24 * 30;//一个月
        long ty = 1000L * 60 * 60 * 24 * 365;//一年
        if (time > ty) {
            return time / ty + "年前";
        }
        if (time > tM) {
            return time / tM + "个月前";
        }
        if (time > td) {
            return time / td + "天前";
        }
        if (time > th) {
            return time / th + "小时前";
        }
        if (time > tm) {
            return time / tm + "分钟前";
        }
        if (time > ts) {
            return time / ts + "秒前";
        }
        return "刚刚";
    }
}
