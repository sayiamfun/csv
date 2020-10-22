package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 1.2.过压故障诊断模型：变异系数
 *
 * @author 76304
 */
public class OutFaultResult implements BulidResultInface {

    private static Logger logger = LoggerFactory.getLogger(OutFaultResult.class);
    public int type;
    public String vid;
    public String vin;
    public String time;
    public String soc;
    public String sTime;//开始迭代时的时间
    public String sSoc;//开始迭代时的soc
    public int unitNum;//单体包序号
    public int varType;//异常系数范围varType [0-3.5]:1,(3.5,4]:2,(4,最大值):3
    public int maxNum;//最大异常系数的单体代号
    public double maxValue;//最大异常系数的对应的绝对值
    private double[] vars;
    public String stringVars;
    public int[] quesRanks;//单体排名
    public String quesSingles;//可疑单体，取前几个
    public String speed;
    public String totalI;
    public String maxT;
    public String maxTMon;
    public String minT;
    public String minTMon;

    public OutFaultResult(){
    }

    public OutFaultResult(String vid, String vin, String time, String soc, int unitNum, int varType,
                          int maxNum, double maxValue, double[] vars) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.time = time;
        this.soc = soc;
        this.unitNum = unitNum;
        this.varType = varType;
        this.maxNum = maxNum;
        this.maxValue = maxValue;
        this.vars = vars;

