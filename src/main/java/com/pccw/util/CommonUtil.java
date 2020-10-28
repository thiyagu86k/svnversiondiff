package com.pccw.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonUtil {
    public static void createFolder(File foldername) {
        if (foldername != null) {
            if (foldername.exists() != true) {
                foldername.mkdir();
            }
        }
    }
    
    public static String getExceptionString(Exception e){
        StringWriter sw=new StringWriter();
            PrintWriter pw=new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
    }
}
