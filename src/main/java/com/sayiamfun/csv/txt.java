package com.sayiamfun.csv;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class txt {

    public static void main(String[] args) {
        File file = new File("D:\\文档资料\\预警\\yq_out_LZ90GWDV4H2001558\\yq_out_LZ90GWDV4H2001558.txt");
        try {
            List<String> lines = FileUtils.readLines(file, "utf8");
            for (int i = 0; i < 3; i++) {
                System.out.println(lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
