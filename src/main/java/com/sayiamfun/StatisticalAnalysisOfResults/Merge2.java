package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.Constant;
import com.spire.doc.Document;
import com.spire.doc.DocumentObject;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;

import java.io.File;

/**
 * 合并word文档
 */
public class Merge2 {

    public static void merge(String outPath, String vin, String type, FrequencyStatistics frequencyStatistics) {
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
        String fil1 = outPath  + vin + "_" + type + "EARate.docx";
        String fil2 = outPath  + vin + "_" + type + "EENum.docx";
        String fil3 = outPath  + vin + "_" + type + "EWRate.docx";
        String fil4 = outPath  + vin + "_" + type + "PARate.docx";
        String fil5 = outPath  + vin + "_" + type + "PENum.docx";
        String fil6 = outPath  + vin + "_" + type + "PWRate.docx";
        String fil7 = outPath  + vin + "_" + type + "VARate.docx";
        String fil8 = outPath  + vin + "_" + type + "VENum.docx";
        String fil9 = outPath  + vin + "_" + type + "VWRate.docx";
        //加载需要合并的两个文档
        //加载需要合并的两个文档
        doc0 = new Document(file0);
        Section lastsec = doc0.getLastSection();
        //获取文档1的最后一节
        //遍历文档2的所有段落内容，添加到文档1
        if (new File(fil1).exists()) {
            doc1 = new Document(fil1);
            doc1.replace("***", "熵值故障诊断模型_-全生命周期异常率", false, true);
            for (Section section : (Iterable<Section>) doc1.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil2).exists()) {
            doc2 = new Document(fil2);
            doc2.replace("***", "熵值故障诊断模型每" + frequencyStatistics.getEntropyNums() + "帧次数", false, true);
            for (Section section : (Iterable<Section>) doc2.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil3).exists()) {
            doc3 = new Document(fil3);
            doc3.replace("***", "熵值故障诊断模型-周异常率", false, true);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc3.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil4).exists()) {
            doc4 = new Document(fil4);
            doc4.replace("***", "压降一致性故障诊断模型_-全生命周期异常率", false, true);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc4.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil5).exists()) {
            doc5 = new Document(fil5);
            doc5.replace("***", "压降一致性故障诊断模型每" + frequencyStatistics.getEntropyNums() + "帧次数", false, true);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc5.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil6).exists()) {
            doc6 = new Document(fil6);
            doc6.replace("***", "压降一致性故障诊断模型-周异常率", false, true);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc6.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil7).exists()) {
            doc7 = new Document(fil7);
            doc7.replace("***", "波动一致性故障诊断模型_-全生命周期异常率", false, true);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc7.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil8).exists()) {
            doc8 = new Document(fil8);
            doc8.replace("***", "波动一致性故障诊断模型每" + frequencyStatistics.getEntropyNums() + "帧次数", false, true);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc8.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        if (new File(fil9).exists()) {
            doc9 = new Document(fil9);
            doc9.replace("***", "波动一致性故障诊断模型-周异常率", false, true);
            lastsec = doc0.getLastSection();
            for (Section section : (Iterable<Section>) doc9.getSections()) {
                for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
                ) {
                    lastsec.getBody().getChildObjects().add(obj.deepClone());
                }
            }
        }
        String text = doc0.getText();
        String[] split = text.split("\n");
        for (String s1 : split) {
            if (s1.contains("高速") || s1.contains("稳定") || s1.contains("无广告") || s1.contains("Evaluation"))
                doc0.replace(s1.trim(), "", false, true);
        }
        //保存合并后的文档
        doc0.saveToFile(outPath + "/0_" + vin + "_" + type + "___统计图.docx", FileFormat.Docx_2010);
    }

}
