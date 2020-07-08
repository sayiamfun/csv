package com.sayiamfun.csv;

public class ProtocolItem extends SysDefine {
    private static final long serialVersionUID = 19990110000201L;

    //以下是报文类型数据项
    /**
     * 指令类型 实时信息上报
     */
    public static final String REALTIME = "REALTIME";
    /**
     * 指令类型 状态信息上报
     */
    public static final String TERMSTATUS = "TERMSTATUS";
    /**
     * 指令类型 补发信息上报
     */
    public static final String HISTORYDATA = "HISTORY";
    /**
     * 指令类型 车辆运行状态
     */
    public static final String CARSTATUS = "CARSTATUS";
    /**
     * 指令类型 租赁点更新数据
     */
    public static final String RENTALSTATION = "RENTALSTATION";
    /**
     * 指令类型 租赁点更新数据
     */
    public static final String CHARGESTATION = "CHARGESTATION";
    /**
     * 指令类型 租赁数据
     */
    public static final String RENTCAR = "RENTCAR";
    /**
     * 指令类型 充电设施数据
     */
    public static final String CHARGE = "CHARGE";

    public static final String VID = "VID";
    public final static String VIN = "VIN";// 车辆VIN
    public static final String TIME = "TIME";
    public static final String LOGIN = "LOGIN";
    public static final String MESSAGETYPE = "MESSAGETYPE";
    public static final String MSG_TIME = "2000";//实时报文消息时间
    public static final String ALARM_STATUS = "2920"; //最高报警等级值
    public static final String CHARGE_STATUS = "2301"; //充电状态
    public static final String SPEED = "2201"; //车速
    public static final String TOTAL_MILEAGE = "2202";//里程
    public static final String longitude = "2502"; //经度
    public static final String latitude = "2503"; //纬度
    public static final String orientation = "2501"; //定位状态
    public static final String LINK_TYPE = "TYPE"; // 车辆链接状态 TYPE：1上线，2心跳，3离线
    public static final String ICCID = "ICCID";//新的报文定义 给web 字段为 ICCID
    public static final String ICCID_ITEM = "1021";//老的 iccid 内部协议数据项是 1021
    public static final String REG_TYPE = "TYPE"; //平台注册通知类型 0:从未上过线，1:车机终端上线 ，2:车机离线，3:平台上线，4:平台下线
    public static final String REG_STATUS = "STATUS";//0,1
    public static final String PLAT_ID = "PLATID";
    public static final String SEQ_ID = "SEQID";
    public static final String USERNAME = "USERNAME";
    //public static final String PASS_WORD ="PASSWORD";
    public static final String GEARS = "2203";//gears 挡位
    public static final String DRIVING_FORCE = "2205";//driving force 驱动力
    public static final String BRAKING_FORCE = "2204";//braking force 制动力
    public static final String ACCELERATOR_PEDAL = "2208";//accelerator pedal 加速踏板
    public static final String BRAKING_PEDAL = "2209";//accelerator pedal 制动踏板
    public static final String TOTAL_VOLT = "2613";// 总电压
    public static final String TOTAL_ELE = "2614";// 总电流
    public static final String DRIVING_ELE_MAC_LIST = "2308";//driving 驱动电机列表
    public static final String DRIVING_ELE_MAC_SEQ = "2309";//driving 驱动电机序号
    public static final String DRIVING_ELE_MAC_STATUS = "2310";//driving 驱动电机状态
    public static final String DRIVING_ELE_MAC_TEMPCTOL = "2302";//driving 驱动电机控制器温度
    public static final String DRIVING_ELE_MAC_REV = "2303";//driving 驱动电机转速
    public static final String DRIVING_ELE_MAC_TORQUE = "2311";//driving 驱动电机转矩
    public static final String DRIVING_ELE_MAC_TEMP = "2304";//driving 驱动电机温度
    public static final String DRIVING_ELE_MAC_VOLT = "2305";//driving 电机控制器输入电压
    public static final String DRIVING_ELE_MAC_ELE = "2306";//driving 驱动电机母线电流
    public static final String INSULATION_RESISTANCE = "2617";//insulation resistance 绝缘电阻
    public static final String SOC = "2615";//SOC 电池剩余电量百分比
    public static final String SOC_HIGH_ALARM = "2909";//SOC 过高告警
    public static final String RUNNING_MODE = "2213";//SOC 运行模式
    public static final String DCDC_STATUS = "2214";//DCDC 状态
    public static final String SINGLE_VOLT_HIGH_VAL = "2603";//单体电压最高值
    public static final String HIGHTEMP_CHILD = "2607";//最高温度子系统号
    public static final String HIGHTEMP_NUM = "2608";//最高温度探针序号
    public static final String HIGHTEMP_VAL = "2609";//最高温度值

