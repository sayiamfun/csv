package com.sayiamfun.csv;

import com.sayiamfun.common.utils.ScanPackage;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TmpClass {

    public static void main(String[] args) {
//        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//        System.out.println("程序类加载器：" + systemClassLoader);
//        ClassLoader parent = systemClassLoader.getParent();
//        System.out.println("程序类加载器父类（扩展类加载器）：" + parent);
//        ClassLoader bootStrapClassLoader = parent.getParent();
//        System.out.println("拓展类加载器父类（启动类加载器）：" + bootStrapClassLoader);
        String title = "车辆Vin,时间,是否补发(0：补发，1：实时),GPS海拔,GPS车速,GPS里程,GPS方向,MCU使能命令,驱动电机状态命令,驱动电机工作模式命令,驱动电机旋转方向命令,加速踏板开度,驱动电机目标转速指令,驱动电机目标转矩指令,空调暖风系统使能指令,蓄电池电压,挡位信号,制动信号,挡位误操作,防溜车功能使能指令,方向盘转角信号,整车State状态（状态机编码）,ON挡唤醒信号,快充唤醒信号,慢充唤醒信号,远程唤醒信号,整车模式,MCU低压唤醒指令,BMS低压唤醒指令,空调暖风系统低压唤醒指令,动力电池高压检测状态超时,高压系统预充电状态超时,动力电池正端继电器断开状态超时,高压放电状态超时,READY灯,动力电池连接（高压上电）,动力电池系统故障显示（文字显示）,制动系统故障显示,VCU故障处理等级,VCU系统最高报警等级,车辆运行工况,扭矩限制,DC/DC使能,慢充CC电压,快充CC2电压,真空泵工作电流,电量低提醒,整车平均能量消耗,续驶里程,ODO总里程,系统故障灯,制动能量回收挡位,车速信号,整车瞬时能量消耗,驱动有效,交流充电剩余充电时间,充电状态,终端唤醒结束标志位,SOC过高报警,运行模式,直流充电剩余充电时间,驱动电机当前状态,驱动电机当前工作模式,驱动电机当前旋转方向,驱动电机请求整车控制器处理标志位,驱动电机当前转速,驱动电机当前转矩,MCU生命信号,直流母线电压,直流母线电流,驱动电机相电流（有效值）,驱动电机最大驱动转矩,驱动电机最大制动转矩,驱动电机控制器IGBT温度,驱动电机温度,MCU温度,驱动电机总数,驱动电机序号,驱动电机故障总数,驱动电机编号1（表示编号中1到8个ASC码值）,驱动电机编号2（表示编号中9到16个ASC码值）,驱动电机编号3（表示编号中17到24个ASC码值）,驱动电机编号4（表示编号中25到32个ASC码值）,VIN编码1（表示编号中1到8个ASC码值）,VIN编码2（表示编号中9到16个ASC码值）,VIN编码3（表示编号中17到24个ASC码值）,动力电池剩余电量SOC,动力电池内部总电压V1,动力电池充/放电电流,动力电池负载端总电压V3,单体电芯最高电压,单体电芯最低电压,动力电池正极对GND绝缘电阻,动力电池负极对GND绝缘电阻,单体电芯最高温度,单体电芯最低温度,动力电池供应商,动力电池类型,动力电池可用能量,动力电池可用容量,电池均衡激活,动力电池充放电状态,电池包1最高温度,电池包1最低温度,电池包1电压,动力电池包编号1（表示编号中1到8个ASC码值）,动力电池包编号2（表示编号中9到16个ASC码值）,动力电池包编号3（表示编号中17到24个ASC码值）,动力电池包编号4（表示编号中25到32个ASC码值）,动力电池包个数,动力电池包编号长度,动力电池子系统序号,动力电池单体总数,动力电池温度点总数,动力电池故障总数,1号电池单体电压,2号电池单体电压,3号电池单体电压,4号电池单体电压,5号电池单体电压,6号电池单体电压,7号电池单体电压,8号电池单体电压,9号电池单体电压,10号电池单体电压,11号电池单体电压,12号电池单体电压,13号电池单体电压,14号电池单体电压,15号电池单体电压,16号电池单体电压,17号电池单体电压,18号电池单体电压,19号电池单体电压,20号电池单体电压,21号电池单体电压,22号电池单体电压,23号电池单体电压,24号电池单体电压,25号电池单体电压,26号电池单体电压,27号电池单体电压,28号电池单体电压,29号电池单体电压,30号电池单体电压,31号电池单体电压,32号电池单体电压,33号电池单体电压,34号电池单体电压,35号电池单体电压,36号电池单体电压,37号电池单体电压,38号电池单体电压,39号电池单体电压,40号电池单体电压,41号电池单体电压,42号电池单体电压,43号电池单体电压,44号电池单体电压,45号电池单体电压,46号电池单体电压,47号电池单体电压,48号电池单体电压,49号电池单体电压,50号电池单体电压,51号电池单体电压,52号电池单体电压,53号电池单体电压,54号电池单体电压,55号电池单体电压,56号电池单体电压,57号电池单体电压,58号电池单体电压,59号电池单体电压,60号电池单体电压,61号电池单体电压,62号电池单体电压,63号电池单体电压,64号电池单体电压,65号电池单体电压,66号电池单体电压,67号电池单体电压,68号电池单体电压,69号电池单体电压,70号电池单体电压,71号电池单体电压,72号电池单体电压,73号电池单体电压,74号电池单体电压,75号电池单体电压,76号电池单体电压,77号电池单体电压,78号电池单体电压,79号电池单体电压,80号电池单体电压,81号电池单体电压,82号电池单体电压,83号电池单体电压,84号电池单体电压,85号电池单体电压,86号电池单体电压,87号电池单体电压,88号电池单体电压,89号电池单体电压,90号电池单体电压,91号电池单体电压,92号电池单体电压,93号电池单体电压,94号电池单体电压,95号电池单体电压,96号电池单体电压,1号温度检测点温度,2号温度检测点温度,3号温度检测点温度,4号温度检测点温度,5号温度检测点温度,6号温度检测点温度,7号温度检测点温度,8号温度检测点温度,9号温度检测点温度,10号温度检测点温度,11号温度检测点温度,12号温度检测点温度,13号温度检测点温度,14号温度检测点温度,15号温度检测点温度,16号温度检测点温度,17号温度检测点温度,18号温度检测点温度,19号温度检测点温度,20号温度检测点温度,21号温度检测点温度,22号温度检测点温度,23号温度检测点温度,24号温度检测点温度,25号温度检测点温度,26号温度检测点温度,27号温度检测点温度,28号温度检测点温度,29号温度检测点温度,30号温度检测点温度,31号温度检测点温度,32号温度检测点温度,最高单体电压编号,最高温度编号,最低温度编号,最低单体电压编号,最高单体电压包号,最高温度包号,最低温度包号,最低单体电压包号,PTC当前工作状态,PTC当前工作等级,ECC当前状态,DCDC当前状态,DC/DC输出端电压,DC/DC输出端电流,车载充电机当前状态,慢充枪连接状态,充电模式,充电机输出端电流,充电机输出端电压,绝对（大气）压力传感器故障三级【C004601还原通道";
        List<String> strings = Arrays.asList(title);
        String inputPath = "/Users/liwenjie/Downloads/vehData/inputData/工作簿3.xlsx";


        //电压下标  115   210
        //温度列表   211   242
        //vin 0
        //vid 0
        //时间 1   需处理
        //实时历史  2  需处理  0不发 1实时
        //车速 4
        //里程 5
        //总电压  87
        //总电流  88
        //绝缘电阻  92
        //SOC  86
        //充电状态   56
        //运行模式   59

        List<List<String>> list = getListXssf(inputPath);
        List<Map<String, String>> resultList = new LinkedList<>();
        Map<String, String> tmpMap = new HashMap<>();
        for (List<String> stringList : list) {
            tmpMap = new HashMap<>();
            /**
             *  String messageType = dat.get(ProtocolItem.MESSAGETYPE);
             *             String carStatus = dat.get(ProtocolItem.CAR_STATUS);
             *             String chargeStatus = dat.get(ProtocolItem.CHARGE_STATUS);
             *             String speed = dat.get(ProtocolItem.SPEED);
             *             String vin = dat.get(ProtocolItem.VIN);
             *             String vid = dat.get(ProtocolItem.VID);
             *             String time = dat.get(ProtocolItem.TIME);
             *             String soc = dat.get(ProtocolItem.SOC);
             *             String mileage = dat.get(ProtocolItem.TOTAL_MILEAGE);
             *             String totalVolt = dat.get(ProtocolItem.TOTAL_VOLT);
             *             String totalElec = dat.get(ProtocolItem.TOTAL_ELE);
             *             String singleVoltages = dat.get(ProtocolItem.SINGLE_VOLT_ORIG);
             *             String singleTemps = dat.get(ProtocolItem.SINGLE_TEMP_ORGI);
             *             String resis = dat.get(ProtocolItem.INSULATION_RESISTANCE);
             */
            tmpMap.put(ProtocolItem.MESSAGETYPE, "1".equals(stringList.get(2)) ? "MESSAGETYPE" : "HISTROYTYPE");
            tmpMap.put(ProtocolItem.CAR_STATUS, stringList.get(59));
            tmpMap.put(ProtocolItem.CHARGE_STATUS, stringList.get(56));
            tmpMap.put(ProtocolItem.SPEED, stringList.get(4));
            tmpMap.put(ProtocolItem.VIN, stringList.get(0));
            tmpMap.put(ProtocolItem.VID, stringList.get(0));
            tmpMap.put(ProtocolItem.TIME, getTime(stringList.get(1)));
            tmpMap.put(ProtocolItem.SOC,stringList.get(86));
            tmpMap.put(ProtocolItem.TOTAL_MILEAGE,stringList.get(5));
            tmpMap.put(ProtocolItem.TOTAL_VOLT,stringList.get(87));
            tmpMap.put(ProtocolItem.TOTAL_ELE,stringList.get(88));
            tmpMap.put(ProtocolItem.SINGLE_VOLT_ORIG,String.join("_",stringList.subList(115,210)));
            tmpMap.put(ProtocolItem.SINGLE_TEMP_ORGI,String.join("_",stringList.subList(211,242)));
            tmpMap.put(ProtocolItem.INSULATION_RESISTANCE,stringList.get(92));
            resultList.add(tmpMap);
        }
//        list.forEach(System.out::println);
        resultList.forEach(System.out::println);
        //excel文件读取


    }

    /**
     * 2020/04/26 09:55:21.000.
     *
     * @param s
     * @return
     */
    private static String getTime(String s) {
        String[] s1 = s.split(" ");
        String s2 = s1[0].replaceAll("/", "");
        String substring = s1[1].replaceAll(":", "").substring(0, 6);
        return s2 + substring;
    }

    public static List<List<String>> getList(String inputPath) {
        InputStream is = null;// 输入流对象
        HSSFWorkbook workbook = null;
        try {
            String cellStr = null;// 单元格，最终按字符串处理
            is = new FileInputStream(new File(inputPath));// 获取文件输入流
            workbook = new HSSFWorkbook(is);// 创建Excel文件对象
            HSSFSheet sheet = workbook.getSheetAt(0);// 取出第一个工作表，索引是0
            // 开始循环遍历行，表头不处理，从1开始
            List<List<String>> resultList = new LinkedList<>(); //封装返回数据的结合
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);// 获取行对象
                if (row == null) {// 如果为空，不处理
                    continue;
                }
                // 循环遍历单元格
                List<String> childrenList = new LinkedList<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);// 获取单元格对象
                    if (cell == null) {// 单元格为空设置cellStr为空串
                        cellStr = "";
                    } else {
                        try {
                            cellStr = cell.getStringCellValue(); //如果是字符串，按照字符串处理
                        } catch (Exception e) {
                            cellStr = "" + cell.getNumericCellValue(); //报异常安装数字处理  暂时没有找到判断方法
                        }
                    }
                    childrenList.add(cellStr);
                }
                resultList.add(childrenList);
            }
            return resultList;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 关闭文件流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static List<List<String>> getListXssf(String inputPath) {
        InputStream is = null;// 输入流对象
        XSSFWorkbook workbook = null;
        try {
            String cellStr = null;// 单元格，最终按字符串处理
            is = new FileInputStream(new File(inputPath));// 获取文件输入流
            workbook = new XSSFWorkbook(is);// 创建Excel文件对象
            XSSFSheet sheet = workbook.getSheetAt(0);// 取出第一个工作表，索引是0
            // 开始循环遍历行，表头不处理，从1开始
            List<List<String>> resultList = new LinkedList<>(); //封装返回数据的结合
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);// 获取行对象
                if (row == null) {// 如果为空，不处理
                    continue;
                }
                // 循环遍历单元格
                List<String> childrenList = new LinkedList<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);// 获取单元格对象
                    if (cell == null) {// 单元格为空设置cellStr为空串
                        cellStr = "";
                    } else {
                        try {
                            cellStr = cell.getStringCellValue(); //如果是字符串，按照字符串处理
                        } catch (Exception e) {
                            cellStr = "" + cell.getNumericCellValue(); //报异常安装数字处理  暂时没有找到判断方法
                        }
                    }
                    childrenList.add(cellStr);
                }
                resultList.add(childrenList);
            }
            return resultList;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 关闭文件流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
