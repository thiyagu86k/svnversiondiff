package com.pccw.util;

import java.io.*;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TemplateExtractor {
    private static final int BUFFER_SIZE = 4096;
    public static void extractFromResourceDIR(String pathtoExtract){
        try{
            String zipFile="documentation_template.zip";
           // InputStream link = TemplateExtractor.class.getResourceAsStream(zipFile);
            FileInputStream link=new FileInputStream(new File(TemplateExtractor.class.getResource("/"+zipFile).toURI()));
            unzipDocumentationTemplate(link,pathtoExtract);
            /*byte[] buffer=new byte[link.available()];
            link.read(buffer);
            FileOutputStream fos=new FileOutputStream(new File(Paths.get(pathtoExtract,zipFile).toString()));
            fos.write(buffer);
            fos.flush();
            fos.close();*/
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void unzipDocumentationTemplate(FileInputStream fos, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(fos);
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
