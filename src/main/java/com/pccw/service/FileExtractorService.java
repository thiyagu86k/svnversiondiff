package com.pccw.service;

import com.pccw.modal.FileRevisionInfo;
import com.pccw.util.*;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.internal.wc2.ng.SvnDiffGenerator;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc2.SvnDiff;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileExtractorService {
    String fileStoragePath;
    SVNRepository repository;
    SvnRepositoryConnector svnRc;
    String branchName = "/trunk";
    String url;
    String userName;
    String password;
    Set<String> fileExtension= Stream.of(".java",".properties",".xml",".sql",".jrxml",".xhtml",".js",".css").collect(Collectors.toSet());

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FileExtractorService(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;

    }

    Map<String, Set<String>> htmlFolderMap = new HashMap<>();

    public void extractFileFromSvn(String csvFilePath) {
        List<FileRevisionInfo> fileRevisionInfoList = LoadFileRevisionViaCSV.fromCSV(csvFilePath);
        svnRc = new SvnRepositoryConnector();
        this.repository = svnRc.connectToRepo(url, userName, password);
        TemplateExtractor.extractFromResourceDIR(fileStoragePath);
        createFolderLayoutFromPathRevison(fileRevisionInfoList);
        buildIndexHtml(indexTreeGenerator());
        createIntroPage();

    }
    public void extractFileFromSvn(List<FileRevisionInfo> fileRevisonList){
         List<FileRevisionInfo> fileRevisionInfoList = fileRevisonList;
        svnRc = new SvnRepositoryConnector();
        this.repository = svnRc.connectToRepo(url, userName, password);
        try {
            System.out.println("latest Revision No"+this.repository.getLatestRevision());
        } catch (SVNException ex) {
            Logger.getLogger(FileExtractorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        TemplateExtractor.extractFromResourceDIR(fileStoragePath);
        createFolderLayoutFromPathRevison(fileRevisionInfoList);
        buildIndexHtml(indexTreeGenerator());
        createIntroPage();
    }

    private void createFolderLayoutFromPathRevison(List<FileRevisionInfo> fileRevisionInfoList) {
        SVNProperties svnProperties = new SVNProperties();
        for (FileRevisionInfo fri : fileRevisionInfoList) {
            String path = fri.getFilePath();
            String extension=path.substring(path.lastIndexOf("."));
            if(fileExtension.contains(extension))
            {
                long revisionNo1 = fri.getRevisionNo1();
                long revisionNo2 = fri.getRevisionNo2();
                resetConnection();
                fri.setFile1Content(pullFile(path, revisionNo1, svnProperties, fri.getBranch()));
                System.out.println("File 1 Content== "+fri.getFile1Content());
                resetConnection();
                fri.setFile2Content(pullFile(path, revisionNo2, svnProperties, fri.getBranch()));
                createHtmlFileSubFile(fri);
            }

        }

    }

    private void diffFileUsingSVNKit(FileRevisionInfo fri){
        final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        long revision1=0;
        long revision2=0;
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final SvnDiffGenerator diffGenerator = new SvnDiffGenerator();
            diffGenerator.setBasePath(new File(""));

            final SvnDiff diff = svnOperationFactory.createDiff();
            
          //  diff.setSources(SvnTarget.fromURL(new SVNURL(url), SVNRevision.create(revision1)), url, SVNRevision.create(revision2)));
            diff.setDiffGenerator(diffGenerator);
            diff.setOutput(byteArrayOutputStream);
            diff.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            svnOperationFactory.dispose();
        }
    }
    private void fileDifferences(FileRevisionInfo fileRevisionInfo){
       // this.repository.testConnection();
    }

    private void resetConnection() {
        try {
            this.repository.testConnection();
            long revisonNo = this.repository.getLatestRevision();
        } catch (SVNException exp) {
            this.repository = svnRc.connectToRepo(url, userName, password);
        }
    }

    private void createHtmlFileSubFile(FileRevisionInfo fileRevisionInfo){
        if(fileRevisionInfo.getFilePath()!=null){
         //   System.out.println("HtmlSubFile: "+fileRevisionInfo.getFilePath());
            String fileUrl=fileRevisionInfo.getFilePath();
            System.out.println(fileUrl);
            String[] spliterFileUrl = fileUrl.split("\\\\");
            String lastFilename = spliterFileUrl[spliterFileUrl.length - 1];
            String firstFilename = spliterFileUrl[1];
          /*  if(firstFilename.contains(" ")){
               firstFilename=firstFilename.replace(" ","");
            }*/
            String middleName="";
            if(fileUrl.contains("pom.cis2.config.online")){
                middleName=spliterFileUrl[4];
                middleName=middleName.replace(".","_");
            }
            String replaceddotToUnderscore = firstFilename.replace(".", "_");

            String lastFilenameHtml=lastFilename.replace(".","_")+".html";
            if(middleName.length()>1){
                lastFilenameHtml=middleName+"_"+lastFilenameHtml;
            }
            File f=new File(Paths.get(this.fileStoragePath,replaceddotToUnderscore,lastFilenameHtml).toString());
            String fileName1_WithPath="./"+fileRevisionInfo.getRevisionNo1()+"_r"+"/"+lastFilename;
            String fileName2_With_Path="./"+fileRevisionInfo.getRevisionNo2()+"_r"+"/"+lastFilename;
            String revisionNo1="Not Exists";
            if(fileRevisionInfo.getRevisionNo1()>0){
                revisionNo1=fileRevisionInfo.getRevisionNo1()+"";
            }
            String revisionNo2="Not Exists";
            if(fileRevisionInfo.getRevisionNo2()>0){
                revisionNo2=fileRevisionInfo.getRevisionNo2()+"";
            }
            String file1Content=fileRevisionInfo.getFile1Content().replace("$","\\$");
            String file2Content=fileRevisionInfo.getFile2Content().replace("$","\\$");
            String subFileCtnt=CommonString.dataHtml.replace("$$FILE_1_CONTENT$$",file1Content)
                    .replace("$$FILE_2_CONTENT$$",file2Content)
                    .replace("$$REVISION_1$$",revisionNo1+"")
                    .replace("$$REVISION_2$$",revisionNo2+"")
                    .replace("$$FILE_PATH$$",fileUrl);

            try {
            //    System.out.println(f.getAbsolutePath());
                FileWriter fw = new FileWriter(f);
            //    System.out.println(f.getAbsolutePath());
             //   System.out.println(subFileCtnt);
                fw.write(subFileCtnt);
                fw.flush();
                fw.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private String pullFile(String fileUrl, long revisionNo, SVNProperties properties, String branchName) {
        if (fileUrl != null && revisionNo != '\0' && properties != null && branchName != null) {
            String[] spliterFileUrl = fileUrl.split("\\\\");
            String lastFilename = spliterFileUrl[spliterFileUrl.length - 1];
            String firstFilename = spliterFileUrl[1];
            String middleName="";
            if(fileUrl.contains("pom.cis2.config.online")){
                middleName=spliterFileUrl[4];
                middleName=middleName.replace(".","_");
                lastFilename=middleName+"_"+lastFilename;
            }
            pushHtmlFolderFile(firstFilename, lastFilename);
            String replaceddotToUnderscore = firstFilename.replace(".", "_");

            createFolder(Paths.get(this.fileStoragePath, replaceddotToUnderscore).toFile());
           // createFolder(Paths.get(this.fileStoragePath, replaceddotToUnderscore, revisionNo + "_r").toFile());
            try {
                //FileOutputStream fos = new FileOutputStream(Paths.get(this.fileStoragePath, replaceddotToUnderscore, revisionNo + "_r", lastFilename).toFile());
                fileUrl = fileUrl.replace("\\", "/");
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                //System.out.println(fileUrl);
                try {
                    String fUrl = branchName + fileUrl;

                    int retryCount=0;
                    int maxRetryCount=2;
                    while(retryCount<maxRetryCount) {
                        try {
                            System.out.println("Retry Count="+retryCount+" Revision No: "+revisionNo+"\tFilePath: "+fUrl);
                            this.repository.getFile(fUrl, revisionNo, properties, baos);
                            break;
                        } catch (SVNException e) {
                            System.out.println("Reason for error "+e.getMessage());
                            retryCount=retryCount+1;
                            resetConnection();
                        }
                    }
                    baos.flush();
                    baos.close();
                    return new String(baos.toByteArray(), Charset.forName("UTF-8"));
                } catch (Exception ex) {
                    System.out.println("FirstMessage: " + ex.getMessage());
                    return "";
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return "";
    }

    private void createFolder(File foldername) {
        if (foldername != null) {
            if (foldername.exists() != true) {
                foldername.mkdir();
            }
        }
    }

    private void pushHtmlFolderFile(String folderPath, String fileName) {
        folderPath = folderPath.toLowerCase();
        if (htmlFolderMap.get(folderPath) != null) {
            htmlFolderMap.get(folderPath).add(fileName);
        } else {
            Set<String> fileNameList = new HashSet<>();
            fileNameList.add(fileName);
            htmlFolderMap.put(folderPath, fileNameList);
        }
    }

    private void buildIndexHtml(String treeNode) {
        try {
            File f = new File(Paths.get(fileStoragePath, "index.html").toString());
            FileWriter fw = new FileWriter(f);
            String indexHtml=CommonString.indexHtml.replace("$FILE_NODE$",treeNode);
            fw.write(indexHtml);
            fw.flush();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String indexTreeGenerator(){
       StringJoiner sj=new StringJoiner(",");
       final String template_1="{id:$$XXNO$$,pId:0,name:\"$$NAME$$\",open:false}";
        final String template_2="{id:$$XXNO$$,pId:$$PID$$,name:\"$$NAME$$\",file:\"$$PATH$$\"}";
        if(htmlFolderMap!=null){
            Set<String> keySet=htmlFolderMap.keySet();
            int i=1;
                for(String s:keySet){
                    String rootNode=template_1.replace("$$XXNO$$",""+i).replace("$$NAME$$",s);
                    sj.add(rootNode+"\n");
                    Set<String> childElements=htmlFolderMap.get(s);
                    int j=i*1000;
                    for(String s1:childElements){
                    String fName=s1.replace(".","_");
                    String childNode=template_2.replace("$$XXNO$$",""+j).replace("$$PID$$",""+i)
                            .replace("$$NAME$$",s1).replace("$$PATH$$",s.replace(".","_")+"/"+fName);
                    sj.add(childNode+"\n");
                        j=j+1;
                    }
                    i=i+1;
                }
                return sj.toString();
        }
        return "";
    }

    private void createIntroPage(){
        Set<String> keySet=htmlFolderMap.keySet();
        int size=0;
        for(String s:keySet){
            size=htmlFolderMap.get(s).size() +size;
        }

        String content= CommonString.introPage.replace("$$FILE_COUNT$$",size+"");
        createFolder(Paths.get(fileStoragePath,"main").toFile());
        try {
            FileWriter fw = new FileWriter(Paths.get(fileStoragePath, "main", "index.html").toFile());
            fw.write(content);
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
