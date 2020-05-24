package com.sayiamfun.file20200414;

public class VinSql {

    public static void main(String[] args) {
        String s = "LB378Y4W7JA178042,\n" +
                "LB378Y4WXJA179539,\n" +
                "LB378Y4W9JA177443,\n" +
                "LB378Y4W7JA173018,\n" +
                "LB378Y4W6JA173771,\n" +
                "LB378Y4W6JA173673,\n" +
                "LB378Y4W8JA174033,\n" +
                "LB378Y4W0JA177671,\n" +
                "LB378Y4W8JA173853,\n" +
                "LB378Y4W8JA172671,\n" +
                "LB378Y4W3JA173680,\n" +
                "LB378Y4W2JA175582,\n" +
                "LB378Y4W0JA174477,\n" +
                "LB378Y4WXJA172994,\n" +
                "LB378Y4W9JA172307,\n" +
                "LB378Y4WXJA173059,\n" +
                "LB378Y4W9JA177121,\n" +
                "LB378Y4W2JA173637,\n" +
                "LB378Y4W9JA174607,\n" +
                "LB378Y4W9JA174669,\n" +
                "LB378Y4W7JA173116,\n" +
                "LB378Y4W0JA174060,\n" +
                "LB378Y4W5JA172711,\n" +
                "LB378Y4W1JA176593,\n" +
                "LB378Y4W7JA174007,\n" +
                "LB378Y4W4JA173655,\n" +
                "LB378Y4W2JA173492,\n" +
                "LB378Y4W6JA172846,\n" +
                "LB378Y4W1JA174035,\n" +
                "LB378Y4W9JA173201,\n" +
                "LB378Y4W1JA176352,\n" +
                "LB378Y4W2JA179633,\n" +
                "LB378Y4W7JA173018,\n" +
                "LB378Y4W1JA173340,\n" +
                "LB378Y4W5JA176273,\n" +
                "LB378Y4W9JA178043,\n" +
                "LB378Y4W5JA179478,\n" +
                "LB378Y4W0JA176455,\n" +
                "LB378Y4W8JA173416,\n" +
                "LB378Y4W7JA178574,\n" +
                "LB378Y4W7JA177795,\n" +
                "LB378Y4W0JA172454,\n" +
                "LB378Y4W2JA179292,\n" +
                "LB378Y4W2JA175906,\n" +
                "LB378Y4W6JA177836,\n" +
                "LB378Y4WXJA173529,\n" +
                "LB378Y4W7JA176064,\n" +
                "LB378Y4W0JA176469,\n" +
                "LB378Y4W7JA172399,\n" +
                "LB378Y4W4JA176362,\n" +
                "LB378Y4W5JA172899,\n" +
                "LB378Y4W4JA179407,\n" +
                "LB378Y4W1JA179395,\n" +
                "LB378Y4WXJA177614,\n" +
                "LB378Y4W1JA176478,\n" +
                "LB378Y4W3JA174439,\n" +
                "LB378Y4W5JA173146,\n" +
                "LB378Y4WXJA173076,\n" +
                "LB378Y4W0JA173443,\n" +
                "LB378Y4W2JA173394,\n" +
                "LB378Y4W5JA173020,\n" +
                "LB378Y4W0JA175404,\n" +
                "LB378Y4W2JA173329,\n" +
                "LB378Y4W2JA176649,\n" +
                "LB378Y4W1JA173158,\n" +
                "LB378Y4W4JA173509,\n" +
                "LB378Y4W9JA173361,\n" +
                "LB378Y4W4JA173185,\n" +
                "LB378Y4W5JA176211,\n" +
                "LB378Y4W3JA174182,\n" +
                "LB378Y4W2JA173301,\n" +
                "LB378Y4W2JA175002,\n" +
                "LB378Y4W7JA173679,\n" +
                "LB378Y4W9JA174364,\n" +
                "LB378Y4W3JA175428,\n" +
                "LB378Y4W4JA174370,\n" +
                "LB378Y4W1JA175542,\n" +
                "LB378Y4W9JA175448,\n" +
                "LB378Y4W5JA173051,\n" +
                "LB378Y4W3JA176417,\n" +
                "LB378Y4W4JA175910,\n" +
                "LB378Y4W9JA176292,\n" +
                "LB378Y4W7JA172452,\n" +
                "LB378Y4W3JA176627,\n" +
                "LB378Y4W8JA174971,\n" +
                "LB378Y4W8JA176638,\n" +
                "LB378Y4WXJA176415,\n" +
                "LB378Y4W5JA176516,\n" +
                "LB378Y4W3JA176482,\n" +
                "LB378Y4W3JA176644,\n" +
                "LB378Y4W1JA176531,\n" +
                "LB378Y4W9JA176552,\n" +
                "LB378Y4W1JA180093,\n" +
                "LB378Y4W9JA179662,\n" +
                "LB378Y4W1JA174066,\n" +
                "LB378Y4WXJA176401,\n" +
                "LB378Y4WXJA176589,\n" +
                "LB378Y4W9JA176440,\n" +
                "LB378Y4W6JA176539,\n" +
                "LB378Y4W9JA176521,\n" +
                "LB378Y4W0JA173281,\n" +
                "LB378Y4W2JA176554,\n" +
                "LB378Y4W3JA176479,\n" +
                "LB378Y4W4JA175342";
        String[] split = s.replaceAll("\n","").split(",");
        int i = 23;
        for (String s1 : split) {
            System.out.println("INSERT INTO `monomer_base`VALUES (" + (++i) + ",'" + s1 + "', '" + s1 + "', '" + s1 + "', '6,17,29,41,53,65,76,86,91');");
        }
    }
}
