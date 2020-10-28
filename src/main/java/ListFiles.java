import java.io.File;
import java.nio.file.Paths;

public class ListFiles {
    public static void main(String[] args) {
     //String folderPath="C:\\CMIS\\Kim\\SWD";
        String folderPath="C:\\CMIS\\Kim\\SWD\\fallback";
        try {
            File f = Paths.get(folderPath).toFile();
            File[] fileList=f.listFiles((file)->file.getName().toLowerCase().endsWith(".sql"));
            for(File ff:fileList){
                System.out.println(ff.getName());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
