package com.marginiq.product;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false) private String category;
    @Column(nullable=false) private BigDecimal unitCost;
    @Column(nullable=false) private BigDecimal unitPrice;
    @Column(nullable=false) private Integer stockQuantity;

    protected Product() {}
    public Product(String name, String category, BigDecimal unitCost, BigDecimal unitPrice, Integer stockQuantity) {
        this.name=name; this.category=category; this.unitCost=unitCost; this.unitPrice=unitPrice; this.stockQuantity=stockQuantity;
    }
    public Long getId(){return id;}
    public String getName(){return name;}
    public String getCategory(){return category;}
    public BigDecimal getUnitCost(){return unitCost;}
    public BigDecimal getUnitPrice(){return unitPrice;}
    public Integer getStockQuantity(){return stockQuantity;}
}
