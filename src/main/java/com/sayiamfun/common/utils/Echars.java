package com.sayiamfun.common.utils;

import lombok.Data;

@Data
public class Echars {
    private String name;
    private Integer num;

    public Echars(String name, Integer num) {
        this.name = name;
        this.num = num;
    }
}
