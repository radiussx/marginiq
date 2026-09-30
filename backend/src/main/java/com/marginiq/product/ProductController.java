package com.marginiq.product;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repo;
    public ProductController(ProductRepository repo){this.repo=repo;}

    @GetMapping
    public List<Product> all(){return repo.findAll();}

    @GetMapping("/top-inventory")
    public List<Product> topInventory(){return repo.findTop10ByOrderByStockQuantityDesc();}
}
