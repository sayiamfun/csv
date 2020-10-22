package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.StatisticalAnalysisOfResults.casion.CreatWord;
import com.sayiamfun.common.Constant;
import com.sayiamfun.common.DateUtils;
import lombok.Data;
import org.apache.commons.collections4.list.TreeList;
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

    private Map<String, String> ignoreTheMonomerMap = new HashMap<>();


    List<FluctuanResult> fluctuanResults = new LinkedList<>();//波动结果
    List<StepConsistencyResult> stepConsistencyResults = new LinkedList<>();//压降结果
    List<OutFaultResult> outFaultResults = new LinkedList<>();//熵值结果


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
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyMapDayZSum = new TreeMap<>();//压降每天压差比的和Z
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek = new TreeMap<>();//压降每周结果
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyMapWeekZSum = new TreeMap<>();//压降每周压差比的和Z
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyMapNumsZSum = new TreeMap<>();//压降每500帧压差比的和Z
    private Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapNums = new TreeMap<>();//压降每500条
    private Map<String, Map<Integer, Integer>> pressureDropConsistencyMapType = new LinkedHashMap<>();//压降充电段
    private Map<Integer, Integer> pressureDropConsistencyMapBatterSum = new TreeMap<>();//压降每个单体出现总次数
    private List<Map<Integer, Double>> pressureDropConsistencyZMaxMapList = new LinkedList<>();//压降计算Zmax使用
    private Map<Long, Map<Integer, Double>> pressureDropConsistencyZMaxNums = new TreeMap<>();//压降每5000帧Zmax


    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay = new TreeMap<>();//波动每天
    private Map<Long, Map<Integer, Double>> volatilityDetectionMapDayVSum = new TreeMap<>();//波动每天倍数差的和Z
    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapWeek = new TreeMap<>();//波动每周
    private Map<Long, Map<Integer, Double>> volatilityDetectionMapWeekZSum = new TreeMap<>();//波动每周倍数差的和Z
    private Map<Long, Map<Integer, Double>> volatilityDetectionMapNumsZSum = new TreeMap<>();//波动每500帧倍数差的和Z
    private Map<Long, Map<Integer, Integer>> volatilityDetectionMapNums = new TreeMap<>();//波动每500条
    private Map<String, Map<Integer, Integer>> volatilityDetectionMapType = new LinkedHashMap<>();//波动充电段
    private Map<Integer, Integer> volatilityDetectionMapBatterSum = new TreeMap<>();//波动每个单体出现总次数
    private List<Map<Integer, Double>> volatilityDetectionZmaxMapList = new LinkedList<>();//波动计算Zmax使用
    private Map<Long, Map<Integer, Double>> volatilityDetectionZmaxNums = new TreeMap<>();//波动每5000帧Zmax


    private Map<Long, Map<Integer, Integer>> EntropyMapDay = new TreeMap<>();//熵值每天
    private Map<Long, Map<Integer, Double>> EntropyMapDayXiShuSum = new TreeMap<>();//熵值每天系数差的和Z
    private Map<Long, Map<Integer, Double>> EntropyMapNumsXiShuSum = new TreeMap<>();//熵值每500系数差的和Z
    private Map<Long, Map<Integer, Integer>> EntropyMapWeek = new TreeMap<>();//熵值每周
    private Map<Long, Map<Integer, Double>> EntropyMapWeekXiShuSum = new TreeMap<>();//熵值每周系数差总和Z
    private Map<Long, Map<Integer, Integer>> EntropyMapNums = new TreeMap<>();//熵值每500条
    private Map<String, Map<Integer, Integer>> EntropyMapType = new LinkedHashMap<>();//熵值充电段
    private Map<Integer, Integer> EntropyMapBatterSum = new TreeMap<>();//熵值每个单体出现总次数
    private List<Map<Integer, Double>> EntropyZmaxMapList = new LinkedList<>();//熵值计算Zmax使用
    private Map<Long, Map<Integer, Double>> EntropyZmaxNums = new TreeMap<>();//熵值每5000帧Zmax


    private DecimalFormat df = new DecimalFormat("0.000000000");


    public FrequencyStatistics(FrequencyStatistics frequencyStatistics) {
        this.volatilityNums = frequencyStatistics.getVolatilityNums();
        this.pressureNums = frequencyStatistics.getPressureNums();
        this.entropyNums = frequencyStatistics.getEntropyNums();
        this.VIN = frequencyStatistics.getVIN();
        this.fluctuanResults = new LinkedList<>(frequencyStatistics.getFluctuanResults());
        this.outFaultResults = new LinkedList<>(frequencyStatistics.getOutFaultResults());
        this.stepConsistencyResults = new LinkedList<>(frequencyStatistics.getStepConsistencyResults());
        this.ignoreTheMonomerMap = frequencyStatistics.getIgnoreTheMonomerMap();
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
     * 每天和每500帧
     */
    public void doVolatilityDetectionDayAndNums() {
        int nums = 0;
        long numsindex = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每500条的数据 单体频次
        Map<Integer, Integer> typeMap = new TreeMap<>(); //存放分段数据 单体频次
        Map<Integer, Double> numsSumpMap = new TreeMap<>();//存放每500条的数据 倍数差的和

        long lastTime = 0L;
        long lastStartTime = 0L;
        long lastEndTime = 0L;
        Map<Integer, Double> maxVMap;
        List<FluctuanResult> fluctuanResults = getFluctuanResults();
        fluctuanResults.sort(Comparator.comparing(o -> Long.valueOf(o.getEndTime())));
        Iterator<FluctuanResult> iterator = fluctuanResults.iterator();
        while (iterator.hasNext()) {
            FluctuanResult fluctuanResult = iterator.next();
//            if (("1".equals(getType()) && Double.parseDouble(fluctuanResult.getTotalI()) <= 0) || ("2".equals(getType()) && Double.parseDouble(fluctuanResult.getTotalI()) > 0)) {
            if (!getType().equals("" + fluctuanResult.getType())) {
                iterator.remove();
                continue;
            }
            Long dataTime = Long.parseLong(fluctuanResult.getEndTime()) / 1000000L;
            setStartTime(dataTime);
            Map<Integer, Integer> tmpMap = getVolatilityDetectionMapDay().get(dataTime);
            if (null == tmpMap) tmpMap = new TreeMap<>(); //存放一天的数据
            Map<Integer, Double> tmpSumMap = getVolatilityDetectionMapDayVSum().get(dataTime);
            if (null == tmpSumMap) tmpSumMap = new TreeMap<>();

            //波动倍数差的和
            String quaListString = fluctuanResult.getQuaListString();
            String[] quaList = quaListString.split(Constant.SPLITE_UNDERLINE);
            int maxMonNum = 0;
            double maxZ = 0.0;
            for (int i = 0; i < quaList.length; i++) {
                Integer smon = getInt(ignoreTheMonomerMap.get("" + (i + 1)));
                String sv = quaList[i];
                if (isNumber(sv)) sv = "0";
                Double multiply = new BigDecimal(sv).subtract(new BigDecimal("3")).doubleValue();
                if (multiply <= 0) continue;
                if (multiply > maxZ) {
                    maxMonNum = smon;
                    maxZ = multiply;
                }
                int monNum = smon;
                // 每天数据
                if (tmpMap.containsKey(monNum)) {
                    tmpMap.put(monNum, tmpMap.get(monNum) + 1);
                } else {
                    tmpMap.put(monNum, 1);
                }
                //每500条数据
                if (numsMap.containsKey(monNum)) {
                    numsMap.put(monNum, numsMap.get(monNum) + 1);
                } else {
                    numsMap.put(monNum, 1);
                }

                //每天波动倍数差的和
                if (tmpSumMap.containsKey(smon)) {
                    tmpSumMap.put(smon, tmpSumMap.get(smon) + multiply);
                } else {
                    tmpSumMap.put(smon, multiply);
                }
                //每500帧波动倍数差的和
                if (numsSumpMap.containsKey(smon)) {
                    numsSumpMap.put(smon, numsSumpMap.get(smon) + multiply);
                } else {
                    numsSumpMap.put(smon, multiply);
                }

                //充电状态
                long thisTime = Long.parseLong(fluctuanResult.getEndTime());
                if (lastTime == 0) {
                    lastTime = thisTime;
                    lastStartTime = thisTime;
                }
                Date thisdate = DateUtils.strToDate("" + thisTime);
                Date lastdate = DateUtils.strToDate("" + lastTime);
                if (null != thisdate && null != lastdate && ((thisdate.getTime() - lastdate.getTime()) / 1000) > getTimeDifference()) {
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
            //每天数据
            if (tmpMap.size() > 0) {
                getVolatilityDetectionMapDay().put(dataTime, tmpMap);
            }
            if (tmpSumMap.size() > 0) {
                getVolatilityDetectionMapDayVSum().put(dataTime, tmpSumMap);
            }
            maxVMap = new HashMap<>();
            maxVMap.put(maxMonNum, maxZ);
            getVolatilityDetectionZmaxMapList().add(maxVMap);
            nums++;
            //每500条数据
            if (nums >= getVolatilityNums()) {
                getVolatilityDetectionMapNums().put(numsindex, numsMap);
                numsMap = new TreeMap<>();

                getVolatilityDetectionMapNumsZSum().put(numsindex, numsSumpMap);
                numsSumpMap = new TreeMap<>();

                nums = 0;
                numsindex++;
            }
        }
        if (typeMap.size() > 0) {
            String startTAndEndT = "" + lastStartTime + "-" + lastEndTime;
            getVolatilityDetectionMapType().put(startTAndEndT, typeMap);
        }
        if (nums > 0) {
            getVolatilityDetectionMapNums().put(numsindex, numsMap);
            getVolatilityDetectionMapNumsZSum().put(numsindex, numsSumpMap);
        }

    }

    /**
     * 处理波动一致性数据
     * 每周
     */
    public void doVolatilityDetectionWeek() {
        //处理存放每周的数据
        if (null != getVolatilityDetectionMapDay() && getVolatilityDetectionMapDay().size() > 0) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getVolatilityDetectionMapDay().entrySet()) {
                if (getVstartTime() == null) setVstartTime(longMapEntry.getKey());
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
                    //每周所有单体次数
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

//        doZ(getVolatilityDetectionMapDayVSum(), 2);

//        doZmaxNums(getVolatilityDetectionZmaxMapList(), 2);
    }

    /**
     * 处理压降一致性数据
     * 每天和每500帧
     */
    public void doPressureDropConsistencyDayAndNums() {
        long nums = 0;
        long numsindex = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每500条的数据 单体频次
        Map<Integer, Integer> typeMap = new TreeMap<>(); //存放分段数据 单体频次
        Map<Integer, Double> numsSumMap = new TreeMap<>(); //存放分段数据 压差倍数的差值

        long lastTime = 0L;
        long lastStartTime = 0L;
        long lastEndTime = 0L;

        List<StepConsistencyResult> stepConsistencyResults = getStepConsistencyResults();
        stepConsistencyResults.sort(Comparator.comparing(o -> Long.valueOf(o.getLastTime())));
        Map<Integer, Double> maxVMap;
        Iterator<StepConsistencyResult> iterator = stepConsistencyResults.iterator();
        while (iterator.hasNext()) {
            StepConsistencyResult stepConsistencyResult = iterator.next();
//            if (("1".equals(getType()) && Double.parseDouble(stepConsistencyResult.getNowtotalI()) <= 0) || ("2".equals(getType()) && Double.parseDouble(stepConsistencyResult.getNowtotalI()) > 0)) {
            String[] split = stepConsistencyResult.getLastVolts().split(Constant.SPLITE_UNDERLINE);
            if (ignoreTheMonomerMap == null || ignoreTheMonomerMap.size() == 0) {
                ignoreTheMonomerMap = new HashMap<>();
                for (int i = 0; i < split.length; i++) {
                    ignoreTheMonomerMap.put("" + (i + 1), "" + (i + 1));
                }
            }

            if (!getType().equals("" + stepConsistencyResult.getType())) {
                iterator.remove();
                continue;
            }
            Long dataTime = Long.parseLong(stepConsistencyResult.getNowTime()) / 1000000;
            setStartTime(dataTime);
            Map<Integer, Integer> tmpMap = getPressureDropConsistencyMapDay().get(dataTime);
            if (null == tmpMap) tmpMap = new TreeMap<>(); //存放一天的数据
            Map<Integer, Double> tmpSumMap = getPressureDropConsistencyMapDayZSum().get(dataTime);
            if (null == tmpSumMap) tmpSumMap = new TreeMap<>();

            //压差倍数差的和
            String s1 = "" + stepConsistencyResult.getDiploid();
            if (isNumber(s1)) s1 = "0";
            if (StringUtils.isNotEmpty(s1) && new BigDecimal(s1).compareTo(new BigDecimal("2")) > 0) {
                double v = new BigDecimal(s1).subtract(new BigDecimal("2")).doubleValue();
                int monNum = getInt(ignoreTheMonomerMap.get("" + stepConsistencyResult.getMaxStepSingleNum()));
                maxVMap = new HashMap<>();
                maxVMap.put(monNum, v);
                getPressureDropConsistencyZMaxMapList().add(maxVMap);
                //每天数据
                if (tmpMap.containsKey(monNum)) {
                    tmpMap.put(monNum, tmpMap.get(monNum) + 1);
                } else {
                    tmpMap.put(monNum, 1);
                }
                // 每500条数据
                if (numsMap.containsKey(monNum)) {
                    numsMap.put(monNum, numsMap.get(monNum) + 1);
                } else {
                    numsMap.put(monNum, 1);
                }

                //每天数据
                if (tmpSumMap.containsKey(1)) {
                    tmpSumMap.put(1, tmpSumMap.get(1) + v);
                } else {
                    tmpSumMap.put(1, v);
                }
                //每500帧
                if (numsSumMap.containsKey(1)) {
                    numsSumMap.put(1, numsSumMap.get(1) + v);
                } else {
                    numsSumMap.put(1, v);
                }

                //充电状态
                long thisTime = Long.parseLong(stepConsistencyResult.getNowTime());
                if (lastTime == 0) {
                    lastTime = thisTime;
                    lastStartTime = thisTime;
                }
                Date thisdate = DateUtils.strToDate("" + thisTime);
                Date lastdate = DateUtils.strToDate("" + lastTime);
                if (null != thisdate && null != lastdate && ((thisdate.getTime() - lastdate.getTime()) / 1000) > getTimeDifference()) {
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
            //将当天数据放入
            if (tmpMap.size() > 0) {
                getPressureDropConsistencyMapDay().put(dataTime, tmpMap);
            }
            if (tmpSumMap.size() > 0) {
                getPressureDropConsistencyMapDayZSum().put(dataTime, tmpSumMap);
            }
            nums++;
            //每500条数据放入
            if (nums >= getPressureNums()) {
                getPressureDropConsistencyMapNums().put(numsindex, numsMap);
                numsMap = new TreeMap<>();
                getPressureDropConsistencyMapNumsZSum().put(numsindex, numsSumMap);
                numsSumMap = new TreeMap<>();
                nums = 0;
                numsindex++;
            }
            //获取电池单体数量
            if (getBatteryNum() == 0)
                setBatteryNum(stepConsistencyResult.getLastVolts().split("_").length + getIgnoreMonNum());
        }
        if (typeMap.size() > 0) {
            String s3 = "" + lastStartTime + "-" + lastEndTime;
            getPressureDropConsistencyMapType().put(s3, typeMap);
        }
        if (nums > 0) {
            getPressureDropConsistencyMapNums().put(numsindex, numsMap);
            getPressureDropConsistencyMapNumsZSum().put(numsindex, numsSumMap);
        }
    }

    /**
     * 处理压降一致性数据
     * 每周
     */
    public void doPressureDropConsistencyWeek() {
        if (null != getPressureDropConsistencyMapDay() && getPressureDropConsistencyMapDay().size() > 0) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getPressureDropConsistencyMapDay().entrySet()) {
                if (getPstartTime() == null) setPstartTime(longMapEntry.getKey());
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
//        doZ(getPressureDropConsistencyMapDayZSum(), 3);

//        doZmaxNums(getPressureDropConsistencyZMaxMapList(), 3);
    }

    /**
     * 处理熵值故障诊断模型数据
     * 每天和每500帧
     */
    public void doEntropyDayAndNums() {
        int nums = 0;
        long index = 1;
        Map<Integer, Integer> numsMap = new TreeMap<>(); //存放每500条的数据 单体频次
        Map<Integer, Integer> typeMap = new TreeMap<>(); //存放分段数据 单体频次
        Map<Integer, Double> numsSumMap = new TreeMap<>(); //存放分段数据 系数差的和

        long lastTime = 0L;
        long lastStartTime = 0L;
        long lastEndTime = 0L;

        Map<Integer, Double> maxVMap;
        List<OutFaultResult> outFaultResults = getOutFaultResults();
        outFaultResults.sort(Comparator.comparing(o -> Long.valueOf(o.time)));
        Iterator<OutFaultResult> iterator = outFaultResults.iterator();
        while (iterator.hasNext()) {

            OutFaultResult outFaultResult = iterator.next();
//            if (("1".equals(getType()) && Double.parseDouble(outFaultResult.totalI) <= 0) || ("2".equals(getType()) && Double.parseDouble(outFaultResult.totalI) > 0)) {
            if (!getType().equals("" + outFaultResult.type)) {
                iterator.remove();
                continue;
            }
            //获取时间
            Long dataTime = Long.parseLong(outFaultResult.time) / 1000000;
            setStartTime(dataTime);
            Map<Integer, Integer> tmpMap = getEntropyMapDay().get(dataTime);
            if (null == tmpMap) tmpMap = new TreeMap<>(); //存放一天的数据
            Map<Integer, Double> tmpSumMap = getEntropyMapDayXiShuSum().get(dataTime);
            if (null == tmpSumMap) tmpSumMap = new TreeMap<>(); //存放一天的(系数减去4)的和
            if (getBatteryNum() == 0) {
                //存放单体数量
                setBatteryNum(outFaultResult.stringVars.split("_").length + getIgnoreMonNum());
            }
            int monNum = getInt(ignoreTheMonomerMap.get("" + outFaultResult.maxNum));
            String s2 = "" + outFaultResult.maxValue;
            if (isNumber(s2)) s2 = "0";
            if (new BigDecimal(s2).compareTo(new BigDecimal("4")) > 0) {
                //天数据
                if (tmpMap.containsKey(monNum)) {
                    tmpMap.put(monNum, tmpMap.get(monNum) + 1);
                } else {
                    tmpMap.put(monNum, 1);
                }
                //天系数差的和数据
                double v = new BigDecimal(s2).subtract(new BigDecimal("4")).doubleValue();
                maxVMap = new HashMap<>();
                maxVMap.put(monNum, v);
                getEntropyZmaxMapList().add(maxVMap);
                if (tmpSumMap.containsKey(monNum)) {
                    tmpSumMap.put(monNum, tmpSumMap.get(monNum) + v);
                } else {
                    tmpSumMap.put(monNum, v);
                }
                //500帧系数差的和
                if (numsSumMap.containsKey(monNum)) {
                    numsSumMap.put(monNum, numsSumMap.get(monNum) + v);
                } else {
                    numsSumMap.put(monNum, v);
                }

                //每500条数据 单体
                if (numsMap.containsKey(monNum)) {
                    numsMap.put(monNum, numsMap.get(monNum) + 1);
                } else {
                    numsMap.put(monNum, 1);
                }
                //充电状态
                long thisTime = Long.parseLong(outFaultResult.time);
                if (lastTime == 0) {
                    lastTime = thisTime;
                    lastStartTime = thisTime;
                }
                Date thisdate = DateUtils.strToDate("" + thisTime);
                Date lastdate = DateUtils.strToDate("" + lastTime);
                if (null != thisdate && null != lastdate && ((thisdate.getTime() - lastdate.getTime()) / 1000) > getTimeDifference()) {
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
            //天数据
            if (tmpMap.size() > 0) {
                getEntropyMapDay().put(dataTime, tmpMap);
            }
            if (tmpSumMap.size() > 0) {
                getEntropyMapDayXiShuSum().put(dataTime, tmpSumMap);
            }
            nums++;
            //每500条数据 存储
            if (nums >= getEntropyNums()) {
                getEntropyMapNums().put(index, numsMap);
                numsMap = new TreeMap<>();
                getEntropyMapNumsXiShuSum().put(index, numsSumMap);
                numsSumMap = new TreeMap<>();
                nums = 0;
                index++;
            }
        }
        if (typeMap.size() > 0) {
            String s3 = "" + lastStartTime + "-" + lastEndTime;
            getEntropyMapType().put(s3, typeMap);
        }
        //500条数据
        if (nums > 0) {
            getEntropyMapNums().put(index, numsMap);
            getEntropyMapNumsXiShuSum().put(index, numsSumMap);
        }
    }

    /**
     * 处理熵值故障诊断模型数据
     * 每周
     */
    public void doEntropyWeek() {
        //处理存放每周的数据
        if (null != getEntropyMapDay() && getEntropyMapDay().size() > 0) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getEntropyMapDay().entrySet()) {
                if (getEstartTime() == null) setEstartTime(longMapEntry.getKey());
                Long startTime = getWeekLastTime(getEstartTime());
                while (longMapEntry.getKey() > startTime) {
                    //如果当前时间小于startTime则为当前周的数据 否则则是新一周的数据  需要更新main.srartTime 并且添加新的数据到Mapweek
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
        //系数差的和
//        doZ(getEntropyMapDayXiShuSum(), 1);
        //处理存放每500帧的数据(系数差的和)
//        doZmaxNums(getEntropyZmaxMapList(), 1);
    }

    /**
     * 处理Zmax
     *
     * @param volatilityDetectionZmaxMapList 每一帧最大Zmax数据信息
     * @param type                           模型
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/6 3:18 下午
     */
    private void doZmaxNums(List<Map<Integer, Double>> volatilityDetectionZmaxMapList, int type) {
        if (null != volatilityDetectionZmaxMapList) {
            int size = volatilityDetectionZmaxMapList.size();
            if (size > 0) {
                long index;
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

    /**
     * 统计z的数据
     *
     * @param zSum 每天Z的数据
     * @param type 模型标志
     * @author liwenjie
     * @date 2020/6/9 8:46
     */
    private void doZ(Map<Long, Map<Integer, Double>> zSum, int type) {
        if (null != zSum && zSum.size() > 0) {
            if (1 == type) {
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
            } else if (2 == type) {
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
     * 两数相除
     *
     * @param integer                    被除数
     * @param pressureDropConsistencySum 除数
     * @return String
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
     * @param startTime 现在时间
     * @return long
     */
    private long getWeekLastTime(Long startTime) {
        if (startTime == null || startTime.toString().length() != 8)
            throw new RuntimeException("input time is not support !");
        Date date = DateUtils.parseDate(startTime.toString());
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DATE, 6);
        String s = DateUtils.parseDateToStr(DateUtils.YYYYMMDD, instance.getTime());
        return Long.parseLong(s);
    }

    /**
     * 输出波动一致性每天数据
     */
    public void outVolatilityDetection(String outPath) {
        if (null != getVolatilityDetectionMapDay() && getVolatilityDetectionMapDay().size() > 0) {
            BaseWriteToCSV(outPath, getVolatilityDetectionMapDay(), "波动一致性故障诊断模型", "日");
//            ResultWriteToCsvW(outPath, getVolatilityDetectionMapDay(), "波动一致性故障诊断模型_日");
        }
        if (null != getVolatilityDetectionMapWeek() && getVolatilityDetectionMapWeek().size() > 0) {
            BaseWriteToCSV(outPath, getVolatilityDetectionMapWeek(), "波动一致性故障诊断模型", "周");
            ResultWriteToCsvWeek(outPath, getVolatilityDetectionMapWeek(), "波动一致性故障诊断模型");
//            ResultWriteToCsvX(outPath, getVolatilityDetectionMapWeek(), "波动一致性故障诊断模型_周");
        }
        if (null != getVolatilityDetectionMapNums() && getVolatilityDetectionMapNums().size() > 0) {
            BaseWriteToCSV(outPath, getVolatilityDetectionMapNums(), "波动一致性故障诊断模型", "每" + getVolatilityNums() + "帧");
//            ResultWriteToCsvX(outPath, getVolatilityDetectionMapNums(), "波动一致性故障诊断模型_" + getVolatilityNums() + "帧");
//            ResultWriteToCsvW(outPath, getVolatilityDetectionMapNums(), "波动一致性故障诊断模型_" + getVolatilityNums() + "帧");
        }

        ResultWriteToCsv(outPath, getVolatilityDetectionMapBatterSum(), "波动一致性故障诊断模型", getVolatilityDetectionSum());

//        ResultWriteToCsvZ(outPath, getVolatilityDetectionMapWeekZSum(), "波动一致性故障诊断模型_周", 1);

//        ResultWriteToCsvZ(outPath, getVolatilityDetectionMapNumsZSum(), "波动一致性故障诊断模型_" + getVolatilityNums() + "帧", 1);

//        ResultWriteToCsvZmax(outPath, getVolatilityDetectionZmaxNums(), "波动一致性故障诊断模型_" + getZMaxNums() + "帧");

        ResultWriteToCsvType(outPath, getVolatilityDetectionMapType(), "波动一致性故障诊断模型");
    }


    /**
     * 输出压降一致性每天数据
     */
    public void outPressureDropConsistency(String outPath) {
        if (null != getPressureDropConsistencyMapDay() && getPressureDropConsistencyMapDay().size() > 0) {
            BaseWriteToCSV(outPath, getPressureDropConsistencyMapDay(), "压降一致性故障诊断模型", "日");
//            ResultWriteToCsvW(outPath, getPressureDropConsistencyMapDay(), "压降一致性故障诊断模型_日");
        }
        if (null != getPressureDropConsistencyMapWeek() && getPressureDropConsistencyMapWeek().size() > 0) {
            BaseWriteToCSV(outPath, getPressureDropConsistencyMapWeek(), "压降一致性故障诊断模型", "周");
            ResultWriteToCsvWeek(outPath, getPressureDropConsistencyMapWeek(), "压降一致性故障诊断模型");
//            ResultWriteToCsvX(outPath, getPressureDropConsistencyMapWeek(), "压降一致性故障诊断模型_周");
        }
        if (null != getPressureDropConsistencyMapNums() && getPressureDropConsistencyMapNums().size() > 0) {
            BaseWriteToCSV(outPath, getPressureDropConsistencyMapNums(), "压降一致性故障诊断模型", "每" + getPressureNums() + "帧");
//            ResultWriteToCsvX(outPath, getPressureDropConsistencyMapNums(), "压降一致性故障诊断模型_" + getPressureNums() + "帧");
//            ResultWriteToCsvW(outPath, getPressureDropConsistencyMapNums(), "压降一致性故障诊断模型_" + getPressureNums() + "帧");
        }

        ResultWriteToCsv(outPath, getPressureDropConsistencyMapBatterSum(), "压降一致性故障诊断模型", getPressureDropConsistencySum());

//        ResultWriteToCsvZ(outPath, getPressureDropConsistencyMapWeekZSum(), "压降一致性故障诊断模型_周", 1);

//        ResultWriteToCsvZ(outPath, getPressureDropConsistencyMapNumsZSum(), "压降一致性故障诊断模型_" + getPressureNums() + "帧", 1);

//        ResultWriteToCsvZmax(outPath, getPressureDropConsistencyZMaxNums(), "压降一致性故障诊断模型_" + getZMaxNums() + "帧");

        ResultWriteToCsvType(outPath, getPressureDropConsistencyMapType(), "压降一致性故障诊断模型");

    }

    /**
     * 输出熵值每天数据
     */
    public void outEntropy(String outPath) {
        if (getEntropyMapDay() != null && getEntropyMapDay().size() > 0) {
            BaseWriteToCSV(outPath, getEntropyMapDay(), "熵值故障诊断模型", "日");
//            ResultWriteToCsvW(outPath, getEntropyMapDay(), "熵值故障诊断模型_日");
        }
        if (null != getEntropyMapWeek() && getEntropyMapWeek().size() > 0) {
            BaseWriteToCSV(outPath, getEntropyMapWeek(), "熵值故障诊断模型", "周");
            ResultWriteToCsvWeek(outPath, getEntropyMapWeek(), "熵值故障诊断模型");
//            ResultWriteToCsvX(outPath, getEntropyMapWeek(), "熵值故障诊断模型_周");
        }
        if (null != getEntropyMapNums() && getEntropyMapNums().size() > 0) {
            BaseWriteToCSV(outPath, getEntropyMapNums(), "熵值故障诊断模型", "每" + getEntropyNums() + "帧");
//            ResultWriteToCsvX(outPath, getEntropyMapNums(), "熵值故障诊断模型_" + getEntropyNums() + "帧");
//            ResultWriteToCsvW(outPath, getEntropyMapNums(), "熵值故障诊断模型_" + getEntropyNums() + "帧");
        }

//        if (null != getEntropyMapWeekXiShuSum() && getEntropyMapWeekXiShuSum().size() > 0) {
//            ResultWriteToCsvZ(outPath, getEntropyMapWeekXiShuSum(), "熵值故障诊断模型_周", 1);
//        }

        ResultWriteToCsv(outPath, getEntropyMapBatterSum(), "熵值故障诊断模型", getEntropySum());

//        ResultWriteToCsvZ(outPath, getEntropyMapNumsXiShuSum(), "熵值故障诊断模型_" + getEntropyNums() + "帧", 1);

//        ResultWriteToCsvZmax(outPath, getEntropyZmaxNums(), "熵值故障诊断模型_" + getZMaxNums() + "帧");

        ResultWriteToCsvType(outPath, getEntropyMapType(), "熵值故障诊断模型");
    }

    /**
     * Zmax 结果输出
     *
     * @param filename        输出文件路径
     * @param entropyZmaxNums 输出结果
     * @param s               熵值故障诊断模型_5000帧
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/6 7:42 下午
     */
    private void ResultWriteToCsvZmax(String filename, Map<Long, Map<Integer, Double>> entropyZmaxNums, String s) {
        if (entropyZmaxNums.size() == 0) return;
        Set<Long> longs = entropyZmaxNums.keySet();
        //写表头
        StringBuilder title = new StringBuilder(1000);
        title.append("单体编号");
        for (Long aLong : longs) {
            title.append(",").append(aLong);
        }
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "Zmax" + "-放电_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "Zmax" + "-充电_" + getNowTime() + ".csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename)), encode);
            bw = new BufferedWriter(ow);
            bw.write(title.toString()); //中间，隔开不同的单元格，一次写一行
            bw.newLine();

            for (int i = 0; i < getBatteryNum(); i++) {
                StringBuilder content = new StringBuilder(1000);
                content.append(i + 1);
                for (Long aLong : longs) {
                    content.append(",").append(getNum(entropyZmaxNums.get(aLong).get(i + 1)));
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
     * @param outPath                        数据文件路径
     * @param pressureDropConsistencyMapType 输出结果
     * @param s                              模型名称
     */
    private void ResultWriteToCsvType(String outPath, Map<String, Map<Integer, Integer>> pressureDropConsistencyMapType, String s) {
        if (pressureDropConsistencyMapType.size() == 0) return;
        Map<Integer, Map<String, Integer>> dayNumMap = new TreeMap<>();
        Set<String> longs = pressureDropConsistencyMapType.keySet();
        for (int i = 0; i < getBatteryNum(); i++) {
            Map<String, Integer> longIntegerMap = new TreeMap<>();
            for (String aLong : longs) {
                longIntegerMap.put(aLong, getIntegerNum(pressureDropConsistencyMapType.get(aLong).get(i + 1)));
            }
            dayNumMap.put(i + 1, longIntegerMap);
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
     * @param filename 输出文件路径
     * @param sumMap   输出数据
     * @param s        频次还是频次差
     */
    private void typeWriteToCsv(String filename, Map<Integer, Map<String, Integer>> sumMap, String s, String s2) {
        if (sumMap.size() == 0) return;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-放电段-" + s2 + "_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-充电段-" + s2 + "_" + getNowTime() + ".csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            int i = 0;
            for (Map.Entry<Integer, Map<String, Integer>> integerMapEntry : sumMap.entrySet()) {
                Integer key = integerMapEntry.getKey();
                Map<String, Integer> value = integerMapEntry.getValue();
                if (i == 0) {
                    Set<String> longs = value.keySet();
                    StringBuilder title = new StringBuilder(1000);
                    title.append("时间段");
                    for (String aLong : longs) {
                        title.append(",").append(aLong);
                    }
                    bw.write(title.toString());
                    bw.newLine();//中间，隔开不同的单元格，一次写一行
                    i++;
                }
                StringBuilder text = new StringBuilder(1000);
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
        if (pressureDropConsistencyMapBatterSum.size() == 0) return;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            df.setRoundingMode(RoundingMode.HALF_UP);
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-全生命周期异常率-放电_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-全生命周期异常率-充电_" + getNowTime() + ".csv";
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
     * @param filename                       输出文件路径
     * @param pressureDropConsistencyMapWeek 输出数据
     * @param s                              模型名称
     */
    private void ResultWriteToCsvWeek(String filename, Map<Long, Map<Integer, Integer>> pressureDropConsistencyMapWeek, String s) {
        if (pressureDropConsistencyMapWeek.size() == 0) return;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-周异常率-放电_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-周异常率-充电_" + getNowTime() + ".csv";
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
            StringBuilder title = new StringBuilder(1000);
            title.append("单体编号");
            for (Long aLong : longs) {
                title.append(",").append(aLong);
            }
            bw.write(title.toString());
            bw.newLine();//中间，隔开不同的单元格，一次写一行
            for (int i = 0; i < getBatteryNum(); i++) {
                StringBuilder text = new StringBuilder(1000);
                text.append(i + 1);
                for (Long aLong : longs) {
                    text.append(",").append(getDouble(pressureDropConsistencyMapWeek.get(aLong).get(i + 1), sumMap.get(aLong)));
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
     * @param filename 输出结果路径
     * @param sumMap   输出数据
     * @param s        模型名称
     */
    private void ResultWriteToCsvX(String filename, Map<Long, Map<Integer, Integer>> sumMap, String s) {
        if (sumMap.size() == 0) return;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "X_Y" + "-放电_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "X_Y" + "-充电_" + getNowTime() + ".csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            Set<Long> longs = sumMap.keySet();
            StringBuilder title = new StringBuilder(1000);
            title.append("日期");
            for (Long aLong : longs) {
                title.append(",").append(aLong);
            }
            bw.write(title.toString());
            bw.newLine();//中间，隔开不同的单元格，一次写一行
            StringBuilder text = new StringBuilder(1000);
            StringBuilder textXnn = new StringBuilder(1000);
            text.append("X");
            textXnn.append("Y");
            int lastaLongSum = 0;
            for (Long aLong : longs) {
                Map<Integer, Integer> integerIntegerMap = sumMap.get(aLong);
                int numsXSum = 0;
                if (null != integerIntegerMap) {
                    for (Integer value : integerIntegerMap.values()) {
                        numsXSum += value;
                    }
                }
                text.append(",").append(numsXSum);
                if (lastaLongSum == 0) {
                    textXnn.append(",").append(numsXSum);
                } else {
                    textXnn.append(",").append(numsXSum - lastaLongSum);
                }
                lastaLongSum = numsXSum;
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
     * 最后结果输出   W
     *
     * @param filename 输出结果路径
     * @param sumMap   输出结果
     * @param s        模型名称
     */
    private void ResultWriteToCsvW(String filename, Map<Long, Map<Integer, Integer>> sumMap, String s) {
        if (sumMap.size() == 0) return;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "W" + "-放电_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "W" + "-充电_" + getNowTime() + ".csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            ArrayList<Long> dateList = new ArrayList<>(sumMap.keySet());
            int batteryNum = getBatteryNum();

            StringBuilder title = new StringBuilder(1000);
            title.append("日期");
            for (Long aLong : dateList) {
                title.append(",").append(aLong);
            }
            bw.write(title.toString());
            bw.newLine();
            StringBuilder body;
            for (int mon = 0; mon < batteryNum; mon++) {
                body = new StringBuilder(1000);
                body.append((mon + 1));
                long lastDate = dateList.get(0);
                for (Long date : dateList) {
                    if (date == lastDate) {
                        body.append(",").append(getInt("" + sumMap.get(date).get((mon + 1))));
                    } else {
                        body.append(",").append(getInt("" + sumMap.get(date).get((mon + 1))) - getInt("" + sumMap.get(lastDate).get((mon + 1))));
                    }
                    lastDate = date;
                }
                bw.write(body.toString());
                bw.newLine();
                bw.flush();
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
     * @param filename                       数据文件路径
     * @param pressureDropConsistencyMapWeek 输出数据
     * @param s                              模型名称
     * @param type                           聚合还是单独输出
     * @return void
     * @author liwenjie
     * @date 2020/6/5 9:00
     */
    private void ResultWriteToCsvZ(String filename, Map<Long, Map<Integer, Double>> pressureDropConsistencyMapWeek, String s, int type) {
        if (pressureDropConsistencyMapWeek.size() == 0) return;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "Z" + "-放电_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "__" + s + "-" + "Z" + "-充电_" + getNowTime() + ".csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename), true), encode);
            bw = new BufferedWriter(ow);
            Map<Long, Map<Integer, Double>> resultMap = new TreeMap<>();
            List<Integer> monList = new TreeList<>();
            if (type == 1) {
                monList.add(1);
                //计算每个周的数据
                for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : pressureDropConsistencyMapWeek.entrySet()) {
                    Map<Integer, Double> sumMap = new HashMap<>();
                    Double sum = 0.0;
                    for (Map.Entry<Integer, Double> integerIntegerEntry : longMapEntry.getValue().entrySet()) {
                        sum += integerIntegerEntry.getValue();
                    }
                    sumMap.put(1, sum);
                    resultMap.put(longMapEntry.getKey(), sumMap);
                }
            } else {
                for (int i = 0; i < getBatteryNum(); i++) {
                    monList.add(i + 1);
                }
                resultMap = pressureDropConsistencyMapWeek;
            }
            Set<Long> longs = pressureDropConsistencyMapWeek.keySet();
            StringBuilder title = new StringBuilder(1000);
            title.append("日期");
            for (Long aLong : longs) {
                title.append(",").append(aLong);
            }
            bw.write(title.toString());
            bw.newLine();//中间，隔开不同的单元格，一次写一行
            bw.flush();
            for (Integer integer : monList) {
                StringBuilder text = new StringBuilder(1000);
                text.append("Z").append(integer);
                for (Long aLong : longs) {
                    text.append(",").append(getDoubleValue(resultMap.get(aLong).get(integer)));
                }
                bw.write(text.toString());
                bw.newLine();
                bw.flush();
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
     * 基础数据输出
     *
     * @param volatilityDetectionMapDay 输出数据
     * @param s                         模型名称
     * @param s1                        前缀  日统计/周统计
     */
    private void BaseWriteToCSV(String filename, Map<Long, Map<Integer, Integer>> volatilityDetectionMapDay, String s, String s1) {
        if (volatilityDetectionMapDay.size() == 0) return;
        Set<Long> longs = volatilityDetectionMapDay.keySet();
        //写表头
        StringBuilder title = new StringBuilder(1000);
        title.append("单体编号");
        for (Long aLong : longs) {
            title.append(",").append(aLong);
        }
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            if ("2".equals(getType())) {
                filename = filename + "/0_" + getVIN() + "_频次_" + s1 + "_统计_" + s + "_放电_" + getNowTime() + ".csv";
            } else {
                filename = filename + "/0_" + getVIN() + "_频次_" + s1 + "_统计_" + s + "_充电" + getNowTime() + ".csv";
            }
            ow = new OutputStreamWriter(new FileOutputStream(new File(filename)), encode);
            bw = new BufferedWriter(ow);
            bw.write(title.toString()); //中间，隔开不同的单元格，一次写一行
            bw.newLine();

            for (int i = 0; i < getBatteryNum(); i++) {
                StringBuilder content = new StringBuilder(1000);
                content.append(i + 1);
                for (Long aLong : longs) {
                    content.append(",").append(getNum(volatilityDetectionMapDay.get(aLong).get(i + 1)));
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
     * @param weekMap 计算每个周的数据和
     * @return Map<Long, Map < Integer, Double>>
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
     *
     * @param outPath 输出路径
     */
    public void outIcon(String outPath) {
        if (null == getVIN()) return;
        if (!outPath.endsWith("/")) outPath += "/";
        ///全生命周期频率
        if (getEntropyMapBatterSum().size() > 0) {
            CreatWord.creatWotdOne(CreatWord.getData(getEntropyMapBatterSum()), outPath, getVIN() + "_" + getType() + "EARate.docx");
        }
        if (getVolatilityDetectionMapBatterSum().size() > 0) {
            CreatWord.creatWotdOne(CreatWord.getData(getVolatilityDetectionMapBatterSum()), outPath, getVIN() + "_" + getType() + "VARate.docx");
        }
        if (getPressureDropConsistencyMapBatterSum().size() > 0) {
            CreatWord.creatWotdOne(CreatWord.getData(getPressureDropConsistencyMapBatterSum()), outPath, getVIN() + "_" + getType() + "PARate.docx");
        }
        //每周频率
        Map<Long, Map<Integer, Double>> weekRate = getWeekRate(getEntropyMapWeek());
        if (getEntropyMapWeek().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : weekRate.entrySet()) {
                CreatWord.creatWotdOne(longMapEntry.getValue(), outPath, getVIN() + "_" + getType() + "EWRate.docx");
            }
        } else if (getEntropyMapWeek().size() > 1) {
            CreatWord.createWotdTwoWeek(weekRate, getBatteryNum(), outPath, getVIN() + "_" + getType() + "EWRate.docx");
        }
        weekRate = getWeekRate(getVolatilityDetectionMapWeek());
        if (getVolatilityDetectionMapWeek().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : weekRate.entrySet()) {
                CreatWord.creatWotdOne(longMapEntry.getValue(), outPath, getVIN() + "_" + getType() + "VWRate.docx");
            }
        } else if (getVolatilityDetectionMapWeek().size() > 1) {
            CreatWord.createWotdTwoWeek(weekRate, getBatteryNum(), outPath, getVIN() + "_" + getType() + "VWRate.docx");
        }
        weekRate = getWeekRate(getPressureDropConsistencyMapWeek());
        if (getPressureDropConsistencyMapWeek().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Double>> longMapEntry : weekRate.entrySet()) {
                CreatWord.creatWotdOne(longMapEntry.getValue(), outPath, getVIN() + "_" + getType() + "PWRate.docx");
            }
        } else if (getPressureDropConsistencyMapWeek().size() > 1) {
            CreatWord.createWotdTwoWeek(weekRate, getBatteryNum(), outPath, getVIN() + "_" + getType() + "PWRate.docx");
        }
        //每500帧次数
        if (getEntropyMapNums().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getEntropyMapNums().entrySet()) {
                CreatWord.creatWotdOne(CreatWord.getData(longMapEntry.getValue()), outPath, getVIN() + "_" + getType() + "EENum.docx");
            }
        } else if (getEntropyMapNums().size() > 1) {
            CreatWord.createWotdTwoNums(getEntropyMapNums(), getBatteryNum(), outPath, getVIN() + "_" + getType() + "EENum.docx");
        }
        if (getVolatilityDetectionMapNums().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getVolatilityDetectionMapNums().entrySet()) {
                CreatWord.creatWotdOne(CreatWord.getData(longMapEntry.getValue()), outPath, getVIN() + "_" + getType() + "VENum.docx");
            }
        } else if (getVolatilityDetectionMapNums().size() > 1) {
            CreatWord.createWotdTwoNums(getVolatilityDetectionMapNums(), getBatteryNum(), outPath, getVIN() + "_" + getType() + "VENum.docx");
        }
        if (getPressureDropConsistencyMapNums().size() == 1) {
            for (Map.Entry<Long, Map<Integer, Integer>> longMapEntry : getPressureDropConsistencyMapNums().entrySet()) {
                CreatWord.creatWotdOne(CreatWord.getData(longMapEntry.getValue()), outPath, getVIN() + "_" + getType() + "PENum.docx");
            }
        } else if (getPressureDropConsistencyMapNums().size() > 1) {
            CreatWord.createWotdTwoNums(getPressureDropConsistencyMapNums(), getBatteryNum(), outPath, getVIN() + "_" + getType() + "PENum.docx");
        }
        Merge2.merge(outPath, getVIN(), getType(), this);
    }


    private boolean isNumber(String str) {
        if (StringUtils.isEmpty(str)) return true;
        for (int i = str.length(); --i >= 0; ) {
            if (str.charAt(i) != '.' && !Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
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
            return Integer.parseInt(null == string || "".equals(string.trim()) || "null".equals(string) ? "0" : string);
        } catch (NumberFormatException e) {
            System.out.println(string);
            return 0;
        }
    }

    private double getDoubleValue(Double d) {
        return null == d ? 0.0 : d;
    }


}
