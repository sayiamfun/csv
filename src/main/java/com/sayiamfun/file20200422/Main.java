package com.sayiamfun.file20200422;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String s26 = "L6T78Y4W4GN084987,\n" +
                "LB377Y2W5KA002338,\n" +
                "LB378Y4W0JA184037,\n" +
                "LB378Y4W0JA185544,\n" +
                "LB378Y4W1HA083647,\n" +
                "LB378Y4W1JA181275,\n" +
                "LB378Y4W1JA186556,\n" +
                "LB378Y4W1JA199355,\n" +
                "LB378Y4W3JA177728,\n" +
                "LB378Y4W3JA186669,\n" +
                "LB378Y4W4JA179388,\n" +
                "LB378Y4W4JA186485,\n" +
                "LB378Y4W6JA182180,\n" +
                "LB378Y4W7JA173018,\n" +
                "LB378Y4W7JA178042,\n" +
                "LB378Y4W7JA179420,\n" +
                "LB378Y4W7JA184374,\n" +
                "LB378Y4W8JA179264,\n" +
                "LB378Y4W8JA182021,\n" +
                "LB378Y4W9JA177443,\n" +
                "LB378Y4W9JA177815,\n" +
                "LB378Y4W9JA199829,\n" +
                "LB378Y4W9KA005740,\n" +
                "LB378Y4WXJA179539,\n" +
                "LB378Y4WXJA186684,\n" +
                "LB378Y4WXJA197510";

        String s27 = "LB378Y4W6JA182180,\n" +
                "LB378Y4W0JA185544,\n" +
                "LB378Y4W7JA179420,\n" +
                "LB378Y4W4JA186485,\n" +
                "LB378Y4W9KA005740,\n" +
                "LB378Y4W8JA179264,\n" +
                "LB378Y4W4JA179388,\n" +
                "LB378Y4W0JA184037,\n" +
                "LB378Y4W1JA181275,\n" +
                "LB378Y4W8JA182021,\n" +
                "LB378Y4WXJA197510,\n" +
                "LB378Y4W3JA177728,\n" +
                "LB378Y4W3JA186669,\n" +
                "LB378Y4W7JA184374,\n" +
                "LB378Y4W9JA199829,\n" +
                "LB378Y4W9JA177815,\n" +
                "LB378Y4W1JA186556,\n" +
                "LB378Y4W1JA199355,\n" +
                "LB378Y4WXJA186684,\n" +
                "LB378Y4W1HA083647,\n" +
                "LB378Y4W9JA177443,\n" +
                "LB378Y4W7JA173018,\n" +
                "LB378Y4W7JA178042,\n" +
                "L6T78Y4W4GN084987,\n" +
                "LB377Y2W5KA002338,\n" +
                "LB378Y4WXJA179539";
        List<String> strings27 = Arrays.asList(s27.replaceAll("\n","").split(","));
        List<String> strings26 = Arrays.asList(s26.replaceAll("\n","").split(","));
        for (String s : strings26) {
            if(!strings27.contains(s)){
                System.out.println(s);
            }
        }

    }
}
