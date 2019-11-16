package com.sayiamfun.csv;

import com.sayiamfun.csv.entity.Pressure;
import com.sayiamfun.csv.entity.ThreeValue;
import com.sayiamfun.csv.entity.ValueEntity;
import com.sayiamfun.csv.utils.Outs;
import com.sayiamfun.csv.utils.PressureDropConsistency;
import com.sayiamfun.common.ScanPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author liwenjie
 * @date 2019/10/11 + 10:19
 * @describe  处理压降一致性文件
 */
public class PressureMain {

    public static void main(String[] args) {
        getMaxAndMin();
    }

    public static void differenceRatio() {
        try {
            DecimalFormat df = new DecimalFormat("######0.00");
            LinkedList<ThreeValue> list = new LinkedList<>();
            ArrayList<String> fils = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String fil : fils) {
                PressureDropConsistency.differenceRatio(list, fil);
            }
//            Outs.toPressureAbsCSV(map, "最大压差和最小压差倍数关系.csv");
            String temp = "";
            int i = 0;
            int j = 0;
            for (ThreeValue threeValue : list) {
                /*i++;
                if (i % 50 == 0) {
                    i = 0;
                    temp += "\n";
                }*/
                temp += "\n";
                temp += "[" + threeValue.getMax() + "," + threeValue.getMin() + "," + j++ + "," + df.format(Double.parseDouble(threeValue.getDiv())) + ",2019],";
            }
            System.out.println(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 单体出现最大压差和最小压差次数统计
     * @author liwenjie
     * @date 2019/10/11   11:23
     * @params []
     */
    public static void getNum() {
        try {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            Map<String, Integer> childrenMap = new TreeMap<>();
            Map<String, Integer> childrenMap2 = new TreeMap<>();
            map.put("1", childrenMap);
            map.put("2", childrenMap2);
            ArrayList<String> fils = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String fil : fils) {
                PressureDropConsistency.getNum(map, fil);
            }
            Outs.toPressureCSV(map, "压降一致性单体压差最大最小次数统计最后一月.csv");
           /*Map<String, Integer> map1 = map.get("1");
            Map<String, Integer> map2 = map.get("2");
            Set<String> set = new HashSet<>();
            for (String s : map1.keySet()) {
                set.add(s);
            }
            for (String s : map2.keySet()) {
                set.add(s);
            }
            for (String s : set) {
                System.out.println(s+","+(map1.get(s)!=null?map1.get(s):"0")+","+(map2.get(s)!=null?map2.get(s):"0"));
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 获取最大压差值和最小压差值散点
     * @author liwenjie
     * @date 2019/10/11   10:23
     * @params []
     */
    public static void getMaxAndMin() {
        try {
            List<Pressure> list = new ArrayList<>();
            ArrayList<String> fils = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String fil : fils) {
                PressureDropConsistency.differenceValueOfMaxAndMin(list, fil);
            }
            int i = 0;
            for (Pressure pressure : list) {
                i++;
                if (i % 10 == 0) {
                    i = 0;
                    System.out.println();
                }
                System.out.print("[" + pressure.getMax() + "," + pressure.getMin() + "],");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void method() {
        try {
            List<ValueEntity> list = new LinkedList<>();
            ArrayList<String> fils = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String fil : fils) {
                if (!fil.contains("值参数")) continue;
                BufferedReader reader = new BufferedReader(new FileReader(new File(fil)));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
                String line = null;
                boolean b = true;
                while ((line = reader.readLine()) != null) {
                    if (b) {
                        b = false;
                        continue;
                    }
                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    System.out.println(item.length);
                    ValueEntity valueEntity = new ValueEntity();
                    valueEntity.setUuid(item[0]);
                    valueEntity.setCarType(item[1]);
                    valueEntity.setCodeNum(item[2]);
                    valueEntity.setVin(item[3]);
                    valueEntity.setAlarmName(item[4]);
                    valueEntity.setDate(item[5]);
                    valueEntity.setRealValue(item[6]);
                    valueEntity.setAlarmThreshold(item[7]);
                    valueEntity.setNum(item[8]);
//                    valueEntity.setQuestionNumber(item[9]);
                    list.add(valueEntity);
                }
            }
            for (ValueEntity valueEntity : list) {
                System.out.println(valueEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
