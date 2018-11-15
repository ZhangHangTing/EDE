//package bupt.zht;
//
//
//import bupt.zht.bom.BpelC;
//import java.io.File;
//import java.io.IOException;
//
//public class Main {
//    public static void main(String[] args){
//        Main m = new Main();
//        String bpelPath = System.getProperty("user.dir")+"/ode-start/src/main/resources/CalculatorProcess.bpel";
//        String cbpSrcPath =  System.getProperty("user.dir")+"/ode-start/src/main/resources";
//        String cbpPath =  System.getProperty("user.dir")+"/ode-start/src/main/resources/CalculatorProcess.cbp";
//        m.createBpel(cbpSrcPath,"CalculatorProcess.cbp");
//        File bpel = new File(bpelPath);
//        m.compile(bpel,cbpPath);
//    }
//    public void compile(final File bpelFile,String cbpPath){
//        //通过编译器类BpelCompiler实现bpel文件的编译
//        final BpelC bpelc = BpelC.newBpelCompiler();
//        //设置bpel文件的基目录，bpel文件需要的wsdl文件及其其他的资源的文件都可以从这个目录中寻找
////        bpelc.setBaseDirectory(bpelFile);
//
//        //首先解析bepel的节点树，将标签之前的关系都解析出来
//        bpelc.parseBpel(bpelFile);
//        //之后创建节点树的根对象process，此process对象代表这一个bpel文件，最后会序列化成cbp文件保存到原来bpel的相同路径中
//        bpelc.compile(bpelFile,cbpPath);
//    }
////    public File findBpelWsdl(File bpelFile){
////        //根据bpel文件找到对应的wsdl文件
////        //返回的是wsdl文件的File
////        return new File(String.valueOf(bpelFile.getParentFile()));
////    }
//    public void createBpel(String bpelPath,String bpelName){
//        File f = new File(bpelPath);
//        if(!f.exists()){
//            f.mkdirs();
//        }
//        File file = new File(f,bpelName);
//        if(!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
