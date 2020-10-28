import com.pccw.service.TestIFaceImpl;
import com.pccw.skeleton.TestIface;

public class App2 {
    public static void main(String[] args) {
        TestIface tFace=new TestIFaceImpl();
        tFace.helloMethod();
    }
}
