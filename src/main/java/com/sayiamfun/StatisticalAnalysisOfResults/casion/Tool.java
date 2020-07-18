package com.sayiamfun.StatisticalAnalysisOfResults.casion;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Tool {

    private static Logger logger = LoggerFactory.getLogger(Tool.class);

    public static void ModifyColor(String dataDir, String filename, int i, String color) {

        String inpath = dataDir + filename;
        String outpath = dataDir + filename + "modify.xml";

        try {
            WordprocessingMLPackage wmlPackage = Docx4J.load(new File(inpath));
            Docx4J.save(wmlPackage, new File(outpath), Docx4J.FLAG_SAVE_FLAT_XML);
            insertXml(outpath, i, color);
        } catch (Throwable e) {
            logger.error("WordprocessingMLPackage wmlPackage = Docx4J.load(new File(inpath));   Exception !");
        }
        System.out.println("Saved: " + outpath);
    }

    public static void insertXml(String filepath, int idx, String color) throws IOException {

        File filestrSrc = new File(filepath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));

        StringBuilder result = new StringBuilder();
        String theLine = null;
        while ((theLine = reader.readLine()) != null) {
            result.append(theLine + "\n");
        }


        /*����λ��*/
        int begin = 0;
        String sub = "<c:ser>";
        for (int i = 0; i < idx; i++) {
            int index = result.indexOf(sub, begin);
            begin = index + sub.length();
            System.out.println(index);
        }


        sub = "</c:tx>";
        int index1 = result.indexOf(sub, begin);
        System.out.println(index1);


        String subxml = "";
        subxml = "<c:spPr>"
                + "<a:solidFill>"
                + "<a:srgbClr val=" + "'" + color + "'" + " />"
                + "</a:solidFill>"
                + "</c:spPr>";

        result.insert(index1 + sub.length(), subxml);


        //��utf-8��д��xml�ĵ���
        FileOutputStream fos = new FileOutputStream(filepath + "xiugai.xml");
        OutputStreamWriter bw = new OutputStreamWriter(fos, "UTF-8");
        bw.write(result.toString());
        System.out.println("Tool.insertXml()");
        reader.close();
        bw.close();
    }

}
