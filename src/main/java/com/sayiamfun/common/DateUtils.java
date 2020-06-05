package com.sayiamfun.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyyMMdd", "yyyyMMddHHmmss"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }


    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 日期格式转换
     *
     * @param param
     */
    public static void dateFormat(Map<String, String> param) {
        String startTime = param.get("startTime") == null ? "" : param.get("startTime");
        String endTime = param.get("endTime") == null ? "" : param.get("endTime");
        param.put("startTime", startTime.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", ""));
        param.put("endTime", endTime.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", ""));
    }

    public static void main(String[] args) {

        String startTime = "20200602102030";
        String startTime2 = "20200602102030";
        Date date = strToDate(startTime);
        Date date1 = strToDate(startTime2);

        System.out.println(date1.getTime() - date.getTime());

    }

    public static Date strToDate(String str) {
        if ("0".equals(str)) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据开始时间和结束时间获取时间分区sql
     *
     * @param startTime
     * @param endTime
     * @return java.lang.String
     * @author liwenjie
     * @date 2020/5/29 13:33
     */
    public static String getDateSqlString(String startTime, String endTime) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime))
            throw new RuntimeException("startTime or endTime is empty Exception");
        if (!startTime.contains("-") && startTime.length() >= 8) {
            String tmpStartTime = "";
            tmpStartTime = startTime.substring(0, 4) + "-" + startTime.substring(4, 6) + "-" + startTime.substring(6, 8);
            startTime = tmpStartTime;
        }
        if (!endTime.contains("-") && endTime.length() >= 8) {
            String tmpStartTime = "";
            tmpStartTime = endTime.substring(0, 4) + "-" + endTime.substring(4, 6) + "-" + endTime.substring(6, 8);
            endTime = tmpStartTime;
        }
        Date startT;
        Date endT;
        StringBuilder stringBuilder;
        try {
            startT = parseDate(startTime);
            endT = parseDate(endTime);
        } catch (Exception e) {
            throw new RuntimeException("String to Date Exceprion ");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(startT);
        Calendar endcalendar = Calendar.getInstance();
        endcalendar.setTime(endT);
        if (endcalendar.compareTo(instance) < 0) {
            throw new RuntimeException("endTime < startTime Exception");
        }
        String end = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, endcalendar.getTime());
        stringBuilder = new StringBuilder(2000);
        stringBuilder.append(" and (");
        int i = 0;
        while (true) {
            String str = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, instance.getTime());
            if (i > 0) stringBuilder.append(" or ");
            String[] split = str.split("-");
            if (split.length == 3) {
                stringBuilder.append("(year = '").append(split[0]).append("' and month = '").append(split[1]).append("' and day = '").append(split[2]).append("')");
            }
            instance.add(Calendar.DATE, 1);
            i++;
            if (end.equals(str)) {
                break;
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
