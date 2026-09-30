package com.marginiq.analytics;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class CategoryAnalyticsController {
    private final CategoryAnalyticsService service;

    public CategoryAnalyticsController(CategoryAnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public List<Map<String, Object>> categories(@RequestParam(defaultValue = "30") int days) {
        return service.categoryProfitability(days);
    }
}
