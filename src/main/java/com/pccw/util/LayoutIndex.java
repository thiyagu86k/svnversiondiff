package com.pccw.util;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LayoutIndex {
    public static void main(String[] args) {
        String fileStoragePath="C:\\CMIS\\Fung_Ming\\Swd_Documentation_5_June_2020";
        createLayout2(fileStoragePath);
    }

    public static void createLayout2(String filepath){
        Set<String> exceptFolder= Stream.of("css","img","js").collect(Collectors.toSet());
        StringJoiner sj=new StringJoiner("\n");
        try{
            File[] folderList=new File(filepath).listFiles();
            for(File f:folderList){
                if(f.isDirectory() && !exceptFolder.contains(f.getName())){
                  File[] htmlFiles=  f.listFiles();
                  for(File ff:htmlFiles){
                        String pPath_FPath=f.getName()+"/"+ff.getName();
                        String iFrameP=CommonString.IFRAME_HTML.replace("$SRC_PATH$",pPath_FPath);
                        sj.add(iFrameP);
                  }

                }
            }
            String index2html=CommonString.INDEX_LAYOUT2.replace("$IFRAMES_LIST$",sj.toString());
            FileWriter fw=new FileWriter(Paths.get(filepath,"index2.html").toFile());
            fw.write(index2html);
            fw.flush();
            fw.close();


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
