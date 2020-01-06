package com.sayiamfun.StatisticalAnalysisOfResults;


import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.sayiamfun.word.CharReport_ZXT;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 频次统计文件
 */
public class FrequencyStatistics {

    private int volatilityNums = 1500;//每多少帧数统计一次  波动
    private int pressureNums = 1500;//每多少帧数统计一次  压降
    private int entropyNums = 1500;//每多少帧数统计一次  熵值
    private int BatteryNum = 0;
    private String VIN;
    private String nowTime;
    private List<String> volatilityDetectionList = new LinkedList<>();//波动性
    private List<String> pressureDropConsistencyList = new LinkedList<>();//压降一致性
    private List<String> entropyList = new LinkedList<>();//熵值
    private Long PstartTime;//数据开始时间   参与统计值会改变
    private Long VstartTime;//数据开始时间   参与统计值会改变
    private Long EstartTime;//数据开始时间   参与统计值会改变
    private Integer pressureDropConsistencySum = 0; //压降总次数
    private Integer volatilityDetectionSum = 0; //波动总次数
    private Integer EntropySum = 0; //熵值总次数
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapDay = new TreeMap<>();//压降每天结果
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek = new TreeMap<>();//压降每周结果
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapNums = new TreeMap<>();//压降每1500条
    private Map<Integer, Integer> pressureDropConsistencyMapBatterSum = new TreeMap<>();//压降每个单体出现总次数

    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay = new TreeMap<>();//波动每天
    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapWeek = new TreeMap<>();//波动每周
    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapNums = new TreeMap<>();//波动每1500条
    private Map<Integer, Integer> volatilityDetectionMapBatterSum = new TreeMap<>();//波动每个单体出现总次数

    private Map<Long, Map<Integer, Integer>> EntropyMapDay = new TreeMap<>();//熵值每天
    private Map<Long, Map<Integer, Integer>> EntropyMapWeek = new TreeMap<>();//熵值每周
    private Map<Long, Map<Integer, Integer>> EntropyMapNums = new TreeMap<>();//熵值每1500条
    private Map<Integer, Integer> EntropyMapBatterSum = new TreeMap<>();//熵值每个单体出现总次数

    public int getVolatilityNums() {
        return volatilityNums;
    }

    public void setVolatilityNums(int volatilityNums) {
        this.volatilityNums = volatilityNums;
    }

    public int getPressureNums() {
        return pressureNums;
    }

    public void setPressureNums(int pressureNums) {
        this.pressureNums = pressureNums;
    }

    public int getEntropyNums() {
        return entropyNums;
    }

    public void setEntropyNums(int entropyNums) {
        this.entropyNums = entropyNums;
    }

    public Map<Long, Map<Integer, Integer>> getPressureDropConsistencyMapNums() {
        return pressureDropConsistencyMapNums;
    }

    public void setPressureDropConsistencyMapNums(Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapNums) {
        this.pressureDropConsistencyMapNums = pressureDropConsistencyMapNums;
    }

    public Map<Long, Map<Integer, Integer>> getVolatilityDetectionMapNums() {
        return volatilityDetectionMapNums;
    }

    public void setVolatilityDetectionMapNums(Map<Long, Map<Integer, Integer>> volatilityDetectionMapNums) {
        this.volatilityDetectionMapNums = volatilityDetectionMapNums;
    }

    public Map<Long, Map<Integer, Integer>> getEntropyMapNums() {
        return EntropyMapNums;
    }

    public void setEntropyMapNums(Map<Long, Map<Integer, Integer>> entropyMapNums) {
        EntropyMapNums = entropyMapNums;
    }

    public Long getPstartTime() {
        return PstartTime;
    }

    public void setPstartTime(Long pstartTime) {
        PstartTime = pstartTime;
    }

    public Long getVstartTime() {
        return VstartTime;
    }

    public void setVstartTime(Long vstartTime) {
        VstartTime = vstartTime;
    }

    public Long getEstartTime() {
        return EstartTime;
    }

    public void setEstartTime(Long estartTime) {
        EstartTime = estartTime;
    }

    public Map<Integer, Integer> getPressureDropConsistencyMapBatterSum() {
        return pressureDropConsistencyMapBatterSum;
    }

    public void setPressureDropConsistencyMapBatterSum(Map<Integer, Integer> pressureDropConsistencyMapBatterSum) {
        this.pressureDropConsistencyMapBatterSum = pressureDropConsistencyMapBatterSum;
    }

    public Map<Integer, Integer> getVolatilityDetectionMapBatterSum() {
        return volatilityDetectionMapBatterSum;
    }

    public void setVolatilityDetectionMapBatterSum(Map<Integer, Integer> volatilityDetectionMapBatterSum) {
        this.volatilityDetectionMapBatterSum = volatilityDetectionMapBatterSum;
    }

    public Map<Integer, Integer> getEntropyMapBatterSum() {
        return EntropyMapBatterSum;
    }

    public void setEntropyMapBatterSum(Map<Integer, Integer> entropyMapBatterSum) {
        EntropyMapBatterSum = entropyMapBatterSum;
    }

    public Integer getPressureDropConsistencySum() {
        return pressureDropConsistencySum;
    }

    public void setPressureDropConsistencySum(int num) {
        this.pressureDropConsistencySum = this.pressureDropConsistencySum + num;
    }

