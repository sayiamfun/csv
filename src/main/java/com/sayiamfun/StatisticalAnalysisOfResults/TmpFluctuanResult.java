package com.sayiamfun.StatisticalAnalysisOfResults;

import lombok.Data;

import java.util.List;

@Data
public class TmpFluctuanResult {

    private List<Integer> list;//异常单体
    private Double quaAvg;//方差平均值
    private Double quaStand;//平均值
    private Integer maxMon;//最大单体
    private Double maxNum;//最大倍数
    private List<String> quaList;//波动单体参数表

}
