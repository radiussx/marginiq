package com.marginiq.analytics;

import com.marginiq.sales.Sale;
import com.marginiq.sales.SaleItem;
import com.marginiq.sales.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CategoryAnalyticsService {
    private final SaleRepository sales;

    public CategoryAnalyticsService(SaleRepository sales) {
        this.sales = sales;
    }

    public List<Map<String, Object>> categoryProfitability(int days) {
        List<Sale> rows = sales.findBySoldAtBetween(
                LocalDateTime.now().minusDays(days), LocalDateTime.now());

        Map<String, BigDecimal> revenue = new HashMap<>();
        Map<String, BigDecimal> cost = new HashMap<>();

        for (Sale sale : rows) {
            for (SaleItem item : sale.getItems()) {
                String category = item.getProduct().getCategory();
                BigDecimal qty = BigDecimal.valueOf(item.getQuantity());
                revenue.merge(category, item.getUnitPrice().multiply(qty), BigDecimal::add);
                cost.merge(category, item.getUnitCost().multiply(qty), BigDecimal::add);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (String category : revenue.keySet()) {
            BigDecimal r = revenue.get(category);
            BigDecimal c = cost.getOrDefault(category, BigDecimal.ZERO);
            BigDecimal profit = r.subtract(c);
            BigDecimal margin = r.signum() == 0
                    ? BigDecimal.ZERO
                    : profit.divide(r, 4, RoundingMode.HALF_UP);

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("category", category);
            row.put("revenue", r.setScale(2, RoundingMode.HALF_UP));
            row.put("profit", profit.setScale(2, RoundingMode.HALF_UP));
            row.put("margin", margin);
            result.add(row);
        }

        result.sort((a, b) -> ((BigDecimal) b.get("profit")).compareTo((BigDecimal) a.get("profit")));
        return result;
    }
}
