package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.Constant;
import com.sayiamfun.common.utils.Common;
import com.sayiamfun.common.utils.ScanPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 测试 统计图表
 */
public class TestFrequencyStatistics {

    private static final Logger logger = LoggerFactory.getLogger(TestFrequencyStatistics.class);


    static String inpath = "/Users/liwenjie/Downloads/vehData/qiyepingtai/";
    static String outPath = "/Users/liwenjie/Downloads/vehData/qiyepingtaiout/";
    static String zipPath = "/Users/liwenjie/Downloads/vehData/qiyepingtai.zip";


    static List<String> vinList = Arrays.asList("LJ8E3A5M6HC002647",
            "LK6ADCE26KB031743",
            "LNBSCB3F5HW163718",
            "LNBSCB3F5GW020556",
            "LNBSCB3F9HW152348",
            "LNBSCB3F2HW151199",
            "LNBSCB3F9HW166203",
            "LNBSCB3F8HW155547",
            "LK6ADCE28HB007601",
            "LK6ADCE20KB030362",
            "LK6ADCE29KB031705",
            "LK6ADCE2XKB025363",
            "LK6ADCE29KB024673",
            "LK6ADCE26KB015820",
            "LS4ASE2C2JF019359",
            "LS4ASE2CXJF024745",
            "LS4ASE2C7JF019468");


