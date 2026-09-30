package com.marginiq.product;

import com.marginiq.supplier.Supplier;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String sku;
    @Column(nullable=false) private String name;
    @Column(nullable=false) private String category;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal unitCost;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal unitPrice;
    @Column(nullable=false) private Integer stockQuantity;
    @Column(nullable=false) private Integer reorderPoint;
    @Column(nullable=false, precision=5, scale=4) private BigDecimal wasteRate;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="supplier_id")
    private Supplier supplier;

    protected Product() {}
    public Product(String sku, String name, String category, BigDecimal unitCost, BigDecimal unitPrice,
                   Integer stockQuantity, Integer reorderPoint, BigDecimal wasteRate, Supplier supplier) {
        this.sku=sku; this.name=name; this.category=category; this.unitCost=unitCost; this.unitPrice=unitPrice;
        this.stockQuantity=stockQuantity; this.reorderPoint=reorderPoint; this.wasteRate=wasteRate; this.supplier=supplier;
    }
    public Long getId(){return id;} public String getSku(){return sku;} public String getName(){return name;}
    public String getCategory(){return category;} public BigDecimal getUnitCost(){return unitCost;}
    public BigDecimal getUnitPrice(){return unitPrice;} public Integer getStockQuantity(){return stockQuantity;}
    public Integer getReorderPoint(){return reorderPoint;} public BigDecimal getWasteRate(){return wasteRate;}
    public Supplier getSupplier(){return supplier;}
}
