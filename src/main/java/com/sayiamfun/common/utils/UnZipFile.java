package com.sayiamfun.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 实现文件[夹]解压
 *
 * @author ljheee
 */
public class UnZipFile {

    /**
     * 解压到指定目录
     *
     * @param zipPath
     * @param descDir
     */
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        unZipFiles(new File(zipPath), descDir);
    }

    /**
     * 解压文件到指定目录
     * 解压后的文件名，和之前一致
     *
     * @param zipFile 待解压的zip文件
     * @param descDir 指定目录
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile, String descDir) throws IOException {

        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));//解决中文文件夹乱码
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));

        File pathFile = new File(descDir + name);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");

            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
//			System.out.println(outPath);

            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        System.out.println("******************解压完毕********************");
        return;
    }

    //测试
    public static void main(String[] args) {
//            unZipFiles(new File("/Users/liwenjie/Downloads/testD.zip"), "/Users/liwenjie/Downloads/vehData/vehOut/testD/");

        String inputPath = "/Volumes/UsbDisk/data_analysis/";   //要解压的文件路径
        String inputPath1 = "/Volumes/UsbDisk/data_analysis2/";   //要解压的文件路径2
        String outPath = "";   //解压文件输出的路径

        Set<String> strings = ScanPackage.scanZipFiles(inputPath);
        strings.addAll(ScanPackage.scanZipFiles(inputPath1));
        List<String> vins = new ArrayList<>();
        vins.add("8042");
//            List<String> vins = Arrays.asList("LB378Y4W6JA173771, LB378Y4W7JA172452, LB378Y4W4JA173655, LB378Y4W7JA174007, LB378Y4W8JA172671, LB378Y4W2JA173394, LB378Y4W0JA177671, LB378Y4W9JA176521, LB378Y4W9JA174669, LB378Y4W5JA172711, LB378Y4W6JA177836, LB378Y4W3JA176417, LB378Y4W3JA173680, LB378Y4W6JA176539, LB378Y4W2JA176554, LB378Y4W2JA173637, LB378Y4W2JA175582, LB378Y4W4JA173509, LB378Y4W1JA174066, LB378Y4W3JA176627, LB378Y4W9JA178043, LB378Y4W1JA176531, LB378Y4W3JA174439, LB378Y4W0JA174060, LB378Y4W4JA174370, LB378Y4W7JA177795, LB378Y4WXJA173529, LB378Y4W0JA172454, LB378Y4W2JA175906, LB378Y4W2JA173492, LB378Y4W3JA176479, LB378Y4W5JA173146, LB378Y4W2JA175002, LB378Y4W4JA179407, LB378Y4W1JA174035, LB378Y4WXJA176401, LB378Y4W0JA176455, LB378Y4W8JA174033, LB378Y4W7JA176064, LB378Y4W7JA178574, LB378Y4W8JA173853, LB378Y4W8JA173416, LB378Y4W1JA176478, LB378Y4W2JA179633, LB378Y4W9JA174607, LB378Y4W0JA176469".split(","));
        for (String string : strings) {
            for (String vin : vins) {
                if (string.contains(vin.trim())) {
                    System.out.println(string);
//                        UnZipFile.unZipFiles(string, outPath);
                    break;
                }
            }
        }

    }


}
