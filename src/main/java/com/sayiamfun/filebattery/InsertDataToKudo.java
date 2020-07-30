package com.sayiamfun.filebattery;

import com.sayiamfun.common.KuduOperUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InsertDataToKudo {

    public static void main(String[] args) {
        String inputFile = "C:\\Users\\liwenjie\\Downloads\\北理工课题专项-20年1-2月(1).xls";
        InputStream is = null;// 输入流对象
        HSSFWorkbook workbook = null;
        try {
            String cellStr = null;// 单元格，最终按字符串处理
            is = new FileInputStream(new File(inputFile));// 获取文件输入流
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
                            cellStr = cell.getStringCellValue().replaceAll("\r", ""); //如果是字符串，按照字符串处理
                        } catch (Exception e) {
                            cellStr = "" + cell.getNumericCellValue(); //报异常按照数字处理  暂时没有找到判断方法
                        }
                    }
                    childrenList.add(cellStr);
                }
                resultList.add(childrenList);
            }
            Set<String> set = new HashSet<>();
            List<Object[]> dataList = new LinkedList<>();
            for (List<String> strings : resultList) {
                if (set.add(strings.toString())) {
                    strings.add(UUID.randomUUID().toString().split("-")[0]);
                    Object[] objects = strings.toArray();
                    dataList.add(objects);
                }
            }
            String[] clu = {
                    "veh_model_name",
                    "un_name",
                    "trade_mark",
                    "product_name",
                    "curb_weight",
                    "total_mass",
                    "mass_long",
                    "mass_width",
                    "mass_high",
                    "max_speed",
                    "dict_uses",
                    "dict_name",
                    "fuel_type",
                    "energy_storage_device",
                    "energy_storage_type",
                    "individual_model",
                    "individual_shape",
                    "individual_shape_size",
                    "monomer_nominal",
                    "three_hour_rated_capacity",
                    "monomer_mass",
                    "monomer_num",
                    "monomer_manufacturer",
                    "assembly_manufacturer",
                    "module_model",
                    "min_module_standard",
                    "min_module_rated_capacity",
                    "min_module_elctro_capacity",
                    "energy_storage_combination",
                    "energy_storage_model",
                    "controller_model",
                    "controller_manufacturer",
                    "energy_assembly_standard",
                    "energy_assembly_outpower",
                    "assembly_nominal_capacity",
                    "energy_total_capacity",
                    "energy_assembly_mass",
                    "quick_change_device",
                    "cathode_material",
                    "anode_material",
                    "electrolyte_composition",
                    "electrolyte_morphology",
                    "veh_energy_sys_model",
                    "veh_energy_manufacturer",
                    "charging_method",
                    "driving_range_work",
                    "driving_range_isokinetic",
                    "top_speed_30",
                    "hybrid_car_pure_maxspeed",
                    "battery_class",
                    "passenger_category",
                    "common_name",
                    "pack_energy_density",
                    "batch_month",
                    "yield",
                    "displacement",
                    "power",
                    "transformation",
                    "wheelbase",
                    "id"
            };
            String table = "impala::default.sys_vehicle_constant";
            KuduOperUtil.insert(table, dataList, clu);
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
    }
}