    public static void main(String[] args) throws Exception {

        File file = new File(outPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (String s : vinList) {
            FrequencyStatistics frequencyStatistics = new FrequencyStatistics();
            ArrayList<String> strings = ScanPackage.scanFiles(inpath);
            for (String string : strings) {
                if (string.contains("熵值") || string.contains("model_fault_result_details")) {
                    addOutFaultResult(s, string, frequencyStatistics);
                } else if (string.contains("波动") || string.contains("model_fluctuan_result_details")) {
                    addFluctuanResult(s, string, frequencyStatistics);
                } else if (string.contains("压降") || string.contains("model_step_consistency_result_details")) {
                    addStepConsistencyResult(s, string, frequencyStatistics);
                }
            }
            Map<String, String> stringStringMap = ConfigerIgnoreTheMonomer.getMonomerNum().get(s);
            frequencyStatistics.setVIN(s);
            frequencyStatistics.setIgnoreTheMonomerMap(stringStringMap);
            frequencyStatistics.setBatteryNum(0);
            frequencyStatistics.setZMaxNums(Constant.zMaxNums);
            frequencyStatistics.setPressureNums(Constant.PRESSURENUMS);
            frequencyStatistics.setEntropyNums(Constant.ENTROPYNUMS);
            frequencyStatistics.setVolatilityNums(Constant.VOLATILITYNUMS);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();

            FrequencyStatistics frequencyStatistics1 = new FrequencyStatistics(frequencyStatistics);
            logger.info("run ");
            frequencyStatistics.setType("2");
            doFrequencyStatistics(formatter, date, frequencyStatistics);

            logger.info("charge");
            frequencyStatistics1.setType("1");
            doFrequencyStatistics(formatter, date, frequencyStatistics1);


        }
        String[] srcDir = {outPath};
        String outDir = zipPath;
        Common.toZip(srcDir, outDir, true);

    }

    /**
     * 执行结果统计类统计方法
     *
     * @param formatter           格式化工具
     * @param date                日期
     * @param frequencyStatistics 统计类对象
     */
    private static void doFrequencyStatistics(SimpleDateFormat formatter, Date date, FrequencyStatistics frequencyStatistics) {
        logger.info("doPressureDropConsistencyDayAndNums");
        frequencyStatistics.doPressureDropConsistencyDayAndNums();
        logger.info("doPressureDropConsistencyWeek");
        frequencyStatistics.doPressureDropConsistencyWeek();
        logger.info("doVolatilityDetectionDayAndNums");
        frequencyStatistics.doVolatilityDetectionDayAndNums();
        logger.info("doVolatilityDetectionWeek");
        frequencyStatistics.doVolatilityDetectionWeek();
        logger.info("doEntropyDayAndNums");
        frequencyStatistics.doEntropyDayAndNums();
        logger.info("doEntropyWeek");
        frequencyStatistics.doEntropyWeek();
        frequencyStatistics.setNowTime(formatter.format(date));   //设置统一输出时间

        outFrequencyStatistics(frequencyStatistics, outPath);
    }

    /**
     * 执行统计类输出方法
     *
     * @param frequencyStatistics 统计类对象
     * @param outputPath          输出路径
     */
    public static void outFrequencyStatistics(FrequencyStatistics frequencyStatistics, String outputPath) {
        logger.info("outPressureDropConsistency");
        frequencyStatistics.outPressureDropConsistency(outputPath);
        logger.info("outVolatilityDetection");
        frequencyStatistics.outVolatilityDetection(outputPath);
        logger.info("outEntropy");
        frequencyStatistics.outEntropy(outputPath);
        frequencyStatistics.outIcon(outputPath);
    }


    /**
     * 添加压降
     *
     * @param s                   vin
     * @param string
     * @param frequencyStatistics 对象
     */
    private static void addStepConsistencyResult(String s, String string, FrequencyStatistics frequencyStatistics) {
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(string)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            StepConsistencyResult stepConsistencyResult;
            while ((line = reader.readLine()) != null) {
                //vid,vin,itemname,lasttime,nowtime,lastsoc,nowsoc,lastvolts,nowvolts,unitnum,
                // maxstep,minstep,maxstepsinglenum,minstepsinglenum,diploid,modeltype,year,month,day
                //97qi4,LK6ADCE20KB030362,最大压差绝对值与最小压差绝对值比K大于2,20200819072830,20200819072832,85,85,3.997_3.991_3.993_3.993_3.995_3.991_3.994_3.998_3.998_3.992_3.997_3.994_3.993_3.995_3.993_3.999_3.995_3.986_3.992_3.992_3.993_3.993_3.994_3.995_3.999_3.994_3.992_3.994_3.989_3.991_3.992_3.994,3.998_3.992_3.995_3.994_3.997_3.993_3.995_3.999_3.999_3.993_3.998_3.995_3.994_3.996_3.995_4.001_3.996_3.987_3.994_3.994_3.994_3.994_3.996_3.996_4.0_3.995_3.993_3.995_3.991_3.994_3.994_3.996,1,
                // 0.003,0.001,30,2,3,2,2020,08,19
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!s.equals(item[1])) continue;
                stepConsistencyResult = new StepConsistencyResult();
                stepConsistencyResult.setVid(item[0]);
                stepConsistencyResult.setVin(item[1]);
                stepConsistencyResult.setName(item[2]);
                stepConsistencyResult.setLastTime(item[3]);
                stepConsistencyResult.setNowTime(item[4]);
                stepConsistencyResult.setLastSoc(item[5]);
                stepConsistencyResult.setNowSoc(item[6]);
                stepConsistencyResult.setLastVolts(item[7]);
                stepConsistencyResult.setNowVolts(item[8]);
                stepConsistencyResult.setUnitNum(Integer.parseInt(item[9]));
                stepConsistencyResult.setMaxStep(Double.parseDouble(item[10]));
                stepConsistencyResult.setMinStep(Double.parseDouble(item[11]));
                stepConsistencyResult.setMaxStepSingleNum(Integer.parseInt(item[12]));
                stepConsistencyResult.setMinStepSingleNum(Integer.parseInt(item[13]));
                stepConsistencyResult.setDiploid(Double.parseDouble(item[14]));
                stepConsistencyResult.setType(Integer.parseInt(item[15]));
                frequencyStatistics.stepConsistencyResults.add(stepConsistencyResult);
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

    /**
     * 添加波动
     *
     * @param s                   vin
     * @param string
     * @param frequencyStatistics 对象
     */
    private static void addFluctuanResult(String s, String string, FrequencyStatistics frequencyStatistics) {
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(string)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            FluctuanResult fluctuanResult;
            while ((line = reader.readLine()) != null) {
                //vid,vin,starttime,endtime,startsoc,endsoc,abnormalsingles,modeltype,unitnum,
                // abnormalmultis,varianceavg,standdev,maxflucnum,maxflucstandmulti,flucmultilist,year,month,day
                //01926,LK6ADCE2XKB025363,20200816160718,20200816160752,52,52,18,2,1,
                // 5.2802,1.1e-05,4.9e-05,18,5.2802,0.2191_0.2077_0.1454_0.2118_0.2132_0.2099_0.2132_0.2009_0.206_0.2017_0.2066_0.214_0.2142_0.174_0.1413_0.1341_0.2066_5.2802_0.2019_0.2107_0.214_0.1995_0.1903_0.2107_0.2101_0.178_0.2118_0.2109_0.205,2020,08,16
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!s.equals(item[1])) continue;
                fluctuanResult = new FluctuanResult();
                fluctuanResult.setVid(item[0]);
                fluctuanResult.setVin(item[1]);
                fluctuanResult.setStartTime(item[2]);
                fluctuanResult.setEndTime(item[3]);
                fluctuanResult.setStartSoc(item[4]);
                fluctuanResult.setEndSoc(item[5]);
                fluctuanResult.setAbnormalSingles(item[6]);
                fluctuanResult.setType(Integer.parseInt(item[7]));
                fluctuanResult.setUnitNum(Integer.parseInt(item[8]));
                fluctuanResult.setQuaAvg(Double.parseDouble(item[10]));
                fluctuanResult.setQuaStand(Double.parseDouble(item[11]));
                fluctuanResult.setMaxMon(Integer.parseInt(item[12]));
                fluctuanResult.setMaxNum(Double.parseDouble(item[13]));
                fluctuanResult.setQuaListString(item[14]);
                frequencyStatistics.fluctuanResults.add(fluctuanResult);
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

    /**
     * 添加熵值
     *
     * @param s                   vin
     * @param string
     * @param frequencyStatistics 对象
     */
    private static void addOutFaultResult(String s, String string, FrequencyStatistics frequencyStatistics) {
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(string)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            OutFaultResult outFaultResult;
            while ((line = reader.readLine()) != null) {
                //vid,vin,octime,soc,stime,ssoc,unitnum,vartype,maxnum,maxvalue,modeltype,stringvars,quessingles,year,month,day
                //bawol,LK6ADCE26KB031743,20200812091049,99,20200812090843,100,1,1,7,2.3018,2,0.045_-0.2059_0.8812_-1.525_0.2716_0.9437_2.3018_-0.2059_-0.4957_1.0958_0.0707_0.1234_-2.0133_-0.8424_-1.4508_-0.6835_-1.0868_1.3535_0.597_0.1195_1.2227_0.4924_-0.3475_1.5694_-1.3199_-1.5357_-0.6092_0.1407_0.6183_1.0103_-0.2232_-0.3122,7_13_24_26,2020,08,12
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!s.equals(item[1])) continue;
                outFaultResult = new OutFaultResult();
                outFaultResult.vid = item[0];
                outFaultResult.vin = item[1];
                outFaultResult.time = item[2];
                outFaultResult.soc = item[3];
                outFaultResult.sTime = item[4];
                outFaultResult.sSoc = item[5];
                outFaultResult.unitNum = Integer.parseInt(item[6]);
                outFaultResult.varType = Integer.parseInt(item[7]);
                outFaultResult.maxNum = Integer.parseInt(item[8]);
                outFaultResult.maxValue = Double.parseDouble(item[9]);
                outFaultResult.type = Integer.parseInt(item[10]);
                outFaultResult.stringVars = item[11];
                outFaultResult.quesSingles = item[12];
                frequencyStatistics.outFaultResults.add(outFaultResult);
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


    public static Map<String, FrequencyStatistics> mapCopy(Map<String, FrequencyStatistics> map) {
        Map<String, FrequencyStatistics> resultMap = new HashMap<>();
        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map.entrySet()) {
            resultMap.put(stringFrequencyStatisticsEntry.getKey(), new FrequencyStatistics(stringFrequencyStatisticsEntry.getValue()));
        }
        return resultMap;
    }

}
