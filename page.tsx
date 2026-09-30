package com.marginiq.analytics;

import com.marginiq.product.*;
import org.springframework.stereotype.Service;
import java.math.*;
import java.util.*;

@Service
public class AnalyticsService {
    private final ProductRepository products;
    public AnalyticsService(ProductRepository products){this.products=products;}

    public List<Map<String,Object>> insights() {
        List<Map<String,Object>> result=new ArrayList<>();
        for(Product p: products.findAll()){
            BigDecimal unitMargin=p.getUnitPrice().subtract(p.getUnitCost());
            BigDecimal margin=p.getUnitPrice().signum()==0?BigDecimal.ZERO:
                unitMargin.divide(p.getUnitPrice(),4,RoundingMode.HALF_UP);
            String classification;
            String recommendation;
            if(p.getStockQuantity()>p.getReorderPoint()*3 && margin.compareTo(new BigDecimal("0.25"))<0){
                classification="MARGIN_AND_OVERSTOCK_RISK";
                recommendation="Review supplier cost, price, or targeted markdown strategy.";
            } else if(p.getStockQuantity()>p.getReorderPoint()*3){
                classification="OVERSTOCK_RISK";
                recommendation="Consider a targeted promotion or reduce replenishment.";
            } else if(margin.compareTo(new BigDecimal("0.20"))<0){
                classification="MARGIN_PROBLEM";
                recommendation="Review pricing and supplier economics.";
            } else if(margin.compareTo(new BigDecimal("0.45"))>=0){
                classification="GROWTH_OPPORTUNITY";
                recommendation="Protect availability and test demand-expansion strategies.";
            } else {
                classification="HEALTHY";
                recommendation="Maintain current economics and monitor demand.";
            }
            Map<String,Object> row=new LinkedHashMap<>();
            row.put("productId",p.getId()); row.put("sku",p.getSku()); row.put("product",p.getName());
            row.put("category",p.getCategory()); row.put("stock",p.getStockQuantity());
            row.put("unitMargin",unitMargin); row.put("margin",margin);
            row.put("classification",classification); row.put("recommendation",recommendation);
            result.add(row);
        }
        return result;
    }
}
