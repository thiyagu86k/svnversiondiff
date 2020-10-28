package com.pccw.util;

import com.pccw.modal.FileRevisionInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LoadFileRevisionViaCSV {
    public static List<FileRevisionInfo> fromCSV(String fileName) {
        List<FileRevisionInfo> fileRevisonInfoList = new ArrayList<>();
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            for (CSVRecord record : records) {
                FileRevisionInfo fileRevisionInfo = new FileRevisionInfo();
                String PRD_Version = record.get("PRD_version");
                if (PRD_Version != null && PRD_Version.length()>0) {
                    try {
                        fileRevisionInfo.setRevisionNo1(Long.parseLong(PRD_Version));
                    }catch(NumberFormatException e){
                        fileRevisionInfo.setRevisionNo1(0);
                    }
                }
                String SWD_Version = record.get("SWD_version");
                if (SWD_Version != null && SWD_Version.length()>0) {
                    try {
                        fileRevisionInfo.setRevisionNo2(Long.parseLong(SWD_Version));
                    }catch(NumberFormatException e){
                        fileRevisionInfo.setRevisionNo2(0);
                    }
                }
                String filePath = record.get("File_Path");
                if (filePath != null && filePath.length()>0) {
                    fileRevisionInfo.setFilePath(filePath);
                }
                String branch = record.get("branch");
                if (branch != null && branch.length()>0) {
                    fileRevisionInfo.setBranch(branch);
                }else{
                    fileRevisionInfo.setBranch("");
                }

                fileRevisonInfoList.add(fileRevisionInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileRevisonInfoList;
    }
}
