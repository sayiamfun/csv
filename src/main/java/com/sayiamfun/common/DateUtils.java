package com.sayiamfun.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

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
        String[] s = "-0.2377_0.2211_-0.5386_1.3268_1.1816_2.2469_1.4802_1.1588_0.1211_-0.3471_-0.1324_-0.6506_-0.0148_-0.4456_-0.0215_0.0438_-0.0115_-1.0603_-0.4456_-0.4094_-0.1895_-0.956_0.8602_1.5067_2.4936_1.6145_0.061_1.1748_-1.0059_-1.6734_-1.4112_-0.3132_1.8934_1.3361_1.1386_1.7788_1.7254_1.4414_-0.1428_0.5227_-0.8928_-0.5228_0.0449_0.2704_0.516_1.8706_1.5294_1.4372_0.3145_-0.8705_-0.3817_0.021_-0.4616_-0.5684_0.0013_-0.4666_-0.1895_0.3787_-0.2973_-0.0706_-0.104_-0.0413_-0.7856_0.0998_-0.3854_-0.3362_-0.2075_-1.732_-1.1291_-0.9043_-0.7124_-0.5363_0.9292_1.248_1.6403_1.644_0.9494_0.7493_0.4279_-0.5091_-1.1589_-0.4542_-0.7984_0.9225_1.2376_1.3092_1.0998_0.401_0.1386_-1.2925_-2.0068_-0.6109_-1.3013_-0.4854_-1.0145_-0.2216_0.6373_-0.2702_0.0024_-0.0189_0.149_-0.1717_0.8639_0.5653_-0.783_-0.0573_-0.104_-1.6817_-1.2055_-2.3872_-0.7175_-1.5527_1.3652_0.644_1.5134_1.008_0.0573_0.9624_0.0319_-0.6378_-1.4684_-1.0257_-1.4166_-0.9697_-0.3269_1.1028_0.0371_-0.1089_-0.668_-1.8913_-4.1962_-1.6617_0.3854_-0.6322_0.0616_-0.3216_-1.0239_-1.2418_-0.9894_-2.1697_-1.0319_-1.2481_-1.2572_0.0277_0.3693_0.5425_-0.6931_-1.2257_-1.9176_-3.5864_-1.6606_-0.9271_1.008_1.0277_1.5067_1.6482_0.4212_0.3177_-0.415_-0.9358_-1.9753_-0.4557_-0.3557_0.3524_0.0796_0.3331_-0.2548_0.4637_-0.8238_-2.1858_-2.1992_-1.7823_-0.3288_0.4279_0.6705_1.1588_0.9691_-0.3817_-0.375_-0.431_-1.1895_-0.5228_-1.0319_0.8214_0.9987_1.0842_1.0277_-0.5024_-1.2609_-2.1495_-1.4163_0.1658_-0.2414_1.3559_1.3652_1.3652_1.0319_1.1028_-0.6478_-0.5651_-0.3787_-0.8577_-0.5161_0.2636_1.2905_1.6306_0.9893_2.4511_1.4802_1.08_-0.0229_0.0935_-0.6397_-0.3787_1.248_1.2443_1.8866_0.245_2.0572_0.4551_0.061_-0.8342_0.9173_1.136_0.6478_0.9587_0.127_0.3264_-0.1649_-1.3698_-0.1563_0.8251_-0.0801_1.9852_2.192_1.0826_-0.0697_0.031_-0.5363_-0.4482_0.005_0.5297_1.7451_0.4637_1.0863_1.8467_1.7519_1.6175_0.2999_0.7814_-0.1328_-0.4124_0.0616_0.7161_0.087_-0.5133_-0.5467_-0.1428_-0.5124_-0.9634_-2.4228_-1.1944_0.2118_-0.0018_0.1909_-0.0738_0.6171_-0.4538_-1.2457_-0.7898_-0.3232_0.0863_0.3856_0.6798_1.4839_1.9587_0.2864_-0.7491_-0.5161_0.6545_-0.9711_-1.1428_-1.2239_-0.2107_-1.7357_-0.2604_0.332_-0.3683_-0.8398_-1.1816_-1.2444_-0.309_-0.5907_-0.4094_0.0512_0.1692_0.1002_0.1428_-0.4638_-1.9083_-1.1231_0.3973_-1.2146_-0.1977_-0.4968_-0.064_0.2838_-0.232_-0.7389_-1.1951_-1.0085_-0.6736_-0.3854_0.2237_0.2051_-0.8685_0.2476_-0.1563_-1.1645_-2.0966_-0.6747_-0.5628_0.6571_0.9038_0.1195_1.4077_0.9322_0.8535_-0.1951_-0.4124_-1.0457_-0.6506_0.2573_-0.4345_-0.9297_-0.2309_-0.0413_-1.1944_-0.3522_-0.4124_-0.7984_-0.4505_0.3923_-0.2504_-0.956_0.3491_0.215_-0.5068_-0.6016_-1.6097_-0.1242_-0.8372_0.9494_-0.1163_1.6736_0.8867_0.1521_0.9131_-0.5456_-0.3164_-0.4519_-0.7226_0.4704_0.5985_0.4484_0.2771_-0.3157_0.1855_0.1263_0.0729_0.2771_-0.349_-0.4998_-0.6346_0.2311_0.327_-0.2079_1.3268_-0.0504_-0.1589_0.4414_1.1588_1.4439_1.3226_0.5548_1.4798_1.0535_1.3652_-0.1851_0.3522_-0.1951_0.7649_1.5227_1.1853_2.0042_1.1786_1.5399_1.2371_1.3226_0.2013".split("_");
        List<String> strings = Arrays.asList(s);
        System.out.println(strings.contains("-4.1962"));
        System.out.println(strings.get(131));

    }
}
