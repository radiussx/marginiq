package com.marginiq.config;

import com.marginiq.product.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.List;

@Configuration
public class SeedData {
    @Bean
    CommandLineRunner seed(ProductRepository repo) {
        return args -> {
            if (repo.count() > 0) return;
            repo.saveAll(List.of(
                new Product("Arabica Coffee Beans","Coffee",new BigDecimal("7.50"),new BigDecimal("16.99"),180),
                new Product("Cold Brew","Beverages",new BigDecimal("2.10"),new BigDecimal("6.99"),220),
                new Product("Granola","Snacks",new BigDecimal("3.20"),new BigDecimal("5.49"),75),
                new Product("Protein Box","Prepared Foods",new BigDecimal("6.00"),new BigDecimal("9.99"),140),
                new Product("Almond Milk","Dairy Alternatives",new BigDecimal("2.40"),new BigDecimal("4.99"),160),
                new Product("Chocolate Muffin","Bakery",new BigDecimal("1.10"),new BigDecimal("3.49"),90),
                new Product("Matcha Latte","Beverages",new BigDecimal("2.60"),new BigDecimal("7.49"),55),
                new Product("Turkey Sandwich","Prepared Foods",new BigDecimal("4.90"),new BigDecimal("8.99"),125)
            ));
        };
    }
}
