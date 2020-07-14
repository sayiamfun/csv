package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.Constant;
import com.sayiamfun.common.DateUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 频次统计文件
 */
@Data
public class FrequencyStatistics {

    private static Logger logger = LoggerFactory.getLogger(FrequencyStatistics.class);

    public FrequencyStatistics() {
    }

    //字符编码
    public final static String encode = "GBK";

    private String type;
    private int TimeDifference = 3 * 60;
    private int volatilityNums = 500;//每多少帧数统计一次  波动
    private int pressureNums = 500;//每多少帧数统计一次  压降
    private int entropyNums = 500;//每多少帧数统计一次  熵值
    private int zMaxNums = 5000;//Zmax每多少帧统计一次 所有模型
    private int BatteryNum = 0;
    private int IgnoreMonNum = 0;
    private Long needTime = 20190401L;
    private String VIN;
    private String nowTime;
    private List<String> volatilityDetectionList = new LinkedList<>();//波动性
    private List<String> pressureDropConsistencyList = new LinkedList<>();//压降一致性
    private List<String> entropyList = new LinkedList<>();//熵值
    private Long PstartTime;//数据开始时间   参与统计值会改变
    private Long PzstartTime;//数据开始时间   参与统计值会改变
    private Long VstartTime;//数据开始时间   参与统计值会改变
    private Long VzstartTime;//数据开始时间   参与统计值会改变
    private Long EstartTime;//数据开始时间   参与统计值会改变
    private Long EzstartTime;//数据开始时间   参与统计值会改变
    private Integer pressureDropConsistencySum = 0; //压降总次数
    private Integer volatilityDetectionSum = 0; //波动总次数
    private Integer EntropySum = 0; //熵值总次数
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapDay = new TreeMap<>();//压降每天结果
    private Map<Integer, Map<Long, Integer>> pressureDropConsistencyMapDayW = new TreeMap<>();//压降天W
    private Map<Integer, Map<Long, Integer>> pressureDropConsistencyMapNumsW = new TreeMap<>();//压降1500帧W
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyMapDayZSum = new TreeMap<>();//压降每天压差比的和Z
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek = new TreeMap<>();//压降每周结果
    private Map<Long, Integer> pressureDropConsistencyMapWeekSum = new TreeMap<>();//压降每周总和
    private Map<Long, Integer> pressureDropConsistencyMapNumsSum = new TreeMap<>();//压降每1500帧总和
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyMapWeekZSum = new TreeMap<>();//压降每周压差比的和Z
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyMapNumsZSum = new TreeMap<>();//压降每1500帧压差比的和Z
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapNums = new TreeMap<>();//压降每1500条
    private Map<String, Map<Integer, Integer>> pressureDropConsistencyMapType = new LinkedHashMap<>();//压降充电段
    private Map<Integer, Integer> pressureDropConsistencyMapBatterSum = new TreeMap<>();//压降每个单体出现总次数
    private List<Map<Integer, Double>> pressureDropConsistencyZMaxMapList = new LinkedList<>();//压降计算Zmax使用
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyZMaxNums = new TreeMap<>();//压降每5000帧Zmax


    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay = new TreeMap<>();//波动每天
    private Map<Integer, Map<Long, Integer>> volatilityDetectionMapDayW = new TreeMap<>();//波动W
    private Map<Integer, Map<Long, Integer>> volatilityDetectionMapNumsW = new TreeMap<>();//波动1500帧W
    private Map<Long, Map<Integer, Double>> volatilityDetectionMapDayVSum = new TreeMap<>();//波动每天倍数差的和Z
    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapWeek = new TreeMap<>();//波动每周
    private Map<Long, Integer> volatilityDetectionMapWeekSum = new TreeMap<>();//波动每周总和
    private Map<Long, Integer> volatilityDetectionMapNumsSum = new TreeMap<>();//波动每1500帧总和
    private Map<Long, Map<Integer, Double>> volatilityDetectionMapWeekZSum = new TreeMap<>();//波动每周倍数差的和Z
    private Map<Long, Map<Integer, Double>> volatilityDetectionMapNumsZSum = new TreeMap<>();//波动每1500帧倍数差的和Z
    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapNums = new TreeMap<>();//波动每1500条
    private Map<String, Map<Integer, Integer>> volatilityDetectionMapType = new LinkedHashMap<>();//波动充电段
    private Map<Integer, Integer> volatilityDetectionMapBatterSum = new TreeMap<>();//波动每个单体出现总次数
    private List<Map<Integer, Double>> volatilityDetectionZmaxMapList = new LinkedList<>();//波动计算Zmax使用
    private Map<Long, Map<Integer, Double>> volatilityDetectionZmaxNums = new TreeMap<>();//波动每5000帧Zmax


    private Map<Long, Map<Integer, Integer>> EntropyMapDay = new TreeMap<>();//熵值每天
    private Map<Integer, Map<Long, Integer>> EntropyMapDayW = new TreeMap<>();//熵值W
    private Map<Integer, Map<Long, Integer>> EntropyMapNumsW = new TreeMap<>();//熵值1500帧W
    private Map<Long, Map<Integer, Double>> EntropyMapDayXiShuSum = new TreeMap<>();//熵值每天系数差的和Z
    private Map<Long, Map<Integer, Double>> EntropyMapNumsXiShuSum = new TreeMap<>();//熵值每1500系数差的和Z
    private Map<Long, Map<Integer, Integer>> EntropyMapWeek = new TreeMap<>();//熵值每周
    private Map<Long, Map<Integer, Double>> EntropyMapWeekXiShuSum = new TreeMap<>();//熵值每周系数差总和Z
    private Map<Long, Integer> EntropyMapWeekSum = new TreeMap<>();//熵值每周总和
    private Map<Long, Integer> EntropyMapNumsSum = new TreeMap<>();//熵值每1500帧总和
    private Map<Long, Map<Integer, Integer>> EntropyMapNums = new TreeMap<>();//熵值每1500条
    private Map<String, Map<Integer, Integer>> EntropyMapType = new LinkedHashMap<>();//熵值充电段
    private Map<Integer, Integer> EntropyMapBatterSum = new TreeMap<>();//熵值每个单体出现总次数
    private List<Map<Integer, Double>> EntropyZmaxMapList = new LinkedList<>();//熵值计算Zmax使用
    private Map<Long, Map<Integer, Double>> EntropyZmaxNums = new TreeMap<>();//熵值每5000帧Zmax


    private DecimalFormat df = new DecimalFormat("0.000000000");


    public FrequencyStatistics(int volatilityNums, int pressureNums, int entropyNums, String VIN, List<String> volatilityDetectionList, List<String> pressureDropConsistencyList, List<String> entropyList) {
        this.volatilityNums = volatilityNums;
        this.pressureNums = pressureNums;
        this.entropyNums = entropyNums;
        this.VIN = VIN;
        this.volatilityDetectionList = volatilityDetectionList;
        this.pressureDropConsistencyList = pressureDropConsistencyList;
        this.entropyList = entropyList;
    }

    public FrequencyStatistics(FrequencyStatistics frequencyStatistics) {
        this.volatilityNums = frequencyStatistics.getVolatilityNums();
        this.pressureNums = frequencyStatistics.getPressureNums();
        this.entropyNums = frequencyStatistics.getEntropyNums();
        this.VIN = frequencyStatistics.getVIN();
        this.volatilityDetectionList = frequencyStatistics.getVolatilityDetectionList();
        this.pressureDropConsistencyList = frequencyStatistics.getPressureDropConsistencyList();
        this.entropyList = frequencyStatistics.getEntropyList();
    }

    public void setStartTime(Long dataTime) {
        if (null == VstartTime || dataTime < VstartTime) {
            VstartTime = dataTime;
            VzstartTime = dataTime;
            PstartTime = dataTime;
            EstartTime = dataTime;
            EzstartTime = dataTime;
        }
    }

