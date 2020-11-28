package com.sayiamfun;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ReadFile {


    public static void main(String[] args) {
        impalaShell();
    }


    public static void createInsertSql() {
        String inputFile = "/Users/liwenjie/Downloads/funeng/funeng.csv";   //生产环境导出数据

        String outFileconfig = "/Users/liwenjie/Downloads/funeng/sql/config.sql";//配置表sql
        String outFilemodel = "/Users/liwenjie/Downloads/funeng/sql/model.sql";//车型表sql
        String outFilevin = "/Users/liwenjie/Downloads/funeng/sql/vin.sql";//车辆表sql

        Map<String, Long> configMap = new HashMap<>();
        Map<String, Long> modelMap = new HashMap<>();

        Set<String> configSet = new HashSet<>();
        Set<String> modelSet = new HashSet<>();
        Set<String> vinSet = new HashSet<>();
        Set<Integer> configidSet = new HashSet<>();
        Set<Integer> modelidSet = new HashSet<>();
        Set<Integer> vinidSet = new HashSet<>();

        OutputStreamWriter ow = null;
        BufferedWriter bw = null;

        InputStreamReader ir = null;
        BufferedReader reader = null;
        for (int i = 0; i < 3; i++) {
            try {
                ir = new InputStreamReader(new FileInputStream(new File(inputFile)), StandardCharsets.UTF_8);
                reader = new BufferedReader(ir);//到读取的文件

                if (i == 0) {
                    ow = new OutputStreamWriter(new FileOutputStream(new File(outFileconfig), true), StandardCharsets.UTF_8);
                    bw = new BufferedWriter(ow);
                } else if (i == 1) {
                    ow = new OutputStreamWriter(new FileOutputStream(new File(outFilemodel), true), StandardCharsets.UTF_8);
                    bw = new BufferedWriter(ow);
                } else {
                    ow = new OutputStreamWriter(new FileOutputStream(new File(outFilevin), true), StandardCharsets.UTF_8);
                    bw = new BufferedWriter(ow);
                }


                String line;
                String lastMsg = "";
                while ((line = reader.readLine()) != null) {
                    if ("".equals(line.trim())) continue;
                    if (line.contains("vin")) continue;
                    String[] s = line.split(",");
                    if (s.length < 11) {
                        lastMsg += line;
                        continue;
                    } else {
                        lastMsg = line;
                    }
                    s = lastMsg.split(",");
                    int vin = 0, uuid = 1, lic = 2, modelid = 3, modelName = 4, bull = 5, configName = 6, unitid = 7, unitName = 8, dictname = 9, salearea = 10;
                    if (i == 0) {
                        if (configSet.add(s[configName])) {
                            try {
                                Long id = getId("1234", configidSet);
                                bw.write("insert into res_veh_config (id,veh_config_name,create_time) values (" + id + ",'" + s[configName] + "',sysdate());");
                                configMap.put(s[configName], id);
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println(line);
                                System.out.println(lastMsg);
                            }
                            bw.newLine();
                            bw.flush();
                        }
                    } else if (i == 1) {
                        if (modelSet.add(s[modelName])) {
                            try {
                                Long id = getId(s[modelid], modelidSet);
                                modelMap.put(s[modelName], id);
                                bw.write("insert into sys_veh_model (id,veh_model_name,veh_config_id,bulletion,unname,CREATE_TIME) VALUES (" + id + ",'" + s[modelName] + "'," + configMap.get(s[configName]) + ",'" + s[bull] + "','" + s[unitName] + "',sysdate());");
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println(line);
                                System.out.println(lastMsg);
                            }
                            bw.newLine();
                            bw.flush();
                        }
                    } else {
                        if (vinSet.add(s[vin])) {
                            try {
                                Long id = getId(s[uuid], vinidSet);
                                bw.write("insert into veh_check_info (id,licenseplate,licensecolor,veh_model_id,veh_config_id,veh_type,unname,salearea,vin,vid,create_time) VALUES (" + id + ",'" + s[lic] + "','渐变绿色'," + modelMap.get(s[modelName]) + "," + configMap.get(s[configName]) + ",'" + s[dictname] + "','" + s[unitName] + "'," + s[salearea] + ",'" + s[vin] + "','" + s[uuid] + "',sysdate());");
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println(line);
                                System.out.println(lastMsg);
                            }
                            bw.newLine();
                            bw.flush();
                        }
                    }
                    lastMsg = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                FileToModelFile.closeFileStream(ir, reader, ow, bw);
            }
        }
    }


    public static void impalaShell() {

        String inputFile = "/Users/liwenjie/Downloads/funeng/vin.txt";  //vin文件 每行一个

        String outFile = "/Users/liwenjie/Downloads/funeng/impalaSql/";  //生成的脚本文件地址

        String shFileName = "_selectdata.sh";//生成的脚本文件名称   生成后为  0_vinselect.sh
        String startsh = "start.sh";//最后执行的脚本文件名称

        String resultFilePath = "/home/hadoop/data/funeng/data/";//导出结果文件存放路径
        String resultFileName = "_funengveh.csv";//导出结果文件名臣  生成后为 0_funengveh.csv

        int lineNum = 5000;//多少个vin放入一个脚本

        String impalaSql = "impala-shell -q \"select\n" +
                "v.vin,\n" +
                "v.uuid,\n" +
                "v.licenseplate,\n" +
                "m.id veh_model_id,\n" +
                "m.veh_model_name,\n" +
                "m.model_notice_id bulletion,\n" +
                "m.reserve1 veh_config_name,\n" +
                "u.id unit_id,\n" +
                "u.name unit_name,\n" +
                "v.dictname,\n" +
                "v.salearea\n" +
                "from veh_check_info v \n" +
                "left join sys_veh_model m on v.veh_model_id = m.id \n" +
                "left join sys_unit u on m.unit_id = u.id where vin in (";  //要执行的impalaSql  注意!结尾必须是   vin in (


        if (!new File(outFile).exists()) System.out.println(new File(outFile).mkdirs());

        OutputStreamWriter ow = null;
        BufferedWriter bw = null;

        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(inputFile)), StandardCharsets.UTF_8);
            reader = new BufferedReader(ir);//到读取的文件

            int index = 0;

            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile + index + shFileName), true), StandardCharsets.UTF_8);
            bw = new BufferedWriter(ow);
            List<String> list = new LinkedList<>();
            List<String> filelist = new LinkedList<>();
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if ("".equals(line.trim())) continue;
                if (i == 0) {
                    bw.write(impalaSql);
                }
                bw.write("'" + line + "',");
                i++;
                if (i != 0 && i % 10 == 0) {
                    bw.flush();
                    bw.newLine();
                }
                if (i >= lineNum) {
                    i = 0;
                    list.add(index + shFileName);
                    filelist.add(index + resultFileName);
                    bw.write("'');\" -B --output_delimiter=\",\" --print_header -o " + resultFilePath + index + resultFileName);
                    bw.flush();
                    index++;
                    ow = new OutputStreamWriter(new FileOutputStream(new File(outFile + index + shFileName), true), StandardCharsets.UTF_8);
                    bw = new BufferedWriter(ow);
                }
            }
            list.add(index + shFileName);
            filelist.add(index + resultFileName);
            bw.write("'');\" -B --output_delimiter=\",\" --print_header -o " + resultFilePath + index + resultFileName);
            bw.flush();


            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile + startsh), true), StandardCharsets.UTF_8);
            bw = new BufferedWriter(ow);


            StringBuilder stringBuilder = new StringBuilder();
            for (String s : list) {
                stringBuilder.append("sh ").append(s).append(" start").append("\n");
            }
            bw.write("mkdir -p " + resultFilePath);
            bw.newLine();
            bw.write(stringBuilder.toString());
            bw.newLine();
            bw.write("cd " + resultFilePath);
            bw.newLine();
            stringBuilder = new StringBuilder("cat");
            for (String s : filelist) {
                stringBuilder.append(" ").append(s);
            }
            stringBuilder.append(" ").append(">").append(" ").append(resultFileName.substring(1));
            bw.write(stringBuilder.toString());
            bw.newLine();
            bw.write("sz " + resultFileName.substring(1));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileToModelFile.closeFileStream(ir, reader, ow, bw);
        }
    }

    public static String firstLetterToUp(String letter) {
        if (null == letter || "".equals(letter.trim())) return "String";
        char[] chars = letter.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) result.append(Character.toUpperCase(chars[i]));
            else result.append(chars[i]);
        }
        return result.toString();
    }

    public static Long getId(String UUID, Set<Integer> idSet) {
        if (null == UUID) return null;
        int userId = UUID.hashCode();
        userId = userId < 0 ? -userId : userId;   //String.hashCode()可能会是负数，如果为负数需要转换为正数
        if (idSet.add(userId))
            return Long.valueOf(String.valueOf(userId));
        while (true) {
            userId = java.util.UUID.randomUUID().hashCode();
            userId = userId < 0 ? -userId : userId;
            if (idSet.add(userId)) {
                return Long.valueOf(String.valueOf(userId));
            }
        }

    }

}
