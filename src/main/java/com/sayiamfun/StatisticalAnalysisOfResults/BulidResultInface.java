package com.sayiamfun.StatisticalAnalysisOfResults;

public interface BulidResultInface {

    public int getChargeType();

    public String toResString();

    public String toTitleContent();

    public String toResAllString();

    public String toTitleAllContent();

    /**
     * @param seq 序号
     * @return
     */
    public String toReport(int seq);

    String getNowTime();
}
