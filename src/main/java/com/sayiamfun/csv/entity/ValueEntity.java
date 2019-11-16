package com.sayiamfun.csv.entity;

import lombok.Data;

/**
 * @author liwenjie
 * @date 2019/10/11 + 11:34
 * @describe
 */
@Data
public class ValueEntity {
//    UUID	车型	配置号	VIN	报警名	时间	实际值	报警阈值	持续帧数	问题编号
    private String uuid;
    private String carType;
    private String codeNum;
    private String vin;
    private String alarmName;
    private String date;
    private String realValue;
    private String alarmThreshold;
    private String num;
    private String questionNumber;

    @Override
    public String toString() {
        return "ValueEntity{" +
                "uuid='" + uuid + '\'' +
                ", carType='" + carType + '\'' +
                ", codeNum='" + codeNum + '\'' +
                ", vin='" + vin + '\'' +
                ", alarmName='" + alarmName + '\'' +
                ", date='" + date + '\'' +
                ", realValue='" + realValue + '\'' +
                ", alarmThreshold='" + alarmThreshold + '\'' +
                ", num='" + num + '\'' +
                ", questionNumber='" + questionNumber + '\'' +
                '}';
    }
}
