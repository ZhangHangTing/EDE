package bupt.zht.service;

public class ProductAService implements Service {
     private String material;
     private String productSatff;
     public ProductAService(String material, String productSatff){
         this.material = material;
         this.productSatff = productSatff;
     }
    @Override
    public void run() {
        System.out.print(productSatff + " 执行： " + material);
    }
}