        bulidVars(this.vars);
    }

    public OutFaultResult(String vid, String vin, int unitNum, VoltOverFaultResult result) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.unitNum = unitNum;
        this.time = result.time;
        this.soc = result.soc;
        this.vars = result.vars;

        bulidVars(this.vars);
        result = null;
    }

    public OutFaultResult(String vid, String vin, String time, String soc, String sTime, String sSoc,
                          int unitNum, int varType, int maxNum, double maxValue, double[] vars) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.time = time;
        this.soc = soc;
        this.sTime = sTime;
        this.sSoc = sSoc;
        this.unitNum = unitNum;
        this.varType = varType;
        this.maxNum = maxNum;
        this.maxValue = maxValue;
        this.vars = vars;

        bulidVars(this.vars);
    }

    public OutFaultResult(VoltOverFaultResult result, String vid, String vin, int unitNum) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.unitNum = unitNum;
        this.time = result.time;
        this.soc = result.soc;
        this.sTime = result.sTime;
        this.sSoc = result.sSoc;
        this.vars = result.vars;

        this.speed = result.speed;
        this.totalI = result.totalI;
        this.maxT = result.maxT;
        this.maxTMon = result.maxTMon;
        this.minT = result.minT;
        this.minTMon = result.minTMon;

        bulidVars(this.vars);
        result = null;
    }

    public OutFaultResult(VoltOverFaultResult result, String vid, String vin, int unitNum, int type) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.unitNum = unitNum;
        this.time = result.time;
        this.soc = result.soc;
        this.sTime = result.sTime;
        this.sSoc = result.sSoc;
        this.vars = result.vars;
        this.type = type;
        bulidVars(this.vars);

        this.speed = result.speed;
        this.totalI = result.totalI;
        this.maxT = result.maxT;
        this.maxTMon = result.maxTMon;
        this.minT = result.minT;
        this.minTMon = result.minTMon;

        result = null;
    }

    private void bulidVars(double[] vars) {
        StringBuilder sber = new StringBuilder();
        int len = vars.length;
        double maxVal = Math.abs(vars[0]);
        int maxSeq = 0;
        for (int i = 0; i < len - 1; i++) {
            if (maxVal < Math.abs(vars[i])) {
                maxVal = Math.abs(vars[i]);
                maxSeq = i;
            }
            sber.append(vars[i]).append("_");
        }
        sber.append(vars[len - 1]);

        if (maxVal < Math.abs(vars[len - 1])) {
            maxVal = Math.abs(vars[len - 1]);
            maxSeq = len - 1;
        }
        int valueType = 0;
        if (maxVal > 4) {
            valueType = 3;
        } else if (maxVal > 3.5) {
            valueType = 2;
        } else {
            valueType = 1;
        }
        this.varType = valueType;
        this.maxNum = maxSeq + 1;
        this.maxValue = maxVal;
        this.stringVars = sber.toString();
        buildQues(vars);
    }

    private void buildQues(double[] vars) {
        int len = vars.length;
        this.quesRanks = rankingVars(vars);
        int top = 4;
        if (len > 166) {
            top = (int) (len * 0.03);
        }
        StringBuilder sder = new StringBuilder();
        int count = 0;
        boolean needbreak = false;
        for (int tn = 1; tn <= top; tn++) {

            for (int idx = 0; idx < len; idx++) {

                if (quesRanks[idx] == tn) {
                    sder.append(idx + 1).append("_");
                    count++;
                    if (count >= top) {
                        needbreak = true;
                        break;
                    }
                }
            }
            if (needbreak) {
                break;
            }
        }

        this.quesSingles = sder.substring(0, sder.length() - 1);
    }

    private int[] rankingVars(double[] vars) {
        int len = vars.length;
        int[] ranks = new int[len];
        List<Double> absVars = new ArrayList<Double>(len);

        for (int i = 0; i < len; i++) {
            double val = Math.abs(vars[i]);
            if (!absVars.contains(val)) {

                absVars.add(val);
            }
        }
        Collections.sort(absVars);//从小到大排序
        Collections.reverse(absVars);//从大到小排序
        for (int i = 0; i < len; i++) {
            double val = Math.abs(vars[i]);
            ranks[i] = absVars.indexOf(val) + 1;
        }
        return ranks;
    }


    @Override
    public int getChargeType() {
        return this.type;
    }

    @Override
    public String toResString() {
        Map<String, String> stringStringMap = ConfigerIgnoreTheMonomer.getMonomerNum().get(vin);
        return vid + "," + vin + "," + time + "," + soc + "," + unitNum + "," + varType
                + "," + stringStringMap.get("" + maxNum) + "," + maxValue + "," + stringVars + "," + type;
    }

    @Override
    public String toReport(int seq) {
        Map<String, String> stringStringMap = ConfigerIgnoreTheMonomer.getMonomerNum().get(vin);
        String[] s = quesSingles.split(Constant.SPLITE_UNDERLINE);
        String tmp = "";
        for (String s1 : s) {
            tmp += stringStringMap.get(s1) + Constant.SPLITE_UNDERLINE;
        }
        return "	" + vin + "      " + seq + "              " + reportTime(sTime)
                + "	  " + reportTime(time) + "       " + StringUtils.substring(tmp, 0, tmp.length() - 1);
    }

    @Override
    public String getNowTime() {
        return this.time;
    }

    @Override
    public String toTitleContent() {
        return "UUID,VIN,时间,SOC,单体包编号,系数异常类型,最大系数单体号,最大系数值,单体异常系数值,类型充电1行驶2";
    }

    @Override
    public String toResAllString() {
        Map<String, String> stringStringMap = ConfigerIgnoreTheMonomer.getMonomerNum().get(vin);
        String[] s = quesSingles.split(Constant.SPLITE_UNDERLINE);
        String tmp = "";
        for (String s1 : s) {
            tmp += stringStringMap.get(s1) + Constant.SPLITE_UNDERLINE;
        }
        return vid + "," + vin + "," + time + "," + soc + "," + sTime + "," + sSoc + "," + unitNum + ","
                + varType + "," + stringStringMap.get("" + maxNum) + "," + maxValue + "," + stringVars + "," + StringUtils.substring(tmp, 0, tmp.length() - 1) + "," + type
                + "," + speed + "," + totalI + "," + maxT + "," + maxTMon + "," + minT + "," + minTMon;
    }

    @Override
    public String toTitleAllContent() {
        return "UUID,VIN,当前时间,当前SOC,迭代开始时间,迭代开始SOC,单体包编号,系数异常类型,最大系数单体号,最大系数值,"
                + "单体异常系数值,排名前的单体代号,类型充电1行驶2" +
                ",速度,总电流,最高温度,最高温度单体编号,最低温度,最低温度单体编号";
    }

    private String reportTime(String time) {
        if (null != time && time.length() == 14) {
            return new String(time.substring(0, 4) + "-" + time.substring(4, 6)
                    + "-" + time.substring(6, 8) + " " + time.substring(8, 10)
                    + ":" + time.substring(10, 12) + ":" + time.substring(12, 14));
        }
        return time;
    }

    public static void main(String[] args) {
        OutFaultResult res = new OutFaultResult("", "", "", "", 0, 1, 1, 1, new double[]{2});
        double[] varst = new double[]{2.3, 5.3, 1.2, 9.5, 5.6, 12, 66, 31, 1.0, -9, -7, -1.2};
        res.buildQues(varst);
        logger.info(Arrays.toString(res.quesRanks));
        logger.info(res.quesSingles);
    }

}
