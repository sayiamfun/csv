package com.sayiamfun.yinhao;

import com.sayiamfun.MonomerResistanceCompare.FirstStepData;
import com.sayiamfun.common.Constant;
import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.*;

public class YinhaoSpliteFile {

    public static void main(String[] args) throws IOException {
        String inputPath = "/Users/liwenjie/Downloads/vehData/苏朝磊3车数据/三车全生命周期原始报文/9539/";
        String outPath = "/Users/liwenjie/Downloads/vehData/苏朝磊3车数据/三车全生命周期原始报文/9539/";
        ArrayList<String> strings = ScanPackage.scanFiles(inputPath);
        int i = 0;
        for (String string : strings) {
            i++;
            System.out.println("第" + i + "个文件开始分析，vin:" + ScanPackage.getFileName(string));
            List<FirstStepData> firstStepDataList = getFirstStepDataList(string);
            Collections.sort(firstStepDataList, (o1, o2) -> {
                Long time = null == o1.getTime() ? 0L : o1.getTime();
                Long time1 = null == o2.getTime() ? 0L : o2.getTime();
                return time.compareTo(time1);
            });
            if (null == firstStepDataList || firstStepDataList.size() == 0) return;
            String vin = firstStepDataList.get(0).getVin();
            //CSV文件输出可追加
            OutputStreamWriter ow = null;
            BufferedWriter bw = null;

            //CSV文件输出可追加
            OutputStreamWriter ow2 = null;
            BufferedWriter bw2 = null;

            ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + vin + "_charge.csv"), true), ScanPackage.UTF_8);
            bw = new BufferedWriter(ow);
            bw.write("vin,time,speed,soc,vlist"); //中间，隔开不同的单元格，一次写一行
            bw.newLine();
            bw.flush();

            ow2 = new OutputStreamWriter(new FileOutputStream(new File(outPath + vin + "_run.csv"), true), ScanPackage.UTF_8);
            bw2 = new BufferedWriter(ow2);
            bw2.write("vin,time,speed,soc,vlist"); //中间，隔开不同的单元格，一次写一行
            bw2.newLine();
            bw2.flush();

            for (FirstStepData firstStepData : firstStepDataList) {


                String time = null == firstStepData.getTime() ? "" : firstStepData.getTime().toString();
                String chargeStatus = firstStepData.getChargeStatus();
                String speed = firstStepData.getSpeed();
                String soc = null == firstStepData.getSOC() ? "" : firstStepData.getSOC().toString();
                String vlist = String.join("_", firstStepData.getMonAndVList());

                if (Constant.CHARGEING.equals(chargeStatus)) {
                    bw.write(vin + "," + time + "," + speed + "," + soc + "," + vlist); //中间，隔开不同的单元格，一次写一行
                    bw.newLine();
                    bw.flush();
                } else {
                    bw2.write(vin + "," + time + "," + speed + "," + soc + "," + vlist); //中间，隔开不同的单元格，一次写一行
                    bw2.newLine();
                    bw2.flush();
                }
            }
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != bw2) bw.close();
                if (null != ow2) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static List<FirstStepData> getFirstStepDataList(String string) {
        List<FirstStepData> firstStepDataList = new ArrayList<>();
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(string)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line;
            FirstStepData firstStepData;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                firstStepData = new FirstStepData();
                for (String s : item) {
                    String[] split = s.split(":");
                    if (split.length != 2) continue;
                    if (Constant.VIN.equals(split[0])) {
                        firstStepData.setVin(split[1]);
                    } else if (Constant.TIME.equals(split[0])) {
                        firstStepData.setTime(Long.valueOf(split[1]));
                    } else if (Constant.SOC.equals(split[0])) {//SOC
                        firstStepData.setSOC(Double.valueOf(split[1]));
                    } else if (Constant.SPEED.equals(split[0])) {
                        firstStepData.setSpeed(split[1]);
                    } else if (Constant.CAR_STATUS.equals(split[0])) {
                        firstStepData.setCarStatus(split[1]);
                    } else if (Constant.CHARGE_STATUS.equals(split[0])) {
                        firstStepData.setChargeStatus(split[1]);
                    } else if (Constant.TOTAL_I.equals(split[0])) {
                        firstStepData.setTotalE(Double.parseDouble(split[1]));
                    } else if (Constant.MAX_V.equals(split[0])) {
                        firstStepData.setMaxV(Double.parseDouble(split[1]));
                    } else if (Constant.V_LIST.equals(split[0])) {
                        firstStepData = getVInfo(split[1], firstStepData);
                    }
                }
                if (!firstStepData.checkFirstData()) {
                    firstStepDataList.add(firstStepData);
                }
            }
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
        return firstStepDataList;
    }


    public static FirstStepData getVInfo(String sv, FirstStepData firstStepData) {
        byte[] singleVol = Base64.getDecoder().decode(sv);
        int barlen = singleVol.length;
        int len = barlen / 2;
        //要忽略的单体
        double[] arrs = new double[len + 3];
        double max = -99999;
        double min = 99999;
        double totalVolt = 0;
        int index = 0;
        int maxNum = 0;//最大值对应的编号
        int minNum = 0;//最小值对应的编号
        List<String> monAndVlist = new LinkedList<>();
        for (int i = 0; i < barlen - 1; i += 2) {
            //如果单体被忽略则直接跳过
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
            monAndVlist.add("" + (i / 2) + ":" + voltVal);
            arrs[index++] = voltVal;
            totalVolt += voltVal;
        }
        firstStepData.setMaxV(max);
        firstStepData.setMaxVNum(maxNum);
        firstStepData.setMinV(min);
        firstStepData.setMinVNum(minNum);
        firstStepData.setMonAndVList(monAndVlist);
        return firstStepData;

    }

    /**
     * 服务部提供的数据拆分
     *
     * @param
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/21 2:21 下午
     */
    private static void fuwubuDataSplite() throws IOException {
        String inputPath = "/Volumes/UsbDisk/尹豪3车数据/";
        String outPath = "/Volumes/UsbDisk/尹豪3车数据/out/";

        int vinIndex = 4;

        ArrayList<String> strings = ScanPackage.scanFiles(inputPath);
        Map<String, List<String>> vinFileMap = new HashMap<>();
        for (String string : strings) {
            if (!string.endsWith(".csv") || !string.contains("base64")) continue;
            String vin = string.split("/")[vinIndex];
            if (vin.equals("LB378Y4W7JA184214")) continue;
            if (vinFileMap.containsKey(vin)) {
                vinFileMap.get(vin).add(string);
            } else {
                List<String> fileList = new LinkedList<>();
                fileList.add(string);
                vinFileMap.put(vin, fileList);
            }
        }

        if (!new File(outPath).exists()) new File(outPath).mkdirs();
        for (Map.Entry<String, List<String>> stringListEntry : vinFileMap.entrySet()) {
            String vin = stringListEntry.getKey();
            List<String> value = stringListEntry.getValue();


            //CSV文件输出可追加
            OutputStreamWriter ow = null;
            BufferedWriter bw = null;

            //CSV文件输出可追加
            OutputStreamWriter ow2 = null;
            BufferedWriter bw2 = null;

            ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + vin + "_charge.csv"), true), ScanPackage.UTF_8);
            bw = new BufferedWriter(ow);
            bw.write("vin,time,speed,soc,vlist"); //中间，隔开不同的单元格，一次写一行
            bw.newLine();
            bw.flush();

            ow2 = new OutputStreamWriter(new FileOutputStream(new File(outPath + vin + "_run.csv"), true), ScanPackage.UTF_8);
            bw2 = new BufferedWriter(ow2);
            bw2.write("vin,time,speed,soc,vlist"); //中间，隔开不同的单元格，一次写一行
            bw2.newLine();
            bw2.flush();

            for (String s : value) {
                //csv文件读取方法
                InputStreamReader ir = null;
                BufferedReader reader = null;
                try {
                    List<List<String>> resultList = new LinkedList<>(); //保存最后读取的所有数据，LinkedList 是为了保证顺序一致
                    ir = new InputStreamReader(new FileInputStream(new File(s)), ScanPackage.encode);
                    reader = new BufferedReader(ir);//到读取的文件
                    String line = reader.readLine();

                    while ((line = reader.readLine()) != null) {
                        String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                        String time = item[1];
                        String chargeStatus = item[4];
                        String speed = item[6];
                        String soc = item[10];
                        String vlist = item[70];

                        if ("停车充电".equals(chargeStatus) || "鏈\uE044厖鐢电姸鎬�".equals(chargeStatus)) {
                            bw.write(vin + "," + time + "," + speed + "," + soc + "," + vlist); //中间，隔开不同的单元格，一次写一行
                            bw.newLine();
                            bw.flush();
                        } else {
                            bw2.write(vin + "," + time + "," + speed + "," + soc + "," + vlist); //中间，隔开不同的单元格，一次写一行
                            bw2.newLine();
                            bw2.flush();
                        }
                    }
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
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != bw2) bw.close();
                if (null != ow2) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
