package com.sayiamfun.file20200418;

import com.sayiamfun.StatisticalAnalysisOfResults.FrequencyStatistics;
import com.sayiamfun.common.DateUtils;
import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ParseException {

        System.out.println("0.0591_0.0835_0.0824_0.0972_0.0855_0.0853_0.0838_0.079_0.0823_0.0772_0.0865_0.0877_0.0983_0.0983_0.0924_0.0674_0.0971_0.0973_0.0657_0.0836_0.0726_0.0921_0.0899_0.0752_0.0659_0.0776_0.0808_0.0756_0.085_0.0665_0.0612_0.0508_0.0646_0.0571_0.0678_0.0524_0.0643_0.0404_0.0547_0.0443_0.0605_0.0916_0.0646_0.0789_0.0857_0.0762_0.0431_0.0646_0.0497_0.0782_0.0858_0.0824_0.068_0.0736_0.0768_0.0892_0.0859_0.062_0.0538_0.0979_0.0909_0.0889_0.0965_0.0933_0.084_0.0949_0.0802_0.0738_0.075_0.0611_0.0552_0.0563_0.063_0.0734_0.061_0.0662_0.1003_0.0813_0.0632_0.0515_0.0706_0.07_0.0793_0.089_0.072_0.1208_0.0887_0.0955_0.0786_0.0735_0.1116_0.0786_0.0775_0.0606_0.0583_0.0861_0.091_0.0695_0.0159_0.0857_0.0907_0.0649_0.0822_0.0423_0.0769_0.0729_0.0971_0.0893_0.0933_0.1058_0.0804_0.0817_0.0845_0.0687_0.0836_0.0848_0.0788_0.0767_0.0857_0.0627_0.0765_0.0621_0.0518_0.0556_0.0756_0.0757_0.0836_0.0706_0.0679_0.0732_0.0885_0.0765_0.057_0.0637_0.0618_0.0596_0.0698_0.0734_0.066_0.049".split("_").length);
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            List<List<String>> resultList = new LinkedList<>(); //保存最后读取的所有数据，LinkedList 是为了保证顺序一致
            ir = new InputStreamReader(new FileInputStream(new File("C:\\Users\\liwenjie\\Downloads\\nidan_entropy.csv")), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                List<String> childrenList = new LinkedList<>();//将数组转换为列表存储，和excel读取结果一致，方便处理LinkedList 是为了保证顺序一致
                for (String s : item) {
                    childrenList.add(s);
                }
                resultList.add(childrenList);
            }
            resultList = resultList.stream().sorted((o1, o2) -> {
                try {
                    return DateUtils.parseDate(o1.get(1) + ":00", DateUtils.parsePatterns[5]).compareTo(DateUtils.parseDate(o2.get(1) + ":00", DateUtils.parsePatterns[5]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return -1;
            }).filter(o -> {
                try {
                    return DateUtils.parseDate(o.get(1) + ":00", DateUtils.parsePatterns[5]).compareTo(DateUtils.parseDate("2019/08/01 00:00:00", DateUtils.parsePatterns[5])) == -1;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }).collect(Collectors.toList());

            Map<Long, Map<Integer, Integer>> EntropyMapDay = new TreeMap<>();//熵值每天
            Map<Long, Map<Integer, Integer>> EntropyMapWeek = new TreeMap<>();//熵值每周
            Map<Long, Map<Integer, Integer>> EntropyMapNums = new TreeMap<>();//熵值每1500条
            Map<Integer, Integer> EntropyMapBatterSum = new TreeMap<>();//熵值每个单体出现总次数
            Integer EntropySum = 0; //熵值总次数

            Long pindex = 1L;
            int index = 0;
            Long startTime = 0L;
            Map<Integer, Integer> EintegerIntegerMap = new HashMap<>();
            Map<Integer, Integer> WintegerIntegerMap = new HashMap<>();
            for (List<String> strings : resultList) {
                index++;
                //日
                Integer num = Integer.parseInt(strings.get(5));
                Long longTime = getLongTime(strings.get(1)) / 1000000;
                if (EntropyMapDay.containsKey(longTime)) {
                    Map<Integer, Integer> integerIntegerMap = EntropyMapDay.get(longTime);
                    if (integerIntegerMap.containsKey(num)) {
                        integerIntegerMap.put(num, integerIntegerMap.get(num) + 1);
                    } else {
                        integerIntegerMap.put(num, 1);
                    }
                } else {
                    Map<Integer, Integer> integerIntegerMap = new HashMap<>();
                    integerIntegerMap.put(num, 1);
                    EntropyMapDay.put(longTime, integerIntegerMap);
                }
                //每500
                if (index < 500) {
                    if (EintegerIntegerMap.containsKey(num)) {
                        EintegerIntegerMap.put(num, EintegerIntegerMap.get(num) + 1);
                    } else {
                        EintegerIntegerMap.put(num, 1);
                    }
                } else {
                    index = 0;
                    EntropyMapNums.put(pindex, EintegerIntegerMap);
                    pindex++;
                    EintegerIntegerMap = new HashMap<>();
                }

                //周
                if (startTime == 0L)
                    startTime = longTime;
                long weekLastTime = getWeekLastTime(startTime);
                if (longTime <= weekLastTime) {
                    if (WintegerIntegerMap.containsKey(num)) {
                        WintegerIntegerMap.put(num, WintegerIntegerMap.get(num) + 1);
                    } else {
                        WintegerIntegerMap.put(num, 1);
                    }
                } else {
                    EntropyMapWeek.put(startTime, WintegerIntegerMap);
                    WintegerIntegerMap = new HashMap<>();
                    startTime = longTime;
                }

                if (EntropyMapBatterSum.containsKey(num)) {
                    EntropyMapBatterSum.put(num, EntropyMapBatterSum.get(num) + 1);
                } else {
                    EntropyMapBatterSum.put(num, 1);
                }

                EntropySum++;
            }
            EntropyMapWeek.put(startTime, WintegerIntegerMap);
            EntropyMapNums.put(pindex + 1, EintegerIntegerMap);

            FrequencyStatistics frequencyStatistics = new FrequencyStatistics();
            frequencyStatistics.setVIN("LVCB4L4D7KM001717");
            frequencyStatistics.setEntropyMapBatterSum(EntropyMapBatterSum);
            frequencyStatistics.setEntropyMapDay(EntropyMapDay);
            frequencyStatistics.setEntropyMapWeek(EntropyMapWeek);
            frequencyStatistics.setEntropyMapNums(EntropyMapNums);
            frequencyStatistics.setBatteryNum(252);
            frequencyStatistics.setEntropySum(EntropySum);

//            frequencyStatistics.outEntropy("D:\\车辆数据\\其他车辆数据\\20200418\\北京公交\\result\\zipout\\LVCB4L4D7KM001717\\month7/");
            frequencyStatistics.outIcon("D:\\车辆数据\\其他车辆数据\\20200418\\北京公交\\result\\zipout\\LVCB4L4D7KM001717\\month7/");



            System.out.println(EntropyMapDay);
            System.out.println(resultList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) reader.close();
                if (null != ir) ir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Long getLongTime(String string) {
        Date date = null;
        try {
            date = DateUtils.parseDate(string + ":00", DateUtils.parsePatterns[5]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, date));
    }

    /**
     * 获取一周后的时间
     *
     * @param startTime
     * @return
     */
    public static long getWeekLastTime(Long startTime) {
        if (startTime == null || startTime.toString().length() != 8) return startTime;
        int year = Integer.parseInt(startTime.toString().substring(0, 4));
        int month = Integer.parseInt(startTime.toString().substring(4, 6));
        int day = Integer.parseInt(startTime.toString().substring(6, 8));
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day + 7 > 30) {
                if (month < 9) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 30));
                }
                return Long.valueOf("" + year + (month + 1) + "0" + (day + 7 - 30));
            } else {
                if (month < 10) {
                    if (day + 7 < 10) {
                        return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                    }
                    return Long.valueOf("" + year + "0" + month + (day + 7));
                }
                if (day + 7 < 10) {
                    return Long.valueOf("" + year + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + month + (day + 7));
            }
        } else if (month == 2) {
            if ((year % 100 == 0 && year / 400 == 0) || year / 4 == 0) {
                if (day + 7 > 29) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 29));
                } else if (day + 7 < 10) {
                    return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + "0" + month + (day + 7));
            } else {
                if (day + 7 > 28) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 28));
                } else if (day + 7 < 10) {
                    return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + "0" + month + (day + 7));
            }
        } else {
            if (day + 7 > 31) {
                if (month + 1 > 12) {
                    return Long.valueOf("" + (year + 1) + "0" + 1 + "0" + (day + 7 - 31));
                } else if (month < 10) {
                    return Long.valueOf("" + year + "0" + (month + 1) + "0" + (day + 7 - 31));
                }
                return Long.valueOf("" + year + (month + 1) + "0" + (day + 7 - 30));
            } else {
                if (month < 10) {
                    if (day + 7 < 10) {
                        return Long.valueOf("" + year + "0" + month + "0" + (day + 7));
                    }
                    return Long.valueOf("" + year + "0" + month + (day + 7));
                }
                if (day + 7 < 10) {
                    return Long.valueOf("" + year + month + "0" + (day + 7));
                }
                return Long.valueOf("" + year + month + (day + 7));
            }
        }
    }
}