    public Integer getVolatilityDetectionSum() {
        return volatilityDetectionSum;
    }

    public void setVolatilityDetectionSum(int num) {
        this.volatilityDetectionSum = this.volatilityDetectionSum + num;
    }

    public Integer getEntropySum() {
        return EntropySum;
    }

    public void setEntropySum(int num) {
        this.EntropySum = this.EntropySum + num;
    }

    public Map<Long, Map<Integer, Integer>> getEntropyMapDay() {
        return EntropyMapDay;
    }

    public void setEntropyMapDay(Map<Long, Map<Integer, Integer>> entropyMapDay) {
        EntropyMapDay = entropyMapDay;
    }

    public Map<Long, Map<Integer, Integer>> getEntropyMapWeek() {
        return EntropyMapWeek;
    }

    public void setEntropyMapWeek(Map<Long, Map<Integer, Integer>> entropyMapWeek) {
        EntropyMapWeek = entropyMapWeek;
    }

    public Map<Long, Map<Integer, Integer>> getPressureDropConsistencyMapWeek() {
        return pressureDropConsistencyMapWeek;
    }

    public void setPressureDropConsistencyMapWeek(Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek) {
        this.pressureDropConsistencyMapWeek = pressureDropConsistencyMapWeek;
    }

    public Map<Long, Map<Integer, Integer>> getVolatilityDetectionMapDay() {
        return volatilityDetectionMapDay;
    }

    public void setVolatilityDetectionMapDay(Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay) {
        this.volatilityDetectionMapDay = volatilityDetectionMapDay;
    }

    public Map<Long, Map<Integer, Integer>> getVolatilityDetectionMapWeek() {
        return volatilityDetectionMapWeek;
    }

    public void setVolatilityDetectionMapWeek(Map<Long, Map<Integer, Integer>> volatilityDetectionMapWeek) {
        this.volatilityDetectionMapWeek = volatilityDetectionMapWeek;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public Map<Long, Map<Integer, Integer>> getPressureDropConsistencyMapDay() {
        return pressureDropConsistencyMapDay;
    }

    public void setPressureDropConsistencyMapDay(Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapDay) {
        this.pressureDropConsistencyMapDay = pressureDropConsistencyMapDay;
    }


    public int getBatteryNum() {
        return BatteryNum;
    }

    public void setBatteryNum(int batteryNum) {
        BatteryNum = batteryNum;
    }

    public List<String> getVolatilityDetectionList() {
        return volatilityDetectionList;
    }

    public void setVolatilityDetectionList(List<String> volatilityDetectionList) {
        this.volatilityDetectionList = volatilityDetectionList;
    }

    public List<String> getPressureDropConsistencyList() {
        return pressureDropConsistencyList;
    }

    public void setPressureDropConsistencyList(List<String> pressureDropConsistencyList) {
        this.pressureDropConsistencyList = pressureDropConsistencyList;
    }

    public List<String> getEntropyList() {
        return entropyList;
    }

    public void setEntropyList(List<String> entropyList) {
        this.entropyList = entropyList;
    }


    private static DecimalFormat df = new DecimalFormat("0.000000000");

    private static final String GBK = "GBK";
    private static final String UTF = "UTF-8";

    /**
     * 最后结果输出天
     *
     * @param frequencyStatistics
     * @param s
     */
    private static void ResultWriteToCsv(String filename, FrequencyStatistics frequencyStatistics, Map<Integer, Integer> pressureDropConsistencyMapBatterSum, String s, Integer sum) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            df.setRoundingMode(RoundingMode.HALF_UP);
            filename = filename + "/0_" + frequencyStatistics.getVIN() + "_" + s + "按日汇总.csv";
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            bw.write("单体编号,全生命周期(率)"); //中间，隔开不同的单元格，一次写一行
            bw.newLine();
            for (int i = 0; i < frequencyStatistics.getBatteryNum(); i++) {
                bw.write("" + (i + 1) + "," + getDouble(pressureDropConsistencyMapBatterSum.get(i + 1), sum));
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 最后结果输出周
     *
     * @param frequencyStatistics
     * @param s
     */
    private static void ResultWriteToCsvWeek(String filename, FrequencyStatistics frequencyStatistics, Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek, String s) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            filename = filename + "/0_" + frequencyStatistics.getVIN() + "_" + s + "趋势.csv";
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            Map<Long, Integer> sumMap = new HashMap<>();
            //计算每个周的数据
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : pressureDropConsistencyMapWeek.entrySet()) {
                int sum = 0;
                for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    sum += integerIntegerEntry.getValue();
                }
                sumMap.put(longMapEntry.getKey(), sum);
            }
            Set<Long> longs = pressureDropConsistencyMapWeek.keySet();
            StringBuffer title = new StringBuffer(1000);
            title.append("单体编号");
            for (Long aLong : longs) {
                title.append("," + aLong);
            }
            bw.write(title.toString());
            bw.newLine();//中间，隔开不同的单元格，一次写一行
            for (int i = 0; i < frequencyStatistics.getBatteryNum(); i++) {
                StringBuffer text = new StringBuffer(1000);
                text.append(i + 1);
                for (Long aLong : longs) {
                    text.append("," + getDouble(pressureDropConsistencyMapWeek.get(aLong).get(i + 1), sumMap.get(aLong)));
                }
                bw.write(text.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 两数相除
     *
     * @param integer
     * @param pressureDropConsistencySum
     * @return
     */
    private static String getDouble(Integer integer, Integer pressureDropConsistencySum) {
        df.setRoundingMode(RoundingMode.HALF_UP);
        if (null == integer) {
            return df.format(0.0);
        }
        return df.format(integer.doubleValue() / pressureDropConsistencySum);
    }


    /**
     * 基础数据输出
     *
     * @param frequencyStatistics       输出对象
     * @param volatilityDetectionMapDay 输出数据
     * @param s                         模型名称
     * @param s1                        前缀  日统计/周统计
     */
    private static void BaseWriteToCSV(String filename, FrequencyStatistics frequencyStatistics, Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay, String s, String s1) {
        Set<Long> longs = volatilityDetectionMapDay.keySet();
        //写表头
        StringBuffer title = new StringBuffer(1000);
        title.append("单体编号");
        for (Long aLong : longs) {
            title.append("," + aLong);
        }
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            filename = filename + "/0_频次" + s1 + "统计_" + frequencyStatistics.getVIN() + "_" + s + frequencyStatistics.getNowTime() + ".csv";
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename)), encode);
            bw = new BufferedWriter(ow);
            bw.write(title.toString()); //中间，隔开不同的单元格，一次写一行
            bw.newLine();

