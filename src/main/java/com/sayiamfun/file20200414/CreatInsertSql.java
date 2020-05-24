package com.sayiamfun.file20200414;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 拼装mysql插入语句
 */
public class CreatInsertSql {

    public static void main(String[] args) throws IOException {
        String s = "veh_model_name,un_name,trade_mark,common_name,product_name,driving_range_work,curb_weight,total_mass,mass_long,mass_width,mass_high,wheelbase,max_speed,top_speed_30,passenger_category,dict_uses,dict_name,fuel_type,energy_storage_device,energy_storage_type,energy_total_capacity,energy_assembly_mass,displacement,power,individual_model,individual_shape,individual_shape_size,monomer_nominal,three_hour_rated_capacity,monomer_mass,monomer_num,monomer_manufacturer,assembly_manufacturer,module_model,min_module_standard,min_module_rated_capacity,energy_storage_model,energy_storage_combination,energy_assembly_standard,energy_assembly_outpower,assembly_nominal_capacity,quick_change_device,cathode_material,anode_material,electrolyte_composition,electrolyte_morphology,veh_energy_sys_model,veh_energy_manufacturer,charging_method,battery_class,pack_energy_density,controller_model,controller_manufacturer";
        List<String> title = Arrays.asList(s.split(","));
        String join = String.join(",", title);
        String file = "C:\\Users\\liwenjie\\Downloads\\10.最终结果_v5.0 -修正库里车型.xls";
        //csv文件读取方法l
        String cellStr = null;// 单元格，最终按字符串处理
        InputStream is = null;// 输入流对象
        HSSFWorkbook workbook = null;
        is = new FileInputStream(new File(file));// 获取文件输入流
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
                        cellStr = cell.getStringCellValue().replaceAll("\r", "").replaceAll("\n",""); //如果是字符串，按照字符串处理
                    } catch (Exception e) {
                        cellStr = "" + cell.getNumericCellValue(); //报异常安装数字处理  暂时没有找到判断方法
                    }
                }
                childrenList.add(cellStr);
            }
            resultList.add(childrenList);
        }

        StringBuilder stringBuilder;
        int total = 0;
        List<String> index = resultList.get(0);
        for (int i = 1; i < resultList.size(); i++) {
            stringBuilder = new StringBuilder(500);
            stringBuilder.append("insert into sys_vehicle_constant (").append(join).append(")").append(" values (");
            int m = 0;
            List<String> strings = resultList.get(i);
            for (String s1 : title) {
                if (m > 0) stringBuilder.append(",");
                if (index.indexOf(s1) == -1 || index.indexOf(s1) >= strings.size()) {
                    stringBuilder.append("''");
                    continue;
                }
                String s2 = strings.get(index.indexOf(s1));
                stringBuilder.append("'").append(s2).append("'");
                m++;
            }
            stringBuilder.append(");");
            System.out.println(stringBuilder.toString());
            ++total;
        }
        System.out.println(total);
        is.close();
    }

    private static String getString(Object object) {
        return null == object ? "" : object.toString();
    }
}

