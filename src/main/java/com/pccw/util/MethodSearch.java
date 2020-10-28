package com.pccw.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodSearch {
    static Connection connection=null;
    public static void main(String[] args) {
      //  getDBConnection();
        long startTime=System.currentTimeMillis();
        String strPath ="C:\\CMIS\\CMIS_SRC_Code_SVN\\phase3_dev_current";
        String searchContent="checkGazetteCPptyForTranCon\\([\\w]+\\)";
        List<String> folderList= Stream.of("cis2_ecaccs_oracle","jar.cis2.commons","jar.cis2.export.file",
                "jar.cis2.framework.commons","jar.cis2.framework.hsm","jar.cis2.framework.service","jar.cis2.framework.ui",
                "jar.cis2.handheld.applet","jar.cis2.itf.online.commons","jar.cis2.printer.applet",
                "jar.cis2.scanner.applet","jar.cis2.search.common","jar.cis2.search.service",
                "jar.cis2.security.applet","jar.cis2.service","jar.cis2.share.ui","jar.cis2.smc.barcodeLabel",
                "jar.cis2.wristband.applet","pom.cis2.app","pom.cis2.batch","pom.cis2.batch.external",
                "pom.cis2.config.online","war.cis2.batch","war.cis2.batch.external","war.cis2.search.test",
                "war.cis2.search.ui.test","war.cis2.ui","war.cis2.ws").collect(Collectors.toList());


       // List<String> folderList=Stream.of("war.cis2.ui").collect(Collectors.toList());
        IndexWriter indexWriter=openIndexwriter();
        FileIndexer indexer=new FileIndexer();

        List<String> fileWithSearchContent=new ArrayList<String>();
        for(String folders:folderList) {
            File f = Paths.get(strPath, folders).toFile();
            try {
                searchFiles(f, searchContent, fileWithSearchContent,indexWriter,indexer);
            }catch(Exception e){
                e.printStackTrace();
        }
        }
        long endTime=System.currentTimeMillis();
        long total=endTime - startTime;
        System.out.println(total);
        fileWithSearchContent.stream().forEach(e->{
            System.out.println(e);
        });
        closeIndexWriter(indexWriter);
     //   closeDbConnection();
    }
    public void printFolders(){
        String fList="C:\\CMIS\\CMIS_SRC_Code_SVN\\phase3_dev_current";
        File f=new File(fList);
        File[] fList1=f.listFiles();
        Arrays.stream(fList1).filter(ff->ff.isDirectory() && !ff.getName().startsWith(".")).forEach(ff->{
            System.out.println("\""+ff.getName()+"\",");
        });
    }

    private static List<String> searchFiles(File file, String pattern, List<String> result,IndexWriter indexWriter,FileIndexer indexer) throws FileNotFoundException,IOException {

        if (!file.isDirectory()) {
            throw new IllegalArgumentException("file has to be a directory");
        }

        if (result == null) {
            result = new ArrayList<String>();
        }

        File[] files = file.listFiles();

        if (files != null) {
            for (File currentFile : files) {
                if (currentFile.isDirectory()) {
                    searchFiles(currentFile, pattern, result,indexWriter,indexer);
                } else {

                   if(currentFile.getName().toLowerCase().endsWith(".java")) {
                       System.out.println(currentFile.getAbsolutePath());
                        indexer.indexDoc(indexWriter,currentFile.toPath());
                   //    System.out.println(currentFile);
//                       Scanner scanner = new Scanner(currentFile);
//                       if (scanner.findWithinHorizon(pattern, 0) != null) {
//                           result.add(currentFile.getName());
//                       }
//                       scanner.close();
                   }
                }
            }
        }
        return result;
    }

//    private static void loadDBindex(String fileName, String content){
//        try {
//            String insert_srcCodeTable = "INSERT INTO SRCCODE_TABLE (fileName,content) VALUES(?,?)";
//            PreparedStatement pst = connection.prepareStatement(insert_srcCodeTable);
//            pst.setString(1,fileName);
//            pst.setString(2,content);
//            pst.execute();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    private static void getDBConnection(){
//        try{
//            connection = DriverManager.getConnection(CommonString.url, CommonString.user, CommonString.passwd);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    private static void closeDbConnection(){
//        try{
//            connection.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
    private static IndexWriter openIndexwriter()  {
        String indexPath="C:\\CMIS\\code_index_directory";
        IndexWriter writer=null;
        try {
            Directory dir = FSDirectory.open(Paths.get(indexPath));

            //analyzer with the default stop words
            Analyzer analyzer = new SimpleAnalyzer();

            //IndexWriter Configuration
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

            //IndexWriter writes new index files to the directory
            writer = new IndexWriter(dir, iwc);
        }catch(Exception e){
            if(writer!=null) {
                closeIndexWriter(writer);
            }
            e.printStackTrace();
        }
        return writer;
    }

    private static void closeIndexWriter(IndexWriter indexWriter){
        try{
            indexWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
