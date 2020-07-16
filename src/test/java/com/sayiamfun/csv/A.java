package com.sayiamfun.csv;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class A {

    public A foo() {
        return this;
    }

    public static void main(String[] args) {
        String sv = "DgEN/g4BDgEN/w4CDf8N/w4ADf8OAg4ADgEN9g4DDgMN/g4CDgIOBA4ADgIN/g3/DgEOAQ4CDf8OAg4DDf4OAw3+Df4N/Q4ADgEOAw4DDgMN/g4ADgIN/g4ADgMOAQ4ADf4OAw39DgAOAQ4DDgEN/w4DDgIN/w4DDgIOAw4ADgIOAg4BDgMOAw4CDgIOAw4ADf4N/w4ADf8OAg4BDf4OAQ3/Df4N/g3/Df4N/g4ADf0OAQ3/Df8N/w3+Df8N/g==";
        if (null != sv) {
            byte[] singleVol = Base64.getDecoder().decode(sv);
            int barlen = singleVol.length;
            int len = barlen / 2;
            //要忽略的单体
            List<String> strings = new ArrayList<>();
            double[] arrs = new double[len + 3];
            double max = -99999;
            double min = 99999;
            double totalVolt = 0;
            int index = 0;
            int maxNum = 0;//最大值对应的编号
            int minNum = 0;//最小值对应的编号

            for (int i = 0; i < barlen - 1; i += 2) {
                //如果单体被忽略则直接跳过
                if (strings.contains("" + (i / 2 + 1))) continue;
                int n1 = singleVol[i] < 0 ? (256 + singleVol[i]) : singleVol[i];
                int n2 = singleVol[i + 1] < 0 ? (256 + singleVol[i + 1]) : singleVol[i + 1];
                int thousand = (n1 * 256 + n2);

                double voltVal = thousand / 1000.0;
                if (max < voltVal) {
                    max = voltVal;
                    maxNum = i / 2;
                }
                if (min > voltVal) {
                    min = voltVal;
                    minNum = i / 2;
                }
                arrs[index++] = voltVal;
                totalVolt += voltVal;
            }
            if (max != -99999 && min != 99999) {
                arrs[len] = min;
                arrs[len + 1] = max;
                arrs[len + 2] = totalVolt / len;
            }
            for (double arr : arrs) {
                System.out.print(arr+",");
            }
        }
    }
}
