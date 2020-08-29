package com.sayiamfun.otherfiletomodelinputfile;

import com.sayiamfun.common.Constant;
import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileToModelFile {

    public static void main(String[] args) {

        String title = "rechargeablestoragedevicemismatchalarm\tishistorypoi\tvehiclepuredevicetypeovercharge\tmaxvoltagebattery\tdcdctemperaturealarm\tisvalidgps\tlastupdatedtime\tdrivemotorcontrollertemperaturealarm\tnevchargesystemtemperaturedtolist\tprobetemperatures\tchargetemperatureprobenum\tignitecumulativemileage\tdcstatus\trepay\tmaxtemperaturesubsystemnum\tcarstatus\tminvoltagebatterysubsystemnum\theading\tdrivemotorfaultcount\tbattcoolngloopsts\tbattcoolactv\tmotorouttemperature\tpowerstatusfeedback\tac_reardefrosterswitch\trearfoglamp\tdriverdoorlock\tacdriverreqtemp\tkeyalarm\taircleanstsremind\trecycletype\tstartcontrolsignal\tairbagwarninglamp\tfrontdefrosterswitch\tfrontblowtype\tfrontreqwindlevel\tbcmfrontwiperstatus\ttmspwract\tgeardriveforce\tbatteryconsistencydifferencealarm\tsoc\tsocjumpalarm\tcaterpillaringfunction\tsatnum\tsoclowalarm\tcharginggunconnectionstate\tmintemperaturesubsystemnum\tchargedelectroniclockstatus\tmaxvoltagebatterynum\tterminaltime\tsinglebatteryovervoltagealarm\totherfaultcount\tvehiclestoragedeviceovervoltagealarm\tbrakesystemalarm\tservertime\tvin\ttuid\tenergyrecoverystatus\tfirestatus\ttargettype\tmaxtemperatureprobe\trechargeablestoragedevicesfaultcodes\tcarmode\thighvoltagebiginterlockstatealarm\tinsulationalarm\tmileageinformation\tmaxtemperaturevalue\totherfaultcodes\tremainpower\tinsulateresistance\tbatterylowtemperatureheater\tfuelconsumption100km\tfuelconsumption\tenginespeed\tenginestatus\tkeyundetectedalarmsign\tpositionlamp\tdriverreqtempmodel\tturnlightswitchsts\tautoheadlightstatus\tdriverdoor\tipufaultcodes\tfrntipufltrnk\tfrontipuswvers\tfrontipuhwvers\tfrntmottqlongtermmax\tfrntmottqlongtermmin\tcpvvalue\tobcchrgsts\tobcfltrnk\tobcfaultcode\tobcchrginpaci\tobcchrginpacu\tobcchrgdci\tnum\tcontrollerdcbuscurrent\ttemperature\ttorque\tstate\tminvoltagebatterynum\tvalidgps\tenginefaultcodes\tmbigintemperaturevalue\tchargestatus\tignitiontime\ttotalodometer\talti\tspeed\tsochighalarm\tvehiclestoragedeviceundervoltagealarm\ttotalcurrent\tbatteryalarm\tespfunctionstatus\tesp_tcsfailstatus\thhcactive\ttcsactive\tespmastercylinderbrakepressure\tesp_mastercylinderbrakepressurevalid\tesptorqsensorstatus\teps_epsfailed\tsasfailure\tsassteeringanglespeed\tsassteeringangle\tsassteeringanglevalid\tespsteeringtorque\tacreq\tacsystemfailure\tptcpwract\tplasmastatus\tbattintemperature\tbattwarmloopsts\tenginefaultcount\tcarid\tcurrentelectricity\tsinglebatteryundervoltagealarm\tmaxvoltagebatterysubsystemnum\tmintemperatureprobe\tdrivemotornum\ttotalvoltage\ttemperaturedifferencealarm\tmaxalarmlevel\tstatus\tgeerposition\taverageenergyconsumption\tminvoltagebattery\tgeerstatus\tdrivemotordata\tcontrollerinputvoltage\tcontrollertemperature\trevolutionspeed\tbattintrheatreq\tbcubatttart\tbattextheatreq\tbcumaxchrgpwrlongt\tbcumaxdchapwrlongt\tbcutotalregenengdisp\tbcutotalregencpdisp\tdcdcfltrnk\tdcdcfaultcode\tdcdcoutpcrrt\tdcdcoutpu\tdcdcavloutppwr\tabsactivestatus\tabsstatus\tvcubrkerr\tepb_achievedclampforce\tepbswitchposition\tepbstatus\tespactivestatus\tshiftpositionvalid\taccpedalvalid\tdrivemode\tdrivemodebuttonstatus\tvcusrscrashoutpsts\ttextdispena\tcrsctrlstatus\tcrstarspd\tcrstextdisp\tkeyon\tvehpwrlim\tvehcfginfo\tvacbrkprmu\trechargeablestoragedevicesfaultcount\tdrivemotortemperaturealarm\tgearbrakeforce\tdcdcstatusalarm\tlat\tdrivemotorfaultcodes\tdevicetype\tvehiclespeed\tlng\tchargingtimeextensionreason\tnevchargesystemvoltagedtolist\tcurrentbatterystartnum\tbatteryvoltage\tchargesystemvoltage\tcurrentbatterycount\tbatterycount\tchildsystemnum\tchargesystemcurrent\tgpstime\ttrunk\tlowbeam\ttriggerlatchoverheatprotect\tturnlndicatorright\thighbeam\tturnlndicatorleft\tbcuswvers\tbcuhwvers\tbcuopermod\tchrgendreason\tbcuregenengdisp\tbcuregencpdisp\tbcuchrgmod\tbatterychargestatus\tbcufaultcodes\tbcufltrnk\tbattpoletover\tbcusoh\tbattintrheatactive\tobcchrgdcu\tobctemperature\tobcmaxchrgoutppwravl\tpassengerbuckleswitch\tcrashlfo\tdriverbuckleswitch\tenginestarthibit\tlockcommand\tsearchcarreq\tactempvaluereq\tvcufaultcode\tvcuerramnt\tvcuswvers\tvcuhwvers\tlowspdwarnstatus\tlowbattchrgrqe\tlowbattchrgsts\tlowbattu\thandlebrakestatus";
        List<String> titleList = Arrays.asList(title.split("\t"));


        String inputPath = "/Users/liwenjie/Downloads/长安14辆车报文（准备模型数据分析）/CANEV To BIT车辆数据/EV460";
        String outPath = "/Users/liwenjie/Downloads/长安14辆车报文（准备模型数据分析）/CANEV To BIT车辆数据/EV460_out";

        if (!new File(outPath).exists()) {
            boolean mkdirs = new File(outPath).mkdirs();
            if (!mkdirs) System.out.println("mkdir directory fail!");
        }

        ArrayList<String> strings = ScanPackage.scanFiles(inputPath);
        for (String string : strings) {
            System.out.println("start do file :" + string);
            InputStreamReader ir = null;
            BufferedReader reader = null;

            OutputStreamWriter ow = null;
            BufferedWriter bw = null;

            try {
                ir = new InputStreamReader(new FileInputStream(new File(string)), ScanPackage.encode);
                reader = new BufferedReader(ir);//到读取的文件

                ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + "/" + string.substring(string.lastIndexOf("/"))), true), ScanPackage.encode);
                bw = new BufferedWriter(ow);

                String line = reader.readLine();
                int i = 0;
                StringBuilder stringBuilder;
                while ((line = reader.readLine()) != null) {
                    stringBuilder = new StringBuilder(200);
                    String[] item = line.split("\t");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    stringBuilder.append("MESSAGETYPE:REALTIME").append(",")
                            .append(Constant.VIN).append(":").append(item[titleList.indexOf("vin")]).append(",")
                            .append(Constant.CAR_STATUS).append(":").append(item[titleList.indexOf("status")]).append(",")
                            .append(Constant.CHARGE_STATUS).append(":").append(item[titleList.indexOf("chargestatus")]).append(",")
                            .append(Constant.TIME).append(":").append(getStringTime(item[titleList.indexOf("terminaltime")])).append(",")
                            .append(Constant.MESSAGETIME).append(":").append(getStringTime(item[titleList.indexOf("terminaltime")])).append(",")
                            .append(Constant.SPEED).append(":").append(getStringTime(item[titleList.indexOf("speed")])).append(",")
                            .append(Constant.SOC).append(":").append(getStringTime(item[titleList.indexOf("soc")])).append(",")
                            .append(Constant.MILS).append(":").append(getStringTime(item[titleList.indexOf("totalodometer")])).append(",")
                            .append(Constant.TOTAL_V).append(":").append(getStringTime(item[titleList.indexOf("totalvoltage")])).append(",")
                            .append(Constant.TOTAL_I).append(":").append(getStringTime(item[titleList.indexOf("totalcurrent")])).append(",")
                            .append(Constant.V_LIST).append(":").append(getStringTime(item[titleList.indexOf("averageenergyconsumption")].replaceAll("~", "_"))).append(",")
                            .append(Constant.T_LIST).append(":").append(getStringTime(item[titleList.indexOf("probetemperatures")].replaceAll("~", "_"))).append(",")
                            .append(Constant.INSULATIONALARM).append(":").append(getStringTime(item[titleList.indexOf("insulationalarm")]));
//                    System.out.println(stringBuilder.toString());
                    bw.write(stringBuilder.toString());
                    bw.newLine();
                    bw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeFileStream(ir, reader, ow, bw);
            }
        }
    }

    private static String getStringTime(String terminaltime) {
        if (terminaltime == null || "".equals(terminaltime)) return "";
        return terminaltime.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
    }

    /**
     * 关闭文件链接
     *
     * @param ir     读取文件流
     * @param reader 读取文件流
     * @param ow     输出文件流
     * @param bw     输出文件流
     */
    public static void closeFileStream(InputStreamReader ir, BufferedReader reader, OutputStreamWriter ow, BufferedWriter bw) {
        try {
            if (null != reader) reader.close();
            if (null != ir) ir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (null != bw) bw.close();
            if (null != ow) ow.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
