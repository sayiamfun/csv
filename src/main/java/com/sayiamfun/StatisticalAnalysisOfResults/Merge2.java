package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.Constant;
import com.spire.doc.*;

import java.io.File;

/**
 * 合并word文档
 */
public class Merge2 {

    public static void merge(String outPath, String vin) {
        Document doc0 = null;
        Document doc1 = null;
        Document doc2 = null;
        Document doc3 = null;
        Document doc4 = null;
        Document doc5 = null;
        Document doc6 = null;
        Document doc7 = null;
        Document doc8 = null;
        Document doc9 = null;
        String file0 = Constant.templatePath3;
        String fil1 = outPath + "/" + vin + "EARate.docx";
        String fil2 = outPath + "/" + vin + "EENum.docx";
        String fil3 = outPath + "/" + vin + "EWRate.docx";
        String fil4 = outPath + "/" + vin + "PARate.docx";
        String fil5 = outPath + "/" + vin + "PENum.docx";
        String fil6 = outPath + "/" + vin + "PWRate.docx";
        String fil7 = outPath + "/" + vin + "VARate.docx";
        String fil8 = outPath + "/" + vin + "VENum.docx";
        String fil9 = outPath + "/" + vin + "VWRate.docx";
        //加载需要合并的两个文档
        //加载需要合并的两个文档
        doc0 = new Document(file0);
        Section lastsec = doc0.getLastSection();
        if(new File(fil1).exists()) {
            doc1 = new Document(fil1);
            for (Section section : (Iterable<Section>) doc1.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil2).exists()) {
            doc2 = new Document(fil2);
            //获取文档1的最后一节
            //遍历文档2的所有段落内容，添加到文档1
            for (Section section : (Iterable<Section>) doc2.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil3).exists()) {
            doc3 = new Document(fil3);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc3.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil4).exists()) {
            doc4 = new Document(fil4);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc4.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil5).exists()) {
            doc5 = new Document(fil5);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc5.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil6).exists()) {
            doc6 = new Document(fil6);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc6.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil7).exists()) {
            doc7 = new Document(fil7);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc7.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil8).exists()) {
            doc8 = new Document(fil8);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc8.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if(new File(fil9).exists()) {
            doc9 = new Document(fil9);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc9.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }

        doc0.replace("推荐使用VIP服务器，高速、稳定、无广告！", "", false, true);

        //保存合并后的文档
        doc0.saveToFile(outPath + "/0_" + vin + "_out.docx", FileFormat.Docx_2010);
    }

}
