package com.marginiq.analytics;

import com.marginiq.product.Product;
import com.marginiq.product.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AnalyticsService {
    private final ProductRepository products;
    public AnalyticsService(ProductRepository products) { this.products = products; }

    public Map<String,Object> summary() {
        List<Product> ps = products.findAll();
        BigDecimal revenue = ps.stream().map(p -> p.getUnitPrice().multiply(BigDecimal.valueOf(Math.max(1,p.getStockQuantity()/5))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal cost = ps.stream().map(p -> p.getUnitCost().multiply(BigDecimal.valueOf(Math.max(1,p.getStockQuantity()/5))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal profit = revenue.subtract(cost);
        BigDecimal margin = revenue.signum()==0 ? BigDecimal.ZERO : profit.divide(revenue, 4, RoundingMode.HALF_UP);
        return Map.of("revenue", revenue, "estimatedCost", cost, "estimatedProfit", profit,
                "margin", margin, "productCount", ps.size(),
                "highInventoryRiskCount", ps.stream().filter(p -> p.getStockQuantity() > 100).count());
    }

    public List<Map<String,Object>> insights() {
        List<Map<String,Object>> out = new ArrayList<>();
        for (Product p : products.findAll()) {
            BigDecimal margin = p.getUnitPrice().subtract(p.getUnitCost())
                    .divide(p.getUnitPrice(), 4, RoundingMode.HALF_UP);
            String label = p.getUnitPrice().compareTo(BigDecimal.valueOf(20)) > 0 && margin.compareTo(BigDecimal.valueOf(.30)) >= 0
                    ? "Growth Opportunity"
                    : p.getStockQuantity() > 100 ? "Overstock Risk"
                    : margin.compareTo(BigDecimal.valueOf(.15)) < 0 ? "Margin Problem" : "Healthy";
            out.add(Map.of("product", p.getName(), "category", p.getCategory(), "margin", margin, "classification", label));
        }
        return out;
    }
}
