package com.sayiamfun.MonomerResistanceCompare;

import com.sayiamfun.common.utils.ScanPackage;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/***
 * 计算最大电阻的单体编号
 *  1. 选择车辆放电状态数据，选择XX帧数据
 *  2. 进行压变计算，即单体电压后一帧减去前一帧（两针数据间隔≤10s）,形成压变数据库K
 *  3. 基于数据库H，筛选出压变为+和-的数据库，分别为看K+和K-
 *  4. 分别从数据库K+和K-中筛选出电流IMAX绝对值最大对应的行矩阵，即H+和H-
 *  5. 分别对H+和H-中电芯压变的绝对值进行排序，形成基于电芯压变大小进行排序，并输出相应的电芯编号B∈[B1,B2,B3……B8]；C∈[C1,C2,C3……C8]
 *  6. B和C进行匹配，编号相同的进行编号记录，不相同的记为0
 *  *
 *  * 只在放电的时候获取单体电阻排序
 * 苏朝磊 提供的计算方法
 */
public class MonomerResistanceCompareAllInOne {

    /**
     * 压差最大有效值
     */
    public static double maxSub = 0.15;
    /**
     * 时间差最大有效值
     */
    public static int maxSubT = 10;
    /**
     * 一段帧数
     */
    public static int oneSum = 2000;
    /**
     * 读取文件总条数 最后多少条
     */
    public static int totalSum = 40000;
    /**
     * 不参与计算的单体
     */
    public static List<String> lostMon = new ArrayList<>();

    public static double defaultDouble = -9000.0;

    public static int defaultInt = -9000;


    /**
     * 初始化不参与计算的单体列表
     */
    static {
        lostMon = Arrays.asList("6,17,29,41,53,65,76,86,91".split(","));
//        lostMon = Arrays.asList("".split(","));
    }

    /**
     * 最后结果只写一次表头
     */
    public static boolean first = true;


    private static String maxSubV = "maxSubV";  //最大 △V   本帧最大减去本帧最小
    private static String maxSubVSOC = "maxSubVSOC";//最大 △V SOC
    private static String maxSubVI = "maxSubVI";//最大 △V  I
    private static String minSubV = "minSubV";//最小 △V
    private static String minSubVSOC = "minSubVSoc";//最小 △V SOC
    private static String minSubVI = "minSubI";//最小 △V I
    private static String monList = "monList";//单体编号列表（阻值较大）
    private static String avargeSubV;// △V 均值

    private static int vinIndex = 4;


