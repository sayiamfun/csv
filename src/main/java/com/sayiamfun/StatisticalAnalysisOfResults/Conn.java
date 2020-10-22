package com.sayiamfun.StatisticalAnalysisOfResults;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.*;
import java.util.*;


public class Conn {
    private static Logger logger = LoggerFactory.getLogger(Conn.class);

    //获取从页面获取上传的报文文件路径
    static String anal_state_sql = "select id,file_path from sys_parsing_task where state=0 and file_path is not null order by id asc limit 1";
    static String source_hdfs_sql = "SELECT ID,UPLOAD_URL FROM SYS_SAFE_ANALYSE SYS_SAFE_ANALYSE WHERE DEL_STATE > 0 AND DEL_STATE < 7";
    static String resolver_hdfs_sql = "SELECT ID,MID_URL FROM SYS_SAFE_ANALYSE WHERE DEL_STATE = 2 OR DEL_STATE = 3";
    static String resolver_hdfs_sql_GJ = "SELECT ID,MID_URL FROM SYS_SAFE_ANALYSE_T WHERE DEL_STATE = 2 OR DEL_STATE = 3";
    static String model_config_sql = "select veh_model_num,veh_config_id,vin,max_vol,min_vol,max_m_vol,min_m_vol,max_m_tem,min_m_tem,max_soc,min_soc,min_res,dif_max_tem,dif_max_vol,tem_abnormity,vol_abnormity,vol_rising,formula_value from sys_veh_model_spec";
    static String model_fuel_config_sql = "select VEH_MODEL_NUM,VEH_CONFIG_ID,VIN,MAX_VOL,MIN_VOL,MAX_M_VOL,MIN_M_VOL,MAX_M_COL,MIN_M_COL,MAX_M_HOL,MIN_M_HOL,MAX_M_QOL,MIN_M_QOL,MIN_M_BOL,MIN_M_EOL,VOL_RISING,VOL_MONOMER_RISING,VOL_RISE_RISING,VOL_T_RISE,VOL_H_RISE,VOL_H_DOWN from SYS_VEH_MODEL_SPEC where max_vol is not null";
    static String model_defined_config_sql = "select id,veh_model_num,veh_config_id,vin,rule  from  sys_veh_model_ordefined  where rule is not null";//自定义值率规则
    static String model_trait_config_sql = "SELECT trait_name,trait_value from model_trait";

    static {
        Properties sysParams = ConfigUtils.sysParams;
        if (null != sysParams) {

            if (sysParams.containsKey("anal.state.sql")) {
                anal_state_sql = sysParams.getProperty("anal.state.sql");
            }

            if (sysParams.containsKey("anal.source.hdfs.state.sql")) {
                source_hdfs_sql = sysParams.getProperty("anal.source.hdfs.state.sql");
            }

            if (sysParams.containsKey("anal.resolver.hdfs.state.sql")) {
                resolver_hdfs_sql = sysParams.getProperty("anal.resolver.hdfs.state.sql");
            }

            if (sysParams.containsKey("model.config.sql")) {
                model_config_sql = sysParams.getProperty("model.config.sql");
            }

            if (sysParams.containsKey("model.fuel.config.sql")) {
                model_fuel_config_sql = sysParams.getProperty("model.fuel.config.sql");
            }

            if (sysParams.containsKey("model.defined.config.sql")) {
                model_defined_config_sql = sysParams.getProperty("model.defined.config.sql");
            }

        }
    }

