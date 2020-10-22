package com.sayiamfun.StatisticalAnalysisOfResults;

/**
 * 1.2.过压故障诊断模型
 *
 * @author 76304
 */
public class VoltOverFaultResult {

    String time;
    String soc;
    String sTime;
    String sSoc;
    String speed;
    String totalI;
    String maxT;
    String maxTMon;
    String minT;
    String minTMon;
    double[] vars;

    public VoltOverFaultResult(String time, String soc, double[] vars) {
        super();
        this.time = time;
        this.soc = soc;
        this.vars = vars;
    }

    public VoltOverFaultResult(String time, String soc, String sTime, String sSoc, double[] vars) {
        super();
        this.time = time;
        this.soc = soc;
        this.sTime = sTime;
        this.sSoc = sSoc;
        this.vars = vars;
    }

    public VoltOverFaultResult(String time, String soc, String sTime, String sSoc, double[] vars, String speed, String totalI, String maxT, String maxTMon, String minT, String minTMon) {
        super();
        this.time = time;
        this.soc = soc;
        this.sTime = sTime;
        this.sSoc = sSoc;
        this.vars = vars;

        this.speed = speed;
        this.totalI = totalI;
        this.maxT = maxT;
        this.maxTMon = maxTMon;
        this.minT = minT;
        this.minTMon = minTMon;
    }
}