    public static final String LOWTEMP_CHILD = "2610";//最低温度子系统号
    public static final String LOWTEMP_NUM = "2611";//最低温度探针序号
    public static final String LOWTEMP_VAL = "2612";//最低温度值
    public static final String SINGLE_TEMP = "2103";//单体温度
    public static final String SINGLE_VOLT_LOW_VAL = "2606";//单体电压最低值
    //    public static final String SINGLE_VOLT_TOTALNUM="2001";//单体电压单体总数
//    public static final String SINGLE_CHILD_TOTALNUM="2002";//单体电池包总数
    public static final String SINGLE_VOLT = "2003";// 单体电压
    public static final String SINGLE_VOLT_TOTALNUM = "7001";//单体电压单体总数
    public static final String SINGLE_CHILD_TOTALNUM = "7002";//单体电池包总数（电池子系统总数）
    public static final String SINGLE_VOLT_ORIG = "7003";// 单体电压原始报文
    public static final String ALARM_MARK = "3801";//通用报警标志值

    public static final String CAR_STATUS = "3201";//车辆状态
    public static final String HIGHVOLT_CHILD_NUM = "2601";//最高电压电池子系统号
    public static final String HIGHVOLT_SINGLE_NUM = "2602";//最高电压电池单体代号
    public static final String LOWVOLT_CHILD_NUM = "2604";//最低电压电池子系统号
    public static final String LOWVOLT_SINGLE_NUM = "2605";//最低电压电池单体代号
    public static final String ENGINES = "2401";//发动机状态
    public static final String CTYPE = "CTYPE";//CTYPE 区别终端直连 还是平台转发 1_1_1

    //    public static final String TOTAL_TEMP_NUM="2101";//可充电储能温度探针个数
//    public static final String TEMP_PACKET_NUM="2102";//可充电温度电池包总数
    public static final String TOTAL_TEMP_NUM = "7101";//可充电储能温度探针个数
    public static final String TEMP_PACKET_NUM = "7102";//可充电温度电池包总数(探针子系统总数)
    public static final String SINGLE_TEMP_ORGI = "7103";//单体温度原始报文

    public static final String LOGIN_TIME = "1025";//登入时间
    public static final String LOGOUT_TIME = "1031";//登出时间
    public static final String LOGIN_SEQ = "1020";//登入流水号
    public static final String LOGOUT_SEQ = "1033";//登出流水号
    public static final String LOCATION = "LOCATION";//GPS 经度，纬度

    public static final String SERVER_RECEIVE_TIME = "9999";//SERVER_TIME服务器接收时间

    public static final String ITEM_PREFIX = "N_";//问题数据项前缀
    public static final String ERROR_CODE = "ERR";//报文错误码

    public static final String SINGLE_VOLTS_LIST = "SINGLE_VOLTS_LIST";//单体电压列表，多个单体电压包用;隔开
    public static final String SINGLE_TEMPS_LIST = "SINGLE_TEMPS_LIST";//单体温度列表，多个单体温度包用;隔开

    public static final String FUEL_TOTAL_VOLT = "2110"; //燃料电池电压
    public static final String FUEL_TOTAL_ELEC = "2111"; //燃料电池电流
    public static final String FUEL_CONSU_RATE = "2112"; //燃料消耗率
    public static final String FUEL_TEMPS_NUM = "2113"; //燃料电池温度探针总数
    public static final String FUEL_TEMPS_LIST = "2114"; //燃料电池温度值，30_31_29_22_35_...
    public static final String FUEL_HYDROGEN_HIGH_TEMPS_VALUE = "2115"; //氢系统中最高温度
    public static final String FUEL_HYDROGEN_HIGH_TEMPS_NUM = "2116"; //氢系统中 最高温度探针号
    public static final String FUEL_HYDROGEN_CONC = "2117"; //氢气最高浓度
    public static final String FUEL_HYDROGEN_CONC_NUM = "2118"; //氢气 最高浓度传感器代号
    public static final String FUEL_HYDROGEN_STRESS = "2119"; //氢气最高压力
    public static final String FUEL_HYDROGEN_STRESS_NUM = "2120"; //氢气最高压力传感器代号
    public static final String FUEL_HIGH_DCDCSTATUS = "2121"; //高压DC-DC状态

}
