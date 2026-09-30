package com.marginiq.config;

import com.marginiq.product.*;
import com.marginiq.sales.*;
import com.marginiq.supplier.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class SeedData {
 @Bean CommandLineRunner seed(SupplierRepository suppliers, ProductRepository products, SaleRepository sales){
  return args -> {
   if(products.count()>0) return;
   Supplier s1=suppliers.save(new Supplier("Northstar Foods","ops@northstar.example",4));
   Supplier s2=suppliers.save(new Supplier("Urban Beverage Co.","supply@urbanbev.example",3));
   Supplier s3=suppliers.save(new Supplier("FreshLine Distribution","orders@freshline.example",2));
   List<Product> ps=products.saveAll(List.of(
    new Product("COF-001","Arabica Coffee Beans","Coffee",new BigDecimal("7.50"),new BigDecimal("16.99"),180,45,new BigDecimal("0.015"),s1),
    new Product("BEV-001","Cold Brew","Beverages",new BigDecimal("2.10"),new BigDecimal("6.99"),220,50,new BigDecimal("0.035"),s2),
    new Product("SNK-001","Granola","Snacks",new BigDecimal("3.20"),new BigDecimal("5.49"),75,35,new BigDecimal("0.010"),s1),
    new Product("FOD-001","Protein Box","Prepared Foods",new BigDecimal("6.00"),new BigDecimal("9.99"),140,40,new BigDecimal("0.070"),s3),
    new Product("BEV-002","Almond Milk","Dairy Alternatives",new BigDecimal("2.40"),new BigDecimal("4.99"),160,40,new BigDecimal("0.025"),s3),
    new Product("BAK-001","Chocolate Muffin","Bakery",new BigDecimal("1.10"),new BigDecimal("3.49"),90,30,new BigDecimal("0.090"),s3),
    new Product("BEV-003","Matcha Latte","Beverages",new BigDecimal("2.60"),new BigDecimal("7.49"),55,25,new BigDecimal("0.030"),s2),
    new Product("FOD-002","Turkey Sandwich","Prepared Foods",new BigDecimal("4.90"),new BigDecimal("8.99"),125,35,new BigDecimal("0.080"),s3)
   ));
   Random rnd=new Random(42);
   for(int n=0;n<1500;n++){
    Sale sale=new Sale(LocalDateTime.now().minusHours(rnd.nextInt(24*90)),
      rnd.nextDouble()<.18 ? new BigDecimal("1.00") : BigDecimal.ZERO);
    int count=1+rnd.nextInt(3);
    for(int j=0;j<count;j++){
      Product p=ps.get(rnd.nextInt(ps.size()));
      int qty=1+rnd.nextInt(4);
      sale.addItem(new SaleItem(p,qty,p.getUnitPrice(),p.getUnitCost()));
    }
    sales.save(sale);
   }
  };
 }
}
