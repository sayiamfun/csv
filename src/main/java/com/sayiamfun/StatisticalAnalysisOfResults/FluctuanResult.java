package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.Constant;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * [6]波动一致性故障诊断模型
 *
 * @author wza
 */
@Data
public class FluctuanResult implements BulidResultInface {

    private DecimalFormat df7 = new DecimalFormat("0.0000000");


    private String vid;
    private String vin;
    private String startTime;
    private String endTime;
    private String startSoc;
    private String endSoc;
    private String abnormalSingles;
    private int type;//type 1:充电，2:行驶
    private int unitNum;//单体包编号
    private String speed;
    private String totalI;
    private String maxT;
    private String maxTMon;
    private String minT;
    private String minTMon;

    /**
     * 后期添加
     */
    private Double quaAvg;//方差平均值
    private Double quaStand;//平均值
    private Integer maxMon;//最大单体
    private Double maxNum;//最大倍数
    private List<String> quaList;//波动单体参数表
    private String quaListString;//波动单体参数表字符串


    public FluctuanResult() {
    }

    public FluctuanResult(String vid, String vin, String startTime, String endTime, String startSoc, String endSoc,
                          List<Integer> singles, int type, int unitNum) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startSoc = startSoc;
        this.endSoc = endSoc;
        String abnormalSingles = getSingleNums(singles);
        this.abnormalSingles = abnormalSingles;
        this.type = type;
        this.unitNum = unitNum;
    }

    public FluctuanResult(String vid, String vin, String startTime, String endTime, String startSoc, String endSoc,
                          List<Integer> singles, int type, int unitNum, TmpFluctuanResult tmpFluctuanResult) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startSoc = startSoc;
        this.endSoc = endSoc;
        String abnormalSingles = getSingleNums(singles);
        this.abnormalSingles = abnormalSingles;
        this.type = type;
        this.unitNum = unitNum;

        this.quaAvg = tmpFluctuanResult.getQuaAvg();
        this.quaStand = tmpFluctuanResult.getQuaStand();
        this.maxMon = tmpFluctuanResult.getMaxMon();
        this.maxNum = tmpFluctuanResult.getMaxNum();
        this.quaList = tmpFluctuanResult.getQuaList();
    }

    public FluctuanResult(String vid, String vin, String startTime, String endTime, String startSoc, String endSoc,
                          List<Integer> singles, int type, int unitNum, TmpFluctuanResult tmpFluctuanResult, String speed, double totalElec, double maxSingleTemp, int maxTempNum, double minSingleTemp, int minTempNum) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startSoc = startSoc;
        this.endSoc = endSoc;
        String abnormalSingles = getSingleNums(singles);
        this.abnormalSingles = abnormalSingles;
        this.type = type;
        this.unitNum = unitNum;

        this.quaAvg = tmpFluctuanResult.getQuaAvg();
        this.quaStand = tmpFluctuanResult.getQuaStand();
        this.maxMon = tmpFluctuanResult.getMaxMon();
        this.maxNum = tmpFluctuanResult.getMaxNum();
        this.quaList = tmpFluctuanResult.getQuaList();

        this.speed = speed;
        this.totalI = "" + totalElec;
        this.maxT = "" + maxSingleTemp;
        this.maxTMon = "" + maxTempNum;
        this.minT = "" + minSingleTemp;
        this.minTMon = "" + minTempNum;
    }

    String getSingleNums(List<Integer> singles) {

        if (null != singles && singles.size() > 0) {
            StringBuilder sder = new StringBuilder();
            for (Integer singNum : singles) {
                sder.append(singNum).append("_");
            }
            int len = sder.length();
            if (len > 1) {
                return sder.substring(0, len - 1);
            }
        }

        return "";
    }

    @Override
    public int getChargeType() {
        return this.type;
    }

    @Override
    public String toResString() {
        Map<String, String> stringStringMap = ConfigerIgnoreTheMonomer.getMonomerNum().get(vin);
        String[] s = abnormalSingles.split(Constant.SPLITE_UNDERLINE);
        StringBuilder stringBuilder = new StringBuilder(500);
        for (String s1 : s) {
            stringBuilder.append(stringStringMap.get(s1)).append(Constant.SPLITE_UNDERLINE);
        }
        StringBuilder qua = new StringBuilder(500);
        for (String s1 : quaList) {
            String[] split = s1.split(":");
            qua.append(stringStringMap.get(split[0])).append(":").append(split[1]).append("_");
        }
        return vid + "," + vin + "," + startTime + "," + endTime + "," + startSoc
                + "," + endSoc + "," + StringUtils.substring(stringBuilder.toString(), 0, stringBuilder.length() - 1) + "," + type + "," + unitNum + "," +
                df7.format(quaAvg) + "," + df7.format(quaStand) + "," + stringStringMap.get("" + maxMon) + "," + maxNum + "," + qua.toString()
                + "," + speed + "," + totalI + "," + maxT + "," + maxTMon + "," + minT + "," + minTMon;
    }

    @Override
    public String toReport(int seq) {
        return "";
    }

    @Override
    public String getNowTime() {
        return this.endTime;
    }

    @Override
    public String toTitleContent() {
        return "UUID,VIN,滑窗第一帧时间,滑窗当前时间,滑窗第一帧SOC,滑窗当前SOC,异常单体,行驶2或充电1,异常单体所在包编号," +
                "方差平均值,标准差,最大波动单体,最大倍数,波动单体参数表" +
                ",速度,总电流,最高温度,最高温度单体编号,最低温度,最低温度单体编号";
    }

    @Override
    public String toResAllString() {
        return toResString();
    }

    @Override
    public String toTitleAllContent() {
        return toTitleContent();
    }

    String reportTime(String time) {
        if (null != time && time.length() == 14) {
            return new String(time.substring(0, 4) + "-" + time.substring(4, 6)
                    + "-" + time.substring(6, 8) + " " + time.substring(8, 10)
                    + ":" + time.substring(10, 12) + ":" + time.substring(12, 14));
        }
        return time;
    }
}
