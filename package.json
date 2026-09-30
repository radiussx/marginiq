package com.marginiq.pricing;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/pricing")
public class PromotionController {
    private final PromotionService service;
    public PromotionController(PromotionService service){this.service=service;}
    @PostMapping("/simulate")
    public Map<String,Object> simulate(@Valid @RequestBody PromotionRequest request){return service.simulate(request);}
}