    public static void main(String[] args) throws IOException {

        String inputPath = "/Volumes/UsbDisk/尹豪3车数据/";
        String inputPath1 = "/Volumes/UsbDisk/data_analysis2/";
        String outPath = "/Users/liwenjie/Downloads/vehData/vehOut/result/";


        Set<String> strings = ScanPackage.scanDirectory(inputPath);
        Map<String, List<String>> map = new HashMap<>();
        for (String string : strings) {
            if (string.contains("/base64")) {
                String[] split = string.split("/");
                String vin = split[vinIndex];
                if (map.containsKey(vin)) {
                    map.get(vin).add(string);
                } else {
                    List<String> tmpList = new ArrayList<>();
                    tmpList.add(string);
                    map.put(vin, tmpList);
                }
            }
        }
        int i = 0;
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            i++;
            System.out.println("第" + i + "辆车开始分析，vin:" + stringListEntry.getKey());
            outResultDataByInputPath(stringListEntry.getValue(), outPath);
        }


    }


    /**
     * 根据输入路径输出结果
     *
     * @param strings
     * @param outPath
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/9 5:30 下午
     */
    private static void outResultDataByInputPath(List<String> strings, String outPath) {
        Map<Long, List<String>> vData = new TreeMap<>();
        for (String s : strings) {
            Set<String> strings1 = ScanPackage.scanThisDirectoryFile(s);
            for (String s1 : strings1) {
                Map<Long, List<String>> items1 = ScanPackage.getMapItems2(s1, strings1.size(), totalSum);
                vData.putAll(items1);
            }
        }
        if (vData.size() == 0) return;
        int count = 0;
        if (vData.size() % oneSum == 0) {
            count = vData.size() / oneSum;
        } else {
            count = (vData.size() / oneSum) + 1;
        }
        ArrayList<Map.Entry<Long, List<String>>> entries = new ArrayList<>(vData.entrySet());
        Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
        for (int i = 0; i < count; i++) {

            int start = i * oneSum;
            int end = (i + 1) * oneSum;
            if (vData.size() < end) {
                List<Map.Entry<Long, List<String>>> entries1 = entries.subList(start, vData.size());
                Map<Long, List<String>> tmpMap = new LinkedHashMap<>();
                entries1.stream().forEach(longListEntry -> tmpMap.put(longListEntry.getKey(), longListEntry.getValue()));
                outResultData(outPath, tmpMap);
            } else {
                List<Map.Entry<Long, List<String>>> entries1 = entries.subList(start, end);
                Map<Long, List<String>> tmpMap = new LinkedHashMap<>();
                entries1.stream().forEach(longListEntry -> tmpMap.put(longListEntry.getKey(), longListEntry.getValue()));
                outResultData(outPath, tmpMap);
            }
        }

    }

    private static void outResultData(String outPath, Map<Long, List<String>> tmpMap) {
        ResultData resultData = runCompare(tmpMap);
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            outPath = mkdirDir(outPath);
//            ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + resultData.getVin() + "-" + resultData.getStartTime() + "-" + resultData.getEndTime() + ".csv"), true), ScanPackage.encode);
            ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + resultData.getVin() + ".csv"), true), ScanPackage.encode);
            bw = new BufferedWriter(ow);
            if (first) {
                bw.write("vin,开始时间,结束时间," +
                        "充电最大压差,充电最大压差SOC,充电最大压差电流," +
                        "充电最小压差,充电最小压差SOC,充电最小压差电流,充电△V平均值," +
                        "放电最大压差,放电最大压差SOC,放电最大压差电流," +
                        "放电最小压差,放电最小压差SOC,放电最小压差电流,放电△V平均值," +
                        "内阻较大单体编号(前10)"); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
                bw.flush();
                first = false;
            }
            bw.write(resultData.toString());
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(resultData);
    }

    private static ResultData runCompare(Map<Long, List<String>> vehData) {
        ResultData resultData = new ResultData();
        String vin = "";
        Long startTime = 0L;//数据开始时间
        Long endTime = 0L;//数据结束时间

        List<FirstStepData> charge = new ArrayList<>();
        List<FirstStepData> run = new ArrayList<>();
        FirstStepData firstStepData = null;
        List<String> monAndVList = null;
        for (Map.Entry<Long, List<String>> longListEntry : vehData.entrySet()) {
            List<String> value = longListEntry.getValue();
            vin = value.get(0);
            Long time = longListEntry.getKey();
            if (startTime == 0 || time < startTime) startTime = time;
            if (endTime == 0 || time > endTime) endTime = time;
            double SOC = Double.parseDouble(getStringValue(value.get(10), "0.0"));
            String carStatus = value.get(3);
            String chargeStatus = value.get(4);
            double totalE = Double.parseDouble(getStringValue(value.get(9), "0.0"));
            List<String> strings3 = Arrays.asList(value.get(70).split("_"));
            int maxMon = 0;
            double maxV = defaultDouble;
            int minMon = 0;
            double minV = defaultDouble;
            monAndVList = new LinkedList<>();
            for (int i = 0; i < strings3.size(); i++) {
                double v = Double.parseDouble(strings3.get(i));
                monAndVList.add((i + 1) + ":" + v);
                if (v > maxV || maxV == defaultDouble) {
                    maxV = v;
                    maxMon = i + 1;
                } else if (v < minV || minV == defaultDouble) {
                    minV = v;
                    minMon = i + 1;
                }

            }
            double v = maxV - minV;
            firstStepData = new FirstStepData(vin, time, SOC, carStatus, chargeStatus, totalE, maxV, maxMon, minV, minMon, v, monAndVList);
            if ("停车充电".equals(chargeStatus)) {
                charge.add(firstStepData);
            } else {
                run.add(firstStepData);
            }
        }
        resultData.setVin(vin);
        resultData.setStartTime(startTime);
        resultData.setEndTime(endTime);

        Collections.sort(run, (o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        Map<String, String> extremeVale = getExtremeVale(run);
        if (null != extremeVale) {
            resultData.setRunMaxSubV(extremeVale.get(maxSubV));
            resultData.setRunMaxSubVSOC(extremeVale.get(maxSubVSOC));
            resultData.setRunMaxSubVI(extremeVale.get(maxSubVI));
            resultData.setRunMinSubV(extremeVale.get(minSubV));
            resultData.setRunMinSubVSOC(extremeVale.get(minSubVSOC));
            resultData.setRunMinSubVI(extremeVale.get(minSubVI));
            resultData.setMonList(extremeVale.get(monList));

            resultData.setRunAvargeSubV(extremeVale.get(avargeSubV));
        }

        extremeVale = getExtremeVale(charge);
        if (null != extremeVale) {
            resultData.setChargeMaxSubV(extremeVale.get(maxSubV));
            resultData.setChargeMaxSubVSOC(extremeVale.get(maxSubVSOC));
            resultData.setChargeMaxSubVI(extremeVale.get(maxSubVI));
            resultData.setChargeMinSubV(extremeVale.get(minSubV));
            resultData.setChargeMinSubVSOC(extremeVale.get(minSubVSOC));
            resultData.setChargeMinSubVI(extremeVale.get(minSubVI));

            resultData.setChargeAvargeSubV(extremeVale.get(avargeSubV));
        }

        return resultData;
    }

    private static String getStringValue(String s, String s1) {
        return StringUtils.isEmpty(s) ? s1 : s;
    }

    /**
     * 输出最后统计结果到CSV
     *
     * @param outPath
     * @param vData
     * @param vehData
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/10 8:52 上午
     */
    private static void outResultData(String outPath, Map<Long, List<String>> vData, Map<Long, List<String>> vehData) {
        ResultData resultData = runCompare(vData, vehData);
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            outPath = mkdirDir(outPath);
            ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + resultData.getVin() + "-" + resultData.getStartTime() + "-" + resultData.getEndTime() + ".csv"), true), ScanPackage.encode);
            bw = new BufferedWriter(ow);
            if (first) {
                bw.write("vin,开始时间,结束时间," +
                        "充电最大压差,充电最大压差SOC,充电最大压差电流," +
                        "充电最小压差,充电最小压差SOC,充电最小压差电流,充电△V平均值," +
                        "放电最大压差,放电最大压差SOC,放电最大压差电流," +
                        "放电最小压差,放电最小压差SOC,放电最小压差电流,放电△V平均值," +
                        "内阻较大单体编号(前10)"); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
                bw.flush();
                first = false;
            }
            bw.write(resultData.toString());
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(resultData);
    }

    /**
     * 判断是否有输出文件夹，没有则新建
     *
     * @param path
     * @return
     */
    private static String mkdirDir(String path) {
        String outPath = StringUtils.substring(path, 0, path.lastIndexOf("/"));
        if (!outPath.endsWith("/")) outPath += "/";
        File file = new File(outPath);
        if (!file.exists()) file.mkdirs();
        return path;
    }

    /***
     *
     * 只获取未充电
     * @param vData
     * @param vehData
     */
    private static ResultData runCompare(Map<Long, List<String>> vData, Map<Long, List<String>> vehData) {
        ResultData resultData = new ResultData();
        String vin = "";
        Long startTime = 0L;//数据开始时间
        Long endTime = 0L;//数据结束时间

        List<FirstStepData> charge = new ArrayList<>();
        List<FirstStepData> run = new ArrayList<>();
        FirstStepData firstStepData = null;
        List<String> monAndVList = null;
        for (Map.Entry<Long, List<String>> longListEntry : vehData.entrySet()) {
            List<String> value = longListEntry.getValue();
            vin = value.get(0);
            Long time = longListEntry.getKey();
            if (startTime == 0 || time < startTime) startTime = time;
            if (endTime == 0 || time > endTime) endTime = time;
            double SOC = Double.parseDouble(value.get(10));
            String carStatus = value.get(3);
            String chargeStatus = value.get(4);
            double totalE = Double.parseDouble(value.get(9));
            List<String> strings2 = vData.get(time);
            if (null == strings2) continue;
            List<String> strings3 = strings2.subList(2, 96);
            int maxMon = 0;
            double maxV = defaultDouble;
            int minMon = 0;
            double minV = defaultDouble;
            monAndVList = new LinkedList<>();
            for (int i = 0; i < strings3.size(); i++) {
                double v = Double.parseDouble(strings3.get(i));
                monAndVList.add((i + 1) + ":" + v);
                if (v > maxV || maxV == defaultDouble) {
                    maxV = v;
                    maxMon = i + 1;
                } else if (v < minV || minV == defaultDouble) {
                    minV = v;
                    minMon = i + 1;
                }

            }
            double v = maxV - minV;
            firstStepData = new FirstStepData(vin, time, SOC, carStatus, chargeStatus, totalE, maxV, maxMon, minV, minMon, v, monAndVList);
            if ("停车充电".equals(chargeStatus)) {
                charge.add(firstStepData);
            } else {
                run.add(firstStepData);
            }
        }
        resultData.setVin(vin);
        resultData.setStartTime(startTime);
        resultData.setEndTime(endTime);

        Collections.sort(run, (o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        Map<String, String> extremeVale = getExtremeVale(run);
        if (null != extremeVale) {
            resultData.setRunMaxSubV(extremeVale.get(maxSubV));
            resultData.setRunMaxSubVSOC(extremeVale.get(maxSubVSOC));
            resultData.setRunMaxSubVI(extremeVale.get(maxSubVI));
            resultData.setRunMinSubV(extremeVale.get(minSubV));
            resultData.setRunMinSubVSOC(extremeVale.get(minSubVSOC));
            resultData.setRunMinSubVI(extremeVale.get(minSubVI));
            resultData.setMonList(extremeVale.get(monList));

            resultData.setRunAvargeSubV(extremeVale.get(avargeSubV));
        }

        extremeVale = getExtremeVale(charge);
        if (null != extremeVale) {
            resultData.setChargeMaxSubV(extremeVale.get(maxSubV));
            resultData.setChargeMaxSubVSOC(extremeVale.get(maxSubVSOC));
            resultData.setChargeMaxSubVI(extremeVale.get(maxSubVI));
            resultData.setChargeMinSubV(extremeVale.get(minSubV));
            resultData.setChargeMinSubVSOC(extremeVale.get(minSubVSOC));
            resultData.setChargeMinSubVI(extremeVale.get(minSubVI));

            resultData.setChargeAvargeSubV(extremeVale.get(avargeSubV));
        }

        return resultData;

    }

    /**
     * 获取最大最小  电压
     * 电流
     * SOC
     *
     * @param run
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/9 4:41 下午
     */
    private static Map<String, String> getExtremeVale(List<FirstStepData> run) {
        List<String> maxVList = null; //压差最大列表
        List<String> minVList = null; //压差最小列表
        double max = defaultDouble; //最大
        double min = defaultDouble; //最小
        double monMax = defaultDouble;
        double monMin = defaultDouble;
        double sumSubV = 0.0;
        int sumSubVCount = 0;
        List<String> lastMonAndVList = null;   //上一帧电压数据
        long lastTime = 0L; //上一帧时间
        Map<String, String> maxAndMin = new HashMap<>();  //返回结果Map
        for (FirstStepData stepData : run) {
            List<String> monAndVList1 = stepData.getMonAndVList();
            if (lastMonAndVList == null) {
                lastTime = stepData.getTime();
                lastMonAndVList = monAndVList1;
                continue;
            }
            if (stepData.getTime() - lastTime > maxSubT) {
                lastTime = stepData.getTime();
                lastMonAndVList = monAndVList1;
                continue;
            }
            double tmpMax = defaultDouble;
            double tmpMin = defaultDouble;
            double monMaxV = defaultDouble;
            double monMinV = defaultDouble;
            List<String> tmpMonAndVList = new LinkedList<>();
            for (int i = 0; i < monAndVList1.size(); i++) {
                double monV = Double.parseDouble(monAndVList1.get(i).split(":")[1]);
                if (monV > monMaxV || monMaxV == defaultDouble) monMaxV = monV;
                if (monV < monMinV || monMinV == defaultDouble) monMinV = monV;
                double v = Double.parseDouble(monAndVList1.get(i).split(":")[1]) - Double.parseDouble(lastMonAndVList.get(i).split(":")[1]);
                if ((v > tmpMax || tmpMax == defaultDouble) && !lostMon.contains("" + (i + 1))) tmpMax = v;
                if ((v < tmpMin || tmpMin == defaultDouble) && !lostMon.contains("" + (i + 1))) tmpMin = v;
                if (!lostMon.contains("" + (i + 1)))
                    tmpMonAndVList.add("" + (i + 1) + ":" + v);
            }
            if ((tmpMax > max || max == defaultDouble) && Math.abs(tmpMax) <= maxSub) {
                max = tmpMax;
                maxVList = tmpMonAndVList;
            }
            if ((tmpMin < min || min == defaultDouble) && Math.abs(tmpMin) <= maxSub) {
                min = tmpMin;
                minVList = tmpMonAndVList;
            }
            double monv = monMaxV - monMinV;
            sumSubV += monv;
            sumSubVCount++;
            if (monv > monMax || monMax == defaultDouble) {
                monMax = monv;
                maxAndMin.put(maxSubV, "" + monv);
                maxAndMin.put(maxSubVSOC, "" + stepData.getSOC());
                maxAndMin.put(maxSubVI, "" + stepData.getTotalE());
            }
            if (monv < monMin || monMin == defaultDouble) {
                monMin = monv;
                maxAndMin.put(minSubV, "" + monv);
                maxAndMin.put(minSubVSOC, "" + stepData.getSOC());
                maxAndMin.put(minSubVI, "" + stepData.getTotalE());
            }
            lastTime = stepData.getTime();
            lastMonAndVList = stepData.getMonAndVList();
        }
        maxAndMin.put(avargeSubV, "" + (sumSubV / sumSubVCount));
        if (null == maxVList || null == minVList) return null;
        Collections.sort(maxVList, (o1, o2) -> new BigDecimal(o2.split(":")[1]).abs().compareTo(new BigDecimal(o1.split(":")[1]).abs()));
        Collections.sort(minVList, (o1, o2) -> new BigDecimal(o2.split(":")[1]).abs().compareTo(new BigDecimal(o1.split(":")[1]).abs()));
        List<String> childList = new LinkedList<>();

        for (int j = 0; j < 10; j++) {
            String s1 = maxVList.get(j).split(":")[0];

            boolean flag = false;
            for (int k = 0; k < 8; k++) {
                String s3 = minVList.get(k).split(":")[0];
                if (s1.equals(s3)) {
                    flag = true;
                    childList.add(s1 + ":" + new BigDecimal(maxVList.get(j).split(":")[1]).add(new BigDecimal(minVList.get(k).split(":")[1])).doubleValue());
                    break;
                }
            }
            if (!flag) {
                childList.add("0:0");
            }
        }

//        System.out.println("maxlastV:" + maxAndMin.get("maxlastV"));
//        System.out.println("maxthisV:" + maxAndMin.get("maxthisV"));
//        System.out.println(maxAndMin.get("max"));
//        System.out.println("minlastV:" + maxAndMin.get("minlastV"));
//        System.out.println("minthisV:" + maxAndMin.get("minthisV"));
//        System.out.println(maxAndMin.get("min"));
//        System.out.println(String.join("_", maxVList));
//        System.out.println(String.join("_", minVList));
        Collections.sort(childList, (o1, o2) -> new BigDecimal(o2.split(":")[1]).abs().compareTo(new BigDecimal(o1.split(":")[1]).abs()));
//        System.out.println(String.join("_", childList));
        StringBuffer stringBuffer = new StringBuffer(100);
        for (String s : childList) {
            stringBuffer.append(s.split(":")[0]).append(",");
        }
        maxAndMin.put(monList, stringBuffer.toString());
        return maxAndMin;
    }

    /**
     * 全部数据充放电作区分
     * 根据总电流排序
     * 放电  大 - 》  小
     * 充电  小 - 》  大
     * <p>
     * 分别取充放电第一帧
     * 放电单体电压列表排序 - 》 大 -》 小
     * 充电单体电压列表排序 - 》 小 -》 大
     * <p>
     * 取前10单体
     * 放电单体与充电五个单体作比较  前后两个  如果相同记下单体编号，否则为0
     *
     * @param vData
     * @param vehData
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/9 9:59 上午
     */
    private static void chargeAndRunCompare(Map<Long, List<String>> vData, Map<Long, List<String>> vehData) {
        List<FirstStepData> charge = new ArrayList<>();
        List<FirstStepData> run = new ArrayList<>();
        FirstStepData firstStepData = null;
        List<String> monAndVList = null;
        for (Map.Entry<Long, List<String>> longListEntry : vehData.entrySet()) {
            List<String> value = longListEntry.getValue();
            String vin = value.get(0);
            Long time = longListEntry.getKey();
            double SOC = Double.parseDouble(value.get(10));
            String carStatus = value.get(3);
            String chargeStatus = value.get(4);
            double totalE = Double.parseDouble(value.get(9));
            List<String> strings2 = vData.get(time);
            if (null == strings2) continue;
            List<String> strings3 = strings2.subList(2, 96);
            int maxMon = 0;
            double maxV = 0;
            int minMon = 0;
            double minV = 0;
            monAndVList = new ArrayList<>();
            for (int i = 0; i < strings3.size(); i++) {
                double v = Double.parseDouble(strings3.get(i));
                monAndVList.add((i + 1) + ":" + v);
                if (v > maxV) {
                    maxV = v;
                    maxMon = i + 1;
                } else if (v < minV) {
                    minV = v;
                    minMon = i + 1;
                }

            }
            double v = maxV - minV;
            firstStepData = new FirstStepData(vin, time, SOC, carStatus, chargeStatus, totalE, maxV, maxMon, minV, minMon, v, monAndVList);
            if ("停车充电".equals(chargeStatus)) {
                charge.add(firstStepData);
            } else {
                run.add(firstStepData);
            }
        }
        Collections.sort(charge, (o1, o2) -> o2.getTotalE().compareTo(o1.getTotalE()));
        Collections.sort(run, (o1, o2) -> o2.getTotalE().compareTo(o1.getTotalE()));
        List<String> monAndVList1 = null;
        List<String> monAndVList2 = null;
        List<List<String>> resultlist = new LinkedList<>();
        List<String> childList = null;
        for (int i = 0; i < 5; i++) {
            monAndVList1 = charge.get(i).getMonAndVList();
            monAndVList2 = run.get(i).getMonAndVList();
            Collections.sort(monAndVList1, (o1, o2) -> new BigDecimal(o2.split(":")[1]).compareTo(new BigDecimal(o1.split(":")[1])));
            Collections.sort(monAndVList2, (o1, o2) -> new BigDecimal(o2.split(":")[1]).compareTo(new BigDecimal(o1.split(":")[1])));
            childList = new LinkedList<>();
            for (int j = 0; j < 10; j++) {
                if (j < 2) {
                    String s1 = monAndVList1.get(j).split(":")[0];
                    boolean flag = false;
                    for (int k = 0; k < j + 2; k++) {
                        String s3 = monAndVList2.get(k).split(":")[0];
                        if (s1.equals(s3)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        childList.add(s1);
                    } else {
                        childList.add("0");
                    }
                } else if (j == 9) {
                    String s1 = monAndVList1.get(j).split(":")[0];

                    boolean flag = false;
                    for (int k = j - 2; k < 10; k++) {
                        String s3 = monAndVList2.get(k).split(":")[0];
                        if (s1.equals(s3)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        childList.add(s1);
                    } else {
                        childList.add("0");
                    }
                } else {
                    String s1 = monAndVList1.get(j).split(":")[0];

                    boolean flag = false;
                    for (int k = j - 2; k <= j + 2; k++) {
                        String s3 = monAndVList2.get(k).split(":")[0];
                        if (s1.equals(s3)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        childList.add(s1);
                    } else {
                        childList.add("0");
                    }
                }
            }
            resultlist.add(childList);
        }
        for (List<String> stringList : resultlist) {
            System.out.println(String.join("_", stringList));
        }
    }
}


