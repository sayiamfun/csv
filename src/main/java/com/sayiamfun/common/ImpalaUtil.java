package com.sayiamfun.common;


//import com.nat.common.StringUtils;
//import com.nat.common.SysDefine;
//import com.nat.evmsc.entity.SysAlarmNoteEntity;
//import com.nat.evmsc.entity.SysUserEntity;
//import org.apache.log4j.Logger;
//import org.apache.poi.ss.formula.functions.T;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 *
 */
public class ImpalaUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImpalaUtil.class);

    private static String url;

    public void setUrl(String url) {
        ImpalaUtil.url = "jdbc:impala://10.11.3.18:21050";
    }

    public static Connection getConnection() throws Exception {
        url = "jdbc:impala://10.10.11.188:21050";
        try {
            Class.forName("com.cloudera.impala.jdbc41.Driver");
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * ResulrSet转List<T>
     *
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return List<T>
     */
    public static <T> List<T> resultToList(ResultSet resultSet, Class<T> clazz) {
        //创建一个 T 类型的数组
        List<T> list = new ArrayList<>();
        try {
            //获取resultSet 的列的信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = 0;
            //遍历resultSet
            while (resultSet.next()) {
                //通过反射获取对象的实例
                T t = clazz.getConstructor().newInstance();
                //遍历每一列
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    //获取列的名字
                    String name = metaData.getColumnLabel(i + 1);
                    //因为列的名字和我们EMP中的属性名是一样的，所以通过列的名字获得其EMP中属性
                    String fName = underlineToCamel(name);
                    Field field = null;
                    try {
                        field = clazz.getDeclaredField(fName);
                    } catch (Exception e) {
                        continue;
                    }
                    //因为属性是私有的，所有获得其对应的set 方法。set+属性名首字母大写+其他小写
                    String setName = "set" + fName.toUpperCase().substring(0, 1) + fName.substring(1);
                    //因为属性的类型和set方法的参数类型一致，所以可以获得set方法
                    Method setMethod = clazz.getMethod(setName, field.getType());
                    //执行set方法，把resultSet中的值传入emp中，  再继续循环传值
                    setMethod.invoke(t, resultSet.getObject(name));
                }
                //把赋值后的对象 加入到list集合中
                list.add(t);
                count++;
            }

        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }
        // 返回list
        return list;
    }

    private static final char UNDERLINE = '_';

    /**
     * 下划线转驼峰
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(param.charAt(i));
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }


    public static List<Map<String, Object>> getResultMap(ResultSet rs)
            throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> hm = new HashMap<String, Object>();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String key = rsmd.getColumnLabel(i);
                int type = rsmd.getColumnType(i);
                switch (type) {
                    case 1:
                        hm.put(key, rs.getString(i));
                        break;
                    case 12:
                        hm.put(key, rs.getString(i));
                        break;
                    case -6:
                        hm.put(key, rs.getInt(i));
                        break;
                    case -5:
                        hm.put(key, rs.getInt(i));
                        break;
                    case 4:
                        hm.put(key, rs.getInt(i));
                        break;
                    case 5:
                        hm.put(key, rs.getInt(i));
                        break;
                    case 6:
                        hm.put(key, rs.getFloat(i));
                        break;
                    case 8:
                        hm.put(key, rs.getDouble(i));
                        break;
                    case 91:
                        hm.put(key, rs.getDate(i));
                        break;
                    case 92:
                        hm.put(key, rs.getTime(i));
                        break;
                    case 93:
                        hm.put(key, rs.getTimestamp(i));
                        break;
                    case 1111:
                        hm.put(key, rs.getObject(i));
                        break;
                }
            }
            list.add(hm);
        }
        return list;
    }

    /**
     * 通过sql从impala查询数据
     *
     * @param sql
     * @return List
     */

    public static List<Map<String, Object>> queryData(String sql) {
        logger.info("impala--" + sql);
        try (Connection connection = getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql.toString())) {
            List<Map<String, Object>> rM = getResultMap(rs);
            return rM;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
//         刷新业务表
            executeImpalaSql("REFRESH pre_veh_check_info");
            executeImpalaSql("REFRESH his_alarm_details");
            executeImpalaSql("REFRESH model_rate_result_details");
            executeImpalaSql("REFRESH model_abnor_result_details");
            executeImpalaSql("REFRESH model_abnormal_single_result_details");
            executeImpalaSql("REFRESH model_fault_result_details");
            executeImpalaSql("REFRESH model_fluctuan_result_details");
            executeImpalaSql("REFRESH model_icpeak_result_details");
            executeImpalaSql("REFRESH model_source_data_details");
            executeImpalaSql("REFRESH model_step_consistency_result_details");
            executeImpalaSql("REFRESH model_thresbyset_result_details");
        }
        return null;
    }

    public static <T> List<T> queryData(String sql, Class<T> clazz) {
        logger.info("impala--" + sql);
        try (Connection connection = getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql.toString())) {
            return rs != null ? resultToList(rs, clazz) : null;
        } catch (Exception e) {
//            logger.error(e);
        }
        return null;
    }

    /**
     * 运行impala sql
     *
     * @param sql
     * @return
     * @throws ClassNotFoundException
     */
    public static int executeImpalaSql(String sql) {

        if (sql.isEmpty()) {
            return 0;
        }
        System.out.println(sql);
        try (Connection connection = getConnection();
             Statement st = connection.createStatement()) {
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                return rs;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    public static List<Map<String, Object>> insertid(List<Map<String, Object>> maps) {
        if (null != maps) {
            for (Map<String, Object> map : maps) {
                map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
            }
            return maps;
        }
        return null;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> maps = ImpalaUtil.queryData("select * from impala_logintime_kudu limit 10;");
        for (Map<String, Object> map : maps) {
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                System.out.print(stringObjectEntry.getKey() + ":" + stringObjectEntry.getValue() + ";");
            }
            System.out.println();
        }
        maps = ImpalaUtil.queryData("select * from ALARM_DETAILS limit 10;");
        for (Map<String, Object> map : maps) {
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                System.out.print(stringObjectEntry.getKey() + ":" + stringObjectEntry.getValue() + ";");
            }
            System.out.println();
        }
    }

}

