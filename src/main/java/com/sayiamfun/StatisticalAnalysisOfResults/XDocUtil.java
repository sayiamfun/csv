package com.sayiamfun.StatisticalAnalysisOfResults;

import com.hg.xdoc.XDocService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * word 图表输出类
 */
public class XDocUtil {

    /**
     * 输出折线图
     *
     * @param data     数据
     * @param template 摸板文件路径
     * @param outPath  输出文件路径
     */
    public static void outLine(String data, String template, String outPath) {
        XDocService xdocService = new XDocService("http://free.xdocin.com", "xsjf5zkfmrd6vcegvgt4trpe2u");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("公司", data);
        try {
            xdocService.run(new File(template),//模板文件
                    param,
                    new File(outPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据获取
     * "公司", "公司,成立时间,年营业额,员工数\r\n小米,2010,780,14000\r\n华为,1987,5600,170000\r\n苹果,1976,16300,110000\r\n魅族,1985,15300,130000"
     *
     * @param map
     * @param title
     * @return
     */
    public static String getIntegerData(Map<Integer, Integer> map, String title) {
        StringBuffer stringBuffer = new StringBuffer(1000);
        stringBuffer.append("数据," + title);
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            stringBuffer.append("\r\n");
            stringBuffer.append("单体" + integerIntegerEntry.getKey() + "," + integerIntegerEntry.getValue());
        }
        return stringBuffer.toString();
    }

    public static String getData(Map<Integer, Double> map, String title) {
        StringBuffer stringBuffer = new StringBuffer(1000);
        stringBuffer.append("数据," + title);
        for (Map.Entry<Integer, Double> integerIntegerEntry : map.entrySet()) {
            stringBuffer.append("\r\n");
            stringBuffer.append("单体" + integerIntegerEntry.getKey() + "," + integerIntegerEntry.getValue());
        }
        return stringBuffer.toString();
    }

    public static String getData(Map<Integer, Integer> map, String title, int numbs) {
        StringBuffer stringBuffer = new StringBuffer(1000);
        stringBuffer.append("数据," + title);
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            stringBuffer.append("\r\n");
            stringBuffer.append("单体" + integerIntegerEntry.getKey() + "," + integerIntegerEntry.getValue().doubleValue() / numbs);
        }
        return stringBuffer.toString();
    }

    /**
     * 折线图数据
     */
    public static String getDoubleData(Map<Long, Map<Integer, Double>> weekMap, int batteryNum) {
        Set<Long> longs = weekMap.keySet();
        StringBuffer title = new StringBuffer(1000);
        title.append("单体编号");
        for (int i = 0; i < batteryNum; i++) {
            title.append(",单体" + (i + 1));
        }
        for (Long aLong : longs) {
            title.append("\r\n" + aLong);
            for (int i = 0; i < batteryNum; i++) {
                title.append("," + weekMap.get(aLong).get(i + 1));
            }
        }
        return title.toString();
    }

    public static String getData(Map<Long, Map<Integer, Integer>> weekMap, int batteryNum) {
        Map<Long, Integer> sumMap = new HashMap<>();
        //计算每个周的数据
        for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : weekMap.entrySet()) {
            int sum = 0;
            for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                sum += integerIntegerEntry.getValue();
            }
            sumMap.put(longMapEntry.getKey(), sum);
        }
        Set<Long> longs = weekMap.keySet();
        StringBuffer title = new StringBuffer(1000);
        title.append("单体编号");
        for (int i = 0; i < batteryNum; i++) {
            title.append(",单体" + (i + 1));
        }
        for (Long aLong : longs) {
            title.append("\r\n" + aLong);
            for (int i = 0; i < batteryNum; i++) {
                title.append("," + (null != weekMap.get(aLong).get(i + 1) ? weekMap.get(aLong).get(i + 1).doubleValue() : 0.0 / sumMap.get(aLong)));
            }
        }
        return title.toString();
    }


}