    public Connection getConn() {
        try {
            Properties p = ConfigUtils.sysDefine;
            String driver = p.getProperty("jdbc.driver").trim();
            String url = p.getProperty("jdbc.url");
            if (null == url || "".equals(url.trim())) {
                return null;
            }
            url = url.trim();
            String username = p.getProperty("jdbc.username").trim();
            String password = p.getProperty("jdbc.password").trim();
            Connection conn = null;
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void executeUpdate(String sql) {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            if (StringUtils.isEmpty(sql)) {
                return;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return;
            s = conn.createStatement();
            s.executeUpdate(sql);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
    }

    public List<String[]> getAnalState() {
        try {
            //id path
            List<String[]> paths = getStringArrList(anal_state_sql, 2);
            if (null != paths) {
                return paths;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public List<String[]> getSourceState() {
        try {
            //id path
            List<String[]> paths = getStringArrList(source_hdfs_sql, 2);
            if (null != paths) {
                return paths;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public List<String[]> getResolverState() {
        try {
            //id path
            List<String[]> paths = getStringArrList(resolver_hdfs_sql, 2);
            if (null != paths) {
                return paths;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取 sys_safe_analyse 表的数据
     * 监管平台新模块
     * 获取国家平台数据的解析结果
     *
     * @return
     */
    public List<String[]> getResolverStateGJ() {
        try {
            //id path
            List<String[]> paths = getStringArrList(resolver_hdfs_sql_GJ, 2);
            if (null != paths) {
                return paths;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private List<Object[]> getObjectArrList(String sql, int arrsize) {
        List<Object[]> rules = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            if (StringUtils.isEmpty(sql)) {
                return null;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;
            rules = new LinkedList<Object[]>();
            s = conn.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                Object[] objs = new Object[arrsize];
                for (int idx = 0; idx < arrsize; idx++) {
                    objs[idx] = rs.getObject(idx + 1);
                }
                rules.add(objs);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return rules;
    }

    public List<Object[]> getModelConfig() {

        List<Object[]> rules = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            if (StringUtils.isEmpty(model_config_sql)) {
                return null;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;

            int arrsize = 18;

            rules = new LinkedList<Object[]>();
            s = conn.createStatement();
            rs = s.executeQuery(model_config_sql);
            while (rs.next()) {
                Object[] objs = new Object[arrsize];
                for (int idx = 0; idx < 3; idx++) {
                    objs[idx] = rs.getString(idx + 1);
                }
                for (int idx = 3; idx < arrsize - 1; idx++) {
                    objs[idx] = rs.getDouble(idx + 1);
                }
                objs[arrsize - 1] = rs.getString(arrsize - 1);
                rules.add(objs);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return rules;
    }

    /**
     * 获取算法参数配置数据
     *
     * @return
     */
    public Map<String, String> getModelTraitConfig() {

        Map<String, String> modeltrait = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            if (StringUtils.isEmpty(model_trait_config_sql)) {
                return null;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;
            modeltrait = new HashMap<>();
            s = conn.createStatement();
            rs = s.executeQuery(model_trait_config_sql);
            while (rs.next()) {
                modeltrait.put(rs.getString(1), rs.getString(2));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return modeltrait;
    }

    public List<Object[]> getModelFuelConfig() {

        List<Object[]> rules = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            if (StringUtils.isEmpty(model_fuel_config_sql)) {
                return null;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;

            int arrsize = 22;

            rules = new LinkedList<Object[]>();
            s = conn.createStatement();
            rs = s.executeQuery(model_fuel_config_sql);
            while (rs.next()) {
                Object[] objs = new Object[arrsize];
                for (int idx = 0; idx < 3; idx++) {
                    objs[idx] = rs.getString(idx + 1);
                }
                for (int idx = 3; idx < arrsize - 1; idx++) {
                    objs[idx] = rs.getDouble(idx + 1);
                }
                objs[arrsize - 1] = rs.getString(arrsize - 1);
                rules.add(objs);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return rules;
    }

    public List<Object[]> getModelDefinedConfig() {

        List<Object[]> rules = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            if (StringUtils.isEmpty(model_defined_config_sql)) {
                return null;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;

            int arrsize = 5;

            rules = new LinkedList<Object[]>();
            s = conn.createStatement();
            rs = s.executeQuery(model_defined_config_sql);
            while (rs.next()) {
                Object[] objs = new Object[arrsize];
                for (int idx = 0; idx < arrsize; idx++) {
                    objs[idx] = rs.getString(idx + 1);
                }
                rules.add(objs);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return rules;
    }

    public void batchExecutes(String sql, List<Object[]> params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if (StringUtils.isEmpty(sql)) {
                return;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null != conn) {

                ps = conn.prepareStatement(sql);
                int len = params.size();
                for (int idx = 0; idx < len; idx++) {
                    Object[] param = params.get(idx);

                    if (null != param && param.length > 0) {

                        for (int i = 0; i < param.length; i++) {
                            Object value = param[i];
                            if (value instanceof Integer) {
                                ps.setInt(i, (int) value);
                            } else if (value instanceof Double) {
                                ps.setDouble(i, (double) value);
                            } else if (value instanceof Float) {
                                ps.setFloat(i, (float) value);
                            } else if (value instanceof Date) {
                                ps.setDate(i, (Date) value);
                            } else {
                                ps.setString(i, value + "");
                            }
                        }
                        ps.addBatch();
                    }
                    if (idx % 50 == 0) {
                        ps.executeBatch();
                        ps.clearBatch();
                    }
                }
                ps.executeBatch();

            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(ps, conn);
        }
    }

    public void updateOrAddExecutes(String sql, Object[] param) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if (StringUtils.isEmpty(sql)) {
                return;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null != conn) {

                ps = conn.prepareStatement(sql);

                if (null != param && param.length > 0) {

                    for (int dx = 0; dx < param.length; dx++) {
                        Object value = param[dx];
                        int idx = dx + 1;
                        if (NumberUtils.stringIsNumber("" + value)) {
                            if (value instanceof Integer) {
                                ps.setInt(idx, (int) value);
                            } else if (value instanceof Double) {
                                ps.setDouble(idx, (double) value);
                            } else if (value instanceof Float) {
                                ps.setFloat(idx, (float) value);
                            } else {
                                ps.setString(idx, value + "");
                            }
                        } else if (value instanceof Date) {
                            ps.setDate(idx, (Date) value);
                        } else {
                            ps.setString(idx, value + "");
                        }
                    }
                }

                ps.executeUpdate();

            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(ps, conn);
        }
    }

    private List<String[]> getStringArrList(String sql, int arrsize) {
        List<String[]> rules = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            if (StringUtils.isEmpty(sql)) {
                return null;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;
            rules = new LinkedList<String[]>();
            s = conn.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                String[] strings = new String[arrsize];
                for (int idx = 0; idx < arrsize; idx++) {
                    strings[idx] = rs.getString(idx + 1);
                }
                rules.add(strings);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return rules;
    }


    public List<String[]> selectBySql(String sql, int arrsize) {
        List<String[]> rules = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            if (StringUtils.isEmpty(sql)) {
                return null;
            }
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;
            rules = new LinkedList<String[]>();
            s = conn.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                String[] strings = new String[arrsize];
                for (int idx = 0; idx < arrsize; idx++) {
                    strings[idx] = rs.getString(idx + 1);
                }
                rules.add(strings);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return rules;
    }

    //返回String数组中元素是 :[VID,MODEL_NOTICE_ID(公告号),CONFIGURATION_NUM(配置号)]
    private String stringNumber(String str) {
        if (!StringUtils.isEmpty(str)
                && (str.matches("[-]?[0-9]+")
                || str.matches("[-]?[0-9]+\\.[0-9]{0,10}"))) {
            return str;
        }
        return "-1";
    }

    public List<Map<String, Object>> get(String sql, String[] filedName) {
        List<Map<String, Object>> list = null;
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            if (null == conn || conn.isClosed())
                conn = getConn();
            if (null == conn)
                return null;
            if (null == filedName || filedName.length < 1)
                return list;
            list = new LinkedList<Map<String, Object>>();
            s = conn.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                Map<String, Object> map = new TreeMap<String, Object>();
                for (int i = 0; i < filedName.length; i++) {
                    if (null != filedName[i] && !"".equals(filedName[i].trim()))
                        map.put(filedName[i], rs.getObject(i + 1));
                }
                list.add(map);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(rs, s, conn);
        }
        return list;
    }


    boolean isNullOrEmpty(String string) {
        if (null == string || "".equals(string))
            return true;
        return "".equals(string.trim());
    }

    private void close(AutoCloseable... ables) {
        if (null != ables && ables.length > 0) {
            for (AutoCloseable able : ables) {
                if (null != able) {
                    try {
                        able.close();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Conn conn = new Conn();
    }
}