            for (int i = 0; i < frequencyStatistics.getBatteryNum(); i++) {
                StringBuffer content = new StringBuffer(1000);
                content.append(i + 1);
                for (Long aLong : longs) {
                    content.append("," + getNum(volatilityDetectionMapDay.get(aLong).get(i + 1)));
                }
                bw.write(content.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 输出波动一致性每天数据
     */
    public void outVolatilityDetection(FrequencyStatistics frequencyStatistics, String outPath) {
        Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay = frequencyStatistics.getVolatilityDetectionMapDay();
        if (null == volatilityDetectionMapDay || volatilityDetectionMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, frequencyStatistics, volatilityDetectionMapDay, "_波动性模型_", "日");
        Map<Long, Map<Integer, Integer>> volatilityDetectionMapWeek = frequencyStatistics.getVolatilityDetectionMapWeek();
        if (null == volatilityDetectionMapDay || volatilityDetectionMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, frequencyStatistics, volatilityDetectionMapWeek, "_波动性模型_", "周");
        Map<Long, Map<Integer, Integer>> volatilityDetectionMapNums = frequencyStatistics.getVolatilityDetectionMapNums();
        if (null == volatilityDetectionMapNums || volatilityDetectionMapNums.size() == 0) return;
        BaseWriteToCSV(outPath, frequencyStatistics, volatilityDetectionMapNums, "_波动性模型_", "每1500帧");

        ResultWriteToCsv(outPath, frequencyStatistics, frequencyStatistics.getVolatilityDetectionMapBatterSum(), "_波动一致性模型_", frequencyStatistics.getVolatilityDetectionSum());
        ResultWriteToCsvWeek(outPath, frequencyStatistics, frequencyStatistics.getVolatilityDetectionMapWeek(), "_波动一致性模型_");
    }

    /**
     * 输出压降一致性每天数据
     */
    public void outPressureDropConsistency(FrequencyStatistics main, String outPath) {
        Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapDay = main.getPressureDropConsistencyMapDay();
        if (null == pressureDropConsistencyMapDay || pressureDropConsistencyMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, main, pressureDropConsistencyMapDay, "_压降一致性模型_", "日");
        Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek = main.getPressureDropConsistencyMapWeek();
        if (null == pressureDropConsistencyMapWeek || pressureDropConsistencyMapWeek.size() == 0) return;
        BaseWriteToCSV(outPath, main, pressureDropConsistencyMapWeek, "_压降一致性模型_", "周");
        Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapNums = main.getPressureDropConsistencyMapNums();
        if (null == pressureDropConsistencyMapNums || pressureDropConsistencyMapNums.size() == 0) return;
        BaseWriteToCSV(outPath, main, pressureDropConsistencyMapNums, "_压降一致性模型_", "每1500帧");

        ResultWriteToCsv(outPath, main, main.getPressureDropConsistencyMapBatterSum(), "_压降一致性模型_", main.getPressureDropConsistencySum());
        ResultWriteToCsvWeek(outPath, main, main.getPressureDropConsistencyMapWeek(), "_压降一致性模型_");

    }

    /**
     * 输出熵值每天数据
     */
    public void outEntropy(FrequencyStatistics frequencyStatistics, String outPath) {
        Map<Long, Map<Integer, Integer>> entropyMapDay = frequencyStatistics.getEntropyMapDay();
        if (null == entropyMapDay || entropyMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, frequencyStatistics, entropyMapDay, "_基于熵值的过压模型_", "日");
        Map<Long, Map<Integer, Integer>> entropyMapWeek = frequencyStatistics.getEntropyMapWeek();
        if (null == entropyMapWeek || entropyMapWeek.size() == 0) return;
        BaseWriteToCSV(outPath, frequencyStatistics, entropyMapWeek, "_基于熵值的过压模型_", "周");
        Map<Long, Map<Integer, Integer>> entropyMapNums = frequencyStatistics.getEntropyMapNums();
        if (null == entropyMapNums || entropyMapNums.size() == 0) return;
        BaseWriteToCSV(outPath, frequencyStatistics, entropyMapNums, "_基于熵值的过压模型_", "每1500帧");

        ResultWriteToCsv(outPath, frequencyStatistics, frequencyStatistics.getEntropyMapBatterSum(), "_基于熵值的过压故障诊断模型_", frequencyStatistics.getEntropySum());
        ResultWriteToCsvWeek(outPath, frequencyStatistics, frequencyStatistics.getEntropyMapWeek(), "_基于熵值的过压故障诊断模型_");
    }

    /**
     * 获取每周的频率
     * @param weekMap
     * @return
     */
    public Map<Long,Map<Integer,Double>> getWeekRate(Map<Long,Map<Integer,Integer>>  weekMap){
        Map<Long, Integer> sumMap = new HashMap<>();
        //计算每个周的数据
        for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : weekMap.entrySet()) {
            int sum = 0;
            for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                sum += integerIntegerEntry.getValue();
            }
            sumMap.put(longMapEntry.getKey(), sum);
        }
        Map<Long,Map<Integer,Double>> resultMap = new TreeMap<>();
        for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : weekMap.entrySet()) {
            Map<Integer,Double> childrenMap = new TreeMap<>();
            for (Integer integer : longMapEntry.getValue().keySet()) {
                childrenMap.put(integer,longMapEntry.getValue().get(integer).doubleValue()/sumMap.get(longMapEntry.getKey()));
            }
            resultMap.put(longMapEntry.getKey(),childrenMap);
        }
        return resultMap;
    }
    /**
     * 输出图表
     */
    public void outIcon(FrequencyStatistics frequencyStatistics, String outPath) {
        CharReport_ZXT.PieChart("基于熵值的过压故障诊断模型按日汇总", frequencyStatistics.getEntropyMapBatterSum(), outPath + "/EARate.png");
        CharReport_ZXT.PieChart("波动一致性模型按日汇总", frequencyStatistics.getVolatilityDetectionMapBatterSum(), outPath + "/VARate.png");
        CharReport_ZXT.PieChart("压降一致性模型按日汇总", frequencyStatistics.getPressureDropConsistencyMapBatterSum(), outPath + "/PARate.png");
        if(frequencyStatistics.getEntropyMapWeek().size()==1){
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getEntropyMapWeek().entrySet()) {
                CharReport_ZXT.PieChart("基于熵值的过压故障诊断模型趋势",longMapEntry.getValue(),outPath + "/EWRate.png");
            }
        }else {
            CharReport_ZXT.createPort("基于熵值的过压故障诊断模型趋势", getWeekRate(frequencyStatistics.getEntropyMapWeek()), "频率", outPath + "/EWRate.png");
        }
        if (frequencyStatistics.getVolatilityDetectionMapWeek().size()==1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getVolatilityDetectionMapWeek().entrySet()) {
                CharReport_ZXT.PieChart("波动一致性模型趋势",longMapEntry.getValue(),outPath + "/VWRate.png");
            }
        }else {
            CharReport_ZXT.createPort("波动一致性模型趋势", getWeekRate(frequencyStatistics.getVolatilityDetectionMapWeek()), "频率", outPath + "/VWRate.png");
        }
        if (frequencyStatistics.getPressureDropConsistencyMapWeek().size()==1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getPressureDropConsistencyMapWeek().entrySet()) {
                CharReport_ZXT.PieChart("压降一致性模型趋势",longMapEntry.getValue(),outPath + "/PWRate.png");
            }
        }else {
            CharReport_ZXT.createPort("压降一致性模型趋势", getWeekRate(frequencyStatistics.getPressureDropConsistencyMapWeek()), "频率", outPath + "/PWRate.png");
        }
        if (frequencyStatistics.getEntropyMapNums().size()==1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getEntropyMapNums().entrySet()) {
                CharReport_ZXT.PieChart("基于熵值的过压故障诊断模型每" + frequencyStatistics.getEntropyNums() + "帧次数",longMapEntry.getValue(),outPath + "/EENum.png");
            }
        }else {
            CharReport_ZXT.createPortInteger("基于熵值的过压故障诊断模型每" + frequencyStatistics.getEntropyNums() + "帧次数", frequencyStatistics.getEntropyMapNums(), "频次", outPath + "/EENum.png");
        }
        if (frequencyStatistics.getVolatilityDetectionMapNums().size()==1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getVolatilityDetectionMapNums().entrySet()) {
                CharReport_ZXT.PieChart("波动一致性模型每" + frequencyStatistics.getVolatilityNums() + "帧次数",longMapEntry.getValue(),outPath + "/VENum.png");
            }
        }else {
            CharReport_ZXT.createPortInteger("波动一致性模型每" + frequencyStatistics.getVolatilityNums() + "帧次数", frequencyStatistics.getVolatilityDetectionMapNums(), "频次", outPath + "/VENum.png");
        }
        if (frequencyStatistics.getPressureDropConsistencyMapNums().size()==1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getPressureDropConsistencyMapNums().entrySet()) {
                CharReport_ZXT.PieChart("压降一致性模型每" + frequencyStatistics.getPressureNums() + "帧次数",longMapEntry.getValue(),outPath + "/PENum.png");
            }
        }else {
            CharReport_ZXT.createPortInteger("压降一致性模型每" + frequencyStatistics.getPressureNums() + "帧次数", frequencyStatistics.getPressureDropConsistencyMapNums(), "频次", outPath + "/PENum.png");
        }
        pictureToWord(frequencyStatistics,outPath);
    }

    /**
     * 输出图片到word
     *
     * @param path
     */
    private static void pictureToWord(FrequencyStatistics frequencyStatistics,String path) {
        try {
            if (!path.endsWith("/")) path += "/";
            Map<String, Object> datas = new HashMap<>();
            //标题
            datas.put("title1","基于熵值的过压故障诊断模型按日汇总");
            datas.put("title2","基于熵值的过压故障诊断模型每" + frequencyStatistics.getEntropyNums() + "帧次数");
            datas.put("title3","基于熵值的过压故障诊断模型趋势");
            datas.put("title4","压降一致性模型按日汇总");
            datas.put("title5","压降一致性模型每" + frequencyStatistics.getPressureNums() + "帧次数");
            datas.put("title6","压降一致性模型趋势");
            datas.put("title7","波动一致性模型按日汇总");
            datas.put("title8","波动一致性模型每" + frequencyStatistics.getVolatilityNums() + "帧次数");
            datas.put("title9","波动一致性模型趋势");
            //本地图片
            datas.put("localPicture1", new PictureRenderData(600, 300, path + "EARate.png"));
            datas.put("localPicture2", new PictureRenderData(600, 300, path + "EENum.png"));
            datas.put("localPicture3", new PictureRenderData(600, 300, path + "EWRate.png"));
            datas.put("localPicture4", new PictureRenderData(600, 300, path + "PARate.png"));
            datas.put("localPicture5", new PictureRenderData(600, 300, path + "PENum.png"));
            datas.put("localPicture6", new PictureRenderData(600, 300, path + "PWRate.png"));
            datas.put("localPicture7", new PictureRenderData(600, 300, path + "VARate.png"));
            datas.put("localPicture8", new PictureRenderData(600, 300, path + "VENum.png"));
            datas.put("localPicture9", new PictureRenderData(600, 300, path + "VWRate.png"));
            //网路图片
            //datas.put("urlPicture", new PictureRenderData(100, 100, ".png", BytePictureUtils.getUrlByteArray("https://avatars3.githubusercontent.com/u/1394854?v=3&s=40")));
//            XWPFTemplate template = XWPFTemplate.compile(Constant.templatePath).render(datas);
            XWPFTemplate template = XWPFTemplate.compile("D:/tmp/test/picture.docx").render(datas);
            FileOutputStream out = new FileOutputStream(path + "0_统计图.docx");
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理基于熵值的过压故障诊断模型数据
     *
     * @param frequencyStatistics
     */
    public void doEntropy(FrequencyStatistics frequencyStatistics) {
        int nums = 0;
        int index = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每1500条的数据
        for (String s : frequencyStatistics.getEntropyList()) {
            if (!s.contains("_基于熵值的过压故障诊断模型")) continue;
            //获取时间
            String time = s.substring(s.lastIndexOf("日期")).split("_")[1];
            //读取文件内容
            List<List<String>> lists = ReadToList(s);
            Map<Integer, Integer> tmpMap = new TreeMap<>(); //存放一天的数据
            for (List<String> list : lists) {
                if (new BigDecimal(list.get(9)).compareTo(new BigDecimal("4")) > 0) {
                    if (null != list.get(8)) {
                        /**
                         * 天数据
                         */
                        if (tmpMap.containsKey(Integer.parseInt(list.get(8)))) {
                            tmpMap.put(Integer.parseInt(list.get(8)), tmpMap.get(Integer.parseInt(list.get(8))) + 1);
                        } else {
                            tmpMap.put(Integer.parseInt(list.get(8)), 1);
                        }
                        /**
                         * 每1500条数据
                         */
                        if (numsMap.containsKey(Integer.parseInt(list.get(8)))) {
                            numsMap.put(Integer.parseInt(list.get(8)), numsMap.get(Integer.parseInt(list.get(8))) + 1);
                        } else {
                            numsMap.put(Integer.parseInt(list.get(8)), 1);
                        }
                    }
                }
                nums++;
                /**
                 * 每1500条数据
                 */
                if (nums >= frequencyStatistics.getEntropyNums()) {
                    frequencyStatistics.getEntropyMapNums().put(index * 1L, numsMap);
                    numsMap = new TreeMap<>();
                    nums = 0;
                    index++;
                }
            }
            /**
             * 天数据
             */
            if (tmpMap.size() > 0) {
                frequencyStatistics.getEntropyMapDay().put(Long.valueOf(time), tmpMap);
            }
        }
        if (nums >= 0) {
            frequencyStatistics.getEntropyMapNums().put(index * 1L, numsMap);
        }
        //处理存放每周的数据
        if (null != frequencyStatistics.getEntropyMapDay() && frequencyStatistics.getEntropyMapDay().size() > 0) {
            Map<Integer, Integer> tmpMapWeek = null;
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getEntropyMapDay().entrySet()) {
                if (frequencyStatistics.getEstartTime() == null) setEstartTime(longMapEntry.getKey());
                Long startTime = getWeekLastTime(frequencyStatistics.getEstartTime());//数据开始时间
                while (longMapEntry.getKey() >= startTime) {//如果当前时间小于startTime则为当前周的数据 否则则是新一周的数据  需要更新main.srartTime 并且添加新的数据到Mapweek
                    setEstartTime(longMapEntry.getKey());
                    startTime = getWeekLastTime(frequencyStatistics.getEstartTime());
                }
                tmpMapWeek = frequencyStatistics.getEntropyMapWeek().get(frequencyStatistics.getEstartTime());
                if (null == tmpMapWeek) {
                    tmpMapWeek = new TreeMap<>(); //存放一周的数据
                }
                for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    if (tmpMapWeek.containsKey(integerIntegerEntry.getKey())) {
                        tmpMapWeek.put(integerIntegerEntry.getKey(), tmpMapWeek.get(integerIntegerEntry.getKey()) + integerIntegerEntry.getValue());
                    } else {
                        tmpMapWeek.put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                    }
                    //所有单体次数
                    setEntropySum(integerIntegerEntry.getValue());
                    //每个单体次数
                    if (frequencyStatistics.getEntropyMapBatterSum().containsKey(integerIntegerEntry.getKey())) {
                        frequencyStatistics.getEntropyMapBatterSum().put(integerIntegerEntry.getKey(), frequencyStatistics.getEntropyMapBatterSum().get(integerIntegerEntry.getKey()) + integerIntegerEntry.getValue());
                    } else {
                        frequencyStatistics.getEntropyMapBatterSum().put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                    }
                }
                frequencyStatistics.getEntropyMapWeek().put(frequencyStatistics.getEstartTime(), tmpMapWeek);
            }
        }
    }

    /**
     * 处理压降一致性数据
     *
     * @param frequencyStatistics
     */
    public void doPressureDropConsistency(FrequencyStatistics frequencyStatistics) {
        int nums = 0;
        int numsindex = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每1500条的数据
        for (String s : frequencyStatistics.getPressureDropConsistencyList()) {
            if (!s.contains("_压降一致性模型")) continue;
            //获取时间
            String time = s.substring(s.lastIndexOf("日期")).split("_")[1];
            //读取文件内容
            List<List<String>> lists = ReadToList(s);
            Map<Integer, Integer> tmpMap = new TreeMap<>(); //存放一天的数据
            //开始统计今天所有数据   将最小压差绝对值小于 0.02 的删除
            Iterator<List<String>> iterator = lists.iterator();
            while (iterator.hasNext()) {
                List<String> next = iterator.next();
                //获取电池单体数量
                if (frequencyStatistics.getBatteryNum() == 0)
                    frequencyStatistics.setBatteryNum(next.get(7).split("_").length);
                //获取vin
                if (frequencyStatistics.getVIN() == null)
                    frequencyStatistics.setVIN(next.get(1));
                //删除不符合条件的数据
                if (new BigDecimal(next.get(11)).abs().compareTo(new BigDecimal("0.02")) < 0) {
                    iterator.remove();
                }
            }
            if (lists.size() == 0) continue;
            //排序取最大压差绝对值与最小压差绝对值比最大的五条
            Collections.sort(lists, new Comparator<List<String>>() {
                @Override
                public int compare(List<String> o1, List<String> o2) {
                    if (new BigDecimal(o2.get(14)).compareTo(new BigDecimal(o1.get(14))) != 0)
                        return new BigDecimal(o2.get(14)).compareTo(new BigDecimal(o1.get(14)));
                    return new BigDecimal(o2.get(10)).abs().compareTo(new BigDecimal(o1.get(10)).abs());
                }
            });
            //根据规则获取需要保留的数据
            int size = lists.size();
            if (size > 5) {
                int index = 5;
                int index2 = 5;
                while (index < size && new BigDecimal(lists.get(index).get(14)).compareTo(new BigDecimal(lists.get(index - 1).get(14))) == 0) {
                    index++;
                }
                if (index > 5) {
                    while (index2 < index && new BigDecimal(lists.get(index2).get(10)).abs().compareTo(new BigDecimal(lists.get(index2 - 1).get(10)).abs()) == 0) {
                        index2++;
                    }
                }
                size = index2;
            }
            for (int i = 0; i < size; i++) {
                /**
                 * 每天数据
                 */
                List<String> list = lists.get(i);
                if (tmpMap.containsKey(getInt(list.get(12)))) {
                    tmpMap.put(getInt(list.get(12)), tmpMap.get(getInt(list.get(12))) + 1);
                } else {
                    tmpMap.put(getInt(list.get(12)), 1);
                }
                /**
                 * 每1500条数据
                 */
                if (numsMap.containsKey(getInt(list.get(12)))) {
                    numsMap.put(getInt(list.get(12)), numsMap.get(getInt(list.get(12))) + 1);
                } else {
                    numsMap.put(getInt(list.get(12)), 1);
                }
                nums++;
                /**
                 * 每1500条数据放入
                 */
                if (nums >= frequencyStatistics.getPressureNums()) {
                    frequencyStatistics.getPressureDropConsistencyMapNums().put(numsindex * 1L, numsMap);
                    numsMap = new TreeMap<>();
                    nums = 0;
                    numsindex++;
                }
            }
            //将当天数据放入
            if (tmpMap.size() > 0) {
                frequencyStatistics.getPressureDropConsistencyMapDay().put(Long.valueOf(time), tmpMap);
            }

        }
        if (nums > 0) {
            frequencyStatistics.getPressureDropConsistencyMapNums().put(numsindex * 1L, numsMap);
        }
        //处理存放每周的数据
        if (null != frequencyStatistics.getPressureDropConsistencyMapDay() && frequencyStatistics.getPressureDropConsistencyMapDay().size() > 0) {
            Map<Integer, Integer> tmpMapWeek = null;
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getPressureDropConsistencyMapDay().entrySet()) {
                if (frequencyStatistics.getPstartTime() == null) setPstartTime(Long.valueOf(longMapEntry.getKey()));
                Long startTime = getWeekLastTime(frequencyStatistics.getPstartTime());//数据开始时间
                while (longMapEntry.getKey() >= startTime) {
                    setPstartTime(longMapEntry.getKey());
                    startTime = getWeekLastTime(frequencyStatistics.getPstartTime());
                }
                tmpMapWeek = frequencyStatistics.getPressureDropConsistencyMapWeek().get(frequencyStatistics.getPstartTime());
                if (null == tmpMapWeek) {
                    tmpMapWeek = new TreeMap<>(); //存放一周的数据
                }
                for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    if (tmpMapWeek.containsKey(integerIntegerEntry.getKey())) {
                        tmpMapWeek.put(integerIntegerEntry.getKey(), tmpMapWeek.get(integerIntegerEntry.getKey()) + integerIntegerEntry.getValue());
                    } else {
                        tmpMapWeek.put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                    }
                    //所有单体次数
                    setPressureDropConsistencySum(integerIntegerEntry.getValue());
                    //每个单体次数
                    if (frequencyStatistics.getPressureDropConsistencyMapBatterSum().containsKey(integerIntegerEntry.getKey())) {
                        frequencyStatistics.getPressureDropConsistencyMapBatterSum().put(integerIntegerEntry.getKey(), frequencyStatistics.getPressureDropConsistencyMapBatterSum().get(integerIntegerEntry.getKey()) + integerIntegerEntry.getValue());
                    } else {
                        frequencyStatistics.getPressureDropConsistencyMapBatterSum().put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                    }
                }
                frequencyStatistics.getPressureDropConsistencyMapWeek().put(frequencyStatistics.getPstartTime(), tmpMapWeek);
            }
        }
    }

    /**
     * 处理波动一致性数据
     *
     * @param frequencyStatistics
     */
    public void doVolatilityDetection(FrequencyStatistics frequencyStatistics) {
        int nums = 0;
        int numsindex = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每1500条的数据
        for (String s : frequencyStatistics.getVolatilityDetectionList()) {
            if (!s.contains("_波动性检测模型")) continue;
            //获取时间
            String time = s.substring(s.lastIndexOf("日期")).split("_")[1];
            //读取文件内容
            List<List<String>> lists = ReadToList(s);
            Map<Integer, Integer> tmpMap = new TreeMap<>(); //存放一天的数据
            for (List<String> list : lists) {
                if (null != list.get(6)) {
                    String[] s1 = list.get(6).split("_");
                    for (String s2 : s1) {
                        /**
                         * 每天数据
                         */
                        if (tmpMap.containsKey(Integer.parseInt(s2))) {
                            tmpMap.put(Integer.parseInt(s2), tmpMap.get(Integer.parseInt(s2)) + 1);
                        } else {
                            tmpMap.put(Integer.parseInt(s2), 1);
                        }
                        /**
                         * 每1500条数据
                         */
                        if (numsMap.containsKey(Integer.parseInt(s2))) {
                            numsMap.put(Integer.parseInt(s2), numsMap.get(Integer.parseInt(s2)) + 1);
                        } else {
                            numsMap.put(Integer.parseInt(s2), 1);
                        }
                    }
                }
                nums++;
                /**
                 * 每1500条数据
                 */
                if (nums >= frequencyStatistics.getVolatilityNums()) {
                    frequencyStatistics.getVolatilityDetectionMapNums().put(numsindex * 1L, numsMap);
                    numsMap = new TreeMap<>();
                    nums = 0;
                    numsindex++;
                }
            }
            /**
             * 每天数据
             */
            if (tmpMap.size() > 0) {
                frequencyStatistics.getVolatilityDetectionMapDay().put(Long.valueOf(time), tmpMap);
            }

        }
        if (nums >= 0) {
            frequencyStatistics.getVolatilityDetectionMapNums().put(numsindex * 1L, numsMap);
        }
        //处理存放每周的数据
        if (null != frequencyStatistics.getVolatilityDetectionMapDay() && frequencyStatistics.getVolatilityDetectionMapDay().size() > 0) {
            Map<Integer, Integer> tmpMapWeek = null;
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : frequencyStatistics.getVolatilityDetectionMapDay().entrySet()) {
                if (frequencyStatistics.getVstartTime() == null) setVstartTime(Long.valueOf(longMapEntry.getKey()));
                Long startTime = getWeekLastTime(frequencyStatistics.getVstartTime());//数据开始时间
                while (longMapEntry.getKey() >= startTime) {
                    setVstartTime(longMapEntry.getKey());
                    startTime = getWeekLastTime(frequencyStatistics.getVstartTime());
                }
                tmpMapWeek = frequencyStatistics.getVolatilityDetectionMapWeek().get(frequencyStatistics.getVstartTime());
                if (null == tmpMapWeek) {
                    tmpMapWeek = new TreeMap<>(); //存放一周的数据
                }
                for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    if (tmpMapWeek.containsKey(integerIntegerEntry.getKey())) {
                        tmpMapWeek.put(integerIntegerEntry.getKey(), tmpMapWeek.get(integerIntegerEntry.getKey()) + integerIntegerEntry.getValue());
                    } else {
                        tmpMapWeek.put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                    }
                    //所有单体异常次数
                    setVolatilityDetectionSum(integerIntegerEntry.getValue());
                    //每个单体次数
                    if (frequencyStatistics.getVolatilityDetectionMapBatterSum().containsKey(integerIntegerEntry.getKey())) {
                        frequencyStatistics.getVolatilityDetectionMapBatterSum().put(integerIntegerEntry.getKey(), frequencyStatistics.getVolatilityDetectionMapBatterSum().get(integerIntegerEntry.getKey()) + integerIntegerEntry.getValue());
                    } else {
                        frequencyStatistics.getVolatilityDetectionMapBatterSum().put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
                    }
                }
                frequencyStatistics.getVolatilityDetectionMapWeek().put(frequencyStatistics.getVstartTime(), tmpMapWeek);
            }
        }
    }

    /**
     * 获取一周后的时间
     *
     * @param startTime
     * @return
     */
    private static long getWeekLastTime(Long startTime) {
        if (startTime == null || startTime.toString().length() != 8) return startTime;
        int year = Integer.parseInt(startTime.toString().substring(0, 4));
        int month = Integer.parseInt(startTime.toString().substring(4, 6));
        int day = Integer.parseInt(startTime.toString().substring(6, 8));
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day + 7 > 30) {
                if (month < 9) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 30));
                }
                return Long.valueOf("" + year + (month + 1) + "0" + (day + 7 - 30));
            } else {
                if (month < 10) {
                    if (day + 7 < 10) {
                        return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                    }
                    return Long.valueOf("" + year + "0" + month + (day + 7));
                }
                if (day + 7 < 10) {
                    return Long.valueOf("" + year + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + month + (day + 7));
            }
        } else if (month == 2) {
            if ((year % 100 == 0 && year / 400 == 0) || year / 4 == 0) {
                if (day + 7 > 29) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 29));
                } else if (day + 7 < 10) {
                    return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + "0" + month + (day + 7));
            } else {
                if (day + 7 > 28) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 28));
                } else if (day + 7 < 10) {
                    return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + "0" + month + (day + 7));
            }
        } else {
            if (day + 7 > 31) {
                if (month + 1 > 12) {
                    return Long.valueOf("" + (year + 1) + "0" + 1 + "0" + (day + 7 - 31));
                } else if (month < 10) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 31));
                }
                return Long.valueOf("" + year + (month + 1) + "0" + (day + 7 - 30));
            } else {
                if (month < 10) {
                    if (day + 7 < 10) {
                        return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                    }
                    return Long.valueOf("" + year + "0" + month + (day + 7));
                }
                if (day + 7 < 10) {
                    return Long.valueOf("" + year + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + month + (day + 7));
            }
        }
    }

    /**
     * 将csv文件读取到List集合中
     *
     * @param path
     * @return
     */
    private static List<List<String>> ReadToList(String path) {
        //csv文件读取方法
        List<List<String>> resultList = new LinkedList<>(); //保存最后读取的所有数据，LinkedList 是为了保证顺序一致
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(path)), encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = null;//去掉第一行
            while ((line = reader.readLine()) != null) {
                if (line.contains("VIN")) continue;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                List<String> childrenList = new LinkedList<>();//将数组转换为列表存储，和excel读取结果一致，方便处理LinkedList 是为了保证顺序一致
                for (String s : item) {
                    childrenList.add(s);
                }
                resultList.add(childrenList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) reader.close();
                if (null != ir) ir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    private static String getNum(Integer integer) {
        return null == integer ? "0" : integer.toString();
    }

    private static int getInt(String string) {
        try {
            return Integer.parseInt(null == string && "".equals(string.trim()) ? "0" : string);
        } catch (NumberFormatException e) {
            System.out.println(string);
            return 0;
        }
    }

    private static BigDecimal getBigDecimal(String string) {
        return new BigDecimal(null == string ? "0" : string);
    }

    //字符编码
    public final static String encode = "GBK";

    /**
     * TODO:递归扫描指定文件夹下面的指定文件
     *
     * @return ArrayList<Object>
     * @throws FileNotFoundException
     */
    public ArrayList<String> scanFilesWithRecursion(String folderPath) {
        ArrayList<String> scanFiles = new ArrayList<>();
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            System.out.println('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
            return scanFiles;
        }
        if (directory.isDirectory()) {
            File[] filelist = directory.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                /**如果当前是文件夹，进入递归扫描文件夹**/
                if (filelist[i].isDirectory()) {
                    /**递归扫描下面的文件夹**/
                    scanFilesWithRecursion(filelist[i].getAbsolutePath());
                }
                /**非文件夹**/
                else {
                    if (!scanFiles.contains(filelist[i].getAbsolutePath())) {
                        scanFiles.add(filelist[i].getAbsolutePath());
                    }
                }
            }
        }
        return scanFiles;
    }
}

