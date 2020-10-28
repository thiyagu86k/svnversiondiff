import com.pccw.service.FileExtractorService;
import com.pccw.util.CommonUtil;
import com.pccw.util.LayoutIndex;
import com.pccw.util.SvnRepositoryConnector;

import java.io.File;

public class App {
    public static void main(String[] args) {
      //  String csvFile="C:\\CMIS\\Fung_Ming\\Bug_file.csv";
        String csvFile="E:\\Kim\\16-07-2020\\GAZ_PR_filelist_v3.csv";
      // String csvFile="C:\\CMIS\\Fung_Ming\\POM_Config_diff_bug.csv";
        String url="http://10.87.103.231/svn/cis2";
        String userName="01677137";
        String password="01677137";
       // String fileStoragePath="C:\\CMIS\\Fung_Ming\\SWD_Documentation_Bug_v2";
        String fileStoragePath="E:\\Kim\\16-07-2020\\Doc_Example1";
        File f=new File(fileStoragePath);
        CommonUtil.createFolder(f);
        FileExtractorService fileExtractorService=new FileExtractorService(fileStoragePath);
        fileExtractorService.setUrl(url);
        fileExtractorService.setUserName(userName);
        fileExtractorService.setPassword(password);
        fileExtractorService.extractFileFromSvn(csvFile);
        LayoutIndex.createLayout2(fileStoragePath);


   // svnRc.connectToRepo(url,userName,password);
    }
}
