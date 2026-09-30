package com.marginiq.pricing;

import com.marginiq.product.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromotionServiceTest {
 @Test void profitablePromotionReturnsPositiveChange(){
   ProductRepository repo=mock(ProductRepository.class);
   Product p=new Product("T-1","Test Product","Test",new BigDecimal("4.00"),new BigDecimal("10.00"),50,20,BigDecimal.ZERO,null);
   when(repo.findById(1L)).thenReturn(Optional.of(p));
   var result=new PromotionService(repo).simulate(new PromotionRequest(1L,new BigDecimal(".10"),new BigDecimal(".30"),100));
   assertTrue(((BigDecimal)result.get("profitChange")).signum()>0);
 }
}
