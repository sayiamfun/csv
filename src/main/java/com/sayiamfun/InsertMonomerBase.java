package com.sayiamfun;

/**
 * 过滤单体插入数据库
 */
public class InsertMonomerBase {

    public static void main(String[] args) {
        String s = "LB378Y4W7JA178042," +
                "LB378Y4WXJA179539," +
                "LB378Y4W9JA177443," +
                "LB378Y4W7JA173018," +
                "LB378Y4W6JA173771," +
                "LB378Y4W6JA173673," +
                "LB378Y4W8JA174033," +
                "LB378Y4W0JA177671," +
                "LB378Y4W8JA173853," +
                "LB378Y4W8JA172671," +
                "LB378Y4W3JA173680," +
                "LB378Y4W2JA175582," +
                "LB378Y4W0JA174477," +
                "LB378Y4WXJA172994," +
                "LB378Y4W9JA172307," +
                "LB378Y4WXJA173059," +
                "LB378Y4W9JA177121," +
                "LB378Y4W2JA173637," +
                "LB378Y4W9JA174607," +
                "LB378Y4W9JA174669," +
                "LB378Y4W7JA173116," +
                "LB378Y4W0JA174060," +
                "LB378Y4W5JA172711," +
                "LB378Y4W1JA176593," +
                "LB378Y4W7JA174007," +
                "LB378Y4W4JA173655," +
                "LB378Y4W2JA173492," +
                "LB378Y4W6JA172846," +
                "LB378Y4W1JA174035," +
                "LB378Y4W9JA173201," +
                "LB378Y4W1JA176352," +
                "LB378Y4W2JA179633," +
                "LB378Y4W7JA173018," +
                "LB378Y4W1JA173340," +
                "LB378Y4W5JA176273," +
                "LB378Y4W9JA178043," +
                "LB378Y4W5JA179478," +
                "LB378Y4W0JA176455," +
                "LB378Y4W8JA173416," +
                "LB378Y4W7JA178574," +
                "LB378Y4W7JA177795," +
                "LB378Y4W0JA172454," +
                "LB378Y4W2JA179292," +
                "LB378Y4W2JA175906," +
                "LB378Y4W6JA177836," +
                "LB378Y4WXJA173529," +
                "LB378Y4W7JA176064," +
                "LB378Y4W0JA176469," +
                "LB378Y4W7JA172399," +
                "LB378Y4W4JA176362," +
                "LB378Y4W5JA172899," +
                "LB378Y4W4JA179407," +
                "LB378Y4W1JA179395," +
                "LB378Y4WXJA177614," +
                "LB378Y4W1JA176478," +
                "LB378Y4W3JA174439," +
                "LB378Y4W5JA173146," +
                "LB378Y4WXJA173076," +
                "LB378Y4W0JA173443," +
                "LB378Y4W2JA173394," +
                "LB378Y4W5JA173020," +
                "LB378Y4W0JA175404," +
                "LB378Y4W2JA173329," +
                "LB378Y4W2JA176649," +
                "LB378Y4W1JA173158," +
                "LB378Y4W4JA173509," +
                "LB378Y4W9JA173361," +
                "LB378Y4W4JA173185," +
                "LB378Y4W5JA176211," +
                "LB378Y4W3JA174182," +
                "LB378Y4W2JA173301," +
                "LB378Y4W2JA175002," +
                "LB378Y4W7JA173679," +
                "LB378Y4W9JA174364," +
                "LB378Y4W3JA175428," +
                "LB378Y4W4JA174370," +
                "LB378Y4W1JA175542," +
                "LB378Y4W9JA175448," +
                "LB378Y4W5JA173051," +
                "LB378Y4W3JA176417," +
                "LB378Y4W4JA175910," +
                "LB378Y4W9JA176292," +
                "LB378Y4W7JA172452," +
                "LB378Y4W3JA176627," +
                "LB378Y4W8JA174971," +
                "LB378Y4W8JA176638," +
                "LB378Y4WXJA176415," +
                "LB378Y4W5JA176516," +
                "LB378Y4W3JA176482," +
                "LB378Y4W3JA176644," +
                "LB378Y4W1JA176531," +
                "LB378Y4W9JA176552," +
                "LB378Y4W1JA180093," +
                "LB378Y4W9JA179662," +
                "LB378Y4W1JA174066," +
                "LB378Y4WXJA176401," +
                "LB378Y4WXJA176589," +
                "LB378Y4W9JA176440," +
                "LB378Y4W6JA176539," +
                "LB378Y4W9JA176521," +
                "LB378Y4W0JA173281," +
                "LB378Y4W2JA176554," +
                "LB378Y4W3JA176479," +
                "LB378Y4W4JA175342";
        String[] split = s.split(",");
        for (String s1 : split) {
            System.out.println("insert into monomer_base (vin,veh_model_name,veh_config_name,monomer) values ('" + s1 + "','" + s1 + "','" + s1 + "','6,17,29,41,53,65,76,86,91');");
        }
    }


}
