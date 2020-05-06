package com.sayiamfun.file2020313;

import java.math.BigDecimal;

public class NewData {

    public static void main(String[] args) {
        String s = "'373.2','373.3','373.4','373.6','373.8','373.7','374.1','373.9','373.7','373.8','373.9','374.1','374.2','374.4','374.3','374.5','374.3','374.5','374.3','374.5','374.7','374.8','375.0','375.1','374.9','375.0','374.9','375.0','374.9','375.1','375.3','375.4','375.3','375.5','375.4','375.3','375.5','375.6','375.5','375.7','375.6','375.5','375.6','375.5','375.9','375.7','376.0','375.9','376.0','375.9','375.7','375.9','375.7','376.0','376.1','376.0','376.2','376.0','376.2','376.0','376.2','376.0','376.1','376.5','376.2','376.3','376.2','376.5','376.3','376.6','376.3','376.5','376.6','376.7','376.5','376.3','375.7','376.0','376.1','376.0','376.2','376.0','376.2','376.0','376.2','376.0','376.1','376.5','376.2','376.3','376.2','376.5','376.3','376.6','376.3','376.5','376.6','376.7','376.5','376.3'";
        double heigth1 = 80;
        double height2 = 50;
        double heigth3 = 20;
        double base = 5;
        double tmp = 1.4;


        getData(s, heigth1, height2, heigth3, base, tmp);
    }

    public static void getData(String s, double heigth1, double height2, double heigth3, double base, double tmp) {
        String[] split = s.split(",");
        int index1 = split.length / 2;
        double add1 = ((heigth1 - base)) / index1;
        StringBuilder stringBuilder = new StringBuilder();
        int total = 0;

        double flag = base;
        int i = 0;
        while (flag <= heigth1) {
            flag = base + getSquaer(add1, i, tmp) + getRandomDouble();
            stringBuilder.append(flag + ",");
            total++;
            i++;
        }
        while (flag >= base + add1) {
            flag = base + getSquaer(add1, i - 2, tmp) + getRandomDouble();
            stringBuilder.append(flag + ",");
            total++;
            i--;
        }
        stringBuilder.append(flag + getRandomDouble() + ",");
        total++;
        while (flag <= height2) {
            flag = base + getSquaer(add1, i, tmp) + getRandomDouble();
            stringBuilder.append(flag + ",");
            total++;
            i++;
        }
        while (flag >= base + add1) {
            flag = base + getSquaer(add1, i - 2, tmp) + getRandomDouble();
            stringBuilder.append(flag + ",");
            total++;
            i--;
        }
        stringBuilder.append(flag + getRandomDouble() + ",");
        total++;
        while (flag <= heigth3) {
            flag = base + getSquaer(add1, i, tmp) + getRandomDouble();
            stringBuilder.append(flag + ",");
            total++;
            i++;
        }
        while (flag >= base + add1) {
            flag = base + getSquaer(add1, i - 2, tmp) + getRandomDouble();
            stringBuilder.append(flag + ",");
            total++;
            i--;
        }
        for (int j = total; j < split.length; j++) {
            stringBuilder.append(flag + getRandomDouble() + ",");
        }
        System.out.println(stringBuilder.toString());
    }

    /**
     * 获取一个波峰
     * @param stringBuilder
     * @return
     */
    public static void getTotal(StringBuilder stringBuilder){

    }
    /**
     * 获取一个随机的两位小数
     * @return
     */
    public static double getRandomDouble() {
        double random = Math.random();
        BigDecimal b = new BigDecimal(random);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取增量
     * @param value
     * @param num
     * @param tmp
     * @return
     */
    public static double getSquaer(double value, int num, double tmp) {
        double thetmp = tmp;
        for (int i = 0; i < num; i++) {
            tmp *= thetmp;
        }
        return tmp + value * num;
    }
}
