package com.marginiq.analytics;

import com.marginiq.sales.*;
import org.springframework.stereotype.Service;
import java.math.*;
import java.time.*;
import java.util.*;

@Service
public class ProfitabilityService {
    private final SaleRepository sales;
    public ProfitabilityService(SaleRepository sales){ this.sales=sales; }

    public Map<String,Object> summary(int days) {
        var rows=sales.findBySoldAtBetween(LocalDateTime.now().minusDays(days), LocalDateTime.now());
        BigDecimal revenue=BigDecimal.ZERO, cogs=BigDecimal.ZERO, discounts=BigDecimal.ZERO, waste=BigDecimal.ZERO;
        for (Sale sale: rows) {
            discounts=discounts.add(sale.getDiscountAmount());
            for (SaleItem i: sale.getItems()) {
                BigDecimal qty=BigDecimal.valueOf(i.getQuantity());
                revenue=revenue.add(i.getUnitPrice().multiply(qty));
                cogs=cogs.add(i.getUnitCost().multiply(qty));
                waste=waste.add(i.getUnitCost().multiply(qty).multiply(i.getProduct().getWasteRate()));
            }
        }
        BigDecimal netRevenue=revenue.subtract(discounts);
        BigDecimal profit=netRevenue.subtract(cogs).subtract(waste);
        BigDecimal margin=netRevenue.signum()==0?BigDecimal.ZERO:
            profit.divide(netRevenue,4,RoundingMode.HALF_UP);
        Map<String,Object> r=new LinkedHashMap<>();
        r.put("periodDays",days); r.put("grossRevenue",revenue); r.put("discounts",discounts);
        r.put("netRevenue",netRevenue); r.put("cogs",cogs); r.put("estimatedWasteCost",waste);
        r.put("estimatedProfit",profit); r.put("profitMargin",margin); r.put("transactions",rows.size());
        return r;
    }
}
