package com.sayiamfun.StatisticalAnalysisOfResults;


import java.util.Arrays;
import java.util.Map;

/**
 * 压降一致性判断模型
 *
 * @author wza
 */
public class StepConsistencyResult implements BulidResultInface {

    private String vid;
    private String vin;
    private String name;

    private String lastTime;
    private String nowTime;
    private String lastSoc;
    private String nowSoc;
    private String lastVolts;
    private String nowVolts;
    private int unitNum;//单体包编号

    private String lastSpeed;
    private String lasttotalI;
    private String lastMaxT;
    private String lastMaxTMon;
    private String lastMinT;
    private String lastMinTMon;

    private String nowSpeed;
    private String nowtotalI;
    private String nowMaxT;
    private String nowMaxTMon;
    private String nowMinT;
    private String nowMinTMon;

    private double maxStep; //绝对值最大压差
    private double minStep; //绝对值最小压差
    private int maxStepSingleNum; //绝对值最大压差单体编号
    private int minStepSingleNum; //绝对值最小压差单体编号
    private double diploid; //压差比（绝对值最大压差与绝对值最小压差比）
    private int type;//type 1:充电，2:行驶

    public StepConsistencyResult() {

    }

    public String getLastSpeed() {
        return lastSpeed;
    }

    public void setLastSpeed(String lastSpeed) {
        this.lastSpeed = lastSpeed;
    }

    public String getLasttotalI() {
        return lasttotalI;
    }

    public void setLasttotalI(String lasttotalI) {
        this.lasttotalI = lasttotalI;
    }

    public String getLastMaxT() {
        return lastMaxT;
    }

    public void setLastMaxT(String lastMaxT) {
        this.lastMaxT = lastMaxT;
    }

    public String getLastMaxTMon() {
        return lastMaxTMon;
    }

    public void setLastMaxTMon(String lastMaxTMon) {
        this.lastMaxTMon = lastMaxTMon;
    }

    public String getLastMinT() {
        return lastMinT;
    }

    public void setLastMinT(String lastMinT) {
        this.lastMinT = lastMinT;
    }

    public String getLastMinTMon() {
        return lastMinTMon;
    }

    public void setLastMinTMon(String lastMinTMon) {
        this.lastMinTMon = lastMinTMon;
    }

    public String getNowSpeed() {
        return nowSpeed;
    }

    public void setNowSpeed(String nowSpeed) {
        this.nowSpeed = nowSpeed;
    }

    public String getNowtotalI() {
        return nowtotalI;
    }

    public void setNowtotalI(String nowtotalI) {
        this.nowtotalI = nowtotalI;
    }

    public String getNowMaxT() {
        return nowMaxT;
    }

    public void setNowMaxT(String nowMaxT) {
        this.nowMaxT = nowMaxT;
    }

    public String getNowMaxTMon() {
        return nowMaxTMon;
    }

    public void setNowMaxTMon(String nowMaxTMon) {
        this.nowMaxTMon = nowMaxTMon;
    }

    public String getNowMinT() {
        return nowMinT;
    }

    public void setNowMinT(String nowMinT) {
        this.nowMinT = nowMinT;
    }

    public String getNowMinTMon() {
        return nowMinTMon;
    }

    public void setNowMinTMon(String nowMinTMon) {
        this.nowMinTMon = nowMinTMon;
    }

    public StepConsistencyResult(String vid, String vin, String name, String lastTime, String nowTime, String lastSoc,
                                 String nowSoc, double[] lastVolts, double[] nowVolts, int unitNum, double maxStep, double minStep,
                                 int maxStepSingleNum, int minStepSingleNum, double diploid, int type, String[] lastTimeAndSoc, String speed, String totalI, String maxT, String maxTMon, String minT, String minTMon) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.name = name;
        this.lastTime = lastTime;
        this.nowTime = nowTime;
        this.lastSoc = lastSoc;
        this.nowSoc = nowSoc;
        String lastVs = bulidVolts(lastVolts);
        lastVs = null == lastVs ? "" : lastVs;
        this.lastVolts = lastVs;

        String nowVs = bulidVolts(nowVolts);
        nowVs = null == nowVs ? "" : nowVs;
        this.nowVolts = nowVs;
        this.unitNum = unitNum;
        this.maxStep = maxStep;
        this.minStep = minStep;
        this.maxStepSingleNum = maxStepSingleNum;
        this.minStepSingleNum = minStepSingleNum;
        this.diploid = diploid;
        this.type = type;

        this.lastSpeed = lastTimeAndSoc[2];
        this.lasttotalI = lastTimeAndSoc[3];
        this.lastMaxT = lastTimeAndSoc[4];
        this.lastMaxTMon = lastTimeAndSoc[5];
        this.lastMinT = lastTimeAndSoc[6];
        this.lastMinTMon = lastTimeAndSoc[7];

