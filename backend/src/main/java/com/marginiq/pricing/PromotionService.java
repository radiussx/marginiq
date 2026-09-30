package com.marginiq.pricing;

import com.marginiq.product.*;
import org.springframework.stereotype.Service;
import java.math.*;
import java.util.*;

@Service
public class PromotionService {
    private final ProductRepository products;
    public PromotionService(ProductRepository products){this.products=products;}

    public Map<String,Object> simulate(PromotionRequest r) {
        Product p=products.findById(r.productId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        BigDecimal baseline=BigDecimal.valueOf(r.baselineUnits());
        BigDecimal promotedUnits=baseline.multiply(BigDecimal.ONE.add(r.expectedDemandLift()));
        BigDecimal promoPrice=p.getUnitPrice().multiply(BigDecimal.ONE.subtract(r.discountRate()));
        BigDecimal currentProfit=p.getUnitPrice().subtract(p.getUnitCost()).multiply(baseline);
        BigDecimal projectedProfit=promoPrice.subtract(p.getUnitCost()).multiply(promotedUnits);
        BigDecimal change=currentProfit.signum()==0?BigDecimal.ZERO:
            projectedProfit.subtract(currentProfit).divide(currentProfit,4,RoundingMode.HALF_UP);
        Map<String,Object> result=new LinkedHashMap<>();
        result.put("product",p.getName()); result.put("currentPrice",p.getUnitPrice());
        result.put("promotionalPrice",promoPrice.setScale(2,RoundingMode.HALF_UP));
        result.put("baselineUnits",r.baselineUnits()); result.put("projectedUnits",promotedUnits.setScale(0,RoundingMode.HALF_UP));
        result.put("currentProfit",currentProfit.setScale(2,RoundingMode.HALF_UP));
        result.put("projectedProfit",projectedProfit.setScale(2,RoundingMode.HALF_UP));
        result.put("profitChange",change);
        result.put("recommendation",change.signum()>0 ? "Promotion may improve contribution profit" : "Promotion may reduce contribution profit");
        return result;
    }
}