    /**
     * 处理波动一致性数据
     * 每天和每1500帧
     *
     * @param
     */
    public void doVolatilityDetectionDayAndNums() {
        int nums = 0;
        int numsindex = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每1500条的数据 单体频次
        Map<Integer, Integer> typeMap = new TreeMap<>(); //存放分段数据 单体频次
        Map<Integer, Double> numsSumpMap = new TreeMap<>();//存放每1500条的数据 倍数差的和

        Long lastTime = 0L;
        Long lastStartTime = 0L;
        Long lastEndTime = 0L;

        for (String s : getVolatilityDetectionList()) {
            if (!s.contains("_波动一致性故障诊断模型")) continue;
            int monSigama = 13;//单体和单体值
            int type = 7;//充放电状态
//            int abnormalMon = 6;//异常单体
            int time = 3;//时间
            //获取时间
            Long dataTime = getaLongTime(s);
            if (dataTime < needTime) continue;
            setStartTime(dataTime);
            //读取文件内容
            List<List<String>> lists = ReadToList(s);
            Map<Integer, Integer> tmpMap = getVolatilityDetectionMapNums().get(dataTime);
            if (null == tmpMap) tmpMap = new TreeMap<>(); //存放一天的数据
            Map<Integer, Double> tmpSumMap = getVolatilityDetectionMapDayVSum().get(dataTime);
            if (null == tmpSumMap) tmpSumMap = new TreeMap<>();
            lists.sort((o1, o2) -> new BigDecimal(o1.get(time)).compareTo(new BigDecimal(o2.get(time))));
            Map<Integer, Double> maxVMap = null;
            for (List<String> list : lists) {
                if (!getType().equals(list.get(type))) continue;
                if (list.size() < monSigama + 1) continue;
                /**
                 * 波动倍数差的和
                 */
                String quaList = list.get(monSigama);
                String[] s4 = quaList.split("_");
                int maxMonNum = 0;
                double maxZ = 0.0;
                for (String s3 : s4) {
                    String[] split = s3.split(":");
                    if (split.length == 2) {
                        Integer smon = Integer.parseInt(split[0]);
                        String sv = split[1];
                        if (!isNumber(sv)) sv = "0";
                        Double multiply = new BigDecimal(sv).subtract(new BigDecimal("3")).doubleValue();
                        if (multiply <= 0) continue;
                        if (multiply > maxZ) {
                            maxMonNum = smon;
                            maxZ = multiply;
                        }
                        int monNum = smon;
                        /**
                         * 每天数据
                         */
                        if (tmpMap.containsKey(monNum)) {
                            tmpMap.put(monNum, tmpMap.get(monNum) + 1);
                        } else {
                            tmpMap.put(monNum, 1);
                        }
                        /**
                         * 每1500条数据
                         */
                        if (numsMap.containsKey(monNum)) {
                            numsMap.put(monNum, numsMap.get(monNum) + 1);
                        } else {
                            numsMap.put(monNum, 1);
                        }


                        /**
                         * 每天波动倍数差的和
                         */
                        if (tmpSumMap.containsKey(smon)) {
                            tmpSumMap.put(smon, tmpSumMap.get(smon) + multiply);
                        } else {
                            tmpSumMap.put(smon, multiply);
                        }
                        /**
                         * 每1500帧波动倍数差的和
                         */
                        if (numsSumpMap.containsKey(smon)) {
                            numsSumpMap.put(smon, numsSumpMap.get(smon) + multiply);
                        } else {
                            numsSumpMap.put(smon, multiply);
                        }

                        /**
                         * 充电状态
                         */
                        Long thisTime = Long.valueOf(list.get(time));
                        if (lastTime == 0) {
                            lastTime = thisTime;
                            lastStartTime = thisTime;
                        }
                        Date thisdate = DateUtils.strToDate("" + thisTime);
                        Date lastdate = DateUtils.strToDate("" + lastTime);
                        if (null != lastdate && ((thisdate.getTime() - lastdate.getTime()) / 1000) > getTimeDifference()) {
                            lastTime = thisTime;
                            lastEndTime = thisTime;
                            String startTAndEndT = "" + lastStartTime + "-" + lastEndTime;
                            getVolatilityDetectionMapType().put(startTAndEndT, typeMap);
                            typeMap = new TreeMap<>();
                            lastStartTime = thisTime;
                        } else {
                            if (typeMap.containsKey(monNum)) {
                                typeMap.put(monNum, typeMap.get(monNum) + 1);
                            } else {
                                typeMap.put(monNum, 1);
                            }
                            lastTime = thisTime;
                            lastEndTime = thisTime;
                        }
                    }
                }
                maxVMap = new HashMap<>();
                maxVMap.put(maxMonNum, maxZ);
                getVolatilityDetectionZmaxMapList().add(maxVMap);
                nums++;
                /**
                 * 每1500条数据
                 */
                if (nums >= getVolatilityNums()) {
                    getVolatilityDetectionMapNums().put(numsindex * 1L, numsMap);
                    numsMap = new TreeMap<>();

                    getVolatilityDetectionMapNumsZSum().put(numsindex * 1L, numsSumpMap);
                    numsSumpMap = new TreeMap<>();

                    nums = 0;
                    numsindex++;
                }
            }
            /**
             * 每天数据
             */
            if (tmpMap.size() > 0) {
                getVolatilityDetectionMapDay().put(dataTime, tmpMap);
            }
            if (tmpSumMap.size() > 0) {
                getVolatilityDetectionMapDayVSum().put(dataTime, tmpSumMap);
            }
            if (typeMap.size() > 0) {
                String startTAndEndT = "" + lastStartTime + "-" + lastEndTime;
                getVolatilityDetectionMapType().put(startTAndEndT, typeMap);
            }

        }
        if (nums > 0) {
            getVolatilityDetectionMapNums().put(numsindex * 1L, numsMap);
            getVolatilityDetectionMapNumsZSum().put(numsindex * 1L, numsSumpMap);
        }
    }

