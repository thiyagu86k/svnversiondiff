package com.pccw.util;

import java.io.File;

public class CommonUtil {
    public static void createFolder(File foldername) {
        if (foldername != null) {
            if (foldername.exists() != true) {
                foldername.mkdir();
            }
        }
    }
}
