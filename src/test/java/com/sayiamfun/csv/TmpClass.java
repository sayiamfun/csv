package com.sayiamfun.csv;

import java.util.Arrays;
import java.util.List;

public class TmpClass {

    public static void main(String[] args) {
//        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//        System.out.println("程序类加载器：" + systemClassLoader);
//        ClassLoader parent = systemClassLoader.getParent();
//        System.out.println("程序类加载器父类（扩展类加载器）：" + parent);
//        ClassLoader bootStrapClassLoader = parent.getParent();
//        System.out.println("拓展类加载器父类（启动类加载器）：" + bootStrapClassLoader);
        String s = new StringBuilder(200).toString();
        System.out.println(s);
        List<String> list = Arrays.asList(s.split(","));
        System.out.println(list);
    }

}