    /**
     * 处理波动一致性数据
     * 每周
     *
     * @param
     */
    public void doVolatilityDetectionWeek() {
        //处理存放每周的数据
        if (null != getVolatilityDetectionMapDay() && getVolatilityDetectionMapDay().size() > 0) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getVolatilityDetectionMapDay().entrySet()) {

                if (getVstartTime() == null) setVstartTime(Long.valueOf(longMapEntry.getKey()));
                Long startTime = getWeekLastTime(getVstartTime());//数据开始时间
                while (longMapEntry.getKey() > startTime) {
                    setVstartTime(longMapEntry.getKey());
                    startTime = getWeekLastTime(getVstartTime());
                }
                Map<Integer, Integer> tmpMapWeek = getVolatilityDetectionMapWeek().get(getVstartTime());
                if (null == tmpMapWeek)
                    tmpMapWeek = new TreeMap<>(); //存放一周的数据
                for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    Integer monNum = integerIntegerEntry.getKey();
                    Integer nums = integerIntegerEntry.getValue();
                    //每周单体异常次数
                    if (tmpMapWeek.containsKey(monNum)) {
                        tmpMapWeek.put(monNum, tmpMapWeek.get(monNum) + nums);
                    } else {
                        tmpMapWeek.put(monNum, nums);
                    }
                    //所有单体异常次数
                    setVolatilityDetectionSum(getVolatilityDetectionSum() + nums);
                    //每个单体次数
                    if (getVolatilityDetectionMapBatterSum().containsKey(monNum)) {
                        getVolatilityDetectionMapBatterSum().put(monNum, getVolatilityDetectionMapBatterSum().get(monNum) + nums);
                    } else {
                        getVolatilityDetectionMapBatterSum().put(monNum, nums);
                    }
                }
                getVolatilityDetectionMapWeek().put(getVstartTime(), tmpMapWeek);
            }

        }

        //处理存放每周的数据(波动倍数差的和)

        doZ(getVolatilityDetectionMapDayVSum(), 2);
        doW(getVolatilityDetectionMapDay(), 2);
        doXY(getVolatilityDetectionMapWeek(), 2);

        doZNums(getVolatilityDetectionMapNumsZSum(), 2);
        doWNums(getVolatilityDetectionMapNums(), 2);
        doXYNums(getVolatilityDetectionMapNums(), 2);

        doZmaxNums(getVolatilityDetectionZmaxMapList(), 2);
    }

    /**
     * 处理压降一致性数据
     * 每天和每1500帧
     *
     * @param
     */
    public void doPressureDropConsistencyDayAndNums() {
        int nums = 0;
        int numsindex = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每1500条的数据 单体频次
        Map<Integer, Integer> typeMap = new TreeMap<>(); //存放分段数据 单体频次
        Map<Integer, Double> numsSumMap = new TreeMap<>(); //存放分段数据 压差倍数的差值

        Long lastTime = 0L;
        Long lastStartTime = 0L;
        Long lastEndTime = 0L;

        for (String s : getPressureDropConsistencyList()) {
            if (!s.contains("_压降一致性故障诊断模型")) continue;
            //获取时间
            Long dataTime = getaLongTime(s);
            if (dataTime < needTime) continue;
            setStartTime(dataTime);
            //读取文件内容
            List<List<String>> lists = ReadToList(s);
            Map<Integer, Integer> tmpMap = getPressureDropConsistencyMapDay().get(dataTime);
            if (null == tmpMap) tmpMap = new TreeMap<>(); //存放一天的数据
            Map<Integer, Double> tmpSumMap = getPressureDropConsistencyMapDayZSum().get(dataTime);
            if (null == tmpSumMap) tmpSumMap = new TreeMap<>();
            Iterator<List<String>> iterator = lists.iterator();
            Map<Integer, Double> maxVMap = null;
            while (iterator.hasNext()) {
                List<String> next = iterator.next();
                int maxMon = 12;//最大值编号
                int tyep = 15;//充电状态
                int time = 4;//时间
                int allMonNums = 7;//所有单体电压值
                int maxNums = 14;//绝对值最大压差值
                if (next.size() < tyep + 1) continue;
                if (!getType().equals(next.get(tyep))) continue;

                /**
                 * 压差倍数差的和
                 */
                String s1 = next.get(maxNums);
                if (!isNumber(s1)) s1 = "0";
                if (StringUtils.isNotEmpty(s1) && new BigDecimal(s1).compareTo(new BigDecimal("2")) > 0) {
                    double v = new BigDecimal(s1).subtract(new BigDecimal("2")).doubleValue();
                    List<String> list = next;
                    int monNum = getInt(list.get(maxMon));

                    maxVMap = new HashMap<>();
                    maxVMap.put(monNum, v);
                    getPressureDropConsistencyZMaxMapList().add(maxVMap);
                    /**
                     * 每天数据
                     */
                    if (tmpMap.containsKey(monNum)) {
                        tmpMap.put(monNum, tmpMap.get(monNum) + 1);
                    } else {
                        tmpMap.put(monNum, 1);
                    }
                    /**
                     * 每1500条数据
                     */
                    if (numsMap.containsKey(monNum)) {
                        numsMap.put(monNum, numsMap.get(monNum) + 1);
                    } else {
                        numsMap.put(monNum, 1);
                    }

                    /**
                     * 每天
                     */
                    if (tmpSumMap.containsKey(1)) {
                        tmpSumMap.put(1, tmpSumMap.get(1) + v);
                    } else {
                        tmpSumMap.put(1, v);
                    }
                    /**
                     * 每1500帧
                     */
                    if (numsSumMap.containsKey(1)) {
                        numsSumMap.put(1, numsSumMap.get(1) + v);
                    } else {
                        numsSumMap.put(1, v);
                    }

                    /**
                     * 充电状态
                     */
                    Long thisTime = Long.valueOf(list.get(time));
                    if (lastTime == 0) {
                        lastTime = thisTime;
                        lastStartTime = thisTime;
                    }
                    Date thisdate = DateUtils.strToDate("" + thisTime);
                    Date lastdate = DateUtils.strToDate("" + lastTime);
                    if (null != lastdate && ((thisdate.getTime() - lastdate.getTime()) / 1000) > getTimeDifference()) {
                        lastTime = thisTime;
                        lastEndTime = thisTime;
                        String s3 = "" + lastStartTime + "-" + lastEndTime;
                        getPressureDropConsistencyMapType().put(s3, typeMap);
                        typeMap = new TreeMap<>();
                        lastStartTime = thisTime;
                    } else {
                        if (typeMap.containsKey(monNum)) {
                            typeMap.put(monNum, typeMap.get(monNum) + 1);
                        } else {
                            typeMap.put(monNum, 1);
                        }
                        lastTime = thisTime;
                        lastEndTime = thisTime;
                    }
                } else {
                    maxVMap = new HashMap<>();
                    maxVMap.put(1, 0.0);
                    getPressureDropConsistencyZMaxMapList().add(maxVMap);
                }
                nums++;
                /**
                 * 每1500条数据放入
                 */
                if (nums >= getPressureNums()) {
                    getPressureDropConsistencyMapNums().put(numsindex * 1L, numsMap);
                    numsMap = new TreeMap<>();
                    getPressureDropConsistencyMapNumsZSum().put(numsindex * 1L, numsSumMap);
                    numsSumMap = new TreeMap<>();
                    nums = 0;
                    numsindex++;
                }
                //获取电池单体数量
                if (getBatteryNum() == 0)
                    setBatteryNum(next.get(allMonNums).split("_").length + getIgnoreMonNum());
                //获取vin
                if (getVIN() == null)
                    setVIN(next.get(1));
                //删除不符合条件的数据
//                if (new BigDecimal(next.get(11)).abs().compareTo(new BigDecimal("0.02")) < 0) {
//                    iterator.remove();
//                }
            }
            if (tmpSumMap.size() > 0) {
                getPressureDropConsistencyMapDayZSum().put(dataTime, tmpSumMap);
            }
//            if (lists.size() == 0) continue;
//            //开始统计今天所有数据   将最小压差绝对值小于 0.02 的删除
//            //排序取最大压差绝对值与最小压差绝对值比最大的五条
//            Collections.sort(lists, new Comparator<List<String>>() {
//                @Override
//                public int compare(List<String> o1, List<String> o2) {
//                    if (new BigDecimal(o2.get(14)).compareTo(new BigDecimal(o1.get(14))) != 0)
//                        return new BigDecimal(o2.get(14)).compareTo(new BigDecimal(o1.get(14)));
//                    return new BigDecimal(o2.get(10)).abs().compareTo(new BigDecimal(o1.get(10)).abs());
//                }
//            });
//            //根据规则获取需要保留的数据
//            int size = lists.size();
//            if (size > 5) {
//                int index = 5;
//                int index2 = 5;
//                while (index < size && new BigDecimal(lists.get(index).get(14)).compareTo(new BigDecimal(lists.get(index - 1).get(14))) == 0) {
//                    index++;
//                }
//                if (index > 5) {
//                    while (index2 < index && new BigDecimal(lists.get(index2).get(10)).abs().compareTo(new BigDecimal(lists.get(index2 - 1).get(10)).abs()) == 0) {
//                        index2++;
//                    }
//                }
//                size = index2;
//            }
//            lists.sort((o1, o2) -> new BigDecimal(o1.get(4)).compareTo(new BigDecimal(o2.get(4))));
//            for (int i = 0; i < size; i++) {
//                /**
//                 * 每天数据
//                 */
//                List<String> list = lists.get(i);
//                int monNum = getInt(list.get(12));
//                if (!getType().equals(list.get(15))) continue;
//                if (tmpMap.containsKey(monNum)) {
//                    tmpMap.put(monNum, tmpMap.get(monNum) + 1);
//                } else {
//                    tmpMap.put(monNum, 1);
//                }
//                /**
//                 * 每1500条数据
//                 */
//                if (numsMap.containsKey(monNum)) {
//                    numsMap.put(monNum, numsMap.get(monNum) + 1);
//                } else {
//                    numsMap.put(monNum, 1);
//                }
//                nums++;
//                /**
//                 * 每1500条数据放入
//                 */
//                if (nums >= getPressureNums()) {
//                    getPressureDropConsistencyMapNums().put(numsindex * 1L, numsMap);
//                    numsMap = new TreeMap<>();
//                    nums = 0;
//                    numsindex++;
//                }
//                /**
//                 * 充电状态
//                 */
//                Long thisTime = Long.valueOf(list.get(4));
//                if (lastTime == 0) {
//                    lastTime = thisTime;
//                    lastStartTime = thisTime;
//                }
//                Date thisdate = DateUtils.strToDate("" + thisTime);
//                Date lastdate = DateUtils.strToDate("" + lastTime);
//                if (null != lastdate && ((thisdate.getTime() - lastdate.getTime()) / 1000) > getTimeDifference()) {
//                    lastTime = thisTime;
//                    lastEndTime = thisTime;
//                    String s3 = "" + lastStartTime + "-" + lastEndTime;
//                    getPressureDropConsistencyMapType().put(s3, typeMap);
//                    typeMap = new TreeMap<>();
//                    lastStartTime = thisTime;
//                } else {
//                    if (typeMap.containsKey(monNum)) {
//                        typeMap.put(monNum, typeMap.get(monNum) + 1);
//                    } else {
//                        typeMap.put(monNum, 1);
//                    }
//                    lastTime = thisTime;
//                    lastEndTime = thisTime;
//                }
//            }
            //将当天数据放入
            if (tmpMap.size() > 0) {
                getPressureDropConsistencyMapDay().put(dataTime, tmpMap);
            }
            if (typeMap.size() > 0) {
                String s3 = "" + lastStartTime + "-" + lastEndTime;
                getPressureDropConsistencyMapType().put(s3, typeMap);
            }
        }
        if (nums > 0) {
            getPressureDropConsistencyMapNums().put(numsindex * 1L, numsMap);
            getPressureDropConsistencyMapNumsZSum().put(numsindex * 1L, numsSumMap);
        }

    }

    /**
     * 处理压降一致性数据
     * 每周
     *
     * @param
     */
    public void doPressureDropConsistencyWeek() {
        if (null != getPressureDropConsistencyMapDay() && getPressureDropConsistencyMapDay().size() > 0) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getPressureDropConsistencyMapDay().entrySet()) {
                if (getPstartTime() == null) setPstartTime(Long.valueOf(longMapEntry.getKey()));
                Long startTime = getWeekLastTime(getPstartTime());//获取一周后的时间
                while (longMapEntry.getKey() > startTime) {
                    setPstartTime(longMapEntry.getKey());
                    startTime = getWeekLastTime(getPstartTime());
                }
                Map<Integer, Integer> tmpMapWeek = getPressureDropConsistencyMapWeek().get(getPstartTime());
                if (null == tmpMapWeek)
                    tmpMapWeek = new TreeMap<>(); //存放一周的数据
                for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    Integer monNum = integerIntegerEntry.getKey();
                    Integer nums = integerIntegerEntry.getValue();
                    //每周单体次数
                    if (tmpMapWeek.containsKey(monNum)) {
                        tmpMapWeek.put(monNum, tmpMapWeek.get(monNum) + nums);
                    } else {
                        tmpMapWeek.put(monNum, nums);
                    }
                    //所有单体次数
                    setPressureDropConsistencySum(getPressureDropConsistencySum() + nums);
                    //每个单体次数
                    if (getPressureDropConsistencyMapBatterSum().containsKey(monNum)) {
                        getPressureDropConsistencyMapBatterSum().put(monNum, getPressureDropConsistencyMapBatterSum().get(monNum) + nums);
                    } else {
                        getPressureDropConsistencyMapBatterSum().put(monNum, nums);
                    }
                }
                getPressureDropConsistencyMapWeek().put(getPstartTime(), tmpMapWeek);
            }
        }
        //处理存放每周的数据(最大压差最小压差比的和)
        doZ(getPressureDropConsistencyMapDayZSum(), 3);
        doW(getPressureDropConsistencyMapDay(), 3);
        doXY(getPressureDropConsistencyMapWeek(), 3);

        doZNums(getPressureDropConsistencyMapNumsZSum(), 3);
        doWNums(getPressureDropConsistencyMapNums(), 3);
        doXYNums(getPressureDropConsistencyMapNums(), 3);

        doZmaxNums(getPressureDropConsistencyZMaxMapList(), 3);
    }

    /**
     * 处理熵值故障诊断模型数据
     * 每天和每1500帧
     *
     * @param
     */
    public void doEntropyDayAndNums() {
        int nums = 0;
        int index = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每1500条的数据 单体频次
        Map<Integer, Integer> typeMap = new TreeMap<>(); //存放分段数据 单体频次
        Map<Integer, Double> numsSumMap = new TreeMap<>(); //存放分段数据 系数差的和

        Long lastTime = 0L;
        Long lastStartTime = 0L;
        Long lastEndTime = 0L;
        for (String s : getEntropyList()) {
            if (!s.contains("_熵值故障诊断模型")) continue;
            //获取时间
            Long dataTime = getaLongTime(s);
            if (dataTime < needTime) continue;
            setStartTime(dataTime);
            //读取文件内容
            List<List<String>> lists = ReadToList(s);
            Map<Integer, Integer> tmpMap = getEntropyMapDay().get(dataTime);
            if (null == tmpMap) tmpMap = new TreeMap<>(); //存放一天的数据
            Map<Integer, Double> tmpSumMap = getEntropyMapDayXiShuSum().get(dataTime);
            if (null == tmpSumMap) tmpSumMap = new TreeMap<>(); //存放一天的(系数减去4)的和
            lists.sort((o1, o2) -> new BigDecimal(o1.get(2)).compareTo(new BigDecimal(o2.get(2))));
            Map<Integer, Double> maxVMap = null;
            for (List<String> list : lists) {
                int time = 2;//时间
                int type = 12;//充放电状态
                int maxMon = 8;//最大单体编号
                int maxNums = 9;//最大熵值系数
                int allMonNums = 10;//所有单体熵值系数
                if (list.size() < type + 1) continue;
                if (!getType().equals(list.get(type))) continue;
                String s1 = list.get(maxMon);
                if (StringUtils.isEmpty(s1)) continue;
                int monNum = Integer.parseInt(s1);
                if (getBatteryNum() == 0) {
                    /** 存放单体数量 */
                    setBatteryNum(list.get(allMonNums).split("_").length + getIgnoreMonNum());
                }

                String s2 = list.get(maxNums);
                if (!isNumber(s2)) s2 = "0";
                if (new BigDecimal(s2).compareTo(new BigDecimal("4")) > 0) {
                    /**
                     * 天数据
                     */
                    if (tmpMap.containsKey(monNum)) {
                        tmpMap.put(monNum, tmpMap.get(monNum) + 1);
                    } else {
                        tmpMap.put(monNum, 1);
                    }
                    /**
                     * 天系数差的和数据
                     */
                    double v = new BigDecimal(s2).subtract(new BigDecimal("4")).doubleValue();
                    maxVMap = new HashMap<>();
                    maxVMap.put(monNum, v);
                    getEntropyZmaxMapList().add(maxVMap);
                    if (tmpSumMap.containsKey(monNum)) {
                        tmpSumMap.put(monNum, tmpSumMap.get(monNum) + v);
                    } else {
                        tmpSumMap.put(monNum, v);
                    }
                    /***
                     * 1500帧系数差的和
                     */
                    if (numsSumMap.containsKey(monNum)) {
                        numsSumMap.put(monNum, numsSumMap.get(monNum) + v);
                    } else {
                        numsSumMap.put(monNum, v);
                    }

                    /**
                     * 每1500条数据 单体
                     */
                    if (numsMap.containsKey(monNum)) {
                        numsMap.put(monNum, numsMap.get(monNum) + 1);
                    } else {
                        numsMap.put(monNum, 1);
                    }
                    /**
                     * 充电状态
                     */
                    Long thisTime = Long.valueOf(list.get(time));
                    if (lastTime == 0) {
                        lastTime = thisTime;
                        lastStartTime = thisTime;
                    }
                    Date thisdate = DateUtils.strToDate("" + thisTime);
                    Date lastdate = DateUtils.strToDate("" + lastTime);
                    if (null != lastdate && ((thisdate.getTime() - lastdate.getTime()) / 1000) > getTimeDifference()) {
                        lastTime = thisTime;
                        lastEndTime = thisTime;
                        String s3 = "" + lastStartTime + "-" + lastEndTime;
                        getEntropyMapType().put(s3, typeMap);
                        typeMap = new TreeMap<>();
                        lastStartTime = thisTime;
                    } else {
                        if (typeMap.containsKey(monNum)) {
                            typeMap.put(monNum, typeMap.get(monNum) + 1);
                        } else {
                            typeMap.put(monNum, 1);
                        }
                        lastTime = thisTime;
                        lastEndTime = thisTime;
                    }
                } else {
                    maxVMap = new HashMap<>();
                    maxVMap.put(1, 0.0);
                    getEntropyZmaxMapList().add(maxVMap);
                }
                nums++;
                /**
                 * 每1500条数据 存储
                 */
                if (nums >= getEntropyNums()) {
                    getEntropyMapNums().put(index * 1L, numsMap);
                    numsMap = new TreeMap<>();
                    getEntropyMapNumsXiShuSum().put(index * 1L, numsSumMap);
                    numsSumMap = new TreeMap<>();
                    nums = 0;
                    index++;
                }

            }
            /**
             * 天数据
             */
            if (tmpMap.size() > 0) {
                getEntropyMapDay().put(dataTime, tmpMap);
            }
            if (tmpSumMap.size() > 0) {
                getEntropyMapDayXiShuSum().put(dataTime, tmpSumMap);
            }
            if (typeMap.size() > 0) {
                String s3 = "" + lastStartTime + "-" + lastEndTime;
                getEntropyMapType().put(s3, typeMap);
            }
        }
        /**
         * 1500条数据
         */
        if (nums > 0) {
            getEntropyMapNums().put(index * 1L, numsMap);
            getEntropyMapNumsXiShuSum().put(index * 1L, numsSumMap);
        }
    }

    /**
     * 处理熵值故障诊断模型数据
     * 每周
     *
     * @param
     */
    public void doEntropyWeek() {
        //处理存放每周的数据
        if (null != getEntropyMapDay() && getEntropyMapDay().size() > 0) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getEntropyMapDay().entrySet()) {
                if (getEstartTime() == null) setEstartTime(longMapEntry.getKey());
                Long startTime = getWeekLastTime(getEstartTime());
                while (longMapEntry.getKey() > startTime) {//如果当前时间小于startTime则为当前周的数据 否则则是新一周的数据  需要更新main.srartTime 并且添加新的数据到Mapweek
                    setEstartTime(longMapEntry.getKey());
                    startTime = getWeekLastTime(getEstartTime());
                }
                Map<Integer, Integer> tmpMapWeek = getEntropyMapWeek().get(getEstartTime());
                if (null == tmpMapWeek)
                    tmpMapWeek = new TreeMap<>(); //存放一周的数据
                for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    Integer monNum = integerIntegerEntry.getKey();
                    Integer nums = integerIntegerEntry.getValue();
                    //每周单体次数
                    if (tmpMapWeek.containsKey(monNum)) {
                        tmpMapWeek.put(monNum, tmpMapWeek.get(monNum) + nums);
                    } else {
                        tmpMapWeek.put(monNum, nums);
                    }
                    //所有单体次数
                    setEntropySum(getEntropySum() + nums);
                    //每个单体次数
                    if (getEntropyMapBatterSum().containsKey(monNum)) {
                        getEntropyMapBatterSum().put(monNum, getEntropyMapBatterSum().get(monNum) + nums);
                    } else {
                        getEntropyMapBatterSum().put(monNum, nums);
                    }
                }
                getEntropyMapWeek().put(getEstartTime(), tmpMapWeek);
            }
        }
        //处理存放每1500帧的数据(系数差的和)
        doZ(getEntropyMapDayXiShuSum(), 1);
        doW(getEntropyMapDay(), 1);
        doXY(getEntropyMapWeek(), 1);

        doZNums(getEntropyMapNumsXiShuSum(), 1);
        doWNums(getEntropyMapNums(), 1);
        doXYNums(getEntropyMapNums(), 1);

        doZmaxNums(getEntropyZmaxMapList(), 1);
    }

    /**
     * 处理Zmax
     *
     * @param volatilityDetectionZmaxMapList
     * @param type
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/6 3:18 下午
     */
    private void doZmaxNums(List<Map<Integer, Double>> volatilityDetectionZmaxMapList, int type) {
        if (null != volatilityDetectionZmaxMapList) {
            int size = volatilityDetectionZmaxMapList.size();
            if (size > 0) {
                long index = 0L;
                if (size % getZMaxNums() == 0) {
                    index = size / getZMaxNums();
                } else {
                    index = size / getZMaxNums() + 1;
                }
                int nums = 0;
                Map<Integer, Double> tmpMap = new HashMap<>();
                if (1 == type) {
                    for (int i = size - 1; i >= 0; i--) {
                        nums++;
                        Map<Integer, Double> integerDoubleMap = volatilityDetectionZmaxMapList.get(i);
                        for (Map.Entry<Integer, Double> integerDoubleEntry : integerDoubleMap.entrySet()) {
                            Integer monNum = integerDoubleEntry.getKey();
                            double v = integerDoubleEntry.getValue();
                            if (tmpMap.containsKey(monNum)) {
                                tmpMap.put(monNum, tmpMap.get(monNum) + v);
                            } else {
                                tmpMap.put(monNum, v);
                            }
                        }
                        if (nums >= getZMaxNums()) {
                            getEntropyZmaxNums().put(index, tmpMap);
                            tmpMap = new HashMap<>();
                            nums = 0;
                            index--;
                        }
                    }
                    getEntropyZmaxNums().put(index, tmpMap);
                } else if (2 == type) {
                    for (int i = size - 1; i >= 0; i--) {
                        nums++;
                        Map<Integer, Double> integerDoubleMap = volatilityDetectionZmaxMapList.get(i);
                        for (Map.Entry<Integer, Double> integerDoubleEntry : integerDoubleMap.entrySet()) {
                            Integer monNum = integerDoubleEntry.getKey();
                            double v = integerDoubleEntry.getValue();
                            if (tmpMap.containsKey(monNum)) {
                                tmpMap.put(monNum, tmpMap.get(monNum) + v);
                            } else {
                                tmpMap.put(monNum, v);
                            }
                        }
                        if (nums >= getZMaxNums()) {
                            getVolatilityDetectionZmaxNums().put(index, tmpMap);
                            tmpMap = new HashMap<>();
                            nums = 0;
                            index--;
                        }
                    }
                    getVolatilityDetectionZmaxNums().put(index, tmpMap);
                } else if (3 == type) {
                    for (int i = size - 1; i >= 0; i--) {
                        nums++;
                        Map<Integer, Double> integerDoubleMap = volatilityDetectionZmaxMapList.get(i);
                        for (Map.Entry<Integer, Double> integerDoubleEntry : integerDoubleMap.entrySet()) {
                            Integer monNum = integerDoubleEntry.getKey();
                            double v = integerDoubleEntry.getValue();
                            if (tmpMap.containsKey(monNum)) {
                                tmpMap.put(monNum, tmpMap.get(monNum) + v);
                            } else {
                                tmpMap.put(monNum, v);
                            }
                        }
                        if (nums >= getZMaxNums()) {
                            getPressureDropConsistencyZMaxNums().put(index, tmpMap);
                            tmpMap = new HashMap<>();
                            nums = 0;
                            index--;
                        }
                    }
                    getPressureDropConsistencyZMaxNums().put(index, tmpMap);
                }
            }
        }
    }

    private void doZNums(Map<Long, Map<Integer, Double>> zSum, int type) {
        if (null != zSum && zSum.size() > 0) {

            if (1 == type) {
                Map<Integer, Double> tmpMap = null;
                for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : zSum.entrySet()) {
                    Map<Integer, Double> value = longMapEntry.getValue();
                    Double sum = 0.0;
                    for (Map.Entry<Integer, Double> integerDoubleEntry : value.entrySet()) {
                        sum += integerDoubleEntry.getValue();
                    }
                    tmpMap = new TreeMap<>();
                    tmpMap.put(1, sum);
                    getEntropyMapNumsXiShuSum().put(longMapEntry.getKey(), tmpMap);
                }
            } else if (2 == type) {
                Map<Integer, Double> tmpMap = null;
                for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : zSum.entrySet()) {
                    Map<Integer, Double> value = longMapEntry.getValue();
                    Double sum = 0.0;
                    for (Map.Entry<Integer, Double> integerDoubleEntry : value.entrySet()) {
                        sum += integerDoubleEntry.getValue();
                    }
                    tmpMap = new TreeMap<>();
                    tmpMap.put(1, sum);
                    getVolatilityDetectionMapNumsZSum().put(longMapEntry.getKey(), tmpMap);
                }
            } else if (3 == type) {
                Map<Integer, Double> tmpMap = null;
                for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : zSum.entrySet()) {
                    Map<Integer, Double> value = longMapEntry.getValue();
                    Double sum = 0.0;
                    for (Map.Entry<Integer, Double> integerDoubleEntry : value.entrySet()) {
                        sum += integerDoubleEntry.getValue();
                    }
                    tmpMap = new TreeMap<>();
                    tmpMap.put(1, sum);
                    getPressureDropConsistencyMapNumsZSum().put(longMapEntry.getKey(), tmpMap);
                }
            }
        }
    }

    /**
     * 统计z的数据
     *
     * @param zSum
     * @param type
     * @return void
     * @author liwenjie
     * @date 2020/6/9 8:46
     */
    private void doZ(Map<Long, Map<Integer, Double>> zSum, int type) {
        if (null != zSum && zSum.size() > 0) {
            if (1 == type) {
                if (null != zSum && zSum.size() > 0) {
                    for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : zSum.entrySet()) {
                        if (getEzstartTime() == null) setEzstartTime(longMapEntry.getKey());
                        Long startTime = getWeekLastTime(getEzstartTime());//
                        while (longMapEntry.getKey() > startTime) {
                            //如果当前时间小于startTime则为当前周的数据 否则则是新一周的数据  需要更新main.srartTime 并且添加新的数据到Mapweek
                            setEzstartTime(longMapEntry.getKey());
                            startTime = getWeekLastTime(getEzstartTime());
                        }
                        Map<Integer, Double> tmpMapWeek = getEntropyMapWeekXiShuSum().get(getEzstartTime());
                        if (null == tmpMapWeek) {
                            tmpMapWeek = new TreeMap<>(); //存放一周的数据
                        }
                        for (Map.Entry<Integer, Double> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                            Integer monNum = integerIntegerEntry.getKey();
                            Double nums = integerIntegerEntry.getValue();
                            if (tmpMapWeek.containsKey(monNum)) {
                                tmpMapWeek.put(monNum, tmpMapWeek.get(monNum) + nums);
                            } else {
                                tmpMapWeek.put(monNum, nums);
                            }
                        }
                        getEntropyMapWeekXiShuSum().put(getEzstartTime(), tmpMapWeek);
                    }
                }
            } else if (2 == type) {
                if (null != zSum && zSum.size() > 0) {
                    for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : zSum.entrySet()) {
                        if (getVzstartTime() == null) setVzstartTime(longMapEntry.getKey());
                        Long startTime = getWeekLastTime(getVzstartTime());//
                        while (longMapEntry.getKey() > startTime) {
                            //如果当前时间小于startTime则为当前周的数据 否则则是新一周的数据  需要更新main.srartTime 并且添加新的数据到Mapweek
                            setVzstartTime(longMapEntry.getKey());
                            startTime = getWeekLastTime(getVzstartTime());
                        }
                        Map<Integer, Double> tmpMapWeek = getVolatilityDetectionMapWeekZSum().get(getVzstartTime());
                        if (null == tmpMapWeek) {
                            tmpMapWeek = new TreeMap<>(); //存放一周的数据
                        }
                        for (Map.Entry<Integer, Double> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                            Integer monNum = integerIntegerEntry.getKey();
                            Double nums = integerIntegerEntry.getValue();
                            if (tmpMapWeek.containsKey(monNum)) {
                                tmpMapWeek.put(monNum, tmpMapWeek.get(monNum) + nums);
                            } else {
                                tmpMapWeek.put(monNum, nums);
                            }
                        }
                        getVolatilityDetectionMapWeekZSum().put(getVzstartTime(), tmpMapWeek);
                    }
                }
            } else if (3 == type) {
                for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : zSum.entrySet()) {
                    if (getPzstartTime() == null) setPzstartTime(longMapEntry.getKey());
                    Long startTime = getWeekLastTime(getPzstartTime());//
                    while (longMapEntry.getKey() > startTime) {
                        //如果当前时间小于startTime则为当前周的数据 否则则是新一周的数据  需要更新main.srartTime 并且添加新的数据到Mapweek
                        setPzstartTime(longMapEntry.getKey());
                        startTime = getWeekLastTime(getPzstartTime());
                    }
                    Map<Integer, Double> tmpMapWeek = getPressureDropConsistencyMapWeekZSum().get(getPzstartTime());
                    if (null == tmpMapWeek) {
                        tmpMapWeek = new TreeMap<>(); //存放一周的数据
                    }
                    for (Map.Entry<Integer, Double> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                        Integer monNum = integerIntegerEntry.getKey();
                        Double nums = integerIntegerEntry.getValue();
                        if (tmpMapWeek.containsKey(monNum)) {
                            tmpMapWeek.put(monNum, tmpMapWeek.get(monNum) + nums);
                        } else {
                            tmpMapWeek.put(monNum, nums);
                        }
                    }
                    getPressureDropConsistencyMapWeekZSum().put(getPzstartTime(), tmpMapWeek);
                }
            }


        }
    }

    /**
     * 求 W
     *
     * @param mapDay
     * @param type   1 熵值 2 波动 3 压降
     * @return java.util.Map<java.lang.Long, java.lang.Integer>
     * @author liwenjie
     * @date 2020/6/5 13:59
     */
    public void doW(Map<Long, Map<Integer, Integer>> mapDay, int type) {
        Map<Integer, Map<Long, Integer>> dayNumMap = new TreeMap<>();
        if (null != mapDay && mapDay.size() > 0) {
            Set<Long> longs = mapDay.keySet();
            for (int i = 0; i < getBatteryNum(); i++) {
                Map<Long, Integer> longIntegerMap = new TreeMap<>();
                for (Long aLong : longs) {
                    longIntegerMap.put(aLong, getIntegerNum(mapDay.get(aLong).get(i + 1)));
                }
                dayNumMap.put(i + 1, longIntegerMap);
            }
        }
        Map<Integer, Map<Long, Integer>> resultDayNumMap = new TreeMap<>();
        for (Map.Entry<Integer, Map<Long, Integer>> integerMapEntry : dayNumMap.entrySet()) {
            Integer key = integerMapEntry.getKey();
            Map<Long, Integer> value = integerMapEntry.getValue();
            int last = 0;
            Map<Long, Integer> longIntegerMap = new TreeMap<>();
            for (Map.Entry<Long, Integer> longIntegerEntry : value.entrySet()) {
                longIntegerMap.put(longIntegerEntry.getKey(), longIntegerEntry.getValue() - last);
                last = longIntegerEntry.getValue();
            }
            resultDayNumMap.put(key, longIntegerMap);
        }
        if (1 == type) {
            setEntropyMapDayW(resultDayNumMap);
        } else if (2 == type) {
            setVolatilityDetectionMapDayW(resultDayNumMap);
        } else if (3 == type) {
            setPressureDropConsistencyMapDayW(resultDayNumMap);
        }
    }

    public void doWNums(Map<Long, Map<Integer, Integer>> mapDay, int type) {
        Map<Integer, Map<Long, Integer>> dayNumMap = new TreeMap<>();
        if (null != mapDay && mapDay.size() > 0) {
            Set<Long> longs = mapDay.keySet();
            for (int i = 0; i < getBatteryNum(); i++) {
                Map<Long, Integer> longIntegerMap = new TreeMap<>();
                for (Long aLong : longs) {
                    longIntegerMap.put(aLong, getIntegerNum(mapDay.get(aLong).get(i + 1)));
                }
                dayNumMap.put(i + 1, longIntegerMap);
            }
        }
        Map<Integer, Map<Long, Integer>> resultDayNumMap = new TreeMap<>();
        for (Map.Entry<Integer, Map<Long, Integer>> integerMapEntry : dayNumMap.entrySet()) {
            Integer key = integerMapEntry.getKey();
            Map<Long, Integer> value = integerMapEntry.getValue();
            int last = 0;
            Map<Long, Integer> longIntegerMap = new TreeMap<>();
            for (Map.Entry<Long, Integer> longIntegerEntry : value.entrySet()) {
                longIntegerMap.put(longIntegerEntry.getKey(), longIntegerEntry.getValue() - last);
                last = longIntegerEntry.getValue();
            }
            resultDayNumMap.put(key, longIntegerMap);
        }
        if (1 == type) {
            setEntropyMapNumsW(resultDayNumMap);
        } else if (2 == type) {
            setVolatilityDetectionMapNumsW(resultDayNumMap);
        } else if (3 == type) {
            setPressureDropConsistencyMapNumsW(resultDayNumMap);
        }
    }

    /**
     * 求 X Y
     *
     * @param weekDay
     * @param
     * @return java.util.Map<java.lang.Long, java.lang.Integer>
     * @author liwenjie
     * @date 2020/6/5 13:59
     */
    public void doXY(Map<Long, Map<Integer, Integer>> weekDay, int type) {
        Map<Long, Integer> mapWeekSum = new TreeMap<>();
        if (null != weekDay && weekDay.size() > 0) {

            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : weekDay.entrySet()) {
                Integer weekSum = mapWeekSum.get(longMapEntry.getKey());
                Map<Integer, Integer> dayValue = longMapEntry.getValue();
                if (null == weekSum) weekSum = 0;
                for (Map.Entry<Integer, Integer> integerIntegerEntry : dayValue.entrySet()) {
                    weekSum += integerIntegerEntry.getValue();
                }
                mapWeekSum.put(longMapEntry.getKey(), weekSum);
            }
        }
        if (1 == type) {
            setEntropyMapWeekSum(mapWeekSum);
        } else if (2 == type) {
            setVolatilityDetectionMapWeekSum(mapWeekSum);
        } else if (3 == type) {
            setPressureDropConsistencyMapWeekSum(mapWeekSum);
        }
    }

    /**
     * 求 X Y
     *
     * @param weekDay
     * @param
     * @return java.util.Map<java.lang.Long, java.lang.Integer>
     * @author liwenjie
     * @date 2020/6/5 13:59
     */
    public void doXYNums(Map<Long, Map<Integer, Integer>> weekDay, int type) {
        Map<Long, Integer> mapWeekSum = new TreeMap<>();
        if (null != weekDay && weekDay.size() > 0) {

            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : weekDay.entrySet()) {
                Integer weekSum = mapWeekSum.get(longMapEntry.getKey());
                Map<Integer, Integer> dayValue = longMapEntry.getValue();
                if (null == weekSum) weekSum = 0;
                for (Map.Entry<Integer, Integer> integerIntegerEntry : dayValue.entrySet()) {
                    weekSum += integerIntegerEntry.getValue();
                }
                mapWeekSum.put(longMapEntry.getKey(), weekSum);
            }
        }
        if (1 == type) {
            setEntropyMapNumsSum(mapWeekSum);
        } else if (2 == type) {
            setVolatilityDetectionMapNumsSum(mapWeekSum);
        } else if (3 == type) {
            setPressureDropConsistencyMapNumsSum(mapWeekSum);
        }
    }


    /**
     * 获取文件数据日期
     *
     * @param s
     * @return
     */
    private Long getaLongTime(String s) {
        String[] split = s.split("/");
        String[] times = split[split.length - 1].split("_");
        String time1 = times[1];
        return Long.valueOf(time1);
    }

    /**
     * 两数相除
     *
     * @param integer
     * @param pressureDropConsistencySum
     * @return
     */
    private String getDouble(Integer integer, Integer pressureDropConsistencySum) {
        df.setRoundingMode(RoundingMode.HALF_UP);
        if (null == integer) {
            return df.format(0.0);
        }
        return df.format(integer.doubleValue() / pressureDropConsistencySum);
    }

    /**
     * 获取一周后的时间
     *
     * @param startTime
     * @return
     */
    private long getWeekLastTime(Long startTime) {
        if (startTime == null || startTime.toString().length() != 8) return startTime;
        Date date = DateUtils.parseDate(startTime.toString());
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DATE, 6);
        String s = DateUtils.parseDateToStr(DateUtils.YYYYMMDD, instance.getTime());
        return Long.valueOf(s);
    }

    /**
     * 输出波动一致性每天数据
     */
    public void outVolatilityDetection(String outPath) {
        Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay = getVolatilityDetectionMapDay();
        if (null == volatilityDetectionMapDay || volatilityDetectionMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, volatilityDetectionMapDay, "波动一致性故障诊断模型", "日");
        Map<Long, Map<Integer, Integer>> volatilityDetectionMapWeek = getVolatilityDetectionMapWeek();
        if (null == volatilityDetectionMapDay || volatilityDetectionMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, volatilityDetectionMapWeek, "波动一致性故障诊断模型", "周");
        Map<Long, Map<Integer, Integer>> volatilityDetectionMapNums = getVolatilityDetectionMapNums();
        if (null == volatilityDetectionMapNums || volatilityDetectionMapNums.size() == 0) return;
        BaseWriteToCSV(outPath, volatilityDetectionMapNums, "波动一致性故障诊断模型", "每" + getVolatilityNums() + "帧");

        ResultWriteToCsv(outPath, getVolatilityDetectionMapBatterSum(), "波动一致性故障诊断模型", getVolatilityDetectionSum());
        ResultWriteToCsvWeek(outPath, getVolatilityDetectionMapWeek(), "波动一致性故障诊断模型");

        ResultWriteToCsvX(outPath, getVolatilityDetectionMapWeekSum(), "波动一致性故障诊断模型_周", "X_Y");
        ResultWriteToCsvW(outPath, getVolatilityDetectionMapDayW(), "波动一致性故障诊断模型_日", "W");
        ResultWriteToCsvZ(outPath, getVolatilityDetectionMapWeekZSum(), "波动一致性故障诊断模型_周", "Z");

        ResultWriteToCsvX(outPath, getVolatilityDetectionMapNumsSum(), "波动一致性故障诊断模型_500帧", "X_Y");
        ResultWriteToCsvW(outPath, getVolatilityDetectionMapNumsW(), "波动一致性故障诊断模型_500帧", "W");
        ResultWriteToCsvZ(outPath, getVolatilityDetectionMapNumsZSum(), "波动一致性故障诊断模型_500帧", "Z");

        ResultWriteToCsvZmax(outPath, getVolatilityDetectionZmaxNums(), "波动一致性故障诊断模型_5000帧", "Zmax");


        ResultWriteToCsvType(outPath, getVolatilityDetectionMapType(), "波动一致性故障诊断模型");
    }


    /**
     * 输出压降一致性每天数据
     */
    public void outPressureDropConsistency(String outPath) {
        Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapDay = getPressureDropConsistencyMapDay();
        if (null == pressureDropConsistencyMapDay || pressureDropConsistencyMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, pressureDropConsistencyMapDay, "压降一致性故障诊断模型", "日");
        Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek = getPressureDropConsistencyMapWeek();
        if (null == pressureDropConsistencyMapWeek || pressureDropConsistencyMapWeek.size() == 0) return;
        BaseWriteToCSV(outPath, pressureDropConsistencyMapWeek, "压降一致性故障诊断模型", "周");
        Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapNums = getPressureDropConsistencyMapNums();
        if (null == pressureDropConsistencyMapNums || pressureDropConsistencyMapNums.size() == 0) return;
        BaseWriteToCSV(outPath, pressureDropConsistencyMapNums, "压降一致性故障诊断模型", "每" + getPressureNums() + "帧");

        ResultWriteToCsv(outPath, getPressureDropConsistencyMapBatterSum(), "压降一致性故障诊断模型", getPressureDropConsistencySum());
        ResultWriteToCsvWeek(outPath, getPressureDropConsistencyMapWeek(), "压降一致性故障诊断模型");

        ResultWriteToCsvX(outPath, getPressureDropConsistencyMapWeekSum(), "压降一致性故障诊断模型_周", "X_Y");
        ResultWriteToCsvW(outPath, getPressureDropConsistencyMapDayW(), "压降一致性故障诊断模型_日", "W");
        ResultWriteToCsvZ(outPath, getPressureDropConsistencyMapWeekZSum(), "压降一致性故障诊断模型_周", "Z");

        ResultWriteToCsvX(outPath, getPressureDropConsistencyMapNumsSum(), "压降一致性故障诊断模型_500帧", "X_Y");
        ResultWriteToCsvW(outPath, getPressureDropConsistencyMapNumsW(), "压降一致性故障诊断模型_500帧", "W");
        ResultWriteToCsvZ(outPath, getPressureDropConsistencyMapNumsZSum(), "压降一致性故障诊断模型_500帧", "Z");

        ResultWriteToCsvZmax(outPath, getPressureDropConsistencyZMaxNums(), "压降一致性故障诊断模型_5000帧", "Zmax");

        ResultWriteToCsvType(outPath, getPressureDropConsistencyMapType(), "压降一致性故障诊断模型");

    }

    /**
     * 输出熵值每天数据
     */
    public void outEntropy(String outPath) {
        Map<Long, Map<Integer, Integer>> entropyMapDay = getEntropyMapDay();
        if (null == entropyMapDay || entropyMapDay.size() == 0) return;
        BaseWriteToCSV(outPath, entropyMapDay, "熵值故障诊断模型", "日");
        Map<Long, Map<Integer, Integer>> entropyMapWeek = getEntropyMapWeek();
        if (null == entropyMapWeek || entropyMapWeek.size() == 0) return;
        BaseWriteToCSV(outPath, entropyMapWeek, "熵值故障诊断模型", "周");
        Map<Long, Map<Integer, Integer>> entropyMapNums = getEntropyMapNums();
        if (null == entropyMapNums || entropyMapNums.size() == 0) return;
        BaseWriteToCSV(outPath, entropyMapNums, "熵值故障诊断模型", "每" + getEntropyNums() + "帧");

        ResultWriteToCsv(outPath, getEntropyMapBatterSum(), "熵值故障诊断模型", getEntropySum());
        ResultWriteToCsvWeek(outPath, getEntropyMapWeek(), "熵值故障诊断模型");

        Map<Long, Map<Integer, Double>> entropyMapWeekXiShuSum = getEntropyMapWeekXiShuSum();
        if (null == entropyMapWeekXiShuSum || entropyMapWeekXiShuSum.size() == 0) return;
        BaseWriteToCSVXiShu(outPath, entropyMapWeekXiShuSum, "熵值故障诊断模型", "周");
//
        ResultWriteToCsvX(outPath, getEntropyMapWeekSum(), "熵值故障诊断模型_周", "X_Y");
        ResultWriteToCsvW(outPath, getEntropyMapDayW(), "熵值故障诊断模型_日", "W");
        ResultWriteToCsvZ(outPath, getEntropyMapWeekXiShuSum(), "熵值故障诊断模型_周", "Z");

        ResultWriteToCsvX(outPath, getEntropyMapNumsSum(), "熵值故障诊断模型_500帧", "X_Y");
        ResultWriteToCsvW(outPath, getEntropyMapNumsW(), "熵值故障诊断模型_500帧", "W");
        ResultWriteToCsvZ(outPath, getEntropyMapNumsXiShuSum(), "熵值故障诊断模型_500帧", "Z");

        ResultWriteToCsvZmax(outPath, getEntropyZmaxNums(), "熵值故障诊断模型_5000帧", "Zmax");

        ResultWriteToCsvType(outPath, getEntropyMapType(), "熵值故障诊断模型");
    }

    /**
     * Zmax 结果输出
     *
     * @param filename
     * @param entropyZmaxNums
     * @param s               熵值故障诊断模型_5000帧
     * @param s1              zmax
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/6 7:42 下午
     */
    private void ResultWriteToCsvZmax(String filename, Map<Long, Map<Integer, Double>> entropyZmaxNums, String s, String s1) {
        Set<Long> longs = entropyZmaxNums.keySet();
        //写表头
        StringBuffer title = new StringBuffer(1000);
        title.append("单体编号");
        for (Long aLong : longs) {
            title.append("," + aLong);
        }
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-充电.csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename)), encode);
            bw = new BufferedWriter(ow);
            bw.write(title.toString()); //中间，隔开不同的单元格，一次写一行
            bw.newLine();

            for (int i = 0; i < getBatteryNum(); i++) {
                StringBuffer content = new StringBuffer(1000);
                content.append(i + 1);
                for (Long aLong : longs) {
                    content.append("," + getNum(entropyZmaxNums.get(aLong).get(i + 1)));
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
     * 按段输出(充电段或行驶段)
     *
     * @param outPath
     * @param pressureDropConsistencyMapType
     * @param s
     */
    private void ResultWriteToCsvType(String outPath, Map<String, Map<Integer, Integer>> pressureDropConsistencyMapType, String s) {
        Map<Integer, Map<String, Integer>> dayNumMap = new TreeMap<>();
        if (null != pressureDropConsistencyMapType && pressureDropConsistencyMapType.size() > 0) {
            Set<String> longs = pressureDropConsistencyMapType.keySet();
            for (int i = 0; i < getBatteryNum(); i++) {
                Map<String, Integer> longIntegerMap = new TreeMap<>();
                for (String aLong : longs) {
                    longIntegerMap.put(aLong, getIntegerNum(pressureDropConsistencyMapType.get(aLong).get(i + 1)));
                }
                dayNumMap.put(i + 1, longIntegerMap);
            }
        }
        typeWriteToCsv(outPath, dayNumMap, s, "频次");
        Map<Integer, Map<String, Integer>> resultDayNumMap = new TreeMap<>();
        for (Map.Entry<Integer, Map<String, Integer>> integerMapEntry : dayNumMap.entrySet()) {
            Integer key = integerMapEntry.getKey();
            Map<String, Integer> value = integerMapEntry.getValue();
            int last = 0;
            Map<String, Integer> longIntegerMap = new TreeMap<>();
            for (Map.Entry<String, Integer> longIntegerEntry : value.entrySet()) {
                longIntegerMap.put(longIntegerEntry.getKey(), longIntegerEntry.getValue() - last);
                last = longIntegerEntry.getValue();
            }
            resultDayNumMap.put(key, longIntegerMap);
        }
        typeWriteToCsv(outPath, resultDayNumMap, s, "频次差");
    }

    /**
     * 最后结果输出周   W
     * 每天的次数和
     *
     * @param filename
     * @param
     * @param s
     */
    private void typeWriteToCsv(String filename, Map<Integer, Map<String, Integer>> sumMap, String s, String s2) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-放电段-" + s2 + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-充电段-" + s2 + ".csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            int i = 0;
            for (Map.Entry<Integer, Map<String, Integer>> integerMapEntry : sumMap.entrySet()) {
                Integer key = integerMapEntry.getKey();
                Map<String, Integer> value = integerMapEntry.getValue();
                if (i == 0) {
                    Set<String> longs = value.keySet();
                    StringBuffer title = new StringBuffer(1000);
                    title.append("时间段");
                    for (String aLong : longs) {
                        title.append("," + aLong);
                    }
                    bw.write(title.toString());
                    bw.newLine();//中间，隔开不同的单元格，一次写一行
                    i++;
                }
                StringBuffer text = new StringBuffer(1000);
                text.append(key);
                for (Map.Entry<String, Integer> longIntegerEntry : value.entrySet()) {
                    text.append(",").append(longIntegerEntry.getValue());
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
     * 最后结果输出天
     *
     * @param filename                            输出文件名字
     * @param pressureDropConsistencyMapBatterSum 输出内容
     * @param s                                   模型名
     * @param sum                                 频次总和
     */
    private void ResultWriteToCsv(String filename, Map<Integer, Integer> pressureDropConsistencyMapBatterSum, String s, Integer sum) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            df.setRoundingMode(RoundingMode.HALF_UP);
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-全生命周期异常率-放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-全生命周期异常率-充电.csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            bw.write("单体编号,全生命周期(率)"); //中间，隔开不同的单元格，一次写一行
            bw.newLine();
            for (int i = 0; i < getBatteryNum(); i++) {
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
     * @param filename
     * @param pressureDropConsistencyMapWeek
     * @param s
     */
    private void ResultWriteToCsvWeek(String filename, Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek, String s) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-周异常率-放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-周异常率-充电.csv";
            }
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
            for (int i = 0; i < getBatteryNum(); i++) {
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
     * 最后结果输出周   X
     * 先求每周所有单体异常次数之和得 x_n
     * 然后逐次递减得  x_n_n
     *
     * @param filename
     * @param
     * @param s
     */
    private void ResultWriteToCsvX(String filename, Map<Long, Integer> sumMap, String s, String s1) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-充电.csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            Set<Long> longs = sumMap.keySet();
            StringBuffer title = new StringBuffer(1000);
            title.append("日期");
            for (Long aLong : longs) {
                title.append("," + aLong);
            }
            bw.write(title.toString());
            bw.newLine();//中间，隔开不同的单元格，一次写一行
            StringBuffer text = new StringBuffer(1000);
            StringBuffer textXnn = new StringBuffer(1000);
            text.append("X");
            textXnn.append("Y");
            int tmp = 0;
            for (Long aLong : longs) {
                text.append(",").append(sumMap.get(aLong));
                textXnn.append(",").append(sumMap.get(aLong) - tmp);
                tmp = sumMap.get(aLong);
            }
            bw.write(text.toString());
            bw.newLine();
            bw.write(textXnn.toString());
            bw.newLine();
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
     * 最后结果输出周   W
     * 每天的次数和
     *
     * @param filename
     * @param
     * @param s
     */
    private void ResultWriteToCsvW(String filename, Map<Integer, Map<Long, Integer>> sumMap, String s, String s1) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-充电.csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            int i = 0;
            for (Map.Entry<Integer, Map<Long, Integer>> integerMapEntry : sumMap.entrySet()) {
                Integer key = integerMapEntry.getKey();
                Map<Long, Integer> value = integerMapEntry.getValue();
                if (i == 0) {
                    Set<Long> longs = value.keySet();
                    StringBuffer title = new StringBuffer(1000);
                    title.append("日期");
                    for (Long aLong : longs) {
                        title.append("," + aLong);
                    }
                    bw.write(title.toString());
                    bw.newLine();//中间，隔开不同的单元格，一次写一行
                    i++;
                }
                StringBuffer text = new StringBuffer(1000);
                text.append(key);
                for (Map.Entry<Long, Integer> longIntegerEntry : value.entrySet()) {
                    text.append(",").append(longIntegerEntry.getValue());
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
     * 熵值系数每周大于四的系数差之和
     *
     * @param filename
     * @param pressureDropConsistencyMapWeek
     * @param s
     * @return void
     * @author liwenjie
     * @date 2020/6/5 9:00
     */
    private void ResultWriteToCsvZ(String filename, Map<Long, Map<Integer, Double>> pressureDropConsistencyMapWeek, String s, String s1) {
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + s1 + "-充电.csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            Map<Long, Double> sumMap = new HashMap<>();
            //计算每个周的数据
            for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : pressureDropConsistencyMapWeek.entrySet()) {
                Double sum = 0.0;
                for (Map.Entry<Integer, Double> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                    sum += integerIntegerEntry.getValue();
                }
                sumMap.put(longMapEntry.getKey(), sum);
            }
            Set<Long> longs = pressureDropConsistencyMapWeek.keySet();
            StringBuffer title = new StringBuffer(1000);
            title.append("日期");
            for (Long aLong : longs) {
                title.append("," + aLong);
            }
            bw.write(title.toString());
            bw.newLine();//中间，隔开不同的单元格，一次写一行
            StringBuffer text = new StringBuffer(1000);
            text.append("Z");
            for (Long aLong : longs) {
                text.append(",").append(sumMap.get(aLong));
            }
            bw.write(text.toString());
            bw.newLine();
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
     * 基础数据输出
     *
     * @param volatilityDetectionMapDay 输出数据
     * @param s                         模型名称
     * @param s1                        前缀  日统计/周统计
     */
    private void BaseWriteToCSV(String filename, Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay, String s, String s1) {
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
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "_频次_" + s1 + "_统计_" + s + "_" + getNowTime() + "_放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "_频次_" + s1 + "_统计_" + s + "_" + getNowTime() + "_充电.csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename)), encode);
            bw = new BufferedWriter(ow);
            bw.write(title.toString()); //中间，隔开不同的单元格，一次写一行
            bw.newLine();

            for (int i = 0; i < getBatteryNum(); i++) {
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
     * 熵值 Y 输出
     * 每周单体大于 4 的系数于 4 做差  求和  输出
     *
     * @param filename
     * @param volatilityDetectionMapDay
     * @param s
     * @param s1
     * @return void
     * @author liwenjie
     * @date 2020/6/4 16:25
     */
    private void BaseWriteToCSVXiShu(String filename, Map<Long, Map<Integer, Double>> volatilityDetectionMapDay, String s, String s1) {
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
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "_系数差之和_" + s1 + "_统计_" + s + "_" + getNowTime() + "_放电.csv";
            } else {
                filename = filename + "/0_" + getVIN() + "_系数差之和_" + s1 + "_统计_" + s + "_" + getNowTime() + "_充电.csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename)), encode);
            bw = new BufferedWriter(ow);
            bw.write(title.toString()); //中间，隔开不同的单元格，一次写一行
            bw.newLine();

            for (int i = 0; i < getBatteryNum(); i++) {
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
     * 获取每周的频率
     *
     * @param weekMap
     * @return
     */
    public Map<Long, Map<Integer, Double>> getWeekRate(Map<Long, Map<Integer, Integer>> weekMap) {
        Map<Long, Integer> sumMap = new HashMap<>();
        //计算每个周的数据
        for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : weekMap.entrySet()) {
            int sum = 0;
            for (Map.Entry<Integer, Integer> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                sum += integerIntegerEntry.getValue();
            }
            sumMap.put(longMapEntry.getKey(), sum);
        }
        Map<Long, Map<Integer, Double>> resultMap = new TreeMap<>();
        for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : weekMap.entrySet()) {
            Map<Integer, Double> childrenMap = new TreeMap<>();
            for (Integer integer : longMapEntry.getValue().keySet()) {
                childrenMap.put(integer, longMapEntry.getValue().get(integer).doubleValue() / sumMap.get(longMapEntry.getKey()));
            }
            resultMap.put(longMapEntry.getKey(), childrenMap);
        }
        return resultMap;
    }

    /**
     * 输出图表
     */
    public void outIcon(String outPath) {
        if (null == getVIN()) return;
        if (!outPath.endsWith("/")) outPath += "/";
        /**
         * 全生命周期频率
         */
        if (getEntropyMapBatterSum().size() > 0) {
            XDocUtil.outLine(XDocUtil.getData(getEntropyMapBatterSum(), "异常率", getEntropySum()), Constant.templatePath, outPath + getVIN() + "_" + getType() + "EARate.docx");
        }
        if (getVolatilityDetectionMapBatterSum().size() > 0) {
            XDocUtil.outLine(XDocUtil.getData(getVolatilityDetectionMapBatterSum(), "异常率", getVolatilityDetectionSum()), Constant.templatePath, outPath + getVIN() + "_" + getType() + "VARate.docx");
        }
        if (getPressureDropConsistencyMapBatterSum().size() > 0) {
            XDocUtil.outLine(XDocUtil.getData(getPressureDropConsistencyMapBatterSum(), "异常率", getPressureDropConsistencySum()), Constant.templatePath, outPath + getVIN() + "_" + getType() + "PARate.docx");
        }
        /**
         * 每周频率
         */
        Map<Long, Map<Integer, Double>> weekRate = getWeekRate(getEntropyMapWeek());
        if (getEntropyMapWeek().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : weekRate.entrySet()) {
                XDocUtil.outLine(XDocUtil.getData(longMapEntry.getValue(), "异常率"), Constant.templatePath, outPath + getVIN() + "_" + getType() + "EWRate.docx");
            }
        } else if (getEntropyMapWeek().size() > 1) {
            XDocUtil.outLine(XDocUtil.getDoubleData(weekRate, getBatteryNum()), Constant.templatePath2, outPath + getVIN() + "_" + getType() + "EWRate.docx");
        }
        weekRate = getWeekRate(getVolatilityDetectionMapWeek());
        if (getVolatilityDetectionMapWeek().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : weekRate.entrySet()) {
                XDocUtil.outLine(XDocUtil.getData(longMapEntry.getValue(), "异常率"), Constant.templatePath, outPath + getVIN() + "_" + getType() + "VWRate.docx");
            }
        } else if (getVolatilityDetectionMapWeek().size() > 1) {
            XDocUtil.outLine(XDocUtil.getDoubleData(weekRate, getBatteryNum()), Constant.templatePath2, outPath + getVIN() + "_" + getType() + "VWRate.docx");
        }
        weekRate = getWeekRate(getPressureDropConsistencyMapWeek());
        if (getPressureDropConsistencyMapWeek().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : weekRate.entrySet()) {
                XDocUtil.outLine(XDocUtil.getData(longMapEntry.getValue(), "异常率"), Constant.templatePath, outPath + getVIN() + "_" + getType() + "PWRate.docx");
            }
        } else if (getPressureDropConsistencyMapWeek().size() > 1) {
            XDocUtil.outLine(XDocUtil.getDoubleData(weekRate, getBatteryNum()), Constant.templatePath2, outPath + getVIN() + "_" + getType() + "PWRate.docx");
        }
        /**
         * 每1500帧次数
         */
        if (getEntropyMapNums().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getEntropyMapNums().entrySet()) {
                XDocUtil.outLine(XDocUtil.getIntegerData(longMapEntry.getValue(), "次数"), Constant.templatePath, outPath + getVIN() + "_" + getType() + "EENum.docx");
            }
        } else if (getEntropyMapNums().size() > 1) {
            XDocUtil.outLine(XDocUtil.getData(getEntropyMapNums(), getBatteryNum()), Constant.templatePath2, outPath + getVIN() + "_" + getType() + "EENum.docx");
        }
        if (getVolatilityDetectionMapNums().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getVolatilityDetectionMapNums().entrySet()) {
                XDocUtil.outLine(XDocUtil.getIntegerData(longMapEntry.getValue(), "次数"), Constant.templatePath, outPath + getVIN() + "_" + getType() + "VENum.docx");
            }
        } else if (getVolatilityDetectionMapNums().size() > 1) {
            XDocUtil.outLine(XDocUtil.getData(getVolatilityDetectionMapNums(), getBatteryNum()), Constant.templatePath2, outPath + getVIN() + "_" + getType() + "VENum.docx");
        }
        if (getPressureDropConsistencyMapNums().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getPressureDropConsistencyMapNums().entrySet()) {
                XDocUtil.outLine(XDocUtil.getIntegerData(longMapEntry.getValue(), "次数"), Constant.templatePath, outPath + getVIN() + "_" + getType() + "PENum.docx");
            }
        } else if (getPressureDropConsistencyMapNums().size() > 1) {
            XDocUtil.outLine(XDocUtil.getData(getPressureDropConsistencyMapNums(), getBatteryNum()), Constant.templatePath2, outPath + getVIN() + "_" + getType() + "PENum.docx");
        }
        Merge2.merge(outPath, getVIN(), getType(), this);
    }


    /**
     * 将csv文件读取到List集合中
     *
     * @param path
     * @return
     */
    private List<List<String>> ReadToList(String path) {
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

    private boolean isNumber(String str) {
        if (StringUtils.isEmpty(str)) return false;
        for (int i = str.length(); --i >= 0; ) {
            if (str.charAt(i) != '.' && !Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String getNum(Integer integer) {
        return null == integer ? "0" : integer.toString();
    }

    private int getIntegerNum(Integer integer) {
        return null == integer ? 0 : integer;
    }

    private String getNum(Double integer) {
        return null == integer ? "0" : integer.toString();
    }

    private int getInt(String string) {
        try {
            return Integer.parseInt(null == string && "".equals(string.trim()) ? "0" : string);
        } catch (NumberFormatException e) {
            System.out.println(string);
            return 0;
        }
    }

    /**
     * 统计频次结果
     *
     * @param
     */
   /* public static void zipBefore(String outputPath) {
        logger.info("开始处理结果文件");
        Map<String, FrequencyStatistics> map = new HashMap<>();
        //读取所有的文件路径
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion(outputPath);
        Collections.sort(strings, (o1, o2) -> {
            o1 = o1.replaceAll("\\\\", "/");
            o2 = o2.replaceAll("\\\\", "/");
            String[] s = o1.split("/");
            String[] s1 = s[s.length - 1].split("_");
            String[] s3 = o2.split("/");
            String[] s4 = s3[s.length - 1].split("_");
            return Integer.parseInt(s1[1]) - Integer.parseInt(s4[1]);
        });
        for (String string : strings) {
            string = string.replaceAll("\\\\", "/");
            if (!string.endsWith(".csv")) continue;
            String[] s = string.split("/");
            String[] s1 = s[s.length - 1].split("_");
            String vin = s1[0].substring(s1[0].length() - 17);
            if (map.containsKey(vin)) {
                if (string.contains("压降一致性故障诊断模型")) {
                    map.get(vin).getPressureDropConsistencyList().add(string);
                } else if (string.contains("波动一致性故障诊断模型")) {
                    map.get(vin).getVolatilityDetectionList().add(string);
                } else if (string.contains("熵值故障诊断模型")) {
                    map.get(vin).getEntropyList().add(string);
                }
            } else {
                FrequencyStatistics frequencyStatistics = new FrequencyStatistics();
                if (string.contains("压降一致性故障诊断模型")) {
                    frequencyStatistics.getPressureDropConsistencyList().add(string);
                } else if (string.contains("波动一致性故障诊断模型")) {
                    frequencyStatistics.getVolatilityDetectionList().add(string);
                } else if (string.contains("熵值故障诊断模型")) {
                    frequencyStatistics.getEntropyList().add(string);
                }
                map.put(vin, frequencyStatistics);
            }

        }
        Map<String, FrequencyStatistics> map2 = mapCopy(map);

        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map.entrySet()) {
            stringFrequencyStatisticsEntry.getValue().setType("2");
            Integer integer = ConfigerIgnoreTheMonomer.getMonomerTotal().get(stringFrequencyStatisticsEntry.getKey());
            if (null == integer) integer = 0;
            stringFrequencyStatisticsEntry.getValue().setBatteryNum(integer);
            stringFrequencyStatisticsEntry.getValue().setZMaxNums(Constant.zMaxNums);
            stringFrequencyStatisticsEntry.getValue().setPressureNums(Constant.PRESSURENUMS);
            stringFrequencyStatisticsEntry.getValue().setEntropyNums(Constant.ENTROPYNUMS);
            stringFrequencyStatisticsEntry.getValue().setVolatilityNums(Constant.VOLATILITYNUMS);
            logger.info("放电");
            logger.info("开始处理压降一致性文件");
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyWeek();
            logger.info("开始处理波动一致性文件");
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionWeek();
            logger.info("开始处理熵值一致性文件");
            stringFrequencyStatisticsEntry.getValue().doEntropyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doEntropyWeek();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            stringFrequencyStatisticsEntry.getValue().setNowTime(formatter.format(new Date()));   //设置统一输出时间

            logger.info("开始输出压降一致性结果");
            stringFrequencyStatisticsEntry.getValue().outPressureDropConsistency(outputPath);
            logger.info("开始输出波动一致性结果");
            stringFrequencyStatisticsEntry.getValue().outVolatilityDetection(outputPath);
            logger.info("开始输出熵值结果");
            stringFrequencyStatisticsEntry.getValue().outEntropy(outputPath);
            stringFrequencyStatisticsEntry.getValue().outIcon(outputPath);
        }

        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map2.entrySet()) {
            stringFrequencyStatisticsEntry.getValue().setType("1");
            Integer integer = ConfigerIgnoreTheMonomer.getMonomerTotal().get(stringFrequencyStatisticsEntry.getKey());
            if (null == integer) integer = 0;
            stringFrequencyStatisticsEntry.getValue().setBatteryNum(integer);
            stringFrequencyStatisticsEntry.getValue().setZMaxNums(Constant.zMaxNums);
            stringFrequencyStatisticsEntry.getValue().setPressureNums(Constant.PRESSURENUMS);
            stringFrequencyStatisticsEntry.getValue().setEntropyNums(Constant.ENTROPYNUMS);
            stringFrequencyStatisticsEntry.getValue().setVolatilityNums(Constant.VOLATILITYNUMS);
            logger.info("充电");
            logger.info("开始处理压降一致性文件");
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyWeek();
            logger.info("开始处理波动一致性文件");
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionWeek();
            logger.info("开始处理熵值一致性文件");
            stringFrequencyStatisticsEntry.getValue().doEntropyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doEntropyWeek();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            stringFrequencyStatisticsEntry.getValue().setNowTime(formatter.format(new Date()));   //设置统一输出时间

            logger.info("开始输出压降一致性结果");
            stringFrequencyStatisticsEntry.getValue().outPressureDropConsistency(outputPath);
            logger.info("开始输出波动一致性结果");
            stringFrequencyStatisticsEntry.getValue().outVolatilityDetection(outputPath);
            logger.info("开始输出熵值结果");
            stringFrequencyStatisticsEntry.getValue().outEntropy(outputPath);
            stringFrequencyStatisticsEntry.getValue().outIcon(outputPath);
        }
        logger.info("-----------------------结束-------------------------------");
    }*/
    public static Map<String, FrequencyStatistics> mapCopy(Map<String, FrequencyStatistics> map) {
        Map<String, FrequencyStatistics> resultMap = new HashMap<>();
        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map.entrySet()) {
            resultMap.put(stringFrequencyStatisticsEntry.getKey(), new FrequencyStatistics(stringFrequencyStatisticsEntry.getValue()));
        }
        return resultMap;
    }

    public static void main(String[] args) {
        System.out.println(new FrequencyStatistics().isNumber("?"));
    }
}
