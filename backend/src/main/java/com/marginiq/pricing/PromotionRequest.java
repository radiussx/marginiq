package com.marginiq.pricing;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record PromotionRequest(
    @NotNull Long productId,
    @NotNull @DecimalMin("0.0") @DecimalMax("0.90") BigDecimal discountRate,
    @NotNull @DecimalMin("0.0") BigDecimal expectedDemandLift,
    @Min(1) int baselineUnits
) {}
