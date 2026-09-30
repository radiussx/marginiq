package com.marginiq.analytics;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService service;
    public AnalyticsController(AnalyticsService service) { this.service=service; }

    @GetMapping("/summary")
    public Map<String,Object> summary(){ return service.summary(); }

    @GetMapping("/insights")
    public List<Map<String,Object>> insights(){ return service.insights(); }
}
