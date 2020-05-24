package com.sql;

public class Sql {

    public static void main(String[] args) {
        String s = "LB378Y4W6JA173673,LB378Y4WXJA172963,LB378Y4W2JA172908,LB378Y4W2JA173220,LB378Y4W4JA172960,LB378Y4W7JA173441,LB378Y4W6JA175584,LB378Y4W4JA173249,LB378Y4W4JA173333,LB378Y4W8JA173108,LB378Y4W2JA173217,LB378Y4W9JA173201,LB378Y4W1JA173340,LB378Y4W6JA173446,LB378Y4W3JA174795";
        String[] split = s.split(",");

        StringBuilder stringBuilder = new StringBuilder(1000);
        for (String s1 : split) {
            stringBuilder.append("insert into cus_dbs.monomer_base(vin,veh_model_name,veh_config_name,monomer) values('"+s1+"','"+s1+"','"+s1+"','6,17,29,41,53,65,76,86,91');\n");
        }
        System.out.println(stringBuilder.toString());
    }
}
