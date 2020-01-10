package com.sayiamfun.StatisticalAnalysisOfResults;

import com.spire.doc.*;
import com.spire.doc.collections.TextBoxCollection;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.fields.TextBox;
import com.spire.doc.fields.TextRange;

public class Merge2 {

    public static void merge(String outPath) {
        //加载需要合并的两个文档
        Document doc1 = new Document(outPath + "/LDYECS745H0010705EARate.docx");
        Document doc2 = new Document(outPath + "/LDYECS745H0010705EENum.docx");
        Document doc3 = new Document(outPath + "/LDYECS745H0010705EWRate.docx");
        Document doc4 = new Document(outPath + "/LDYECS745H0010705PARate.docx");
        Document doc5 = new Document(outPath + "/LDYECS745H0010705PENum.docx");
        Document doc6 = new Document(outPath + "/LDYECS745H0010705PWRate.docx");
        Document doc7 = new Document(outPath + "/LDYECS745H0010705VARate.docx");
        Document doc8 = new Document(outPath + "/LDYECS745H0010705VENum.docx");
        Document doc9 = new Document(outPath + "/LDYECS745H0010705VWRate.docx");

        //获取文档1的最后一节
        Section lastsec = doc1.getLastSection();
        //遍历文档2的所有段落内容，添加到文档1
        for (Section section : (Iterable<Section>) doc2.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        lastsec = doc1.getLastSection();
        for (Section section : (Iterable<Section>) doc3.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        lastsec = doc1.getLastSection();
        for (Section section : (Iterable<Section>) doc4.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        lastsec = doc1.getLastSection();
        for (Section section : (Iterable<Section>) doc5.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        lastsec = doc1.getLastSection();
        for (Section section : (Iterable<Section>) doc6.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        lastsec = doc1.getLastSection();
        for (Section section : (Iterable<Section>) doc7.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        lastsec = doc1.getLastSection();
        for (Section section : (Iterable<Section>) doc8.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        lastsec = doc1.getLastSection();
        for (Section section : (Iterable<Section>) doc9.getSections()) {
            for (DocumentObject obj : (Iterable<DocumentObject>) section.getBody().getChildObjects()
            ) {
                lastsec.getBody().getChildObjects().add(obj.deepClone());
            }
        }

        doc1.replace("推荐使用VIP服务器，高速、稳定、无广告！", "", false, true);


        //保存合并后的文档
        doc1.saveToFile(outPath + "/0_out.docx",FileFormat.Docx_2010);
    }

    public static void main(String[] args) {

    }
}
