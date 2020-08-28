package com.sayiamfun.MonomerResistanceCompare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstStepData {

    private String vin;  //vin
    private Long time;  //时间
    private Double SOC; //SOC
    private String speed;
    private String carStatus; //车辆状态
    private String chargeStatus;  //充电状态
    private Double totalE;  //总电流
    private Double maxV;    //最大电压
    private Integer maxVNum;    //最大电压单体
    private Double minV;    //最小电压
    private Integer minVNum;  //最小电压单体
    private Double subV;    //压差
    private List<String> monAndVList = new LinkedList<>();//电压列表

    public FirstStepData(String vin, Long time, Double SOC, String carStatus, String chargeStatus, Double totalE, Double maxV, Integer maxVNum, Double minV, Integer minVNum, Double subV, List<String> monAndVList) {
        this.vin = vin;
        this.time = time;
        this.SOC = SOC;
        this.carStatus = carStatus;
        this.chargeStatus = chargeStatus;
        this.totalE = totalE;
        this.maxV = maxV;
        this.maxVNum = maxVNum;
        this.minV = minV;
        this.minVNum = minVNum;
        this.subV = subV;
        this.monAndVList = monAndVList;
    }

    public boolean checkFirstData() {
        return null == this.getVin() ||
                null != this.getTime() ||
                null == this.getChargeStatus() ||
                null == this.getMonAndVList();
    }
}
