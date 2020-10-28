package com.pccw.util;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.io.ISVNSession;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.*;

public class SvnRepositoryConnector {
    ISVNSession session;

    public  SVNRepository connectToRepo(String url, String userName, String password) {
        SVNRepository repository=null;
        try {
            BasicAuthenticationManager basicAuthMgr=BasicAuthenticationManager.newInstance(userName,password.toCharArray());
            SVNURL svnURL = SVNURL.parseURIEncoded(url);
             repository = SVNRepositoryFactory.create(svnURL, session);
            repository.setAuthenticationManager(basicAuthMgr);
          // repository.getFileRevisions(fileUrl, Collections.emptyList(),0,-1);
        } catch (Exception svnEx) {
            svnEx.printStackTrace();
        }
        return repository;
    }

    private void createFolder(File foldername){
        if(foldername!=null){
            if(foldername.exists()!=true){
                foldername.mkdir();
            }
        }
    }

    private void pullFile(){
       /* System.out.println(repository.getLatestRevision());
        String fileUrl="/trunk/jar.cis2.commons/src/main/java/gov/hkpf/cis2/common/instrumentation/SpringAopAction.java";

        File f=new File("C:\\CMIS\\Fung_Ming\\documet_test\\"+"jar_cis2_commons");

        long firstRevisionNo=22870l;
        long secondRevisionNo=15147l;
        String firstRevisionFolderName=firstRevisionNo+"_"+"r";
        String secondRevisionFolderName=secondRevisionNo+"_"+"r";
        createFolder(f);

        createFolder(Paths.get(f.getAbsolutePath(),firstRevisionFolderName).toFile());
        createFolder(Paths.get(f.getAbsolutePath(),secondRevisionFolderName).toFile());
        File f1=Paths.get(f.getAbsolutePath(),firstRevisionFolderName,"SpringAopAction.java").toFile();
        System.out.println(f1.getAbsolutePath());
        //   repository.

        FileOutputStream fos1=new FileOutputStream(f1);
        System.out.println(fileUrl);
        // Map fileProp=new HashMap<String,Object>();
        repository.getFile(fileUrl,firstRevisionNo,new SVNProperties(),fos1);
        fos1.flush();
        fos1.close();
        FileOutputStream fos2=new FileOutputStream(Paths.get(f.getAbsolutePath(),secondRevisionFolderName,"SpringAopAction.java").toFile());
        repository.getFile(fileUrl,secondRevisionNo,new SVNProperties(),fos2);

        fos2.flush();
        fos2.close(); */
    }

    private void logRepository(SVNRepository repository,String fileUrl) {
        Collection logEntries = null;
        try {
            logEntries = repository.log(new String[]{fileUrl}, null, 0, -1, true, true);
            for (Iterator entries = logEntries.iterator(); entries.hasNext(); ) {
                SVNLogEntry logEntry = (SVNLogEntry) entries.next();
                System.out.println("---------------------------------------------");
                System.out.println("revision: " + logEntry.getRevision());
                System.out.println("author: " + logEntry.getAuthor());
                System.out.println("date: " + logEntry.getDate());
                System.out.println("log message: " + logEntry.getMessage());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
