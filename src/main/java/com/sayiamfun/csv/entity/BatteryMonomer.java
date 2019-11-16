package com.sayiamfun.csv.entity;

import lombok.Data;

/**
 * @author liwenjie
 * @date 2019/10/10 + 11:07
 * @describe
 */
@Data
public class BatteryMonomer {

    private String code;
    private Double sum;
    private Integer num;

    @Override
    public String toString() {
        return "BatteryMonomer{" +
                "code='" + code + '\'' +
                ", sum=" + sum +
                ", num=" + num +
                '}';
    }
}