        this.nowSpeed = speed;
        this.nowtotalI = totalI;
        this.nowMaxT = maxT;
        this.nowMaxTMon = maxTMon;
        this.nowMinT = minT;
        this.nowMinTMon = minTMon;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getLastSoc() {
        return lastSoc;
    }

    public void setLastSoc(String lastSoc) {
        this.lastSoc = lastSoc;
    }

    public String getNowSoc() {
        return nowSoc;
    }

    public void setNowSoc(String nowSoc) {
        this.nowSoc = nowSoc;
    }

    public String getLastVolts() {
        return lastVolts;
    }

    public void setLastVolts(String lastVolts) {
        this.lastVolts = lastVolts;
    }

    public String getNowVolts() {
        return nowVolts;
    }

    public void setNowVolts(String nowVolts) {
        this.nowVolts = nowVolts;
    }

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

    public double getMaxStep() {
        return maxStep;
    }

    public void setMaxStep(double maxStep) {
        this.maxStep = maxStep;
    }

    public double getMinStep() {
        return minStep;
    }

    public void setMinStep(double minStep) {
        this.minStep = minStep;
    }

    public int getMaxStepSingleNum() {
        return maxStepSingleNum;
    }

    public void setMaxStepSingleNum(int maxStepSingleNum) {
        this.maxStepSingleNum = maxStepSingleNum;
    }

    public int getMinStepSingleNum() {
        return minStepSingleNum;
    }

    public void setMinStepSingleNum(int minStepSingleNum) {
        this.minStepSingleNum = minStepSingleNum;
    }

    public double getDiploid() {
        return diploid;
    }

    public void setDiploid(double diploid) {
        this.diploid = diploid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public StepConsistencyResult(String vid, String vin, String name, String lastTime, String nowTime, String lastSoc,
                                 String nowSoc, double[] lastVolts, double[] nowVolts, int unitNum, double maxStep, double minStep,
                                 int maxStepSingleNum, int minStepSingleNum, double diploid, int type) {
        super();
        this.vid = vid;
        this.vin = vin;
        this.name = name;
        this.lastTime = lastTime;
        this.nowTime = nowTime;
        this.lastSoc = lastSoc;
        this.nowSoc = nowSoc;
        String lastVs = bulidVolts(lastVolts);
        lastVs = null == lastVs ? "" : lastVs;
        this.lastVolts = lastVs;

        String nowVs = bulidVolts(nowVolts);
        nowVs = null == nowVs ? "" : nowVs;
        this.nowVolts = nowVs;
        this.unitNum = unitNum;
        this.maxStep = maxStep;
        this.minStep = minStep;
        this.maxStepSingleNum = maxStepSingleNum;
        this.minStepSingleNum = minStepSingleNum;
        this.diploid = diploid;
        this.type = type;
    }

    private String bulidVolts(double[] volts) {
        if (null != volts && volts.length > 0) {
            return Arrays.toString(volts).replace(",", "_")
                    .replace(" ", "")
                    .replace("[", "")
                    .replace("]", "");
        }
        return null;
    }

    @Override
    public int getChargeType() {
        return this.type;
    }

    @Override
    public String toResString() {
        Map<String, String> stringStringMap = ConfigerIgnoreTheMonomer.getMonomerNum().get(vin);
        return vin + "," + name + "," + lastTime + "," + nowTime + "," + lastSoc + "," + nowSoc + "," + lastVolts + "," + nowVolts + ","
                + unitNum + "," + maxStep + "," + minStep + "," + stringStringMap.get("" + maxStepSingleNum) + "," + stringStringMap.get("" + minStepSingleNum) + "," + diploid + "," + type + ","
                + lastSpeed + "," + nowSpeed + "," + lasttotalI + "," + nowtotalI + "," + lastMaxT + "," + nowMaxT + "," + lastMaxTMon + "," + nowMaxTMon + "," + lastMinT + "," + nowMinT + "," + lastMinTMon + "," + nowMinTMon;
    }

    @Override
    public String toReport(int seq) {
        return "";
    }

    @Override
    public String toTitleContent() {
        return "VIN,报警名,上一帧时间,当前帧时间,上一帧SOC,当前帧SOC,上一帧单体,当前帧单体,单体包号,绝对值最大压差值,"
                + "绝对值最小压差值,压差绝对值最大值编号,压差绝对值最小值编号,最大压差绝对值与最小压差绝对值比,充电1或行驶2" +
                ",上一帧速度,当前帧速度,上一帧总电流,当前帧总电流,上一帧最高温度,当前帧最高温度,上一帧最高温度单体编号,当前帧最高温度单体编号,上一帧最低温度,当前帧最低温度,上一帧最低温度单体编号,当前帧最低温度单体编号";
    }

    @Override
    public String toResAllString() {
        return vid + "," + toResString();
    }

    @Override
    public String toTitleAllContent() {
        return "UUID" + "," + toTitleContent();
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
        double[] varst = new double[]{2.3, 5.3, 1.2, 9.5, 5.6, 12, 66, 31, 1.0, 9, 7, 1.2};
    }


}
